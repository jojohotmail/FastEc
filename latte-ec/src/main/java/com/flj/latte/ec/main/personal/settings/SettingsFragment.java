package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.global.PushManager;
import com.flj.latte.global.TestLocalJsonManager;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.ec.main.personal.address.AddressFragment;
import com.flj.latte.ec.main.personal.list.ListAdapter;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.personal.list.ListItemType;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by 傅令杰
 */

public class SettingsFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public View setToolbar() {
        return $(R.id.tb_settings);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final RecyclerView recyclerView = $(R.id.rv_settings);

        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setSelectTag(PushManager.isPushState())
                .setDelegate(new AddressFragment())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                            PushManager.setPushState(true);
                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_SHORT).show();
                        } else {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                            PushManager.setPushState(false);
                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setText("消息推送")
                .build();
        final ListBean testLocalJson = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(2)
                .setSelectTag(TestLocalJsonManager.isLocalJsonState())
                .setDelegate(new AddressFragment())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            TestLocalJsonManager.setLocalJsonState(true);
                            Toast.makeText(getContext(), "打开测试本地数据", Toast.LENGTH_SHORT).show();
                        } else {
                            TestLocalJsonManager.setLocalJsonState(false);
                            Toast.makeText(getContext(), "关闭测试本地数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setText("测试本地数据")
                .build();
        final ListBean upApk = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("更新应用")
                .build();
        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setDelegate(new AboutFragment())
                .setText("关于")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(testLocalJson);
        data.add(upApk);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }

}
