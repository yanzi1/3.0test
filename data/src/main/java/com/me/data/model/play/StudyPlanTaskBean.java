package com.me.data.model.play;


import com.me.data.model.intel.IntelWeekTask;

import java.util.List;

public class StudyPlanTaskBean {

    private String taskCountCompleted;
    private String taskCount;
    private String studyPercent;
    private List<IntelWeekTask> studyTaskList;
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTaskCountCompleted() {
        return taskCountCompleted;
    }

    public void setTaskCountCompleted(String taskCountCompleted) {
        this.taskCountCompleted = taskCountCompleted;
    }

    public String getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(String taskCount) {
        this.taskCount = taskCount;
    }

    public String getStudyPercent() {
        return studyPercent;
    }

    public void setStudyPercent(String studyPercent) {
        this.studyPercent = studyPercent;
    }

    public List<IntelWeekTask> getStudyTaskList() {
        return studyTaskList;
    }

    public void setStudyTaskList(List<IntelWeekTask> studyTaskList) {
        this.studyTaskList = studyTaskList;
    }
}
