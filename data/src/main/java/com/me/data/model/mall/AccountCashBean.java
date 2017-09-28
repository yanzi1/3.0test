package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 23/05/2017.
 */

public class AccountCashBean implements Serializable {

    private String id;
    private String beforeAmount;
    private String amount;
    private String freezeAmount;
    private String memberId;
    private String changedate;
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(String beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
