package com.me.fanyin.zbme.views.download;

import com.alibaba.fastjson.JSON;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.rxdownload.entity.DownloadBundle;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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

public class MyDownloadCoursePresenter extends BasePersenter<MyDownloadCourseView> {

    private DownloadManager downloadManager;
    private String examId;
    private String subjectId;
    private String classId;

    @Inject
    public MyDownloadCoursePresenter() {
        this.downloadManager = DownloadManager.getInstance();

    }

    @Override
    public void getData() {
        getData(examId,subjectId,classId);
    }


    /**
     * 根据 subject class 查询
     */
    public void getData(String examId ,String subjectId, String classId) {
        this.examId = examId;
        this.subjectId = subjectId;
        this.classId = classId;
        downloadManager.getDownloadedBundleBySubjectAndClass(subjectId, classId)
                .observeOn(Schedulers.computation())
                .flatMap(new Function<List<DownloadBundle>, ObservableSource<DownloadBundle>>() {
                    @Override
                    public ObservableSource<DownloadBundle> apply(@NonNull List<DownloadBundle> downloadBundleList) throws Exception {
                        return Observable.fromIterable(downloadBundleList);
                    }
                }).map(new Function<DownloadBundle, CourseWare>() {
            @Override
            public CourseWare apply(@NonNull DownloadBundle downloadBundle) throws Exception {
                return JSON.parseObject(downloadBundle.getCwStr(), CourseWare.class);
            }
        }).toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CourseWare>>() {
                    @Override
                    public void accept(@NonNull List<CourseWare> courseWares) throws Exception {
                        getMvpView().showData(courseWares);
                    }
                });
//        .subscribe(new Consumer<List<DownloadBundle>>() {
//            @Override
//            public void accept(@NonNull List<DownloadBundle> downloadBundleList) throws Exception {
//
//            }
//        });
    }

    public void deleteSelect(final List<String> select) {
        Observable.fromIterable(select)
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        return downloadManager.delete(s);
                    }
                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideLoading();
                        getData();
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showLoading();
                    }
                })
                .subscribe(new Consumer<List<Object>>() {
                    @Override
                    public void accept(@NonNull List<Object> objects) throws Exception {
                        getMvpView().deleteSuccess(select);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().deleteFail();
                    }
                });

    }
}
