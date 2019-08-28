package com.latte.fastec.web_test;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.flj.latte.fragments.IPageLoadListener;
import com.flj.latte.fragments.web.WebFragment;
import com.flj.latte.fragments.web.route.Router;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.ui.loader.LatteLoader;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;

/**
 * Author: 傅令杰
 * Date: 2018/7/12
 */
public class WebViewClientExample extends WebViewClient {

    private final WebFragment mFragment;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientExample(WebFragment delegate) {
        this.mFragment = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(mFragment, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    //获取浏览器cookie
    private void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        /*
          注意，这里的Cookie和API请求的Cookie是不一样的，这个在网页不可见
         */
        final String webHost = Latte.getConfiguration(ConfigKeys.WEB_HOST);
        if (webHost != null) {
            if (manager.hasCookies()) {
                final String cookieStr = manager.getCookie(webHost);
                if (cookieStr != null && !cookieStr.equals("")) {
                    LattePreference.addCustomAppProfile("cookie", cookieStr);
                }
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        final long delayed = Latte.getConfiguration(ConfigKeys.LOADER_DELAYED);
        HANDLER.postDelayed(LatteLoader::stopLoading, delayed);
    }
}
