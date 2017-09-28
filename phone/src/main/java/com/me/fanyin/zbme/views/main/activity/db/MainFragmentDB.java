package com.me.fanyin.zbme.views.main.activity.db;

import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.main.MainFragmentBean;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by wyc 
 * 此类DAO只用于查询和特殊操作
 */
public class MainFragmentDB {
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public MainFragmentDB(){
        dbExecutor =  DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(MainFragmentBean collection){
        dbExecutor.insert(collection);
    }

    public void update(MainFragmentBean collection){
        dbExecutor.updateById(collection);
    }

    public long findAllCount(){
       return dbExecutor.count(MainFragmentBean.class);
    }

    public MainFragmentBean find(String examId){
        sql = SqlFactory.find(MainFragmentBean.class).where(" examId=? ", new Object[]{examId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void deleteByMainFragmentBean(MainFragmentBean examPaperLog) {
            dbExecutor.deleteById(MainFragmentBean.class, examPaperLog.getDbId());//   .delete(Collection.class, collection);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(MainFragmentBean.class);
    }
   
    public void deleteBeanById(String examId) {
        dbExecutor.deleteById(MainFragmentBean.class, examId);//  
    }
}
