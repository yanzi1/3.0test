package com.me.fanyin.zbme.views.exam.activity.myfault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragmentActivity;
import com.me.fanyin.zbme.views.exam.activity.myfault.adapter.MyFaultAdapter;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyc on 2016/5/10.
 */
public class MyFaultActivity extends BaseFragmentActivity implements ExpandableListView.OnChildClickListener,MyFaultView {
    @BindView(R.id.top_title_left)
    ImageView top_title_left;
    @BindView(R.id.top_title_right)
    ImageView top_title_right;
    @BindView(R.id.top_title_text)
    TextView top_title_text;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.pager)
    ViewPager pager;
    private MyFaultPercenter myDefaultPercenter;
    private MyFaultAdapter myFaultAdapter;

    @OnClick(R.id.top_title_left) void onBack(){
        onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_myfault_activity);
        ButterKnife.bind(this);
        myDefaultPercenter=new MyFaultPercenter();
        myDefaultPercenter.attachView(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        top_title_text.setText(getResources().getString(R.string.exam_falt));
        initTab();
        top_title_left.setVisibility(View.VISIBLE);
    }

    private void initTab() {

        myFaultAdapter = new MyFaultAdapter(getSupportFragmentManager());
        myFaultAdapter.setFaultClasss(myDefaultPercenter.faultClassList);
        pager.setAdapter(myFaultAdapter);
        pager.setOffscreenPageLimit(4);
        //   pager.setCurrentItem(0);
        viewpagertab.setViewPager(pager);
        viewpagertab.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                myDefaultPercenter.getOnPageChangeListener(position);
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                myDefaultPercenter.getOnPageChangeListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void initData() {
        myDefaultPercenter.getData();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

//    @Override
//    public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
//        myDefaultPercenter.getData();
//    }

    @Override
    public void showLoading() {
        
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return MyFaultActivity.this;
    }

    @Override
    public void initAdapter() {
        myFaultAdapter.setFaultClasss(myDefaultPercenter.faultClassList);
        viewpagertab.setViewPager(pager);
        myFaultAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);
    }

    @Override
    public Intent getTheIntent() {
        return getIntent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
