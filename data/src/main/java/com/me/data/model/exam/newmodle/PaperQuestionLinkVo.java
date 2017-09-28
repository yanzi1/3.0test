package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/5/25 0025.
 * 试卷试题对象
 */

public class PaperQuestionLinkVo {

    /**
     * id : 试卷与试题关联的ID
     * paperId : 试卷id
     * paperChoiceTypeId : 段id
     * questionId : 试题ID
     * isGroup : 	是否题帽题
     * groupId : 题帽ID
     * score : 本题分值
     * seasonQuestionVo : 试题对象（如题帽题则是完整的题帽加小题）
     * errorId : 错题ID
     * isCollectd : 是否收藏
     * collectId : 	收藏ID
     * errorTimes : 错误次数
     *
     * PaperQuestionLinkVo 未标注字段
     * sort : null
     */

    private long id;
    private long paperId;
    private long paperChoiceTypeId;
    private long questionId;
    private int isGroup;
    private long groupId;
    private int sort;
    private float score;
    private SeasonQuestionVo seasonQuestionVo;
    /**
     * 以下字段在我的错题是才给出具体值
     */
    private String errorId;
    private boolean isCollectd;
    private String collectId;
    private int errorTimes;
    /**
     *自增字段
     */
    private int indexInCurrentTypeListWrong;//这道题在查看错题解析时所在该类型题目中的角标
    private int indexInAllQuesList;//这道题在所有题中的角标(不包含题冒题)
    private String paperSectionId;//试卷段id
    private int positionInList;

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public String getPaperSectionId() {
        return paperSectionId;
    }

    public void setPaperSectionId(String paperSectionId) {
        this.paperSectionId = paperSectionId;
    }

    public boolean isCollectd() {
        return isCollectd;
    }

    public void setCollectd(boolean collectd) {
        isCollectd = collectd;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public int getIndexInCurrentTypeListWrong() {
        return indexInCurrentTypeListWrong;
    }

    public void setIndexInCurrentTypeListWrong(int indexInCurrentTypeListWrong) {
        this.indexInCurrentTypeListWrong = indexInCurrentTypeListWrong;
    }

    public int getIndexInAllQuesList() {
        return indexInAllQuesList;
    }

    public void setIndexInAllQuesList(int indexInAllQuesList) {
        this.indexInAllQuesList = indexInAllQuesList;
    }

    public SeasonQuestionVo getSeasonQuestionVo() {
        return seasonQuestionVo;
    }

    public void setSeasonQuestionVo(SeasonQuestionVo seasonQuestionVo) {
        this.seasonQuestionVo = seasonQuestionVo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public long getPaperChoiceTypeId() {
        return paperChoiceTypeId;
    }

    public void setPaperChoiceTypeId(long paperChoiceTypeId) {
        this.paperChoiceTypeId = paperChoiceTypeId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
