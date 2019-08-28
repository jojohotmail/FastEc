package com.flj.latte.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;

/**
 * Created by 傅令杰 on 2017/3/29
 */

public final class Latte {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void toast(String msg) {
        Toast.makeText(Latte.getConfiguration(ConfigKeys.ACTIVITY), msg, Toast.LENGTH_SHORT).show();
    }

    public static RequestOptions getRequestOptions() {
        return getConfiguration(ConfigKeys.RECYCLER_OPTIONS);
    }
}