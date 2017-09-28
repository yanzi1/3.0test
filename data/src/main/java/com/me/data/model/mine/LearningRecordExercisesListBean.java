package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class LearningRecordExercisesListBean {

    /**
     * count : 41
     * list : [{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"2","score":"7.0","detailList":"","endTime":"1497265365995","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497265364703","sSubjectId":"183","id":"3498","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"1","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"1","score":"4.0","detailList":"","endTime":"1497265339106","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497265340202","sSubjectId":"183","id":"3497","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"1","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"2","score":"5.0","detailList":"","endTime":"1497264968871","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497264960772","sSubjectId":"183","id":"3496","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"1","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"2","score":"8.0","detailList":"","endTime":"1497264931324","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497264937126","sSubjectId":"183","id":"3495","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"2","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"2","score":"8.0","detailList":"","endTime":"1497264478206","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497264483451","sSubjectId":"183","id":"3494","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"2","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"2","score":"8.0","detailList":"","endTime":"1497264315748","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497264315687","sSubjectId":"183","id":"3493","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"2","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"2.0","paperId":"245","paperName":"APP审计2017随堂练习（二）","sectionList":"","status":"1","answerNum":"1","score":"0.0","detailList":"","endTime":"1497262574336","totleNum":"1","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497262574912","sSubjectId":"183","id":"3492","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"5000","memberId":"333","rightNum":"0","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"236","paperName":"APP审计2017随堂练习（一）","sectionList":"","status":"1","answerNum":"0","score":"0.0","detailList":"","endTime":"1497264257777","totleNum":"15","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497261609311","sSubjectId":"183","id":"3491","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"75000","memberId":"333","rightNum":"0","commitType":"2","papertypeName":"APP随堂练习"},{"platformCode":"1","examModel":"1","totalScore":"2.0","paperId":"246","paperName":"APP审计2017随堂练习（三）","sectionList":"","status":"1","answerNum":"0","score":"0.0","detailList":"","endTime":"1497259426235","totleNum":"1","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497259406780","sSubjectId":"183","id":"3488","papertypeId":"128","matchId":"","subjectId":"175","timeCost":"11917","memberId":"333","rightNum":"0","commitType":"1","papertypeName":"APP随堂练习"},{"platformCode":"12","examModel":"1","totalScore":"100.0","paperId":"257","paperName":"APP审计2017历年真题（一）","sectionList":"","status":"1","answerNum":"1","score":"1.0","detailList":"","endTime":"1497257348222","totleNum":"24","sSubjectName":"APP审计2017全国","endTimeStr":"2017-06-12","startTimeStr":"2017-06-12","startTime":"1497257334957","sSubjectId":"183","id":"3478","papertypeId":"126","matchId":"","subjectId":"175","timeCost":"120000","memberId":"333","rightNum":"0","commitType":"1","papertypeName":"APP历年真题"}]
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
         * platformCode : 12
         * examModel : 1
         * totalScore : 100.0
         * paperId : 236
         * paperName : APP审计2017随堂练习（一）
         * sectionList :
         * status : 1
         * answerNum : 2
         * score : 7.0
         * detailList :
         * endTime : 1497265365995
         * totleNum : 15
         * sSubjectName : APP审计2017全国
         * endTimeStr : 2017-06-12
         * startTimeStr : 2017-06-12
         * startTime : 1497265364703
         * sSubjectId : 183
         * id : 3498
         * papertypeId : 128
         * matchId :
         * subjectId : 175
         * timeCost : 75000
         * memberId : 333
         * rightNum : 1
         * commitType : 1
         * papertypeName : APP随堂练习
         */

        private String platformCode;
        private String examModel;
        private String totalScore;
        private String paperId;
        private String paperName;
        private String sectionList;
        private String status;
        private String answerNum;
        private String score;
        private String detailList;
        private String endTime;
        private String totleNum;
        private String sSubjectName;
        private String endTimeStr;
        private String startTimeStr;
        private String startTime;
        private String sSubjectId;
        private String id;
        private String papertypeId;
        private String matchId;
        private String subjectId;
        private String timeCost;
        private String memberId;
        private String rightNum;
        private String commitType;
        private String papertypeName;

        public String getPlatformCode() {
            return platformCode;
        }

        public void setPlatformCode(String platformCode) {
            this.platformCode = platformCode;
        }

        public String getExamModel() {
            return examModel;
        }

        public void setExamModel(String examModel) {
            this.examModel = examModel;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getPaperName() {
            return paperName;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
        }

        public String getSectionList() {
            return sectionList;
        }

        public void setSectionList(String sectionList) {
            this.sectionList = sectionList;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAnswerNum() {
            return answerNum;
        }

        public void setAnswerNum(String answerNum) {
            this.answerNum = answerNum;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getDetailList() {
            return detailList;
        }

        public void setDetailList(String detailList) {
            this.detailList = detailList;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTotleNum() {
            return totleNum;
        }

        public void setTotleNum(String totleNum) {
            this.totleNum = totleNum;
        }

        public String getSSubjectName() {
            return sSubjectName;
        }

        public void setSSubjectName(String sSubjectName) {
            this.sSubjectName = sSubjectName;
        }

        public String getEndTimeStr() {
            return endTimeStr;
        }

        public void setEndTimeStr(String endTimeStr) {
            this.endTimeStr = endTimeStr;
        }

        public String getStartTimeStr() {
            return startTimeStr;
        }

        public void setStartTimeStr(String startTimeStr) {
            this.startTimeStr = startTimeStr;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getSSubjectId() {
            return sSubjectId;
        }

        public void setSSubjectId(String sSubjectId) {
            this.sSubjectId = sSubjectId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPapertypeId() {
            return papertypeId;
        }

        public void setPapertypeId(String papertypeId) {
            this.papertypeId = papertypeId;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getTimeCost() {
            return timeCost;
        }

        public void setTimeCost(String timeCost) {
            this.timeCost = timeCost;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getRightNum() {
            return rightNum;
        }

        public void setRightNum(String rightNum) {
            this.rightNum = rightNum;
        }

        public String getCommitType() {
            return commitType;
        }

        public void setCommitType(String commitType) {
            this.commitType = commitType;
        }

        public String getPapertypeName() {
            return papertypeName;
        }

        public void setPapertypeName(String papertypeName) {
            this.papertypeName = papertypeName;
        }
    }
}
