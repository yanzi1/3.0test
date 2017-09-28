package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

/**
 * Created by fengzongwei on 2016/5/11 0011.
 * 我的收藏实体
 */ 
@Table(name="t_persenal_collection")
public class MyCollection {
    public static final String TYPE_EXAMNATION = 1+"",TYPE_QUESTION = 2+"" ,TYPE_VEDIO = 3+"",TYPE_WEB = 4+"";
    @Id
    private int dbid;
    private String userId;//用户Id
    private String year;//接口改变，之前收藏的视频id跟之后的不一样
    private String time;//收藏时间  long值
    private String type;//收藏类型  1.题  2.答疑 3.视频 4.web文件
    private String title;//主名称
    private String searchKey;//搜索关键字
    private String content;//JSON内容体
    private String collectionId;//此条收藏的id(试题：subjectId(科目)_试卷Id_试题id  视频: 班次id_讲id  答疑：答疑id)
    private String imageUrl;//图片地址
    
    //新添加的字段
    private String examId; //考试Id
    private String subjectId; //考试科目Id
    private String examinationId; //试卷Id
    private String classsId;//班次Id
    private String sectionId;//章节id
    private String cwId;//讲id
    private String questionId;//试题id
    private String answerId;//答疑id
    private String typeId;//试题类型
    private String mainType;//试卷类型(历年真题、知识点练习、课后作业、随堂练习)

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getClasssId() {
        return classsId;
    }

    public void setClasssId(String classsId) {
        this.classsId = classsId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getCwId() {
        return cwId;
    }

    public void setCwId(String cwId) {
        this.cwId = cwId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
