package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.StudentsAppraiseAdapter;
import com.me.fanyin.zbme.widget.loadmoreview.AppViewLoadMore;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/11.
 */

public class StudentsAppraiseActivity extends BaseMvpActivity<StudentsAppraiseView, StudentsAppraisePresenter> implements StudentsAppraiseView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.subpage_rcv)
    RecyclerView subpage_rcv;
    @BindView(R.id.subpage_srl)
    SwipeRefreshLayout subpage_srl;
    @BindView(R.id.appraise_star_rb1)
    RatingBar appraise_star_rb1;
    @BindView(R.id.appraise_score_tv1)
    TextView appraise_score_tv1;
    @BindView(R.id.appraise_star_rb2)
    RatingBar appraise_star_rb2;
    @BindView(R.id.appraise_score_tv2)
    TextView appraise_score_tv2;
    @BindView(R.id.appraise_star_rb3)
    RatingBar appraise_star_rb3;
    @BindView(R.id.appraise_score_tv3)
    TextView appraise_score_tv3;
    @BindView(R.id.appraise_ratings_ll)
    LinearLayout appraise_ratings_ll;
    private String lectureId;
    private StudentsAppraiseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        lectureId = getIntent().getStringExtra(Constants.LECTURE_ID);
        super.onCreate(savedInstnceState);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("学员评价");
        subpage_srl.setOnRefreshListener(this);
        subpage_rcv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = mPresenter.initAdapter(subpage_rcv);
        mAdapter.setLoadMoreView(new AppViewLoadMore());
        mAdapter.setOnLoadMoreListener(this, subpage_rcv);
        mAdapter.disableLoadMoreIfNotFullPage(subpage_rcv);
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_students_appraise_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.subpage_fragment;
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

    @Override
    public String getLectureId() {
        return lectureId;
    }

    @Override
    public void setAppraiseScore(float beautifulScore, float attractScore, float reasonableScore) {
        appraise_star_rb1.setRating(beautifulScore);
        appraise_score_tv1.setText(beautifulScore + "分");
        appraise_star_rb2.setRating(attractScore);
        appraise_score_tv2.setText(attractScore + "分");
        appraise_star_rb3.setRating(reasonableScore);
        appraise_score_tv3.setText(reasonableScore + "分");
        setAppraiseLlVisibility(true);
    }

    private void setAppraiseLlVisibility(boolean isVisibility) {
        appraise_ratings_ll.setVisibility(isVisibility?View.VISIBLE:View.GONE);
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
                break;
            case LoadMoreView.STATUS_FAIL:
                mAdapter.loadMoreFail();
                break;
        }
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                mPresenter.getData();
            }
        };
    }
}
