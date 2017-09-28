package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class NotesExercisesList {

    /**
     * list : [{"chapterId":1,"choicetypeId":1,"content":"1","id":110,"questionId":2,"sSubjectId":1,"showTime":"2017-05-31 15:02","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":92,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 16:14","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":91,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 16:05","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":90,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 16:05","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":89,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 16:04","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":88,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 16:04","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":1,"content":"1","id":87,"questionId":1,"sSubjectId":1,"showTime":"2017-05-27 15:59","subjectId":1,"title":"1"},{"chapterId":1,"choicetypeId":16,"content":"这是一条测试记录","id":70,"questionId":77,"sSubjectId":1,"showTime":"2016-12-01 20:58","subjectId":1,"title":"这是一个测试记录"},{"chapterId":1,"choicetypeId":1,"content":"adfadfa","id":51,"questionId":89,"sSubjectId":1,"showTime":"2016-11-05 16:30","subjectId":1,"title":"adfadfa"},{"chapterId":1,"choicetypeId":17,"content":"adfadf","id":50,"questionId":79,"sSubjectId":1,"showTime":"2016-11-05 16:29","subjectId":1,"title":"adf"}]
     * count : 15
     */

    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * chapterId : 1
         * choicetypeId : 1   题型
         * content : 1
         * id : 110
         * questionId : 2
         * sSubjectId : 1
         * showTime : 2017-05-31 15:02
         * subjectId : 1
         * title : 1
         */

        private int chapterId;
        private int choicetypeId;
        private String content;
        private int id;
        private int questionId;
        private int sSubjectId;
        private String showTime;
        private int subjectId;
        private String title;

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public int getChoicetypeId() {
            return choicetypeId;
        }

        public void setChoicetypeId(int choicetypeId) {
            this.choicetypeId = choicetypeId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public int getSSubjectId() {
            return sSubjectId;
        }

        public void setSSubjectId(int sSubjectId) {
            this.sSubjectId = sSubjectId;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
