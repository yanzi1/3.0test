package com.me.fanyin.zbme.base;

import java.io.Serializable;

/**
 * Created by xunice on 28/07/2017.
 */

public class UserBean implements Serializable {

    private String userId;
    private String type;
    private String signstr;

    public String getSignstr() {
        return signstr;
    }

    public void setSignstr(String signstr) {
        this.signstr = signstr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
