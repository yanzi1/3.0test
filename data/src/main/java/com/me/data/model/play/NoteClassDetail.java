package com.me.data.model.play;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenpeng on 2016/5/30 0030.
 * 讲
 */
public class NoteClassDetail implements Serializable {

    private String lectrueId;
    private String updateBy;
    private String serverStartTime;
    private String platformCode;
    private String knowName;
    private String courseId;
    private String createBy;

    private String picPaths;
    private String updateDate;

    private String sSubjectId;
    private String id;
    private String content;
    private String title;
    private String chapterId;
    private String chapterNames;
    private String hanConId;
    private String coursewareTime;
    private String subjectId;
    private String chapterIds;
    private String memberId;
    private String createDate;
    private String serverEndTime;
    private String hdDetailUrl;

    private List<String> imgPaths;

    public String getHdDetailUrl() {
        return hdDetailUrl;
    }

    public void setHdDetailUrl(String hdDetailUrl) {
        this.hdDetailUrl = hdDetailUrl;
    }

    public String getLectrueId() {
        return lectrueId;
    }

    public void setLectrueId(String lectrueId) {
        this.lectrueId = lectrueId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getServerStartTime() {
        return serverStartTime;
    }

    public void setServerStartTime(String serverStartTime) {
        this.serverStartTime = serverStartTime;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getKnowName() {
        return knowName;
    }

    public void setKnowName(String knowName) {
        this.knowName = knowName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getPicPaths() {
        return picPaths;
    }

    public void setPicPaths(String picPaths) {
        this.picPaths = picPaths;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterNames() {
        return chapterNames;
    }

    public void setChapterNames(String chapterNames) {
        this.chapterNames = chapterNames;
    }

    public String getHanConId() {
        return hanConId;
    }

    public void setHanConId(String hanConId) {
        this.hanConId = hanConId;
    }

    public String getCoursewareTime() {
        return coursewareTime;
    }

    public void setCoursewareTime(String coursewareTime) {
        this.coursewareTime = coursewareTime;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(String chapterIds) {
        this.chapterIds = chapterIds;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getServerEndTime() {
        return serverEndTime;
    }

    public void setServerEndTime(String serverEndTime) {
        this.serverEndTime = serverEndTime;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }
}
