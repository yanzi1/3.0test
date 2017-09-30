package com.me.fanyin.zbme.views.exam.activity.myfault;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.ExamActivity;
import com.me.fanyin.zbme.views.exam.activity.myfault.bean.ExaminationClass;
import com.me.fanyin.zbme.views.exam.db.FaltQuestionDB;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyc on 2016/5/18.
 */
public class MyFaultFragmentPercenter extends BasePersenter<MyFaultFragmentView> {
    private String userId;
    private String examId;
    private String subjectId;
    private String typeId;
    public List<ExaminationClass> faultList;
    public boolean isVisible=true;
    public boolean mHasLoadedOnce;
    public boolean isLogin;
    /** 标志位，标志已经初始化完成 */
    public boolean isPrepared;

    @Override
    public void getData() {
       
        userId= SharedPrefHelper.getInstance().getUserId()+"";
        examId=SharedPrefHelper.getInstance().getExamId();
        subjectId=SharedPrefHelper.getInstance().getSubjectId();
        typeId=SharedPrefHelper.getInstance().getFaultTypeId();

        FaltQuestionDB faltQuestionDB=new FaltQuestionDB(getMvpView().context());
        faultList=faltQuestionDB.findAllByName(userId, examId, subjectId,typeId);
        if (faultList==null){
            faultList=new ArrayList<>();
        }
        getMvpView().initAdapter();
        mHasLoadedOnce = true; //设置是否
        if (faultList.size()==0){
            getMvpView().getEmptyView().showEmpty();
        }else{
            getMvpView().getEmptyView().showContentView();
        }
        getMvpView().setIsRefresh(false);
    }

    @Override
    public void setData(String obj) {

    }
    
    public void setUserHint(boolean isTrue){
        if(isTrue) {
            isVisible = true;
            boolean isLogins = SharedPrefHelper.getInstance().isLogin();
            String examid = SharedPrefHelper.getInstance().getExamId();
            if(isLogins != isLogin || examid .equals( examId)){
                mHasLoadedOnce = false;
            }
            getMvpView().setIsRefresh(true);
            getData();
        } else {
            isVisible = false;
        }
    }

    public void setOnItemClick(AdapterView<?> parent, View view, int position, long id){
        SharedPrefHelper.getInstance().setExamTag(Constants.EXAM_TAG_FALT);
        Intent intent=new Intent(getMvpView().context(), ExamActivity.class);
        intent.putExtra("examinationId",faultList.get(position).getExaminationId());
        getMvpView().context().startActivity(intent);
    }
    
}
