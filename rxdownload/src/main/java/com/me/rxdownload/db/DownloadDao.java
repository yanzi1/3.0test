package com.me.rxdownload.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.entity.DownloadStatus;
import com.me.rxdownload.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库实体类
 * Created by mayunfei on 17-3-23.
 */

public class DownloadDao implements IDownloadDB {
    private static final String TAG = "DownloadDao";

    private volatile static DownloadDao singleton;
    private SQLiteHelper sqLiteHelper;
    private final Object databaseLock = new Object();
    private volatile SQLiteDatabase readableDatabase;
    private volatile SQLiteDatabase writableDatabase;

    private DownloadDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context.getApplicationContext());
    }

    public static DownloadDao getSingleton(Context context) {
        if (singleton == null) {
            synchronized (DownloadDao.class) {
                if (singleton == null) {
                    singleton = new DownloadDao(context);
                }
            }
        }
        return singleton;
    }

    @Override
    public void closeDataBase() {
        synchronized (databaseLock) {
            readableDatabase = null;
            writableDatabase = null;
            sqLiteHelper.close();
        }
    }

    @Override
    public DownloadEvent selectBundleStatus(@NonNull String key) {
        DownloadEvent downloadEvent = new DownloadEvent();
        Cursor cursor = getReadableDatabase().query(DownloadBundle.TABLE_NAME, new String[]{
                DownloadBundle.TOTAL_SIZE, DownloadBundle.COMPLETED_SIZE, DownloadBundle.STATUS
        }, DownloadBundle.KEY + "=? ", new String[]{key}, null, null, null);
        try {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                long total = DBHelper.getLong(cursor, DownloadBundle.TOTAL_SIZE);
                long complete = DBHelper.getLong(cursor, DownloadBundle.COMPLETED_SIZE);
                int status = DBHelper.getInt(cursor, DownloadBundle.STATUS);
                downloadEvent.setTotalSize(total);
                downloadEvent.setStatus(status);
                downloadEvent.setCompletedSize(complete);
            }
        } finally {
            cursor.close();
        }

        return downloadEvent;
    }

    @NonNull
    @Override
    public List<DownloadBundle> getAllDownloadBundle() {

        Cursor cursor =
                getReadableDatabase().rawQuery("SELECT * FROM " + DownloadBundle.TABLE_NAME, null);
        ArrayList<DownloadBundle> list = new ArrayList<>(cursor.getCount());
        L.i(TAG, "getAllDownloadBundle \n");
        try {
            while (cursor.moveToNext()) {
                DownloadBundle downloadBundle = DownloadBundle.getDownloadBundle(cursor);
                list.add(downloadBundle);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    @NonNull
    @Override
    public List<DownloadBundle> getDownloadBundleByUserId(String userId) {

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=?",
                new String[]{userId});
        return getDownloadBundleByCursor(cursor);
    }

    @Override
    public List<DownloadBundle> getDownloadBundledBySubjectAndClass(String userId, String subjectId, String classId) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND " + DownloadBundle.SSUBJECT_ID + " =? AND " + DownloadBundle.CLASS_ID + " =? " + " AND " + DownloadBundle.STATUS + "=? ORDER BY " +DownloadBundle.SORT + " ASC ",
                new String[]{userId, subjectId, classId, DownloadStatus.FINISH + ""});
        return getDownloadBundleByCursor(cursor);
    }

    @Override
    public String getDownloadPathByKey(String key) {
        Cursor cursor = getReadableDatabase().query(DownloadBundle.TABLE_NAME, new String[]{
                DownloadBundle.PATH
        }, DownloadBundle.KEY + "=? ", new String[]{key,}, null, null, null);
        try {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                return DBHelper.getString(cursor, DownloadBundle.PATH);
            }

        } finally {
            cursor.close();
        }
        return null;
    }

