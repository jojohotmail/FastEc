package com.latte.fastec.web_test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.flj.latte.activities.ProxyActivity;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.global.Latte;

public class WebExampleActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
    }

    @Override
    public LatteFragment setRootDelegate() {
        return WebExampleFragment.create(Config.INDEX_URL);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
