package com.me.data.model.mall;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsDetailBean implements Serializable{

    private List<ActivityBean> activityList;

    private GoodsInfoBean goodsInfo;

    public List<ActivityBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityBean> activityList) {
        this.activityList = activityList;
    }

    public GoodsInfoBean getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfoBean goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
