package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/2 0002.
 * 试卷类型
 */

public class ExamPaperType {

    /**
     * sSubjectId : 考季ID
     * showLock : 是否加锁
     * paperTypeId : 试卷类型ID
     * papertypeName : 试卷类型名称
     */

    private String sSubjectId;
    private boolean showLock;
    private String paperTypeId;
    private String papertypeName;

    public String getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public boolean getShowLock() {
        return showLock;
    }

    public void setShowLock(boolean showLock) {
        this.showLock = showLock;
    }

    public String getPaperTypeId() {
        return paperTypeId;
    }

    public void setPaperTypeId(String paperTypeId) {
        this.paperTypeId = paperTypeId;
    }

    public String getPapertypeName() {
        return papertypeName;
    }

    public void setPapertypeName(String papertypeName) {
        this.papertypeName = papertypeName;
    }
}
