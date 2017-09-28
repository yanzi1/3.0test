package com.me.data.model.main;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

@Table(name="t_maintypedetail")
public class MainTypeDetailBean implements Serializable {
    @Id
    private int dbId;
    private String examId;
    private String examName;
    private String examCode;
    //添加的userID
    private String userId;
    //2017.08.07 新添加字段
    private String examImg;//图片
    

    public MainTypeDetailBean() {
    }

    public MainTypeDetailBean(String id, String tittle){
        this.examId=id;
        this.examName=tittle;
    }

    public String getExamImg() {
        return examImg;
    }

    public void setExamImg(String examImg) {
        this.examImg = examImg;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return examId;
    }

    public void setId(String id) {
        this.examId = id;
    }

    public String getTittle() {
        return examName;
    }

    public void setTittle(String tittle) {
        this.examName = tittle;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    @Override
    public String toString() {
        return "MainTypeDetailBean{" +
                ", examId='" + examId + '\'' +
                ", examName='" + examName + '\'' +
                ", examCode='" + examCode + '\'' +
                '}';
    }
}
