package com.flj.latte.ec.main.personal.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.diabin.latte.ec.R;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.ec.main.personal.list.ListAdapter;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.personal.list.ListItemType;
import com.flj.latte.ec.main.personal.settings.NameFragment;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 个人信息修改页
 */
public class UserProfileFragment extends LatteFragment implements CancelAdapt {

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public View setToolbar() {
        return $(R.id.tb_user_profile);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final RecyclerView recyclerView = $(R.id.rv_user_profile);

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setDelegate(new NameFragment())
                .setValue("未设置姓名")
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }


}
