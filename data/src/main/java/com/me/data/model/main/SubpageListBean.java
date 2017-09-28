package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/6/5.
 */

public class SubpageListBean {

    /**
     * typeId : 112
     * typeName : 证书领取
     * pageNo : 1
     * pageTotal : 167
     * rows : 8
     * totalPage : 21
     * nextPageUrl : m.json.dongao.com/kjkt/cjhjzc/dhtab/zslq/index_2.json
     * list : [{"id":"728586","title":"2017年初级会计职称考试证书办理须知汇总","publishDate":"2017-06-02 11:13:59","image":"http://files.dongao.com/upload/resources/image/2017/06/02/74524.jpg","link":"m.dongao.com/c/2017-06-02/728586.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"728567","title":"2017初级会计考试成绩公布需要携带哪些资料办理证书","publishDate":"2017-06-02 11:00:04","image":"","link":"m.dongao.com/c/2017-06-02/728567.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"721665","title":"2017年全国初级会计考试成绩公布后什么时候办理证书","publishDate":"2017-05-27 11:29:45","image":"","link":"m.dongao.com/c/2017-05-27/721665.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"721625","title":"2017年全国初级会计职称资格证书如何办理","publishDate":"2017-05-27 11:14:16","image":"","link":"m.dongao.com/c/2017-05-27/721625.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"706734","title":"2016年襄阳4月领取初级会计资格证书的通知","publishDate":"2017-04-27 09:50:49","image":"","link":"m.dongao.com/c/2017-04-27/706734.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"704183","title":"2016年新沂市领取初级会计资格证书的通知","publishDate":"2017-04-21 15:49:38","image":"","link":"m.dongao.com/c/2017-04-21/704183.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"704182","title":"2016年盐城市大丰区领取初级会计资格证书的通知","publishDate":"2017-04-21 15:50:02","image":"","link":"m.dongao.com/c/2017-04-21/704182.shtml","tab":"证书领取","author":"焦莉涵","des":""},{"id":"704179","title":"2016年南京市领取初级会计资格证书的通知","publishDate":"2017-04-21 15:49:59","image":"","link":"m.dongao.com/c/2017-04-21/704179.shtml","tab":"证书领取","author":"焦莉涵","des":""}]
     */

    private String typeId;
    private String typeName;
    private String pageNo;
    private int pageTotal;
    private int rows;
    private int totalPage;
    private String nextPageUrl;
    private List<MainDetailItemBean> list;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPageNo() {
        return Integer.parseInt(pageNo);
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<MainDetailItemBean> getList() {
        return list;
    }

    public void setList(List<MainDetailItemBean> list) {
        this.list = list;
    }
}
