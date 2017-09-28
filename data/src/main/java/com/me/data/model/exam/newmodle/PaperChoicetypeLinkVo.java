package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/5/25 0025.
 * 试卷段信息对象
 */

public class PaperChoicetypeLinkVo {

    /**
     * id : 试卷段id
     * paperId : 试卷id
     * choicetypeId : 题型id
     * name : 段名称-默认题型显示名
     * score : 分值
     * questionCount : 题型下的试题数量
     * description : 描述
     * questionList : 试卷里面该段下面配置的试题信息列表，列表中的对象为PaperQuestionLinkVo类型
     *
     * PaperChoicetypeLinkVo 未标注字段
     * sort : null
     * examSection : null
     * subObjType : null
     */

    private long id;
    private long paperId;
    private long choicetypeId;
    private String name;
    private float score;
    private int questionCount;
    private int sort;
    private String description;
    private Object examSection;
    private Object subObjType;
    private List<PaperQuestionLinkVo> questionList;

    public List<PaperQuestionLinkVo> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<PaperQuestionLinkVo> questionList) {
        this.questionList = questionList;
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

    public long getChoicetypeId() {
        return choicetypeId;
    }

    public void setChoicetypeId(long choicetypeId) {
        this.choicetypeId = choicetypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getExamSection() {
        return examSection;
    }

    public void setExamSection(Object examSection) {
        this.examSection = examSection;
    }

    public Object getSubObjType() {
        return subObjType;
    }

    public void setSubObjType(Object subObjType) {
        this.subObjType = subObjType;
    }
}
