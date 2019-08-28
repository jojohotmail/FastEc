package com.flj.latte.fragments.web;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.flj.latte.fragments.IPageLoadListener;
import com.flj.latte.fragments.web.chromeclient.WebChromeClientImpl;
import com.flj.latte.fragments.web.client.WebViewClientImpl;
import com.flj.latte.fragments.web.route.RouteKeys;
import com.flj.latte.fragments.web.route.Router;

/**
 * Created by 傅令杰
 */

public class WebFragmentImpl extends WebFragment implements IUrlHandler {
    private IPageLoadListener mIPageLoadListener = null;

    public static WebFragmentImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebFragmentImpl delegate = new WebFragmentImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public IUrlHandler setUrlHandler() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    @Override
    public void handleUrl(WebFragment fragment) {
        if (getUrl() != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public View setToolbar() {
        return null;
    }
}
