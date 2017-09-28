package com.me.core.common;

public interface Constants {


    int HTTP_TIME_OUT = 12;
    int HTTP_TIME_CACHE = 24*60*60*1000;

    int CACHE_TIME = 10 * 24 * 60 * 60 * 1000;// 缓存10天，转换为毫秒

    int CACHE_TIME_ONEDAY = 1 * 24 * 60 * 60 * 1000;// 缓存1天，转换为毫秒

    int CACHE_TIME_FOR_1 = 24 * 60 * 60 * 1000;// 缓存1天，转换为毫秒


    int MAX_CACHE_SPACE = 200 * 1024 * 1024; //最小存储空间(MB)

    /**
     * 是否启用证书
     */
    boolean IS_ENABLED_CER= false;
    /**
     * 使用的证书名称
     */
    String HTTPS_CER_NAME = "srca1dongao.cer";

}