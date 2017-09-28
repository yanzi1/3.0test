package com.me.core.exception;

/**
 * Created by xunice on 10/03/2017.
 */

public class ApiException extends RuntimeException {

    public static final int ERROR_STATUS = 1009;
    public static final int EMPTY_CODE = -1000;//接口访问正常，但是obj对应为null（代表没数据）
    private int code;
    private String msg;

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case ERROR_STATUS:
                message = "密码错误";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}
