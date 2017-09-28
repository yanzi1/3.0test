package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/5/27 0027.
 *  答题报告
 */

public class ExamPaperReport {

    /**
     * examModel : 考试模式：1练习,2月考,3竞赛,4听课答题,5机考,6闯关
     * totalScore : 总分值
     * paperId : 试卷id
     * paperName : 试卷名称
     * answerNum : 答题数
     * score : 	得分
     * endTime : 交卷时间-时间戳
     * totleNum : 总题数
     * startTime : 开始作答时间-时间戳
     * sSubjectId : 考季id
     * papertypeId : 试卷类型id
     * subjectId : 科目id
     * matchId : 月考/竞赛id(练习模式时用此id判断有无权限)
     * timeCost : 用时（毫秒）
     * memberId : 学员id
     * rightNum : 正确数
     * commitType : 提交类型：1学员交卷，2系统自动交卷，3后台交卷
     * paperChoiceTypeLinkList : 试卷与题型关联的简化版vo 列表-由SimplePaperChoiceTypeLinkVo组成
     * id : null
     */

    private int examModel;
    private float totalScore;
    private long paperId;
    private String paperName;
    private int answerNum;
    private float score;
    private long endTime;
    private int totleNum;
    private long startTime;
    private String id;
    private long sSubjectId;
    private long papertypeId;
    private long subjectId;
    private long matchId;
    private long timeCost;
    private long memberId;
    private int rightNum;
    private int commitType;
    private List<SimplePaperChoiceTypeLinkVo> paperChoiceTypeLinkList;
    /**
     * 智能系统添加字段
     */
    private boolean showRank;
    private boolean showBeat;
    private boolean showAdvice;
    private String beatRate;
    private String remarks;
    /**
     * 自增字段
     * @return
     */
    private int quesCount;//除了主观题之外的题目个数
    private int isShowError;//是否显示错题解析按钮 1.显示

    public boolean isShowRank() {
        return showRank;
    }

    public void setShowRank(boolean showRank) {
        this.showRank = showRank;
    }

    public boolean isShowBeat() {
        return showBeat;
    }

    public void setShowBeat(boolean showBeat) {
        this.showBeat = showBeat;
    }

    public boolean isShowAdvice() {
        return showAdvice;
    }

    public void setShowAdvice(boolean showAdvice) {
        this.showAdvice = showAdvice;
    }

    public String getBeatRate() {
        return beatRate;
    }

    public void setBeatRate(String beatRate) {
        this.beatRate = beatRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getIsShowError() {
        return isShowError;
    }

    public void setIsShowError(int isShowError) {
        this.isShowError = isShowError;
    }

    public int getQuesCount() {
        return quesCount;
    }

    public void setQuesCount(int quesCount) {
        this.quesCount = quesCount;
    }

    public long getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public List<SimplePaperChoiceTypeLinkVo> getPaperChoiceTypeLinkList() {
        return paperChoiceTypeLinkList;
    }

    public void setPaperChoiceTypeLinkList(List<SimplePaperChoiceTypeLinkVo> paperChoiceTypeLinkList) {
        this.paperChoiceTypeLinkList = paperChoiceTypeLinkList;
    }

    public int getExamModel() {
        return examModel;
    }

    public void setExamModel(int examModel) {
        this.examModel = examModel;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getTotleNum() {
        return totleNum;
    }

    public void setTotleNum(int totleNum) {
        this.totleNum = totleNum;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public long getPapertypeId() {
        return papertypeId;
    }

    public void setPapertypeId(long papertypeId) {
        this.papertypeId = papertypeId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getRightNum() {
        return rightNum;
    }

    public void setRightNum(int rightNum) {
        this.rightNum = rightNum;
    }

    public int getCommitType() {
        return commitType;
    }

    public void setCommitType(int commitType) {
        this.commitType = commitType;
    }
}
