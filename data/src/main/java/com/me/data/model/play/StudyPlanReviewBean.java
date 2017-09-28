package com.me.data.model.play;


import com.me.data.model.intel.IntelWorkReview;

import java.util.List;

public class StudyPlanReviewBean {

    private String isToday;//1 yes, 0 no
    private String dayName;
    private List<IntelWorkReview> studyTaskList;

    public String getIsToday() {
        return isToday;
    }

    public void setIsToday(String isToday) {
        this.isToday = isToday;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<IntelWorkReview> getStudyTaskList() {
        return studyTaskList;
    }

    public void setStudyTaskList(List<IntelWorkReview> studyTaskList) {
        this.studyTaskList = studyTaskList;
    }
}
