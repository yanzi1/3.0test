package com.me.fanyin.zbme.views.course.play.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.me.data.model.play.PlayParamsBean;

public class PlayParamsDB {
    private Context mContext;
    private HelperDB db;

    public PlayParamsDB(Context context) {
        mContext = context;
        db=new HelperDB(context);
    }

    public long add(String userId, String cwId, String app, String type, String vid, String key, String code, String message) {
        if (find(userId, cwId)) {
            return -2;
        }
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("cwId", cwId);
        values.put("app", app);
        values.put("type", type);
        values.put("vid", vid);
        values.put("key", key);
        values.put("code", code);
        values.put("message", message);
        long result = sql.insert("playparams", null, values);
        sql.close();
        return result;
    }

    public boolean find(String userId, String cwId) {
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.query("playparams", null, "userId=? and cwId=?",
                new String[]{userId, cwId}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            sql.close();
            return true;
        } else {
            cursor.close();
            sql.close();
            return false;
        }
    }

    public PlayParamsBean getParamById(String userId, String cwId){
        PlayParamsBean paramsBean=null;
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.query("playparams", null, "userId=? and cwId=?",
                new String[]{userId, cwId}, null, null, null);
        if (cursor.moveToNext()) {
            paramsBean = new PlayParamsBean();
            paramsBean.setApp(cursor.getString(3));
            paramsBean.setType(cursor.getString(4));
            paramsBean.setVid(cursor.getString(5));
            paramsBean.setKey(cursor.getString(6));
            paramsBean.setCode(cursor.getString(7));
            paramsBean.setMessage(cursor.getString(8));
        }
        cursor.close();
        sql.close();
        return paramsBean;
    }
}
