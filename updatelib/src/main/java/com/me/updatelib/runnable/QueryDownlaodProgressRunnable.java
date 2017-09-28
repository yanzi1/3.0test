package com.me.updatelib.runnable;

import android.app.DownloadManager;
import android.database.Cursor;
import android.os.Handler;

import com.me.updatelib.UpdateManager;
import com.me.updatelib.bean.DownLoadApkSizeInfo;

/**
 * Created by jjr on 2017/7/3.
 */

public class QueryDownlaodProgressRunnable implements Runnable {

    private Handler mHandler;
    private DownloadManager mDownloadManager;
    private DownloadManager.Query mQuery;
    private DownLoadApkSizeInfo mDownLoadApkSizeInfo;

    public QueryDownlaodProgressRunnable(Handler handler, DownloadManager downloadManager, DownloadManager.Query query) {
        mHandler = handler;
        mDownloadManager = downloadManager;
        mQuery = query;
    }

    @Override
    public void run() {
        while (true) {
            DownLoadApkSizeInfo downLoadApkSizeInfo = queryDownloadStatus();
            if (downLoadApkSizeInfo == null) {
                mHandler.obtainMessage(UpdateManager.ERROR_DOWNLOAD_APK).sendToTarget();
                break;
            } else if (downLoadApkSizeInfo.getSizeHadDownLoaded() == downLoadApkSizeInfo.getSizeTotal()) {
                mHandler.obtainMessage(UpdateManager.PROGRESS_DOWNLOAD_APK, downLoadApkSizeInfo).sendToTarget();
                break;
            } else {
                mHandler.obtainMessage(UpdateManager.PROGRESS_DOWNLOAD_APK, downLoadApkSizeInfo).sendToTarget();
            }
        }
    }

    /**
     * 查询文件的总大小和已下载文件的大小
     *
     * @return
     */
    private DownLoadApkSizeInfo queryDownloadStatus() {
        if (mDownLoadApkSizeInfo == null) {
            mDownLoadApkSizeInfo = new DownLoadApkSizeInfo();
        }
        Cursor c = mDownloadManager.query(mQuery);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_FAILED:
                    c.close();
                    return null;
                default:
                    break;
            }

            int fileSizeIdx = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);
            mDownLoadApkSizeInfo.setSizeTotal(fileSize);
            mDownLoadApkSizeInfo.setSizeHadDownLoaded(bytesDL);
            c.close();
            return mDownLoadApkSizeInfo;
        } else {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }

}
