package com.me.data.event;

/**
 * Created by wp on 2017/5/24.
 */

public class MediaSettingQualityEvent {

    private String beforeQuality;

    public MediaSettingQualityEvent(String beforeQuality){
        this.beforeQuality=beforeQuality;
    }

    public String getBeforeQuality() {
        return beforeQuality;
    }

    public void setBeforeQuality(String beforeQuality) {
        this.beforeQuality = beforeQuality;
    }
}
