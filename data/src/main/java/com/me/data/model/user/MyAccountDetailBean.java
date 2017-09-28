package com.me.data.model.user;

import java.util.List;

/**
 * Created by xd on 2017/5/23.
 */

public class MyAccountDetailBean {


    /**
     * pageNo : 1
     * pageSize : 10
     * count : 33
     * first : 1
     * last : 4
     * prev : 1
     * next : 2
     * firstPage : true
     * lastPage : false
     * list : [{"id":1494315191961,"accountId":6,"businessId":766036,"transactionNo":"BE091455540052E514","type":2,"amount":82.9,"remark":"消费-直接消费金额","createdate":"2017-05-09 15:33:11","beforeAmount":7.85657304E7,"accountAmount":7.85656475E7,"optChar":"-"},{"id":1493021623157,"accountId":6,"businessId":766001,"transactionNo":"BD24161342005203A2","type":2,"amount":3000,"remark":"消费-直接消费金额","createdate":"2017-04-24 16:13:43","beforeAmount":7.85687304E7,"accountAmount":7.85657304E7,"optChar":"-"},{"id":1492076426344,"accountId":6,"businessId":765991,"transactionNo":"BD1317402500529429","type":2,"amount":30,"remark":"消费-直接消费金额","createdate":"2017-04-13 17:40:26","beforeAmount":7.85687604E7,"accountAmount":7.85687304E7,"optChar":"-"},{"id":1492063842099,"accountId":6,"businessId":765989,"transactionNo":"BD131410400052E3FE","type":2,"amount":3000,"remark":"消费-直接消费金额","createdate":"2017-04-13 14:10:42","beforeAmount":7.85717604E7,"accountAmount":7.85687604E7,"optChar":"-"},{"id":1487667938588,"accountId":6,"businessId":765778,"transactionNo":"BB211659570052772B","type":3,"amount":0.2,"remark":"订单：BB211659570052772B 退款","createdate":"2017-02-21 17:05:38","beforeAmount":7.85717602E7,"accountAmount":7.85717604E7,"optChar":"+"},{"id":1487667902944,"accountId":6,"businessId":-99999,"transactionNo":null,"type":2,"amount":0.4,"remark":"扣款原因：【0.4】","createdate":"2017-02-21 17:05:02","beforeAmount":7.85717606E7,"accountAmount":7.85717602E7,"optChar":"-"},{"id":1487667598661,"accountId":6,"businessId":765778,"transactionNo":"BB211659570052772B","type":2,"amount":9.6,"remark":"消费-直接消费金额","createdate":"2017-02-21 16:59:58","beforeAmount":7.85717702E7,"accountAmount":7.85717606E7,"optChar":"-"},{"id":1487667549812,"accountId":6,"businessId":-99999,"transactionNo":null,"type":2,"amount":0.6,"remark":"扣款原因：【0.6】","createdate":"2017-02-21 16:59:09","beforeAmount":7.85717708E7,"accountAmount":7.85717702E7,"optChar":"-"},{"id":1487314182718,"accountId":6,"businessId":765764,"transactionNo":"BB171447080052A2C5","type":3,"amount":163,"remark":"订单：BB171447080052A2C5 退款","createdate":"2017-02-17 14:49:42","beforeAmount":7.85716078E7,"accountAmount":7.85717708E7,"optChar":"+"},{"id":1487314029111,"accountId":6,"businessId":765764,"transactionNo":"BB171447080052A2C5","type":2,"amount":163,"remark":"消费-直接消费金额","createdate":"2017-02-17 14:47:09","beforeAmount":7.85717708E7,"accountAmount":7.85716078E7,"optChar":"-"}]
     * totalPage : 4
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
         * id : 1494315191961
         * accountId : 6
         * businessId : 766036
         * transactionNo : BE091455540052E514
         * type : 2
         * amount : 82.9
         * remark : 消费-直接消费金额
         * createdate : 2017-05-09 15:33:11
         * beforeAmount : 7.85657304E7
         * accountAmount : 7.85656475E7
         * optChar : -
         */

        private long id;
        private int accountId;
        private int businessId;
        private String transactionNo;
        private int type;
        private double amount;
        private String remark;
        private String createdate;
        private double beforeAmount;
        private double accountAmount;
        private String optChar;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getTransactionNo() {
            return transactionNo;
        }

        public void setTransactionNo(String transactionNo) {
            this.transactionNo = transactionNo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public double getBeforeAmount() {
            return beforeAmount;
        }

        public void setBeforeAmount(double beforeAmount) {
            this.beforeAmount = beforeAmount;
        }

        public double getAccountAmount() {
            return accountAmount;
        }

        public void setAccountAmount(double accountAmount) {
            this.accountAmount = accountAmount;
        }

        public String getOptChar() {
            return optChar;
        }

        public void setOptChar(String optChar) {
            this.optChar = optChar;
        }
    }
}
