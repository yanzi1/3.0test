package com.me.fanyin.zbme.views.main.fragemnt;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.main.MainDetailBean;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeAdapter;
import com.me.fanyin.zbme.widget.loadmoreview.AppViewLoadMoreLine;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页内容fragment
 */
public class MainTypeFragment extends BaseMvpFragment<MainTypeView, MainTypePersenter> implements MainTypeView, MainTypeAdapter.MainTypeItemClick, BaseQuickAdapter.RequestLoadMoreListener {
	@BindView(R.id.main_item_srl)
	SwipeRefreshLayout main_item_srl;
	@BindView(R.id.main_item_rv)
	RecyclerView main_item_rv;
	private MainTypeAdapter mainTypeAdapter;
	private int mPosition;
	private String examId;
	/**
	 * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
	 */
	private boolean hasCreateView;

	/**
	 * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
	 */
	private boolean isFragmentVisible;

	/**
	 * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
	 */
	protected View rootView;

	/**
	 * 是否已经进行过数据的加载
	 */
	protected boolean isLoadData = false;

	/**
	 * 当前是否可见
	 */
	protected boolean isVisibleNow = false;
	private boolean isVisibleToUser;
	private String positionId;
	private int test;

	public static MainTypeFragment newInstance(Bundle args) {
		MainTypeFragment fragment = new MainTypeFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt(Constants.POSITION);
		examId = getArguments().getString(Constants.EXAM_ID);
		positionId = SharedPrefHelper.getInstance().getMainExamId();
		initVariable();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		init();
		super.onViewCreated(view, savedInstanceState);
		if (rootView == null)
			rootView = mStatusLayoutManager.getRootLayout();
		if (!hasCreateView && getUserVisibleHint()) {
			onFragmentVisibleChange(true);
			isFragmentVisible = true;
		}
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.main_item_fragment;
	}

