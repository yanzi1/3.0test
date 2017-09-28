package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/5/27 0027.
 * 答题报告中题目类型
 */

public class SimplePaperChoiceTypeLinkVo {

    /**
     * choicetypeId : 题型id
     * choicetypeName : 显示题型名称
     * memberQuestionList : 学员作答试题结果列表-由SimpleMemberQuestionVo组成
     */

    private long choicetypeId;
    private String choicetypeName;
    private List<SimpleMemberQuestionVo> memberQuestionList;

    public List<SimpleMemberQuestionVo> getMemberQuestionList() {
        return memberQuestionList;
    }

    public void setMemberQuestionList(List<SimpleMemberQuestionVo> memberQuestionList) {
        this.memberQuestionList = memberQuestionList;
    }

    public long getChoicetypeId() {
        return choicetypeId;
    }

    public void setChoicetypeId(long choicetypeId) {
        this.choicetypeId = choicetypeId;
    }

    public String getChoicetypeName() {
        return choicetypeName;
    }

    public void setChoicetypeName(String choicetypeName) {
        this.choicetypeName = choicetypeName;
    }
}
