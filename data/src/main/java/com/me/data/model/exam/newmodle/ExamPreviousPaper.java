package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/6/2 0002.
 * 历年真题数据modle
 */

public class ExamPreviousPaper {

    /**
     * pageNo :
     * pageSize :
     * count :
     * first :
     * last :
     * prev :
     * next :
     * firstPage :
     * lastPage :
     * orderBy :
     * funcName :
     * funcParam :
     * message :
     * disabled :
     * totalPage :
     * notCount :
     * maxResults :
     * firstResult :
     * list :
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
    private String orderBy;
    private String funcName;
    private String funcParam;
    private String message;
    private boolean disabled;
    private int totalPage;
    private boolean notCount;
    private int maxResults;
    private int firstResult;
    private List<ExamPreviousPaperVo> list;

    public List<ExamPreviousPaperVo> getList() {
        return list;
    }

    public void setList(List<ExamPreviousPaperVo> list) {
        this.list = list;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isNotCount() {
        return notCount;
    }

    public void setNotCount(boolean notCount) {
        this.notCount = notCount;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }
}
