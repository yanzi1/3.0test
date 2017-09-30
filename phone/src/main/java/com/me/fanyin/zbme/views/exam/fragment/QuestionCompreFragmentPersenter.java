package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.me.core.utils.DensityUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.Option;
import com.me.data.model.exam.Question;
import com.me.data.model.exam.RelevantPoint;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.event.ExamIndexEvent;
import com.me.fanyin.zbme.views.exam.event.ShowComprehensiveAnalyzeEvent;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/5/4.
 */
public class QuestionCompreFragmentPersenter extends BasePersenter<QuestionCompreFragmentView> {
    public Map<Integer, Integer> heightList=new HashMap<>();
    public Question question;
    private int buttonHeight;
    private int exam_tag;
    private Intent scardIntent;
    private boolean isShowWebView=false;
    public String[] optionJudge = {"对", "错"};
    public int[] optionAnswer = {1, 2};
    public String[] optionChoice = {"A", "B", "C", "D", "E", "F", "G"};
    private int lvHeight=0;
    public static ArrayList<Question> questions = new ArrayList<>();
    private StringBuffer sb;
    public int index=0;

    @Override
    public void getData() {
        buttonHeight= DensityUtils.dip2px(getMvpView().context(), 20);
        question= (Question) getMvpView().getArgumentData().getSerializable(Constants.ARG_POSITION);
        initData();

    }

