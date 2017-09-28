package com.me.rxdownload.download;

import android.util.Log;

import com.me.rxdownload.RxDownloadManager;
import com.me.rxdownload.db.IDownloadDB;
import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.utils.IOUtils;
import com.me.rxdownload.utils.L;
import com.me.rxdownload.utils.RxUtils;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;

/**
 * 真正的文件下载
 * Created by mayunfei on 17-3-27.
 */

public class ItemTask {

    private static final int DOWNLOAD_CHUNK_SIZE = 2048;
    private static final String TAG = "ItemTask";
    private DownloadApi mDownloadApi;
    private IDownloadDB downloadDB;
    //用于取消
    private Disposable disposable;
    private DownloadBean downloadBean;

    private volatile boolean isCancel = false;
    private long startTiem;

    public synchronized void pause() {
        //避免出现 取消后继续下载
        isCancel = true;
        try {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        } catch (Throwable e) {
            L.e(TAG, "pause " + e.toString());
        }


    }

    public ItemTask(DownloadApi mDownloadApi, IDownloadDB downloadDB, DownloadBean downloadBean) {
        this.mDownloadApi = mDownloadApi;
        this.downloadDB = downloadDB;
        this.downloadBean = downloadBean;
    }

    public  void startDownload(Executor executor,final Semaphore semaphore, final DownloadTask.TaskObserver taskObserver)
            throws InterruptedException {
        if (isCancel) {
            return;
        }
        semaphore.acquire();
        if (isCancel) {
            semaphore.release();
            return;
        }

        disposable = download(downloadBean).toObservable()
                .subscribeOn(Schedulers.from(executor))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        taskObserver.onSubscribe(disposable);
//                        L.i(TAG, "start " + downloadBean.getUrl());
                        startTiem = System.currentTimeMillis();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        semaphore.release();
                        L.i(TAG, "doFinally " + downloadBean.getUrl());
                    }
                })
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        if (isCancel) {
                            if (disposable != null && !disposable.isDisposed()) {
                                disposable.dispose();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        L.e(TAG, throwable + downloadBean.getUrl());
                        taskObserver.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //finishe
                        long end = System.currentTimeMillis() - startTiem;
                        taskObserver.onComplete(downloadBean.getTotalSize() * 1.0 / end * 1000 * RxDownloadManager.MAX_DOWNLOAD_COUNT);
                    }
                });
    }

    /**
     * 单一任务下载
     */
    private synchronized Flowable<DownloadEvent> download(final DownloadBean downloadBean) {

        if (downloadBean.getMultiple_type() == DownloadBean.TYPE_MULTIPLE) {
            String rangeStr = "bytes=" + downloadBean.getStartSize() + "-" + (downloadBean.getStartSize() + downloadBean.getTotalSize());
            return mDownloadApi.download(rangeStr, downloadBean.getUrl())
                    .flatMap(new Function<Response<ResponseBody>, Publisher<DownloadEvent>>() {
                        @Override
                        public Publisher<DownloadEvent> apply(@NonNull Response<ResponseBody> response)
                                throws Exception {
                            return save(downloadBean, response);
                        }
                    }).compose(RxUtils.<DownloadEvent>retry(downloadBean, downloadDB))
                    .doOnNext(new Consumer<DownloadEvent>() {
                        @Override
                        public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                            downloadBean.setCompletedSize(downloadEvent.getCompletedSize());
//                            downloadBean.setTotalSize(downloadEvent.getTotalSize());
                            downloadDB.updateDownloadBean(downloadBean);
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            downloadDB.setBeanFinished(downloadBean.getId());
                        }
                    });
        } else {
            return mDownloadApi.download(downloadBean.getUrl())
                    .flatMap(new Function<Response<ResponseBody>, Publisher<DownloadEvent>>() {
                        @Override
                        public Publisher<DownloadEvent> apply(@NonNull Response<ResponseBody> response)
                                throws Exception {
                            return save(downloadBean, response);
                        }
                    })
                    .compose(RxUtils.<DownloadEvent>retry(downloadBean, downloadDB))
                    .doOnNext(new Consumer<DownloadEvent>() {
                        @Override
                        public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                            downloadBean.setCompletedSize(downloadEvent.getCompletedSize());
                            downloadBean.setTotalSize(downloadEvent.getTotalSize());
                            downloadDB.updateDownloadBean(downloadBean);
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            downloadDB.setBeanFinished(downloadBean.getId());
                        }
                    });
        }

    }

    /**
     * 将请求存储 并重试
     */
    private Publisher<DownloadEvent> save(final DownloadBean bean,
                                          final Response<ResponseBody> response) {
        return Flowable.create(new FlowableOnSubscribe<DownloadEvent>() {
            @Override
            public void subscribe(FlowableEmitter<DownloadEvent> emitter) throws Exception {
                if (bean.isFinished()) { //优先级是 low 的重试之后会设置为finished
                    emitter.onComplete();
                } else {
                    if (bean.getMultiple_type() == DownloadBean.TYPE_MULTIPLE) {

                        RandomAccessFile save = null;
//                        FileChannel saveChannel = null;
                        InputStream inStream = null;
                        try {
                            try {
                                int readLen;
                                byte[] buffer = new byte[DOWNLOAD_CHUNK_SIZE];

                                DownloadEvent downloadEvent = new DownloadEvent();

                                long start = bean.getStartSize();

                                long totalSize = bean.getTotalSize();

                                downloadEvent.setTotalSize(totalSize);

                                save = new RandomAccessFile(new File(bean.getPath(), bean.getFileName()), "rw");
//                                saveChannel = save.getChannel();
                                save.seek(start);
                                inStream = response.body().byteStream();




                                while ((readLen = inStream.read(buffer)) != -1 && !emitter.isCancelled()) {
//                                    MappedByteBuffer saveBuffer = saveChannel.map(READ_WRITE, start, readLen);
                                    start += readLen;
//                                    saveBuffer.put(buffer, 0, readLen);
                                    save.write(buffer,0,readLen);
                                    downloadEvent.setCompletedSize(start - bean.getStartSize());
                                    if (!emitter.isCancelled()) {
                                        emitter.onNext(downloadEvent);
                                    }
                                }
                                if (!emitter.isCancelled()) {
                                    emitter.onComplete();
                                }
                                Log.i("+++",Thread.currentThread().getName()+" save");
                            } finally {
                                IOUtils.close(save);
//                                IOUtils.close(saveChannel);
                                IOUtils.close(inStream);
//                                IOUtils.close(response);

                            }
                        } catch (IOException e) {
                            if (e instanceof StreamResetException && ((StreamResetException) e).errorCode == ErrorCode.CANCEL) {

                            } else {
                                L.e(TAG, " save TYPE_MULTIPLE " + e);
                                if (!emitter.isCancelled()) {
                                    emitter.onError(e);
                                } else {
                                    L.e(TAG, " have canceled");
                                }
                            }
                        }


                    } else {
                        saveFile(emitter, response, bean.getPath(), bean.getFileName());
                    }
                }
            }
        }, BackpressureStrategy.LATEST);
    }

    /**
     * 存储下载结果
     */
    private void saveFile(FlowableEmitter<DownloadEvent> emitter, Response<ResponseBody> response,
                          String path, String name) {
        if (!response.isSuccessful()) {
            emitter.onError(new HttpException(response));
            return;
        }
        BufferedSink sink = null;
        BufferedSource source = null;
        try {
            DownloadEvent downloadEvent = new DownloadEvent();
            ResponseBody body = response.body();
            downloadEvent.setTotalSize(body.contentLength());
            File file = new File(path, name);
            sink = Okio.buffer(Okio.sink(file));
//            long totalRead = 0;
//            long read = 0;
            downloadEvent.setStatus(DOWNLOADING);
            source = body.source();
//            while ((read = (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE))) != -1
//                    && !emitter.isCancelled()) {
//                totalRead += read;
//                downloadEvent.setCompletedSize(totalRead);
//                //IO
//                if (!emitter.isCancelled()) {
//                    emitter.onNext(downloadEvent);
//                }
//            }
            sink.writeAll(source);
            downloadEvent.setCompletedSize(downloadEvent.getTotalSize());
            if (!emitter.isCancelled()) {
                emitter.onNext(downloadEvent);
            }
//            source.close();
//            sink.close();
            Log.i("+++",Thread.currentThread().getName()+" save");
            if (!emitter.isCancelled()) {
                emitter.onComplete();
            }
        } catch (IOException e) {
//            e.printStackTrace();
            if (e instanceof StreamResetException && ((StreamResetException) e).errorCode == ErrorCode.CANCEL) {
            } else {
                L.e(TAG, " save TYPE_SINGLE" + e);
                if (!emitter.isCancelled()) {
                    emitter.onError(e);
                } else {
                    L.e(TAG, " have canceled");
                }
            }

        } finally {
            IOUtils.close(sink, source);
        }
    }
}
