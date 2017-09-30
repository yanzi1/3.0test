package com.me.fanyin.zbme.views.exam.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.db.AnswerLogDB;
import com.me.fanyin.zbme.views.exam.db.FaltQuestionDB;
import com.me.fanyin.zbme.views.exam.event.CloseExamActivity;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.utils.CommenUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyc on 2016/5/6.
 */
public class ScoreCardPercenter extends BasePersenter<ScoreCardView> {
    public int tag = 1;
    public int exam_tag;
    private int newTime;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }

        }
    };
    private String classId;
    private String sectionId;
    private String examinationId;
    private String typeId;
    private String examId;
    private String subjectId;
    private String userId;

    @Override
    public void getData() {
        exam_tag = SharedPrefHelper.getInstance().getExamTag();
//        subjectId=SharedPrefHelper.getInstance().getSubjectId();
        } 
    
    @Override
    public void setData(String obj) {

    }

    /**
     * 提交报告
     */
    public void submitReport() {
        /**
         * 判断当前是否是能力评估
         */
        if (exam_tag== Constants.EXAM_TAG_ABILITY){
//TODO            SharedPrefHelper.getInstance().setCapScore(subjectId,CommenUtils.getAllScore(ExamPersenter.questionlist));
        }
        //获取时间
        Intent intent=getMvpView().getTheIntent();
        newTime=intent.getIntExtra("time", 0);

        userId = SharedPrefHelper.getInstance().getUserId()+"";
        subjectId = SharedPrefHelper.getInstance().getSubjectId();
        examId = SharedPrefHelper.getInstance().getExamId();
        typeId = SharedPrefHelper.getInstance().getMainTypeId();
        examinationId = SharedPrefHelper.getInstance().getExaminationId();
        sectionId=SharedPrefHelper.getInstance().getSectionId();
        classId=SharedPrefHelper.getInstance().getClassId();
        getIntentData();
        AnswerLogDB answerLogDB = new AnswerLogDB(getMvpView().context());
        //由于当前只是真正的提交服务器，试题已经完成所以不需要存储学习位置
        int position=0;
        Map<String, Integer> compMap=new HashMap<>();
        CommenUtils.saveAnswerLog(getMvpView().context(), true, ExamPersenter.questionlist, userId, examId, subjectId, typeId,  examinationId, answerLogDB, newTime,position, compMap,sectionId,classId);
        //保存错题：
//        saveError();
        FaltQuestionDB faltQuestionDB = new FaltQuestionDB(getMvpView().context());
        String examinationName=SharedPrefHelper.getInstance().getExaminationTitle();
        CommenUtils.saveError(sectionId,ExamPersenter.questionlist, userId, examId, subjectId, typeId, examinationId, faltQuestionDB,examinationName);

//TODO        SharedPrefHelper.getInstance().setAnswerLogIsChange(true);

        if(NetWorkUtils.isNetworkAvailable(getMvpView().context())){
            //提交服务器并跳转界面
            CommenUtils.upLoadResult(userId,subjectId,examinationId,newTime,handler,getMvpView().context());
        }else{
            getMvpView().showError("网络未连接,暂时不能保存到服务器");
        }
        //跳转到页面
        intentReport();
    }

    /**
     * 提交服务器返回的结果
     */
    private void intentReport(){
        //获取所有要传的参数并进行传递
        Intent intent_report = new Intent(getMvpView().context(), ExamReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("error_num", CommenUtils.getErrorNum(ExamPersenter.questionlist));
        bundle.putInt("right_num",  CommenUtils.getRightNum(ExamPersenter.questionlist));
        bundle.putString("time",  CommenUtils.changeTime(newTime));
        bundle.putString("score", CommenUtils.getAllScore(ExamPersenter.questionlist)+"");
        //把数据传递给答题报告
        bundle.putString("typeId",  typeId);
        bundle.putString("examId",  examId);
        bundle.putString("examinationId", examinationId);
        bundle.putString("subjectId", subjectId);
        /**
         * 把高度设为默认0
         */
        CommenUtils.setAllQuestionHeight(ExamPersenter.questionlist);

        getMvpView().getAppContext().setAllList(ExamPersenter.questionlist);
        getMvpView().getAppContext().setErrorList(CommenUtils.getErrorList(ExamPersenter.questionlist));
        intent_report.putExtras(bundle);
        getMvpView().showStatueView(Constants.VIEW_STATUS_SUCCESS);

        getMvpView().context().startActivity(intent_report);
        //发送EventBus关闭之前的界面
        EventBus.getDefault().post(new CloseExamActivity(true));
        getMvpView().finishActivity();

    }

    /**获取intent传递过来的数据*/
    private void getIntentData() {
        Intent intent =getMvpView().getTheIntent();
        typeId=intent.getStringExtra("typeId");
        if (typeId==null||typeId.isEmpty()){
            typeId=SharedPrefHelper.getInstance().getMainTypeId();
        }
        examId=intent.getStringExtra("examId");
        if (examId==null||examId.isEmpty()){
            examId=SharedPrefHelper.getInstance().getExamId();
        }
        examinationId=intent.getStringExtra("examinationId");
        if (examinationId==null||examinationId.isEmpty()){
            examinationId=SharedPrefHelper.getInstance().getExaminationId();
        }
        subjectId=intent.getStringExtra("subjectId");
        if (subjectId==null||subjectId.isEmpty()){
            subjectId=SharedPrefHelper.getInstance().getSubjectId();
        }
    }

}
