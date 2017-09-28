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
 *  热点资讯
 */
public class MainTypeDetailAdapter2 extends BaseQuickAdapter<MainDetailItemBean, BaseViewHolder> {
    private Context context;
    private List<MainDetailItemBean> data = new ArrayList<>();
    private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
    private int mainPosition;

    public MainTypeDetailAdapter2() {
        super(R.layout.maintype_recycle_type3_item1);
    }

    public MainTypeDetailAdapter2(Context context, List<MainDetailItemBean> data, MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
        super(R.layout.maintype_recycle_type3_item1, data);
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
                    mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE2, 0, helper.getPosition());
                }
            }
        });
        GlideIUtils.mainCommenGlid(context,item.getImage(),(ImageView) helper.getView(R.id.maintype_type3_item1_iv),
                R.mipmap.img_default_course_book);
//        Glide.with(context).load(item.getImage()).
//                placeholder(R.mipmap.img_default_course_book).
//                error(R.mipmap.img_default_course_book)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
//                .fitCenter()
//                .into((ImageView) helper.getView(R.id.maintype_type3_item1_iv));
        helper.setText(R.id.maintype_type3_item1_title, item.getTitle());
        helper.setText(R.id.maintype_type3_item1_type, item.getTab());
        String date;
        if (item.getPublishDate().contains(" ")){
            date =item.getPublishDate().substring(0,item.getPublishDate().indexOf(" "));
        }else{
            date=item.getPublishDate();
        }
        helper.setText(R.id.maintype_type3_item1_time, date);
        if (helper.getPosition()==data.size()-1){
            helper.setVisible(R.id.app_line,false);
        }else{
            helper.setVisible(R.id.app_line,true);
        }
    }

    public void setContext(Context context){
        this.context=context;
    }

    //get & set
    public List<MainDetailItemBean> getResults() {
        return data;
    }

    public void setResults(List<MainDetailItemBean> data) {
        this.data = data;
    }

    public void setClickListener(MainTypeAdapter.MainTypeItemClick mainTypeItemClick){
        this.mainTypeItemClick=mainTypeItemClick;
    }

    public void setMainPosition(int position) {
        this.mainPosition=position;
    }
}
