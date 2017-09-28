package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.Subject;
import com.yunqing.core.db.sql.FindSql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by wenpeng on 2016/6/13 0013.
 * 科目列表数据库
 */
public class SubjectDB extends BaseDB{



    public boolean insert(Subject subject){
        return dbExecutor.insert(subject);
    }

    public Subject find(String userId,String examId){
        FindSql sql = SqlFactory.find(Subject.class).where("userId=? and examId=?", new Object[]{userId, examId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void update(Subject subject){
        dbExecutor.updateById(subject);
    }

}
