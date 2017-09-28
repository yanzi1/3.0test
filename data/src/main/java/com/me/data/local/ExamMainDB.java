package com.me.data.local;

import com.me.data.model.exam.newmodle.ExamMainDbBean;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

/**
 * Created by fzw on 2017/6/9 0009.
 */

public class ExamMainDB extends BaseDB{
    Sql sql = null;

    public void insert(ExamMainDbBean examMainDbBean){
        ExamMainDbBean examMainDbBean_db = find(examMainDbBean.getSubjectId(),examMainDbBean.getSsubjectId());
        if(examMainDbBean_db == null)
            dbExecutor.insert(examMainDbBean);
        else{
            examMainDbBean.setId(examMainDbBean_db.getId());
            update(examMainDbBean);
        }
    }

    public ExamMainDbBean find(String subjectId,String ssubjectId){
        sql = SqlFactory.find(ExamMainDbBean.class).
                where("userId=? and subjectId=? and ssubjectId=?",
                        new Object[]{SharedPrefHelper.getInstance().getUserId() + "",subjectId,ssubjectId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public void update(ExamMainDbBean examMainDbBean){
        dbExecutor.updateById(examMainDbBean);
    }

}
