package com.me.data.local;

import com.me.core.app.AppContext;
import com.yunqing.core.db.DBExecutor;

public class BaseDB {
    protected DBExecutor dbExecutor;
    public BaseDB(){
        dbExecutor = DBExecutor.getInstance(AppContext.getInstance());
    }
}
