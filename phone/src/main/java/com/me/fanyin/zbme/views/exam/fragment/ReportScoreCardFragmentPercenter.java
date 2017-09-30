package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.ExamActivity;
import com.me.fanyin.zbme.views.exam.adapter.ScoreCardAdapter;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.view.ExamType;
import com.me.fanyin.zbme.views.exam.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyc on 2016/5/6.
 */
public class ReportScoreCardFragmentPercenter extends BasePersenter<ReportScoreCardFragmentView> {
    public Map<Integer, List<Question>> map;
    public List<ExamType> mExamTypeList;
    public List<ArrayList<Question>> lists = new ArrayList<>();
    private int tag = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bundle bundle = msg.getData();
                int index = bundle.getInt("index");
                int position = bundle.getInt("position");
                int totalPosition = bundle.getInt("totalPosition");
                onItemClick(index, position, totalPosition);
            }
        }
    };
    private String typeId;
    private String examId;
    private String examinationId;
    private String subjectId;

    @Override
    public void getData() {
        getIntentData();
        mExamTypeList = new ArrayList<ExamType>();
        map = new HashMap<Integer, List<Question>>();
        List<Question> allQuestion=new ArrayList<>();
        if (getMvpView().getAppContext().getAllList()==null){
            return;
        }
        for (int i = 0; i <getMvpView().getAppContext().getAllList().size() ; i++) {
            if (getMvpView().getAppContext().getAllList().get(i).getQuestionList()==null ||getMvpView().getAppContext().getAllList().get(i).getQuestionList().size()==0){
                allQuestion.add(getMvpView().getAppContext().getAllList().get(i));
            }else{
                for (int j=0;j<getMvpView().getAppContext().getAllList().get(i).getQuestionList().size();j++){
                    allQuestion.add(getMvpView().getAppContext().getAllList().get(i).getQuestionList().get(j));
                }
            }
        }
        for (Question question : allQuestion) {
            if ("".equals(question.getGroupId())) {//非题冒题
                if (map.containsKey(question.getChoiceType())) {
                    map.get(question.getChoiceType()).add(question);
                } else {
                    List<Question> questions = new ArrayList<Question>();
                    questions.add(question);
                    map.put(question.getChoiceType(), questions);
                    ExamType examType = new ExamType();
                    examType.setId(question.getChoiceType());
                    examType.setName(ExamTypeEnum.getValue(question.getChoiceType()));
                    mExamTypeList.add(examType);
                }
            } else {
                if (question.getChoiceType()!=0){
                    if (map.containsKey(question.getChoiceType())) {
                        map.get(question.getChoiceType()).add(question);
                    } else {
                        List<Question> questions = new ArrayList<Question>();
                        questions.add(question);
                        map.put(question.getChoiceType(), questions);
                        ExamType examType = new ExamType();
                        examType.setId(question.getChoiceType());
                        examType.setName(ExamTypeEnum.getValue(question.getChoiceType()));
                        mExamTypeList.add(examType);
                    }
                }else{

                    if (map.containsKey(ExamTypeEnum.EXAM_TYPE_TIMAOTI.getId())) {
                        map.get(ExamTypeEnum.EXAM_TYPE_TIMAOTI.getId()).add(question);
                    } else {
                        List<Question> questions = new ArrayList<Question>();
                        questions.add(question);
                        map.put(ExamTypeEnum.EXAM_TYPE_TIMAOTI.getId(), questions);
                        ExamType examType = new ExamType();
                        examType.setId(ExamTypeEnum.EXAM_TYPE_TIMAOTI.getId());
                        examType.setName(ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getName());
                        mExamTypeList.add(examType);
                    }
                }
            }
        }
        int index = 0;
        for (ExamType examType : mExamTypeList) {
            if(getMvpView().context()==null){
                return;
            }
            View view = LayoutInflater.from(getMvpView().context()).inflate(R.layout.exam_test_item_scorecard_layout, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            NoScrollGridView gv = (NoScrollGridView) view.findViewById(R.id.gridview);
            title.setText(examType.getName());
            final ArrayList<Question> questions = (ArrayList<Question>) map.get(examType.getId());
            lists.add(questions);
            ScoreCardAdapter adapter = new ScoreCardAdapter(getMvpView().context(), (ArrayList<Question>) map.get(examType.getId()), 2, handler, index, allQuestion);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 30;
            view.setLayoutParams(layoutParams);
            getMvpView().setView(view);
            index++;
        } }

    private void onItemClick(int index, int position, int totalPosition) {
        getMvpView().getAppContext().setQuestionlist(getMvpView().getAppContext().getAllList());
        Intent intent = new Intent(getMvpView().context(), ExamActivity.class);
        SharedPrefHelper.getInstance().setExamTag(Constants.EXAM_TAG_REPORT);
        if (!"".equals(lists.get(index).get(position).getGroupId())) {
            for (int i = 0; i <getMvpView().getAppContext().getAllList().size() ; i++) {
                if (null!=getMvpView().getAppContext().getAllList().get(i).getQuestionList()&&getMvpView().getAppContext().getAllList().get(i).getQuestionList().size()!=0){
                    for (int j=0;j<getMvpView().getAppContext().getAllList().get(i).getQuestionList().size();j++){
                        if (getMvpView().getAppContext().getAllList().get(i).getQuestionList().get(j).getQuestionId().equals(lists.get(index).get(position).getQuestionId())){
                            intent.putExtra("fatherPosition",i);
                            intent.putExtra("childPosition",j);
                            intent.putExtra("compreQuestionId",getMvpView().getAppContext().getAllList().get(i).getQuestionId());
                            intent.putExtra("typeId", typeId);
                            intent.putExtra("examId", examId);
                            intent.putExtra("examinationId", examinationId);
                            intent.putExtra("subjectId", subjectId);
                            break;
                        }
                    }
                }
            }
        }else {
            int fatherPosition= getMvpView().getAppContext().getAllList().indexOf(lists.get(index).get(position));
            intent.putExtra("fatherPosition",fatherPosition);
            intent.putExtra("childPosition", 0);
            intent.putExtra("typeId", typeId);
            intent.putExtra("examId", examId);
            intent.putExtra("examinationId", examinationId);
            intent.putExtra("subjectId", subjectId);
        }
        getMvpView().context().startActivity(intent);

    }

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

    @Override
    public void setData(String obj) {

    }
}
