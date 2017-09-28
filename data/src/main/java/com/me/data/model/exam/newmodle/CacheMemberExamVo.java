package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/5 0005.
 * 提交做题时使用的modle
 */

public class CacheMemberExamVo {

    /**
     * examRecordId : 做题记录id 第一次保存进度的时候没有此id，可以为空，已经保存过进度的，此id必填，否则认为是第一次保存重新建立新的做题记录
     * paperId : 试卷id
     * questionId : 试题id
     * choicetypeId : 题型id
     * paperSectionId : 试卷段id
     * groupId : 题帽id，题帽小题必须传题帽id，普通题没有题帽id
     * wasteTime : 耗时（单位毫秒）
     * myAnswer : 我的答案
     * answerStrategy : 录入形式（所属的大题型）
     */

    private String examRecordId = "";
    private String wasteTime = "";
    private long paperId;
    private long questionId;
    private long choicetypeId;
    private int answerStrategy;
    private String paperSectionId;
    private String groupId = "";
    private String myAnswer;

    public int getAnswerStrategy() {
        return answerStrategy;
    }

    public void setAnswerStrategy(int answerStrategy) {
        this.answerStrategy = answerStrategy;
    }

    public String getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(String examRecordId) {
        this.examRecordId = examRecordId;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getChoicetypeId() {
        return choicetypeId;
    }

    public void setChoicetypeId(long choicetypeId) {
        this.choicetypeId = choicetypeId;
    }

    public String getPaperSectionId() {
        return paperSectionId;
    }

    public void setPaperSectionId(String paperSectionId) {
        this.paperSectionId = paperSectionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getWasteTime() {
        return wasteTime;
    }

    public void setWasteTime(String wasteTime) {
        this.wasteTime = wasteTime;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }
}
