package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/2 0002.
 * 历年真题具体Item modle
 */

public class ExamPreviousPaperVo {

    /**
     * id : 试卷ID
     * questionNum : 试题数量
     * hasDone : 是否做过试题
     * doneNum : 已做题目数量
     * name : 试卷名称
     * score : 考试成绩
     * limitedTime : 考试限定的分钟数
     * accuracy : 正确率
     */

    private int id;
    private String questionNum;
    private boolean hasDone;
    private String doneNum;
    private String name;
    private String score;
    private int limitedTime;
    private String accuracy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }

    public String getDoneNum() {
        return doneNum;
    }

    public void setDoneNum(String doneNum) {
        this.doneNum = doneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(int limitedTime) {
        this.limitedTime = limitedTime;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
