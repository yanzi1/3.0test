package com.me.data.model.mall;

import java.util.List;

/**
 * 商城首页 类型
 * Created by mayunfei on 17-5-5.
 */

public class Category {


    /**
     * subject : [{"subjectName":"初级职称下分类1","catPid":"295"},{"subjectName":"注会基础","catPid":"161"},{"subjectName":"经济法基础","catPid":"115"},{"subjectName":"初级会计实务","catPid":"114"}]
     * examId : 112
     * examName : 初级
     * catPid : 112
     */

    private int examId;
    private String examName;
    private int catPid;
    private List<SubjectBean> subject;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getCatPid() {
        return catPid;
    }

    public void setCatPid(int catPid) {
        this.catPid = catPid;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

    public static class SubjectBean {
        /**
         * subjectName : 初级职称下分类1
         * catPid : 295
         */

        private String subjectName;
        private int catPid;

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public int getCatPid() {
            return catPid;
        }

        public void setCatPid(int catPid) {
            this.catPid = catPid;
        }
    }
}
