package com.latte.fastec.web_test;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.flj.latte.fragments.IPageLoadListener;
import com.flj.latte.fragments.web.IUrlHandler;
import com.flj.latte.fragments.web.IWebViewInitializer;
import com.flj.latte.fragments.web.WebFragment;
import com.flj.latte.fragments.web.WebViewInitializer;
import com.flj.latte.fragments.web.chromeclient.WebChromeClientImpl;
import com.flj.latte.fragments.web.route.RouteKeys;

/**
 * Author: 傅令杰
 * Date: 2018/7/12
 */
public class WebExampleFragment extends WebFragment
        implements IPageLoadListener {

    //简单工厂，让参数更加透明
    public static WebExampleFragment create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebExampleFragment fragment =
                new WebExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public IUrlHandler setUrlHandler() {
        return new UrlHandlerExample();
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientExample client =
                new WebViewClientExample(this);
        client.setPageLoadListener(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadEnd() {

    }

    @Override
    public View setToolbar() {
        return null;
    }
}
