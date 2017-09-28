package com.me.updatelib.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.me.updatelib.UpdateManager;
import com.me.updatelib.util.Utils;


public class DownloadCompleteReceiver extends BroadcastReceiver {

    private Handler handler;

    public DownloadCompleteReceiver() {
    }

    public DownloadCompleteReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long id = Utils.getDownloadId(context);
            if (id != -1 && downloadApkId == id) {
                if (handler != null) handler.obtainMessage(UpdateManager.COMPLETE_DOWNLOAD_APK, downloadApkId).sendToTarget();
            }
        }
    }
}
