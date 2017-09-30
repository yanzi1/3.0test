package com.me.fanyin.zbme.views.exam.db;

import android.content.Context;

import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.exam.ExamPaperLog;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.List;

/**
 * Created by wyc on 15/8/20.
 * 此类DAO只用于查询和特殊操作
 */
public class ExamPaperDB {
    Context mContext;
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public ExamPaperDB(Context mContext){
        this.mContext = mContext;
        dbExecutor =  DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(ExamPaperLog collection){
        dbExecutor.insert(collection);
    }

    public void update(ExamPaperLog collection){
        dbExecutor.updateById(collection);
    }

    public ExamPaperLog find(String id){
        return dbExecutor.findById(ExamPaperLog.class,id);
    }


    /**
     * 搜索全部记录count
     * @return
     */
    public long findAllCount(){
       return dbExecutor.count(ExamPaperLog.class);
    }


    /**
     * 搜索某一科目下试卷全部记录
     * @param subjectId
     * @param examId
     * @return
     */
    public List<ExamPaperLog> findAll(String userId,String examId,String subjectId){
        sql = SqlFactory.find(ExamPaperLog.class).where("userId=? and subjectId=? ", new Object[]{userId,subjectId});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 搜索某一试卷
     * @param subjectId
     * @param examId
     * @param typeId
     * @param examinationId
     * @return
     */
    public List<ExamPaperLog> findAll(String subjectId,String examId,String typeId,String examinationId){
        sql = SqlFactory.find(ExamPaperLog.class).where("typeId", "=", typeId);
        return dbExecutor.executeQuery(sql);
    }

    public ExamPaperLog find(String userId,String examId,String subjectId){
        sql = SqlFactory.find(ExamPaperLog.class).where("userId=? and subjectId=? ", new Object[]{userId, subjectId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public ExamPaperLog find(String userId,String examId,String subjectId,String typeId){
        sql = SqlFactory.find(ExamPaperLog.class).where("userId=? and subjectId=? and typeId=?", new Object[]{userId,subjectId,typeId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    /**
     * 查询一套试卷
     */
    public ExamPaperLog findByExamination(String userId,String examId,String subjectId,String typeId,String examinationId){
        sql = SqlFactory.find(ExamPaperLog.class).where("userId=? and subjectId=? and typeId=?  and examinationId=?", new Object[]{userId,subjectId,typeId,examinationId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 查询一套试卷用试卷ID
     */
    public ExamPaperLog findByExaminationId(String userId,String examId,String subjectId,String examinationId){
        sql = SqlFactory.find(ExamPaperLog.class).where("userId=?  and subjectId=?  and examinationId=?", new Object[]{userId,subjectId,examinationId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    
    /**
     * 根据试卷删除本套试卷
     */
    public void deleteByExamPaperLog(ExamPaperLog examPaperLog) {
            dbExecutor.deleteById(ExamPaperLog.class, examPaperLog.getDbId());//   .delete(Collection.class, collection);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(ExamPaperLog.class);
    }
}
