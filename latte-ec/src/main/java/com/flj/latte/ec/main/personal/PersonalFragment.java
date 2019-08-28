package com.flj.latte.ec.main.personal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.diabin.latte.ec.R;
import com.flj.latte.ec.main.personal.list.PersonalListAdapter;
import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.flj.latte.ec.main.personal.address.AddressFragment;
import com.flj.latte.ec.main.personal.list.ListAdapter;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.personal.list.ListItemType;
import com.flj.latte.ec.main.personal.order.OrderListFragment;
import com.flj.latte.ec.main.personal.profile.UserProfileFragment;
import com.flj.latte.ec.main.personal.settings.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 个人页
 */
public class PersonalFragment extends BottomItemFragment {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public View setToolbar() {
        return $(R.id.rl_head);
    }

    private void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    private void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileFragment());
    }

    private void startOrderListByType() {
        final OrderListFragment delegate = new OrderListFragment();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final RecyclerView rvSettings = $(R.id.rv_personal_setting);
        $(R.id.tv_all_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AutoSizeConfig.getInstance().stop(getActivity());
//                onClickAllOrder();
                AutoSize.cancelAdapt(getActivity());
                onClickAllOrder();
//                AutoSize.autoConvertDensityOfGlobal(getActivity());
            }
        });
        $(R.id.img_user_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AutoSizeConfig.getInstance().stop(getActivity());
//                onClickAvatar();
                AutoSize.cancelAdapt(getActivity());
                onClickAvatar();
//                AutoSize.autoConvertDensityOfGlobal(getActivity());
            }
        });

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressFragment())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsFragment())
                .setText("系统设置")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final PersonalListAdapter adapter = new PersonalListAdapter(data);
        rvSettings.setAdapter(adapter);
        rvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }


}
