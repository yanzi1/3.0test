package com.me.fanyin.zbme.views.download;

import com.me.data.mvp.BasePersenter;
import com.me.data.remote.rxjava.RxUtils;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

import static com.me.rxdownload.entity.DownloadStatus.DELETED;
import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;


public class DownloadManagerPresenter extends BasePersenter<DownloadManagerView> {

    private FlowableProcessor<DownloadEvent> publishProcessor;
    private HashMap<String, DownloadEventItem> data;

    @Inject
    public DownloadManagerPresenter() {
        data = new LinkedHashMap<>();
        publishProcessor = PublishProcessor.<DownloadEvent>create().toSerialized();
    }

    @Override
    public void getData() {
        DownloadManager.getInstance().getDownloadingBundle()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideLoading();
                    }
                })
                .flatMap(new Function<List<DownloadBundle>, ObservableSource<DownloadBundle>>() {
                    @Override
                    public ObservableSource<DownloadBundle> apply(@NonNull List<DownloadBundle> downloadBundleList) throws Exception {
                        return Observable.fromIterable(downloadBundleList);
                    }
                }).doOnNext(new Consumer<DownloadBundle>() {
            @Override
            public void accept(@NonNull final DownloadBundle downloadBundle) throws Exception {

                DownloadEventItem downloadEventItem = data.get(downloadBundle.getKey());
                if (downloadEventItem == null) {
                    downloadEventItem = new DownloadEventItem();
                } else {
                    RxUtils.dispose(downloadEventItem.disposable);
                }
                downloadEventItem.downloadBundle = downloadBundle;
                final DownloadEventItem finalDownloadEventItem = downloadEventItem;


                //获得每个下载的状态
                downloadEventItem.disposable = DownloadManager.getInstance().getDownloadEvent(downloadBundle.getKey()).subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        finalDownloadEventItem.status = downloadEvent.getStatus();

                        if (downloadEvent.getStatus() == FINISH || downloadEvent.getStatus() == DELETED) {
                            data.remove(downloadBundle.getKey());
                        }

                        if (publishProcessor != null) {
                            publishProcessor.onNext(downloadEvent);
                        }
                    }
                });
                data.put(downloadBundle.getKey(), downloadEventItem);


            }
        }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DownloadBundle>>() {
                    @Override
                    public void accept(@NonNull List<DownloadBundle> downloadBundles) throws Exception {
                        getMvpView().showData(downloadBundles);
                    }
                });


        Disposable subscribe = publishProcessor
                .sample(500, TimeUnit.MILLISECONDS)
                .flatMap(new Function<DownloadEvent, Publisher<Boolean>>() {
                    @Override
                    public Publisher<Boolean> apply(@NonNull final DownloadEvent downloadEvent) throws Exception {
                        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {

                                if (downloadEvent.getStatus() == DOWNLOADING) {
                                    //全部暂停
                                    e.onNext(false);
                                } else {
                                    boolean pause = true;
                                    for (DownloadEventItem item :
                                            data.values()) {
                                        if (item.status == DOWNLOADING) {
                                            pause = false;
                                            break;
                                        }
                                    }
                                    if (pause) {
                                        e.onNext(true);
                                    } else {
                                        e.onNext(false);
                                    }

                                }
                                e.onComplete();
                            }
                        }, BackpressureStrategy.LATEST);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //全部开始
                            Logger.i("全部开始");
                            getMvpView().showStartAll();
                        } else {
                            //全部暂停
                            Logger.i("全部暂停");
                            getMvpView().showPauseAll();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Logger.e("error ==  %s", throwable);
                    }
                });
        mDisposable.add(subscribe);

    }


    @Override
    public void detachView() {
        super.detachView();
        for (DownloadEventItem item : data.values()) {
            RxUtils.dispose(item.disposable);
        }
        publishProcessor.onComplete();
        publishProcessor = null;
    }

    public void startAll() {
        Observable.fromIterable(data.values())
                .map(new Function<DownloadEventItem, DownloadBundle>() {
                    @Override
                    public DownloadBundle apply(@NonNull DownloadEventItem downloadEventItem) throws Exception {
                        return downloadEventItem.downloadBundle;
                    }
                })
                .flatMap(new Function<DownloadBundle, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull DownloadBundle downloadBundle) throws Exception {
                        return DownloadManager.getInstance().addDownloadTask(downloadBundle);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                        getMvpView().hideLoading();
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                getMvpView().showDialogLoading();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void pauseAll() {
        Observable.fromIterable(data.values())
                .map(new Function<DownloadEventItem, DownloadBundle>() {
                    @Override
                    public DownloadBundle apply(@NonNull DownloadEventItem downloadEventItem) throws Exception {
                        return downloadEventItem.downloadBundle;
                    }
                })
                .flatMap(new Function<DownloadBundle, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull DownloadBundle downloadBundle) throws Exception {
                        return DownloadManager.getInstance().pause(downloadBundle.getKey());
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                        getMvpView().hideLoading();
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                getMvpView().showDialogLoading();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteSelect(List<String> select) {
        Observable.fromIterable(select)
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        return DownloadManager.getInstance().delete(s);
                    }
                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                        getMvpView().hideLoading();
                        getData();
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showDialogLoading();
                        getMvpView().showLoading();
                    }
                })
                .subscribe(new Consumer<List<Object>>() {
                    @Override
                    public void accept(@NonNull List<Object> objects) throws Exception {
                        getMvpView().deleteSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().deleteFail();
                    }
                });

    }


    private static class DownloadEventItem {
        Disposable disposable;
        DownloadBundle downloadBundle;
        int status;
    }

}
