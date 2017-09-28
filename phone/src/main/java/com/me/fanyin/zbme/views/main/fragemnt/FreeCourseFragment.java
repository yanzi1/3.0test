package com.me.fanyin.zbme.views.main.fragemnt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.main.activity.StudentsAppraiseActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.StudentsAppraiseAdapter;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/17.
 */

public class FreeCourseFragment extends BaseMvpFragment<FreeCourseFragmentView, FreeCourseFragmentPresenter> implements FreeCourseFragmentView, RecommendedFragment.RecommendedIsEmptyListener {

    @BindView(R.id.free_course_nsv)
    NestedScrollView free_course_nsv;
//    @BindView(R.id.free_course_summary_tv)
//    TextView free_course_summary_tv;
    @BindView(R.id.recyclerview_head_more_ll)
    LinearLayout head_more_ll;
    @BindView(R.id.recyclerview_head_title_tv)
    TextView head_title_tv;
    @BindView(R.id.recyclerview_head_more_tv)
    TextView head_more_tv;
    @BindView(R.id.free_course_appraise_rcv)
    RecyclerView free_course_appraise_rcv;
    @BindView(R.id.recommended_fl)
    FrameLayout recommended_fl;
    private String examId;
    private String lectureId;
    private boolean recommendedIsEmpty = false;
    private StudentsAppraiseAdapter mAdapter;
    private StudentsEvaluateInfo studentsEvaluateInfo = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        examId = arguments.getString(Constants.EXAM_ID);
        lectureId = arguments.getString(Constants.LECTURE_ID);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerview_head_more_tv:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.LECTURE_ID, lectureId);
                gotoActivity(StudentsAppraiseActivity.class, false, bundle);
                break;
        }
    }

    private void setHeadView(boolean isShow, String title, boolean isShowMoreView, View.OnClickListener listener, String moreText) {
        head_more_ll.setVisibility(isShow ? View.VISIBLE : View.GONE);
        head_title_tv.setText(title);
        head_more_tv.setVisibility(isShowMoreView ? View.VISIBLE : View.GONE);
        head_more_tv.setOnClickListener(listener);
        head_more_tv.setText(moreText);
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(mActivity, message);
    }

    @Override
    public void initView() {
        RecommendedFragment recommendedFragment = RecommendedFragment.newInstance(examId, "2");
        recommendedFragment.setRecommendedIsEmptyListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.recommended_fl, recommendedFragment).commit();
        free_course_appraise_rcv.setLayoutManager(new LinearLayoutManager(mActivity));
        free_course_appraise_rcv.setNestedScrollingEnabled(false);
        mAdapter = mPresenter.getAdapter(free_course_appraise_rcv);
    }

    @Override
    public void initData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_free_course_fragment;
    }

    @Override
    public String getLectureId() {
        return lectureId;
    }

    @Override
    public void resetView(StudentsEvaluateInfo studentsEvaluateInfo) {
        if (studentsEvaluateInfo != null && studentsEvaluateInfo.getList().size() > 0) {
            this.studentsEvaluateInfo = studentsEvaluateInfo;
            mAdapter.setNewData(studentsEvaluateInfo.getList());
            LinearLayout common_recyclerview_head_more = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.common_recyclerview_head_more, free_course_appraise_rcv, false);
            TextView recyclerview_head_title_tv = (TextView) common_recyclerview_head_more.findViewById(R.id.recyclerview_head_title_tv);
            recyclerview_head_title_tv.setText("学员评价");
            TextView recyclerview_head_more_tv = (TextView) common_recyclerview_head_more.findViewById(R.id.recyclerview_head_more_tv);
            recyclerview_head_more_tv.setText("全部评论(" + studentsEvaluateInfo.getAllCount() + ")");
            recyclerview_head_more_tv.setVisibility(View.VISIBLE);
//            recyclerview_head_more_tv.setOnClickListener(this);
            mAdapter.addHeaderView(common_recyclerview_head_more);
            setHeadView(true, "学员评价", true, this, "全部评论(" + studentsEvaluateInfo.getAllCount() + ")");
            free_course_appraise_rcv.setVisibility(View.VISIBLE);
            hideLoading();
        } else {
            free_course_appraise_rcv.setVisibility(View.GONE);
            if (recommendedIsEmpty) {
                setHeadView(false, null, false, null, "");
                if (NetWorkUtils.isNetworkAvailable(mActivity)) {
                    showEmptyData();
                } else {
                    showNetworkError();
                }
            }
        }
    }

    @Override
    public void recommendedIsEmpty(boolean isEmpty) {
        recommendedIsEmpty = isEmpty;
        if (mAdapter.getData().size() == 0 && recommendedIsEmpty) {
            if (NetWorkUtils.isNetworkAvailable(mActivity)) {
                showEmptyData();
            } else {
                showNetworkError();
            }
        } else if (mAdapter.getData().size() > 0 && !recommendedIsEmpty) {
            free_course_nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY >= free_course_appraise_rcv.getHeight()) {
                        setHeadView(true, "推荐课程", false, null, "");
                    } else {
                        setHeadView(true, "学员评价", true, FreeCourseFragment.this, "全部评论(" + studentsEvaluateInfo.getAllCount() + ")");
                    }
                }
            });
        } else if (mAdapter.getData().size() == 0 && !recommendedIsEmpty) {
            setHeadView(true, "推荐课程", false, null, "");
            hideLoading();
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
