package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class ActivityBean implements Serializable{

    private String activityName;
    private String activityNo;
    private int activityType;
    private String activityTypeDicName;
    private String link;
    private String shopId;

    private String id;    //活动Id
    private String title; //活动名称
    private String type;  //活动类型
    private boolean isSelect = true;
    private int isFirst = 2;
    private boolean isShopSelect = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isShopSelect() {
        return isShopSelect;
    }

    public void setShopSelect(boolean shopSelect) {
        isShopSelect = shopSelect;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getActivityTypeDicName() {
        return activityTypeDicName;
    }

    public void setActivityTypeDicName(String activityTypeDicName) {
        this.activityTypeDicName = activityTypeDicName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
