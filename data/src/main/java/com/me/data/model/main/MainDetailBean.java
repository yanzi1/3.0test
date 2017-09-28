package com.me.data.model.main;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.util.List;

@Table(name="t_maindetail")
public class MainDetailBean implements MultiItemEntity {
    @Id
    private int dbId;
    private int forumId;
    private String forumName;
    private int showMore;
    private int mouldCode;//模板code	
    private List<MainDetailItemBean> list;
	
	//测试数据
	private int testType;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getMouldCode() {
        return mouldCode;
    }

    public void setMouldCode(int mouldCode) {
        this.mouldCode = mouldCode;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public int getShowMore() {
        return showMore;
    }

    public void setShowMore(int showMore) {
        this.showMore = showMore;
    }

    public List<MainDetailItemBean> getList() {
        return list;
    }

    public void setList(List<MainDetailItemBean> list) {
        this.list = list;
    }

	public int getTestType() {
		return testType;
	}

	public void setTestType(int testType) {
		this.testType = testType;
	}

	@Override
    public int getItemType() {
        return testType==0?mouldCode:testType;
    }
}