	@Override
	public void initView() {
		main_item_rv.setLayoutManager(new LinearLayoutManager(main_item_rv.getContext(), LinearLayoutManager.VERTICAL, false));
		main_item_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mPresenter.getNetData();
			}
		});
	}

	@Override
	public void initData() {
//		showLoading();
//		mPresenter.getData();
	}

	private void getInterData() {
		showLoading();
		mPresenter.getData();
	}


	@Override
	protected void initInject() {
		getAppComponent().inject(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
		}
	}

	@Override
	public void showLoading() {
		main_item_srl.setRefreshing(true);
	}

	@Override
	public void hideLoading() {
		main_item_srl.setRefreshing(false);
	}

	@Override
	public void showError(String message) {

	}

	@Override
	public void initAdapter() {
		DayTestData();
		threadAdapter();
	}

	private void DayTestData() {
		List<MainDetailItemBean> mainTypeDetailBeanList=new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			MainDetailItemBean mainDetailBean = new MainDetailItemBean();
			mainDetailBean.setId("test"+i);
			mainDetailBean.setImage("test"+i);
			mainDetailBean.setTitle("test"+i);
			mainDetailBean.setLink("test"+i);
			mainDetailBean.setAuthor("test"+i);
			mainDetailBean.setDes("test"+i);
			mainDetailBean.setPublishDate("test"+i);
			mainDetailBean.setSourceText("test"+i);
			mainDetailBean.setTab("test"+i);
			mainDetailBean.setTargetText("test"+i);
			mainDetailBean.setMouldCode(MainTypeAdapter.TypeEntity.TYPE_2);
			mainTypeDetailBeanList.add(mainDetailBean);
		}
		for (int i = 0; i < test; i++) {
			MainDetailItemBean mainDetailBean = new MainDetailItemBean();
			mainDetailBean.setId("test");
			mainDetailBean.setImage("test");
			mainDetailBean.setTitle("test");
			mainDetailBean.setLink("test");
			mainDetailBean.setAuthor("test");
			mainDetailBean.setDes("test");
			mainDetailBean.setPublishDate("test");
			mainDetailBean.setSourceText("test");
			mainDetailBean.setTab("test");
			mainDetailBean.setTargetText("test");
			mainDetailBean.setMouldCode(MainTypeAdapter.TypeEntity.TYPE_2);
			mPresenter.mainTypeDetailList.get(2).getList().add(0, mainDetailBean);

			MainDetailBean mainDetailBean1=new MainDetailBean();
			mainDetailBean1.setList(mainTypeDetailBeanList );
			mainDetailBean1.setMouldCode(mPresenter.mainTypeDetailList.get(2).getMouldCode());
			mainDetailBean1.setForumName(mPresenter.mainTypeDetailList.get(2).getForumName()+":"+i);
			mainDetailBean1.setForumId(mPresenter.mainTypeDetailList.get(2).getForumId()+i);
			mainDetailBean1.setShowMore(mPresenter.mainTypeDetailList.get(2).getShowMore());
			mPresenter.mainTypeDetailList.add(2,mainDetailBean1);
		}
		test++;
	}

	private void threadAdapter() {
		isLoadData = true;
		Observable<MainTypeAdapter> observable = Observable.create(new ObservableOnSubscribe<MainTypeAdapter>() {
			@Override
			public void subscribe(ObservableEmitter<MainTypeAdapter> e) {
				if (mainTypeAdapter == null) {
					mainTypeAdapter = new MainTypeAdapter(getActivity(), mPresenter.getTestTypeData(), mPresenter.mainTypeDetailList);
					mainTypeAdapter.setFragment(MainTypeFragment.this);
					mainTypeAdapter.setLoadMoreView(new AppViewLoadMoreLine());
					mainTypeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

						@Override
						public void onLoadMoreRequested() {
							mainTypeAdapter.loadMoreEnd();
						}
					}, main_item_rv);
					mainTypeAdapter.setClickListener(new MainTypeAdapter.MainTypeItemClick() {
						@Override
						public void itemClick(int mainPosition, int itemType, int type, int position) {
							mPresenter.itemClick(mainPosition, itemType, type, position);
						}
					});
				}
				e.onNext(mainTypeAdapter);
				e.onComplete();
			}
		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		observable.doFinally(new Action() {
			@Override
			public void run() throws Exception {
			}
		})
				.subscribe(new Consumer<MainTypeAdapter>() {
					@Override
					public void accept(@io.reactivex.annotations.NonNull final MainTypeAdapter mainTypeAdapter) throws Exception {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
						if (main_item_rv != null) {
							if (!isCancel) {
								if (main_item_rv.getAdapter() == null) {
									main_item_rv.setAdapter(mainTypeAdapter);
								} else {
//									mainTypeAdapter.notifyData();
									mainTypeAdapter.notifyDataSetChanged();
								}
							}
						}
						if (main_item_srl != null) main_item_srl.setRefreshing(false);
							}
						}, 500);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
					}
				});
	}

	@Override
	public void showViewStatus(int status) {
		main_item_srl.setRefreshing(false);
		switch (status) {
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
	public String getExamId() {
		return examId;
	}

	@Override
	public void itemClick(int mainPosition, int itemType, int type, int position) {
		mPresenter.itemClick(mainPosition, itemType, type, position);
	}

	@Override
	public void onDestroy() {
		mPresenter.detachView();
		super.onDestroy();
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
	public void onLoadMoreRequested() {
		mainTypeAdapter.loadMoreEnd();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		this.isVisibleToUser = isVisibleToUser;
		if (rootView == null) return;
		hasCreateView = true;
		if (isVisibleToUser) {
			onFragmentVisibleChange(true);
			isFragmentVisible = true;
			return;
		}
		if (isFragmentVisible) {
			onFragmentVisibleChange(false);
			isFragmentVisible = false;
		}
	}

	protected void onFragmentVisibleChange(boolean isVisible) {
		isVisibleNow = isVisible;
		if (isVisible) {
			isCancel = false;
			if ((!isLoadData) && rootView != null) {
//				initData();
				getInterData();
				isLoadData = false;
			}
		} else {
			isCancel = true;
		}
	}

	private boolean isCancel = false;

	private void initVariable() {
		hasCreateView = false;
		isFragmentVisible = false;
	}
}
