package com.me.fanyin.zbme.views.mine.settings;

import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.fanyin.zbme.R;

import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xd on 2017/5/19.
 */

public class SettingsPresenter extends BasePersenter<SettingsView> {

    @Inject
    public SettingsPresenter() {
    }

    @Override
    public void getData() {

    }

    public void logout() {
        Disposable subscribe = ApiService.logout()
                .compose(RxUtils.<BaseRes<String>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>showDialogLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        SharedPrefHelper.getInstance().logout();
                        getMvpView().logoutSuccess(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        String msg=throwable.getMessage();
                        if (throwable instanceof UnknownHostException)
                            msg=getMvpView().context().getString(R.string.app_nonetwork_message);
                        getMvpView().showError(msg);
                    }
                });
        addSubscription(subscribe);
    }
}
