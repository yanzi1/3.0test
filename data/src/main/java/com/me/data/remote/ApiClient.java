package com.me.data.remote;

import com.me.core.app.AppContext;
import com.me.core.common.Constants;
import com.me.core.utils.StringUtils;
import com.me.data.remote.fastjson.FastJsonConverterFactory;
import com.me.data.remote.https.HTTPS;
import com.me.data.remote.utils.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xunice on 10/03/2017.
 */

public class ApiClient {

    private static ApiInterface apiInterface;

    private static String baseUrl = ApiInterface.BASE_URL;

    private static OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(Constants.HTTP_TIME_OUT, TimeUnit.SECONDS);
//                        .writeTimeout(120, TimeUnit.SECONDS)


    /**
     * 更新baseUrl
     */
    private static void changeApiBaseUrl(String newApiBaseUrl) {
        if (apiInterface == null) {
            if (StringUtils.isEmpty(newApiBaseUrl)) {
                baseUrl = ApiInterface.BASE_URL;
            } else {
                baseUrl = newApiBaseUrl;
            }
            apiInterface = buildRetrofitToApiInterface(baseUrl);
        } else {
            if (StringUtils.isEmpty(newApiBaseUrl)) {
                //没有新的url
                if (!baseUrl.equals(ApiInterface.BASE_URL)) {
                    //如果在没有传递baseUrl的情况下，如果之前的有改变则还原成默认的baseUrl
                    baseUrl = ApiInterface.BASE_URL;
                    apiInterface = buildRetrofitToApiInterface(baseUrl);
                }
            } else {
                //有新的url
                if (!baseUrl.equals(newApiBaseUrl)) {
                    baseUrl = newApiBaseUrl;
                    //如果新的url和之前的不一样，则重新构建
                    apiInterface = buildRetrofitToApiInterface(baseUrl);
                }
            }
        }
    }

    /**
     * 构建ApiInterface
     */

    private static ApiInterface buildRetrofitToApiInterface(final String apiBaseUrl) {

        return getRetrofit(apiBaseUrl).create(ApiInterface.class);
    }

    public static Retrofit getRetrofit(final String apiBaseUrl) {

        final Retrofit[] retrofit = new Retrofit[1];

        Flowable.just(Constants.IS_ENABLED_CER).map(new Function<Boolean, String>() {

            @Override
            public String apply(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    return Constants.HTTPS_CER_NAME;
                }
                return "";
            }
        }).map(new Function<String, OkHttpClient>() {
            @Override
            public OkHttpClient apply(String s) throws Exception {
                if (!StringUtils.isEmpty(s)) {
                    InputStream inputStream = AppContext.getInstance().getAssets().open(Constants.HTTPS_CER_NAME);
                    X509TrustManager x509TrustManager = HTTPS.getX509TrustManager(inputStream);
                    SSLSocketFactory sslSocketFactory = HTTPS.getSSLSocketFactory(x509TrustManager);
                    return httpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager).build();
                }
                return httpClientBuilder.build();
            }
        }).subscribe(new Consumer<OkHttpClient>() {
            @Override
            public void accept(OkHttpClient okHttpClient) throws Exception {
                retrofit[0] = new Retrofit.Builder()
//                        .addConverterFactory(GsonConverterFactory.create())
                        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .addConverterFactory(FastJsonConverterFactory.create()) // 添加fastJson转换器
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl(apiBaseUrl).client(okHttpClient).build();
            }
        });

        return retrofit[0];
    }


    //获取单例
    static ApiInterface getApiInterface(String newApiBaseUrl) {
        changeApiBaseUrl(newApiBaseUrl);
        return apiInterface;
    }

    //获取单例
    public static ApiInterface getApiInterface() {
        return getApiInterface(null);
    }

    //获取单例
    static ApiInterface getApiInterfaces() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(baseUrl).client(httpClientBuilder.build()).build().create(ApiInterface.class);
    }
}
