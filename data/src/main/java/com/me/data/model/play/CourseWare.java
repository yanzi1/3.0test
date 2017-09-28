package com.me.data.model.play;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wenpeng on 2016/5/30 0030.
 * 讲
 */
public class CourseWare implements Serializable {

    private String id;//课件ID
    private String name;//课件名字
    private String lectureOrder;//序号，本课件在所属课程中是第几讲
    private String mobileDownloadUrl;//移动端课件下载地址
    private String mobileVideoUrl;//移动端课件听课地址
    private String mobileVideoUrlLD;//流畅播放地址
    private String mobileVideoMP3;//MP3播放地址
    private String mobileLectureUrl;//移动端讲义地址
    private String totalTime;//时间长度 单位是秒
    private String practiceExaminationType;//随堂练习试卷类型
    private String paperId;//试卷Id(如果这个讲有课后作业的话则有此id)

    private String classId;//班次id
    private String year;//年份  在此版本中没怎么用
//    private String cwBean;//此courseWare的json串

    private String sSubjectId;//考季Id
    private String sSubjectName;//考季名称
    private String picPath;
    private String className;

    private String examId;//考试Id
    private String sectionId;//章节id

    private String startTime;
    private String endTime;
    private String tagName;
    private String cwVersion;
    private Course courseDto;
    private boolean isPlayFinished;
    private String userId;
    private String jm;
    private List<CwClarity> claritys;//视频有几种清晰度
    private String qualityName;
    private boolean isCollect;
    private int sort;
    private String status;//0:未掌握 1.已掌握 2.未学习
    private String lectureId;

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getsSubjectName() {
        return sSubjectName;
    }

    public void setsSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public boolean isIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean collect) {
        isCollect = collect;
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public List<CwClarity> getClaritys() {
        return claritys;
    }

    public void setClaritys(List<CwClarity> claritys) {
        this.claritys = claritys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLectureOrder() {
        return lectureOrder;
    }

    public void setLectureOrder(String lectureOrder) {
        this.lectureOrder = lectureOrder;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getMobileVideoMP3() {
        return mobileVideoMP3;
    }

    public void setMobileVideoMP3(String mobileVideoMP3) {
        this.mobileVideoMP3 = mobileVideoMP3;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getMobileVideoUrlLD() {
        return mobileVideoUrlLD;
    }

    public void setMobileVideoUrlLD(String mobileVideoUrlLD) {
        this.mobileVideoUrlLD = mobileVideoUrlLD;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isPlayFinished() {
        return isPlayFinished;
    }

    public void setIsPlayFinished(boolean isPlayFinished) {
        this.isPlayFinished = isPlayFinished;
    }


    public String getPracticeExaminationType() {
        return practiceExaminationType;
    }

    public void setPracticeExaminationType(String practiceExaminationType) {
        this.practiceExaminationType = practiceExaminationType;
    }

    public Course getCourseDto() {
        return courseDto;
    }

    public void setCourseDto(Course courseDto) {
        this.courseDto = courseDto;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCwVersion() {
        return cwVersion;
    }

    public void setCwVersion(String cwVersion) {
        this.cwVersion = cwVersion;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getMobileDownloadUrl() {
        return mobileDownloadUrl;
    }

    public void setMobileDownloadUrl(String mobileDownloadUrl) {
        this.mobileDownloadUrl = mobileDownloadUrl;
    }

    public String getMobileVideoUrl() {
        return mobileVideoUrl;
    }

    public void setMobileVideoUrl(String mobileVideoUrl) {
        this.mobileVideoUrl = mobileVideoUrl;
    }

    public String getMobileLectureUrl() {
        return mobileLectureUrl;
    }

    public void setMobileLectureUrl(String mobileLectureUrl) {
        this.mobileLectureUrl = mobileLectureUrl;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CourseWare) {
            CourseWare courseWare = (CourseWare) o;
            return this.id.equals(courseWare.getId());
        }
        return super.equals(o);
    }

}
