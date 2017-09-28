package com.me.fanyin.zbme.views.main.activity.adapter;

import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class SubjectMenuAdapter extends ShopMenuAdapter<BooksErrataMenuListBean.SubjectListBean> {

    public SubjectMenuAdapter(List<BooksErrataMenuListBean.SubjectListBean> data) {
        super(data);
    }

    @Override
    protected String showItemText(BooksErrataMenuListBean.SubjectListBean bean) {
        return bean.getName();
    }
}