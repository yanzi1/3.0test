package com.me.data.model.intel;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_intel_week_subject")
public class IntelWeekSubject extends AbstractExpandableItem<IntelWeekTask> implements MultiItemEntity, Serializable {
    @Id
    private int dbId;
    private String subjectId;
    private String subjectName;
    //科目列表用
    private String openDate;
    private int openState;//开课状态 1.还未到开课时间 2.还未入学测试 3.还没有内容 4.正常
    private String openMsg;

    private List<IntelWeekTask> studyTaskList;
   
    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
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

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    public List<IntelWeekTask> getWeekList() {
        return studyTaskList;
    }

    public void setWeekList(List<IntelWeekTask> weekList) {
        this.studyTaskList = weekList;
        setSubItems(weekList);
    }

    public List<IntelWeekTask> getStudyTaskList() {
        return studyTaskList;
    }

    public void setStudyTaskList(List<IntelWeekTask> studyTaskList) {
        this.studyTaskList = studyTaskList;
    }

    public String getOpenMsg() {
        return openMsg;
    }

    public void setOpenMsg(String openMsg) {
        this.openMsg = openMsg;
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
	public List<IntelWeekTask> getSubItems() {
		return studyTaskList;
	}
}
