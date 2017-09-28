package com.me.data.model.mine;

import java.util.List;

/**
 * 我的优惠券
 * Created by mayunfei on 17-5-23.
 */

public class MyCouponResponseBean {

    /**
     * count : 4
     * disabled : false
     * first : 1
     * firstPage : true
     * firstResult : 0
     * funcName : page
     * funcParam :
     * html : <ul>
     * <li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
     * <li class="active"><a href="javascript:">1</a></li>
     * <li><a href="javascript:" onclick="page(2,1,'');">2</a></li>
     * <li><a href="javascript:" onclick="page(3,1,'');">3</a></li>
     * <li><a href="javascript:" onclick="page(4,1,'');">4</a></li>
     * <li><a href="javascript:" onclick="page(2,1,'');">下一页 &#187;</a></li>
     * <li class="disabled controls"><a href="javascript:">当前 第<input type="text" value="1" onkeypress="var c=event.keyCode||event.which||event.charCode;if(c==13)page(this.value,1,'');" onclick="this.select();"/> 页 / 每页 <input type="text" value="1" onkeypress="var c=event.keyCode||event.which||event.charCode;if(c==13)page(1,this.value,'');" onclick="this.select();"/> 条，共 4 条</a></li>
     * </ul>
     * <div style="clear:both;"></div>
     * last : 4
     * lastPage : false
     * list : [{"batchId":6,"batchNo":"1477383246192","begintdate":1477324800000,"couponKey":"14931005496640","couponName":"优惠现金券scx1024","couponNo":"QAC21064FFB89B7AB1","createddate":1493100550000,"enddate":1509379200000,"id":18051,"isDelete":0,"ruleId":11,"ruleType":1,"status":1,"typeName":"优惠券（现金）","value":100}]
     * maxResults : 1
     * message :
     * next : 2
     * notCount : false
     * orderBy :
     * pageNo : 1
     * pageSize : 1
     * prev : 1
     * totalPage : 4
     */

    private int count;
    private boolean disabled;
    private int first;
    private boolean firstPage;
    private int firstResult;
    private String funcName;
    private String funcParam;
    private String html;
    private int last;
    private boolean lastPage;
    private int maxResults;
    private String message;
    private int next;
    private boolean notCount;
    private String orderBy;
    private int pageNo;
    private int pageSize;
    private int prev;
    private int totalPage;
    private List<CouponBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isNotCount() {
        return notCount;
    }

    public void setNotCount(boolean notCount) {
        this.notCount = notCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

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

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<CouponBean> getList() {
        return list;
    }

    public void setList(List<CouponBean> list) {
        this.list = list;
    }

}
