package com.me.data.cartview.view;

import com.me.data.cartview.base.BaseItem;

import java.util.List;

/**
 * @Change
 */
interface TreeParent {
    /**
     * 是否允许展开
     *
     * @return
     */
    boolean canExpandOrCollapse();

    /**
     * 展开后的回调
     */
    void onExpand();

    /**
     * 折叠后的回调
     */
    void onCollapse();

    /**
     * 获取子集
     *
     * @return
     */
    List<? extends BaseItem> getChilds();

}
