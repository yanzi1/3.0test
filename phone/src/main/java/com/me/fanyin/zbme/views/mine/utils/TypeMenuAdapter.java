package com.me.fanyin.zbme.views.mine.utils;

import com.me.data.model.mine.TypeMenu;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class TypeMenuAdapter extends ShopMenuAdapter<TypeMenu> {

    public TypeMenuAdapter(List<TypeMenu> data) {
        super(data);
    }

    @Override
    protected String showItemText(TypeMenu bean) {
        return bean.getTypeName();
    }
}
