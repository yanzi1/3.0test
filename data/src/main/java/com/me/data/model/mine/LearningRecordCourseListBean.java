package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class LearningRecordCourseListBean {

    /**
     * count : 128
     * recordList : [{"sSubjectId":"136","lectrueId":"273","chapterName":"第一章 战略与战略管理","memberId":"65","lectureOrder":"第07讲","courseId":"173","createDate":"2017-06-05 18:02:02","endTime":"00:05:13","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"顶顶顶","courseName":"张志凤基础班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-02 18:33:04","endTime":"00:54:56","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-02 17:39:57","endTime":"00:54:57","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-02 17:39:11","endTime":"00:47:12","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-02 15:01:06","endTime":"00:46:41","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-01 21:04:27","endTime":"00:45:55","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"250","chapterName":"第一章 战略与战略管理","memberId":"65","lectureOrder":"第一讲","courseId":"174","createDate":"2017-06-01 18:42:55","endTime":"00:09:31","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"自我介绍","courseName":"刘圣妮基础班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-01 18:32:12","endTime":"00:42:33","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-01 18:20:25","endTime":"00:35:47","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"},{"sSubjectId":"136","lectrueId":"282","chapterName":"","memberId":"65","lectureOrder":"第01讲","courseId":"207","createDate":"2017-06-01 17:59:33","endTime":"00:35:46","sSubjectName":"T公司战略与风险管理2017全国","lectureName":"特殊交易的会计处理","courseName":"张志凤/刘圣妮分类专题班"}]
     */

    private String count;
    private List<RecordListBean> recordList;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class RecordListBean {
        /**
         * sSubjectId : 136
         * lectrueId : 273
         * chapterName : 第一章 战略与战略管理
         * memberId : 65
         * lectureOrder : 第07讲
         * courseId : 173
         * createDate : 2017-06-05 18:02:02
         * endTime : 00:05:13
         * sSubjectName : T公司战略与风险管理2017全国
         * lectureName : 顶顶顶
         * courseName : 张志凤基础班
         */

        private String sSubjectId;
        private String lectrueId;
        private String chapterName;
        private String memberId;
        private String lectureOrder;
        private String courseId;
        private String createDate;
        private String endTime;
        private String sSubjectName;
        private String lectureName;
        private String courseName;

        public String getSSubjectId() {
            return sSubjectId;
        }

        public void setSSubjectId(String sSubjectId) {
            this.sSubjectId = sSubjectId;
        }

        public String getLectrueId() {
            return lectrueId;
        }

        public void setLectrueId(String lectrueId) {
            this.lectrueId = lectrueId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getLectureOrder() {
            return lectureOrder;
        }

        public void setLectureOrder(String lectureOrder) {
            this.lectureOrder = lectureOrder;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getSSubjectName() {
            return sSubjectName;
        }

        public void setSSubjectName(String sSubjectName) {
            this.sSubjectName = sSubjectName;
        }

        public String getLectureName() {
            return lectureName;
        }

        public void setLectureName(String lectureName) {
            this.lectureName = lectureName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
