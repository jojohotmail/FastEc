package com.latte.fastec.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.fragments.web.event.Event;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.util.log.LatteLogger;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * Created by 傅令杰
 */

public class ShareEvent extends Event {

    @Override
    public String execute(String params) {


        LatteLogger.json("ShareEvent", params);

//        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
//        final String title = object.getString("title");
//        final String url = object.getString("url");
//        final String imageUrl = object.getString("imageUrl");
//        final String text = object.getString("text");
//
//        final OnekeyShare oks = new OnekeyShare();
//        oks.disableSSOWhenAuthorize();
//        oks.setTitle(title);
//        oks.setText(text);
//        oks.setImageUrl(imageUrl);
//        oks.setUrl(url);
//        oks.show(getContext());

        showShare();
        return null;
    }
    private void showShare(){
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.share(sp);
//        Platform qzone = ShareSDK.getPlatform (QQ.NAME);
//// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
//        qzone.setPlatformActionListener (new PlatformActionListener() {
//            public void onError(Platform arg0, int arg1, Throwable arg2) {
//                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
//            }
//            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
//                //分享成功的回调
//            }
//            public void onCancel(Platform arg0, int arg1) {
//                //取消分享的回调
//            }
//        });
//// 执行图文分享
//        qzone.share(sp);
    }
}
