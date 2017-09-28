package com.me.data.exception;

/**
 * Created by xunice on 10/03/2017.
 */

public class LoginException extends RuntimeException {
    public static final int LOGIN_PASSWORD_ERROR_CODE = 1007;
    public static final int LOGIN_USER_ERROR_CODE = 1005;
    public static final int REQUEST_ERROR_CODE = 1333;
    public static final int REGISTER_USER_REPEAT_ERROR_CODE = 1001;
    public static final String LOGIN_PASSWORD_ERROR_MESSAGE = "密码错误，请重试";
    public static final String LOGIN_USER_ERROR_MESSAGE = "用户名不存在";
    public static final String REGISTER_USER_REPEAT_ERROR_MESSAGE = "用户已注册";
    public static final String REQUEST_ERROR_MESSAGE = "非法请求";


    public LoginException(int code) {
        this(getLoginExceptionMessage(code));
    }

    public LoginException(String message) {
        super(message);
    }

    private static String getLoginExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case LOGIN_PASSWORD_ERROR_CODE:
                message = LOGIN_PASSWORD_ERROR_MESSAGE;
                break;
            case LOGIN_USER_ERROR_CODE:
                message = LOGIN_USER_ERROR_MESSAGE;
                break;
            case REGISTER_USER_REPEAT_ERROR_CODE:
                message = REGISTER_USER_REPEAT_ERROR_MESSAGE;
                break;
            case REQUEST_ERROR_CODE:
                message = REQUEST_ERROR_MESSAGE;
                break;
            default:
                message = "未知错误";
                break;
        }

        return message;
    }
}
