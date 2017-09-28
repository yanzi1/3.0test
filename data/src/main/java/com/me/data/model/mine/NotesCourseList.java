package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class NotesCourseList {

    /**
     * count : 35
     * noteList : [{"chapterId":"","chapterIds":"","chapterNames":"","content":"笔记内容","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:59:25","hanConId":"4553","id":"428","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"儿童节","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"笔记内容","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:49:40","hanConId":"4553","id":"427","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"儿童节","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"笔记内容","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:27:46","hanConId":"4553","id":"426","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"儿童节","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"老师问大家一起的时光\u2026\u2026在","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:04:59","hanConId":"4553","id":"425","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的话？我","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"我说过话是说你有本事你们说","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:02:36","hanConId":"4553","id":"424","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"我说那么多数钱数到五分钟不到自己","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 14:00:21","hanConId":"4553","id":"423","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师在课堂","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"老师问学生在老师指导思想","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 13:57:18","hanConId":"4553","id":"422","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的一","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"老师们辛苦我想吃火锅时记得","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 13:50:39","hanConId":"4553","id":"421","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"老师们辛苦我想吃火锅时记得","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 13:49:06","hanConId":"4553","id":"420","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的","updateBy":"","updateDate":""},{"chapterId":"","chapterIds":"","chapterNames":"","content":"老师们辛苦我想吃火锅时记得","courseId":"174","coursewareTime":"00:00:00","createBy":"15501063380","createDate":"2017-06-03 13:47:53","hanConId":"4553","id":"419","imgPaths":"","knowName":"","lectrueId":"250","memberId":"65","picPaths":"","platformCode":"","sSubjectId":"","serverEndTime":"","serverStartTime":"","subjectId":"","title":"老师的","updateBy":"","updateDate":""}]
     */

    private String count;
    private List<NoteListBean> noteList;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<NoteListBean> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<NoteListBean> noteList) {
        this.noteList = noteList;
    }

    public static class NoteListBean {
        /**
         * chapterId :
         * chapterIds :
         * chapterNames :
         * content : 笔记内容
         * courseId : 174
         * coursewareTime : 00:00:00
         * createBy : 15501063380
         * createDate : 2017-06-03 14:59:25
         * hanConId : 4553
         * id : 428
         * imgPaths :
         * knowName :
         * lectrueId : 250
         * memberId : 65
         * picPaths :
         * platformCode :
         * sSubjectId :
         * serverEndTime :
         * serverStartTime :
         * subjectId :
         * title : 儿童节
         * updateBy :
         * updateDate :
         */

        private String chapterId;
        private String chapterIds;
        private String chapterNames;
        private String content;
        private String courseId;
        private String coursewareTime;
        private String createBy;
        private String createDate;
        private String hanConId;
        private String id;
        private String imgPaths;
        private String knowName;
        private String lectrueId;
        private String memberId;
        private String picPaths;
        private String platformCode;
        private String sSubjectId;
        private String serverEndTime;
        private String serverStartTime;
        private String subjectId;
        private String title;
        private String updateBy;
        private String updateDate;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterIds() {
            return chapterIds;
        }

        public void setChapterIds(String chapterIds) {
            this.chapterIds = chapterIds;
        }

        public String getChapterNames() {
            return chapterNames;
        }

        public void setChapterNames(String chapterNames) {
            this.chapterNames = chapterNames;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCoursewareTime() {
            return coursewareTime;
        }

        public void setCoursewareTime(String coursewareTime) {
            this.coursewareTime = coursewareTime;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getHanConId() {
            return hanConId;
        }

        public void setHanConId(String hanConId) {
            this.hanConId = hanConId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgPaths() {
            return imgPaths;
        }

        public void setImgPaths(String imgPaths) {
            this.imgPaths = imgPaths;
        }

        public String getKnowName() {
            return knowName;
        }

        public void setKnowName(String knowName) {
            this.knowName = knowName;
        }

        public String getLectrueId() {
            return lectrueId;
        }

        public void setLectrueId(String lectrueId) {
            this.lectrueId = lectrueId;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getPicPaths() {
            return picPaths;
        }

        public void setPicPaths(String picPaths) {
            this.picPaths = picPaths;
        }

        public String getPlatformCode() {
            return platformCode;
        }

        public void setPlatformCode(String platformCode) {
            this.platformCode = platformCode;
        }

        public String getSSubjectId() {
            return sSubjectId;
        }

        public void setSSubjectId(String sSubjectId) {
            this.sSubjectId = sSubjectId;
        }

        public String getServerEndTime() {
            return serverEndTime;
        }

        public void setServerEndTime(String serverEndTime) {
            this.serverEndTime = serverEndTime;
        }

        public String getServerStartTime() {
            return serverStartTime;
        }

        public void setServerStartTime(String serverStartTime) {
            this.serverStartTime = serverStartTime;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }
}
