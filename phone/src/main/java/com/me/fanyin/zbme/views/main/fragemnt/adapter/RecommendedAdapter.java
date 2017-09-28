package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.utils.StringUtils;
import com.me.data.model.main.RecommendedGoodListBean;
import com.me.data.remote.utils.ImageUrlUtils;
import com.me.fanyin.zbme.R;

/**
 *  Created by jjr on 2017/03/23.
 *  热门课程、热门图书、推荐课程
 */
public class RecommendedAdapter extends BaseQuickAdapter<RecommendedGoodListBean.ListBean, BaseViewHolder> {

    private String goodsType;

    public RecommendedAdapter(){
        super(R.layout.maintype_recycle_type3_item5);
    }

    public RecommendedAdapter(String goodsType) {
        super(R.layout.maintype_recycle_type3_item5);
        this.goodsType = goodsType;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RecommendedGoodListBean.ListBean item) {
        Glide.with(mContext).load(ImageUrlUtils.checkUrl(item.getImage())).placeholder(R.mipmap.img_default_book).error(R.mipmap.img_default_book).into((ImageView) helper.getView(R.id.maintype_type3_item5_iv));
        helper.setText(R.id.maintype_type3_item5_coursename, item.getTitle());
        helper.setVisible(R.id.maintype_type3_item5_lecturer_tv, false);
        helper.setText(R.id.maintype_type3_item5_original_price_tv, "¥" + StringUtils.formatPrice(Double.parseDouble(item.getTab())));
        helper.getView(R.id.maintype_type3_item5_cv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
