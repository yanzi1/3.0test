package com.me.fanyin.zbme.views.mine.utils;

import com.me.data.model.mine.ExamMenu;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class SubjectMenuAdapter extends ShopMenuAdapter<ExamMenu.SubjectMenu> {

    public SubjectMenuAdapter(List<ExamMenu.SubjectMenu> data) {
        super(data);
    }

    @Override
    protected String showItemText(ExamMenu.SubjectMenu bean) {
        return bean.getSubjectName();
    }
}