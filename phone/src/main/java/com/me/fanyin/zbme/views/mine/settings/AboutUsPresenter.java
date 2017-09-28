package com.me.fanyin.zbme.views.mine.settings;

import android.text.TextUtils;

import com.me.data.model.BaseRes;
import com.me.data.model.mine.VersionInfo;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.fanyin.zbme.base.CommonWebViewActivity;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jjr on 2017/6/27.
 */

class AboutUsPresenter extends BasePersenter<AboutUsView> {

    @Inject
    AboutUsPresenter() {

    }

    @Override
    public void getData() {

    }

    public void getVersionInfo() {
        Disposable subscribe = ApiService.getVersionDesc()
                .compose(RxUtils.<BaseRes<VersionInfo>>ioMain())
                .compose(RxUtils.<BaseRes<VersionInfo>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<VersionInfo>retrofit())
                .compose(RxUtils.<VersionInfo>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<VersionInfo>() {
                    @Override
                    public void accept(@NonNull VersionInfo versionInfo) throws Exception {
                        if (!TextUtils.isEmpty(versionInfo.getExplainAddress())) {
                            CommonWebViewActivity.startActivity(getMvpView().context(), "版本说明", versionInfo.getExplainAddress());
                        }
                    }
                });
        addSubscription(subscribe);
    }
}
