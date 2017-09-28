package com.me.data.model.studyrecord;

import java.util.List;

/**
 * Created by fzw on 2017/9/5 0005.
 */

public class WeekRecordObj {

    private String currentMonth;
    private List<WeekRecord> weekReport;
    private WeekRecordCurrentWeek currentWeekMap;

    public WeekRecordCurrentWeek getCurrentWeekMap() {
        return currentWeekMap;
    }

    public void setCurrentWeekMap(WeekRecordCurrentWeek currentWeekMap) {
        this.currentWeekMap = currentWeekMap;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public List<WeekRecord> getWeekReport() {
        return weekReport;
    }

    public void setWeekReport(List<WeekRecord> weekReport) {
        this.weekReport = weekReport;
    }
}
