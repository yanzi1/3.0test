package com.me.data.model.mine;

/**
 * Created by jjr on 2017/6/1.
 */

public class TypeMenu {

    private int typeId;//1:课程  2:题库  3:答疑
    private String typeName;

    public TypeMenu() {
    }

    public TypeMenu(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
