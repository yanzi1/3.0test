package com.me.fanyin.zbme.views.exam.activity.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.me.fanyin.zbme.base.PhoneAppContext;

import me.drakeet.materialdialog.MaterialDialog;


public abstract class BaseFragment extends Fragment implements View.OnClickListener{


    protected PhoneAppContext appContext;
    protected ProgressDialog progress;


    protected MaterialDialog materialDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initProgress();
    }

    /**
     * 登陆成功后的操作
     */
    protected void loginSuccessed(){

    }

    private void initProgress(){
        progress=new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("正在加载…………");

    }

    // 初始化
    private void init() {
        // 添加Activity到堆栈
       // AppManager.getAppManager().addActivity(getActivity());
        appContext = (PhoneAppContext) PhoneAppContext.getInstance();
    }


    public abstract void initView();

    public abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 结束Activity&从堆栈中移除
//        AppManager.getAppManager().finishActivity(getActivity());
        super.onDestroy();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }



}
