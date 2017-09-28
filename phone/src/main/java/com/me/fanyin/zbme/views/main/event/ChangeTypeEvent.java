package com.me.fanyin.zbme.views.main.event;

/**
 * Created by wyc on 17/03/28.
 */
public class ChangeTypeEvent {
    private boolean flag;
    private static final String TAG = "ChangeTypeEvent";
    public ChangeTypeEvent(boolean flag)
    {
        this.flag=flag;
    }
}
