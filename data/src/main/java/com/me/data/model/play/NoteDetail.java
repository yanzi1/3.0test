package com.me.data.model.play;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenpeng on 2016/5/30 0030.
 * 讲
 */
public class NoteDetail implements Serializable {

    private String id;
    private String memberId;
    private String subjectId;
    private String sSubjectId;
    private String chapterId;
    private String questionId;
    private String choicetypeId;

    private String title;
    private String content;

    private String createTime;
    private String updateTime;
    private String showTime;
    private List<String> urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getChoicetypeId() {
        return choicetypeId;
    }

    public void setChoicetypeId(String choicetypeId) {
        this.choicetypeId = choicetypeId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
