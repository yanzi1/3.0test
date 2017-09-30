package com.me.fanyin.zbme.views.exam.utils;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.AnswerLog;
import com.me.data.model.exam.Answers;
import com.me.data.model.exam.ExamPaper;
import com.me.data.model.exam.Examination;
import com.me.data.model.exam.FaltQuestion;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.db.AnswerLogDB;
import com.me.fanyin.zbme.views.exam.db.FaltQuestionDB;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.remote.ApiClient;
import com.me.fanyin.zbme.views.exam.remote.ApiModel;
import com.me.fanyin.zbme.views.exam.remote.ParamsUtils;
import com.me.fanyin.zbme.views.exam.remote.ResultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wyc on 24/7/15.
 */
public class CommenUtils {
    public static String getPingJia(float score, List<Question> listNoFor) {
        if (listNoFor == null || listNoFor.size() == 0) {
            return "学霸就是任性。";
        }
        float totalScore = getTotalScore(listNoFor);
        int pingjia = (int) ((score * 100) / totalScore);
        if (pingjia < 31) {
            return "通往你成功的路是在施工么？";
        } else if (30 < pingjia && pingjia < 60) {
            return "学习只要开始,就永远都不晚！";
        } else if (59 < pingjia && pingjia < 80) {
            return "付出终有回报,成绩还能提高。";
        } else {
            return "学霸就是任性。";
        }
    }

    public static float getTotalScore(List<Question> listNoFor) {
        float num = 0;
        for (int i = 0; i < listNoFor.size(); i++) {
            if (null == listNoFor.get(i).getQuestionList() || listNoFor.get(i).getQuestionList().size() == 0) {
                if (listNoFor.get(i).getScore() == null || listNoFor.get(i).getScore().equals("")) {
                    listNoFor.get(i).setScore("0");
                }
                num += Float.valueOf(listNoFor.get(i).getScore());
            } else { 
                for (int j = 0; j < listNoFor.get(i).getQuestionList().size(); j++) {
                    if (listNoFor.get(i).getQuestionList().get(j).getScore() == null || listNoFor.get(i).getQuestionList().get(j).getScore().equals("")) {
                        listNoFor.get(i).getQuestionList().get(j).setScore("0");
                    }
                    num += Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                }
            }
        }
        return num;
    }

