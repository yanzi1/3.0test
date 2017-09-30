package com.me.fanyin.zbme.views.exam.activity.exampaperlist;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.AnswerLog;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.ExamActivity;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean.Knowledge;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean.KnowledgeListData;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.db.KnowledgeDataDB;
import com.me.fanyin.zbme.views.exam.db.AnswerLogDB;
import com.me.fanyin.zbme.views.exam.dict.ExamListTypeEnum;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.remote.ApiClient;
import com.me.fanyin.zbme.views.exam.remote.ParamsUtils;
import com.me.fanyin.zbme.views.exam.remote.bean.BaseBean;
import com.me.fanyin.zbme.views.exam.utils.Constant;
import com.me.fanyin.zbme.views.exam.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wyc on 2016/5/19.
 */
public class ExamPaperListPercenter extends BasePersenter<ExamPaperListView> {

    public List<Knowledge> KnowledgeList;
    private String userId;
    public int currentPage = 1;
    public int totalPage = 0;
    private String subjectId;
    private String typeId;
    private String year;
    private KnowledgeListData knowledgeListData;
    private KnowledgeDataDB knowledgeDB;
    private String examId;
    private AnswerLogDB answerLogDB;
    private List<AnswerLog> answerLogList;

    public void initData() {
        subjectId= SharedPrefHelper.getInstance().getSubjectId();
        userId= SharedPrefHelper.getInstance().getUserId();
        typeId= SharedPrefHelper.getInstance().getMainTypeId();
        examId= SharedPrefHelper.getInstance().getExamId();
        year="";
        /*test*/
//        userId = "21878075";
//        examId = "711";
//        subjectId = "71639";
//        SharedPrefHelper.getInstance().setUserId(userId);
//        SharedPrefHelper.getInstance().setExamId(examId);
//        SharedPrefHelper.getInstance().setSubjectId(subjectId);
        /*test*/
        
        Intent intent=getMvpView().getTheIntent();
        String title=intent.getStringExtra("typeName");
        if (title==null ||title.isEmpty()){
            title= ExamListTypeEnum.getValue(Integer.valueOf(typeId));
        }
        getMvpView().showTopTextTitle(title);
        KnowledgeList= new ArrayList<>();
        knowledgeListData=new KnowledgeListData();
        knowledgeDB=new KnowledgeDataDB(getMvpView().context());
        if (answerLogDB==null){
            answerLogDB=new AnswerLogDB(getMvpView().context());
        }
    }

    public void getDBData() {
        answerLogList=answerLogDB.findTypeAll(userId,examId,subjectId,typeId);
        if (answerLogList==null){
            answerLogList=new ArrayList<>();
        }
        getData();
    }
    
    private void chageData(){
        if (KnowledgeList.size()!=0&&answerLogList.size()!=0){
            for (int i = 0; i < KnowledgeList.size(); i++) {
                for (int j = 0; j < answerLogList.size(); j++) {
                    if (KnowledgeList.get(i).getExaminationId().equals(answerLogList.get(j).getExaminationId())){
                        int errorNumber=answerLogList.get(j).getAnswerErrorNums();
                        int doneNumber=answerLogList.get(j).getFinishedQuestions();
                        int rightNumber=answerLogList.get(j).getAnswerRightNums();
                        if (doneNumber==0){
                            continue;
                        }
                        String correctRate;
                        if (rightNumber>=doneNumber){
                            correctRate="100%";
                        }else{
                            correctRate=(rightNumber*100/doneNumber)+"%";
                        }
                        KnowledgeList.get(i).setCorrectRate(correctRate);
                        KnowledgeList.get(i).setErrorQuestions(errorNumber);
                        KnowledgeList.get(i).setFinishedQuestions(doneNumber);
                    }
                }
            }
        }
    }

    @Override
    public void getData() {
        getMvpView().showLoading();
//        test();
        if (NetUtils.checkNet(getMvpView().context()).isAvailable()) {
            getInterData();
        } else {
            KnowledgeListData data= knowledgeDB.findByUserType(userId + "", typeId, subjectId);
            if (data==null){
                getMvpView().showContentView(Constant.VIEW_TYPE_1);
                return;
            }
            knowledgeListData= JSON.parseObject(data.getContent(), KnowledgeListData.class);
            KnowledgeList=  knowledgeListData.getExaminationList();
            chageData();
            getMvpView().initAdapter();
            getMvpView().hideLoading();
            getMvpView().showContentView(Constant.VIEW_TYPE_0);
        }
    }

    public void getLoadData() {
        if (getMvpView().isRefreshNow()) {
            getMvpView().initAdapter();
            getMvpView().showContentView(Constant.VIEW_TYPE_0);
            getMvpView().showError(getMvpView().context().getResources().getString(R.string.check_net));
            return;
        }
//        testLoad();
        getMvpView().hideLoading();
        getMvpView().showContentView(Constant.VIEW_TYPE_0);
        if (NetUtils.checkNet(getMvpView().context()).isAvailable()) {
            if (currentPage>totalPage){
                getMvpView().setNoDataMoreShow(true);
                return;
            }
            loadMore(ParamsUtils.getInstance(getMvpView().context()).getExamPaperList(currentPage));
        } else {
            getMvpView().hideLoading();
            getMvpView().showContentView(Constant.VIEW_TYPE_0);
            getMvpView().showError(getMvpView().context().getResources().getString(R.string.check_net));
        }
    }

