package com.latte.fastec.web_test;

import android.widget.Toast;

import com.flj.latte.fragments.web.IUrlHandler;
import com.flj.latte.fragments.web.WebFragment;
import com.flj.latte.fragments.web.route.Router;

/**
 * Author: 傅令杰
 * Date: 2018/7/12
 */
public class UrlHandlerExample implements IUrlHandler {

    @Override
    public void handleUrl(WebFragment fragment) {
        //是我们跳转新页面的url
        final String url = fragment.getUrl();
        if (url != null) {
            //用原生的方式代替浏览器的跳转，每次打开新的WebFragment
            Router.getInstance().loadPage(fragment, url);
            if (url.contains("www.baidu.com")) {
                Toast.makeText
                        (fragment.getContext(),
                                "这是百度",
                                Toast.LENGTH_SHORT).show();
            }
        }
    }
}
