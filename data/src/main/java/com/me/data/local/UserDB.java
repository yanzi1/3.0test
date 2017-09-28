package com.me.data.local;

import com.me.data.model.user.UserBean;
import com.yunqing.core.db.sql.Sql;

/**
 * Created by xunice on 15/4/15.
 * 此类DAO只用于查询和特殊操作
 */
public class UserDB extends BaseDB {
    Sql sql = null;

    public void insert(UserBean user){
        dbExecutor.deleteAll(UserBean.class);
        dbExecutor.insert(user);
    }

    public void update(UserBean user){
        dbExecutor.updateById(user);
    }

    public UserBean find(int id){
        return dbExecutor.findById(UserBean.class,id);
    }

    public UserBean find(){
        return dbExecutor.findAll(UserBean.class).get(0);
    }

}
