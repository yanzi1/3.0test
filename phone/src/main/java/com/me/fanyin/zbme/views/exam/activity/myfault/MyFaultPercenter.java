package com.me.fanyin.zbme.views.exam.activity.myfault;


import android.content.Intent;

import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.activity.myfault.bean.FaultClass;
import com.me.fanyin.zbme.views.exam.db.FaltQuestionDB;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;

import java.util.ArrayList;

/**
 * Created by wyc on 2016/5/10.
 */
public class MyFaultPercenter extends BasePersenter<MyFaultView> {
    public ArrayList<FaultClass> faultClassList =new ArrayList<>();
    private String userId;
    private String examId;
    private String subjectId;
    private int typeId;

    @Override
    public void getData() {
        getMvpView().showLoading();
        userId = SharedPrefHelper.getInstance().getUserId()+"";
        examId= SharedPrefHelper.getInstance().getExamId();
        subjectId= SharedPrefHelper.getInstance().getSubjectId();

        //test
        faultClassList = new ArrayList<>();
       
        Intent intent=getMvpView().getTheIntent();
        faultClassList= (ArrayList<FaultClass>) intent.getSerializableExtra("list");
        if (faultClassList==null){
            faultClassList=new ArrayList<>();
        }
       
        SharedPrefHelper.getInstance().setFaultTypeId(faultClassList.get(0).getClassId());
        getMvpView().initAdapter();
    }

    private void testData() {
        typeId= ExamTypeEnum.EXAM_TYPE_DANXUAN.getId();
        faultClassList.add(getFaultClassByType(typeId));
        typeId= ExamTypeEnum.EXAM_TYPE_PANDUAN.getId();
        faultClassList.add(getFaultClassByType(typeId));
    }

    @Override
    public void setData(String str) {
    }
    @Override
    public void onError(Exception e) {
        getMvpView().showError("获取数据失败");
    }
    
    private FaultClass getFaultClassByType(int type){
        FaultClass faultClass=new FaultClass();
        FaltQuestionDB faltQuestionDB=new FaltQuestionDB(getMvpView().context());
        faultClass.setClassId(type+"");
        faultClass.setClassName(ExamTypeEnum.getValue(type));
        return faultClass;
    }
    
    public void getOnPageChangeListener(int position){
        SharedPrefHelper.getInstance().setFaultTypeId(faultClassList.get(position).getClassId());
    }
}
