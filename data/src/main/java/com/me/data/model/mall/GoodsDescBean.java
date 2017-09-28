package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsDescBean implements Serializable{

    private String id;
    private String goodsDescription;
    private String goodsId;
    private String platform;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }
}
