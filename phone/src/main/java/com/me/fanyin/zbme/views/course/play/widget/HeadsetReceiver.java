package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.me.core.app.AppManager;
import com.me.data.event.HeadSetEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络状态改变的监听
 * Created by wp on 2016/2/1.
 */
public class HeadsetReceiver extends BroadcastReceiver {

    private Context context;
    boolean isInPlayAcitivy;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", 0);
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
//                    log.v(TAG,"耳机未插入");
                    Activity activity= AppManager.getAppManager().currentActivity();
                    if(activity==null){
                        isInPlayAcitivy=false;
                    }else{
                        isInPlayAcitivy=activity.getLocalClassName().equals("views.course.play.PlayActivity");
                    }
                    if(isInPlayAcitivy){     //在播放页
                        EventBus.getDefault().post(new HeadSetEvent());
                    }
                } else if (intent.getIntExtra("state", 0) == 1){
//                    log.v(TAG,"耳机已插入");
                }
            }
        }
    }
}
