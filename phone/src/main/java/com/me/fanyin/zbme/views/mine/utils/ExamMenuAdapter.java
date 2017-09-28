package com.me.fanyin.zbme.views.mine.utils;

import com.me.data.model.mine.ExamMenu;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class ExamMenuAdapter extends ShopMenuAdapter<ExamMenu> {

    public ExamMenuAdapter(List<ExamMenu> data) {
        super(data);
    }

    @Override
    protected String showItemText(ExamMenu bean) {
        return bean.getExamName();
    }
}
