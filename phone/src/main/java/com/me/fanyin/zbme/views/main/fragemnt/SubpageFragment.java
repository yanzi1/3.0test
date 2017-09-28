package com.me.fanyin.zbme.views.main.fragemnt;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.main.activity.divider.SpacesItemDecoration;
import com.me.fanyin.zbme.widget.loadmoreview.AppViewLoadMore;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 首页二级列表页面的Fragment
 *
 * Created by jjr on 2017/3/30.
 */

public class SubpageFragment extends BaseMvpFragment<SubpageFragmentView, SubpageFragmentPresenter> implements SwipeRefreshLayout.OnRefreshListener, SubpageFragmentView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.subpage_rcv)
    RecyclerView subpage_rcv;
    @BindView(R.id.subpage_srl)
    SwipeRefreshLayout subpage_srl;
    private BaseQuickAdapter mAdapter;
    private Bundle mArguments;
    private HashMap<String, String> mParams = new HashMap<>();
    private int mouldCode;

    @Override
    protected int getLayoutRes() {
        return R.layout.subpage_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArguments = getArguments();
        mParams.put(Constants.EXAM_ID, mArguments.getString(Constants.EXAM_ID));
        mParams.put(Constants.FORUM_ID, mArguments.getString(Constants.FORUM_ID));
        mParams.put(Constants.TAB_ID, mArguments.getString(Constants.TAB_ID));
        mParams.put(Constants.LINK, mArguments.getString(Constants.LINK));

        mouldCode = mArguments.getInt(Constants.MOULD_CODE);
    }

    @Override
    public void initView() {
        subpage_srl.setOnRefreshListener(this);
        switch (mouldCode) {
            case 3:
            case 4://考试动态
            case 6://东奥名师
            case 8:
                subpage_rcv.setLayoutManager(new LinearLayoutManager(mActivity));
                break;
            case 7://免费试听
            case 9://热门图书
                subpage_rcv.setLayoutManager(new GridLayoutManager(mActivity, 2));
                subpage_rcv.addItemDecoration(new SpacesItemDecoration(DensityUtils.dip2px(mActivity, 15), DensityUtils.dip2px(mActivity, 10), Color.WHITE));
                break;
        }
        mAdapter = mPresenter.getAdatper(subpage_rcv);
        mAdapter.setLoadMoreView(new AppViewLoadMore());
        mAdapter.setOnLoadMoreListener(this, subpage_rcv);
        mAdapter.enableLoadMoreEndClick(true);
    }

    @Override
    public void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public int getMouldCode() {
        return mouldCode;
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                initData();
            }
        };
    }

    @Override
    public void onRefreshComplete() {
        subpage_srl.setRefreshing(false);
    }

    @Override
    public void onLoadMoreComplete(int status) {
        switch (status) {
            case LoadMoreView.STATUS_DEFAULT:
            case LoadMoreView.STATUS_LOADING:
                mAdapter.loadMoreComplete();
                break;
            case LoadMoreView.STATUS_END:
                mAdapter.loadMoreEnd();
                mAdapter.enableLoadMoreEndClick(false);
                break;
            case LoadMoreView.STATUS_FAIL:
                mAdapter.loadMoreFail();
                break;
        }
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(mActivity, message);
    }

    @Override
    public Activity context() {
        return mActivity;
    }

    @Override
    public void onRefresh() {
        if (mAdapter.isLoading()) {
            subpage_srl.setRefreshing(false);
            return;
        }
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        if (subpage_srl.isRefreshing()) {
            mAdapter.loadMoreEnd(true);
            return;
        }
        mPresenter.onLoadMore();
    }
}
