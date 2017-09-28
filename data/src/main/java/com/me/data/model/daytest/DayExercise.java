package com.me.data.model.daytest;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

/**
 * Created by fengzongwei on 2016/5/20 0020.
 * 用户地址科目表
 */
@Table(name="t_day_test")
public class DayExercise {
    @Id
    private int dbId;
    private String userId;//用户ID
    private String examId;//考试ID
    private String subjectId;//科目ID
    private String questionId;//问题ID
    private String dataTime;//做题时间  xxxx-xx-xx
    private String answerRight;//1:正确；2:错误；0:未答题
    private String answerLocal;//本地答案
    private String choiceType;//题型
    private String content;//试题json

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getAnswerRight() {
        return answerRight;
    }

    public void setAnswerRight(String answerRight) {
        this.answerRight = answerRight;
    }

    public String getAnswerLocal() {
        return answerLocal;
    }

    public void setAnswerLocal(String answerLocal) {
        this.answerLocal = answerLocal;
    }

    public String getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(String choiceType) {
        this.choiceType = choiceType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
