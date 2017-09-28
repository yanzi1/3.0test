package com.me.data.model.studyrecord;

import com.me.data.model.intel.IntelWeekTask;
import com.me.data.model.intel.IntelWorkReview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzw on 2017/9/4 0004.
 */

public class DayRecord {

    private ArrayList<IntelWeekTask> dayStudyKpList;
    private List<IntelWorkReview> dayReviewList;

    public ArrayList<IntelWeekTask> getDayStudyKpList() {
        return dayStudyKpList;
    }

    public void setDayStudyKpList(ArrayList<IntelWeekTask> dayStudyKpList) {
        this.dayStudyKpList = dayStudyKpList;
    }

    public List<IntelWorkReview> getDayReviewList() {
        return dayReviewList;
    }

    public void setDayReviewList(List<IntelWorkReview> dayReviewList) {
        this.dayReviewList = dayReviewList;
    }
}
