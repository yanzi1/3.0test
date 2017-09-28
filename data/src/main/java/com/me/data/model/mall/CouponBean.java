package com.me.data.model.mall;

import com.me.data.cartview.base.BaseItemData;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class CouponBean extends BaseItemData implements Serializable{

    private String id;
    private String period;
    private String title;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
