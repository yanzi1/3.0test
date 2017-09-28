package com.me.data.model.exam;

import java.io.Serializable;

/**
 * 提交的答案个体
 * Created by wangyongchao on 2015/7/14 0014.
 */

public class Answers implements Serializable {
    private String questionId;
    private String answerLocal;
    //2016.11.29新加
    private int isRight;
    //2017.04.10
    private String score;

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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerLocal() {
        return answerLocal;
    }

    public void setAnswerLocal(String answerLocal) {
        this.answerLocal = answerLocal;
    }
}
