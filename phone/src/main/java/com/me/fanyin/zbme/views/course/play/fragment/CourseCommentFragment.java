package com.me.fanyin.zbme.views.course.play.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.StringUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.widget.KeyBoardShowListener;
import com.me.fanyin.zbme.widget.DialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class CourseCommentFragment extends BaseMvpFragment<CourseCommentView, CourseCommentPersenter> implements CourseCommentView, RatingBar.OnRatingBarChangeListener {

    @BindView(R.id.comment_close)
    TextView commentClose;
    @BindView(R.id.course_comment_star)
    RatingBar courseCommentStar;
    @BindView(R.id.course_difficult_star)
    RatingBar courseDifficultStar;
    @BindView(R.id.course_teacher_star)
    RatingBar courseTeacherStar;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    Unbinder unbinder;
    @BindView(R.id.course_comment_post)
    Button courseCommentPost;
    @BindView(R.id.comment_scorll)
    ScrollView commentScorll;
    @BindView(R.id.top)
    RelativeLayout top;
    private PlayActivity activity;

    public CourseCommentFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void initView() {
        activity = (PlayActivity) getActivity();
        commentClose.setOnClickListener(this);
        courseCommentPost.setOnClickListener(this);
        content.setFilters(new InputFilter[]{
                new StringUtils.SpecialCharFilter(), new StringUtils.MaxLengthShowToastFilter(getActivity(), 140, "")});
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLimit.setText(s.length() + "");
                if (140 - s.length() > 0 && 140 - s.length() < 140) {
                    tvLimit.setTextColor(getResources().getColor(R.color.color_accent_2));
                } else if (140 - s.length() == 0) {
                    tvLimit.setTextColor(getResources().getColor(R.color.color_accent));
                } else if (140 - s.length() == 140) {
                    tvLimit.setTextColor(getResources().getColor(R.color.text_color_primary_light_more));
                }
                if (s.length() > 0) {
                    courseCommentPost.setEnabled(true);
                } else {
                    courseCommentPost.setEnabled(false);
                }
            }
        });
        courseCommentPost.setEnabled(false);
        courseCommentStar.setOnRatingBarChangeListener(this);
        courseDifficultStar.setOnRatingBarChangeListener(this);
        courseTeacherStar.setOnRatingBarChangeListener(this);

        new KeyBoardShowListener(activity).setKeyboardListener(
                new KeyBoardShowListener.OnKeyboardVisibilityListener() {
                    @Override
                    public void onVisibilityChanged(boolean visible) {
                        if(courseCommentPost==null){
                            return;
                        }
                        if (visible) {
                            //软键盘已弹出
//                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
////                            int hight = DensityUtils.dip2px(getActivity(), 44);
//                            lp.setMargins(0, 0, 0, 0);
//                            commentScorll.setLayoutParams(lp);
                            courseCommentPost.setVisibility(View.GONE);
                            top.setVisibility(View.GONE);
                        } else {
                            //软键盘未弹出
//                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                            int hight = DensityUtils.dip2px(getActivity(), 55);
//                            lp.setMargins(0, 0, 0, hight);
                            content.clearFocus();
//                            commentScorll.setLayoutParams(lp);
                            courseCommentPost.setVisibility(View.VISIBLE);
                            top.setVisibility(View.VISIBLE);
                        }
                    }
                }, activity);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()) {
            case R.id.course_comment_star:
                if (rating < 1) {
                    courseCommentStar.setRating(1);
                }
                break;
            case R.id.course_difficult_star:
                if (rating < 1) {
                    courseDifficultStar.setRating(1);
                }
                break;
            case R.id.course_teacher_star:
                if (rating < 1) {
                    courseTeacherStar.setRating(1);
                }
                break;

        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.play_course_comment_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_close:
                if (activity.isPost) {
                    activity.hideSetting(this);
                } else {
                    if (TextUtils.isEmpty(getComment())) {
                        activity.hideSetting(this);
                    } else {
                        DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                            @Override
                            public void confirm() {
                                content.setText("");
                                activity.hideSetting(CourseCommentFragment.this);
                            }

                            @Override
                            public void cancel() {

                            }
                        }, "您的评价还未提交，是否确认离开？", 0, null, null);
                    }
                }
                break;
            case R.id.course_comment_post:
                if (TextUtils.isEmpty(content.getText().toString().trim())) {
                    Toast.makeText(activity, "评价不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NetWorkUtils.isConnected(getActivity())) {
                    Toast.makeText(activity, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.postComment(getCwId(), getComment(), getCwStarts(), getdiffStarts(), getTeachStarts());
                break;
        }
    }


    @Override
    public void showError(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        if (message.equals("该讲次已经被该学员评价过")) {
            activity.hideSetting(this);
        }
    }

    @Override
    public void showResult(String message) {
        activity.isPost = true;
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        content.setText("");
        activity.hideSetting(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public String getCwStarts() {
        return (int) courseCommentStar.getRating() + "";
    }

    public String getdiffStarts() {
        return (int) courseDifficultStar.getRating() + "";
    }

    public String getTeachStarts() {
        return (int) courseTeacherStar.getRating() + "";
    }

    public String getCwId() {
        return activity.courseWare.getId();
    }

    public String getComment() {
        if(content==null){
            return "";
        }
        return content.getText().toString();
    }
}
