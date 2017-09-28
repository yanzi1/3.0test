package com.me.updatelib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.me.updatelib.UpdateManager;
import com.me.updatelib.util.Utils;

/**
 * Created by jjr on 2017/7/11.
 */

public class AppReplacedReceiver extends BroadcastReceiver {

    private Handler handler;

    public AppReplacedReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            if (Utils.getPackageInfo(context).packageName.equals(intent.getPackage())) {
                handler.obtainMessage(UpdateManager.COMPLETE_REPLACED_APK).sendToTarget();
            }
        }
    }

}