    public static float getAllScore(List<Question> listNoFor) {
        float num = 0;
        float panduan = 0;
        for (int i = 0; i < listNoFor.size(); i++) {
            if (null == listNoFor.get(i).getQuestionList() || listNoFor.get(i).getQuestionList().size() == 0) {
                if (listNoFor.get(i).getScore() == null || listNoFor.get(i).getScore().equals("")) {
                    listNoFor.get(i).setScore("0");
                }
                if (listNoFor.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()) {//单选
                    if (listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                        num += Float.valueOf(listNoFor.get(i).getScore());
                    }
                } else if (listNoFor.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {//不定项
                    if (listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                        num += Float.valueOf(listNoFor.get(i).getScore());
                    } else {
                        boolean isContain = true;
                        int time=0;
                        if (listNoFor.get(i).getUserAnswer().contains(",")) {
                            String[] localAnswers = listNoFor.get(i).getUserAnswer().split(",");
                            for (int j = 0; j < localAnswers.length; j++) {
                                if (!listNoFor.get(i).getRealAnswer().contains(localAnswers[j])) {
                                    isContain = false;
                                }else{
                                    time++;
                                }
                            }
                            if (isContain) {
                                num += 0.5*time;
                            }
                        } else {
                            if (!"".equals(listNoFor.get(i).getUserAnswer()) && listNoFor.get(i).getRealAnswer().contains(listNoFor.get(i).getUserAnswer())) {
                                num += 0.5;
                            }
                        }
                    }
                } else if (listNoFor.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()) {//多选
                    if (listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                        num += Float.valueOf(listNoFor.get(i).getScore());
                    }
                } else if (listNoFor.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {//判断
                    float scot=Float.valueOf(listNoFor.get(i).getScore());
                    if (listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                        panduan += scot;
                    } else if (listNoFor.get(i).getUserAnswer() != null && !"".equals(listNoFor.get(i).getUserAnswer()) && !listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
//                        panduan -= 0.5*scot;
                    }
                }
            } else { 
                for (int j = 0; j < listNoFor.get(i).getQuestionList().size(); j++) {
                    if (listNoFor.get(i).getQuestionList().get(j).getScore() == null || listNoFor.get(i).getQuestionList().get(j).getScore().equals("")) {
                        listNoFor.get(i).getQuestionList().get(j).setScore("0");
                    }
                    if (listNoFor.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {//不定项
                        if (listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                            num += Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                        } else {
                            boolean isContain = true;
                            if (listNoFor.get(i).getQuestionList().get(j).getUserAnswer().contains(",")) {
                                String[] localAnswers = listNoFor.get(i).getQuestionList().get(j).getUserAnswer().split(",");
                                for (int m = 0; m < localAnswers.length; m++) {
                                    if (!listNoFor.get(i).getQuestionList().get(j).getRealAnswer().contains(localAnswers[m])) {
                                        isContain = false;
                                    }
                                }
                                if (isContain) {
                                    num += 0.5*Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                                }
                            } else {
                                if (!"".equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer()) && listNoFor.get(i).getQuestionList().get(j).getRealAnswer().contains(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                                    num += 0.5*Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                                }
                            }
                        }
                    } else if (listNoFor.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()) {//单选
                        if (listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                            num += Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                        }
                    } else if (listNoFor.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()) {//多选
                        if (listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                            num += Float.valueOf(listNoFor.get(i).getQuestionList().get(j).getScore());
                        }
                    }
                }
            }
        }
        if (panduan > 0) {
            num += panduan;
        }
        return num;
    }
    
    public static float getBudingxiangScore(Question question){
        float num=0;
        if (question.getRealAnswer().equals(question.getUserAnswer())) {
            num += Float.valueOf(question.getScore());
        } else {
            boolean isContain = true;
            int time=0;
            if (question.getUserAnswer().contains(",")) {
                String[] localAnswers = question.getUserAnswer().split(",");
                for (int j = 0; j < localAnswers.length; j++) {
                    if (!question.getRealAnswer().contains(localAnswers[j])) {
                        isContain = false;
                    }else{
                        time++;
                    }
                }
                if (isContain) {
                    num += 0.5*Float.valueOf(question.getScore());
                }
            } else {
                if (!"".equals(question.getUserAnswer()) && question.getRealAnswer().contains(question.getUserAnswer())) {
                    num += 0.5*Float.valueOf(question.getScore());
                }
            }
        }
        return num;
    }

    public static int getRightNum(List<Question> listNoFor) {
        int num = 0;
        for (int i = 0; i < listNoFor.size(); i++) {
            if (listNoFor.get(i).getQuestionList() == null || listNoFor.get(i).getQuestionList().size() == 0) {
                if (listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                    num++;
                }
            } else {
                for (int j = 0; j < listNoFor.get(i).getQuestionList().size(); j++) {
                    if (listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    public static int getErrorNum(List<Question> listNoFor) {
        int num = 0;
        for (int i = 0; i < listNoFor.size(); i++) {
            if (listNoFor.get(i).getQuestionList() == null || listNoFor.get(i).getQuestionList().size() == 0) {
                if (null != listNoFor.get(i).getUserAnswer() && !"".equals(listNoFor.get(i).getUserAnswer()) && !listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                    num++;
                }
            } else {
                for (int j = 0; j < listNoFor.get(i).getQuestionList().size(); j++) {
                    if (null != listNoFor.get(i).getQuestionList().get(j).getUserAnswer() && !"".equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer()) && !listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    public static List<Question> getErrorList(List<Question> listNoFor) {
        List<Question> errorList = new ArrayList<>();
        for (int i = 0; i < listNoFor.size(); i++) {
            if (listNoFor.get(i).getQuestionList() == null || listNoFor.get(i).getQuestionList().size() == 0) {
                if (null != listNoFor.get(i).getUserAnswer() && !"".equals(listNoFor.get(i).getUserAnswer()) && !listNoFor.get(i).getRealAnswer().equals(listNoFor.get(i).getUserAnswer())) {
                    errorList.add(listNoFor.get(i));
                }
            } else {
                List<Question> comprehensiveList = new ArrayList<>();
                Question question = new Question();
                for (int j = 0; j < listNoFor.get(i).getQuestionList().size(); j++) {
                    if (null != listNoFor.get(i).getQuestionList().get(j).getUserAnswer() && !"".equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer()) && !listNoFor.get(i).getQuestionList().get(j).getRealAnswer().equals(listNoFor.get(i).getQuestionList().get(j).getUserAnswer())) {
                        comprehensiveList.add(listNoFor.get(i).getQuestionList().get(j));
                    }
                }
                if (comprehensiveList.size() != 0) {
                    newQuestion(listNoFor.get(i), question);
                    question.setQuestionList(comprehensiveList);
                    errorList.add(question);
                }
            }
        }
        return errorList;
    }


    public static void newQuestion(Question fuQuestion, Question ziQuestion) {
        ziQuestion.setUserAnswer(fuQuestion.getUserAnswer());
        ziQuestion.setPointList(fuQuestion.getPointList());
        ziQuestion.setExaminationId(fuQuestion.getExaminationId());
        ziQuestion.setChoiceType(fuQuestion.getChoiceType());
        ziQuestion.setExamId(fuQuestion.getExamId());
        ziQuestion.setGroupId(fuQuestion.getGroupId());
        ziQuestion.setTypeId(fuQuestion.getTypeId());
        ziQuestion.setOptionList(fuQuestion.getOptionList());
        ziQuestion.setQuestionId(fuQuestion.getQuestionId());
        ziQuestion.setQuestionList(fuQuestion.getQuestionList());
        ziQuestion.setQuizAnalyze(fuQuestion.getQuizAnalyze());
        ziQuestion.setRealAnswer(fuQuestion.getRealAnswer());
        ziQuestion.setScore(fuQuestion.getScore());
        ziQuestion.setSubjectId(fuQuestion.getSubjectId());
        ziQuestion.setTitle(fuQuestion.getTitle());
        ziQuestion.setTypeId(fuQuestion.getTypeId());
        ziQuestion.setUserId(fuQuestion.getUserId());
    }

    public static List<Question> getAllQuestionByList(List<Question> questionlist) {
        List<Question> allList = new ArrayList<>();
        for (int i = 0; i < questionlist.size(); i++) {
            if (null != questionlist.get(i).getQuestionList() && questionlist.get(i).getQuestionList().size() != 0) {//是题冒题
                for (int j = 0; j < questionlist.get(i).getQuestionList().size(); j++) {
                    allList.add(questionlist.get(i).getQuestionList().get(j));
                }
            } else {
                allList.add(questionlist.get(i));
            }
        }
        return allList;
    }

    public static void setAllQuestionHeight(List<Question> questionlist) {
        for (int i = 0; i < questionlist.size(); i++) {
            if (questionlist.get(i).getCompreHeight() != 0) {
                questionlist.get(i).setCompreHeight(0);
            }
        }
    }

    public static int getPositionAtAll(Question question, List<Question> allList) {
        int num = 0;
        for (int i = 0; i < allList.size(); i++) {
            if (question.getQuestionId().equals(allList.get(i).getQuestionId())) {
                num = i;
                break;
            }
        }
        return (num + 1);
    }

    /**
     * 换算时间
     */
    public static String changeTime(int time) {
        StringBuffer sb = new StringBuffer();
        sb.append(getHour(time)).append(":").append(getMin(time)).append(":").append(getSec(time)).append("");
        return sb.toString();
    }

    public static String getSec(int number) {
        int sec = number % 60;
        return sec < 10 ? "0" + sec : sec + "";
    }

    public static String getMin(int number) {
        int min = (number / 60) % 60;
        return min < 10 ? "0" + min : min + "";
    }

    public static String getHour(int number) {
        int hour = (number / 60 / 60) % 24;
        return hour < 10 ? "0" + hour : hour + "";
    }

    public static void saveError(String sectionId, List<Question> questionlist, String userId, String examId, String subjectId, String typeId, String examinationId, FaltQuestionDB faltQuestionDB, String examinationName) {
        for (Question question : questionlist) {
            if (question.getQuestionList() == null || question.getQuestionList().size() == 0) {
                if (question.getUserAnswer() != null && !"".equals(question.getUserAnswer())) {
                    if (!question.getUserAnswer().equals(question.getRealAnswer())) {
                        FaltQuestion faltQuestion = new FaltQuestion();
                        faltQuestion.setUserId(userId);
                        faltQuestion.setSubjectId(subjectId);
                        faltQuestion.setExamId(examId);
                        faltQuestion.setTypeId(typeId);
                        faltQuestion.setSectionId(sectionId);
                        faltQuestion.setExaminationId(examinationId);
                        faltQuestion.setQuestionId(question.getQuestionId());
                        faltQuestion.setChoiceType(question.getChoiceType());
                        faltQuestion.setExaminationName(examinationName);
                        faltQuestion.setContent(JSON.toJSONString(question) + "");
                        FaltQuestion falt = faltQuestionDB.findAllByQuestionId(userId, examId, subjectId, examinationId,question.getQuestionId());
                        if (falt != null) {
                            faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, question.getQuestionId());
                        }
                        faltQuestionDB.insert(faltQuestion);
                    } 
                }
            } else {
                List<Question> errlist = new ArrayList<>();
                for (int i = 0; i < question.getQuestionList().size(); i++) {
                    if (question.getQuestionList().get(i).getUserAnswer() != null && !"".equals(question.getQuestionList().get(i).getUserAnswer())) {
                        if (!question.getQuestionList().get(i).getUserAnswer().equals(question.getQuestionList().get(i).getRealAnswer())) {
                            errlist.add(question.getQuestionList().get(i));
                        } 
                    }
                }
                if (errlist.size() != 0) {
                    Question zi = new Question();
                    Question fu = question;
                    CommenUtils.newQuestion(fu, zi);
                    zi.setQuestionList(errlist);
                    FaltQuestion faltQuestion = new FaltQuestion();
                    faltQuestion.setUserId(userId);
                    faltQuestion.setSubjectId(subjectId);
                    faltQuestion.setExamId(examId);
                    faltQuestion.setTypeId(typeId);
                    faltQuestion.setExaminationId(examinationId);
                    faltQuestion.setQuestionId(zi.getQuestionId());
                    faltQuestion.setChoiceType(zi.getChoiceType());
                    faltQuestion.setSectionId(sectionId);
                    faltQuestion.setContent(JSON.toJSONString(zi) + "");
                    faltQuestion.setExaminationName(examinationName);
                    FaltQuestion falt = faltQuestionDB.findAllByQuestionId(userId, examId, subjectId, examinationId, zi.getQuestionId());
                    if (falt != null) {
                        faltQuestionDB.deleteByQuestionId(userId, examId, subjectId, examinationId, zi.getQuestionId());
                    }
                    faltQuestionDB.insert(faltQuestion);
                }
            }
        }
    }

    public static void saveAnswerLog(Context context, boolean isFinish, List<Question> questionlist, String userId, String examId, String subjectId, String typeId, String examinationId, AnswerLogDB answerLogDB, int time, int currentPosition, Map<String, Integer> compMap, String sectionId, String classId) {
        ExamPaper examPaper = new ExamPaper();
        String examinationTitle = SharedPrefHelper.getInstance().getExaminationTitle();
        Examination examination = new Examination();
        String subjectName = SharedPrefHelper.getInstance().getSubjectName();
        examination.setExaminationId(examinationId);
        examination.setTotalQuestions(questionlist.size());
        examination.setExaminationName(examinationTitle);
        AnswerLog answerLog = new AnswerLog();
        answerLog.setUserId(userId);
        answerLog.setExamId(examId);
        answerLog.setSubjectId(subjectId);
        answerLog.setTypeId(typeId);
        answerLog.setSectionId(sectionId);
        answerLog.setClassId(classId);
        answerLog.setExaminationId(examinationId);
        answerLog.setExaminationTitle(examination.getExaminationName());
        answerLog.setTotalQuestions(examination.getTotalQuestions());
        int rightDone = CommenUtils.getRightNum(questionlist);
        int errorDone = CommenUtils.getErrorNum(questionlist);
        int totalDone = rightDone + errorDone;
        answerLog.setFinishedQuestions(totalDone);
        answerLog.setAnswerErrorNums(errorDone);
        answerLog.setAnswerRightNums(rightDone);
        answerLog.setScore("" + getAllScore(questionlist));
        answerLog.setSectionId(sectionId);
        answerLog.setClassId(classId);
        examPaper.setExamination(examination);
        CommenUtils.setAllQuestionHeight(questionlist);
        examPaper.setQuestionList(questionlist);
        String answers = JSON.toJSONString(examPaper, SerializerFeature.DisableCircularReferenceDetect);
        answerLog.setContent(answers);
        if (currentPosition >= questionlist.size()) {
            if (questionlist.get(currentPosition - 1).getQuestionList() == null || questionlist.get(currentPosition - 1).getQuestionList().size() == 0) {
                answerLog.setCurrentIndex(questionlist.size() - 1);
            } else {
                answerLog.setCurrentIndex(questionlist.size() - 1);
                answerLog.setChildIndex(0);
            }
        } else {
            if (questionlist.get(currentPosition).getQuestionList() == null || questionlist.get(currentPosition).getQuestionList().size() == 0) {
                answerLog.setCurrentIndex(currentPosition);
            } else {
                answerLog.setCurrentIndex(currentPosition);
                if (compMap.get(questionlist.get(currentPosition).getQuestionId()) == null) {
                    compMap.put(questionlist.get(currentPosition).getQuestionId(), 0);
                }
                answerLog.setChildIndex(compMap.get(questionlist.get(currentPosition).getQuestionId()));
            }
        }
        String nowTime = System.currentTimeMillis() + "";
        answerLog.setUpdateTime(nowTime);
        answerLog.setIsFinished(isFinish);
        answerLog.setUsedTime(time);
        AnswerLog historyAnswer = answerLogDB.findByExamination(userId, examId, subjectId, typeId, examinationId);
        if (historyAnswer != null) {
            answerLogDB.deleteByExamination(historyAnswer);
        }
        answerLogDB.insert(answerLog);
    }

    public static void upLoadResult(String userId, String subjectId, String examinationId, int time, final Handler handler, final Context context) {
        List<Answers> list = new ArrayList();
        for (int i = 0; i < ExamPersenter.questionlist.size(); i++) {
            if (ExamPersenter.questionlist.get(i).getQuestionList() == null || ExamPersenter.questionlist.get(i).getQuestionList().size() == 0) {
                if(ExamPersenter.questionlist.get(i).getUserAnswer()==null|| StringUtil.isEmpty(ExamPersenter.questionlist.get(i).getUserAnswer())){
                    continue;
                }
                Answers answer = new Answers();
                if (ExamPersenter.questionlist.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()){
                    answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionId());
                    answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getUserAnswer());
                    if (ExamPersenter.questionlist.get(i).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getRealAnswer())){
                        answer.setIsRight(1);
                        answer.setScore(ExamPersenter.questionlist.get(i).getScore());
                    }else {
                        answer.setIsRight(0);
                        answer.setScore("0");
                    }
                }else{
                    if (ExamPersenter.questionlist.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()){
                        answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionId());
                        answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getUserAnswer());
                        if (ExamPersenter.questionlist.get(i).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getRealAnswer())){
                            answer.setIsRight(1);
                            answer.setScore(ExamPersenter.questionlist.get(i).getScore());
                        }else {
                            answer.setIsRight(0);
                            answer.setScore("0");
                        }
                    }else  if (ExamPersenter.questionlist.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()){
                        answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionId());
                        answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getUserAnswer());
                        if (ExamPersenter.questionlist.get(i).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getRealAnswer())){
                            answer.setIsRight(1);
                            answer.setScore(ExamPersenter.questionlist.get(i).getScore());
                        }else {
                            answer.setIsRight(0);
                            answer.setScore("0");
                        }
                    }else  if (ExamPersenter.questionlist.get(i).getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()){
                        answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionId());
                        answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getUserAnswer());
                        float score=getBudingxiangScore(ExamPersenter.questionlist.get(i));
                        answer.setScore(score+"");
                        if (ExamPersenter.questionlist.get(i).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getRealAnswer())){
                            answer.setIsRight(1);
                        }else {
                            answer.setIsRight(0);
                        }
                    }
                }
                list.add(answer);
            } else {
                for (int j = 0; j < ExamPersenter.questionlist.get(i).getQuestionList().size(); j++) {
                    if(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer()==null|| StringUtil.isEmpty(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer())){
                        continue;
                    }
                    Answers answer = new Answers();
                    if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()){
                        answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId());
                        answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer());
                        if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getRealAnswer())){
                            answer.setIsRight(1);
                            answer.setScore(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getScore());
                        }else {
                            answer.setIsRight(0);
                            answer.setScore("0");
                        }
                    }else{
                        if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()){
                            answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId());
                            answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer());
                            if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getRealAnswer())){
                                answer.setIsRight(1);
                                answer.setScore(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getScore());
                            }else {
                                answer.setIsRight(0);
                                answer.setScore("0");
                            }
                        }else  if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()){
                            answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId());
                            answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer());
                            if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getRealAnswer())){
                                answer.setIsRight(1);
                                answer.setScore(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getScore());
                            }else {
                                answer.setIsRight(0);
                                answer.setScore("0");
                            }
                        }else  if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()){
                            answer.setQuestionId(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getQuestionId());
                            answer.setAnswerLocal(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer());
                            float score=getBudingxiangScore(ExamPersenter.questionlist.get(i).getQuestionList().get(j));
                            answer.setScore(score+"");
                            if (ExamPersenter.questionlist.get(i).getQuestionList().get(j).getUserAnswer().equals(ExamPersenter.questionlist.get(i).getQuestionList().get(j).getRealAnswer())){
                                answer.setIsRight(1);
                            }else {
                                answer.setIsRight(0);
                            }
                        }
                    }
                    list.add(answer);
                }
            }
        }
        String datas = JSON.toJSONString(list);
        ApiModel apiModel = new ApiModel(new ResultListener() {
            @Override
            public void onSuccess(String json) {
            }

            @Override
            public void onError(Exception e) {
            }
        });
        long startTime=System.currentTimeMillis()-time;
        float totalScore=getTotalScore(ExamPersenter.questionlist);
        apiModel.getData(ApiClient.getClient().postUploadExamPaper(ParamsUtils.getInstance(context).uploadExamPaper(examinationId, subjectId, time + "", datas, startTime, totalScore)));
    }

}