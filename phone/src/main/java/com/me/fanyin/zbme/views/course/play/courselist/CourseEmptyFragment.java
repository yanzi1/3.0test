package com.me.fanyin.zbme.views.course.play.courselist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.fragemnt.RecommendedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class CourseEmptyFragment extends Fragment implements View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.app_no_permission_bt)
    TextView appNoPermissionBt;
    @BindView(R.id.app_no_permission_recommended_container)
    LinearLayout appNoPermissionRecommendedContainer;
    @BindView(R.id.app_no_permission_hint)
    TextView appNoPermissionHint;
    private View view;
    private RecommendedFragment recommendedFragment;

    public CourseEmptyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exam_no_permission, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        appNoPermissionBt.setVisibility(View.GONE);
        appNoPermissionHint.setVisibility(View.GONE);
        String examId = SharedPrefHelper.getInstance().getExamId();
        recommendedFragment = RecommendedFragment.newInstance(examId, "2");

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.app_no_permission_recommended_container, recommendedFragment);
        ft.commit();

        appNoPermissionBt.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
