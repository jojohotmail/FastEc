package com.flj.latte.util.up_apk;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.diabin.latte.R;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.global.TestLocalJsonManager;
import com.flj.latte.net.interceptors.DebugInterceptor;
import com.flj.latte.util.file.FileUtil;
import com.flj.latte.util.up_apk.sample.v1.BaseDialog;
import com.flj.latte.util.up_apk.sample.v2.V2Activity;
import com.flj.latte.util.version.VersionUtil;

import me.jessyan.autosize.AutoSize;

public class UpdateApkManager {
    private static DownloadBuilder builder;

    public static void updateApk(Context context){

        final String url =
                TestLocalJsonManager.isLocalJsonState() ?
                        Latte.getConfiguration(ConfigKeys.NET_HOST):
                        Latte.getConfiguration(ConfigKeys.LOCAL_HOST);
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
//                .setRequestUrl(url+"update_apk")
                .setRequestUrl("https://www.baidu.com")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String response) {
                        final String json = FileUtil.getRawFile(R.raw.update_apk);
                        final JSONObject object = JSON.parseObject(json).getJSONObject("data");
                        final int lineVersion = object.getInteger("version_i");
                        VersionUtil.check(lineVersion);
                        if (!VersionUtil.check(lineVersion)){
                            Latte.toast("已是最新版本！");
                            return null;
                        }
                        return crateUIData(
                                object.getString("title"),
                                object.getString("android_url"),
                                object.getString("content"));
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
//                        Toast.makeText(, "request failed", Toast.LENGTH_SHORT).show();

                    }
                });
        //自定义界面
        builder.setCustomVersionDialogListener(createCustomDialogTwo());
        builder.executeMission(context);
    }
    private static CustomVersionDialogListener createCustomDialogTwo() {
        return (context, versionBundle) -> {

            BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.cv_custom_dialog_two_layout);

            TextView title = baseDialog.findViewById(R.id.tv_title);
            title.setText(versionBundle.getTitle());

            TextView content = baseDialog.findViewById(R.id.tv_msg);
            content.setText(versionBundle.getContent());
            baseDialog.setCanceledOnTouchOutside(true);
            return baseDialog;
        };
    }

    private static UIData crateUIData(String title,String url,String content) {
        UIData uiData = UIData.create();
        uiData.setTitle(title);
        uiData.setDownloadUrl(url);
        uiData.setContent(content);
        return uiData;
    }
}
