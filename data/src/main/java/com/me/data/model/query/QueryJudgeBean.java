package com.me.data.model.query;

import java.io.Serializable;

/**
 * Created by wyc
 */
public class QueryJudgeBean implements Serializable {
    private String content;
    private String id;
    private float score;

    private boolean evaluationOver;//true:已经评价过的标签 false:未评价过的标签
    private boolean isChecked;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isEvaluationOver() {
        return evaluationOver;
    }

    public void setEvaluationOver(boolean evaluationOver) {
        this.evaluationOver = evaluationOver;
    }
}
