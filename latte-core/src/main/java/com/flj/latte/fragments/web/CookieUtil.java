package com.flj.latte.fragments.web;

import android.webkit.CookieManager;

import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.util.storage.LattePreference;

/**
 * Created by 傅令杰
 */

public final class CookieUtil {

    //获取浏览器cookie，并存入本地
    public static void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        /*
          注意，这里的Cookie和API请求的Cookie是不一样的，这个在网页不可见
         */
        final String webApiHost = Latte.getConfiguration(ConfigKeys.NATIVE_API_HOST);
        if (webApiHost != null) {
            if (manager.hasCookies()) {
                final String cookieStr = manager.getCookie(webApiHost);
                if (cookieStr != null && !cookieStr.equals("")) {
                    //将WebApi都Cookie存入本地
                    LattePreference.addCustomAppProfile(ConfigKeys.COOKIE.name(), cookieStr);
                }
            } else {
                LattePreference.removeCustomAppProfile(ConfigKeys.COOKIE.name());
            }
        }
    }
}
