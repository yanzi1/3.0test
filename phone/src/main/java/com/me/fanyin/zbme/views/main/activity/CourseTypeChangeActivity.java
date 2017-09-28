package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.ItemDragAdapter;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import butterknife.BindView;

/**
 * 也加上Mvp模式，方式后续加入其它的功能
 */
public class CourseTypeChangeActivity extends BaseMvpActivity<CourseTypeChangeView, CourseTypeChangePersenter> implements CourseTypeChangeView {

	@BindView(R.id.maintype_change_rv)
	RecyclerView maintype_change_rv;
	@BindView(R.id.maintype_change_hide_rv)
	RecyclerView maintype_change_hide_rv;
	@BindView(R.id.toolbar)
	CommonToolbar toolbar;
	private boolean isMove = false;
	
	private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
	private ItemDragAdapter mAdapter;


	@Override
	public void initView() {
		maintype_change_hide_rv.setVisibility(View.GONE);
		maintype_change_hide_rv.setNestedScrollingEnabled(false);
		toolbar.setTitleText("全部类目");
		initConfig();
	}


	private void initConfig() {
		maintype_change_rv.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));
	}

	@Override
	public void initData() {
		mPresenter.getData();
	}


	@Override
	public int getLayoutRes() {
		return R.layout.main_type_layout;
	}

	@Override
	protected int getContentResId() {
		return R.layout.main_type_layout_content;
	}

	@Override
	protected void initInject() {
		getAppComponent().inject(CourseTypeChangeActivity.this);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {

	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}

	@Override
	public void showError(String message) {

	}

	@Override
	public void initAdapter() {
		if (mAdapter!=null){mAdapter.notifyDataSetChanged(); return;}
		
		mAdapter = new ItemDragAdapter(mPresenter.typeList);
		mAdapter.setContext(this);
		mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
		mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
		mAdapter.disableSwipeItem();
		mAdapter.disableDragItem();
		maintype_change_rv.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				mPresenter.onItemClick(view,position);
			}
		});
	}

	@Override
	public void showViewStatus(int status) {
        switch (status){
            case Constants.VIEW_STATUS_INIT:
            	showLoading();
                break;
            case Constants.VIEW_STATUS_SUCCESS:
            	hideLoading();
                break;
            case Constants.VIEW_STATUS_ERROR_NET:
            	showNetworkError();
                break;
            case Constants.VIEW_STATUS_EMPTY:
            	showEmptyData();
            	break;
            case Constants.VIEW_STATUS_ERROR_OTHER:
            	showErrorData();
                break;
        }
	}

	@Override
	protected StatusLayoutManager.OnRetryListener addRetryListener() {
		return new StatusLayoutManager.OnRetryListener() {
			@Override
			public void onRetry(View v) {
				mPresenter.getData();
			}
		};
	}

	@Override
	public void finishActivity() {
		onBackPressed();
	}

	@Override
	public Intent getTheIntent() {
		return getIntent();
	}
}
