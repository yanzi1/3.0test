package com.me.fanyin.zbme.views.main.view.bean;

public class SimpleTitleTip implements Tip {
    private long id;
    private String tip;
    private String examCode;
    private String examImg;
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "tip:"+ tip;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamImg() {
        return examImg;
    }

    public void setExamImg(String examImg) {
        this.examImg = examImg;
    }
}
