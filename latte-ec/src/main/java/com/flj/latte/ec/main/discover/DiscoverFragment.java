package com.flj.latte.ec.main.discover;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.flj.latte.fragments.web.WebFragmentImpl;
import com.diabin.latte.ec.R;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CancelAdapt;
import me.jessyan.autosize.internal.CustomAdapt;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 发现页
 */
public class DiscoverFragment extends BottomItemFragment implements CancelAdapt {

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public Toolbar setToolbar() {
        return $(R.id.tb_discover);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final WebFragmentImpl delegate = WebFragmentImpl.create("index.html");
        delegate.setTopFragment(this.getParentDelegate());
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container, delegate);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onStart() {
        super.onStart();
        AutoSize.cancelAdapt(getActivity());
//        AutoSizeConfig.getInstance().stop(Latte.getConfiguration(ConfigKeys.ACTIVITY));
    }


}
