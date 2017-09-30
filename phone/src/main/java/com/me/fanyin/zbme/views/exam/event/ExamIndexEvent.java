package com.me.fanyin.zbme.views.exam.event;

/**
 * Created by xunice on 9/4/15.
 */
public class ExamIndexEvent {
    public int currentIndex;
    public int type; //type == 1 page type == 2 next
    public int totalIndex;//总位置
    private static final String TAG = "ExamIndexEvent";
    public ExamIndexEvent(int currentIndex, int type)
    {
        this.currentIndex = currentIndex;
        this.type = type;
    }

    public ExamIndexEvent(int currentIndex, int type, int totalIndex)
    {
        this.currentIndex = currentIndex;
        this.type = type;
        this.totalIndex=totalIndex;
    }
}
