package com.me.fanyin.zbme.views.course.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.adapter.PlayViewpageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class RankFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener{

    Unbinder unbinder;
    @BindView(R.id.rb_listen_rank)
    RadioButton rbListenRank;
    @BindView(R.id.rb_exam_rank)
    RadioButton rbExamRank;
    @BindView(R.id.rank_vp)
    ViewPager rankVp;
    private View view;

    private ArrayList<Fragment> fragments;
    private PlayViewpageAdapter adapter;
    private FragmentManager fragmentManager;
    private ExamRankFragment examRecordFragment;
    private ListenRankFragment listenRecordFragment;

    public RankFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rank_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        fragments=new ArrayList<>();
        examRecordFragment = new ExamRankFragment();
        listenRecordFragment = new ListenRankFragment();

        rbListenRank.setOnClickListener(this);
        rbExamRank.setOnClickListener(this);

        fragments.add(listenRecordFragment);
        fragments.add(examRecordFragment);

        fragmentManager = getChildFragmentManager();
//        adapter = new PlayViewpageAdapter(fragmentManager, fragments);
//        rankVp.setAdapter(adapter);
//        rankVp.setOffscreenPageLimit(2);
//        rankVp.addOnPageChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_listen_rank:
                rankVp.setCurrentItem(0);
                break;
            case R.id.rb_exam_rank:
                rankVp.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rbListenRank.setChecked(true);
        } else if (position == 1) {
            rbExamRank.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
