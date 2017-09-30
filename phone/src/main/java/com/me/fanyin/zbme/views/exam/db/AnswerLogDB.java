package com.me.fanyin.zbme.views.exam.db;

import android.content.Context;

import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.exam.AnswerLog;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.List;

/**
 * Created by xunice on 15/4/15.
 * 此类DAO只用于查询和特殊操作
 */
public class AnswerLogDB {
    Context mContext;
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public AnswerLogDB(Context mContext){
        this.mContext = mContext;
        dbExecutor =  DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(AnswerLog collection){
        dbExecutor.insert(collection);
    }

    public void update(AnswerLog collection){
        dbExecutor.updateById(collection);
    }

    public AnswerLog find(String id){
        return dbExecutor.findById(AnswerLog.class, id);
    }


    /**
     * 搜索全部记录count
     * @return
     */
    public long findAllCount(){
       return dbExecutor.count(AnswerLog.class);
    }


    /**
     * 搜索某一科目下试卷全部记录
     * @param subjectId
     * @param examId
     * @return
     */
    public List<AnswerLog> findAll(String userId,String examId,String subjectId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=?  and subjectId=? ", new Object[]{userId,subjectId});
        return dbExecutor.executeQuery(sql);
    }

    public List<AnswerLog> findTypeAll(String userId,String examId,String subjectId,String typeId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=?  and subjectId=? and typeId=?", new Object[]{userId,subjectId,typeId});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 搜索某一科目下试卷全部记录
     * @param subjectId
     * @param examId
     * @return
     */
    public List<AnswerLog> findAllBySubject(String userId,String examId,String subjectId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=? and subjectId=? ", new Object[]{userId,subjectId});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 搜索某一试卷全部记录
     * @param subjectId
     * @param examId
     * @param typeId
     * @param examinationId
     * @return
     */
    public List<AnswerLog> findAll(String subjectId,String examId,String typeId,String examinationId){
        sql = SqlFactory.find(AnswerLog.class).where("typeId", "=", typeId);
        return dbExecutor.executeQuery(sql);
    }

    public AnswerLog find(String userId,String examId,String subjectId,boolean isFinished){
        sql = SqlFactory.find(AnswerLog.class).where("userId=?  and subjectId=? and isFinished=?", new Object[]{userId, subjectId, isFinished}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public AnswerLog find(String userId,String examId,String subjectId,String typeId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=?  and subjectId=? and typeId=?", new Object[]{userId,subjectId,typeId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 获取当前最后做题记录
     * @param userId
     * @param examId
     * @param subjectId
     * @return
     */
    public AnswerLog findLastAnswerLog(String userId,String examId,String subjectId){
//        sql = SqlFactory.find(AnswerLog.class).where("userId=? and examId=? and subjectId=? ", new Object[]{userId,examId,subjectId}).orderBy("dbId",true);
//        return dbExecutor.executeQueryGetFirstEntry(sql);
        sql = SqlFactory.find(AnswerLog.class).where("userId=?  and subjectId=? ", new Object[]{userId,subjectId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    /**
     * 查询一套试卷
     */
    public AnswerLog findByExamination(String userId,String examId,String subjectId,String typeId,String examinationId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=? and subjectId=? and typeId=? and examinationId=?", new Object[]{userId,subjectId,typeId,examinationId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 删除答题记录
     */

    public void deleteByExamination(AnswerLog answerLog) {
            dbExecutor.deleteById(AnswerLog.class, answerLog.getDbId());//   .delete(Collection.class, collection);
    }
    /**
     * 删除全部记录
     */

    public void deleteAll() {
        dbExecutor.deleteAll(AnswerLog.class);//   
    }

    /**
     * 查询是否含有一套试卷
     */
    public boolean findIsTheExamination(String userId,String subjectId,String examinationId){
        sql = SqlFactory.find(AnswerLog.class).where("userId=? and subjectId=? and examinationId=?", new Object[]{userId,subjectId,examinationId}).orderBy("dbId",true);
        AnswerLog answerLog=dbExecutor.executeQueryGetFirstEntry(sql);
        if (answerLog!=null){
            return  true;
        }else {
            return false;
        }
    }

    /**
     * 获取所有试卷的正确率
     */
    public String findAllQuestionCorrectRate(String userId,String examId,String subjectId){
//        List<AnswerLog> allList=findAll(userId,examId,subjectId);
        List<AnswerLog> allList=findAllBySubject(userId,examId,subjectId);
        if (allList==null || allList.size()==0){
            return "0";
        }
        int rightNumber=0;
        int errorNumber=0;
        for (int i = 0; i < allList.size(); i++) {
            errorNumber+= allList.get(i).getAnswerErrorNums();
            rightNumber+= allList.get(i).getAnswerRightNums();
        }
        int allDone=rightNumber+errorNumber;
        int correctRate=0;
        if (allDone==0){
            correctRate=0;
        }else{
            correctRate=((rightNumber*100)/allDone);
        }
        if (correctRate>100){
            correctRate=100;
        }
        return correctRate+"";
    }

    /**
     * 获取已做题的数量
     * @param userId
     * @param examId
     * @param subjectId
     * @return
     */
    public String findAllDoneQuestion(String userId,String examId,String subjectId){
//        List<AnswerLog> allList=findAll(userId,examId,subjectId);
        List<AnswerLog> allList=findAllBySubject(userId, examId, subjectId);
        if (allList==null || allList.size()==0){
            return "0";
        }
        int rightNumber=0;
        int errorNumber=0;
        for (int i = 0; i < allList.size(); i++) {
            errorNumber+= allList.get(i).getAnswerErrorNums();
            rightNumber+= allList.get(i).getAnswerRightNums();
        }
        int allDone=rightNumber+errorNumber;
        return allDone+"";
    }
}
