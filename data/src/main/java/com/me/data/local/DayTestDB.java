package com.me.data.local;

import com.me.data.model.daytest.DayExercise;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by fengzongwei on 2016/5/31 0031.
 * 每日一练数据库
 */
public class DayTestDB extends BaseDB{

    Sql sql = null;

    /**
     * 获取某个用户某天某考试的某科目下每日一练做题情况
     * @param examId
     * @param subjectId
     * @param dataTime
     * @return
     */
    public DayExercise getTodayDayExercise(String examId, String subjectId, String dataTime){
        sql = SqlFactory.find(DayExercise.class).
                where("subjectId=? and dataTime=? and userId=?",
                        new Object[]{subjectId, dataTime, SharedPrefHelper.getInstance().getUserId() + ""});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void insert(DayExercise dayExercise){
        dbExecutor.insert(dayExercise);
    }

    public void update(DayExercise dayExercise){
        dbExecutor.updateById(dayExercise);
    }


}
