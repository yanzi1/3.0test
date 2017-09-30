package com.me.fanyin.zbme.views.exam;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.AnswerLog;
import com.me.data.model.exam.ExamPaper;
import com.me.data.model.exam.ExamPaperLog;
import com.me.data.model.exam.Examination;
import com.me.data.model.exam.FaltQuestion;
import com.me.data.model.exam.Question;
import com.me.data.model.play.MyCollection;
import com.me.fanyin.zbme.views.course.play.db.MyCollectionDB;
import com.me.fanyin.zbme.views.exam.db.AnswerLogDB;
import com.me.fanyin.zbme.views.exam.db.ExamPaperDB;
import com.me.fanyin.zbme.views.exam.db.FaltQuestionDB;
import com.me.fanyin.zbme.views.exam.db.QuestionDB;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.event.Comprehensive;
import com.me.fanyin.zbme.views.exam.event.ComprehensiveUpdatePage;
import com.me.fanyin.zbme.views.exam.event.DeleteCompEvent;
import com.me.fanyin.zbme.views.exam.event.ExamIndexEvent;
import com.me.fanyin.zbme.views.exam.event.FlushScorecard;
import com.me.fanyin.zbme.views.exam.event.ShowAnalyzeEvent;
import com.me.fanyin.zbme.views.exam.event.ShowComprehensiveAnalyzeEvent;
import com.me.fanyin.zbme.views.exam.fragment.QuestionCompreFragmentPersenter;
import com.me.fanyin.zbme.views.exam.fragment.QuestionFragmentPersenter;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;
import com.me.fanyin.zbme.views.exam.remote.ApiClient;
import com.me.fanyin.zbme.views.exam.remote.ApiModel;
import com.me.fanyin.zbme.views.exam.remote.ParamsUtils;
import com.me.fanyin.zbme.views.exam.remote.ResultListener;
import com.me.fanyin.zbme.views.exam.remote.bean.BaseBean;
import com.me.fanyin.zbme.views.exam.utils.CommenUtils;
import com.me.fanyin.zbme.views.exam.utils.Constant;
import com.me.fanyin.zbme.views.exam.utils.NetUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamPersenter extends BasePersenter<ExamView> {
    public String[] optionChoice = {"A", "B", "C", "D", "E", "F", "G"};
    public int time = 0;
    private int exam_tag;
    private String userId;
    public String typeId;
    public String examId;
    public String examinationId;
    public String subjectId;
    private MyCollectionDB collectionDB;
    private QuestionDB questionDB;
    private FaltQuestionDB faltQuestionDB;
    private AnswerLogDB answerLogDB;
    private ExamPaperDB examPaperDB;
    private ArrayList<MyCollection> collectionList;
    private ArrayList<Question> listCollQuestion;
    private ArrayList<Question> showAnswerList;
    private ExamPaper examPaper;
    public static AnswerLog answerLog;
    public static List<Question> questionlist = new ArrayList<>();
    public static List<Question> totallist = new ArrayList<>();
    public static int currentIndex = 0;
    private ArrayList<Question> timaoList;
    Map<String, Integer> compMap = new HashMap<>();
    private int timaoIndext;
    private Examination examination;
    private boolean isPause = true;
    public boolean isShowAdvice=true;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!isPause) {
                        time++;
                        getMvpView().showExamTime(CommenUtils.getHour(time) + ":" + CommenUtils.getMin(time) + ":" + CommenUtils.getSec(time));
                    }
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }

        }
    };
    private int state;
    private String sectionId;
    private MaterialDialog alertDialog;

    @Override
    public void attachView(ExamView payWayView) {
        super.attachView(payWayView);
        initData();
    }


    private void initData() {
        exam_tag = SharedPrefHelper.getInstance().getExamTag();
        userId= SharedPrefHelper.getInstance().getUserId();
        examId = SharedPrefHelper.getInstance().getExamId();
        examinationId = SharedPrefHelper.getInstance().getExaminationId();
        subjectId = SharedPrefHelper.getInstance().getSubjectId();
        typeId= SharedPrefHelper.getInstance().getMainTypeId();
        collectionDB = new MyCollectionDB();
        questionDB = new QuestionDB(getMvpView().context());
        faltQuestionDB = new FaltQuestionDB(getMvpView().context());
        answerLogDB = new AnswerLogDB(getMvpView().context());
        examPaperDB = new ExamPaperDB(getMvpView().context());
        collectionList = new ArrayList<>();
        showAnswerList = new ArrayList<>();
    }

    private void getCollectionInExamination() {
        collectionList = new ArrayList<>();
        collectionList = (ArrayList<MyCollection>) collectionDB.findAllCollectionQuestion(userId, Constant.COLLECTION_TYPE_1, examinationId);
        if (collectionList == null) {
            collectionList = new ArrayList<>();
        }
        listCollQuestion = new ArrayList<>();
        for (int i = 0; i < collectionList.size(); i++) {
            Question question = JSON.parseObject(collectionList.get(i).getContent(), Question.class);
            if (question.getQuestionList() == null || question.getQuestionList().size() == 0) {

                listCollQuestion.add(question);
            } else {
                for (int j = 0; j < question.getQuestionList().size(); j++) {
                    question.getQuestionList().get(j).setGroupId(question.getQuestionId());
                    listCollQuestion.add(question.getQuestionList().get(j));
                }
            }
        }
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void getData() {
        sectionId = getMvpView().getWayIntent().getStringExtra("sectionId");
        if (sectionId == null) {
            sectionId = "";
        }
        getMvpView().showLoading();
        getMvpView().showTopTitleRight(true);
        getMvpView().progressStatus(Constants.VIEW_STATUS_INIT);
        if (exam_tag == Constants.EXAM_TAG_REPORT) {
            getReportData();
        } else if (exam_tag == Constants.EXAM_TAG_FALT) {
            getFaltData();
        } else if (exam_tag == Constants.EXAM_TAG_COLLECTION) {
            getCollectionData();
        } else if (exam_tag == Constants.EXAM_DO_CONTINUE) {
            getContinueData();
        } else if (exam_tag == Constants.EXAM_ORIGINAL_QUESTION) {
            SharedPrefHelper.getInstance().setExaminationTittle("答疑原题");
            getMvpView().showExaminationTittle("答疑原题");
            Intent intent = getMvpView().getWayIntent();
            String questionId = intent.getStringExtra("questionId");
            String subjectId = intent.getStringExtra("subjectId");
            if (subjectId==null||subjectId.isEmpty()){
                subjectId=SharedPrefHelper.getInstance().getSubjectId();
            }
            getOriginalQuestion(questionId,subjectId);
        } else {
            getNormalData();
        }
    }

    private void getOriginalQuestion(String questionId,String subjectId) {
        if (exam_tag== Constants.EXAM_ORIGINAL_QUESTION){
            getMvpView().showExamPagerBottom(false);
        }
        getMvpView().progressStatus(Constants.VIEW_STATUS_INIT);
        if (NetUtils.checkNet(getMvpView().context()).isAvailable()) {
            getOriginalQuestionData(ParamsUtils.getInstance(getMvpView().context()).getOriginalQuestion(questionId,subjectId));
        } else {
            getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_NET);
        }
    }

    private void getContinueData() {
        getCollectionInExamination();
        typeId = SharedPrefHelper.getInstance().getMainTypeId();
        AnswerLog answerLog = null;
        if (typeId == null || typeId.isEmpty()) {
            answerLog = answerLogDB.findLastAnswerLog(userId, examId, subjectId);
        } else {
            answerLog = answerLogDB.find(userId, examId, subjectId, typeId);
        }
        if (answerLog.getExaminationTitle()!=null){
            SharedPrefHelper.getInstance().setExaminationTittle(answerLog.getExaminationTitle());
        }else{
            SharedPrefHelper.getInstance().setExaminationTittle("");
        }
        examinationId=answerLog.getExaminationId();
        SharedPrefHelper.getInstance().setExaminationId(examinationId);
        if (answerLog.isFinished()) {
            ExamPaperLog examPaperLog = examPaperDB.findByExaminationId(userId, examId, subjectId, answerLog.getExaminationId());
            if (examPaperLog==null){
                getDataAgain();
                return;
            }
            examPaper = JSON.parseObject(examPaperLog.getContent(), ExamPaper.class);
            if (typeId.isEmpty()&&examPaperLog.getTypeId()!=null&&!examPaperLog.getTypeId().isEmpty()){
                typeId=examPaperLog.getTypeId();
                SharedPrefHelper.getInstance().setMainTypeId(typeId);
            }
            questionlist = examPaper.getQuestionList();
            getTotalList();
            getMvpView().updateQuestionItemAdapter();
            time = 0;
            getMvpView().showCurrentNumber(1);
        } else {
            if (answerLog.getContent() == null || answerLog.getContent().isEmpty()) {
                if (answerLog.getExaminationId()!=null&&!answerLog.getExaminationId().isEmpty()){
                    examinationId=answerLog.getExaminationId();
                    SharedPrefHelper.getInstance().setExaminationId(answerLog.getExaminationId());
                    getOldData(answerLog.getExaminationId());
                }
                return;
            }
            examPaper = JSON.parseObject(answerLog.getContent(), ExamPaper.class);
            if (typeId.isEmpty()&&answerLog.getTypeId()!=null&&!answerLog.getTypeId().isEmpty()){
                typeId=answerLog.getTypeId();
                SharedPrefHelper.getInstance().setMainTypeId(typeId);
            }
            questionlist = examPaper.getQuestionList();
            getTotalList();
            getMvpView().updateQuestionItemAdapter();
            time = answerLog.getUsedTime();
            int fatherIndex = answerLog.getCurrentIndex();
            int childIndex = answerLog.getChildIndex();
            if (questionlist.get(fatherIndex).getQuestionList() == null || questionlist.get(fatherIndex).getQuestionList().size() == 0) {
                getMvpView().getViewPager().setCurrentItem(fatherIndex);
                getMvpView().showCurrentNumber(fatherIndex + 1);
            } else {
                getMvpView().getViewPager().setCurrentItem(fatherIndex);
                EventBus.getDefault().post(new Comprehensive(childIndex, questionlist.get(fatherIndex).getQuestionId()));
                for (int i = 0; i < totallist.size(); i++) {
                    if (totallist.get(i).getQuestionId().equals(questionlist.get(fatherIndex).getQuestionList().get(childIndex).getQuestionId())) {
                        getMvpView().showCurrentNumber((i + 1));
                    }
                }
            }
        }
        prepareData();
    }

    private void getCollectionData() {
        questionlist.clear();
        SharedPrefHelper.getInstance().setExaminationTittle("我的收藏");
        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());
        getMvpView().showExamBottomRight(exam_tag);
        getMvpView().showBottomTittle(exam_tag);
        Intent intent = getMvpView().getWayIntent();
        String questionId = intent.getStringExtra("questionId");
        List<MyCollection> collectionList1 = collectionDB.findCollectionQuestion(userId, questionId, Constant.COLLECTION_TYPE_1);
        if (collectionList1.get(0).getContent() == null || collectionList1.get(0).getContent().isEmpty()) {
//            getOriginalQuestion(collectionList1.get(0).getCollectionId());
            return;
        }
        String tittle="";
        List<Question> lists = new ArrayList<>();
        for (int i = 0; i < collectionList1.size(); i++) {
            Question question = JSON.parseObject(collectionList1.get(i).getContent(), Question.class);
            lists.add(question);
        }
        if (lists.size()>0) {
            tittle=lists.get(0).getExaminationName();
            SharedPrefHelper.getInstance().setExaminationTittle(tittle);
        }
        questionlist = lists;
        getTotalList();
        getMvpView().updateQuestionItemAdapter();
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);
        getMvpView().showCurrentNumber(1);
        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());

        getMvpView().showDuoxuanGuide(false);
        isShowAdvice=false;
    }

    private void getFaltData() {
        questionlist.clear();
        SharedPrefHelper.getInstance().setExaminationTittle("我的错题");
        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());
        getMvpView().showExamBottomRight(exam_tag);
        getMvpView().showBottomTittle(exam_tag);
        Intent intent = getMvpView().getWayIntent();
        examinationId = intent.getStringExtra("examinationId");
        SharedPrefHelper.getInstance().setExaminationId(examinationId);
        int choiceType = intent.getIntExtra(Constant.EXAM_TYPE, 0);
        choiceType = 1;
        List<FaltQuestion> faltList = faltQuestionDB.findAllByExaminationId(userId, examId, subjectId, examinationId);
        List<Question> lists = new ArrayList<>();
        String title="";
        if (faltList.size()>0){
            title=faltList.get(0).getExaminationName();
        }
        SharedPrefHelper.getInstance().setExaminationTittle(title);
        for (int i = 0; i < faltList.size(); i++) {
            Question question = JSON.parseObject(faltList.get(i).getContent(), Question.class);
            lists.add(question);
        }
        questionlist = lists;
        getTotalList();
        getMvpView().updateQuestionItemAdapter();
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);
        getMvpView().showCurrentNumber(1);
