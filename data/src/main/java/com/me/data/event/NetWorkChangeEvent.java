package com.me.data.event;

/**
 * Created by wp on 2017/5/24.
 */

public class NetWorkChangeEvent {

    private String type;//0 nonet 1,phonenet 2,wifi

    public NetWorkChangeEvent(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
