package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class OrderSaveBean implements Serializable{

    private boolean isCan;
    private String noCanMsg;
    private OrderVOBean orderVO;

    public boolean isCan() {
        return isCan;
    }

    public void setCan(boolean can) {
        isCan = can;
    }

    public String getNoCanMsg() {
        return noCanMsg;
    }

    public void setNoCanMsg(String noCanMsg) {
        this.noCanMsg = noCanMsg;
    }

    public OrderVOBean getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVOBean orderVO) {
        this.orderVO = orderVO;
    }
}
