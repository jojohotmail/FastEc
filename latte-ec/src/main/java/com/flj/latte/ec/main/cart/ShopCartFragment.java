package com.flj.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.ViewStubCompat;

import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.ec.R;
import com.flj.latte.fragments.bottom.BottomItemFragment;
import com.flj.latte.ec.main.EcBottomFragment;
import com.flj.latte.ec.pay.FastPay;
import com.flj.latte.ec.pay.IAlPayResultListener;
import com.flj.latte.fragments.bottom.OnGoToIndexFragment;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import me.jessyan.autosize.AutoSize;

/**
 * 购物车
 */
public class ShopCartFragment extends BottomItemFragment implements View.OnClickListener, ISuccess, ICartItemListener, IAlPayResultListener {


    private ShopCartAdapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;

    private RecyclerView mRecyclerView = null;
    private IconTextView mIconSelectAll = null;
    private ViewStubCompat mStubNoItem = null;
    private AppCompatTextView mTvTotalPrice = null;
    private AppCompatTextView tvToBuy = null;

    public ShopCartFragment(OnGoToIndexFragment onGoToIndexFragment) {
        this.onGoToIndexFragment = onGoToIndexFragment;
    }

    private OnGoToIndexFragment onGoToIndexFragment;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public Toolbar setToolbar() {
        return $(R.id.tb_shop_cart);
    }

    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        final List<MultipleItemEntity> data = mAdapter.getData();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
//            mAdapter.setIsSelectedAll(true);
            for (MultipleItemEntity entity : data) {
                entity.setField(ShopCartItemFields.IS_SELECTED, true);
            }
            //更新RecyclerView的显示状态
            //由于全选进会闪一下,而且notifyDataSetChanged()全部更新会消耗大量内存，所以没用下面这一行代码
//            mShopCartAdapter.notifyDataSetChanged();
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            for (MultipleItemEntity entity : data) {
                entity.setField(ShopCartItemFields.IS_SELECTED, false);
            }
//            mAdapter.setIsSelectedAll(false);
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        if (data == null || data.size() == 0) {
            return;
        }
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        double itemCount = 0;
        //以数据驱动UI，就不用写过多的东西
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                final double price = entity.getField(ShopCartItemFields.PRICE);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                itemCount = count * price;
                double totalPrice = mAdapter.getTotalPrice();
                totalPrice = totalPrice - itemCount;
                mAdapter.setTotalPrice(totalPrice);
                deleteEntities.add(entity);
            }
        }
        final int size = deleteEntities.size() - 1;
        for (int i = size; i >= 0; i--) {
            final int removePosition = deleteEntities.get(i).getField(ShopCartItemFields.POSITION);
            final int totleCount = mAdapter.getItemCount();

            if (removePosition < totleCount - 1) {
                final int count = totleCount - (removePosition + 1);
                for (int j = count; j >= 0; j--) {
                    final int changedPosition = removePosition + j;
                    final int oldPosition = mAdapter.getData().get(changedPosition).getField(ShopCartItemFields.POSITION);
                    mAdapter.getData().get(changedPosition).setField(ShopCartItemFields.POSITION, oldPosition - 1);
                }
            }
            mAdapter.remove(removePosition);
            //更新数据
            mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());


        }

        //更改合计的金额数
        mTvTotalPrice.setText(String.valueOf(mAdapter.getTotalPrice()));
        checkItemCount();
    }

    void onClickClear() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        if (data == null || data.size() == 0) {
            return;
        }
        mAdapter.setTotalPrice(0.0);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        //更改合计的金额数
        mTvTotalPrice.setText(String.valueOf(0.0));
        checkItemCount();
    }

    //创建订单，注意，和支付是没有关系的
    private void createOrder() {
//        final String orderUrl = "你的生成订单的API";
        final String orderUrl = "peyment";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userId", "264392");
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("type", 1);
        orderParams.put("ordertype", 0);
        orderParams.put("isanonymous", true);
        orderParams.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        LatteLogger.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartFragment.this)
                                .setPayResultListener(ShopCartFragment.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();

    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            if (mStubNoItem != null && mStubNoItem.getVisibility() == View.GONE) {
                if (tvToBuy==null) {
                    final View stubView = mStubNoItem.inflate();
                    tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
                    tvToBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AutoSize.cancelAdapt(getActivity());
                            Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                            onGoToIndexFragment.GoToIndexFragment();

                        }
                    });
                }
                mStubNoItem.setVisibility(View.VISIBLE);
            } else {
                tvToBuy.setVisibility(View.VISIBLE);
            }

            mRecyclerView.setVisibility(View.GONE);
        } else {
            if (mStubNoItem != null && mStubNoItem.getVisibility() == View.VISIBLE) {
                mStubNoItem.setVisibility(View.GONE);
            }
            mRecyclerView.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_shop_cart);
        mIconSelectAll = $(R.id.icon_shop_cart_select_all);
        mStubNoItem = $(R.id.stub_no_item);
        mTvTotalPrice = $(R.id.tv_shop_cart_total_price);
        mIconSelectAll.setTag(0);
        $(R.id.icon_shop_cart_select_all).setOnClickListener(this);
        $(R.id.tv_top_shop_cart_remove_selected).setOnClickListener(this);
        $(R.id.tv_top_shop_cart_clear).setOnClickListener(this);
        $(R.id.tv_shop_cart_pay).setOnClickListener(this);
        setColor($(R.id.tv_shop_cart_pay));
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (mStubNoItem != null ) {
                mStubNoItem.setVisibility(View.GONE);
            }
            RestClient.builder()
                    .url("shop_cart.php")
                    .loader(getContext())
                    .success(this)
                    .build()
                    .get();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSuccess(String response) {
//        AutoSize.autoConvertDensityOfGlobal(getActivity());
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.icon_shop_cart_select_all) {
            onClickSelectAll();

        } else if (i == R.id.tv_top_shop_cart_remove_selected) {
            onClickRemoveSelectedItem();

        } else if (i == R.id.tv_top_shop_cart_clear) {
            onClickClear();

        } else if (i == R.id.tv_shop_cart_pay) {
            createOrder();

        }
    }
}
