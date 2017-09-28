package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.me.core.app.AppManager;
import com.me.data.event.NetWorkChangeEvent;
import com.me.data.event.PostStudyLogEvent;
import com.me.data.local.SharedPrefHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络状态改变的监听
 * Created by wp on 2016/2/1.
 */
public class ConnectChange extends BroadcastReceiver {

    private Context context;
    boolean isInPlayAcitivy;
    private Handler handler=new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        //这里要判断应用是否退出
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        boolean isSmart=SharedPrefHelper.getInstance().isIntel();

        Activity activity= AppManager.getAppManager().currentActivity();
        if(activity==null){
            isInPlayAcitivy=false;
        }else{
            isInPlayAcitivy=activity.getLocalClassName().equals("views.course.play.PlayActivity");
        }

        if (wifiInfo!=null && wifiInfo.isConnected()) { //切换到wifi
            handler.removeCallbacksAndMessages(null);
            if(isSmart){
                EventBus.getDefault().post(new PostStudyLogEvent("1"));
            }else{
                EventBus.getDefault().post(new PostStudyLogEvent("2"));
            }
        } else if (mobileInfo!=null && mobileInfo.isConnected()) { //切换到流量
            handler.removeCallbacksAndMessages(null);
            if(isSmart){
                EventBus.getDefault().post(new PostStudyLogEvent("1"));
            }else{
                EventBus.getDefault().post(new PostStudyLogEvent("2"));
            }
            if(isInPlayAcitivy){     //在播放页
                EventBus.getDefault().post(new NetWorkChangeEvent("1"));
            }
        }else{
            if(isInPlayAcitivy){     //在播放页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new NetWorkChangeEvent("0"));
                    }
                },5000);
            }
        }
    }
}
