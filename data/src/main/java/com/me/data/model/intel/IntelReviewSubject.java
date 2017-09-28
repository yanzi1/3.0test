package com.me.data.model.intel;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.util.List;

@Table(name="t_intel_review_subject")
public class IntelReviewSubject extends AbstractExpandableItem<IntelWorkReview> implements MultiItemEntity {
    @Id
    private int dbId;
    private String openDate;
    private String subjectId;
    private String subjectName;
    private String sSubjectId;
    //科目列表用
    private int openState;//开课状态 1.还未到开课时间 2.还未入学测试 3.还没有内容 3.正常
    private String openMsg;
    private List<IntelWorkReview> studyTaskList;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getOpenMsg() {
        return openMsg;
    }

    public void setOpenMsg(String openMsg) {
        this.openMsg = openMsg;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    public List<IntelWorkReview> getReviewList() {
        return studyTaskList;
    }

    public void setReviewList(List<IntelWorkReview> reviewList) {
        this.studyTaskList = reviewList;
    }

    public List<IntelWorkReview> getStudyTaskList() {
        return studyTaskList;
    }

    public void setStudyTaskList(List<IntelWorkReview> studyTaskList) {
        this.studyTaskList = studyTaskList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public List<IntelWorkReview> getSubItems() {
        return studyTaskList;
    }
}
