package com.me.fanyin.zbme.views.exam.activity;

import android.content.Intent;
import android.os.Bundle;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.ExamActivity;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.utils.CommenUtils;

/**
 * Created by wyc on 2016/5/3.
 */
public class ExamReportPersenter extends BasePersenter<ExamReportView> {
    private int report_false_num;
    private int report_true_num;
    private String time;
    private String score;
    private String typeId;
    private String examId;
    private String examinationId;
    private String subjectId;

    @Override
    public void getData() {
       // exam_tag = SharedPrefHelper.getInstance().getExamTag();
        int exam_tag= Constants.EXAM_TAG_ABILITY;
        Intent intent=getMvpView().getWayIntent();
        Bundle bundle=intent.getExtras();
        report_false_num=bundle.getInt("error_num", 0);
        report_true_num=bundle.getInt("right_num", 0);
        //time=bundle.getString("time", "");
        time=bundle.getString("time");
        score=bundle.getString("score");
        float sc=Float.valueOf(score);
        String pingjia= CommenUtils.getPingJia(sc,getMvpView().getAppContext().getAllList());
        getMvpView().showPingJia(pingjia);
        if (null==time){
            time="";
        }
        if (null==score){
            score="";
        }
        getIntentData(bundle);
        getMvpView().setErrorNumber(report_false_num);
        getMvpView().setRightNumber(report_true_num);
        getMvpView().setScore(score);
        getMvpView().setUseTime(time);
        getMvpView().setVisible(exam_tag);

    }

    @Override
    public void setData(String obj) {

    }

    @Override
    public void attachView(ExamReportView mvpView) {
        super.attachView(mvpView);
    }

    public void analyzeWrong(){
        getMvpView().getAppContext().setQuestionlist(getMvpView().getAppContext().getErrorList());
        Intent intent_wrong = new Intent(getMvpView().context(),ExamActivity.class);
        //把数据传递给答题报告
        intent_wrong.putExtra("typeId", typeId);
        intent_wrong.putExtra("examId", examId);
        intent_wrong.putExtra("examinationId", examinationId);
        intent_wrong.putExtra("subjectId", subjectId);
        SharedPrefHelper.getInstance().setExamTag(Constants.EXAM_TAG_REPORT);
        getMvpView().context().startActivity(intent_wrong);
    }

    public void analyzeAll(){
        getMvpView().getAppContext().setQuestionlist(getMvpView().getAppContext().getAllList());
        Intent intent_all = new Intent(getMvpView().context(),ExamActivity.class);
        intent_all.putExtra("typeId", typeId);
        intent_all.putExtra("examId", examId);
        intent_all.putExtra("examinationId", examinationId);
        intent_all.putExtra("subjectId", subjectId);
        SharedPrefHelper.getInstance().setExamTag(Constants.EXAM_TAG_REPORT);
        getMvpView().context().startActivity(intent_all);
    }

    /**获取intent传递过来的数据
     * @param bundle*/
    private void getIntentData(Bundle bundle) {
        typeId=bundle.getString("typeId");
        if (typeId==null||typeId.isEmpty()){
            typeId=SharedPrefHelper.getInstance().getMainTypeId();
        }
        examId=bundle.getString("examId");
        if (examId==null||examId.isEmpty()){
            examId=SharedPrefHelper.getInstance().getExamId();
        }
        examinationId=bundle.getString("examinationId");
        if (examinationId ==null||examinationId.isEmpty()){
            examinationId=SharedPrefHelper.getInstance().getExaminationId();
        }
        subjectId=bundle.getString("subjectId");
        if (subjectId==null||subjectId.isEmpty()){
            subjectId=SharedPrefHelper.getInstance().getSubjectId();
        }
    }
}
