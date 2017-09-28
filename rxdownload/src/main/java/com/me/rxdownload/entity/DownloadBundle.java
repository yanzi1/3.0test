package com.me.rxdownload.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.me.rxdownload.db.DBHelper;

import java.util.List;

import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.entity.DownloadStatus.QUEUE;

/**
 * 下载组合
 * Created by yunfei on 17-3-25.
 */

public class DownloadBundle {
    public static final String TABLE_NAME = "DownloadBundle";
    public static final String ID = "id";
    public static final String KEY = "key";
    public static final String PATH = "path";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String COMPLETED_SIZE = "completedSize";
    public static final String STATUS = "status";
    //分类存储
    public static final String USER_ID = "userId";
    public static final String SSUBJECT_ID = "sSubjectId";
    public static final String CLASS_ID = "classId";
    public static final String SESSION_ID = "sessionId"; //章节
    public static final String CW_ID = "cwId";
    public static final String SSUBJECT_NAME = "sSubjectName";
    public static final String CLASS_NAME = "className";
    public static final String CW_NAME = "cwName";
    public static final String CWSTR = "cwStr";
    public static final String TYPE = "type";
    public static final String SORT = "sort";

    public static final int cif = 0; //流畅
    public static final int sd = 1; //标清
    public static final int hd = 2; //高清


    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY
            + " TEXT NOT NULL UNIQUE,"
            + PATH
            + " TEXT NOT NULL,"
            + TOTAL_SIZE
            + " LONG,"
            + COMPLETED_SIZE
            + " LONG,"
            + STATUS
            + " INTEGER,"
            + USER_ID
            + " INTEGER,"
            + SSUBJECT_ID
            + " INTEGER,"
            + CLASS_ID
            + " INTEGER,"
            + SESSION_ID
            + " INTEGER,"
            + CW_ID
            + " INTEGER,"
            + SSUBJECT_NAME
            + " TEXT,"
            + CLASS_NAME
            + " TEXT,"
            + CW_NAME
            + " TEXT,"
            + CWSTR
            + " TEXT,"
            + SORT
            + " INTEGER,"
            + TYPE
            + " INTEGER"
            + ")";

    public static ContentValues insert(DownloadBundle downloadBundle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, downloadBundle.key);
        contentValues.put(PATH, downloadBundle.path);
        contentValues.put(TOTAL_SIZE, downloadBundle.totalSize);
        contentValues.put(COMPLETED_SIZE, downloadBundle.completedSize);
        contentValues.put(STATUS, downloadBundle.status);
        contentValues.put(TYPE, downloadBundle.type);
        contentValues.put(USER_ID, downloadBundle.userId);
        contentValues.put(SSUBJECT_ID, downloadBundle.sSubjectId);
        contentValues.put(CLASS_ID, downloadBundle.classId);
        contentValues.put(SESSION_ID, downloadBundle.sessionId);
        contentValues.put(CW_ID, downloadBundle.cwId);
        contentValues.put(SSUBJECT_NAME, downloadBundle.sSubjectName);
        contentValues.put(CLASS_NAME, downloadBundle.className);
        contentValues.put(CW_NAME, downloadBundle.cwName);
        contentValues.put(CWSTR,downloadBundle.cwStr);
        contentValues.put(SORT,downloadBundle.sort);
        return contentValues;
    }

    public static DownloadBundle getDownloadBundle(Cursor cursor) {
        int id = DBHelper.getInt(cursor, ID);
        String key = DBHelper.getString(cursor, KEY);
        String path = DBHelper.getString(cursor, PATH);
        long totalSize = DBHelper.getLong(cursor, TOTAL_SIZE);
        long completedSize = DBHelper.getLong(cursor, COMPLETED_SIZE);
        int status = DBHelper.getInt(cursor, STATUS);
        int type = DBHelper.getInt(cursor, TYPE);
        int userId = DBHelper.getInt(cursor, USER_ID);
        int subjectId = DBHelper.getInt(cursor, SSUBJECT_ID);
        int classId = DBHelper.getInt(cursor, CLASS_ID);
        int sessionId = DBHelper.getInt(cursor, SESSION_ID);
        int cwId = DBHelper.getInt(cursor, CW_ID);
        int sort = DBHelper.getInt(cursor,SORT);
        String subjectName = DBHelper.getString(cursor, SSUBJECT_NAME);
        String className = DBHelper.getString(cursor, CLASS_NAME);
        String cwName = DBHelper.getString(cursor, CW_NAME);
        String cwStr = DBHelper.getString(cursor, CWSTR);
        DownloadBundle downloadBundle = new DownloadBundle();
        downloadBundle.setId(id);
        downloadBundle.setKey(key);
        downloadBundle.setPath(path);
        downloadBundle.setTotalSize(totalSize);
        downloadBundle.setCompletedSize(completedSize);
        downloadBundle.setStatus(status);
        downloadBundle.setType(type);
        downloadBundle.setUserId(userId);
        downloadBundle.setsSubjectId(subjectId);
        downloadBundle.setClassId(classId);
        downloadBundle.setSessionId(sessionId);
        downloadBundle.setCwId(cwId);
        downloadBundle.setsSubjectName(subjectName);
        downloadBundle.setClassName(className);
        downloadBundle.setCwName(cwName);
        downloadBundle.setCwStr(cwStr);
        downloadBundle.setSort(sort);

        return downloadBundle;
    }

    public static ContentValues update(int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        return contentValues;
    }

    public static ContentValues update(DownloadBundle downloadBundle) {
        ContentValues insert = insert(downloadBundle);
        insert.put(ID, downloadBundle.getId());
        return insert;
    }

    private int id;
    private String key;
    private String path;
    private long totalSize;
    private long completedSize;
    private int status;
    private int type;
    private int examId;
    private int userId;
    private int sSubjectId;
    private int classId;
    private int sessionId;
    private int cwId;
    private String sSubjectName;//ssubjectName
    private String className;//className
    private String cwName;//cwName
    private String cwStr;//cwStr
    private int sort;
    private List<DownloadBean> downloadList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(int sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getCwId() {
        return cwId;
    }

    public void setCwId(int cwId) {
        this.cwId = cwId;
    }

    public String getsSubjectName() {
        return sSubjectName;
    }

    public void setsSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public String getCwStr() {
        return cwStr;
    }

    public void setCwStr(String cwStr) {
        this.cwStr = cwStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<DownloadBean> getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(List<DownloadBean> downloadList) {
        this.downloadList = downloadList;
    }

    @Override
    public String toString() {
        String showStatus = "";
        switch (status) {
            case DOWNLOADING:
                showStatus = "下载中";
                break;
            case PAUSE:
                showStatus = "暂停";
                break;
            case QUEUE:
                showStatus = "等待中";
                break;
            case FINISH:
                showStatus = "完成";
                break;
            case ERROR:
                showStatus = "错误";
        }

        return "DownloadBundle{"
                + "id = "
                + id
                + ", path ="
                + path
                + ", key="
                + key
                + '\''
                + ", totalSize="
                + totalSize
                + ", completedSize="
                + completedSize
                + ", status="
                + showStatus
                + ", type="
                + type
                + ", sort="
                + sort
                + '}';
    }

    public void init(DownloadBundle oldBundle) {
        this.setId(oldBundle.id);
        this.setPath(oldBundle.path);
        this.setDownloadList(oldBundle.downloadList);
        this.setTotalSize(oldBundle.totalSize);
        this.setCompletedSize(oldBundle.completedSize);
    }
}