    private void getInterData() {
            apiModel.getData(ApiClient.getClient().getExamPaperList(ParamsUtils.getInstance(getMvpView().context()).getExamPaperList(currentPage)));
    }

    @Override
    public void setData(String obj) {
        try{
            getMvpView().hideLoading();
            BaseBean baseBean = JSON.parseObject(obj, BaseBean.class);
            if (baseBean == null) {
                getMvpView().showContentView(Constant.VIEW_TYPE_1);
                return;
            } else {
                int result = baseBean.getCode();
                if (result != 1000) {
                    getMvpView().showContentView(Constant.VIEW_TYPE_2);
                    return;
                }
            }
            KnowledgeList.clear();
            String body = baseBean.getBody();
            knowledgeListData= JSON.parseObject(body, KnowledgeListData.class);
            totalPage=knowledgeListData.getTotalPages();
            if (currentPage==1){
                KnowledgeListData oldData=knowledgeDB.findByUserType(userId + "", typeId,  subjectId);
                if (oldData!=null){
                    knowledgeDB.delete(oldData);
                }
                knowledgeListData.setUserId(userId);
                knowledgeListData.setType(typeId);
                knowledgeListData.setYear(year);
                knowledgeListData.setSubjectId(subjectId);
                knowledgeListData.setContent(JSON.toJSONString(knowledgeListData));
                knowledgeDB.insert(knowledgeListData);
            }
            currentPage++;
            List<Knowledge> newList=knowledgeListData.getExaminationList();
//        judgeDB(newList);
            KnowledgeList.addAll(knowledgeListData.getExaminationList());
            chageData();
            getMvpView().initAdapter();
            if(KnowledgeList.size()==0){
                getMvpView().showContentView(Constant.VIEW_TYPE_2);
            }else{
                getMvpView().showContentView(Constant.VIEW_TYPE_0);
            }
        }catch (Exception e){
                getMvpView().showContentView(Constant.VIEW_TYPE_3);
        }
    }

    private void judgeDB(List<Knowledge> newList) {
        AnswerLogDB answerLogDB=new AnswerLogDB(getMvpView().context());
        for (int i = 0; i < newList.size(); i++) {
            AnswerLog answerLog=answerLogDB.findByExamination(userId,examId,subjectId,typeId,newList.get(i).getExaminationId());
            if (answerLog!=null){
                newList.get(i).setFinishedQuestions(answerLog.getFinishedQuestions());
                newList.get(i).setErrorQuestions(answerLog.getAnswerErrorNums());
                newList.get(i).setTotalQuestions(answerLog.getTotalQuestions());
                int corrRate=(int)(answerLog.getAnswerRightNums()/answerLog.getFinishedQuestions());
                newList.get(i).setCorrectRate(corrRate+"");
            }
        }
    }

    private void loadMore(HashMap<String, String> params) {
        Call<String> call = ApiClient.getClient().getExamPaperList(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String str = response.body();
                    try {
                        getMvpView().hideLoading();
                        BaseBean baseBean = JSON.parseObject(str, BaseBean.class);
                        if (baseBean == null) {
                            return;
                        } else {
                            if (baseBean.getCode() != 1000) {
                                if (baseBean.getCode() == 9) {
                                } else {
//                                    getMvpView().showContentView(Constant.VIEW_TYPE_1);
                                }
                                return;
                            }
                        }
                        getMvpView().showContentView(Constant.VIEW_TYPE_0);
                        String body = baseBean.getBody();
                        knowledgeListData= JSON.parseObject(body, KnowledgeListData.class);
                        totalPage=knowledgeListData.getTotalPages();
//                        judgeDB(knowledgeListData.getExaminationList());
                        KnowledgeList.addAll(knowledgeListData.getExaminationList());
                        chageData();
                        getMvpView().initAdapter();
                        currentPage++;
                    } catch (Exception e) {
                        getMvpView().showContentView(Constant.VIEW_TYPE_1);
                    }

                } else {
                    getMvpView().showContentView(Constant.VIEW_TYPE_1);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().showContentView(Constant.VIEW_TYPE_1);
            }
        });
    }

    @Override
    public void onError(Exception e) {
        super.onError(e);
        getMvpView().showContentView(Constant.VIEW_TYPE_1);
    }

    public void setOnItemClick(int position) {
        SharedPrefHelper.getInstance().setExamTag(Constants.EXAM_TAG_ABILITY);
        SharedPrefHelper.getInstance().setExaminationId(KnowledgeList.get(position).getExaminationId());
        
        Intent intent = new Intent(getMvpView().context(), ExamActivity.class);
        intent.putExtra("examinationId", KnowledgeList.get(position).getExaminationId());
        getMvpView().context().startActivity(intent);
    }
    
}
