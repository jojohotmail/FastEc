package com.flj.latte.ec.main.personal.order;

import android.annotation.SuppressLint;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.diabin.latte.ec.R;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by 傅令杰
 */

public class OrderListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                final AppCompatTextView title = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView price = holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView time = holder.getView(R.id.tv_order_list_time);

                final String titleVal = entity.getField(MultipleFields.TITLE);
                final String timeVal = entity.getField(OrderItemFields.TIME);
                final double priceVal = entity.getField(OrderItemFields.PRICE);
                final String imageUrl = entity.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleVal);
                price.setText("价格：" + String.valueOf(priceVal));
                time.setText("时间：" + timeVal);
                break;
            default:
                break;
        }
    }
}
