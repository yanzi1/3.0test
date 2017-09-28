package com.me.rxdownload.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.me.rxdownload.db.DBHelper;

/**
 * 基础的下载信息
 * Created by yunfei on 17-3-25.
 */

public class DownloadBean {

    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_MULTIPLE = 1;

    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_LOW = -1;

    public static final String TABLE_NAME = "DownloadBean";
    public static final String ID = "id";
    public static final String BUNDLE_ID = "bundleId";
    public static final String FILENAME = "fineName";
    public static final String PATH = "path";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String COMPLETED_SIZE = "completedSize";
    public static final String START_SIZE = "startSize";
    public static final String URL = "url";
    public static final String PRIORITY = "priority";
    public static final String IS_FINISHED = "is_finished";
    public static final String MULTIPLE_TYPE = "multiple_type";
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BUNDLE_ID
            + " INTEGER,"
            + FILENAME
            + " TEXT NOT NULL,"
            + PATH
            + " TEXT,"
            + TOTAL_SIZE
            + " LONG,"
            + COMPLETED_SIZE
            + " LONG,"
            + START_SIZE
            + " LONG,"
            + URL
            + " TEXT,"
            + PRIORITY
            + " INTEGER, "
            + IS_FINISHED
            + " INTEGER, "
            + MULTIPLE_TYPE
            + " BOOLEAN NOT NULL CHECK ("
            + IS_FINISHED
            + " IN (0,1)),"
            + "FOREIGN KEY ("
            + BUNDLE_ID
            + ") REFERENCES "
            + DownloadBundle.TABLE_NAME
            + "("
            + DownloadBundle.ID
            + ") ON DELETE CASCADE )"; //开启级联删除

    private int id;
    private int bundleId;
    private String fileName;
    private String path;
    private long totalSize = -1;
    private long completedSize = 0;
    private long startSize = 0;
    private String url;
    private int multiple_type = TYPE_SINGLE;
    private boolean isFinished = false;

    private int priority = PRIORITY_NORMAL; //优先级

    public DownloadBean() {

    }

    private DownloadBean(Builder builder) {
        setId(builder.id);
        setBundleId(builder.bundleId);
        setFileName(builder.fileName);
        setPath(builder.path);
        setTotalSize(builder.totalSize);
        setCompletedSize(builder.completedSize);
        setStartSize(builder.startSize);
        setUrl(builder.url);
        setMultiple_type(builder.multiple_type);
        setFinished(builder.isFinished);
        setPriority(builder.priority);
    }


    public static ContentValues insert(DownloadBean downloadBean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUNDLE_ID, downloadBean.getBundleId());
        contentValues.put(FILENAME, downloadBean.getFileName());
        contentValues.put(PATH, downloadBean.getPath());
        contentValues.put(TOTAL_SIZE, downloadBean.getTotalSize());
        contentValues.put(COMPLETED_SIZE, downloadBean.getCompletedSize());
        contentValues.put(START_SIZE, downloadBean.getStartSize());
        contentValues.put(URL, downloadBean.getUrl());
        contentValues.put(PRIORITY, downloadBean.getPriority());
        contentValues.put(MULTIPLE_TYPE, downloadBean.getMultiple_type());
        contentValues.put(IS_FINISHED, downloadBean.isFinished());
        return contentValues;
    }

    public static ContentValues update(DownloadBean downloadBean) {
        ContentValues insert = insert(downloadBean);
        insert.put(ID, downloadBean.getId());
        return insert;
    }

    public static DownloadBean getDownloadBean(Cursor cursor) {
        int id = DBHelper.getInt(cursor, ID);
        int bundleId = DBHelper.getInt(cursor, BUNDLE_ID);
        String fileName = DBHelper.getString(cursor, FILENAME);
        String path = DBHelper.getString(cursor, PATH);
        long totalSize = DBHelper.getLong(cursor, TOTAL_SIZE);
        long startSize = DBHelper.getLong(cursor, START_SIZE);
        long completedSize = DBHelper.getLong(cursor, COMPLETED_SIZE);
        String url = DBHelper.getString(cursor, URL);
        int priority = DBHelper.getInt(cursor, PRIORITY);
        int multiple_type = DBHelper.getInt(cursor, MULTIPLE_TYPE);
        boolean isFinished = DBHelper.getBoolean(cursor, IS_FINISHED);
        return newBuilder().id(id)
                .bundleId(bundleId)
                .fileName(fileName)
                .path(path)
                .totalSize(totalSize)
                .startSize(startSize)
                .completedSize(completedSize)
                .url(url)
                .priority(priority)
                .isFinished(isFinished)
                .multiple_type(multiple_type)
                .build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBundleId() {
        return bundleId;
    }

    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getStartSize() {
        return startSize;
    }

    public void setStartSize(long startSize) {
        this.startSize = startSize;
    }

    public int getMultiple_type() {
        return multiple_type;
    }

    public void setMultiple_type(int multiple_type) {
        this.multiple_type = multiple_type;
    }


    @Override
    public String toString() {
        return "DownloadBean{" +
                "id=" + id +
                ", bundleId=" + bundleId +
                ", fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                ", totalSize=" + totalSize +
                ", completedSize=" + completedSize +
                ", startSize=" + startSize +
                ", url='" + url + '\'' +
                ", multiple_type=" + multiple_type +
                ", isFinished=" + isFinished +
                ", priority=" + priority +
                '}';
    }

    public static final class Builder {
        private int id;
        private int bundleId;
        private String fileName;
        private String path;
        private long totalSize;
        private long completedSize;
        private long startSize;
        private String url;
        private int multiple_type;
        private boolean isFinished;
        private int priority;

        private Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder bundleId(int val) {
            bundleId = val;
            return this;
        }

        public Builder fileName(String val) {
            fileName = val;
            return this;
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder totalSize(long val) {
            totalSize = val;
            return this;
        }

        public Builder completedSize(long val) {
            completedSize = val;
            return this;
        }

        public Builder startSize(long val) {
            startSize = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder multiple_type(int val) {
            multiple_type = val;
            return this;
        }

        public Builder isFinished(boolean val) {
            isFinished = val;
            return this;
        }

        public Builder priority(int val) {
            priority = val;
            return this;
        }

        public DownloadBean build() {
            return new DownloadBean(this);
        }
    }
}
