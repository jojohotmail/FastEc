package com.flj.latte.ec.main.index;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.fragments.BaseFragment;
import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.flj.latte.ec.main.EcBottomFragment;
import com.flj.latte.ec.main.index.search.SearchFragment;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.RestCreator;
import com.flj.latte.net.rx.RxRestClient;
import com.flj.latte.ui.recycler.BaseDecoration;
import com.flj.latte.ui.refresh.IOnRefresh;
import com.flj.latte.ui.refresh.RefreshHandler;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 首页
 */
public class IndexFragment extends BottomItemFragment implements
        View.OnFocusChangeListener,
        IOnRefresh {

    private RecyclerView mRecyclerView = null;
    private SwipeRefreshLayout mRefreshLayout = null;

    private RefreshHandler mRefreshHandler = null;
    private Toolbar mToolbar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_index);
        mRefreshLayout = $(R.id.srl_index);

        mToolbar = $(R.id.tb_index);

        final IconTextView mIconScan = $(R.id.icon_index_scan);
        final AppCompatEditText mSearchView = $(R.id.et_search_view);

        mIconScan.setOnClickListener(view -> startScanWithCheck((BaseFragment) getParentFragment()));

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter(), this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, args ->
                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show());
        mSearchView.setOnFocusChangeListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Toolbar toolbar = view.findViewById(R.id.tb_index);
        toolbar.getBackground().setAlpha(0);
    }

    //TODO:测试方法，没啥卵用
    void onCallRxGet() {

        final String url = "index.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();

        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO:测试方法，没啥卵用X2
    private void onCallRxRestClient() {


        final String url = "index.php";
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        mRefreshLayout.setProgressViewOffset(true, 120, 300);

    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        final Context context = getContext();
        mRecyclerView.setLayoutManager(manager);
        if (context != null) {
            mRecyclerView.addItemDecoration
                    (BaseDecoration.create(ContextCompat.getColor(context, R.color.app_background), 5));
        }
        final EcBottomFragment ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public Toolbar setToolbar() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        AutoSize.cancelAdapt(getActivity());
        initRefreshLayout();
        AutoSize.autoConvertDensityOfGlobal(getActivity());
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }


    @Override
    public void onResume() {
        super.onResume();
        AutoSize.autoConvertDensityOfGlobal(getActivity());
    }


    @Override
    public void setDistanceY() {
        AutoSize.autoConvertDensityOfGlobal(getActivity());
        final EcBottomFragment ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
        mRefreshHandler.firstPage("index.php");
        TranslucentBehavior.mDistanceY = 0;
        mToolbar.getBackground().setAlpha(0);
//        mToolbar.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentDelegate().start(new SearchFragment());
        }
    }


}
