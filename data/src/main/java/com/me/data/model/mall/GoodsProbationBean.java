package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsProbationBean implements Serializable{

    private String contents;
    private String goodsId;
    private String platform;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

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
}
