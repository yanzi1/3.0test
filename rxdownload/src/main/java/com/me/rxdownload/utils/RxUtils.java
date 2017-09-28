package com.me.rxdownload.utils;

import com.me.rxdownload.db.IDownloadDB;
import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadEvent;

import org.reactivestreams.Publisher;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import okhttp3.internal.http2.StreamResetException;
import retrofit2.HttpException;

/**
 * Created by yunfei on 17-3-26.
 */

public class RxUtils {
    /**
     * 默认重试的次数
     */
    private static final int RETRY_COUNT = 3;

    /**
     * 下载重试
     */
    public static  FlowableTransformer<DownloadEvent, DownloadEvent> retry(final DownloadBean downloadBean,
                                                      final IDownloadDB downloadDB) {
        return new FlowableTransformer<DownloadEvent, DownloadEvent>() {
            @Override
            public Publisher<DownloadEvent> apply(Flowable<DownloadEvent> upstream) {



                if (downloadBean.isFinished()){
                    DownloadEvent downloadEvent = new DownloadEvent();
                    downloadEvent.setCompletedSize(downloadBean.getCompletedSize());
                    downloadEvent.setTotalSize(downloadBean.getTotalSize());
                    return Flowable.just(downloadEvent);
                }


                return upstream.retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable)
                            throws Exception {

                        return retry(downloadBean, downloadDB, integer, throwable);
                    }
                });
            }
        };
    }

    private RxUtils() {

    }

    /**
     * 重试规则
     */
    private static boolean retry(DownloadBean downloadBean, IDownloadDB downloadDB, Integer count,
                                 Throwable throwable) {
        L.i("重试中.. " + count + " throwable = " + throwable);
        if (throwable instanceof ProtocolException) {
            if (count < RETRY_COUNT + 1) {
                return true;
            }
            return updateFinished(downloadBean, downloadDB);
        } else if (throwable instanceof UnknownHostException) {
//            if (count < RETRY_COUNT + 1) {
//                return true;
//            }
//            return updateFinished(downloadBean, downloadDB);
            return false;
        } else if (throwable instanceof HttpException) {
            if (count < RETRY_COUNT + 1) {
                return true;
            }
            return updateFinished(downloadBean, downloadDB);
        } else if (throwable instanceof SocketTimeoutException) {
            if (count < RETRY_COUNT + 1) {
                return true;
            }
            return false;
        } else if (throwable instanceof ConnectException) {
            if (count < RETRY_COUNT + 1) {
                return true;
            }
            return false;
        } else if (throwable instanceof SocketException) {
            if (count < RETRY_COUNT + 1) {
                return true;
            }
            return updateFinished(downloadBean, downloadDB);
        } else if (throwable instanceof StreamResetException) {
            return false;
        } else {
            return updateFinished(downloadBean, downloadDB);
        }
    }

    private static boolean updateFinished(DownloadBean downloadBean, IDownloadDB downloadDB) {
        if (downloadBean.getPriority() == DownloadBean.PRIORITY_LOW) {
            downloadBean.setCompletedSize(1);
            downloadBean.setTotalSize(1);
            downloadBean.setFinished(true);
            downloadDB.updateDownloadBean(downloadBean);
            return true;
        }
        return false;
    }

    public static FlowableProcessor<DownloadEvent> createProcessor(String key,
                                                                   Map<String, FlowableProcessor<DownloadEvent>> processorMap) {

        if (processorMap.get(key) == null) {
            FlowableProcessor<DownloadEvent> processor =
                    BehaviorProcessor.<DownloadEvent>create().toSerialized();
            processorMap.put(key, processor);
        }
        return processorMap.get(key);
    }
}
