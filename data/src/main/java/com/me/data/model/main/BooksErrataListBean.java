package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/6/12.
 */

public class BooksErrataListBean {

    /**
     * count : 12
     * pageNo : 1
     * pageSize : 10
     * list : [{"id":"24","author":"luantingting","title":"第四章 第11页 第1题 简答题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-04 09:47:09","targetText":""},{"id":"23","author":"luantingting","title":"第四章 第11页 第1题 简答题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-04 09:46:49","targetText":""},{"id":"22","author":"luantingting","title":"第四章 第11页 第1题 简答题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-04 09:46:43","targetText":""},{"id":"20","author":"luantingting","title":"第四章 第33页 第2题 单选题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-03 16:09:41","targetText":""},{"id":"13","author":"luantingting","title":"其他章 第4页 第4题 多选题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-03 11:35:08","targetText":""},{"id":"12","author":"luantingting","title":"第四章 第4页 第4题 多选题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-05-03 11:32:16","targetText":""},{"id":"6","author":"zhoukun","title":"第一章  资产 第12页 第11题 判断题","des":"","sourceText":"","link":"","image":"","tab":"科目晁cc2017区域晁,初级会计实务教材(2016)","publishDate":"2017-03-20 16:54:53","targetText":""},{"id":"5","author":"zhoukun","title":"第三章  所有者权益 第33页 第33题 简答题","des":"","sourceText":"","link":"","image":"","tab":"科目晁cc2017区域晁,初级会计实务教材(2016)","publishDate":"2017-03-09 13:57:33","targetText":""},{"id":"4","author":"zhoukun","title":"第一章  资产 第13页 第13题 试题类型晁","des":"","sourceText":"","link":"","image":"","tab":"科目晁cc2017区域晁,初级会计实务教材(2016)","publishDate":"2017-03-09 13:45:15","targetText":""},{"id":"3","author":"zhoukun","title":"第二章 税务管理概述 第1页 第2题 判断题","des":"","sourceText":"","link":"","image":"","tab":"初级会计实务2016全国,初级会计实务教材(2016)","publishDate":"2017-03-08 13:52:17","targetText":""}]
     * totalPage : 2
     */

    private String count;
    private String pageNo;
    private String pageSize;
    private String totalPage;
    private List<MainDetailItemBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<MainDetailItemBean> getList() {
        return list;
    }

    public void setList(List<MainDetailItemBean> list) {
        this.list = list;
    }

}
