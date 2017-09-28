package com.me.share.callback;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;

/**
 * Created by xd on 2017/3/29.
 */

public interface ShareCallBack{
    void onComplete(Platform platform, int i, HashMap<String, Object> hashMap);
    void onError(Platform platform, int i, Throwable throwable);
    void onCancel(Platform platform, int i);
    void checkIsRegister(Platform platform);
}
