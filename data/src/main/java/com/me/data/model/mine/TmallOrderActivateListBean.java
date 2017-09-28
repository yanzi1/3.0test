package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class TmallOrderActivateListBean {

    /**
     * tmallList : [{"id":"12","createTime":"1497323051000","orderNo":"BF131104180333BF3A","tmailOrder":"TM004","userId":"333","userName":"15101506981w","examName":"注册会计师","goodsService":"预科班，基础班，习题班","goodsName":"注册会计师课程"},{"id":"10","createTime":"1496912826000","orderNo":"BF08170706033350C4","tmailOrder":"TM003","userId":"333","userName":"Apptest02","examName":"注册会计师","goodsService":"预科班，基础班，习题班","goodsName":"会计计-精品保障班"},{"id":"8","createTime":"1496911501000","orderNo":"BF0816445603336522","tmailOrder":"TM1001","userId":"333","userName":"15101506981","examName":"初级会计职称","goodsService":"初级,中级,高级","goodsName":"精华答疑"},{"id":"6","createTime":"1496814387000","orderNo":"BF071346220333CA6D","tmailOrder":"TM001","userId":"333","userName":"15101506981","examName":"注册会计师","goodsService":"预科班，基础班，习题班","goodsName":"审计-超值特惠班"},{"id":"4","createTime":"1496814107000","orderNo":"BF071341430333B2EE","tmailOrder":"TM002","userId":"333","userName":"15101506981","examName":"注册会计师","goodsService":"预科班，基础班，习题班","goodsName":"审计-超值特惠班"},{"id":"2","createTime":"1496049765000","orderNo":"TY3748","tmailOrder":"122","userId":"333","userName":"qwewqe","examName":"初级","goodsService":"老师精讲","goodsName":"初级会计基础一"}]
     * count : 6
     */

    private String count;
    private List<ListBean> tmallList;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getTmallList() {
        return tmallList;
    }

    public void setTmallList(List<ListBean> tmallList) {
        this.tmallList = tmallList;
    }

    public static class ListBean {
        /**
         * id : 12
         * createTime : 1497323051000
         * orderNo : BF131104180333BF3A
         * tmailOrder : TM004
         * userId : 333
         * userName : 15101506981w
         * examName : 注册会计师
         * goodsService : 预科班，基础班，习题班
         * goodsName : 注册会计师课程
         */

        private String id;
        private String createTime;
        private String orderNo;
        private String tmailOrder;
        private String userId;
        private String userName;
        private String examName;
        private String goodsService;
        private String goodsName;
        private boolean showSerrviceInfo = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getTmailOrder() {
            return tmailOrder;
        }

        public void setTmailOrder(String tmailOrder) {
            this.tmailOrder = tmailOrder;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getGoodsService() {
            return goodsService;
        }

        public void setGoodsService(String goodsService) {
            this.goodsService = goodsService;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public boolean isShowSerrviceInfo() {
            return showSerrviceInfo;
        }

        public void setShowSerrviceInfo(boolean showSerrviceInfo) {
            this.showSerrviceInfo = showSerrviceInfo;
        }
    }
}
