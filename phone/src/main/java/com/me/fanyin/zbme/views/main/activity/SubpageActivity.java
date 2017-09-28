package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.main.adapter.BasicViewPagerAdapter;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/3/30.
 */

public class SubpageActivity extends BaseMvpActivity<SubpageView, SubpagePresenter> implements SubpageView {

    @BindView(R.id.subpage_tbl)
    SmartTabLayout subpage_tbl;
    @BindView(R.id.subpage_vp)
    ViewPager subpage_vp;
    private BasicViewPagerAdapter mAdapter;
    private String examId;
    private String forumId;
    private String forumName;
    private int mouldCode;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        getIntentExtra();
        super.onCreate(savedInstnceState);
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        mouldCode = Integer.parseInt(intent.getStringExtra(Constants.MOULD_CODE));
        examId = intent.getStringExtra(Constants.EXAM_ID);
        forumId = intent.getStringExtra(Constants.FORUM_ID);
        forumName = intent.getStringExtra(Constants.FORUM_NAME);
        if (TextUtils.isEmpty(examId) || TextUtils.isEmpty(forumId) || TextUtils.isEmpty(forumName)|| mouldCode == 0) {
            throw new RuntimeException("intent参数传递错误");
        }
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText(forumName);

        mAdapter = new BasicViewPagerAdapter(subpage_vp, getSupportFragmentManager());

        mPresenter.initTabs(mAdapter, subpage_tbl, subpage_vp);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.subpage_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.subpage_activity_content;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(this, message);
    }

    @Override
    public String getExamId() {
        return examId;
    }

    @Override
    public String getForumId() {
        return forumId;
    }

    @Override
    public int getMouldCode() {
        return mouldCode;
    }

    @Override
    public void setTabVisibility(boolean tabVisibility) {
        subpage_tbl.setVisibility(tabVisibility ? View.VISIBLE : View.GONE);
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                mPresenter.initTabs(mAdapter, subpage_tbl, subpage_vp);
            }
        };
    }
}
