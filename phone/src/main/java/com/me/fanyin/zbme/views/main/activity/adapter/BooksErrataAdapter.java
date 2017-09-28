package com.me.fanyin.zbme.views.main.activity.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.activity.BooksErrataDetailActivity;

import java.util.List;

/**
 *  Created by jjr on 2017/03/23.
 *  图书勘误列表的adpter
 */
public class BooksErrataAdapter extends BaseQuickAdapter<MainDetailItemBean, BaseViewHolder> {

    public BooksErrataAdapter(List<MainDetailItemBean> data) {
        super(R.layout.main_books_errata_item, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MainDetailItemBean item) {
        helper.setText(R.id.books_errata_title_tv, item.getTitle());
        helper.setText(R.id.books_errata_time_tv, item.getPublishDate().substring(0,item.getPublishDate().indexOf(" ")));
        helper.setText(R.id.books_errata_books_tv, item.getTab().replaceAll(",", "/"));
        helper.getView(R.id.books_errata_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.FORUM_NAME, item);
                gotoActivity(BooksErrataDetailActivity.class, bundle);
            }
        });
    }

    private void gotoActivity(Class<?> clz, Bundle ex) {
        Intent intent = new Intent(getRecyclerView().getContext(), clz);
        if (ex != null) intent.putExtras(ex);
        getRecyclerView().getContext().startActivity(intent);
    }
}
