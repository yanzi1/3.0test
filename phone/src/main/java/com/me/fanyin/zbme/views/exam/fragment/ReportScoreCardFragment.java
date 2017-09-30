package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyc on 2016/5/6.
 */
public class ReportScoreCardFragment extends BaseFragment implements ReportScoreCardFragmentView{
    @BindView(R.id.pager_layout)
    LinearLayout pager_layout;
    
    private ReportScoreCardFragmentPercenter reportScoreCardFragmentPercenter;

    public static ReportScoreCardFragment newInstance(int tag) {
        ReportScoreCardFragment f = new ReportScoreCardFragment();
        Bundle b = new Bundle();
        b.putInt(Constants.ARG_POSITION, tag);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exam_test_item_scorecard,
                container, false);
        ButterKnife.bind(this, rootView);
        reportScoreCardFragmentPercenter=new ReportScoreCardFragmentPercenter();
        reportScoreCardFragmentPercenter.attachView(this);
        ViewGroup p = (ViewGroup) rootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }
    
    @Override
    public void initView() {
        
    }

    @Override
    public void initData() {
        reportScoreCardFragmentPercenter.getData(); 
    }

    @Override
    public void onClick(View v) {

    }

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

    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pager_layout!=null){
            pager_layout.removeAllViews();
        }
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }

    @Override
    public void setView(View view) {
        pager_layout.addView(view);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public PhoneAppContext getAppContext() {
        return appContext;
    }

    @Override
    public Intent getTheIntent() {
        return getActivity().getIntent();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            if (pager_layout != null) {
                pager_layout.removeAllViews();
            }
            initView();
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
