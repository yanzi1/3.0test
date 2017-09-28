package com.me.fanyin.zbme.views.user;

import android.content.Context;

import com.me.core.exception.ApiException;
import com.me.core.utils.NetWorkUtils;
import com.me.fanyin.zbme.R;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;


/**
 * Created by xd on 2017/7/11.
 */

public class CheckExceptionUtils {

    public static String getExceptionMsg(Throwable throwable,Context context){
        String msg;
        if(!NetWorkUtils.isNetworkAvailable(context))
            msg=context.getString(R.string.app_nonetwork_message);
        else if (throwable instanceof ApiException)
            msg=throwable.getMessage();
        else if (throwable instanceof HttpException)
            msg="服务器异常";
        else
            msg="网络错误";
        return msg;
    }
}
