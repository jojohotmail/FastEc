package com.flj.latte.ec.main;

import android.graphics.Color;
import android.view.View;

import com.flj.latte.fragments.bottom.BaseBottomFragment;
import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.flj.latte.fragments.bottom.BottomTabBean;
import com.flj.latte.fragments.bottom.ItemBuilder;
import com.flj.latte.ec.main.cart.ShopCartFragment;
import com.flj.latte.ec.main.discover.DiscoverFragment;
import com.flj.latte.ec.main.index.IndexFragment;
import com.flj.latte.ec.main.personal.PersonalFragment;
import com.flj.latte.ec.main.sort.SortFragment;
import com.flj.latte.fragments.bottom.OnGoToIndexFragment;

import java.util.LinkedHashMap;


/**
 * 电商容器页
 */
public class EcBottomFragment extends BaseBottomFragment implements OnGoToIndexFragment {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexFragment());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortFragment());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverFragment());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartFragment(this));
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalFragment());
        return builder.addItems(items).build();

    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }

    @Override
    public View setToolbar() {
        return null;
    }


    @Override
    public void GoToIndexFragment() {
        goToIndexFragment();
    }
}
