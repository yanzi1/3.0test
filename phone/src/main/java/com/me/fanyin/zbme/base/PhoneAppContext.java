package com.me.fanyin.zbme.base;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.me.core.app.AppContext;
import com.me.core.utils.NetStateChangeReceiver;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.NanoHTTPD;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * Created by fzw on 2017/4/14 0014.
 */

public class PhoneAppContext extends AppContext implements NetStateChangeReceiver.NetStateChangeObserver {

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        startApplicationService();
        DownloadManager.init(this);
        NetStateChangeReceiver.registerReceiver(this);
        NetStateChangeReceiver.registerObserver(this);
        CrashReport.initCrashReport(getApplicationContext(), "1d5a200bdb", false);
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        NetStateChangeReceiver.unregisterReceiver(this);
        httpserver.stop();
    }

    @Override
    public void onNetDisconnected() {
        int downloadingCount = DownloadManager.getInstance().getDownloadingCount();
        if (downloadingCount > 0) {
            ToastBarUtils.showToast(this, "网络出现异常，已暂停下载");
            Log.i("×××××××××××××××××××","××××××××××××××××××× 网络出现异常，已暂停下载");
            DownloadManager.getInstance().pauseAll();
        }
    }

    @Override
    public void onNetConnected(String network) {
        int downloadingCount = DownloadManager.getInstance().getDownloadingCount();
        if (downloadingCount > 0) {
            switch (network) {
                case NetWorkUtils.NETWORK_TYPE_2G:
                case NetWorkUtils.NETWORK_TYPE_3G:
                case NetWorkUtils.NETWORK_TYPE_WAP:
                case NetWorkUtils.NETWORK_TYPE_UNKNOWN:
                    if (SharedPrefHelper.getInstance().canUseIn4G()) {  //34g 可用
                        ToastBarUtils.showToast(this, "当前已切换至非wifi网络，已为您暂停下载");
                    }
//                    else {
//                        ToastBarUtils.showToast(this, "您已设置不允许非wi-fi环境下下载");
//                    }
                    Log.i("×××××××××××××××××××","××××××××××××××××××× 当前已切换至非wifi网络，已为您暂停下载 暂停所有");
                    DownloadManager.getInstance().pauseAll();
                    break;
                case NetWorkUtils.NETWORK_TYPE_DISCONNECT:

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 启动NanoHTTP服务
     */
    private NanoHTTPD httpserver;

    private void startApplicationService() {

        // 启动HTTP服务
        if (TextUtils.isEmpty(FileUtil.getKeyPath(this))) {
            Toast.makeText(this, "当前没有SD卡存储，下载课程功能无法使用", Toast.LENGTH_LONG)
                    .show();
        }
        try {
            int port = Constants.SERVER_PORT;
            if (httpserver == null) {
                httpserver = new NanoHTTPD(port, new File(FileUtil.getKeyPath(this)), getApplicationContext());
            }
            System.out.println("代理服务启动成功");
        } catch (Exception e) {

        }
    }

}
