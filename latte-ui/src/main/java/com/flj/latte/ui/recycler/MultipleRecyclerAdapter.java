package com.flj.latte.ui.recycler;

import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.ui.R;
import com.flj.latte.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSize;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * Created by 傅令杰
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE4, R.layout.item_multiple_image_text4);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        addItemType(ItemType.TEXT_IMAGE2, R.layout.item_multiple_image_text2);
        //设置宽度监听
        setSpanSizeLookup(this);
//        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        //屏幕的宽度(px值）
        final int screenWidth = ((Context)Latte.getConfiguration(ConfigKeys.ACTIVITY))
                .getResources().getDisplayMetrics().widthPixels;
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        final boolean flag = entity.getField(MultipleFields.SPAN_SIZE)!=null;
        int width = 0;
        if (flag){
            final int span = (int)entity.getField(MultipleFields.SPAN_SIZE);
            width = span==4 ?screenWidth:screenWidth/2;
        }

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:

                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                if (flag){
                    Glide.with(mContext)
                            .load(imageUrl)
                            .override(width,SIZE_ORIGINAL)
                            .apply(Latte.getRequestOptions())
                            .into((ImageView) holder.getView(R.id.img_single));
                }else{
                    Glide.with(mContext)
                            .load(imageUrl)
                            .apply(Latte.getRequestOptions())
                            .into((ImageView) holder.getView(R.id.img_single));
                }

                break;
            case ItemType.TEXT_IMAGE4:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                if (flag){
                    Glide.with(mContext)
                            .load(imageUrl)
                            .override(width,SIZE_ORIGINAL)
                            .apply(Latte.getRequestOptions())
                            .into((ImageView) holder.getView(R.id.img_multiple));
                }else{
                    Glide.with(mContext)
                            .load(imageUrl)
                            .apply(Latte.getRequestOptions())
                            .into((ImageView) holder.getView(R.id.img_multiple));
                }
                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.TEXT_IMAGE2:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .override(width,SIZE_ORIGINAL)
//                        .centerCrop()
                        .apply(Latte.getRequestOptions())
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.BANNER:
                AutoSize.autoConvertDensityOfGlobal(Latte.getConfiguration(ConfigKeys.ACTIVITY));
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    //设置初始化成功
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
