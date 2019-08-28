package com.flj.latte.ec.main.sort;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.diabin.latte.ec.R;
import com.flj.latte.ec.main.sort.content.ContentFragment;
import com.flj.latte.ec.main.sort.list.VerticalListFragment;

/**
 * 分类页
 */
public class SortFragment extends BottomItemFragment {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public Toolbar setToolbar() {
        return  $(R.id.tb_sort);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListFragment listDelegate = new VerticalListFragment();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
//        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentFragment.newInstance(1));
    }

}
