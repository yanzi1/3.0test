package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

/**
 *试卷：包括试题，试卷信息
 * Created by wangyongchao on 2015/7/14 0014.
 */

@Table(name="t_exam_pager")
public class ExamPaper implements Serializable{
    //知识点ID
    private Examination examination;
    private List<Question> questionList;

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
