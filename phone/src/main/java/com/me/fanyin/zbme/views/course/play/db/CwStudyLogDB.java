package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwStudyLog;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunice on 2015/1/12.
 */
public class CwStudyLogDB extends BaseDB{
    Sql sql = null;

    public void insert(CwStudyLog cwStudyLog){
        dbExecutor.insert(cwStudyLog);
    }

    public void update(CwStudyLog cwStudyLog){
        dbExecutor.updateById(cwStudyLog);
    }

    public boolean delete(CwStudyLog cwStudyLog){
        return dbExecutor.deleteById(CwStudyLog.class, cwStudyLog.getDbId());
    }

    public CwStudyLog find(int id){
        return dbExecutor.findById(CwStudyLog.class, id);
    }


//    public List<CwStudyLog> findAll(){
//        sql = SqlFactory.find(MyAllCourse.class);
//        return dbExecutor.executeQuery(sql);
//    }

    /**
     * 查询方法
     * @return
     */
    public CwStudyLog query(String userid, String cwid, String courseId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("cwid=? and userId=? and courseId=?", new Object[]{cwid, userid, courseId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 查询方法
     * @return
     */
    public CwStudyLog queryLogs(String userid, String courseId, String subjectId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and courseId=? and subjectId=?", new Object[]{userid, courseId,subjectId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }
    /**
     * 查询方法
     * @return
     */
    public List<CwStudyLog> query(String userid, String courseId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and courseId=?", new Object[]{userid, courseId});
        return dbExecutor.executeQuery(sql);
    }

    public List<CourseWare> getStudyLogCw(String userid, String courseId){
        List<CourseWare> cws=new ArrayList<>();
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and courseId=?", new Object[]{userid, courseId});
        List<CwStudyLog> studyLogs=dbExecutor.executeQuery(sql);
        if(studyLogs!=null && studyLogs.size()>0){
            for(int i=0;i<studyLogs.size();i++){
                CourseWare cw=new CourseWare();
                cw.setId(studyLogs.get(i).getCwid());
                cw.setName(studyLogs.get(i).getCwName());
                cws.add(cw);
            }
        }
        return cws;
    }

    /**
     * 查询改课程最后播放的视频课件
     * @return
     */
    public String getLastCoursePlay(String userid, String courseId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and courseId=?", new Object[]{userid, courseId});
        CwStudyLog studyLog=dbExecutor.executeQueryGetFirstEntry(sql);
        if(studyLog!=null){
            return studyLog.getCwid();
        }
        return "";
    }

    /**
     * 查询方法
     * @return
     */
    public List<CwStudyLog> queryByYearAndCourseId(String userid, String curriculumId, String mYear){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("courseId=? and userId=? and mYear=? and watchedAt > 0", new Object[]{curriculumId, userid, mYear});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查询方法
     * @return
     */
    public List<CwStudyLog> queryByUserId(String userid){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and watchedAt > 0 and courseId!=0", new Object[]{userid});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查询方法
     * @return
     */
    public List<CwStudyLog> queryByUserIdSmart(String userid){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and watchedAt > 0 and courseId=0", new Object[]{userid});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查询方法
     * @return
     */
    public List<CwStudyLog> queryLast5LogByUserId(String userid){
        List<CwStudyLog> list;
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=?", new Object[]{userid}).limit(5);
        list = dbExecutor.executeQuery(sql);
        if(list!=null && list.size()>5){
            list.subList(0,5);
        }
        return list;
    }

    /**
     * 查询方法所有听过的课节数
     * @return
     */
    public int getAllListened(String userid, String subjectId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and subjectId=?", new Object[]{userid,subjectId});
        return dbExecutor.executeQuery(sql).size();
    }

    /**
     * 查询方法所有听过的课节数的总时长
     * @return
     */
    public long getListenedTimes(String userid, String subjectId){
        long time=0;
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and subjectId=?", new Object[]{userid,subjectId});
        List<CwStudyLog> cwStudyLogs=dbExecutor.executeQuery(sql);
        for(CwStudyLog cwStudyLog:cwStudyLogs){
            time=time+cwStudyLog.getNativeWatcheAt();
        }
        return time;
    }

    /**
     * 查询方法
     * @return
     */
    public CwStudyLog queryByUserIdLastUpdateTime(String userid, String subjectId){
        sql = SqlFactory.find(CwStudyLog.class).orderBy("lastUpdateTime", true).where("userId=? and subjectId=?", new Object[]{userid,subjectId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public boolean deleteAll(){
        return dbExecutor.deleteAll(CwStudyLog.class);
    }

    /**
     * 查询方法
     * @return
     */
    public boolean isFinished(String userid, String cwid, String courseId){
        sql = SqlFactory.find(CwStudyLog.class).where("cwid=? and userId=? and courseId=?", new Object[]{cwid, userid, courseId});
        CwStudyLog cwStudyLog=dbExecutor.executeQueryGetFirstEntry(sql);
        if(cwStudyLog!=null && cwStudyLog.getNativeWatcheAt()+15000>=cwStudyLog.getTotalTime()){
            return true;
        }
        return false;
    }
}
