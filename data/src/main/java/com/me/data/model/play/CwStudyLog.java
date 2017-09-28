package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;


/**
 * 学习记录的实体
 * @author admin
 *
 */
@Table(name="t_cw_study_log")
public class CwStudyLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int dbId;

	/**
	 * 学习记录的Id
	 */
	private int id;
	/**
	 * 当前学习用户的id
	 */
	private String userId;
	/**
	 * 当前这条记录的开始时间
	 */
	private long startTime;
	/**
	 * 当前记录的结束时间
	 */
	private long endTime;
	/**
	 * 当前记录的状态
	 */
	private int status;
	
	/**
	 * 课节的Id
	 */
	private String cwid;
	
	/**
	 * 课程的Id
	 */
	private String examId;

	private String subjectId;

	private String sectionId;

	private String courseId;

	private String startData;//课件开始的听课时间
	
	/**
	 * 课节的名称
	 */
	private String cwName;
	
	/**
	 * 课程的名称
	 */
	private String curriculumName;
	
	/**
	 * 年份
	 */
	private String mYear;
	
	
	/**
	 * 总时长
	 */
	private long totalTime;
	
	/**
	 * 已经播放的时长
	 */
	private long watchedAt;
	
	/**
	 * 本地此课节播放的时长
	 */
	private long nativeWatcheAt;
	
	/**
	 * 记录创建的时间
	 */
	private String createdTime;
	
	/**
	 * 记录最后更新的时间
	 */
	private String lastUpdateTime;

	public String getStartData() {
		return startData;
	}

	public void setStartData(String startData) {
		this.startData = startData;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCwid() {
		return cwid;
	}

	public void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getCwName() {
		return cwName;
	}

	public void setCwName(String cwName) {
		this.cwName = cwName;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public String getmYear() {
		return mYear;
	}

	public void setmYear(String mYear) {
		this.mYear = mYear;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getWatchedAt() {
		return watchedAt;
	}

	public void setWatchedAt(long watchedAt) {
		this.watchedAt = watchedAt;
	}

	public long getNativeWatcheAt() {
		return nativeWatcheAt;
	}

	public void setNativeWatcheAt(long nativeWatcheAt) {
		this.nativeWatcheAt = nativeWatcheAt;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}

