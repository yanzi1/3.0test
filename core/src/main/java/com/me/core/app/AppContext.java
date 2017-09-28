package com.me.core.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.me.core.BuildConfig;
import com.me.core.exception.ErrorHandlers;
import com.mob.MobSDK;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;


/**
 * Created by xunice on 08/03/2017.
 */

public class AppContext extends Application {

    public static final String APP_NAME = "kaoqian";

    private static AppContext instance = null;

    /**
     * 获取单例
     */
    public static synchronized AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (isMainProcess(this)) {
            if (BuildConfig.DEBUG) {
                //严苛模式主要检测两大问题，一个是线程策略，即TreadPolicy，另一个是VM策略，即VmPolicy。
                StrictMode.enableDefaults();
                //内存分析
                if (LeakCanary.isInAnalyzerProcess(this)) {
                    // This process is dedicated to LeakCanary for heap analysis.
                    // You should not init your app in this process.
                    return;
                }
                LeakCanary.install(this);


             Logger
            .init(APP_NAME)                 // default PRETTYLOGGER or use just init()
            .methodCount(3)                 // default 2
            .hideThreadInfo()               // default shown
            .logLevel(LogLevel.FULL)        // default LogLevel.FULL
            .methodOffset(2);                // default 0
            }
            //图片加载库
            RxJavaPlugins.setErrorHandler(ErrorHandlers.displayErrorConsumer(this));
            MobSDK.init(this);

        }
    }

    private void initFresco() {
//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
//                .setMaxCacheSize(Constants.MAX_CACHE_SPACE)//200MB
//                .build();
//        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(getApplicationContext())
//                .setMainDiskCacheConfig(diskCacheConfig)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(getApplicationContext(), imagePipelineConfig);
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
