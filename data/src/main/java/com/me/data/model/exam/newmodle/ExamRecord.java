package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/2 0002.
 * 继续做题记录modle
 */

public class ExamRecord {

    /**
     * papertypeId : 试卷类型id
     * paperName : 试卷名称
     * status : 状态：1交卷，2未交卷
     * examRecordId : 做题记录id
     */

    private String papertypeId;
    private String paperName;
    private String status;
    private String examRecordId;

    public String getPapertypeId() {
        return papertypeId;
    }

    public void setPapertypeId(String papertypeId) {
        this.papertypeId = papertypeId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(String examRecordId) {
        this.examRecordId = examRecordId;
    }
}
