package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.fragemnt.view.FreeCourseImageView;
import com.me.fanyin.zbme.widget.GlideIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by wyc on 2017/03/23.
 *  免费课程(精品试听)
 */
public class MainTypeDetailAdapter5 extends BaseQuickAdapter<MainDetailItemBean, BaseViewHolder> {

    private Context context;
    private List<MainDetailItemBean> data = new ArrayList<>();
    private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
    private int mainPosition;

    public MainTypeDetailAdapter5() {
        super(R.layout.maintype_recycle_type3_item4);
    }

    public MainTypeDetailAdapter5(Context context, List<MainDetailItemBean> data, MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
        super(R.layout.maintype_recycle_type3_item4, data);
        this.context = context;
        this.data = data;
        this.mainTypeItemClick = mainTypeItemClick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MainDetailItemBean item) {
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainTypeItemClick!=null){
                    mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE5,0,helper.getPosition());
                }
            }
        });
        GlideIUtils.mainCommenGlid(context,item.getImage(),
                (FreeCourseImageView) helper.getView(R.id.maintype_type3_item4_iv),
                R.mipmap.img_default_freecourse);
//        Glide.with(context).load(item.getImage()).
//                placeholder(R.mipmap.img_default_freecourse).
//                error(R.mipmap.img_default_freecourse).
//				diskCacheStrategy(DiskCacheStrategy.ALL).
//                fitCenter().
//                into((FreeCourseImageView) helper.getView(R.id.maintype_type3_item4_iv));
        helper.setText(R.id.maintype_type3_item4_coursename, item.getTitle());
        helper.setText(R.id.maintype_type3_item4_zhujiang, item.getAuthor());
        if (StringUtils.isEmpty(item.getTab())){
            helper.setVisible(R.id.maintype_type3_item4_type, false);
        }else{
            helper.setText(R.id.maintype_type3_item4_type, item.getTab());
            helper.setVisible(R.id.maintype_type3_item4_type, true);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //get & set
    public List<MainDetailItemBean> getResults() {
        return data;
    }

    public void setResults(List<MainDetailItemBean> data) {
        this.data = data;
    }

    public void setClickListener(MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
        this.mainTypeItemClick = mainTypeItemClick;
    }

    public void setMainPosition(int mainPosition) {
        this.mainPosition = mainPosition;
    }
}
