package com.flj.latte.fragments.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.flj.latte.fragments.web.event.Event;
import com.flj.latte.fragments.web.event.EventManager;
import com.flj.latte.util.log.LatteLogger;

/**
 * Created by 傅令杰
 */

final class LatteWebInterface {
    private final WebFragment DELEGATE;

    private LatteWebInterface(WebFragment delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebFragment delegate) {
        return new LatteWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        LatteLogger.d("WEB_EVENT",params);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
