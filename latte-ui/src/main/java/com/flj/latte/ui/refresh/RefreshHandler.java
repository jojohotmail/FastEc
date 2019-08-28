package com.flj.latte.ui.refresh;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flj.latte.global.Latte;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.IError;
import com.flj.latte.net.callback.IFailure;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.util.log.LatteLogger;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private IOnRefresh mIOnRefresh = null;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter,
                           IOnRefresh onRefresh,
                           PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.mIOnRefresh = onRefresh;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,
                                        IOnRefresh onRefresh) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, onRefresh, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
                mIOnRefresh.setDistanceY();

            }
        }, 1000);
    }

    public void firstPage(String url) {

        LatteLogger.d("IUDHAS", url);
        //有一千秒的延迟，实际用应该取消
//        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(response -> {
                    final JSONObject object = JSON.parseObject(response);
                    BEAN.setTotal(object.getInteger("total"))
                            .setPageSize(object.getInteger("page_size"));
                    if (mAdapter != null && mAdapter.getData().size() > 0) {
                        mAdapter.getData().clear();
                        mAdapter.loadMoreEnd(false);
                        //累加数量
                        BEAN.setCurrentCount(0);
                    }
                    //设置Adapter
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.setPageIndex(0);

                })
                .build()
                .get();
    }

    private void paging(final String url) {
        BEAN.addIndex();
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (currentCount >= total || BEAN.getPageIndex() > pageSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            RestClient.builder()
                    .url(url + index)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            mAdapter.addData(CONVERTER.setJsonData(response).convert());
                            //累加数量
                            BEAN.setCurrentCount(mAdapter.getData().size());
                            mAdapter.loadMoreComplete();

                        }
                    })
                    .build()
                    .get();

        }
    }


    @Override
    public void onRefresh() {
        refresh();
    }


    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}
