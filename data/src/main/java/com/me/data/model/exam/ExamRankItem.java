package com.me.data.model.exam;

/**
 * Created by fzw on 2017/9/5 0005.
 */

public class ExamRankItem {


    /**
     * id : 做题记录ID
     * username : 姓名
     * rank : 排名
     * paperId : 试卷Id
     * entTime : 交卷时间
     * score : 成绩
     * memberId : 学员Id
     */

    private int id;
    private String username;
    private String rank;
    private String paperId;
    private String entTime;
    private String score;
    private String memberId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getEntTime() {
        return entTime;
    }

    public void setEntTime(String entTime) {
        this.entTime = entTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}


