package com.me.data.cartview.adpater;

import android.annotation.TargetApi;
import android.os.Build;

import com.me.data.cartview.view.TreeItem;
import com.me.data.cartview.view.TreeItemGroup;

import java.util.List;

/**
 * 树级结构recycleradapter.
 * item之间有子父级关系,
 */

public class TreeRecyclerAdapter<T extends TreeItem> extends BaseRecyclerAdapter<T> {

    private TreeRecyclerViewType type;
    /**
     * 最初的数据.没有经过增删操作.
     */
    private List<T> initialDatas;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onBindViewHolderClick(final ViewHolder holder) {
       /* if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    if (getCheckItem().checkPosition(layoutPosition)) {
                        int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
                        //展开,折叠和item点击不应该同时响应事件.
                        if (type != TreeRecyclerViewType.SHOW_ALL) {
                            //展开,折叠
                            expandOrCollapse(itemPosition);
                        } else {
                            //点击事件
                            T item = getDatas().get(itemPosition);
                            TreeItemGroup itemParentItem = item.getParentItem();
                            //判断上一级是否需要拦截这次事件，只处理当前item的上级，不关心上上级如何处理.
                            if (itemParentItem != null && itemParentItem.onInterceptClick(item)) {
                                return;
                            }
                            item.onClick();
                        }
                    }
                }
            });
        }*/
    }

    /**
     * 获得初始的data
     *
     * @return
     */
    public List<T> getInitialDatas() {
        if (initialDatas == null) {
            initialDatas = getDatas();
        }
        return initialDatas;
    }

    @Override
    public void setDatas(List<T> datas) {
        initialDatas = datas;
        getDatas().clear(); //设置数据前先清空数据
        if (type == TreeRecyclerViewType.SHOW_ALL) {
            for (int i = 0; i < datas.size(); i++) {
                T t = datas.get(i);
                getDatas().add(t);
                if (t instanceof TreeItemGroup) {
                    List childs = ((TreeItemGroup) t).getAllChilds();
                    if (childs != null) {
                        getDatas().addAll(childs);
                    }
                }
            }
        } else {
            super.setDatas(datas);
        }
    }

    /**
     * 相应RecyclerView的点击事件 展开或关闭某节点
     *
     * @param position 触发的条目
     */
    private void expandOrCollapse(int position) {
        T baseItem = getDatas().get(position);
        if (baseItem instanceof TreeItemGroup && ((TreeItemGroup) baseItem).isCanChangeExpand()) {
            TreeItemGroup treeParentItem = (TreeItemGroup) baseItem;
            boolean expand = treeParentItem.isExpand();
            List<T> allChilds = treeParentItem.getAllChilds();
            if (expand) {
                getDatas().removeAll(allChilds);
                treeParentItem.onCollapse();
                treeParentItem.setExpand(false);
            } else {
                getDatas().addAll(position + 1, allChilds);
                treeParentItem.onExpand();
                treeParentItem.setExpand(true);
            }
            getItemManager().notifyDataSetChanged();
        }
    }

    /**
     * 需要设置在setdata之前,否则type不会生效
     *
     * @param type
     */
    public void setType(TreeRecyclerViewType type) {
        this.type = type;
    }

}
