package com.me.data.cartview.base;

/**
 * javabean继承该类,后台返回的json中可以包含viewItemType,通过解析返回的viewItemType确定item样式
 */
public abstract class BaseItemData {

    protected int viewItemType;

    public void setViewItemType(int viewItemType) {
        this.viewItemType = viewItemType;
    }

    public int getViewItemType() {
        return viewItemType;
    }
}
