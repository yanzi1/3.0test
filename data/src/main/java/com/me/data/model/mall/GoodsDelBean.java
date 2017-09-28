package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsDelBean implements Serializable {

    private String pid;
    private String venderId;

    public GoodsDelBean(String venderId,String pid){
        this.venderId = venderId;
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
}