package com.me.fanyin.zbme.views.main.activity.db;

import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.main.MainTypeDetailBean;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.List;

/**
 * Created by wyc 
 * 此类DAO只用于查询和特殊操作
 */
public class MainTypeDB {
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public MainTypeDB(){
        dbExecutor =  DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(MainTypeDetailBean collection){
        dbExecutor.insert(collection);
    }

    public void update(MainTypeDetailBean collection){
        dbExecutor.updateById(collection);
    }

    public MainTypeDetailBean find(String id){
        return dbExecutor.findById(MainTypeDetailBean.class,id);
    }


    /**
     * 搜索全部记录count
     * @return
     */
    public long findAllCount(){
       return dbExecutor.count(MainTypeDetailBean.class);
    }


    public List<MainTypeDetailBean> findAll(){
        sql = SqlFactory.find(MainTypeDetailBean.class);
        return dbExecutor.executeQuery(sql);
    }
    public List<MainTypeDetailBean> findAll(String userId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=?", new Object[]{userId});
        return dbExecutor.executeQuery(sql);
    }
    public List<MainTypeDetailBean> findAll(String userId, String examId, String subjectId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=? and subjectId=? ", new Object[]{userId,subjectId});
        return dbExecutor.executeQuery(sql);
    }

    public List<MainTypeDetailBean> findAll(String subjectId, String examId, String typeId, String examinationId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("typeId", "=", typeId);
        return dbExecutor.executeQuery(sql);
    }

    public MainTypeDetailBean find(String userId, String examId, String subjectId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=? and subjectId=? ", new Object[]{userId, subjectId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public MainTypeDetailBean find(String userId, String examId, String subjectId, String typeId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=? and subjectId=? and typeId=?", new Object[]{userId,subjectId,typeId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    /**
     * 查询一套试卷
     */
    public MainTypeDetailBean findByExamination(String userId, String examId, String subjectId, String typeId, String examinationId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=? and subjectId=? and typeId=?  and examinationId=?", new Object[]{userId,subjectId,typeId,examinationId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 查询一套试卷用试卷ID
     */
    public MainTypeDetailBean findByExaminationId(String userId, String examId, String subjectId, String examinationId){
        sql = SqlFactory.find(MainTypeDetailBean.class).where("userId=?  and subjectId=?  and examinationId=?", new Object[]{userId,subjectId,examinationId}).orderBy("dbId",true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    
    /**
     * 根据试卷删除本套试卷
     */
    public void deleteByMainTypeDetailBean(MainTypeDetailBean examPaperLog) {
            dbExecutor.deleteById(MainTypeDetailBean.class, examPaperLog.getDbId());//   .delete(Collection.class, collection);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(MainTypeDetailBean.class);
    }
}
