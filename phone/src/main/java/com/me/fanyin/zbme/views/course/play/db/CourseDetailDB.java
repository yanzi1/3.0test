package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.CourseDetail;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by fengzongwei on 2016/6/13 0013.
 * 课程详情数据库
 */
public class CourseDetailDB extends BaseDB{

    Sql sql = null;

    public boolean insert(CourseDetail courseDetail){
        return dbExecutor.insert(courseDetail);
    }

    public CourseDetail find(String userId, String sSubjectId, String classId){
        sql = SqlFactory.find(CourseDetail.class).
                where("userId=? and sSubjectId=? and id=?", new Object[]{userId, sSubjectId, classId}).orderBy("dbId",true);
        CourseDetail courseDetail = dbExecutor.executeQueryGetFirstEntry(sql);
        return courseDetail;
    }

    public void update(CourseDetail courseDetail){
        dbExecutor.updateById(courseDetail);
    }

    /**
     * 获取某课程下所有讲数量
     * @param sSubjectId
     * @param classId
     * @return
     */
    public int getCourseCount(String sSubjectId, String classId){
        sql = SqlFactory.find(CourseDetail.class).
                where("sSubjectId=? and classId=?", new Object[]{sSubjectId, classId}).orderBy("dbId",true);
        CourseDetail courseDetail = dbExecutor.executeQueryGetFirstEntry(sql);
        if(courseDetail != null)
            return courseDetail.getCwCount();
        else
            return -1;
    }

}
