package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.ClassHomeBean;
import com.yunqing.core.db.sql.FindSql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by wenpeng on 2016/6/13 0013.
 * 课程首页数据库
 */
public class ClassHomeDB extends BaseDB{


    public boolean insert(ClassHomeBean homeBean){
        return dbExecutor.insert(homeBean);
    }

    public ClassHomeBean find(String userId,String examId){
        FindSql sql = SqlFactory.find(ClassHomeBean.class).where("userId=? and examId=?", new Object[]{userId, examId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void update(ClassHomeBean homeBean){
        dbExecutor.updateById(homeBean);
    }

}
