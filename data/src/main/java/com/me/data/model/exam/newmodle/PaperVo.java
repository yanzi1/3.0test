package com.me.data.model.exam.newmodle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fzw on 2017/5/25 0025.
 * 试卷对象
 */

public class PaperVo implements Serializable {


    /**
     * id : 试卷id
     * examId : 项目id
     * sSubjectId : 考季id
     * difficultyLevel : 难易程度：1易2中3难
     * name : 试卷名称
     * papertypeId : 试卷类型id
     * effectiveDate : 2016-05-17 17:59:02
     * limitedTime : 考试限定的分钟数
     * totalScore : 试卷总分
     * passingScore : 合格分数线
     * canDownload : 能否下载：0不能，1能
     * downloadUrl : 下载地址路径
     * status : 状态：1=新建，2=激活，3=隐藏
     * deleteStatus : 删除状态：0正常，1已删除
     * choicetypeList : 试卷下段信息列表，列表中的对象为PaperChoicetypeLinkVo类型
     *
     * PaperVo 未标注字段
     * determinant : null
     * source : null
     * moment : null
     * minTotalScore : null
     * maxTotalScore : null
     * createBy : null
     * createDate : null
     * startCreateDate : null
     * endCreateDate : null
     * updateBy : null
     * updateDate : null
     * papertypeName : null
     * questionNum : null
     */

    private long id;
    private long examId;
    private long sSubjectId;
    private int difficultyLevel;
    private String name;
    private long papertypeId;
    private String effectiveDate;
    private int limitedTime;
    private float totalScore;
    private float passingScore;
    private int canDownload;
    private String downloadUrl;
    private int status;
    private int questionNum;
    private int deleteStatus;
    private List<PaperChoicetypeLinkVo> choicetypeList;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private Object determinant;
    private Object startCreateDate;
    private Object maxTotalScore;
    private Object moment;
    private Object endCreateDate;
    private Object minTotalScore;
    private Object papertypeName;
    private Object source;

    /**
     * 自增
     * @param choicetypeList
     */
    private List<PaperQuestionLinkVo> paperQuestionLinkVoList;//所有试题（不区分类别,不包含题冒题中小题）的集合
    private List<PaperQuestionLinkVo> paperQuestionLinkVoListWrong;//查看解析时的所有错误试题集合
    private List<CacheMemberExamVo> cacheMemberExamVoList;//用于提交试卷使用的集合
    private String examRecordId = "";//做题记录id
    private long timeCost;//如果是从继续做题进入做题，则此字段为用户上次做了多久

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }

    public String getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(String examRecordId) {
        this.examRecordId = examRecordId;
    }

    public long getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public List<CacheMemberExamVo> getCacheMemberExamVoList() {
        return cacheMemberExamVoList;
    }

    public void setCacheMemberExamVoList(List<CacheMemberExamVo> cacheMemberExamVoList) {
        this.cacheMemberExamVoList = cacheMemberExamVoList;
    }

    public List<PaperQuestionLinkVo> getPaperQuestionLinkVoListWrong() {
        return paperQuestionLinkVoListWrong;
    }

    public void setPaperQuestionLinkVoListWrong(List<PaperQuestionLinkVo> paperQuestionLinkVoListWrong) {
        this.paperQuestionLinkVoListWrong = paperQuestionLinkVoListWrong;
    }

    public List<PaperQuestionLinkVo> getPaperQuestionLinkVoList() {
        return paperQuestionLinkVoList;
    }

    public void setPaperQuestionLinkVoList(List<PaperQuestionLinkVo> paperQuestionLinkVoList) {
        this.paperQuestionLinkVoList = paperQuestionLinkVoList;
    }

    public List<PaperChoicetypeLinkVo> getChoicetypeList() {
        return choicetypeList;
    }

    public void setChoicetypeList(List<PaperChoicetypeLinkVo> choicetypeList) {
        this.choicetypeList = choicetypeList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPapertypeId() {
        return papertypeId;
    }

    public void setPapertypeId(long papertypeId) {
        this.papertypeId = papertypeId;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public int getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(int limitedTime) {
        this.limitedTime = limitedTime;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public Object getMinTotalScore() {
        return minTotalScore;
    }

    public void setMinTotalScore(Object minTotalScore) {
        this.minTotalScore = minTotalScore;
    }

    public Object getMaxTotalScore() {
        return maxTotalScore;
    }

    public void setMaxTotalScore(Object maxTotalScore) {
        this.maxTotalScore = maxTotalScore;
    }

    public float getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(float passingScore) {
        this.passingScore = passingScore;
    }

    public Object getDeterminant() {
        return determinant;
    }

    public void setDeterminant(Object determinant) {
        this.determinant = determinant;
    }

    public Object getMoment() {
        return moment;
    }

    public void setMoment(Object moment) {
        this.moment = moment;
    }

    public int getCanDownload() {
        return canDownload;
    }

    public void setCanDownload(int canDownload) {
        this.canDownload = canDownload;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Object startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Object getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Object endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Object getPapertypeName() {
        return papertypeName;
    }

    public void setPapertypeName(Object papertypeName) {
        this.papertypeName = papertypeName;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }
}
