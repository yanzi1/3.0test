package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/5/25 0025.
 * 试题选项对象
 */

public class QuestionInfoOptionVo {


    /**
     * id : 选项Id
     * questionId : 试题id
     * tag : 选项标识
     * sort : 排序
     * description : 选项描述
     */

    private long id;
    private long questionId;
    private String tag = "null";
    private int sort;
    private String description;
    //自增
    private int choseFlag;//当前是否被选中 1.选中

    public int getChoseFlag() {
        return choseFlag;
    }

    public void setChoseFlag(int choseFlag) {
        this.choseFlag = choseFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
