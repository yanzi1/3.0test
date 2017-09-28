package com.me.data.model.query;

import java.io.Serializable;

/**
 * Created by wyc
 */
public class QueryAddKnowledgeBean implements Serializable {
    private String id;//	试题id
    private String sSubjectId;//考级id
    private String bookId;//书id
    private String chapterId;//章id
    private int pageNum;//页码
    private String kpId;//知识点id
    private String showName;//名称
    private String shortName;//知识点简称
    private String questionNum;//试题的题号
    //自增  类型 0 知识点 1 试题
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getKpId() {
        return kpId;
    }

    public void setKpId(String kpId) {
        this.kpId = kpId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
