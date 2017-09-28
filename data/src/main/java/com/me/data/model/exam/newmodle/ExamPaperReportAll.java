package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/27 0027.
 */

public class ExamPaperReportAll {
    private PaperNums paperNums;//试卷下的类型试题数量
    private ExamPaperReport doView;//做题报告返回对象
    private int isErrorFlag;//是否显示错题解析按钮 1显示

    public PaperNums getPaperNums() {
        return paperNums;
    }

    public void setPaperNums(PaperNums paperNums) {
        this.paperNums = paperNums;
    }

    public ExamPaperReport getDoView() {
        return doView;
    }

    public void setDoView(ExamPaperReport doView) {
        this.doView = doView;
    }

    public int getIsErrorFlag() {
        return isErrorFlag;
    }

    public void setIsErrorFlag(int isErrorFlag) {
        this.isErrorFlag = isErrorFlag;
    }
}
