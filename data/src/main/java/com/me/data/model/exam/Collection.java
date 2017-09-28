package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 * 收藏
 */
@Table(name="t_collection")
public class Collection implements Serializable {
    @Id
    private int dbId;
    private String userId;//用户ID
	private String questionId;
    private String subjectId; //考试科目
    private String examId; //考试类型
    private String typeId; //首页某一个type
    private String knowledgeId;//知识点的Id
    private String examinationId; //试卷Id
    private String realAnswer;//正确答案
    private String answerLocal;//本地答案
    private boolean isRight;//是否正确
    private String score;//分数
    private boolean isAnswer;//是否回答
    private int choiceType; //问题类型 可从试题里面读取，但是在这里定义，方便汇总和整理
    private int groupId;


    private String examData; //试题json




    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(int choiceType) {
        this.choiceType = choiceType;
    }


    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public String getAnswerLocal() {
        return answerLocal;
    }

    public void setAnswerLocal(String answerLocal) {
        this.answerLocal = answerLocal;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getExamData() {
        return examData;
    }

    public void setExamData(String examData) {
        this.examData = examData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }
}
