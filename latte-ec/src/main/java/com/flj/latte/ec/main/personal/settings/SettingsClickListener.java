package com.flj.latte.ec.main.personal.settings;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.net.RestClient;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.refresh.RefreshHandler;
import com.flj.latte.util.up_apk.UpdateApkManager;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by 傅令杰
 */

public class SettingsClickListener extends SimpleClickListener {

    private final LatteFragment DELEGATE;

    public SettingsClickListener(LatteFragment delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //这是消息推送的开关
                break;
            case 2:
                //测试本地数据
                break;
            case 3:
                AutoSizeConfig.getInstance().stop(Latte.getConfiguration(ConfigKeys.ACTIVITY));
                //更新apk
                UpdateApkManager.updateApk(DELEGATE.getContext());
                break;
            case 4:
                DELEGATE.getSupportDelegate().start(bean.getDelegate());


                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private UIData crateUIData(String title, String url, String content) {
        UIData uiData = UIData.create();
        uiData.setTitle(title);
        uiData.setDownloadUrl(url);
        uiData.setContent(content);
        return uiData;
    }
}
