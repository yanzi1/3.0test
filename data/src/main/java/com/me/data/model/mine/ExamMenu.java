package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/1.
 */

public class ExamMenu {

    /**
     * memberSubjectList : [{"memberSeasonSubjectList":[{"sSubjectServeList":"","sSubjectId":"183","sSubjectYear":"2017","sSubjectName":"APP审计2017全国"}],"subjectName":"APP审计","subjectId":"175","serveEndDate":"","serveStartDate":""},{"memberSeasonSubjectList":[{"sSubjectServeList":"","sSubjectId":"182","sSubjectYear":"2017","sSubjectName":"APP会计2017全国"}],"subjectName":"APP会计","subjectId":"174","serveEndDate":"","serveStartDate":""}]
     * examId : 120
     * examName : APP注册会计师（移动端专属，勿动）
     */

    private String examId;
    private String examName;
    private List<SubjectMenu> memberSubjectList;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<SubjectMenu> getMemberSubjectList() {
        return memberSubjectList;
    }

    public void setMemberSubjectList(List<SubjectMenu> memberSubjectList) {
        this.memberSubjectList = memberSubjectList;
    }

    public static class SubjectMenu {
        /**
         * memberSeasonSubjectList : [{"sSubjectServeList":"","sSubjectId":"183","sSubjectYear":"2017","sSubjectName":"APP审计2017全国"}]
         * subjectName : APP审计
         * subjectId : 175
         * serveEndDate :
         * serveStartDate :
         */

        private String subjectName;
        private String subjectId;
        private String serveEndDate;
        private String serveStartDate;
        private List<SeasonMenu> memberSeasonSubjectList;

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getServeEndDate() {
            return serveEndDate;
        }

        public void setServeEndDate(String serveEndDate) {
            this.serveEndDate = serveEndDate;
        }

        public String getServeStartDate() {
            return serveStartDate;
        }

        public void setServeStartDate(String serveStartDate) {
            this.serveStartDate = serveStartDate;
        }

        public List<SeasonMenu> getMemberSeasonSubjectList() {
            return memberSeasonSubjectList;
        }

        public void setMemberSeasonSubjectList(List<SeasonMenu> memberSeasonSubjectList) {
            this.memberSeasonSubjectList = memberSeasonSubjectList;
        }

        public static class SeasonMenu {
            /**
             * sSubjectServeList :
             * sSubjectId : 183
             * sSubjectYear : 2017
             * sSubjectName : APP审计2017全国
             */

            private String sSubjectServeList;
            private String sSubjectId;
            private String sSubjectYear;
            private String sSubjectName;

            public String getSSubjectServeList() {
                return sSubjectServeList;
            }

            public void setSSubjectServeList(String sSubjectServeList) {
                this.sSubjectServeList = sSubjectServeList;
            }

            public String getSSubjectId() {
                return sSubjectId;
            }

            public void setSSubjectId(String sSubjectId) {
                this.sSubjectId = sSubjectId;
            }

            public String getSSubjectYear() {
                return sSubjectYear;
            }

            public void setSSubjectYear(String sSubjectYear) {
                this.sSubjectYear = sSubjectYear;
            }

            public String getSSubjectName() {
                return sSubjectName;
            }

            public void setSSubjectName(String sSubjectName) {
                this.sSubjectName = sSubjectName;
            }
        }
    }
}
