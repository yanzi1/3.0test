package com.me.data.model.mine;

import java.util.List;

/**
 * Created by xd on 2017/5/26.
 */


public class OpenClassListBean {

    /**
     * orderDetailId : 1707217
     * orderNo : B18170257005280B5
     * clazzList : [{"clazzName":"2017年注册会计师 -通关无忧班","subjectList":[{"aliasName":"审计","id":"400951","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《审计》-通关无忧班"},{"aliasName":"税法","id":"400952","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《税法》-通关无忧班"},{"aliasName":"经济法","id":"400953","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《经济法》-通关无忧班"},{"aliasName":"公司战略与风险管理","id":"400954","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《公司战略与风险管理》- 通关无忧班"},{"aliasName":"财务成本管理","id":"400955","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《财务成本管理》- 通关无忧班"},{"aliasName":"会计","id":"400956","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《会计》-通关无忧班"}]},{"clazzName":"2017年注册会计师 -通关无忧班","subjectList":[{"aliasName":"审计","id":"400945","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《审计》-通关无忧班"},{"aliasName":"税法","id":"400946","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《税法》-通关无忧班"},{"aliasName":"经济法","id":"400947","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《经济法》-通关无忧班"},{"aliasName":"公司战略与风险管理","id":"400948","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《公司战略与风险管理》- 通关无忧班"},{"aliasName":"财务成本管理","id":"400949","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《财务成本管理》- 通关无忧班"},{"aliasName":"会计","id":"400950","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《会计》-通关无忧班"}]}]
     * examId : 5
     * examName : 注册会计师
     * orderId : 765652
     */


    private String orderNo;
    private String examId;
    private String examName;
    private String orderId;
    private List<ClazzListBean> clazzList;




    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ClazzListBean> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<ClazzListBean> clazzList) {
        this.clazzList = clazzList;
    }


    public static class ClazzListBean {
        /**
         * clazzName : 2017年注册会计师 -通关无忧班
         * subjectList : [{"aliasName":"审计","id":"400951","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《审计》-通关无忧班"},{"aliasName":"税法","id":"400952","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《税法》-通关无忧班"},{"aliasName":"经济法","id":"400953","logisticsStatusName":"已开课","logisticsStatus":"2","shortDesc":"","goodsName":"2017年注册会计师《经济法》-通关无忧班"},{"aliasName":"公司战略与风险管理","id":"400954","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《公司战略与风险管理》- 通关无忧班"},{"aliasName":"财务成本管理","id":"400955","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《财务成本管理》- 通关无忧班"},{"aliasName":"会计","id":"400956","logisticsStatusName":"未开课","logisticsStatus":"0","shortDesc":"","goodsName":"2017年注册会计师《会计》-通关无忧班"}]
         */
        private String orderDetailId;
        private String clazzName;
        private List<SubjectListBean> subjectList;

        public String getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(String orderDetailId) {
            this.orderDetailId = orderDetailId;
        }

        public String getClazzName() {
            return clazzName;
        }

        public void setClazzName(String clazzName) {
            this.clazzName = clazzName;
        }

        public List<SubjectListBean> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<SubjectListBean> subjectList) {
            this.subjectList = subjectList;
        }

        public static class SubjectListBean {
            /**
             * aliasName : 审计
             * id : 400951
             * logisticsStatusName : 已开课
             * logisticsStatus : 2
             * shortDesc :
             * goodsName : 2017年注册会计师《审计》-通关无忧班
             */

            private String aliasName;
            private String id;
            private String logisticsStatusName;
            private String logisticsStatus;
            private String shortDesc;
            private String goodsName;

            public String getAliasName() {
                return aliasName;
            }

            public void setAliasName(String aliasName) {
                this.aliasName = aliasName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLogisticsStatusName() {
                return logisticsStatusName;
            }

            public void setLogisticsStatusName(String logisticsStatusName) {
                this.logisticsStatusName = logisticsStatusName;
            }

            public String getLogisticsStatus() {
                return logisticsStatus;
            }

            public void setLogisticsStatus(String logisticsStatus) {
                this.logisticsStatus = logisticsStatus;
            }

            public String getShortDesc() {
                return shortDesc;
            }

            public void setShortDesc(String shortDesc) {
                this.shortDesc = shortDesc;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
