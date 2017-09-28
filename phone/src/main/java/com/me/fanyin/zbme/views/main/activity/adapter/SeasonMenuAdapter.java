package com.me.fanyin.zbme.views.main.activity.adapter;

import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class SeasonMenuAdapter extends ShopMenuAdapter<BooksErrataMenuListBean.SubjectListBean.SeasonListBean> {

    public SeasonMenuAdapter(List<BooksErrataMenuListBean.SubjectListBean.SeasonListBean> data) {
        super(data);
    }

    @Override
    protected String showItemText(BooksErrataMenuListBean.SubjectListBean.SeasonListBean bean) {
        return bean.getYear();
    }
}
