package com.me.fanyin.zbme.views.download;

import com.me.core.exception.ErrorHandlers;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.rxdownload.entity.DownloadBundle;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by mayunfei on 17-6-3.
 */

public class DownloadMorePresenter extends BasePersenter<DownloadMoreView> {

    private CourseWare courseWare;

    @Inject
    public DownloadMorePresenter() {
    }

    @Override
    public void getData() {

    }


    public void getDownloading() {
        Disposable subscribe = DownloadManager.getInstance().getDownloadingBundle()
                .subscribe(new Consumer<List<DownloadBundle>>() {
                    @Override
                    public void accept(@NonNull List<DownloadBundle> downloadBundleList) throws Exception {
                        if (downloadBundleList != null && downloadBundleList.size() > 0) {
                            getMvpView().setdownloadCount(downloadBundleList.size());
                        } else {
                            getMvpView().setdownloadCount(0);
                        }
                    }
                });
        mDisposable.add(subscribe);


    }


    public void getData(CourseWare cw) {
        this.courseWare = cw;
        Disposable subscribe = ApiService.playDetail(ParamsUtils.playDetail(cw.getClassId()))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        getMvpView().showDialogLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                    }
                })
                .compose(RxUtils.<CourseDetail>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<CourseDetail>() {
                               @Override
                               public void accept(CourseDetail courseDetail) throws Exception {
                                   CourseDetail reCourseDetail = reSetDatas(courseDetail, courseWare);
                                   getMvpView().setData(courseDetail);
                               }
                           }, ErrorHandlers.displayErrorConsumer(getMvpView().context())
                );
        addSubscription(subscribe);
    }


    private CourseDetail reSetDatas(CourseDetail courseDetail, CourseWare courseWare) {

        String sSId = courseDetail.getsSubjectId();
        String sSname = courseDetail.getsSubjectName();
        String picPath = courseDetail.getPicPath();
        String className = courseDetail.getName();
        String id = courseDetail.getId();
        for (int i = 0; i < courseDetail.getResultList().size(); i++) {
            for (int k = 0; k < courseDetail.getResultList().get(i).getPcClientCourseWareList().size(); k++) {
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setsSubjectId(sSId);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setClassId(id);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setPicPath(picPath);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setsSubjectName(sSname);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setClassName(className);
            }
        }
        return courseDetail;
    }
}
