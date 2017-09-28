package com.me.fanyin.zbme.views.course.play.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.fanyin.zbme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class CourseIntroFragment extends Fragment {

    @BindView(R.id.course_info)
    TextView courseInfo;
    Unbinder unbinder;
    @BindView(R.id.network_error_layout)
    RelativeLayout networkErrorLayout;
    private String intro;

    public CourseIntroFragment() {
    }

    public static CourseIntroFragment getInstance(String intro) {
        CourseIntroFragment f = new CourseIntroFragment();
        Bundle args = new Bundle();
        args.putString("intro", intro);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        intro = arguments.getString("intro");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_course_intro_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setCourseInfo(intro);
        return view;
    }

    public void setCourseInfo(String info) {
        if(!TextUtils.isEmpty(info)){
            courseInfo.setText(info);
            networkErrorLayout.setVisibility(View.GONE);
        }else{
            courseInfo.setText("");
            networkErrorLayout.setVisibility(View.VISIBLE);
        }
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
