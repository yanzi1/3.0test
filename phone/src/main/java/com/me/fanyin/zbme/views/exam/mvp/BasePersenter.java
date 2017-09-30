package com.me.fanyin.zbme.views.exam.mvp;

import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.app.AppManager;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.remote.ApiModel;
import com.me.fanyin.zbme.views.exam.remote.ResultListener;
import com.me.fanyin.zbme.views.exam.remote.bean.BaseBean;

/**
 * 登录UI
 */
public abstract class BasePersenter<T extends MvpView> implements Presenter<T> , ResultListener {

    public ApiModel apiModel;

    public BasePersenter(){
        apiModel = new ApiModel(this);
    }


    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    @Override
    public void onSuccess(String json) {
        try {
            BaseBean baseBean = JSON.parseObject(json,BaseBean.class);
            if(baseBean == null){
                getMvpView().showError("");
                return;
            }
            if(baseBean.getCode()==1000 || baseBean.getCode()==1009 || baseBean.getCode() == 1014 ||
                    baseBean.getCode() == 1013 || baseBean.getCode() == 1012 || baseBean.getCode() == 1016 || baseBean.getCode() == 1015 || baseBean.getCode() == 1021){ //1014是图书激活码列表 二维码付费1013   二维码需要登陆1012
                setData(json);
            }else if(baseBean.getCode()==1555 || baseBean.getCode()==1554 || baseBean.getCode()==1553){ //账号在其他地方登陆
                /*Intent intent = new Intent();
                intent.setClassName(getMvpView().context(),"com.me.fanyin.zbme.views.user.LoginNewActivity");
                getMvpView().context().startActivity(intent);
                SharedPrefHelper.getInstance().setIsOtherLogin(true);*/
                AppManager.getAppManager().finishAllActivity();
                Intent intent = new Intent();
                intent.setClassName(getMvpView().context(),"com.me.fanyin.zbme.views.main.MainActivity");
                getMvpView().context().startActivity(intent);
                SharedPrefHelper.getInstance().setIsLogin(false);
                SharedPrefHelper.getInstance().setUserId("");
                Toast.makeText(getMvpView().context(),baseBean.getMsg()+"", Toast.LENGTH_SHORT).show();
            }else{
                resetRequestStatus();
                getMvpView().showError(baseBean.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Exception e) {
        resetRequestStatus();
        try {
            mMvpView.showError("服务器错误");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void resetRequestStatus(){

    }
    public abstract void getData();
    public abstract void setData(String obj);

}
