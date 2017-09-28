package com.me.fanyin.zbme.views.course.play.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper {

    public HelperDB(Context context) {
        super(context, "Download", null, 2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//		db.execSQL("create table download (id integer primary key autoincrement,video_name varchar(50),video_url varchar(255),error_item varchar(50),isdone integer,percent integer,desPath varchar(255),lectrue_url varchar(255),caption_url varchar(255),taskId varchar(20),errorInfo varchar(20),captionError varchar(20))");
//		db.execSQL("create table studyrecord (id integer primary key autoincrement,userId varchar(20),videoID varchar(20),videoName varchar(20),courseWareId varchar(20),cwName varchar(20),year varchar(20),totalTime text,watchedAt text,nativeWatcheAt text,createdTime text,startTime text,endTime text)");
        db.execSQL("create table downloadtable (id integer primary key autoincrement,userId varchar(20),classId varchar(20),courseWareId varchar(20),year varchar(20),status integer,percent integer,desPath varchar(255),courseCount integer,courseBean text,courseWareBean text,type integer,subjectId varchar(20),sectionId varchar(20),examId varchar(20),isOld integer,version varchar(20))");
        db.execSQL("create table operation (id integer primary key autoincrement,userId varchar(20),device varchar(20),sysversion varchar(20),appversion varchar(20),appname varchar(20),playurl varchar(255),errurl varchar(255),netype text,errtime text,erroreson varchar(20),deviceDNS varchar(255),deviceIP varchar(255),subjectId varchar(20),sectionId varchar(20),classId varchar(20),cwId varchar(20))");
        db.execSQL("create table courseOld (id integer primary key autoincrement,subjectId varchar(20),courseId varchar(20),imgUrl text,name text,teacherName text)");
        db.execSQL("create table playparams (id integer primary key autoincrement,userId text,cwId text,app text,type text,vid text,key text,code text,message text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if (oldVersion == 1) {
            //创建播放参数的库
            db.execSQL("create table if not exists playparams (id integer primary key autoincrement,userId text,cwId text,app text,type text,vid text,key text,code text,message text)");

            Cursor cursor = db.rawQuery("select sql from sqlite_master where tbl_name='downloadtable' and type='table';", null);
            if (cursor.moveToFirst()) {
                String db_sql = cursor.getString(0);
                if (!db_sql.contains("version")) {
                    // 不存在字段添加
                    db.execSQL("alter table downloadtable add version varchar(20)");
                }
            }
        }

    }

}
