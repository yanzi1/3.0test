package com.me.updatelib.bean;

/**
 * Created by jjr on 2017/7/1.
 */

public class DownLoadApkSizeInfo {

    public DownLoadApkSizeInfo() {
    }

    public DownLoadApkSizeInfo(int sizeTotal, int sizeHadDownLoaded) {
        this.sizeTotal = sizeTotal;//文件总大小
        this.sizeHadDownLoaded = sizeHadDownLoaded;//已下载的文件大小
    }

    private int sizeHadDownLoaded;
    private int sizeTotal;

    public int getSizeHadDownLoaded() {
        return sizeHadDownLoaded;
    }

    public void setSizeHadDownLoaded(int sizeHadDownLoaded) {
        this.sizeHadDownLoaded = sizeHadDownLoaded;
    }

    public int getSizeTotal() {
        return sizeTotal;
    }

    public void setSizeTotal(int sizeTotal) {
        this.sizeTotal = sizeTotal;
    }
}
