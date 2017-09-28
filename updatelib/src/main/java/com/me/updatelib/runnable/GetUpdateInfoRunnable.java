package com.me.updatelib.runnable;

import android.os.Handler;

import com.me.updatelib.util.JSONHandler;
import com.me.updatelib.UpdateManager;
import com.me.updatelib.bean.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jjr on 2017/7/3.
 */

public class GetUpdateInfoRunnable implements Runnable {

    private Handler mHandler;
    private String checkUrl;

    public GetUpdateInfoRunnable(Handler handler, String checkUrl) {
        mHandler = handler;
        this.checkUrl = checkUrl;
    }

    @Override
    public void run() {
        UpdateInfo updateInfo = getUpdateInfo();
        if (updateInfo != null) {
            mHandler.obtainMessage(UpdateManager.CHECK_UPDATE_INFO, updateInfo).sendToTarget();
        } else {
            mHandler.obtainMessage(UpdateManager.MESSAGE_TRANSFER).sendToTarget();
        }
    }

    /**
     * 从服务器获取app的版本信息
     *
     * @return
     */
    private UpdateInfo getUpdateInfo() {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        UpdateInfo updateInfo = null;
        try {
            URL url = new URL(checkUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            inputStream = connection.getInputStream();
            updateInfo = JSONHandler.toUpdateInfo(inputStream);
        } catch (Exception e) {
            mHandler.obtainMessage(UpdateManager.MESSAGE_TRANSFER).sendToTarget();
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return updateInfo;
    }
}