//        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());
        getMvpView().showDuoxuanGuide(false);
        isShowAdvice=false;
    }

    private void getNormalData() {
        getIntentData();
        getCollectionInExamination();
        AnswerLog answerLog = answerLogDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
        state = 0;
        if (answerLog != null && !answerLog.isFinished()) {
            state = Constants.EXAM_KNOWLEDGE_CONTINU;
        }else{
            state = 0;
        }
        if (state == Constants.EXAM_KNOWLEDGE_CONTINU) {
            if (answerLog.getContent() == null || answerLog.getContent().isEmpty()) {
                getOldData(answerLog.getExaminationId());
                return;
            }
            SharedPrefHelper.getInstance().setExaminationTittle(answerLog.getExaminationTitle());
            examPaper = JSON.parseObject(answerLog.getContent(), ExamPaper.class);
            questionlist = examPaper.getQuestionList();
            getTotalList();
            getMvpView().updateQuestionItemAdapter();
            time = answerLog.getUsedTime();
            int fatherIndex = answerLog.getCurrentIndex();
            int childIndex = answerLog.getChildIndex();
            if (questionlist.get(fatherIndex).getQuestionList() == null || questionlist.get(fatherIndex).getQuestionList().size() == 0) {
                getMvpView().getViewPager().setCurrentItem(fatherIndex);
//                getMvpView().showCurrentNumber(fatherIndex + 1);
                getMvpView().showCurrentNumber(CommenUtils.getPositionAtAll(questionlist.get(fatherIndex),totallist));
            } else {
                getMvpView().getViewPager().setCurrentItem(fatherIndex);
                EventBus.getDefault().post(new Comprehensive(childIndex, questionlist.get(fatherIndex).getQuestionId()));
                for (int i = 0; i < totallist.size(); i++) {
                    if (totallist.get(i).getQuestionId().equals(questionlist.get(fatherIndex).getQuestionList().get(childIndex).getQuestionId())) {
                        getMvpView().showCurrentNumber((i + 1));
                    }
                }
            }
            prepareData();
        } else {
            if (NetUtils.checkNet(getMvpView().context()).isAvailable()) {
                if (exam_tag == Constants.EXAM_TAG_EVERY_YEAR) {
                    getDataAgain();
                } else {
                    getDataAgain();
                }
            } else {
                ExamPaperLog examPaperLog = examPaperDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
                if ( null == examPaperLog ||examPaperLog.getContent()==null) {
                    getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_NET);
                } else {
                    examPaper = JSON.parseObject(examPaperLog.getContent(), ExamPaper.class);
                    SharedPrefHelper.getInstance().setExaminationTittle(examPaper.getExamination().getExaminationName());
                    questionlist = examPaper.getQuestionList();
                    getTotalList();
                    getMvpView().updateQuestionItemAdapter();
                    getMvpView().showCurrentNumber(1);
                    prepareData();
                }
            }
        }
    }
    private void getIntentData() {
        Intent intent =getMvpView().getWayIntent();
        typeId=intent.getStringExtra("typeId");
        if (typeId==null||typeId.isEmpty()){
            typeId= SharedPrefHelper.getInstance().getMainTypeId();
        }
        examId=intent.getStringExtra("examId");
        if (examId==null||examId.isEmpty()){
            examId= SharedPrefHelper.getInstance().getExamId();
        }
        examinationId=intent.getStringExtra("examinationId");
        if (examinationId==null||examinationId.isEmpty()){
            examinationId= SharedPrefHelper.getInstance().getExaminationId();
        }else{
            SharedPrefHelper.getInstance().setExaminationId(examinationId);
        }
        subjectId=intent.getStringExtra("subjectId");
        if (subjectId==null||subjectId.isEmpty()){
            subjectId= SharedPrefHelper.getInstance().getSubjectId();
        }
    }

    private void getOldData(String examinationId) {
        getOldHistoryData(ParamsUtils.getInstance(getMvpView().context()).getExamPaper(examinationId));
    }

    public void prepareData(){
        boolean rember= SharedPrefHelper.getInstance().getIsfirstInDuoxuan();
        if (rember){
            if (isShowAdvice){
                int cPosition=getMvpView().getViewPager().getCurrentItem();
                if (questionlist.size()>cPosition){
                    if (questionlist.get(cPosition).getChoiceType()== ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId() ||questionlist.get(cPosition).getChoiceType()== ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()){
                        getMvpView().showDuoxuanGuide(true);
                    }
                }
            }
        }
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);
        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());
        changListener();
        startCounter();
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void getOldHistoryData(HashMap<String, String> params) {
        Call<String> call = ApiClient.getClient().getExamPaper(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String str = response.body();
                    try {
                        getMvpView().hideLoading();
                        BaseBean baseBean = JSON.parseObject(str, BaseBean.class);
                        if (baseBean == null) {
                            getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_OTHER);
                            questionlist.clear();
                            return;
                        } else {
                            int result = baseBean.getCode();
                            if (result != 1000) {
                                getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                                questionlist.clear();
                                return;
                            }
                        }
                        AnswerLog answerLog;
                        if (exam_tag == Constants.EXAM_DO_CONTINUE) {
                            typeId = SharedPrefHelper.getInstance().getMainTypeId();
                            if (typeId == null || typeId.isEmpty()) {
                                answerLog = answerLogDB.findLastAnswerLog(userId, examId, subjectId);
                            } else {
                                answerLog = answerLogDB.find(userId, examId, subjectId, typeId);
                            }
                        } else {
                            answerLog = answerLogDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
                        }
                        if (typeId.isEmpty() && answerLog.getTypeId() != null && !answerLog.getTypeId().isEmpty()) {
                            typeId = answerLog.getTypeId();
                            SharedPrefHelper.getInstance().setMainTypeId(typeId);
                        }
                        examPaper = JSON.parseObject(baseBean.getBody(), ExamPaper.class);
                        examination = examPaper.getExamination();
                        List<Question> interList = examPaper.getQuestionList();
                        if (interList.size() == 0) {
                            getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                            return;
                        }
                        answerLogDB.deleteByExamination(answerLog);
                        examPaper.setQuestionList(interList);
                        answerLog.setContent(JSON.toJSONString(examPaper));
                        answerLogDB.insert(answerLog);
                        ExamPaperLog examPaperLog = examPaperDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
                        ExamPaperLog newExamPaper = new ExamPaperLog(userId, examId, subjectId, typeId, sectionId, examinationId, baseBean.getBody());
                        if (null != examPaperLog) {
                            examPaperDB.deleteByExamPaperLog(examPaperLog);
                        }
                        examPaperDB.insert(newExamPaper);
                        questionlist = interList;
                        getTotalList();
                        getMvpView().updateQuestionItemAdapter();
                        getMvpView().updateQuestionItemAdapter();
                        time = answerLog.getUsedTime();
                        int fatherIndex = answerLog.getCurrentIndex();
                        int childIndex = answerLog.getChildIndex();
                        if (questionlist.get(fatherIndex).getQuestionList() == null || questionlist.get(fatherIndex).getQuestionList().size() == 0) {
                            getMvpView().getViewPager().setCurrentItem(fatherIndex);
                            getMvpView().showCurrentNumber(fatherIndex + 1);
                        } else {
                            getMvpView().getViewPager().setCurrentItem(fatherIndex);
                            EventBus.getDefault().post(new Comprehensive(childIndex, questionlist.get(fatherIndex).getQuestionId()));
                            for (int i = 0; i < totallist.size(); i++) {
                                if (totallist.get(i).getQuestionId().equals(questionlist.get(fatherIndex).getQuestionList().get(childIndex).getQuestionId())) {
                                    getMvpView().showCurrentNumber((i + 1));
                                }
                            }
                        }
                        prepareData();
                    } catch (Exception e) {
                    }
                } else {
                    getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
            }
        });
    }

    private void getOriginalQuestionData(HashMap<String, String> params) {
        Call<String> call = ApiClient.getClient().getOriginalQuestion(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String str = response.body();
                    try {
                        questionlist.clear();
                        getMvpView().hideLoading();
                        BaseBean baseBean = JSON.parseObject(str, BaseBean.class);
                        if (baseBean == null) {
                            getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_OTHER);
                            return;
                        }
                        int result = baseBean.getCode();
                        if (result != 1000) {
                            getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                            return;
                        }
                        if (exam_tag != Constants.EXAM_ORIGINAL_QUESTION)  {
                            Intent intent = getMvpView().getWayIntent();
                            String questionId = intent.getStringExtra("questionId");
                            List<MyCollection> collectionList1 = collectionDB.findCollectionQuestion(userId, questionId, Constant.COLLECTION_TYPE_1);
                            MyCollection myCollection = collectionList1.get(0);
                            collectionDB.delete(myCollection);
                            myCollection.setContent(baseBean.getBody());
                            collectionDB.insert(myCollection);
                        }
                        Question question = JSON.parseObject(baseBean.getBody(), Question.class);
                        if (question==null){
                            getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                            return;
                        }
                        questionlist.add(question);
                        getTotalList();
                        getMvpView().updateQuestionItemAdapter();
                        getMvpView().showCurrentNumber(1);
                        getMvpView().showTotalNumber("/" + totallist.size());
                        prepareData();

                    } catch (Exception e) {
                        getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_OTHER);
                    }

                } else {
                    getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
            }
        });
    }

    private void getReportData() {
        getCollectionInExamination();
        getMvpView().showBottomTittlePress(exam_tag);
        getMvpView().showExamTimeOrNot(false);
        if (getMvpView().getAppContext().getIsScanTakIn()) {
            examId = getMvpView().getAppContext().getScanExamId();
            subjectId = getMvpView().getAppContext().getScanSubjectId();
            examinationId = getMvpView().getAppContext().getScanExaminationId();
            collectionList = new ArrayList<>();
            collectionList = (ArrayList<MyCollection>) collectionDB.findAllCollectionQuestion(userId, Constant.COLLECTION_TYPE_1, examinationId);
            if (collectionList == null) {
                collectionList = new ArrayList<>();
            }
            listCollQuestion = new ArrayList<>();
            for (int i = 0; i < collectionList.size(); i++) {
                Question question = JSON.parseObject(collectionList.get(i).getContent(), Question.class);
                if (question.getQuestionList() == null || question.getQuestionList().size() == 0) {
                    listCollQuestion.add(question);
                } else {
                    for (int j = 0; j < question.getQuestionList().size(); j++) {
                        question.getQuestionList().get(j).setGroupId(question.getQuestionId());
                        listCollQuestion.add(question.getQuestionList().get(j));
                    }
                }
            }
        }
        questionlist = getMvpView().getAppContext().getQuestionlist();
        getTotalList();
        getMvpView().updateQuestionItemAdapter();
        getIntentData();
        Intent intent = getMvpView().getWayIntent();
        int fatherPossition = intent.getIntExtra("fatherPosition", 0);
        int childPossition = intent.getIntExtra("childPosition", 0);
        if (fatherPossition >= questionlist.size()) {
            fatherPossition = 0;
        }
        int cPosition;
        Question cQuestion=questionlist.get(fatherPossition);
        if (cQuestion.getQuestionList()==null||cQuestion.getQuestionList().size()==0){
            cPosition=totallist.indexOf(cQuestion);
        }else{
            cQuestion=cQuestion.getQuestionList().get(childPossition);
            cPosition=totallist.indexOf(cQuestion);
        }
        getMvpView().showCurrentNumber(cPosition + 1);
        compMap.put(questionlist.get(fatherPossition).getQuestionId(), childPossition);
        getMvpView().setVPIndex(fatherPossition);
        getMvpView().showTopTittle(SharedPrefHelper.getInstance().getExaminationTitle());
        changListener();
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);

        getMvpView().showDuoxuanGuide(false);
        isShowAdvice=false;

    }

    public void clickExaminationBottomCenter() {
        if (exam_tag == Constants.EXAM_TAG_ABILITY || exam_tag == Constants.EXAM_TAG_KNOWLEDGE || exam_tag == Constants.EXAM_TAG_HIGHFREQUENCY || exam_tag == Constants.EXAM_TAG_EVERY_YEAR || exam_tag == Constants.EXAM_TAG_REPORT || exam_tag == Constants.EXAM_TAG_CONTINU || exam_tag == Constants.EXAM_DO_CONTINUE) { //能力评估，历年真题，高频考点，知识点练习//答案解析
            judgeCollection();
            MobclickAgent.onEvent(getMvpView().context(),Constant.EXAM_COLLECT);
        } else if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {
            analysisQuestion();
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {
                if (QuestionFragmentPersenter.questions.contains(questionlist.get(getMvpView().getViewPager().getCurrentItem()))) {
                    QuestionFragmentPersenter.questions.remove(questionlist.get(getMvpView().getViewPager().getCurrentItem()));
                    EventBus.getDefault().post(new ShowAnalyzeEvent(false));
                } else {
                    QuestionFragmentPersenter.questions.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()));
                    EventBus.getDefault().post(new ShowAnalyzeEvent(true));
                }
            } else {
                if (QuestionCompreFragmentPersenter.questions.contains(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())))) {
                    QuestionCompreFragmentPersenter.questions.remove(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())));
                    EventBus.getDefault().post(new ShowComprehensiveAnalyzeEvent(false));
                } else {
                    QuestionCompreFragmentPersenter.questions.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())));
                    EventBus.getDefault().post(new ShowComprehensiveAnalyzeEvent(true));
                }
            }

        }
    }

    public void clickExaminationBottomRight() {
        if(exam_tag == Constants.EXAM_TAG_COLLECTION||exam_tag == Constants.EXAM_TAG_FALT) {

        }else{
            return;
        }
        if (alertDialog!=null){
            alertDialog.show();
            return;
        }
        alertDialog = new MaterialDialog(getMvpView().context());
        alertDialog.setTitle("提示");
        if (exam_tag == Constants.EXAM_TAG_COLLECTION) {
            alertDialog.setMessage("是否确定取消收藏吗？");
        } else if (exam_tag == Constants.EXAM_TAG_FALT) {
            alertDialog.setMessage("是否确定删除？");
        }
        alertDialog.setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exam_tag == Constants.EXAM_TAG_COLLECTION) {
                    deleteCollection();
                } else if (exam_tag == Constants.EXAM_TAG_FALT) {
                    deleteFaltQuestion();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void setData(String str) {
        try {
            questionlist.clear();
            getMvpView().hideLoading();
            BaseBean baseBean = JSON.parseObject(str, BaseBean.class);
            if (baseBean == null) {
                getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_OTHER);
                questionlist.clear();
                return;
            } else {
                int result = baseBean.getCode();
                if (result != 1000) {
                    getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                    questionlist.clear();
                    return;
                }
            }
            examPaper = JSON.parseObject(baseBean.getBody(), ExamPaper.class);
            examination = examPaper.getExamination();
            List<Question> interList = examPaper.getQuestionList();
            if (interList.size()==0){
                getMvpView().progressStatus(Constants.VIEW_STATUS_EMPTY);
                return;
            }
            ExamPaperLog examPaperLog = examPaperDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
            if (sectionId == null) {
                sectionId = "";
            }
            ExamPaperLog newExamPaper = new ExamPaperLog(userId, examId, subjectId, typeId, sectionId, examinationId, baseBean.getBody());
            if (null != examPaperLog) {
                examPaperDB.deleteByExamPaperLog(examPaperLog);
            }
            examPaperDB.insert(newExamPaper);
            questionlist = interList;
            getTotalList();
            getMvpView().updateQuestionItemAdapter();
            SharedPrefHelper.getInstance().setExaminationTittle(examination.getExaminationName());
            getMvpView().showCurrentNumber(1);
            getMvpView().showTotalNumber("/" + totallist.size());
            //监控当前的试题是否已经被收藏
            prepareData();
        } catch (Exception e) {
            getMvpView().showError("获取数据失败");
        }
    }
    private void analysisQuestion() {
        if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {
            //先判断是否是题冒题
            getMvpView().showBottomTittle(exam_tag);
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {//不是题冒题
                if (showAnswerList.size() == 0) {
                    showAnswerList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()));
                    getMvpView().showBottomTittlePress(exam_tag);
                } else {
                    boolean isNoShow = true;
                    for (int i = 0; i < showAnswerList.size(); i++) {
                        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId().equals(showAnswerList.get(i).getQuestionId())) {
                            showAnswerList.remove(i);
                            isNoShow = false;
                            break;
                        }
                    }
                    if (isNoShow) {
                        showAnswerList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()));
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            } else {
                if (compMap.containsKey(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                    timaoIndext = compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                } else {
                    timaoIndext = 0;
                    compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
                }
                if (showAnswerList.size() == 0) {
                    showAnswerList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(timaoIndext));
                    getMvpView().showBottomTittlePress(exam_tag);
                } else {
                    boolean isNoShow = true;
                    for (int i = 0; i < showAnswerList.size(); i++) {
                        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(timaoIndext).getQuestionId().equals(showAnswerList.get(i).getQuestionId())) {
                            showAnswerList.remove(i);
                            isNoShow = false;
                            break;
                        }
                    }
                    if (isNoShow) {
                        showAnswerList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(timaoIndext));
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            }
        }
    }
    private void getTotalList() {
        timaoList = new ArrayList<>();
        totallist = new ArrayList<>();
        if (questionlist==null){
            questionlist=new ArrayList<>();
        }
        for (int i = 0; i < questionlist.size(); i++) {
            if (null != questionlist.get(i).getQuestionList() && questionlist.get(i).getQuestionList().size() != 0) {//是题冒题
                timaoList.add(questionlist.get(i));
                for (int j = 0; j < questionlist.get(i).getQuestionList().size(); j++) {
                    questionlist.get(i).getQuestionList().get(j).setGroupId(questionlist.get(i).getQuestionId());
                    questionlist.get(i).getQuestionList().get(j).setExaminationId(examinationId);
                    totallist.add(questionlist.get(i).getQuestionList().get(j));
                }
            } else {
                questionlist.get(i).setExaminationId(examinationId);
                totallist.add(questionlist.get(i));
            }
        }
        getMvpView().showTotalNumber("/" + totallist.size());
    }

    public void changListener() {
        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()) == null) {
            return;
        }
        if (exam_tag == Constants.EXAM_TAG_ABILITY || exam_tag == Constants.EXAM_TAG_KNOWLEDGE || exam_tag == Constants.EXAM_TAG_HIGHFREQUENCY || exam_tag == Constants.EXAM_TAG_EVERY_YEAR || exam_tag == Constants.EXAM_TAG_REPORT || exam_tag == Constants.EXAM_TAG_CONTINU  ||exam_tag == Constants.EXAM_DO_CONTINUE) {
            getMvpView().showBottomTittle(exam_tag);
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {//不是题冒题
                for (int i = 0; i < listCollQuestion.size(); i++) {
                    if (listCollQuestion.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            } else {
                if (compMap.containsKey(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                    timaoIndext = compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                } else {
                    timaoIndext = 0;
                    compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
                }

                for (int i = 0; i < listCollQuestion.size(); i++) {
                    if (listCollQuestion.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())).getQuestionId())) {
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            }

        } else if (exam_tag == Constants.EXAM_TAG_FALT || exam_tag == Constants.EXAM_TAG_COLLECTION) {
            getMvpView().showBottomTittle(exam_tag);
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {//不是题冒题
                for (int i = 0; i < showAnswerList.size(); i++) {
                    if (showAnswerList.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            } else {
                if (compMap.containsKey(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                    timaoIndext = compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                } else {
                    timaoIndext = 0;
                    compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
                }
                for (int i = 0; i < showAnswerList.size(); i++) {
                    if (showAnswerList.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())).getQuestionId())) {
                        getMvpView().showBottomTittlePress(exam_tag);
                    }
                }
            }
        }
    }

    public void getDataAgain() {
        ApiModel apiModel = new ApiModel(new ResultListener() {
            @Override
            public void onSuccess(String json) {
            }

            @Override
            public void onError(Exception e) {
            }
        });
        checkNetwork();
        userId = SharedPrefHelper.getInstance().getUserId();
        examinationId = SharedPrefHelper.getInstance().getExaminationId();
        if (exam_tag == Constants.EXAM_TAG_ABILITY) {
            if (state == Constants.EXAM_KNOWLEDGE_CONTINU) {
                AnswerLog answerLog = answerLogDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
                if (answerLog != null && answerLog.getContent() == null &&!answerLog.isFinished()) {
                    getOldData(answerLog.getExaminationId());
                    return;
                }
            }
            getIntentData();
            apiModel.getData(ApiClient.getClient().getExamPaper(ParamsUtils.getInstance(getMvpView().context()).getExamPaper(examinationId)));
        } else if (exam_tag == Constants.EXAM_TAG_EVERY_YEAR) {
            HashMap<String, String> params = new HashMap<>();
            apiModel.getData(ApiClient.getClient().getExamPaper(params));
        } else if (exam_tag == Constants.EXAM_ORIGINAL_QUESTION) {
            Intent intent = getMvpView().getWayIntent();
            String questionId = intent.getStringExtra("questionId");
            String subjectId = intent.getStringExtra("subjectId");
            getOriginalQuestion(questionId,subjectId);
        } else if (exam_tag == Constants.EXAM_TAG_COLLECTION) {
            getCollectionData();
        } else if (exam_tag == Constants.EXAM_DO_CONTINUE) {
            AnswerLog answerLog = answerLogDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
            if (answerLog != null && answerLog.getContent() == null &&!answerLog.isFinished()) {
                getOldData(answerLog.getExaminationId());
                return;
            }
            apiModel.getData(ApiClient.getClient().getExamPaper(ParamsUtils.getInstance(getMvpView().context()).getExamPaper(examinationId)));
        }
    }

    public void checkNetwork() {
        if (!NetWorkUtils.isNetworkAvailable(getMvpView().context())) {
            getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_NET);
        }
    }

    private void judgeCollection() {
        boolean isNoCollection = true;
        for (int i = 0; i < listCollQuestion.size(); i++) {
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {
                if (listCollQuestion.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                    isNoCollection = false;
                    break;
                }
            } else {
                if (listCollQuestion.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())).getQuestionId())) {
                    isNoCollection = false;
                    break;
                }
            }
        }
        if (isNoCollection) {
            getMvpView().showBottomTittlePress(exam_tag);
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {//非题冒题
                listCollQuestion.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()));
                MyCollection faltQuestion = new MyCollection();
                if (typeId==null||typeId.isEmpty()){
                    typeId="";
                }
                faltQuestion.setMainType(typeId);
                faltQuestion.setUserId(userId);
                String collectionId=subjectId+"_"+examinationId+"_"+questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                faltQuestion.setCollectionId(collectionId);
                faltQuestion.setQuestionId(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                faltQuestion.setTime(System.currentTimeMillis() + "");
                faltQuestion.setType(Constant.COLLECTION_TYPE_1);
                faltQuestion.setSearchKey("");
                questionlist.get(getMvpView().getViewPager().getCurrentItem()).setExaminationName(SharedPrefHelper.getInstance().getExaminationTitle());
                faltQuestion.setTitle(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getTitle());
                faltQuestion.setContent(JSON.toJSONString(questionlist.get(getMvpView().getViewPager().getCurrentItem()), SerializerFeature.DisableCircularReferenceDetect) + "");
                faltQuestion.setExamId(examId);
                faltQuestion.setSubjectId(subjectId);
                faltQuestion.setExaminationId(examinationId);
//                faltQuestion.setExaminationName(SharedPrefHelper.getInstance().getExaminationTitle());
                MyCollection oldCollection = collectionDB.findCollection(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                if (null != oldCollection) {
                    collectionDB.delete(oldCollection);
                }
                collectionDB.insert(faltQuestion);
            } else {
                Question ziQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId()));
                ziQuestion.setGroupId(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                if (null != ziQuestion) {
                    listCollQuestion.add(ziQuestion);
                    MyCollection faltQuestion = new MyCollection();
                    String mainTypeId= SharedPrefHelper.getInstance().getMainTypeId();
                    if (mainTypeId!=null){
                        mainTypeId="";
                    }
                    faltQuestion.setMainType(mainTypeId);
                    faltQuestion.setUserId(userId);
                    String collectionId=subjectId+"_"+examinationId+"_"+questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                    faltQuestion.setCollectionId(collectionId);
                    faltQuestion.setQuestionId(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                    faltQuestion.setTime(System.currentTimeMillis() + "");
                    faltQuestion.setType(Constant.COLLECTION_TYPE_1);
                    faltQuestion.setSearchKey("");
                    faltQuestion.setTitle(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getTitle());
                    faltQuestion.setExamId(examId);
                    faltQuestion.setSubjectId(subjectId);
                    faltQuestion.setExaminationId(examinationId);
//                    faltQuestion.setExaminationName(SharedPrefHelper.getInstance().getExaminationTitle());
                    List<Question> timaotiList = new ArrayList<>();
                    for (int i = 0; i < listCollQuestion.size(); i++) {
                        if (listCollQuestion.get(i).getGroupId().equals(ziQuestion.getGroupId())) {
                            timaotiList.add(listCollQuestion.get(i));
                        }
                    }
                    if (timaotiList.size() > 1) {
                        collectionDB.deleteByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                    }

                    Question zi = new Question();
                    Question fu = questionlist.get(getMvpView().getViewPager().getCurrentItem());
                    CommenUtils.newQuestion(fu, zi);
                    zi.setQuestionList(timaotiList);
                    zi.setExaminationName(SharedPrefHelper.getInstance().getExaminationTitle());
                    faltQuestion.setContent(JSON.toJSONString(zi, SerializerFeature.DisableCircularReferenceDetect) + "");
                    collectionDB.insert(faltQuestion);
                }
            }

        } else {//已经包含
            getMvpView().showBottomTittle(exam_tag);
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {//非题冒题
                for (int i = 0; i < listCollQuestion.size(); i++) {
                    if (listCollQuestion.get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())) {
                        listCollQuestion.remove(i);
                        break;
                    }
                }
                String questionId = questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                collectionDB.deleteByQuestionId(userId, questionId, Constant.COLLECTION_TYPE_1);
            } else {
                Question ziQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId()));
                ziQuestion.setGroupId(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                if (null != ziQuestion) {
                    MyCollection faltQuestion = new MyCollection();
                    String mainTypeId= SharedPrefHelper.getInstance().getMainTypeId();
                    if (mainTypeId!=null||!mainTypeId.isEmpty()){
                        mainTypeId="";
                    }
                    faltQuestion.setMainType(mainTypeId);
                    faltQuestion.setUserId(userId);
                    String collectionId=subjectId+"_"+examinationId+"_"+questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                    faltQuestion.setCollectionId(collectionId);
                    faltQuestion.setQuestionId(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                    faltQuestion.setTime(System.currentTimeMillis() + "");
                    faltQuestion.setType(Constant.COLLECTION_TYPE_1);
                    faltQuestion.setSearchKey("");
                    faltQuestion.setTitle(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getTitle());
                    faltQuestion.setExamId(examId);
                    faltQuestion.setSubjectId(subjectId);
                    faltQuestion.setExaminationId(examinationId);
                    List<Question> timaotiList = new ArrayList<>();
                    for (int i = 0; i < listCollQuestion.size(); i++) {
                        if (null != listCollQuestion.get(i).getGroupId() && !"".equals(listCollQuestion.get(i).getGroupId())) {
                            if (listCollQuestion.get(i).getGroupId().equals(ziQuestion.getGroupId())) {
                                if (listCollQuestion.get(i).getQuestionId().equals(ziQuestion.getQuestionId())) {
                                    listCollQuestion.remove(i);
                                    i--;
                                } else {
                                    timaotiList.add(listCollQuestion.get(i));
                                }
                            }
                        }
                    }
                    String queId = questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                    collectionDB.deleteByQuestionId(userId, queId, Constant.COLLECTION_TYPE_1);
                    if (timaotiList.size() != 0) {//此题冒题已经包含进了
                        Question zi = new Question();
                        Question zii = questionlist.get(getMvpView().getViewPager().getCurrentItem());
                        CommenUtils.newQuestion(zii, zi);
                        zi.setQuestionList(timaotiList);
                        faltQuestion.setContent(JSON.toJSONString(zi, SerializerFeature.DisableCircularReferenceDetect));
                        collectionDB.insert(faltQuestion);
                    }
                }
            }
        }

    }

    public void deleteFaltQuestion() {
        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {
            faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
            if (totallist.size() == 1) {
                getMvpView().finishActivity();
                return;
            }
            totallist.remove(getMvpView().getViewPager().getCurrentItem());
            questionlist.remove(getMvpView().getViewPager().getCurrentItem());
            getMvpView().vpNotifyData();
            if (totallist.size() == 1) {
                getMvpView().setVPIndex(0);
            } else if (totallist.size() == getMvpView().getViewPager().getCurrentItem()) {
                getMvpView().showCurrentNumber(getMvpView().getViewPager().getCurrentItem() - 1);
                getMvpView().setVPIndex(getMvpView().getViewPager().getCurrentItem() - 1);
            }
            getMvpView().showTotalNumber("/" + totallist.size());

        } else {
            if (compMap.size() == 0) {
                compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
            }
            int position=compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
            if (totallist.size() == 1) {
                faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                getMvpView().finishActivity();
                return;
            }
            totallist.remove(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())));
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 1) {
                faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                questionlist.remove(getMvpView().getViewPager().getCurrentItem());
                getMvpView().vpNotifyData();
                if (totallist.size() == 1) {
                    getMvpView().setVPIndex(0);
                } else if (questionlist.size() == getMvpView().getViewPager().getCurrentItem()) {
                    getMvpView().setVPIndex(getMvpView().getViewPager().getCurrentItem() - 1);
                }
            } else {
                FaltQuestion sholdDelete = faltQuestionDB.findAllByQuestionId(userId, examId, subjectId, examinationId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                FaltQuestion faltQuestion = new FaltQuestion();
                if (sholdDelete != null) {
                    faltQuestion.setUserId(sholdDelete.getUserId());
                    faltQuestion.setSubjectId(sholdDelete.getSubjectId());
                    faltQuestion.setExamId(sholdDelete.getExamId());
                    faltQuestion.setTypeId(sholdDelete.getTypeId());
                    faltQuestion.setExaminationId(sholdDelete.getExaminationId());
                    faltQuestion.setQuestionId(sholdDelete.getQuestionId());
                    faltQuestion.setChoiceType(sholdDelete.getChoiceType());
                    faltQuestion.setExaminationName(sholdDelete.getExaminationName());
                    faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
                }
                List<Question> ziList = new ArrayList<>();
                Question del=questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId()));
                for (int i = 0; i < questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size(); i++) {
                    if (!questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(i).getQuestionId().equals(del.getQuestionId())) {
                        ziList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(i));
                    }
                }

                Question ziQuestion = new Question();
                ziQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem());
                ziQuestion.setQuestionList(ziList);
                faltQuestion.setContent(JSON.toJSONString(ziQuestion, SerializerFeature.DisableCircularReferenceDetect) + "");
                faltQuestionDB.insert(faltQuestion);
                EventBus.getDefault().post(new DeleteCompEvent());

            }
            getMvpView().showTotalNumber("/" + totallist.size());
        }
        getMvpView().showError("删除成功");
        Question cuQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem());
        getMvpView().showBottomTittle(exam_tag);
        if (cuQuestion.getQuestionList() == null || cuQuestion.getQuestionList().size() == 0) {//不是题冒题
            for (int i = 0; i < showAnswerList.size(); i++) {
                if (showAnswerList.get(i).getQuestionId().equals(cuQuestion.getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                }
            }
        } else {
            if (compMap.containsKey(cuQuestion.getQuestionId())) {
                timaoIndext = compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
            } else {
                timaoIndext = 0;
                compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
            }
            for (int i = 0; i < showAnswerList.size(); i++) {
                if (showAnswerList.get(i).getQuestionId().equals(cuQuestion.getQuestionList().get(compMap.get(cuQuestion.getQuestionId())).getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                }
            }
        }
    }

    public void startCounter() {
        isPause = false;
    }

    public void stopCounter() {
        isPause = true;
    }

    public void deleteCollection() {
        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList() == null || questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 0) {
            collectionDB.deleteByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
            if (totallist.size() == 1) {
                getMvpView().finishActivity();
                return;
            }
            totallist.remove(getMvpView().getViewPager().getCurrentItem());
            questionlist.remove(getMvpView().getViewPager().getCurrentItem());
            getMvpView().vpNotifyData();
            if (totallist.size() == 1) {
                getMvpView().setVPIndex(0);
            } else if (totallist.size() == getMvpView().getViewPager().getCurrentItem()) {
                getMvpView().showCurrentNumber(getMvpView().getViewPager().getCurrentItem() - 1);
                getMvpView().setVPIndex(getMvpView().getViewPager().getCurrentItem() - 1);
            }
            getMvpView().showTopTittle("/" + totallist.size());
        } else {//当前为题冒题
            if (compMap.size() == 0) {
                compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
            }
            if (totallist.size() == 1) {
                collectionDB.deleteByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                getMvpView().finishActivity();
                return;
            }
            totallist.remove(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())));
            if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size() == 1) {
                collectionDB.deleteByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                questionlist.remove(getMvpView().getViewPager().getCurrentItem());
                getMvpView().vpNotifyData();
                if (totallist.size() == 1) {
                    getMvpView().setVPIndex(0);
                } else if (questionlist.size() == getMvpView().getViewPager().getCurrentItem()) {
                    getMvpView().setVPIndex(getMvpView().getViewPager().getCurrentItem() - 1);
                }else{
                    getMvpView().setVPIndex(getMvpView().getViewPager().getCurrentItem() - 1);
                }
            } else {
                MyCollection faltQuestion = new MyCollection();
                String quId=questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId();
                MyCollection deleteCollection = collectionDB.findCollectionByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                if (deleteCollection != null) {
                    faltQuestion.setUserId(deleteCollection.getUserId());
                    faltQuestion.setCollectionId(deleteCollection.getCollectionId());
                    faltQuestion.setQuestionId(deleteCollection.getQuestionId());
                    faltQuestion.setTitle(deleteCollection.getTitle());
                    faltQuestion.setSearchKey("");
                    faltQuestion.setExamId(deleteCollection.getExamId());
                    faltQuestion.setSubjectId(deleteCollection.getSubjectId());
                    faltQuestion.setExaminationId(deleteCollection.getExaminationId());
                    faltQuestion.setTime(System.currentTimeMillis() + "");
                    faltQuestion.setType(Constant.COLLECTION_TYPE_1);
                    faltQuestion.setMainType(deleteCollection.getMainType());
                    collectionDB.deleteByQuestionId(userId, questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), Constant.COLLECTION_TYPE_1);
                }
                List<Question> ziList = new ArrayList<>();
                for (int i = 0; i < questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().size(); i++) {
                    if (!questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(i).getQuestionId().equals(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId())).getQuestionId())) {
                        ziList.add(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionList().get(i));
                    }
                }
                Question ziQuestion = new Question();
                ziQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem());
                ziQuestion.setQuestionList(ziList);
                faltQuestion.setContent(JSON.toJSONString(ziQuestion, SerializerFeature.DisableCircularReferenceDetect) + "");
                collectionDB.insert(faltQuestion);
                EventBus.getDefault().post(new DeleteCompEvent());
            }
            getMvpView().showTotalNumber("/" + totallist.size());
        }
        getMvpView().showError("取消收藏成功");
        Question cuQuestion = questionlist.get(getMvpView().getViewPager().getCurrentItem());
        getMvpView().showBottomTittle(exam_tag);
        if (cuQuestion.getQuestionList() == null || cuQuestion.getQuestionList().size() == 0) {//不是题冒题
            for (int i = 0; i < showAnswerList.size(); i++) {
                if (showAnswerList.get(i).getQuestionId().equals(cuQuestion.getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                }
            }
        } else {
            if (compMap.containsKey(cuQuestion.getQuestionId())) {
                timaoIndext = compMap.get(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId());
            } else {
                timaoIndext = 0;
                compMap.put(questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId(), 0);
            }
            for (int i = 0; i < showAnswerList.size(); i++) {
                if (showAnswerList.get(i).getQuestionId().equals(cuQuestion.getQuestionList().get(compMap.get(cuQuestion.getQuestionId())).getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                }
            }
        }
    }

    public void submitQuestion() {
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUBMIT);
        submit();
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);
        stopCounter();
        getMvpView().finishActivity();
    }

    private void submit() {
        if (exam_tag == Constants.EXAM_TAG_ABILITY) {
//            SharedPrefHelper.getInstance().setCapScore(subjectId, CommenUtils.getAllScore(questionlist));
        }
        String classId = SharedPrefHelper.getInstance().getClassId();
        getIntentData();
        CommenUtils.saveAnswerLog(getMvpView().context(), true, questionlist, userId, examId, subjectId, typeId, examinationId, answerLogDB, time, getMvpView().getViewPager().getCurrentItem(), compMap, sectionId, classId);
        String examinationName = SharedPrefHelper.getInstance().getExaminationTitle();
        CommenUtils.saveError(sectionId, questionlist, userId, examId, subjectId, typeId, examinationId, faltQuestionDB, examinationName);
        CommenUtils.upLoadResult(userId, subjectId, examinationId, time, handler, getMvpView().context());
        getMvpView().intentExamReportActivity();
    }


    public void doNextTime() {
        String classId = SharedPrefHelper.getInstance().getClassId();
        getIntentData();
        getMvpView().progressStatus(Constants.VIEW_STATUS_SAVE_DATA);
        CommenUtils.saveAnswerLog(getMvpView().context(), false, questionlist, userId, examId, subjectId, typeId, examinationId, answerLogDB, time, getMvpView().getViewPager().getCurrentItem(), compMap, sectionId, classId);
        String examinationName = SharedPrefHelper.getInstance().getExaminationTitle();
        CommenUtils.saveError(sectionId, questionlist, userId, examId, subjectId, typeId, examinationId, faltQuestionDB, examinationName);
        getMvpView().progressStatus(Constants.VIEW_STATUS_SUCCESS);
        getMvpView().finishActivity();
    }

    public void onEventMainThreadPageChange(ExamIndexEvent event) {
        if (event.type == 1) {
            if (event.totalIndex != 0) {
                getMvpView().showCurrentNumber(event.totalIndex);
            } else {
                getMvpView().showCurrentNumber(event.currentIndex + 1);
            }
            getMvpView().setVPIndex(event.currentIndex);
        } else {
            int position = getMvpView().getViewPager().getCurrentItem();
            if (position == questionlist.size() - 1) {
                EventBus.getDefault().post(new FlushScorecard(true));
            }
            getMvpView().setVPIndex(position + 1);
        }
    }

    public void onEventMainThreadComprehensiveUpdatePage(ComprehensiveUpdatePage event) {
        int index = 0;
        if (questionlist.get(getMvpView().getViewPager().getCurrentItem()).getQuestionId().equals(event.questionId)) {
            for (int i = 0; i < totallist.size(); i++) {
                if (totallist.get(i).getQuestionId().equals(event.question.getQuestionId())) {
                    index = i + 1;
                }
            }
            getMvpView().showCurrentNumber(index);
            compMap.put(event.questionId, event.childIndex);
        }
        if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {//当前是错题和收藏
            getMvpView().showBottomTittle(exam_tag);
            for (int i = 0; i < showAnswerList.size(); i++) {
                if (showAnswerList.get(i).getQuestionId().equals(event.question.getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                }
            }
        } else {
            getMvpView().showBottomTittle(exam_tag);
            for (int i = 0; i < listCollQuestion.size(); i++) {
                if (listCollQuestion.get(i).getQuestionId().equals(event.question.getQuestionId())) {
                    getMvpView().showBottomTittlePress(exam_tag);
                    break;
                }
            }
        }

    }

    public void setVPPageChangeListener(int arg0) {
        currentIndex = arg0;
        if (arg0 + 1 > questionlist.size()) {
            getMvpView().showTopTitleRight(false);
            getMvpView().showExamPagerBottom(false);
            getMvpView().showExamPagerBottomReportLayou(true);
            if (exam_tag == Constants.EXAM_TAG_REPORT || exam_tag == Constants.EXAM_TAG_FALT || exam_tag == Constants.EXAM_TAG_COLLECTION) {
                getMvpView().showExamPagerBottomReportLayou(false);
            }

        } else {
            showDuoxuanGuide(arg0);
            if (questionlist.get(arg0).getQuestionList() == null || questionlist.get(arg0).getQuestionList().size() == 0) {
                getMvpView().showCurrentNumber(CommenUtils.getPositionAtAll(questionlist.get(arg0), totallist));
            } else {
                int ziIndex = 0;
                if (compMap.containsKey(questionlist.get(arg0).getQuestionId())) {
                    ziIndex = compMap.get(questionlist.get(arg0).getQuestionId());
                }
                getMvpView().showCurrentNumber(CommenUtils.getPositionAtAll(questionlist.get(arg0).getQuestionList().get(ziIndex), totallist));
            }
            getMvpView().showExamPagerBottomReportLayou(false);
            getMvpView().showTopTitleRight(true);
            getMvpView().showExamPagerBottom(true);
            changListener();
        }
    }

    private void showDuoxuanGuide(int arg0) {
        if (isShowAdvice){
            if (questionlist.get(arg0).getChoiceType()== ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()||questionlist.get(arg0).getChoiceType()== ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()){
                if(SharedPrefHelper.getInstance().getIsfirstInDuoxuan()){
                    getMvpView().showDuoxuanGuide(true);
                }else{
                    getMvpView().showDuoxuanGuide(false);
                }
            }else{
                getMvpView().showDuoxuanGuide(false);
            }
        }else{
            getMvpView().showDuoxuanGuide(false);
        }
    }

    public void backPressed() {
        if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT || exam_tag == Constants.EXAM_TAG_REPORT || exam_tag == Constants.EXAM_ORIGINAL_QUESTION) {
            getMvpView().finishActivity();
        } else {
            if (examPaper != null && examPaper.getExamination() != null && !TextUtils.isEmpty(typeId) && !TextUtils.isEmpty(examinationId) && !TextUtils.isEmpty(examId)) {
                String examinationTitle = examPaper.getExamination().getExaminationName();
            }
            int num = 0;
            int errorNum = CommenUtils.getErrorNum(questionlist);
            int rightNum = CommenUtils.getRightNum(questionlist);
            num = errorNum + rightNum;
            if (num == 0) {
                getMvpView().finishActivity();
            } else {
                getMvpView().showBackPress();
                stopCounter();
            }
        }
    }

    @Override
    public void onError(Exception e) {
        super.onError(e);
        getMvpView().progressStatus(Constants.VIEW_STATUS_ERROR_NET);
    }
}