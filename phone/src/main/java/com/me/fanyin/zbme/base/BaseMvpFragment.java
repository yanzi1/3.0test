package com.me.fanyin.zbme.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.me.data.mvp.BasePersenter;
import com.me.data.mvp.MvpView;
import com.me.fanyin.zbme.dagger.component.AppComponent;
import com.me.fanyin.zbme.dagger.component.DaggerFragmentComponent;
import com.me.fanyin.zbme.dagger.component.FragmentComponent;
import com.me.fanyin.zbme.dagger.modules.FragmentModule;

import javax.inject.Inject;

public abstract class BaseMvpFragment<V extends MvpView, T extends BasePersenter<V>> extends BaseFragment implements MvpView, View.OnClickListener {

    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    // 初始化
    protected void init() {
        initInject();
        mPresenter.attachView((V) this);
    }

    protected abstract void initInject();

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    protected FragmentComponent getAppComponent() {
        return DaggerFragmentComponent.builder().appComponent(AppComponent.getInstance()).fragmentModule
                (getModule()).build();
    }

    protected FragmentModule getModule() {
        return new FragmentModule(this);
    }

    @Override
    public void showLoading() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showLoading();
    }

    @Override
    public void hideLoading() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showContent();
    }

    @Override
    public void showEmptyData() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showEmptyData();
    }

    @Override
    public void showErrorData() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showError();
    }

    @Override
    public void showNetworkError() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showNetWorkError();
    }

    @Override
    public void showNoPermission() {
        if(mStatusLayoutManager != null)
            mStatusLayoutManager.showNoPermission();
    }

    @Override
    public void showDialogLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideDialogLoading() {
        if (loadingDialog.isShowing())
        loadingDialog.dismiss();
    }


    @Override
    public Activity context() {
        return getActivity();
    }
}
