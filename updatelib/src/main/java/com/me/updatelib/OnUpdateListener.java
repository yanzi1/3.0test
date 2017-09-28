package com.me.updatelib;

import android.net.Uri;

import com.me.updatelib.bean.UpdateInfo;

/**
 * Created by jjr on 2017/7/1.
 *
 * 更新各个阶段完成的回调接口
 */

public interface OnUpdateListener {

    void onStartCheck(String checkUrl);

    void onFinishCheck(UpdateInfo info);

    void onStartDownload(long downloadId);

    void onDownloading(int maxSize, int currentSize);

    void onFinshDownload(long downloadApkId, Uri downloadFileUri);

}
