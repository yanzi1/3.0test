package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/5/15.
 *
 * 图书激活明细列表
 */

public class BookCardActivateListBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * count : 1
     * first : 1
     * last : 1
     * prev : 1
     * next : 1
     * firstPage : true
     * lastPage : true
     * list : [{"memberId":52,"couponNo":"QECCDAF8033E4E47FF","ruleId":193,"categoryId":115,"categoryName":"会计从业资格","goodsName":"2016年会计从业《会计电算化》-辅导教材","goodsId":223,"endDate":"2017-05-14 00:00:00"}]
     * totalPage : 1
     */

    private int pageNo;
    private int pageSize;
    private int count;
    private int first;
    private int last;
    private int prev;
    private int next;
    private boolean firstPage;
    private boolean lastPage;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * memberId : 52
         * couponNo : QECCDAF8033E4E47FF
         * ruleId : 193
         * categoryId : 115
         * categoryName : 会计从业资格
         * goodsName : 2016年会计从业《会计电算化》-辅导教材
         * goodsId : 223
         * endDate : 2017-05-14 00:00:00
         * isShowSerrviceInfo: false
         */

        private int memberId;
        private String couponNo;
        private int ruleId;
        private int categoryId;
        private String categoryName;
        private String goodsName;
        private int goodsId;
        private String endDate;
        private boolean showSerrviceInfo = false;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public int getRuleId() {
            return ruleId;
        }

        public void setRuleId(int ruleId) {
            this.ruleId = ruleId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public boolean isShowSerrviceInfo() {
            return showSerrviceInfo;
        }

        public void setShowSerrviceInfo(boolean showSerrviceInfo) {
            this.showSerrviceInfo = showSerrviceInfo;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "memberId=" + memberId +
                    ", couponNo='" + couponNo + '\'' +
                    ", ruleId=" + ruleId +
                    ", categoryId=" + categoryId +
                    ", categoryName='" + categoryName + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsId=" + goodsId +
                    ", endDate='" + endDate + '\'' +
                    ", showSerrviceInfo=" + showSerrviceInfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BookCardActivateListBean{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", count=" + count +
                ", first=" + first +
                ", last=" + last +
                ", prev=" + prev +
                ", next=" + next +
                ", firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }
}
