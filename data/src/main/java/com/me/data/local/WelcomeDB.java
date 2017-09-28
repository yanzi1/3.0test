package com.me.data.local;

import android.database.sqlite.SQLiteDatabase;

import com.me.data.model.WelcomeRes;

import java.util.List;

/**
 * Created by xd on 2017/6/8.
 */

public class WelcomeDB extends BaseDB {

    public void updateBydeleteAll(WelcomeRes welcomeRes){
        SQLiteDatabase database = dbExecutor.getDBHelper().getDatabase();
        database.beginTransaction();
        try{
            dbExecutor.deleteAll(WelcomeRes.class);
            dbExecutor.insert(welcomeRes);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void update(){
//        dbExecutor.updateById()
    }

    public WelcomeRes find(){
        List<WelcomeRes> all = dbExecutor.findAll(WelcomeRes.class);
        if (all ==null || all.size()<=0)
            return null;
        else
            return all.get(0);
    }
}
