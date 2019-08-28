package com.flj.latte.fragments.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.fragments.web.WebFragment;

/**
 * Created by 傅令杰
 */

public abstract class Event implements IEvent {
    private Context mContent = null;
    private String mAction = null;
    private WebFragment mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Context getContext() {
        return mContent;
    }

    public WebView getWebView(){
        return mDelegate.getWebView();
    }

    public void setContext(Context mContent) {
        this.mContent = mContent;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public LatteFragment getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebFragment mDelegate) {
        this.mDelegate = mDelegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
