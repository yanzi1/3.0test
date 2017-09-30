package com.me.fanyin.zbme.views.exam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.activity.ExamReportActivity;
import com.me.fanyin.zbme.views.exam.activity.ScoreCardActivity;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragmentActivity;
import com.me.fanyin.zbme.views.exam.adapter.QuestionItemAdapter;
import com.me.fanyin.zbme.views.exam.event.CloseExamActivity;
import com.me.fanyin.zbme.views.exam.event.ComprehensiveUpdatePage;
import com.me.fanyin.zbme.views.exam.event.ExamIndexEvent;
import com.me.fanyin.zbme.views.exam.utils.CommenUtils;
import com.me.fanyin.zbme.views.exam.view.CustomDialog;
import com.me.fanyin.zbme.widget.EmptyViewLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by wyc on 2016/4/29.
 */
public class ExamActivity extends BaseFragmentActivity implements View.OnClickListener,ExamView {
    
    @BindView(R.id.tv_sequence)
    TextView tv_sequence;
    @BindView(R.id.tv_total_count)
    TextView tv_total_count;
    @BindView(R.id.top_title_text)
    TextView top_title_text;
    @BindView(R.id.examination_bottom_right)
    TextView examination_bottom_right;
    @BindView(R.id.examination_bottom_center)
    TextView examination_bottom_center;
    @BindView(R.id.examination_bottom_left)
    TextView examination_bottom_left;
    @BindView(R.id.exam_pager_bottom_report_layout)
    RelativeLayout exam_pager_bottom_report_layout;
    @BindView(R.id.empty_layout)
    LinearLayout empty_layout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.top_title_right)
    LinearLayout top_title_right;
    @BindView(R.id.exam_pager_bottom)
    View exam_pager_bottom;
    @BindView(R.id.exam_slide_guide)
    RelativeLayout exam_slide_guide;
    @BindView(R.id.iv_exam_no_advice)
    ImageView iv_exam_no_advice;
    private CustomDialog dialog_uncomplete;
    private CustomDialog dialog_complete;
    private CustomDialog dialog_complete_unsubmit;
    private QuestionItemAdapter mPagerAdapter;
    private ExamPersenter examPersenter;
    private EmptyViewLayout mEmptyLayout;
    private MaterialDialog alertDialog;

    @OnClick(R.id.tv_classroom_guide2) void onGuide() {
        exam_slide_guide.setVisibility(View.GONE);
        examPersenter.isShowAdvice=false;
    }

    @OnClick(R.id.ll_remanber) void onRemanber() {
        boolean rember= SharedPrefHelper.getInstance().getIsfirstInDuoxuan();
        if (rember){
            SharedPrefHelper.getInstance().setIsfirstInDuoxuan(false);
            iv_exam_no_advice.setBackgroundResource(R.drawable.exam_guide_noadvice_sel);
        }else{
            SharedPrefHelper.getInstance().setIsfirstInDuoxuan(true);
            iv_exam_no_advice.setBackgroundResource(R.drawable.exam_guide_noadvice_nor);
        }
    }

    @OnClick(R.id.top_title_left) void onBack() {
        onBackPressed();
    }
    @OnClick(R.id.examination_bottom_right) void onExaminationBottomRight() {
        examPersenter.clickExaminationBottomRight();
    }
    @OnClick(R.id.examination_bottom_center) void onExaminationBottomCenter() {
        examPersenter.clickExaminationBottomCenter();
    }
    @OnClick(R.id.examination_bottom_left) void onExaminationBottomLeft() {
        Intent intent = new Intent(ExamActivity.this, ScoreCardActivity.class);
        intent.putExtra("time", examPersenter.time);
        intent.putExtra("typeId", examPersenter.typeId);
        intent.putExtra("examId", examPersenter.examId);
        intent.putExtra("examinationId", examPersenter.examinationId);
        intent.putExtra("subjectId", examPersenter.subjectId);
        startActivity(intent);
    }
    @OnClick(R.id.exam_pager_bottom_report_layout) void onExampagerBottomReportLayout() {
        if ((CommenUtils.getErrorNum(examPersenter.questionlist) + CommenUtils.getRightNum(examPersenter.questionlist)) == examPersenter.totallist.size()) {
            dialog_complete.show();
        } else {
            dialog_uncomplete.show();
        }
    }
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.exam_test_pager);
        ButterKnife.bind(this);
        examPersenter = new ExamPersenter();
        examPersenter.attachView(this);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                examPersenter.setVPPageChangeListener(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }

    @Override
    public void  initView() {
        boolean rember= SharedPrefHelper.getInstance().getIsfirstInDuoxuan();
        examPersenter.isShowAdvice=rember;
        initBroadcast();
        dialog_uncomplete = new CustomDialog(this, R.layout.dialog_uncomplete, R.style.Theme_dialog);
        dialog_uncomplete.setCanceledOnTouchOutside(false);
        dialog_uncomplete.findViewById(R.id.tv_exam_contiue).setOnClickListener(this);
        dialog_uncomplete.findViewById(R.id.tv_exam_submit).setOnClickListener(this);
        dialog_uncomplete.findViewById(R.id.tv_exam_nextdo).setOnClickListener(this);
        dialog_complete = new CustomDialog(this, R.layout.dialog_complete, R.style.Theme_dialog);
        dialog_complete.setCanceledOnTouchOutside(false);
        dialog_complete.findViewById(R.id.tv_exam_submit).setOnClickListener(this);
        dialog_complete.findViewById(R.id.tv_exam_contiue).setOnClickListener(this);
        dialog_complete_unsubmit = new CustomDialog(this, R.layout.dialog_complete_unsubmit, R.style.Theme_dialog);
        dialog_complete_unsubmit.setCanceledOnTouchOutside(false);
        dialog_complete_unsubmit.findViewById(R.id.tv_exam_contiue).setOnClickListener(this);
        dialog_complete_unsubmit.findViewById(R.id.tv_exam_giveup).setOnClickListener(this);
        dialog_complete_unsubmit.findViewById(R.id.tv_exam_nextdo).setOnClickListener(this);
        mEmptyLayout = new EmptyViewLayout(this, empty_layout);
        mEmptyLayout.setErrorButtonClickListener(mErrorClickListener);
        mEmptyLayout.setEmptyButtonClickListener(emptyButtonClickListener);
        updateQuestionItemAdapter();
    }

    private void initBroadcast() {
        registerReceiver(receiver,
                new IntentFilter("com.dongao.positon.change"));
    }

    @Override
    public void initData() {
        examPersenter.getData();
    }

    @Override
    public void onBackPressed() {
        examPersenter.backPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exam_contiue:
                dialogDissmis();
                break;
            case R.id.tv_exam_submit:
                dialogDissmis();
                examPersenter.submitQuestion();
                break;
            case R.id.tv_exam_nextdo:
                dialogDissmis();
                examPersenter.doNextTime();
                break;
            case R.id.tv_exam_giveup:
                dialogDissmis();
                ExamActivity.this.finish();
                break;
            default:
                break;
        }
    }

    private View.OnClickListener mErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            examPersenter.getDataAgain();
        }
    };
    private View.OnClickListener emptyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    public void progressStatus(int status) {
        switch (status) {
            case Constants.VIEW_STATUS_INIT:
                mEmptyLayout.showLoading();
                break;
            case Constants.VIEW_STATUS_SUBMIT:
                mEmptyLayout.showLoading();
                break;
            case Constants.VIEW_STATUS_SAVE_DATA:
                mEmptyLayout.showLoading();
                break;
            case Constants.VIEW_STATUS_SUCCESS:
                showTopTitleRight(true);
                mEmptyLayout.showContentView();
                break;
            case Constants.VIEW_STATUS_ERROR_OTHER:
                mEmptyLayout.showError();
                break;
            case Constants.VIEW_STATUS_EMPTY:
                mEmptyLayout.showEmpty();
                break;
            case Constants.VIEW_STATUS_ERROR_NET:
                mEmptyLayout.showNetErrorView();
                break;
        }
    }

    @Override
    public void showDuoxuanGuide(boolean isShow) {
//        if (isShow){
//            exam_slide_guide.setVisibility(View.VISIBLE);
//        }else{
//            exam_slide_guide.setVisibility(View.GONE);
//        }
        exam_slide_guide.setVisibility(View.GONE);
    }

    public void dialogDissmis() {
        if (dialog_complete.isShowing()) {
            dialog_complete.dismiss();
            examPersenter.startCounter();
        } else if (dialog_uncomplete.isShowing()) {
            dialog_uncomplete.dismiss();
            examPersenter.startCounter();
        } else if (dialog_complete_unsubmit.isShowing()) {
            dialog_complete_unsubmit.dismiss();
            examPersenter.startCounter();
        }
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
    public void showTotalNumber(String totalNumber) {
        tv_total_count.setText(totalNumber);
    }

    @Override
    public void showCurrentNumber(int currentNumber) {
        tv_sequence.setText(currentNumber+"");
    }

    @Override
    public void showExaminationTittle(String title) {
        top_title_text.setText(title);
    }

    @Override
    public ViewPager getViewPager() {
        return vp;
    }

    @Override
    public void showBottomTittle(int type) {
        if (type == Constants.EXAM_TAG_ABILITY || type == Constants.EXAM_TAG_KNOWLEDGE || type == Constants.EXAM_TAG_HIGHFREQUENCY || type == Constants.EXAM_TAG_EVERY_YEAR  || type == Constants.EXAM_TAG_REPORT || type == Constants.EXAM_TAG_CONTINU || type == Constants.EXAM_DO_CONTINUE) {
            examination_bottom_center.setText(getString(R.string.collect));
            examination_bottom_center.setTextColor(getResources().getColor(R.color.exam_test_question_bottom_font));
            examination_bottom_center.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_favor_normal, 0, 0);
        }else if(type == Constants.EXAM_TAG_FALT || type == Constants.EXAM_TAG_COLLECTION){
            examination_bottom_center.setText(getString(R.string.exam_check_analyze));
            examination_bottom_center.setTextColor(getResources().getColor(R.color.exam_test_question_bottom_font));
            examination_bottom_center.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_view_normal, 0, 0);
        }
    }

    @Override
    public void showBottomTittlePress(int type) {
        if (type == Constants.EXAM_TAG_ABILITY || type == Constants.EXAM_TAG_KNOWLEDGE || type == Constants.EXAM_TAG_HIGHFREQUENCY || type == Constants.EXAM_TAG_EVERY_YEAR  || type == Constants.EXAM_TAG_REPORT || type == Constants.EXAM_TAG_CONTINU || type == Constants.EXAM_DO_CONTINUE) {
            examination_bottom_center.setText(getString(R.string.collect));
            examination_bottom_center.setTextColor(getResources().getColor(R.color.color_primary));
            examination_bottom_center.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_favor_hover, 0, 0);
        }else if(type == Constants.EXAM_TAG_FALT || type == Constants.EXAM_TAG_COLLECTION){
            examination_bottom_center.setText(getString(R.string.exam_check_analyze));
            examination_bottom_center.setTextColor(getResources().getColor(R.color.color_primary));
            examination_bottom_center.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_view_hover, 0, 0);

        }
    }

    @Override
    public void showTopTittle(String title) {
        top_title_text.setText(title);
    }

    @Override
    public void updateQuestionItemAdapter() {
        if (mPagerAdapter!=null){
            mPagerAdapter.notifyDataSetChanged();
        }else{
            mPagerAdapter = new QuestionItemAdapter(getSupportFragmentManager(),this);
            vp.setAdapter(mPagerAdapter);
        }
    }

    @Override
    public void showExamTime(String time) {
        if (examination_bottom_right!=null&&time!=null){
            examination_bottom_right.setText(time);
        }
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void setVPIndex(int position) {
        vp.setCurrentItem(position);
    }

    @Override
    public void vpNotifyData() {
        if (mPagerAdapter!=null){
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void intentExamReportActivity() {
        goIntent();
    }

    @Override
    public void showTopTitleRight(boolean isShow) {
        if (isShow){
            top_title_right.setVisibility(View.VISIBLE);
        }else{
            top_title_right.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showExamPagerBottom(boolean isShow) {
        if (isShow){
            exam_pager_bottom.setVisibility(View.VISIBLE);
        }else{
            exam_pager_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void showExamPagerBottomReportLayou(boolean isShow) {
        if (isShow){
            exam_pager_bottom_report_layout.setVisibility(View.VISIBLE);
        }else{
            exam_pager_bottom_report_layout.setVisibility(View.GONE);
        }
       
    }

    @Override
    public PhoneAppContext getAppContext() {
        return appContext;
    }

    @Override
    public void showExamTimeOrNot(boolean isShow) {
        if (isShow){
            examination_bottom_right.setVisibility(View.VISIBLE);
        }else{
            examination_bottom_right.setVisibility(View.GONE);
        }
    }

    @Override
    public void showExamBottomRight(int type) {
        if (type == Constants.EXAM_TAG_ABILITY || type == Constants.EXAM_TAG_KNOWLEDGE || type == Constants.EXAM_TAG_HIGHFREQUENCY || type == Constants.EXAM_TAG_EVERY_YEAR  || type == Constants.EXAM_TAG_REPORT || type == Constants.EXAM_TAG_CONTINU || type == Constants.EXAM_DO_CONTINUE) {
            examination_bottom_center.setText("");
        }else if(type == Constants.EXAM_TAG_FALT){
            examination_bottom_right.setText("我会了");
            examination_bottom_right.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_del_fault_normal, 0, 0);
        }else if( type == Constants.EXAM_TAG_COLLECTION){
            examination_bottom_right.setText(getString(R.string.collect));
            examination_bottom_right.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.exam_button_favor_hover, 0, 0);
        }
    }

    @Override
    public void showBackPress() {
        dialog_complete_unsubmit.show();
    }

    @Override
    public EmptyViewLayout getEmptyView() {
        return mEmptyLayout;
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

    public void showNoPermission() {

    }


    @Override
    public void showError(String message) {
    }

    @Override
    public Context context() {
        return ExamActivity.this;
    }

    private void goIntent() {
        Intent intent_report = new Intent(ExamActivity.this, ExamReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("error_num", CommenUtils.getErrorNum(examPersenter.questionlist));
        bundle.putInt("right_num", CommenUtils.getRightNum(examPersenter.questionlist));
        bundle.putString("time", CommenUtils.changeTime(examPersenter.time));
        bundle.putString("score", CommenUtils.getAllScore(examPersenter.questionlist) + "");
        bundle.putString("typeId", examPersenter.typeId);
        bundle.putString("examId", examPersenter. examId);
        bundle.putString("examinationId",examPersenter. examinationId);
        bundle.putString("subjectId",examPersenter. subjectId);
        appContext.setAllList(examPersenter.questionlist);
        appContext.setErrorList(CommenUtils.getErrorList(examPersenter.questionlist));
        intent_report.putExtras(bundle);
        startActivity(intent_report);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);
    }

    @Subscribe
    public void onEventMainThread(ExamIndexEvent event) {
        examPersenter.onEventMainThreadPageChange(event);
    }

    @Subscribe
    public void onEventMainThread(CloseExamActivity event) {
        this.finishActivity();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.dongao.positon.change".equals(action)) {
                ComprehensiveUpdatePage comprehensiveUpdatePage= (ComprehensiveUpdatePage) intent.getSerializableExtra("ComprehensiveUpdatePage");
                examPersenter.onEventMainThreadComprehensiveUpdatePage(comprehensiveUpdatePage);
            }
        }
    };
       
}
