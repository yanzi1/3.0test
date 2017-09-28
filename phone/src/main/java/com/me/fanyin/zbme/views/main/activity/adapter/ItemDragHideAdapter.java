package com.me.fanyin.zbme.views.main.activity.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;

import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
public class ItemDragHideAdapter extends BaseItemDraggableAdapter<SimpleTitleTip, BaseViewHolder> {
    public ItemDragHideAdapter(List data) {
        super(R.layout.main_type_change_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SimpleTitleTip item) {
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.iv_head, R.mipmap.ico_kz_cma);
                break;
            case 1:
                helper.setImageResource(R.id.iv_head, R.mipmap.ico_kz_jiijn);
                break;
            case 2:
                helper.setImageResource(R.id.iv_head, R.mipmap.ico_kz_zhukuai);
                break;
        }
        helper.setText(R.id.tv, item.getTip());
    }
}
