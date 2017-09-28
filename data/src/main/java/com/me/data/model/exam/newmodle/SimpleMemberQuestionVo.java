package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/5/27 0027.
 * 答题报告中具体的题
 */

public class SimpleMemberQuestionVo {

    /**
     * groupId : 题帽id
     * myAnswer : 我的答案
     * littleQuestionList : 小题作答详情列表-由SimpleMemberQuestionVo组成
     * questionId : 试题id
     * isRight : 是否正确 0错误，1正确，2未答
     * isGroup : 是否题帽：0否，1是
     */

    private long groupId;
    private String myAnswer;
    private List<SimpleMemberQuestionVo> littleQuestionList;
    private long questionId;
    private int isRight;
    private int isGroup;

    /**
     * 自增
     * @return
     */
    private int indexInList;//在所有题目集合中的个数

    public int getIndexInList() {
        return indexInList;
    }

    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public List<SimpleMemberQuestionVo> getLittleQuestionList() {
        return littleQuestionList;
    }

    public void setLittleQuestionList(List<SimpleMemberQuestionVo> littleQuestionList) {
        this.littleQuestionList = littleQuestionList;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }
}
