package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.GlideIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by wyc on 2017/03/23.
 *  东奥名师
 */
public class MainTypeDetailAdapter4 extends BaseQuickAdapter<MainDetailItemBean, BaseViewHolder> {
    private Context context;
    private List<MainDetailItemBean> data = new ArrayList<>();
    private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
    private int mainPosition;

    public MainTypeDetailAdapter4() {
        super(R.layout.maintype_recycle_type3_item3);
    }

    public MainTypeDetailAdapter4(Context context, List<MainDetailItemBean> data, MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
        super(R.layout.maintype_recycle_type3_item3, data);
        this.context = context;
        this.data = data;
        this.mainTypeItemClick = mainTypeItemClick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MainDetailItemBean item) {
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainTypeItemClick != null){
                    mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE4,0,helper.getPosition());
                } 
            }
        });
        GlideIUtils.mainCommenGlid(context,item.getImage(),
                (ImageView) helper.getView(R.id.maintype_type3_item3_iv),
                R.mipmap.img_default_teacher);
//        Glide.with(context).load(item.getImage()).
//                placeholder(R.mipmap.img_default_course_book).
//                error(R.mipmap.img_default_course_book).
//                diskCacheStrategy(DiskCacheStrategy.ALL).
//                fitCenter().
//                into((ImageView) helper.getView(R.id.maintype_type3_item3_iv));
        helper.setText(R.id.maintype_type3_item3_teachername, item.getTitle());
        helper.setText(R.id.maintype_type3_item3_pro, item.getDes());
        if (helper.getPosition()==data.size()-1){
            helper.setVisible(R.id.app_line,false);
        }else{
            helper.setVisible(R.id.app_line,true);
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

    public void setMainPosition(int position) {
        this.mainPosition=position;
    }
}
