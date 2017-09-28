package com.me.data.remote.utils;

import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by xunice on 15/10/26.
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();

        if (BuildConfig.DEBUG){
            if (request.method().equals("POST")) { //处理POST参数
                String url = request.url().toString();
                if (url.contains("?")) {
                    Logger.i("POST api url= " + request.url() + "&" + bodyToString(request.body()));
                } else {
                    Logger.i("POST api url= " + request.url() + "?" + bodyToString(request.body()));
                }
            } else {
                Logger.i("api url=" + String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }

        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Logger.i("api url=" + String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
