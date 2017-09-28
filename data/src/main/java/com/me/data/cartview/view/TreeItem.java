package com.me.data.cartview.view;

import android.support.annotation.Nullable;

import com.me.data.cartview.base.BaseItem;
import com.me.data.cartview.base.BaseItemData;

/**
 * TreeRecyclerAdapter的item
 */
public abstract class TreeItem<D extends BaseItemData> extends BaseItem<D> {
    private TreeItemGroup parentItem;

    public void setParentItem(TreeItemGroup parentItem) {
        this.parentItem = parentItem;
    }

    /**
     * 获取当前item的父级
     *
     * @return
     */
    @Nullable
    public TreeItemGroup getParentItem() {
        return parentItem;
    }

}