    private void initData() { 
        if (question.getPointList()==null||question.getPointList().size()==0){
            question.setPointList(new ArrayList<RelevantPoint>());
        }
        if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()){
            if (question.getOptionList().size() <= 1){
                List<Option> opList = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    Option option = new Option();
                    option.setOptionContent(optionJudge[i]);
                    option.setName(optionAnswer[i] + "");
                    opList.add(option);
                }
                question.setOptionList(opList);
            }
        }
        exam_tag = SharedPrefHelper.getInstance().getExamTag();
        judgeHasSolutions();
        if (question.getOptionList() != null && question.getOptionList().size() != 0) {
            for (int i = 0; i < question.getOptionList().size(); i++) {
                if (question.getOptionList().get(i).getShowWebView() != null && question.getOptionList().get(i).getShowWebView().equals("1")) {
                    isShowWebView = true;
                }
            }
        }
        getMvpView().setNSListviewIsWebview(isShowWebView);
        if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {
            getMvpView().setRealAnswer(true, true, optionJudge[Integer.valueOf(question.getRealAnswer().toString().trim()) - 1] + "");
            if (question.getUserAnswer() != null && !"".equals(question.getUserAnswer())) {
                getMvpView().setLocalAnswer(optionJudge[Integer.valueOf(question.getUserAnswer().toString().trim()) - 1] + "");
            }else{
                getMvpView().setLocalAnswer("");
            }
        } else {
            boolean isNormalChoiceType = (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()
                    || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId());
            boolean isShowHtmlTextview = !(question.getRealAnswer().contains("</td>") || question.getRealAnswer().contains("<img"));
            getMvpView().setRealAnswer(isNormalChoiceType, isShowHtmlTextview, question.getRealAnswer());
            if (question.getUserAnswer() != null) {
                getMvpView().setLocalAnswer(question.getUserAnswer() + "");
            }else{
                getMvpView().setLocalAnswer("");
            }
        }
        boolean hasAnalyze=!(question.getQuizAnalyze()==null ||"".equals(question.getQuizAnalyze()));
        getMvpView().showAnalyze(hasAnalyze);
        if (hasAnalyze){ 
            boolean analyzeIsWebview = question.getQuizAnalyze().contains("</td>") || question.getQuizAnalyze().contains("<img");
            getMvpView().setAnalyzeIsWebview(analyzeIsWebview, question.getQuizAnalyze());
        }
        boolean showRelevant = !(question.getPointList() == null || question.getPointList().size() == 0);
        getMvpView().showRlevantPoint(showRelevant);
        judgeTypeForQuestion();
    }
    private void judgeTypeForQuestion() {
        boolean questionTypeIsNormal=(question.getQuestionList() == null || question.getQuestionList().size() == 0);
        getMvpView().showNormalQuestionOrNot(questionTypeIsNormal);
        boolean titleIsWebview=(question.getTitle().contains("</td>")||question.getTitle().contains("<img"));
        if (questionTypeIsNormal){ 
            hideAnalyze(); 
            getMvpView().setQuestionTitleName(titleIsWebview,question.getTitle());
            getMvpView().setQuestionTypeName(ExamTypeEnum.getValue(question.getChoiceType()));
        }
    }

    public void hideAnalyze() {
        if (exam_tag == Constants.EXAM_TAG_REPORT) {
            getMvpView().setOptionListViewIsEnable(false);
            getMvpView().setShowAllAnalyze(true);
            getMvpView().setNotNormalTipShow(false);

        } else if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {
            getMvpView().setOptionListViewIsEnable(true);
            if (questions.contains(question)) {
                getMvpView().setShowAllAnalyze(true);
                getMvpView().setNotNormalTipShow(false);
            } else {
                getMvpView().setShowAllAnalyze(false);
                if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId() ||question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()
                        ||question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() ||question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()){
                    getMvpView().setNotNormalTipShow(false);
                }else{
                    getMvpView().setNotNormalTipShow(true);
                }
            }
        } else if (exam_tag == Constants.EXAM_ORIGINAL_QUESTION) { 
            getMvpView().setOptionListViewIsEnable(true);
            getMvpView().setShowAllAnalyze(true);
            getMvpView().setNotNormalTipShow(false);
            getMvpView().setRelavantAnswers(false);
        } else {
            getMvpView().setOptionListViewIsEnable(true);
        }
    }

    @Override
    public void setData(String obj) {
       
    }

    @Override
    public void attachView(QuestionCompreFragmentView mvpView) {
        super.attachView(mvpView);
    }
    
    public void getHeightChange(int height,int position ){
        if (heightList.size()<=4){
            if (heightList.size()==0&&question.getLvHeight()!=0){
                return;
            }
            if (heightList.get(position)!=null&&height==heightList.get(position)){
                return;
            }
            heightList.put(position, height);
            if (heightList.size()==4){
                setListViewHeightBasedOnChildren(getMvpView().getOptionListView());
            }
        }
    }

    private void judgeHasSolutions() {
        if (question.getSolutions()==null || question.getSolutions().isEmpty()){
            getMvpView().showSolutions(false,"");
        }else{
            getMvpView().showSolutions(true, question.getSolutions());
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int h = 0;
        for (Integer i : heightList.keySet()) {
            h+=heightList.get(i);
        }
        if (h> totalHeight){
            totalHeight=h;
        }
        totalHeight=totalHeight+140;
        lvHeight=totalHeight;
        if (question.getLvHeight()!=0){
            if (lvHeight==question.getLvHeight()){
            }else if(lvHeight<question.getLvHeight()){

                lvHeight=question.getLvHeight();
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = question.getLvHeight()+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))+50;
                listView.setLayoutParams(params);
            }else{
                question.setLvHeight(lvHeight);
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = question.getLvHeight()+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))+50;
                listView.setLayoutParams(params);
            }
        }else{
            if (heightList.size()!=4){
                return;
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))+50;

            listView.setLayoutParams(params);
        }

    }
    
    public void setListViewItemClick(int position){
        if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()) {//单选
            long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
            sb = new StringBuffer();
            for (int i = 0; i < ids.length; i++) {
                long id1 = ids[i];
                sb.append(optionChoice[(int) id1]).append("");
            }
            question.setUserAnswer(sb.toString() + "");
            getMvpView().refreshOptionAdapter();
        }else if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {//多选
            sb = new StringBuffer();
            long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
            for (int i = 0; i < ids.length; i++) {
                long id1 = ids[i];
                sb.append(optionChoice[(int) id1]).append(",");
            }
            if (sb.toString().equals("") || sb.toString().length() == 1 || sb.toString().length() == 0) {
                question.setUserAnswer("");
            } else {

                question.setUserAnswer(sb.toString().substring(0, sb.toString().length() - 1) + "");
            }
            getMvpView().refreshOptionAdapter();
        }else if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {//判断
            sb = new StringBuffer();
            long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
            for (int i = 0; i < ids.length; i++) {
                long id1 = ids[i];
                sb.append(optionAnswer[(int) id1]).append("");
            }
            question.setUserAnswer(sb.toString() + "");
            getMvpView().refreshOptionAdapter();
            EventBus.getDefault().post(new ExamIndexEvent(position, 2));
        }
    }
    
    
    public void onEventMainThreadShowAnalyzeEvent(ShowComprehensiveAnalyzeEvent event) {
            hideAnalyze();
    }

    public void onClickRelevantQuestion() {
//        Intent intent1=new Intent(getMvpView().context(), ExamRecommQuestionNewActivity.class);
//        intent1.putExtra("examinationQuestionId",question.getQuestionId());
//        Bundle intent = new Bundle();
//        String  examinationId=getMvpView().getTheIntent().getStringExtra("examinationId");
//        if (examinationId==null||examinationId.isEmpty()){
//            examinationId=SharedPrefHelper.getInstance().getExaminationId();
//        }
//        intent.putString("examinationId",examinationId);
//        intent.putString("paperName",SharedPrefHelper.getInstance().getExaminationTitle());
//        intent.putString("largeSegmentName",ExamTypeEnum.getValue(question.getChoiceType()));
//        intent.putString("subsectionName", CommenUtils.getPositionAtAll(question, ExamPersenter.questionlist)+"");
//        intent1.putExtras(intent);
//        getMvpView().context().startActivity(intent1);
    }

}
