package com.me.fanyin.zbme.views.main.activity.adapter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;
import com.me.fanyin.zbme.widget.GlideIUtils;

import java.util.List;

/**
 * Created by wyc on 2017.06.06.
 */
public class ItemDragAdapter extends BaseItemDraggableAdapter<SimpleTitleTip, BaseViewHolder> {
    private Context context;

    public ItemDragAdapter(List data) {
        super(R.layout.main_type_change_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleTitleTip item) {
        int position=helper.getLayoutPosition();
        if (position % 3 == 0 && helper.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) helper.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
        GlideIUtils.mainCommenGlid(context,item.getExamImg(),(ImageView) helper.getView(R.id.iv_head),R.mipmap.img_default_kaozhong);
        helper.setText(R.id.tv, item.getTip());
    }

    public void setContext(Context context) {
        this.context=context;
    }
}
