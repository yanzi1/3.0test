package com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

/**
 *高频考点，知识点列表，带分页的效果
 * Created by wyc on 2016/6/12 0014.
 *
 */
@Table(name="t_knowledge_list_data")
public class KnowledgeListData implements Serializable{
    @Id
    private int dbId;
    private String userId;//用户Id
    private String type;//试题类型（随堂。。。）
    private String year ;//年份
    private String subjectId;//科目id
    private String subjectName;// 科目名称
    private String examId;// 考试Id
    //这是返回的数据
    private int totalPages;//总页数
    private List<Knowledge> examinationList;
    private String content;//整个json
    //存数据库需要用
    private int currentPage;//当前页数

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Knowledge> getExaminationList() {
        return examinationList;
    }

    public void setExaminationList(List<Knowledge> examinationList) {
        this.examinationList = examinationList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
