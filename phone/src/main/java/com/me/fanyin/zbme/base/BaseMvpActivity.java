package com.me.fanyin.zbme.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.me.data.mvp.BasePersenter;
import com.me.data.mvp.MvpView;
import com.me.fanyin.zbme.dagger.component.ActivityComponent;
import com.me.fanyin.zbme.dagger.component.AppComponent;
import com.me.fanyin.zbme.dagger.component.DaggerActivityComponent;
import com.me.fanyin.zbme.dagger.modules.ActivityModule;
import com.me.fanyin.zbme.widget.LoadingDialog;
import javax.inject.Inject;

public abstract class BaseMvpActivity<V extends MvpView, T extends BasePersenter<V>> extends BaseActivity implements View.OnClickListener ,MvpView {
	@Inject
	protected T mPresenter;

	protected LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstnceState) {
		initInject();
		init();
		super.onCreate(savedInstnceState);
	}

	protected abstract void initInject();

	// 初始化
	private void init() {
		mPresenter.attachView((V) this);
		//加载框
		loadingDialog = new LoadingDialog(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

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
		mPresenter.detachView();
		super.onDestroy();
	}

	protected ActivityComponent getAppComponent() {
		return DaggerActivityComponent.builder().activityModule(getModule()).appComponent(AppComponent
				.getInstance()).build();
	}

	protected ActivityModule getModule() {
		return new ActivityModule(this);
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
		if(loadingDialog != null) loadingDialog.show();
	}

	@Override
	public void hideDialogLoading() {
		if (loadingDialog != null && loadingDialog.isShowing())
		loadingDialog.dismiss();
	}

	@Override
	public Activity context() {
		return this;
	}
}
