package com.me.rxdownload.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.me.rxdownload.RxDownloadManager;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;

import java.util.List;
import java.util.concurrent.Semaphore;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 保证service 是同步的
 * Created by mayunfei on 17-3-23.
 */

public class ServiceHelper {
    private static final Object object = new Object();
    private volatile static boolean bound = false;
    private Context context;
    //信号量  用于绑定service
    private Semaphore semaphore;

    private DownloadService downloadService;

    public ServiceHelper(Context context) {
        this.context = context.getApplicationContext();
        semaphore = new Semaphore(1);
    }

    public Observable<?> addTask(final DownloadTask task) {
        return createGeneralObservable(new GeneralObservableCallback() {
            @Override
            public void call() throws Exception {
                downloadService.addTask(task);
            }
        });
    }

    public Observable<?> createGeneralObservable(final GeneralObservableCallback callback) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
                if (!bound) {
                    semaphore.acquire();
                    if (!bound) {
                        startBindServiceAndDo(new ServiceConnectedCallback() {
                            @Override
                            public void call() {
                                doCall(callback, emitter);
                                semaphore.release();
                            }
                        });
                    } else {
                        doCall(callback, emitter);
                        semaphore.release();
                    }
                } else {
                    doCall(callback, emitter);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private void doCall(GeneralObservableCallback callback, ObservableEmitter<Object> emitter) {
        if (callback != null) {
            try {
                callback.call();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }
        emitter.onNext(object);
        emitter.onComplete();
    }

    /**
     * start and bind service.
     *
     * @param callback Called when service connected.
     */
    private void startBindServiceAndDo(final ServiceConnectedCallback callback) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.INTENT_KEY, RxDownloadManager.MAX_DOWNLOAD_COUNT);
        context.startService(intent);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                //https://bugly.qq.com/v2/crash-reporting/crashes/1d5a200bdb/54?pid=1
                if (binder instanceof  DownloadService.DownloadBinder){
                    DownloadService.DownloadBinder downloadBinder = (DownloadService.DownloadBinder) binder;
                    downloadService = downloadBinder.getService();
                    bound = true;
                    callback.call();
                    context.unbindService(this);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //注意!!这个方法只会在系统杀掉Service时才会调用!!
                bound = false;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    public Observable<DownloadEvent> getDownloadEvent(final String key) {
        return createGeneralObservable(null).flatMap(
                new Function<Object, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull Object o)
                            throws Exception {
                        return downloadService.getDownloadEvent(key).toObservable();
                    }
                });
    }

    public Observable<?> pause(final String key) {
        return createGeneralObservable(new GeneralObservableCallback() {
            @Override
            public void call() throws Exception {
                downloadService.pause(key);
            }
        });
    }

    public Observable<List<DownloadBundle>> getAllDownloadBundle() {
        return createGeneralObservable(null).flatMap(
                new Function<Object, ObservableSource<List<DownloadBundle>>>() {
                    @Override
                    public ObservableSource<List<DownloadBundle>> apply(@NonNull Object o)
                            throws Exception {
                        return downloadService.getAllDownloadBundle();
                    }
                });
    }

    public Observable<?> delete(final String key) {
        return createGeneralObservable(new GeneralObservableCallback() {
            @Override
            public void call() throws Exception {
                downloadService.delete(key);
            }
        });
    }

    public interface GeneralObservableCallback {
        void call() throws Exception;
    }

    private interface ServiceConnectedCallback {
        void call();
    }
}
