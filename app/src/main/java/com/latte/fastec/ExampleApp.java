package com.latte.fastec;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.flj.latte.ec.database.DataBaseManager;
import com.flj.latte.ec.icon.FontEcModule;
import com.flj.latte.global.Latte;
import com.flj.latte.global.TestLocalJsonManager;
import com.flj.latte.net.interceptors.DebugInterceptor;
import com.flj.latte.net.rx.AddCookieInterceptor;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.latte.fastec.event.ShareEvent;
import com.latte.fastec.event.TestEvent;
import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by 傅令杰 on 2017/3/29
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withNetHost("http://mock.fulingjie.com/mock-android/api/")
                .withLocalHost("http://mock.fulingjie.com/mock-android/api/")
                .withWeChatAppId("你的微信AppKey")
                .withWeChatAppSecret("你的微信AppSecret")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share", new ShareEvent())
                //添加Cookie同步拦截器
                .withWebHost("https://www.baidu.com/")
                .withInterceptor(new AddCookieInterceptor())
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withInterceptor(new DebugInterceptor("user_profile.php", R.raw.user_profile))
                .withInterceptor(new DebugInterceptor("index.php", R.raw.index_data))
                .withInterceptor(new DebugInterceptor("refresh.php?index=1", R.raw.index_data1))
                .withInterceptor(new DebugInterceptor("refresh.php?index=2", R.raw.index_data2))
                .withInterceptor(new DebugInterceptor("refresh.php?index=3", R.raw.index_data3))
                .withInterceptor(new DebugInterceptor("refresh.php?index=4", R.raw.index_data4))
                .withInterceptor(new DebugInterceptor("refresh.php?index=5", R.raw.index_data5))
                .withInterceptor(new DebugInterceptor("refresh.php?index=6", R.raw.index_data6))
                .withInterceptor(new DebugInterceptor("sort_list.php", R.raw.sort_list_data))
                .withInterceptor(new DebugInterceptor("sort_content_list.php?contentId=1", R.raw.sort_content_data_1))
                .withInterceptor(new DebugInterceptor("sort_content_list.php?contentId=2", R.raw.sort_content_data_2))
                .withInterceptor(new DebugInterceptor("shop_cart.php", R.raw.shop_cart_data))
                .withInterceptor(new DebugInterceptor("shop_cart_count.php", R.raw.shop_cart_count))
                .withInterceptor(new DebugInterceptor("search.php?key=", R.raw.search))
                .withInterceptor(new DebugInterceptor("peyment", R.raw.peyment))
                .withInterceptor(new DebugInterceptor("order_list.php", R.raw.order_list))
                .withInterceptor(new DebugInterceptor("address.php", R.raw.address))
                .withInterceptor(new DebugInterceptor("about.php", R.raw.about))
                .withInterceptor(new DebugInterceptor("add_shop_cart_count.php", R.raw.add_shop_cart))
                .withInterceptor(new DebugInterceptor("goods_detail.php?goods_id=1", R.raw.goods_detail_data_1))
                .withInterceptor(new DebugInterceptor("goods_detail.php?goods_id=2", R.raw.goods_detail_data_2))
                .withInterceptor(new DebugInterceptor("update_apk", R.raw.update_apk))
                .configure();

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        DataBaseManager.getInstance().init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });

        MobSDK.init(this);

        // 主要是添加下面这句代码
        MultiDex.install(this);


    }

}
