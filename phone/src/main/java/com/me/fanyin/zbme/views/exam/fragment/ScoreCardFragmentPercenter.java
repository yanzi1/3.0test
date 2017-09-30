package com.me.fanyin.zbme.views.exam.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.adapter.ScoreCardAdapter;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.event.Comprehensive;
import com.me.fanyin.zbme.views.exam.event.ExamIndexEvent;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.view.ExamType;
import com.me.fanyin.zbme.views.exam.view.NoScrollGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyc on 2016/5/6.
 */
public class ScoreCardFragmentPercenter extends BasePersenter<ScoreCardFragmentView> {
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
    
    @Override
    public void getData() {
        mExamTypeList = new ArrayList<ExamType>();
        map = new HashMap<Integer, List<Question>>();
        tag = getMvpView().getArgumentData().getInt(Constants.ARG_POSITION);
        for (Question question : ExamPersenter.totallist) {
            if ("".equals(question.getGroupId())) { 
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
            ScoreCardAdapter adapter = new ScoreCardAdapter(getMvpView().context(), (ArrayList<Question>) map.get(examType.getId()), tag, handler, index, ExamPersenter.totallist);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (tag == 1) {  
                        getMvpView().finishActivity();
                    }
                    if (questions.get(position).getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {//题冒题
                        for (int i = 0; i < ExamPersenter.questionlist.size(); i++) {
                            if (null != ExamPersenter.questionlist.get(i).getQuestionList() && ExamPersenter.questionlist.get(i).getQuestionList().size() != 0) {
                                for (int j = 0; j < ExamPersenter.questionlist.get(i).getQuestionList().size(); j++) {
                                    if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId().equals(questions.get(position).getQuestionId())) {
                                        EventBus.getDefault().post(new ExamIndexEvent(i, 1, 0));
                                        EventBus.getDefault().post(new Comprehensive(j,ExamPersenter.questionlist.get(i).getQuestionId()));
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        EventBus.getDefault().post(new ExamIndexEvent(ExamPersenter.questionlist.indexOf(questions.get(position)), 1));
                    }
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 30;
            view.setLayoutParams(layoutParams);
            getMvpView().setView(view);
            index++;
        } }

    private void onItemClick(int index, int position, int totalPosition) {
        if (tag == 1) {  
            getMvpView().finishActivity();
        }
        if (!"".equals(lists.get(index).get(position).getGroupId())) {//题冒题
            for (int i = 0; i < ExamPersenter.questionlist.size(); i++) {
                if (null != ExamPersenter.questionlist.get(i).getQuestionList() && ExamPersenter.questionlist.get(i).getQuestionList().size() != 0) {
                    for (int j = 0; j < ExamPersenter.questionlist.get(i).getQuestionList().size(); j++) {
                        if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId().equals(lists.get(index).get(position).getQuestionId())) {
                            EventBus.getDefault().post(new ExamIndexEvent(i, 1, totalPosition));
                            EventBus.getDefault().post(new Comprehensive(j,ExamPersenter.questionlist.get(i).getQuestionId()));
                            break;
                        }
                    }
                }
            }
        } else {
            EventBus.getDefault().post(new ExamIndexEvent(ExamPersenter.questionlist.indexOf(lists.get(index).get(position)), 1));
        }
    }
    
    

    @Override
    public void setData(String obj) {

    }
}
