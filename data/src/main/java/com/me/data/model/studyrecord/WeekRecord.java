package com.me.data.model.studyrecord;

import com.me.data.model.intel.IntelWeekTask;

import java.util.List;

/**
 * Created by fzw on 2017/9/5 0005.
 */

public class WeekRecord {


    /**
     * examId : 考种id
     * sSubjectId : 考季id
     * memberId : 学员id
     * memberName : 学员名称
     * weekPlanId : 学习计划id
     * totalProgress : 总进度
     * completionRate : 任务完成率
     * totalErrorRate : 0
     * masterKpCount : 本周任务掌握考点数
     * studySuggestion : 学习建议
     * weekNumber : 3
     * completeWeekNumber : 任务完成的周数
     * kpNumber : 本周任务考点个数
     * createDate : 创建时间
     * masterKpCountAll : 总范围内掌握的总考点数
     * weekStartDate : 周开始时间
     * weekEndDate : 周结束时间
     * examName : 	考种名称
     * sSubjectName : 考季名称
     * extremeKps : 薄弱考点
     */

    private int examId;
    private int sSubjectId;
    private int memberId;
    private String memberName;
    private String weekPlanId;
    private double totalProgress;
    private double completionRate;
    private double totalErrorRate;
    private int masterKpCount;
    private String studySuggestion;
    private int weekNumber;
    private int completeWeekNumber;
    private int kpNumber;
    private String createDate;
    private int masterKpCountAll;
    private String weekStartDate;
    private String weekEndDate;
    private String examName;
    private String sSubjectName;
    private List<IntelWeekTask> extremeKps;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(int sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getWeekPlanId() {
        return weekPlanId;
    }

    public void setWeekPlanId(String weekPlanId) {
        this.weekPlanId = weekPlanId;
    }

    public double getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(double totalProgress) {
        this.totalProgress = totalProgress;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public double getTotalErrorRate() {
        return totalErrorRate;
    }

    public void setTotalErrorRate(double totalErrorRate) {
        this.totalErrorRate = totalErrorRate;
    }

    public int getMasterKpCount() {
        return masterKpCount;
    }

    public void setMasterKpCount(int masterKpCount) {
        this.masterKpCount = masterKpCount;
    }

    public String getStudySuggestion() {
        return studySuggestion;
    }

    public void setStudySuggestion(String studySuggestion) {
        this.studySuggestion = studySuggestion;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getCompleteWeekNumber() {
        return completeWeekNumber;
    }

    public void setCompleteWeekNumber(int completeWeekNumber) {
        this.completeWeekNumber = completeWeekNumber;
    }

    public int getKpNumber() {
        return kpNumber;
    }

    public void setKpNumber(int kpNumber) {
        this.kpNumber = kpNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getMasterKpCountAll() {
        return masterKpCountAll;
    }

    public void setMasterKpCountAll(int masterKpCountAll) {
        this.masterKpCountAll = masterKpCountAll;
    }

    public String getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(String weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public String getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(String weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSSubjectName() {
        return sSubjectName;
    }

    public void setSSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public List<IntelWeekTask> getExtremeKps() {
        return extremeKps;
    }

    public void setExtremeKps(List<IntelWeekTask> extremeKps) {
        this.extremeKps = extremeKps;
    }
}
