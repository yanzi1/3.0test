package com.me.data.model.intel;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_intel_workreview")
public class IntelWorkReview implements MultiItemEntity,Serializable {
    @Id
    private int dbId;
    private String id;
    private String subjectId;
    private String type;//复习包类型：1错题，2节点
    private String nodeName;
    private String nodeId;
    private String reviewStatus;//复习状态：1完成，2未完成
    private String lectureUrl;
    private String memberId;
    
    private String createDate;
    private String updateDate;
    private String publishDate;
    //自己加的
    private String sSubjectId;
    private int openState;//开课状态 1.还未到开课时间 2.还未入学测试 3.还没有内容 3.正常
    private String openDate;
    private String openMsg;
    private List<String> choicetypes;
    private List<String> reviewQuestionList;
    private boolean isToday;//是否可以点击跳转至复习包
    //后续添加list位置
    private int position;

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<String> getChoicetypes() {
        return choicetypes;
    }

    public void setChoicetypes(List<String> choicetypes) {
        this.choicetypes = choicetypes;
    }

    public List<String> getReviewQuestionList() {
        return reviewQuestionList;
    }

    public void setReviewQuestionList(List<String> reviewQuestionList) {
        this.reviewQuestionList = reviewQuestionList;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getOpenMsg() {
        return openMsg;
    }

    public void setOpenMsg(String openMsg) {
        this.openMsg = openMsg;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
