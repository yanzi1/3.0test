package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.UserExamIdBean;
import com.yunqing.core.db.sql.FindSql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by wenpeng on 2016/6/13 0013.
 * 课程首页数据库
 */
public class UserExamDB extends BaseDB{

    public boolean insert(UserExamIdBean homeBean){
        return dbExecutor.insert(homeBean);
    }

    public UserExamIdBean find(String userId){
        FindSql sql = SqlFactory.find(UserExamIdBean.class).where("userId=?", new Object[]{userId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void update(UserExamIdBean homeBean){
        dbExecutor.updateById(homeBean);
    }

}
