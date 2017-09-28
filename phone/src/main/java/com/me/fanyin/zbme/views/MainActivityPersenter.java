package com.me.fanyin.zbme.views;

import android.widget.Toast;

import com.me.core.app.AppManager;
import com.me.core.exception.ErrorHandlers;
import com.me.data.model.mine.Provice;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by xunice on 13/03/2017.
 */

public class MainActivityPersenter extends BasePersenter<MainActivityView> {

    private List<Provice> proviceList = new ArrayList<>();

    @Inject
    MainActivityPersenter() {
    }

    @Override
    public void attachView(MainActivityView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void getData() {
       // getProvice();
    }


    public void onBackPressed() {
        doublePressBackToast();
    }

    private boolean isBackPressed = false;

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            Toast.makeText(getMvpView().context(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
        } else {
            AppManager.getAppManager().AppExit(getMvpView().context(),true);
        }
        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                isBackPressed = false;
            }
        });
    }

    /**
     * 听课记录上传
     */
    public void upLoadVideos(String studyLog) {
        Disposable subscribe = ApiService.upLoadVideos(ParamsUtils.getInstance().upLoadVideos(studyLog))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   getMvpView().postStudyLogSucess();
                               }
                           }, ErrorHandlers.displayErrorConsumer(getMvpView().context())
                );
        addSubscription(subscribe);
    }

    /**
     * 智能系统听课记录上传
     */
    public void upLoadVideosSmart(String studyLog) {
        Disposable subscribe = ApiService.upLoadVideosSmart(ParamsUtils.getInstance().upLoadVideos(studyLog))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   getMvpView().postStudyLogSucess();
                               }
                           }, ErrorHandlers.displayErrorConsumer(getMvpView().context())
                );
        addSubscription(subscribe);
    }
}
