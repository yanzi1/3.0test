package com.me.data.model.studyrecord;

/**
 * Created by fzw on 2017/9/11 0011.
 */

public class WeekRecordCurrentWeek {

    /**
     * currentWeekNodeNum : 当前周所需学考点数
     * currentWeekEnd : 当前周的开始时间
     * currentWeekStart : 当前周的结束时间
     */
    private int currentWeekNodeNum;
    private String currentWeekEnd;
    private String currentWeekStart;

    public int getCurrentWeekNodeNum() {
        return currentWeekNodeNum;
    }

    public void setCurrentWeekNodeNum(int currentWeekNodeNum) {
        this.currentWeekNodeNum = currentWeekNodeNum;
    }

    public String getCurrentWeekEnd() {
        return currentWeekEnd;
    }

    public void setCurrentWeekEnd(String currentWeekEnd) {
        this.currentWeekEnd = currentWeekEnd;
    }

    public String getCurrentWeekStart() {
        return currentWeekStart;
    }

    public void setCurrentWeekStart(String currentWeekStart) {
        this.currentWeekStart = currentWeekStart;
    }
}
