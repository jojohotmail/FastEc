package com.flj.latte.util.up_apk.sample;

import android.app.Application;


/**
 * Created by allenliu on 2018/1/3.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
    }
}
