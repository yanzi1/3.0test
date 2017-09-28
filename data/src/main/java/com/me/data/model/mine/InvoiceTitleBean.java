package com.me.data.model.mine;

/**
 * Created by mayunfei on 17-5-25.
 */

public class InvoiceTitleBean {

    /**
     * id : 50
     * title : 天津
     * status : null
     * memberId : 54
     * memberName : zhangfuzhou
     */

    private int id;
    private String title;
    private int memberId;
    private String memberName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return title;
    }
}
