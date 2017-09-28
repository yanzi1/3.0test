package com.me.fanyin.zbme.widget.dropdown;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.utils.DensityUtils;
import com.me.data.remote.utils.ImageUrlUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.GlideIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 横向商品列表
 * Created by mayunfei on 17-4-27.
 */

public class GoodsHorizontalListLayout extends LinearLayout {

    private OnClickListener onAllGoodsClickListener;

    public void setOnAllGoodsClickListener(OnClickListener onAllGoodsClickListener) {
        this.onAllGoodsClickListener = onAllGoodsClickListener;
    }

    @BindView(R.id.horizontal_recycler)
    RecyclerView horizontalRecycler;
    @BindView(R.id.horizontal_tv)
    TextView horizontalTv;
    private List<GoodsHorizontalItem> data;
    private MyAdapter adapter;

    //点击全部商品
    @OnClick(R.id.horizontal_tv)
    public void onViewClicked() {
        if (onAllGoodsClickListener!=null){
            onAllGoodsClickListener.onClick(horizontalTv);
        }
    }

    public interface GoodsHorizontalItem {
        String getGoodsImageUrl();
    }

    public GoodsHorizontalListLayout(@NonNull Context context) {
        this(context, null);
    }

    public GoodsHorizontalListLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsHorizontalListLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.mall_goods_horizontal_list_layout, this, true);
        ButterKnife.bind(this, this);
        data = new ArrayList<>();
        horizontalRecycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
        adapter = new MyAdapter(data);
        horizontalRecycler.setAdapter(adapter);
        horizontalRecycler.addItemDecoration(new GoodsHorizontalListDecoration());
        LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
        mLinearSnapHelper.attachToRecyclerView(horizontalRecycler);
    }

    public void setGoodsSize(int goodsSize){
        horizontalTv.setText("共" + goodsSize + "件商品");
    }

    public void setData(List<GoodsHorizontalItem> data) {
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onAllGoodsClickListener!=null){
                    onAllGoodsClickListener.onClick(view);
                }
            }
        });
    }

    static class MyAdapter extends BaseQuickAdapter<GoodsHorizontalItem, BaseViewHolder> {

        public MyAdapter(List<GoodsHorizontalItem> data) {
            super(R.layout.mall_goods_horizontal_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsHorizontalItem item) {
            ImageView imageView = helper.getView(R.id.imageview);
//            Glide.with(imageView.getContext()).load("http://www.dongao.com/upload/resources/image/2016/12/30/45065_270x135c.jpg").into(imageView);
            GlideIUtils.mallImageLoad(imageView.getContext(), ImageUrlUtils.checkUrl(item.getGoodsImageUrl()), imageView);
        }
    }

    static class GoodsHorizontalListDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int itemPosition =
                    ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            int totalItemCount = parent.getAdapter().getItemCount();

            if (itemPosition == 0) {
                outRect.set(0, 0, DensityUtils.dip2px(view.getContext(), 10), 0);
            } else if (itemPosition == totalItemCount - 1) {
                outRect.set(0, 0, DensityUtils.dip2px(view.getContext(), 120), 0);
            } else {
                outRect.set(0, 0, DensityUtils.dip2px(view.getContext(), 10), 0);
            }
        }
    }


}
