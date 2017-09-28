package com.me.fanyin.zbme.views.download;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadAdapter;
import com.me.rxdownload.db.DownloadDao;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.entity.DownloadStatus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xunice on 13/03/2017.
 */

public class MyDownloadPresenter extends BasePersenter<MyDownloadView> {

    private List<DownloadBundle> downloadingList;
    private List<MyDownloadAdapter.GroupItem> data;
    private boolean isLoading = false;

    @Inject
    public MyDownloadPresenter() {
        downloadingList = new ArrayList<>();
    }

    public void getDownloading() {
        Disposable subscribe = DownloadManager.getInstance().getDownloadingBundle()
                .subscribe(new Consumer<List<DownloadBundle>>() {
                    @Override
                    public void accept(@NonNull List<DownloadBundle> downloadBundleList) throws Exception {
                        if (downloadBundleList != null && downloadBundleList.size() > 0) {
                            downloadingList.clear();
                            downloadingList.addAll(downloadBundleList);
                            getMvpView().showHeader();
                            boolean isDwonloading = false;

                            for (DownloadBundle bundle :
                                    downloadBundleList) {
                                if (bundle.getStatus() == DownloadStatus.DOWNLOADING || bundle.getStatus() == DownloadStatus.QUEUE) {

//                                    Disposable subscribe1 = DownloadManager.getInstance().getDownloadEvent(bundle.getKey())
//                                            .subscribe(new Consumer<DownloadEvent>() {
//                                                @Override
//                                                public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//
//                                                }
//                                            });
//                                    addSubscription(subscribe1);

                                    isDwonloading = true;
                                    break;
                                }
                            }
                            getMvpView().setHeaderCount((isDwonloading ? "正在缓存" : "暂停中") + " (" + downloadBundleList.size() + ")");
                            setLoadingView();
                            isLoading = true;
                            getMvpView().showData();
                        } else {
                            getMvpView().hideHeader();
                            isLoading = false;
                        }
                    }
                });
        addSubscription(subscribe);

    }

    private void setLoadingView() {
        DownloadBundle downloading = null;
        for (DownloadBundle bundle : downloadingList) {
            if (bundle.getStatus() == DownloadStatus.DOWNLOADING) {
                downloading = bundle;
                break;
            }
        }
        if (downloading == null) {
            downloading = downloadingList.get(0);
        }
        //设置正在下载的数据
        getMvpView().setHeaderDownloadingTitle(downloading.getCwName());


        Disposable subscribe = DownloadManager.getInstance().getDownloadEvent(downloading.getKey())
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        if (downloadEvent.getStatus() == DownloadStatus.FINISH) {
                            getDownloading();
                            getData();
                        } else {
                            getMvpView().setHeaderProgressBar(downloadEvent.getTotalSize(), downloadEvent.getCompletedSize());
                        }
                    }
                });
        addSubscription(subscribe);

    }

    @Override
    public void getData() {
//        Observable.just(testSource())
        Disposable subscribe = DownloadManager.getInstance().getDownloadFinishedGroupByClassId()
                .observeOn(Schedulers.computation())
                .map(new Function<List<DownloadDao.DownloadResultByClassId>, List<MyDownloadAdapter.GroupItem>>() {
                    @Override
                    public List<MyDownloadAdapter.GroupItem> apply(@NonNull List<DownloadDao.DownloadResultByClassId> downloadResultByClassIds) throws Exception {
                        List<MyDownloadAdapter.GroupItem> groupItems = new ArrayList<MyDownloadAdapter.GroupItem>();

                        for (DownloadDao.DownloadResultByClassId resultByClassId : downloadResultByClassIds) {
                            MyDownloadAdapter.GroupItem group = null;
                            DownloadBundle downloadBundle = resultByClassId.getDownloadBundle();
                            for (MyDownloadAdapter.GroupItem groupitem : groupItems
                                    ) {
                                if (groupitem.subjectId == downloadBundle.getsSubjectId()) {
                                    group = groupitem;
                                }
                            }

                            if (group == null) {
                                group = new MyDownloadAdapter.GroupItem();
                                group.className = downloadBundle.getClassName();
                                group.subjectName = downloadBundle.getsSubjectName();
                                group.subjectId = downloadBundle.getsSubjectId();
                                group.childItems = new ArrayList<MyDownloadAdapter.ChildItem>();
                                groupItems.add(group);
                            }

                            CourseWare courseWare = JSON.parseObject(downloadBundle.getCwStr(), CourseWare.class);
                            MyDownloadAdapter.ChildItem childItem = new MyDownloadAdapter.ChildItem();
                            childItem.classId = downloadBundle.getClassId() + "";
                            childItem.subjectId = downloadBundle.getsSubjectId() + "";
                            childItem.examId = downloadBundle.getExamId() + "";
                            childItem.count = resultByClassId.count;
                            childItem.className = downloadBundle.getClassName();
                            childItem.subjectName = downloadBundle.getsSubjectName();
                            childItem.imageUrl = courseWare.getPicPath();
                            group.childItems.add(childItem);
                            Log.i("download ", " isDownloadFinishedBy = " + DownloadManager.getInstance().isCWDownlaoded(courseWare));
                            Log.i("download ", " m3u8 = " + DownloadManager.getInstance().getM3u8Path(courseWare));
                            Log.i("download ", " mp3 = " + DownloadManager.getInstance().getMp3Path(courseWare));
                            Log.i("download ", " getLecturePath = " + DownloadManager.getInstance().getLecturePath(courseWare));

                        }

                        return groupItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showDialogLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                    }
                }).subscribe(new Consumer<List<MyDownloadAdapter.GroupItem>>() {
                    @Override
                    public void accept(@NonNull List<MyDownloadAdapter.GroupItem> groupItems) throws Exception {
                        data = groupItems;
                        getMvpView().onSelect(0);
                        if (data.size() == 0 && !isLoading) {
                            getMvpView().showEmpty();
                        } else {
                            getMvpView().showData();
                        }
                        getMvpView().showDownloadedData(groupItems);
                    }
                });

        addSubscription(subscribe);


    }


    public void deleteSelect() {
        if (data != null) {
            List<Observable<List<Object>>> observableList = new ArrayList<>();
            for (MyDownloadAdapter.GroupItem groupItem : data) {
                for (MyDownloadAdapter.ChildItem childItem : groupItem.childItems) {
                    if (childItem.checked) {
                        Observable<List<Object>> observable = DownloadManager.getInstance().deleteFinishedByExamIdSubjectIdClassId(childItem.examId, childItem.subjectId, childItem.classId);
                        observableList.add(observable);
                    }
                }
            }

            Disposable subscribe = Observable.merge(observableList)
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            getMvpView().showDialogLoading();
                        }
                    })
                    .doFinally(new Action() {
                        @Override
                        public void run() throws Exception {
                            getMvpView().hideDialogLoading();
                            getData();
                        }
                    }).subscribe(new Consumer<List<List<Object>>>() {
                        @Override
                        public void accept(@NonNull List<List<Object>> booleen) throws Exception {
                            getMvpView().deleteSuccess();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            getMvpView().deleteFail();
                        }
                    });

            mDisposable.add(subscribe);


        }
    }

    public void delete(MyDownloadAdapter.ChildItem childItem) {
        Disposable subscribe = DownloadManager.getInstance()
                .deleteFinishedByExamIdSubjectIdClassId(childItem.examId, childItem.subjectId, childItem.classId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showDialogLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                        getData();
                    }
                }).subscribe(new Consumer<List<Object>>() {
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
        mDisposable.add(subscribe);
    }
}
