package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/5 0005.
 * 学员做题详情对象
 */

public class MemberExamDetailVo {


    /**
     * memberId : 学员id
     * examRecordId : 做题记录id
     * questionId : 试题id
     * choicetypeId : 题型id
     * paperSectionId : 试卷段id
     * isGroup : 是否题帽：0否，1是
     * groupId : 题帽id
     * score : 得分
     * isRight : 0错误，1正确，2未答
     * createTime : 答案提交时间
     * wasteTime : 耗时（单位毫秒）
     * myAnswer : 我的答案
     * id : 2353
     */

    private long memberId;
    private long examRecordId;
    private long questionId;
    private long choicetypeId;
    private long paperSectionId;
    private int isGroup;
    private String groupId;
    private String score;
    private int isRight;
    private long createTime;
    private long wasteTime;
    private String myAnswer;
    private int id;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(long examRecordId) {
        this.examRecordId = examRecordId;
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

    public long getPaperSectionId() {
        return paperSectionId;
    }

    public void setPaperSectionId(long paperSectionId) {
        this.paperSectionId = paperSectionId;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getWasteTime() {
        return wasteTime;
    }

    public void setWasteTime(long wasteTime) {
        this.wasteTime = wasteTime;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
