package com.me.data.cartview.adpater.wapper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.me.data.cartview.adpater.ViewHolder;
import com.me.data.cartview.adpater.BaseRecyclerAdapter;
import com.me.data.cartview.base.BaseItem;

import java.util.List;

public class EmptyWapper<T extends BaseItem> extends BaseRecyclerAdapter<T> {
    public static final int ITEM_TYPE_EMPTY = Integer.MIN_VALUE;

    private BaseRecyclerAdapter<T> mAdapter;

    private View mEmptyView;

    private int mEmptyLayoutId;


    public EmptyWapper(BaseRecyclerAdapter<T> adapter) {
        mAdapter = adapter;
        mAdapter.setItemManager(getItemManager());
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mAdapter.getItemCount() == 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            if (mEmptyView != null) {
                return ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                return ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) return 1;
        return mAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }


    @Override
    public List<T> getDatas() {
        return mAdapter.getDatas();
    }

    @Override
    public void setDatas(List<T> datas) {
        mAdapter.setDatas(datas);
    }
}
