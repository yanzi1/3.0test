package com.me.data.model.play;


import java.util.List;

public class StudyPlanBean {

    private String hasPrev;
    private String hasNext;
    private List<StudyPlanReviewBean> reviewList;
    private StudyPlanTaskBean studyTask;

    public String getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(String hasPrev) {
        this.hasPrev = hasPrev;
    }

    public String getHasNext() {
        return hasNext;
    }

    public void setHasNext(String hasNext) {
        this.hasNext = hasNext;
    }

    public List<StudyPlanReviewBean> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<StudyPlanReviewBean> reviewList) {
        this.reviewList = reviewList;
    }

    public StudyPlanTaskBean getStudyTask() {
        return studyTask;
    }

    public void setStudyTask(StudyPlanTaskBean studyTask) {
        this.studyTask = studyTask;
    }
}
