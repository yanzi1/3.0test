package com.me.data.model.intel;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.me.data.model.play.ClassHomeTop;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name="t_intel_main")
public class IntelMainBean implements Serializable {
    @Id
    private int dbId;
    private IntelOpenInfo openInfo;
    private ClassHomeTop countDownInfo;
    private String oilInfo;
    private List<IntelReviewSubject> reviewList;
    private List<IntelWeekSubject> weekList;
    
    private List<MultiItemEntity> reviewMIList;
    private List<MultiItemEntity> weekMIList;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public IntelOpenInfo getOpenInfo() {
        return openInfo;
    }

    public void setOpenInfo(IntelOpenInfo openInfo) {
        this.openInfo = openInfo;
    }

    public List<IntelReviewSubject> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<IntelReviewSubject> reviewList) {
        this.reviewList = reviewList;
    }

    public List<IntelWeekSubject> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<IntelWeekSubject> weekList) {
        this.weekList = weekList;
    }

    public List<MultiItemEntity> getReviewMIList() {
        if (reviewMIList==null){
            reviewMIList=new ArrayList<>();
        }
        reviewMIList.clear();
        reviewMIList.addAll(reviewList);
        return reviewMIList;
    }

    public void setReviewMIList(List<MultiItemEntity> reviewMIList) {
        this.reviewMIList = reviewMIList;
    }

    public List<MultiItemEntity> getWeekMIList() {
        if (weekMIList==null){
            weekMIList=new ArrayList<>();
        }
        weekMIList.clear();
        weekMIList.addAll(weekList);
        return weekMIList;
    }

    public ClassHomeTop getCountDownInfo() {
        return countDownInfo;
    }

    public void setCountDownInfo(ClassHomeTop countDownInfo) {
        this.countDownInfo = countDownInfo;
    }

    public String getOilInfo() {
        return oilInfo;
    }

    public void setOilInfo(String oilInfo) {
        this.oilInfo = oilInfo;
    }

    public void setWeekMIList(List<MultiItemEntity> weekMIList) {
        this.weekMIList = weekMIList;
    }
}
