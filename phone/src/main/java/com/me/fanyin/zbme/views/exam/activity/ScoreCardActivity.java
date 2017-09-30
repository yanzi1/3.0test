package com.me.fanyin.zbme.views.exam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragmentActivity;
import com.me.fanyin.zbme.views.exam.fragment.ScoreCardFragment;
import com.me.fanyin.zbme.views.exam.utils.CommenUtils;
import com.me.fanyin.zbme.views.exam.view.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyc on 2016/5/6.
 */
public class ScoreCardActivity extends BaseFragmentActivity implements ScoreCardView{

    private ScoreCardPercenter scoreCardPercenter;
    @BindView(R.id.top_title_left)
    ImageView top_title_left;
    @BindView(R.id.top_title_right)
    ImageView top_title_right;
    @BindView(R.id.top_title_text)
    TextView top_title_text;
    @BindView(R.id.empty_layout)
    LinearLayout empty_layout;
    @BindView(R.id.exam_pager_bottom_report_layout)
    RelativeLayout exam_pager_bottom_report_layout;
    private CustomDialog dialog_complete;
    private TextView tv_title_submit;

    @OnClick(R.id.exam_pager_bottom_report_layout) void onExamPagerBottomReportLayout() {
        //判断当前题是否已经做完
        int err= CommenUtils.getErrorNum(ExamPersenter.questionlist);
        int rig=CommenUtils.getRightNum(ExamPersenter.questionlist);
        if((err+rig)==0){
                   /* Toast.makeText(ScoreCardActivity.this, "您当前还未做题", Toast.LENGTH_SHORT).show();
                    return;*/
            tv_title_submit.setText("您还有未做完的题，是否确认提交？");
            dialog_complete.show();
        }else if ((err+rig)==ExamPersenter.totallist.size()){
            dialog_complete.show();
        }else {
            tv_title_submit.setText("您还有未做完的题，是否确认提交？");
            dialog_complete.show();
        }
    }
    @OnClick(R.id.top_title_left) void onBack() {   onBackPressed();
    }
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_test_score_card);
        ButterKnife.bind(this);
        scoreCardPercenter = new ScoreCardPercenter();
        scoreCardPercenter.attachView(this);
        initView();
        initData();

    }
    @Override
    public void initView() {
        top_title_left.setVisibility(View.VISIBLE);
        top_title_text.setText(getResources().getString(R.string.score_card));
        dialog_complete = new CustomDialog(this, R.layout.dialog_complete, R.style.Theme_dialog);
        dialog_complete.setCanceledOnTouchOutside(false);
        dialog_complete.findViewById(R.id.tv_exam_submit).setOnClickListener(this);
        dialog_complete.findViewById(R.id.tv_exam_contiue).setOnClickListener(this);
        tv_title_submit=(TextView)dialog_complete.findViewById(R.id.tv_title_submit);
        scoreCardPercenter.getData();
        //判断当前是哪一种情况进入的答题卡
        if (scoreCardPercenter.exam_tag== Constants.EXAM_TAG_REPORT ||scoreCardPercenter.exam_tag==Constants.EXAM_TAG_COLLECTION ||scoreCardPercenter.exam_tag==Constants.EXAM_TAG_FALT ){
            exam_pager_bottom_report_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment().newInstance(scoreCardPercenter.tag);
        getSupportFragmentManager().beginTransaction().replace(R.id.exam_score_card_fragment, scoreCardFragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exam_contiue:
                dialogDissmis();
                break;
            case R.id.tv_exam_submit:
                dialogDissmis();
                progressStatus(Constants.VIEW_STATUS_SUBMIT);
                scoreCardPercenter.submitReport();
                break;

            default:
                break;
        }
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

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return ScoreCardActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ScoreCardActivity.this.finish();
    }

    /**
     * 取消dialog
     */
    public void dialogDissmis() {
        if (dialog_complete.isShowing()) {
            dialog_complete.dismiss();
        } 
    }

    /**
     * View状态切换
     *
     * @param status
     */
    private void progressStatus(int status) {
        switch (status) {
            case Constants.VIEW_STATUS_SUBMIT:
                /*aq.id(R.id.textViewMessage).text("正在提交数据，请稍后...");
                empty_layout.setVisibility(View.VISIBLE);*/
                break;
            case Constants.VIEW_STATUS_SAVE_DATA:
                /*aq.id(R.id.textViewMessage).text("正在保存数据，请稍后...");
                empty_layout.setVisibility(View.VISIBLE);*/
                break;
            case Constants.VIEW_STATUS_SUCCESS:
                empty_layout.setVisibility(View.GONE);
                break;
            case Constants.VIEW_STATUS_EMPTY:
                /*aq.id(R.id.empty_iv).background(R.drawable.empty_myerror);
                aq.id(R.id.empty_tv).text("没有题哦...");
                aq.id(R.id.empty_tv2).gone();*/
                empty_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public Intent getTheIntent() {
        return getIntent();
    }

    @Override
    public PhoneAppContext getAppContext() {
        return appContext;
    }

    @Override
    public void showStatueView(int state) {
        progressStatus(state);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }
}
