package com.me.rxdownload.download;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yunfei on 17-3-25.
 */

public interface DownloadApi {
    @GET
    @Streaming
    Flowable<Response<ResponseBody>> download(@Header("Range") String range,
                                              @Url String url);

    @GET
    @Streaming
    Flowable<Response<ResponseBody>> download(@Url String url);
}
