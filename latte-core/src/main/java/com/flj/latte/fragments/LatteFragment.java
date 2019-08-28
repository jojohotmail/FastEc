package com.flj.latte.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 扩展页
 */
public abstract class LatteFragment extends PermissionCheckerFragment {

    @SuppressWarnings("unchecked")
    public <T extends LatteFragment> T getParentDelegate() {
        return (T) getParentFragment();
    }

    public abstract View setToolbar();

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setToolBarColor();
    }

    private void setToolBarColor(){
        if (setToolbar()!=null){
            setToolbar().setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
    }

    public void setColor(View view){
        if (view!=null){
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
    }
}
