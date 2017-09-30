package com.me.fanyin.zbme.views.exam.event;

/**
 * Created by wyc.
 * 定位题冒题小题的位置
 */
public class Comprehensive {
    public int index;
    //添加指定的题的ID
    public String questionId;
    private static final String TAG = "Comprehensive";
    public Comprehensive(int index, String questionId)
    {
        this.index=index;
        this.questionId=questionId;
    }
}
