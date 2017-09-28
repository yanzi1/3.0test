package com.me.fanyin.zbme.views.main.activity;

import com.me.data.mvp.BasePersenter;

import javax.inject.Inject;


/**
 * Created by wyc on 13/03/2017.
 */

public class MainTypeChangePersenter extends BasePersenter<MainTypeChangeView> {

    @Inject
	MainTypeChangePersenter() {

    }

    @Override
    public void attachView(MainTypeChangeView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void getData() {

    }


    private boolean isBackPressed = false;

}
