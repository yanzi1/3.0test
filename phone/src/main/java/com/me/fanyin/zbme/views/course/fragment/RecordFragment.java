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
public class RecordFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener{

    Unbinder unbinder;
    @BindView(R.id.rb_listen_record)
    RadioButton rbListenRecord;
    @BindView(R.id.rb_exam_record)
    RadioButton rbExamRecord;
    @BindView(R.id.record_vp)
    ViewPager recordVp;
    private View view;

    private ArrayList<Fragment> fragments;
    private PlayViewpageAdapter adapter;
    private FragmentManager fragmentManager;
    private ExamRecordFragment examRecordFragment;
    private StudyRecordFragment listenRecordFragment;

    public RecordFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        fragments=new ArrayList<>();
        examRecordFragment = new ExamRecordFragment();
        listenRecordFragment = new StudyRecordFragment();

        rbListenRecord.setOnClickListener(this);
        rbExamRecord.setOnClickListener(this);

        fragments.add(listenRecordFragment);
        fragments.add(examRecordFragment);

        fragmentManager = getChildFragmentManager();
//        adapter = new PlayViewpageAdapter(fragmentManager, fragments);
//        recordVp.setAdapter(adapter);
//        recordVp.setOffscreenPageLimit(2);
//        recordVp.addOnPageChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_listen_record:
                recordVp.setCurrentItem(0);
                break;
            case R.id.rb_exam_record:
                recordVp.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rbListenRecord.setChecked(true);
        } else if (position == 1) {
            rbExamRecord.setChecked(true);
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
