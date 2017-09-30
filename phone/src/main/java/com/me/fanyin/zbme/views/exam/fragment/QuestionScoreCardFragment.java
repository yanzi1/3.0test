package com.me.fanyin.zbme.views.exam.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by wyc on 2016/5/4.
 */
public class QuestionScoreCardFragment extends BaseFragment {

    public static QuestionScoreCardFragment newInstance(int index) {
        QuestionScoreCardFragment f = new QuestionScoreCardFragment();
        Bundle b = new Bundle();
        b.putInt(Constants.ARG_POSITION, index);
        f.setArguments(b);
        return f;
    }
   
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.exam_test_pager_item_yuan,
                container, false);
        ButterKnife.bind(this, rootView);
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

    }

    @Override
    public void onClick(View v) {

    }
}
