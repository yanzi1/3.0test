package com.me.fanyin.zbme.views.exam.event;


import com.me.data.model.exam.Question;

import java.io.Serializable;

/**
 * Created by wyc on 10/8/15.
 */
public class ComprehensiveUpdatePage implements Serializable{
    public Question question;//试题
    public int childIndex;//子索引
    public String questionId;//题冒题的Id
    private static final String TAG = "ComprehensiveUpdatePage";
    public ComprehensiveUpdatePage(Question question, int childIndex, String questionId)
    {
        this.question=question;
        this.childIndex=childIndex;
        this.questionId=questionId;
    }
}
