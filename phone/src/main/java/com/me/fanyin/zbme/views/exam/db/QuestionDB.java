package com.me.fanyin.zbme.views.exam.db;

import android.content.Context;

import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.exam.Question;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.List;

/**
 * Created by xunice on 15/4/15.
 * 此类DAO只用于查询和特殊操作
 */
public class QuestionDB {
    Context mContext;
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public QuestionDB(Context mContext) {
        this.mContext = mContext;
        dbExecutor = DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(Question question) {
        dbExecutor.insert(question);
    }

    public void insertAll(List<Question> list) {
        dbExecutor.insertAll(list);
    }

    public void update(Question ts) {
        dbExecutor.updateById(ts);
    }

    /**
     * 获取一套试卷中单个的题
     * @param userId
     * @param typeId
     * @param examId
     * @param subjectId
     * @param examinationId
     * @param questionId
     * @return
     */
    public List<Question> find(int userId,int examId,int subjectId,int typeId, int examinationId,int questionId ){
        sql = SqlFactory.find(Question.class).where("userId=? and typeId=? and examId=? and subjectId=?  and examinationId=? and questionId=?", new Object[]{userId,typeId,examId,subjectId,examinationId,questionId});
        return dbExecutor.executeQuery(sql);
    }


    /**
     * 获取试卷中全部的题
     * @param userId
     * @param typeId
     * @param examId
     * @param subjectId
     * @param examinationId
     * @return
     */
    public List<Question> findAll(int userId,int examId,int subjectId,int typeId, int examinationId ){
        sql = SqlFactory.find(Question.class).where("userId=? and typeId=? and examId=? and subjectId=?  and examinationId=?", new Object[]{userId,typeId,examId,subjectId,examinationId});
        return dbExecutor.executeQuery(sql);
    }


}
