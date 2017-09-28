package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/6/8.
 */

public class StudentsEvaluateInfo {

    /**
     * allCount : 1
     * reasonableScore : 4.0
     * lectureId : 173
     * list : [{"content":"非常好222","id":"14","username":"182****1649","avatarSmall":"http://static.front.test.com/v1.0/static/common/img/user.png","lectureId":"173","memberId":"1764","createDate":"2017-05-25 15:22:14","createBy":"18243011649"}]
     * beautifulScore : 4.0
     * attractScore : 4.0
     */

    private String allCount;
    private String reasonableScore;
    private String lectureId;
    private String beautifulScore;
    private String attractScore;
    private List<ListBean> list;

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getReasonableScore() {
        return reasonableScore;
    }

    public void setReasonableScore(String reasonableScore) {
        this.reasonableScore = reasonableScore;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getBeautifulScore() {
        return beautifulScore;
    }

    public void setBeautifulScore(String beautifulScore) {
        this.beautifulScore = beautifulScore;
    }

    public String getAttractScore() {
        return attractScore;
    }

    public void setAttractScore(String attractScore) {
        this.attractScore = attractScore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * content : 非常好222
         * id : 14
         * username : 182****1649
         * avatarSmall : http://static.front.test.com/v1.0/static/common/img/user.png
         * lectureId : 173
         * memberId : 1764
         * createDate : 2017-05-25 15:22:14
         * createBy : 18243011649
         */

        private String content;
        private String id;
        private String username;
        private String avatarSmall;
        private String lectureId;
        private String memberId;
        private String createDate;
        private String createBy;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatarSmall() {
            return avatarSmall;
        }

        public void setAvatarSmall(String avatarSmall) {
            this.avatarSmall = avatarSmall;
        }

        public String getLectureId() {
            return lectureId;
        }

        public void setLectureId(String lectureId) {
            this.lectureId = lectureId;
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

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }
    }
}
