package com.me.fanyin.zbme.views.exam.remote;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public final class ApiClient {

    public static String USER_AGENT = "android-phone/3.1.3 (http://loopj.com/android-async-http)";
//    private static final String API_HOST = "http://member.dongao.com/api/";
//    private static final String API_HOST = "http://192.168.0.199/";
//    public final static String API_HOST = "http://examapi.dev3.com/";
   // private static final String API_HOST = "http://member.test.com/api/";
//    private static final String API_HOST = "http://kq.api.test.com:8081/app/api/v3/";
    private static final String API_HOST = "http://kq.api.dongao.com/app/api/v3/";
   // private static final String API_HOST = "http://172.16.198.171:8080/app/api/v3/";

    //private static final String API_HOST = "http://api.dongao.com/app/api/v1/";//192.168.0.199:8180
    //private static final String API_HOST = "http://192.168.0.173:8080/app/api/v1/";//192.168.0.199:8180

    //public static final String API_HOST_FOR_EXAM="http://192.168.0.199:8180/app/api/";
    private static final int TIME_OUT = 12;
    private static final int TIME_CACHE = 24*60*60*1000;
    private static ApiService apiService ;

    public static ApiService getClient() {
        if (apiService == null) {

            // Define the interceptor, add authentication headers
            Interceptor USER_AGENT_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("User-Agent",USER_AGENT).build();
                    return chain.proceed(newRequest);
                }
            };
            /** Dangerous interceptor that rewrites the server's cache-control header. */
            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "max-age=60")
                            .build();
                }
            };
            // Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath()+"/dongao/cache"),TIME_CACHE);
            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(USER_AGENT_INTERCEPTOR)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addInterceptor(new LoggingInterceptor())
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    //.cache(cache)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okClient)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService ;
    }

    /**
     * 特定使用，课程详情请求数据很慢，传一个超时时间
     * @param timeout
     * @return
     */
    public static ApiService getClientTimeout(int timeout) {
            // Define the interceptor, add authentication headers
            Interceptor USER_AGENT_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("User-Agent",USER_AGENT).build();
                    return chain.proceed(newRequest);
                }
            };
            /** Dangerous interceptor that rewrites the server's cache-control header. */
            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "max-age=60")
                            .build();
                }
            };
            // Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath()+"/dongao/cache"),TIME_CACHE);
            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(USER_AGENT_INTERCEPTOR)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addInterceptor(new LoggingInterceptor())
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    //.cache(cache)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okClient)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
    }
}
