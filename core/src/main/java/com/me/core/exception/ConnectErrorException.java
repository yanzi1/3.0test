package com.me.core.exception;

import java.util.concurrent.TimeoutException;

/**
 * Created by xunice on 17/03/2017.
 */
public class ConnectErrorException extends TimeoutException {
    // 超时连接
    public static final int TIME_OUT_ERROR = 100;

    public ConnectErrorException(int resultCode) {
        getApiExceptionMessage(resultCode);
    }

    public ConnectErrorException(String message) {
        super(message);
    }

    private static String getApiExceptionMessage(int resultCode) {
        String message = "";
        switch (resultCode){
            case TIME_OUT_ERROR:
                message = "连接超时啦";
                break;
            default:
                message = "未知异常";
        }

        return message;
    }
}
