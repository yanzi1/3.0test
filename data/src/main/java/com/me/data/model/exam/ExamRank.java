package com.me.data.model.exam;

import java.util.List;

/**
 * Created by fzw on 2017/9/5 0005.
 * 答题报告排行榜
 */

public class ExamRank {

    /**
     * tips : 小奥提示：只按你第一次做这套卷子的分数排名哦，且做且珍惜
     * myRank : 我的排名
     * myScore : 我的成绩
     * rankTop : 成绩排名集合
     */

    private String tips;
    private String myRank;
    private String myScore;
    private List<ExamRankItem> rankTop;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getMyRank() {
        return myRank;
    }

    public void setMyRank(String myRank) {
        this.myRank = myRank;
    }

    public String getMyScore() {
        return myScore;
    }

    public void setMyScore(String myScore) {
        this.myScore = myScore;
    }

    public List<ExamRankItem> getRankTop() {
        return rankTop;
    }

    public void setRankTop(List<ExamRankItem> rankTop) {
        this.rankTop = rankTop;
    }
}
