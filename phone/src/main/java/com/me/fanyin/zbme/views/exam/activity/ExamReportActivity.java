package com.me.fanyin.zbme.views.exam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragmentActivity;
import com.me.fanyin.zbme.views.exam.fragment.ReportScoreCardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyc on 2016/4/29.
 */
public class ExamReportActivity extends BaseFragmentActivity implements ExamReportView {
    
    @BindView(R.id.exam_report_time)
    TextView exam_report_time;
    @BindView(R.id.tv_right_number)
    TextView tv_right_number;
    @BindView(R.id.top_title_text)
    TextView top_title_text;
    @BindView(R.id.top_title_left)
    ImageView top_title_left;
    @BindView(R.id.tv_pingjia)
    TextView tv_pingjia;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.tv_wrong_analysis)
    TextView tv_wrong_analysis;
    @BindView(R.id.ll_report_score)
    LinearLayout ll_report_score;
    @BindView(R.id.bt_total_score)
    TextView bt_total_score;
    @BindView(R.id.content_sv)
    ScrollView content_sv;
    @BindView(R.id.ll_error_bg)
    LinearLayout ll_error_bg;
    
    
    private ExamReportPersenter examReportPersenter;
    

    @OnClick(R.id.top_title_left) void onBack() {
        onBackPressed();
    }
    @OnClick(R.id.tv_wrong_analysis) void onTvWrongAnalysis() {
        examReportPersenter.analyzeWrong();
    }
    @OnClick(R.id.tv_all_analysis) void onTvAllAnalysis() {
        examReportPersenter.analyzeAll();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_test_report);
        ButterKnife.bind(this);
        examReportPersenter = new ExamReportPersenter();
        examReportPersenter.attachView(this);
        initView();
        setListener();
        initData();
        
    }

    @Override
    public void initView() {
        //设置scrollview 自动置顶
        ll_report_score.setFocusable(true);
        ll_report_score.setFocusableInTouchMode(true);
        ll_report_score.requestFocus();
        top_title_left.setVisibility(View.VISIBLE);
        top_title_text.setText(getResources().getString(R.string.exam_report));
    }

    private void setListener() {

    }

    @Override
    public void initData() {
        examReportPersenter.getData();
        ReportScoreCardFragment scoreCardAnalysisFragment=new ReportScoreCardFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.exam_score_card_fragment,scoreCardAnalysisFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void intent() {
        
    }

    @Override
    public void out() {

    }

    @Override
    public Intent getWayIntent() {
        return getIntent();
    }

    @Override
    public void setRightNumber(int number) {
        tv_right_number.setText(number+"");
    }

    @Override
    public void setErrorNumber(int number) {
        tv_error.setText(number+"");
        if (number==0){
            tv_wrong_analysis.setEnabled(false);
            ll_error_bg.setBackgroundResource(R.drawable.exam_scorecard_bottom_bg_error_no);
        }
    }

    @Override
    public void setUseTime(String time) {
        exam_report_time.setText(time);
    }

    @Override
    public void setScore(String score) {
        bt_total_score.setText(score);
    }

    @Override
    public void setVisible(int examTag) {
        ll_report_score.setVisibility(View.VISIBLE);
    }

    @Override
    public PhoneAppContext getAppContext() {
        return appContext;
    }

    @Override
    public void showPingJia(String str) {
        tv_pingjia.setText(str);
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
        return ExamReportActivity.this;
    }

    @Override
    public void onClick(View v) {
        
    }
}
