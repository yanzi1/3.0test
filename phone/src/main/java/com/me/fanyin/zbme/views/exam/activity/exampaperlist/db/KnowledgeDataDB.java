package com.me.fanyin.zbme.views.exam.activity.exampaperlist.db;

import android.content.Context;

import com.me.data.local.KaoQianDBHelper;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean.KnowledgeListData;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;
import com.yunqing.core.db.sql.WhereBuilder;

import java.util.List;

/**
 * Created by xunice on 15/4/15.
 * 此类DAO只用于查询和特殊操作
 */
public class KnowledgeDataDB {
    Context mContext;
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public KnowledgeDataDB(Context mContext) {
        this.mContext = mContext;
        dbExecutor = DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(KnowledgeListData knowledgeListData) {
        dbExecutor.insert(knowledgeListData);
    }

    public void update(KnowledgeListData knowledgeListData) {
        dbExecutor.updateById(knowledgeListData);
    }

    public KnowledgeListData find(String id) {
        return dbExecutor.findById(KnowledgeListData.class, id);
    }
    public KnowledgeListData findByUserType(String userId, String typeId ,String subjectId) {
        sql = SqlFactory.find(KnowledgeListData.class).where("userId=? and type=?  and subjectId=?", new Object[]{userId, typeId, subjectId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }


    public List<KnowledgeListData> findAllByTypeId(String typeId) {
        sql = SqlFactory.find(KnowledgeListData.class).where("typeId", "=", typeId);
        return dbExecutor.executeQuery(sql);
    }

    public List<KnowledgeListData> findAll(String userId, String typeId,String year ,String subjectId) {
        sql = SqlFactory.find(KnowledgeListData.class).where("userId=? and type=? and year=? and subjectId=?", new Object[]{userId, typeId, year, subjectId}).orderBy("dbId", true);
        return dbExecutor.executeQuery(sql);
    }

    public void deleteAll(String subjectId,String userId,String typeId){
        sql = SqlFactory.delete(KnowledgeListData.class).where(new WhereBuilder()).where("subjectId=? and userId=? and typeId=?", new Object[]{subjectId,userId,typeId});
        dbExecutor.executeQuery(sql);
    }

    public void delete(KnowledgeListData knowledgeListData){
        dbExecutor.deleteById(KnowledgeListData.class, knowledgeListData.getDbId());//  
    }
}
