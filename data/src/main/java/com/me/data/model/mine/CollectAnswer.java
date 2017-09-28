package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/3.
 */

public class CollectAnswer {

    /**
     * list : [{"answerDate":"","answerStatus":"0","childrenNum":"","content":"1111231231231231231","createBy":"18511866554","createDate":"2017-06-02 09:22:22","endDate":"2017-06-03 09:22:22","essence":"0","id":"10001070","knowledgeIds":"","knowledgeNames":"","myContent":"","qaType":"2","readStatus":"0","title":"2017战略 题目答疑 11261526"},{"answerDate":"2017-05-26 17:14:04","answerStatus":"1","childrenNum":"","content":"1、几点几水电费合适的2、的见风使舵净空法师3、收到会色机考的","createBy":"lv2001","createDate":"2017-05-26 11:38:28","endDate":"2017-05-26 12:38:28","essence":"0","id":"10001014","knowledgeIds":"85871","knowledgeNames":"法律法规存在的目的","myContent":"","qaType":"3","readStatus":"1","title":"法律存在的目的知识点课程来的"},{"answerDate":"2016-11-28 15:40:33","answerStatus":"1","childrenNum":"","content":"提问具体内容：这是252讲次","createBy":"17011111115","createDate":"2016-11-28 15:29:20","endDate":"2016-11-29 15:29:20","essence":"0","id":"10000080","knowledgeIds":"85861","knowledgeNames":"公司战略的传统概念（波特）2017","myContent":"","qaType":"3","readStatus":"3","title":"公司战略的传统概念（波特）2017"}]
     * count : 3
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
         * answerDate :
         * answerStatus : 0
         * childrenNum :
         * content : 1111231231231231231
         * createBy : 18511866554
         * createDate : 2017-06-02 09:22:22
         * endDate : 2017-06-03 09:22:22
         * essence : 0
         * id : 10001070
         * knowledgeIds :
         * knowledgeNames :
         * myContent :
         * qaType : 2
         * readStatus : 0
         * title : 2017战略 题目答疑 11261526
         */

        private String answerDate;//答疑回答时间
        private String answerStatus;//0=未回答1=已回答
        private String childrenNum;
        private String content;//
        private String createBy;//创建人
        private String createDate;//答疑创建时间
        private String endDate;//预计回答时间
        private String essence;//精华状态:0=非精华 1=精华
        private String id;//
        private String knowledgeIds;//
        private String knowledgeNames;//
        private String myContent;
        private String qaType;//问题出处的类型 :1=试题 2= 图书，3课件，4教辅
        private String readStatus;//读取状态:0=新建1=已回复未读3=已读
        private String title;//标题

        public String getAnswerDate() {
            return answerDate;
        }

        public void setAnswerDate(String answerDate) {
            this.answerDate = answerDate;
        }

        public String getAnswerStatus() {
            return answerStatus;
        }

        public void setAnswerStatus(String answerStatus) {
            this.answerStatus = answerStatus;
        }

        public String getChildrenNum() {
            return childrenNum;
        }

        public void setChildrenNum(String childrenNum) {
            this.childrenNum = childrenNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEssence() {
            return essence;
        }

        public void setEssence(String essence) {
            this.essence = essence;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKnowledgeIds() {
            return knowledgeIds;
        }

        public void setKnowledgeIds(String knowledgeIds) {
            this.knowledgeIds = knowledgeIds;
        }

        public String getKnowledgeNames() {
            return knowledgeNames;
        }

        public void setKnowledgeNames(String knowledgeNames) {
            this.knowledgeNames = knowledgeNames;
        }

        public String getMyContent() {
            return myContent;
        }

        public void setMyContent(String myContent) {
            this.myContent = myContent;
        }

        public String getQaType() {
            return qaType;
        }

        public void setQaType(String qaType) {
            this.qaType = qaType;
        }

        public String getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(String readStatus) {
            this.readStatus = readStatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
