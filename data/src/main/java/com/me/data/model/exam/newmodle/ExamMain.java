package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/6/2 0002.
 * 题库首页数据modle
 */

public class ExamMain {
    private ExamRecord record;
    private List<ExamPaperType> typeList;

    public ExamRecord getRecord() {
        return record;
    }

    public void setRecord(ExamRecord record) {
        this.record = record;
    }

    public List<ExamPaperType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<ExamPaperType> typeList) {
        this.typeList = typeList;
    }
}