//    @Override
//    public List<DownloadBundle> getDownloadBundleByExamIdSubjectIdClassId(String userId, String subjectId, String classId) {
//        Cursor cursor = getReadableDatabase().rawQuery(
//                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND "  + DownloadBundle.SSUBJECT_ID + " =? And " + DownloadBundle.CLASS_ID + " =? ",
//                new String[]{userId, subjectId, classId});
//        return getDownloadBundleByCursor(cursor);
//    }


    private List<DownloadBundle> getDownloadBundleByCursor(Cursor cursor) {
        ArrayList<DownloadBundle> list = new ArrayList<>(cursor.getCount());
        try {
            while (cursor.moveToNext()) {
                DownloadBundle downloadBundle = DownloadBundle.getDownloadBundle(cursor);
                list.add(downloadBundle);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    @NonNull
    @Override
    public List<DownloadBundle> getFinishedBundleByUserId(String userId) {
        return getBundleByUserIdAndStatus(userId, DownloadStatus.FINISH);
    }

    @Override
    public List<DownloadBundle> getDownloadingBundleByUserId(String userId) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND " + DownloadBundle.STATUS + "<>? ORDER BY " + DownloadBundle.ID,
                new String[]{userId, DownloadStatus.FINISH + ""});
        return getDownloadBundleByCursor(cursor);
    }
    @Override
    public List<DownloadBundle> getDownloadingBundleNoUser() {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.STATUS + "<>?",
                new String[]{DownloadStatus.FINISH + ""});
        return getDownloadBundleByCursor(cursor);
    }

    @Override
    public List<DownloadBundle> getDownloadingNotPauseBundleByUserId(String userId) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND " + " ( " + DownloadBundle.STATUS + "=?" + " OR " + DownloadBundle.STATUS + "=?" + " OR " + DownloadBundle.STATUS + "=?" + " )",
                new String[]{userId, DownloadStatus.DOWNLOADING + "", DownloadStatus.QUEUE + "", DownloadStatus.ERROR + ""});
        return getDownloadBundleByCursor(cursor);
    }

    private List<DownloadBundle> getBundleByUserIdAndStatus(String userId, int status) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND " + DownloadBundle.STATUS + "=?",
                new String[]{userId, status + ""});
        return getDownloadBundleByCursor(cursor);
    }

    @Override
    public List<DownloadResultByClassId> getDownloadFinishedGroupByClassId(String userId) {

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT count(id) AS count,* FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.USER_ID + "=? AND " + DownloadBundle.STATUS + "=? " + " GROUP BY " + DownloadBundle.CLASS_ID,
                new String[]{userId, DownloadStatus.FINISH + ""});
        List<DownloadResultByClassId> data = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                DownloadBundle downloadBundle = DownloadBundle.getDownloadBundle(cursor);
                int count = DBHelper.getInt(cursor, "count");
                DownloadResultByClassId downloadResultByClassId = new DownloadResultByClassId();
                downloadResultByClassId.count = count;
                downloadResultByClassId.setDownloadBundle(downloadBundle);
                data.add(downloadResultByClassId);
            }
        } finally {
            cursor.close();
        }
        return data;
    }

    public static class DownloadResultByClassId {
        public int count;
        private DownloadBundle downloadBundle;

        public DownloadBundle getDownloadBundle() {
            return downloadBundle;
        }

        public void setDownloadBundle(DownloadBundle downloadBundle) {
            this.downloadBundle = downloadBundle;
        }
    }

    @Override
    public void pauseAll() {

        getWritableDatabase().update(DownloadBundle.TABLE_NAME,
                DownloadBundle.update(DownloadStatus.PAUSE),
                DownloadBundle.STATUS + "=? OR " + DownloadBundle.STATUS + "= ?",
                new String[]{DownloadStatus.DOWNLOADING + "", DownloadStatus.QUEUE + ""});
    }

    @Override
    public void pause(String key){
        getWritableDatabase().update(DownloadBundle.TABLE_NAME,
                DownloadBundle.update(DownloadStatus.PAUSE),
                DownloadBundle.KEY + "=?" ,
                new String[]{key});
    }

    @Override
    public DownloadBundle getDownloadBundle(String key) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBundle.TABLE_NAME + " WHERE " + DownloadBundle.KEY + " =?",
                new String[]{key});
        try {
            if (cursor.moveToFirst()) {
                DownloadBundle downloadBundle = DownloadBundle.getDownloadBundle(cursor);
                downloadBundle.setDownloadList(getDownloadBeans(downloadBundle.getId()));
                return downloadBundle;
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    @Override
    public void setBeanFinished(int beanId) {
        L.i(TAG, "setBeanFinished \n" + beanId);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadBean.IS_FINISHED, true);
        getWritableDatabase().update(DownloadBean.TABLE_NAME, contentValues, DownloadBean.ID + "=?",
                new String[]{beanId + ""});
    }

    @Override
    public boolean delete(String key) {
        getWritableDatabase().execSQL("PRAGMA foreign_keys=ON");
        return getWritableDatabase().delete(DownloadBundle.TABLE_NAME, DownloadBundle.KEY + "=? ",
                new String[]{key}) > 0;
    }


    private List<DownloadBean> getDownloadBeans(int bundleId) {

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DownloadBean.TABLE_NAME + " WHERE " + DownloadBean.BUNDLE_ID + " = ?",
                new String[]{bundleId + ""});
        ArrayList<DownloadBean> list = new ArrayList<>(cursor.getCount());
        try {
            while (cursor.moveToNext()) {
                list.add(DownloadBean.getDownloadBean(cursor));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    private SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = writableDatabase;
        if (db == null) {
            synchronized (databaseLock) {
                db = writableDatabase;
                if (db == null) {
                    db = writableDatabase = sqLiteHelper.getWritableDatabase();
                }
            }
        }
        return db;
    }

    private SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = readableDatabase;
        if (db == null) {
            synchronized (databaseLock) {
                db = readableDatabase;
                if (db == null) {
                    db = readableDatabase = sqLiteHelper.getReadableDatabase();
                }
            }
        }
        return db;
    }

    @Override
    public boolean updateDownloadBean(DownloadBean bean) {
        //L.i(TAG, "updateDownloadBean \n" + bean);
        getWritableDatabase().update(DownloadBean.TABLE_NAME, DownloadBean.update(bean),
                DownloadBean.ID + "=?", new String[]{bean.getId() + ""});

        return true;
    }

    @Override
    public boolean updateDownloadBundle(DownloadBundle downloadBundle) {
        L.i(TAG, "updateDownloadBundle \n" + downloadBundle);
        getWritableDatabase().update(DownloadBundle.TABLE_NAME, DownloadBundle.update(downloadBundle),
                DownloadBundle.KEY + "=?", new String[]{downloadBundle.getKey()});

        return true;
    }

    @Override
    public boolean insertDownloadBundle(final DownloadBundle downloadBundle) {
        L.i(TAG, "insertDownloadBundle");
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            long insert =
                    db.insert(DownloadBundle.TABLE_NAME, null, DownloadBundle.insert(downloadBundle));
            if (insert <= 0) {
                return false;
            }
            int bundleId = getLastInsertRowId(db, DownloadBundle.TABLE_NAME);
            if (bundleId == -1) {
                return false;
            }
            downloadBundle.setId(bundleId);
            for (DownloadBean bean : downloadBundle.getDownloadList()) {
                bean.setBundleId(bundleId);
                db.insert(DownloadBean.TABLE_NAME, null, DownloadBean.insert(bean));
                int beanId = getLastInsertRowId(db, DownloadBean.TABLE_NAME);
                bean.setId(beanId);
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }

    private int getLastInsertRowId(SQLiteDatabase db, String tableName) {

        Cursor cursor = db.rawQuery("SELECT LAST_INSERT_ROWID() FROM " + tableName, null);
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }

    @Override
    public boolean existsDownloadBundle(String key) {
        Cursor cursor = null;
        try {
            cursor =
                    getReadableDatabase().query(DownloadBundle.TABLE_NAME, new String[]{DownloadBundle.ID},
                            DownloadBundle.KEY + "=? ", new String[]{key}, null, null, null);
            cursor.moveToFirst();
            return cursor.getCount() != 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public boolean isDownloadFinishedBy(String key) {
        Cursor cursor = null;
        try {
            cursor =
                    getReadableDatabase().query(DownloadBundle.TABLE_NAME, new String[]{DownloadBundle.ID, DownloadBundle.STATUS},
                            DownloadBundle.KEY + "=? AND " + DownloadBundle.STATUS + " = ? ", new String[]{key, DownloadStatus.FINISH + ""}, null, null, null);
            cursor.moveToFirst();
            return cursor.getCount() != 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
