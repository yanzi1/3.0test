package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.CourseListBean;
import com.yunqing.core.db.sql.FindSql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by wenpeng on 2016/6/13 0013.
 * 课程列表数据库
 */
public class CourseListDB extends BaseDB {


    public boolean insert(CourseListBean courseList){
        return dbExecutor.insert(courseList);
    }

    public CourseListBean find(String userId, String subjectId){
        FindSql sql = SqlFactory.find(CourseListBean.class).where("userId=? and sSubjectId=?", new Object[]{userId, subjectId}).orderBy("dbId", true);
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void update(CourseListBean courseList){
        dbExecutor.updateById(courseList);
    }

}
