package com.me.fanyin.zbme.views.main.activity.db;

import com.alibaba.fastjson.JSON;
import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.main.MainTypeBean;
import com.me.data.model.main.MainTypeDetailBean;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyc 
 * 此类DAO只用于查询和特殊操作
 */
public class MainTypeNormalDB {
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public MainTypeNormalDB(){
        dbExecutor =  DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(MainTypeBean collection){
        dbExecutor.insert(collection);
    }

    public void update(MainTypeBean collection){
        dbExecutor.updateById(collection);
    }

    public MainTypeBean find(String id){
        return dbExecutor.findById(MainTypeBean.class,id);
    }


    /**
     * 搜索全部记录count
     * @return
     */
    public long findAllCount(){
       return dbExecutor.count(MainTypeBean.class);
    }


    public List<MainTypeDetailBean> findAll(){
        sql = SqlFactory.find(MainTypeBean.class);
        List<MainTypeBean> mainTypeBeanList=dbExecutor.executeQuery(sql);
        if (mainTypeBeanList==null||mainTypeBeanList.size()<1){
            return new ArrayList<>();
        }else{
            String content=mainTypeBeanList.get(0).getContent();
            if (content==null){
                return new ArrayList<>();
            }else{
                return JSON.parseArray(content,MainTypeDetailBean.class);
            }
        }
        
    }
    public List<MainTypeBean> findAll(String userId){
        sql = SqlFactory.find(MainTypeBean.class).where("userId=?", new Object[]{userId});
        return dbExecutor.executeQuery(sql);
    }

    
    /**
     * 根据试卷删除本套试卷
     */
    public void deleteByMainTypeBean(MainTypeBean examPaperLog) {
            dbExecutor.deleteById(MainTypeBean.class, examPaperLog.getDbId());//   .delete(Collection.class, collection);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(MainTypeBean.class);
    }
}
