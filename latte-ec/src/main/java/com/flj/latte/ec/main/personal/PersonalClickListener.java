package com.flj.latte.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * Created by 傅令杰
 */

public class PersonalClickListener extends SimpleClickListener {

    private final LatteFragment DELEGATE;

    public PersonalClickListener(LatteFragment delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                AutoSizeConfig.getInstance().stop(Latte.getConfiguration(ConfigKeys.ACTIVITY));
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 2:
                AutoSizeConfig.getInstance().stop(Latte.getConfiguration(ConfigKeys.ACTIVITY));
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
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
}
