package com.me.data.model.query;

import java.io.Serializable;

/**
 * Created by wyc
 */
public class QueryFragmentBean implements Serializable{
    private String id;
    private int answerStatus;//0=未回答1=已回答
    private String createDate;//答疑创建时间
    private String answerDate;//答疑回答时间
    private String endDate;//预计回答时间
    private String title;//标题
    private String content;
    //创建人
    private String createBy;//创建人
    private String knowledgeIds;
    private String knowledgeNames;
    private int qaType;//问题出处的类型 :1=试题 2= 图书，3课件，4教辅
    private String essence;//精华状态:0=非精华 1=精华
    private int readStatus;//读取状态:0=新建1=已回复未读3=已读
    
    public QueryFragmentBean() {
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getKnowledgeIds() {
        return knowledgeIds;
    }

    public void setKnowledgeIds(String knowledgeIds) {
        this.knowledgeIds = knowledgeIds;
    }

    public String getKnowledgeNames() {
        return knowledgeNames;
    }

    public void setKnowledgeNames(String knowledgeNames) {
        this.knowledgeNames = knowledgeNames;
    }

    public int getQaType() {
        return qaType;
    }

    public void setQaType(int qaType) {
        this.qaType = qaType;
    }

    public String getEssence() {
        return essence;
    }

    public void setEssence(String essence) {
        this.essence = essence;
    }
}
