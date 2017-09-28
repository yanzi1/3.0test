package com.me.data.cartview.view;


import com.me.data.cartview.base.BaseItem;
import com.me.data.cartview.base.BaseItemData;

import java.util.ArrayList;
import java.util.List;


/**
 * 可以选中子item的TreeItemGroup,点击的item会保存起来.可以通过 getSelectItems()获得选中item
 * 暂时先不采用
 */
public abstract class TreeSelectItemGroup<D extends BaseItemData>
        extends TreeItemGroup<D> {
    /**
     * 选中的子item.只支持下一级,不支持下下级
     */
    private List<BaseItem> selectItems;

    public List<BaseItem> getSelectItems() {
        if (selectItems == null) {
            selectItems = new ArrayList<>();
        }
        return selectItems;
    }

    /**
     * 是否有选中item,
     * @return
     */
    public boolean isHaveCheck() {
        return !getSelectItems().isEmpty();
    }

    @Override
    public boolean onInterceptClick(TreeItem child) {
        if (selectFlag() == SelectFlag.SINGLE_CHOICE) {
            if (getSelectItems().size() != 0) {
                getSelectItems().set(0, child);
            } else {
                getSelectItems().add(child);
            }
        } else {
            int index = getSelectItems().indexOf(child);
            if (index == -1) {//不存在则添加
                getSelectItems().add(child);
            } else {//存在则添加
                getSelectItems().remove(index);
            }
        }
        return super.onInterceptClick(child);
    }

    /**
     * 必须指定选中样式
     * @return
     */
    public abstract SelectFlag selectFlag();

    /**
     * 决定TreeSelectItemGroup的选中样式
     */
    public enum SelectFlag {
        /**
         * 单选
         */
        SINGLE_CHOICE,
        /**
         * 多选
         */
        MULTIPLE_CHOICE
    }
}
