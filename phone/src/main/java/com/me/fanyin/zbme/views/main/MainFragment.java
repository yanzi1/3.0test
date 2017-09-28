package com.me.fanyin.zbme.views.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.event.LogoutSuccessEvent;
import com.me.data.model.main.MainADBean;
import com.me.data.model.main.MainTypeDetailBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.base.CommonWebViewBuyActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.views.main.activity.CaptureActivity;
import com.me.fanyin.zbme.views.main.activity.MainTypeChangeActivity;
import com.me.fanyin.zbme.views.main.adapter.BasicViewPagerAdapter;
import com.me.fanyin.zbme.views.main.event.ChangeTypeEvent;
import com.me.fanyin.zbme.views.main.fragemnt.MainTypeFragment;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.me.fanyin.zbme.views.MainActivity.TAG_MAIN;

/**
 * 首页fragment
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseMvpFragment<MainView, MainPersenter> implements MainView {
	@BindView(R.id.status_bar_fix)
	View status_bar_fix;
	@BindView(R.id.main_fragment_vp)
	ViewPager main_fragment_vp;
	@BindView(R.id.main_fragment_indicator)
	SmartTabLayout main_fragment_indicator;
	@BindView(R.id.main_fragment_content_ll)
	LinearLayout main_fragment_content_ll;
	@BindView(R.id.main_fragment_add_tv)
	TextView main_fragment_add_tv;
	@BindView(R.id.main_fragemnt_type_ll)
	LinearLayout main_fragemnt_type_ll;

	private BasicViewPagerAdapter mAdapter;
	public static final int MESSAGE_CODE = 1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_CODE:
					mPresenter.getADData();
					break;
			}
		}
	};
	private boolean showADStatus = true;

	@OnClick(R.id.main_fragment_add_tv)
	void changTypePosition() {
		changeTypeClick();
	}

	public static MainFragment newInstance() {
		MainFragment fragment = new MainFragment();
		return fragment;
	}

	@Override
	public void initView() {
		EventBus.getDefault().register(this);
		// setTranslucentStatus();
		main_fragment_vp.setOffscreenPageLimit(0);
		mToolbar.setLogo(R.mipmap.icon_logo);
		mToolbar.inflateMenu(R.menu.main_menu);
		ImageView main_menu_kefu_iv = (ImageView) mToolbar.getMenu().findItem(R.id.menu_main_kefu).getActionView().findViewById(R.id.main_menu_kefu_iv);
		main_menu_kefu_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MobclickAgent.onEvent(mActivity, Statistics.PROFILE_ONLINECUSTOMERSERVICE);
			}
		});
		ImageView main_menu_saoyisao_iv = (ImageView) mToolbar.getMenu().findItem(R.id.menu_main_saoyisao).getActionView().findViewById(R.id.main_menu_saoyisao_iv);
		main_menu_saoyisao_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoActivity(CaptureActivity.class);
				MobclickAgent.onEvent(mActivity, Statistics.SCANNER_LAUNCH);
			}
		});
		initClick();
		mPresenter.checkUpdate(mHandler, MESSAGE_CODE);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void initClick() {
		main_fragment_indicator.setPageAnimClose(true);
		main_fragment_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				mPresenter.onPageSelected(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void initData() {
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
	public void showError(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void initAdapter() {
		mAdapter = new BasicViewPagerAdapter(main_fragment_vp, getChildFragmentManager());
		setupAdapter(mAdapter);
		main_fragment_indicator.setViewPager(main_fragment_vp);
	}

	@Override
	public void showADDialog(final MainADBean mainADBean) {
		if (((MainActivity) getActivity()).lastTag != TAG_MAIN) return;
		if (!showADStatus) return;
		if (StringUtils.isEmpty(mainADBean.getAdImg())) return;
		DialogManager.showADDialog(mActivity, mainADBean.getAdImg(), new DialogManager.ConfirmWithCancelDialogListener() {

			@Override
			public void confirm() {
				if (StringUtils.isEmpty(mainADBean.getAdLink())) return;
				if (mainADBean.getType() == 2) {
					String url = mainADBean.getAdLink();
					String venderId = "";
					String goodsId = "";
					String[] urlSplit = url.split("&");
					for (int i = 0; i < urlSplit.length; i++) {
						if (urlSplit[i].contains("venderId=")) {
							int vP = urlSplit[i].indexOf("=");
							venderId = urlSplit[i].substring(vP + 1);
							continue;
						}
						if (urlSplit[i].contains("goodsId=")) {
							int vP = urlSplit[i].indexOf("=");
							goodsId = urlSplit[i].substring(vP + 1);
							continue;
						}
					}
					if (StringUtils.isEmpty(venderId) || StringUtils.isEmpty(goodsId)) {
						return;
					}
				} else if (mainADBean.getType() == 1) {
					CommonWebViewBuyActivity.startActivity(getActivity(), "活动详情", mainADBean.getAdLink());
				} else {
					CommonWebViewBuyActivity.startActivity(getActivity(), "活动详情", mainADBean.getAdLink());
					MobclickAgent.onEvent(getActivity(), Statistics.MAIN_ADVERTISEVIEW);
				}
			}

			@Override
			public void cancel() {

			}
		});
	}

	private void setupAdapter(BasicViewPagerAdapter adapter) {
		for (int i = 0; i < mPresenter.mainTypeBeanList.size(); i++) {
			adapter.addTab(mPresenter.mainTypeBeanList.get(i).getTittle(), MainTypeFragment.class, getBundle(i, mPresenter.mainTypeBeanList.get(i).getId()));
		}
	}

	@NonNull
	public Bundle getBundle(int position, String examId) {
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.POSITION, position);
		bundle.putString(Constants.EXAM_ID, examId);
		return bundle;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case Constants.MAIN_FRAGMENT_CHANGE_TYPE_REQUEST:
				Bundle bundle = data.getExtras();
				changeFragment(bundle);
				break;
			default:
				break;
		}
	}

	private void changeFragment(Bundle bundle) {
		boolean isChanged = bundle.getBoolean("isChanged", false);
		if (isChanged) {
			String types = bundle.getString("typeList");
			String clickiExamId = bundle.getString("clickiExamId");
			List<SimpleTitleTip> list = JSON.parseArray(types, SimpleTitleTip.class);
			List<MainTypeDetailBean> mainTypeBeanList = new ArrayList<>();
			changTipToMain(list, mainTypeBeanList);
			MainTypeDetailBean oldTypeBean = mPresenter.mainTypeBeanList.get(main_fragment_vp.getCurrentItem());
			mPresenter.mainTypeBeanList.clear();
			mPresenter.mainTypeBeanList.addAll(mainTypeBeanList);
			mPresenter.saveDb();
			mPresenter.addFirst();
//            addNewTabs(mPresenter.mainTypeBeanList);
//                mAdapter.notifyDataSetChanged();
			initAdapter();
			main_fragment_indicator.populateNewTabs();
			if (StringUtils.isEmpty(clickiExamId)) {
				int newPosition = getNewPosition(oldTypeBean);
				main_fragment_vp.setCurrentItem(newPosition);
			} else {
				main_fragment_vp.setCurrentItem(Integer.valueOf(clickiExamId) + 1);
			}
		} else {
			String clickiExamId = bundle.getString("clickiExamId");
			if (StringUtils.isEmpty(clickiExamId)) return;
			main_fragment_vp.setCurrentItem(Integer.valueOf(clickiExamId) + 1);
		}

	}

	private int getNewPosition(MainTypeDetailBean oldTypeBean) {
		int position = 0;
		for (int i = 0; i < mPresenter.mainTypeBeanList.size(); i++) {
			if (mPresenter.mainTypeBeanList.get(i).getExamId().equals(oldTypeBean.getExamId())) {
				position = i;
				break;
			}

		}
		return position;
	}

	private void addNewTabs(List<MainTypeDetailBean> mainTypeBeanList) {
		List<BasicViewPagerAdapter.PagerTab> pagerTabs = new ArrayList<>();
		for (int i = 0; i < mainTypeBeanList.size(); i++) {
			BasicViewPagerAdapter.PagerTab pagerTab = new BasicViewPagerAdapter.PagerTab(mPresenter.mainTypeBeanList.get(i).getTittle(), MainTypeFragment.class, getBundle(i, mPresenter.mainTypeBeanList.get(i).getId()));
			pagerTabs.add(pagerTab);
		}
		mAdapter.addNewTabs(pagerTabs);
	}

	private void changTipToMain(List<SimpleTitleTip> list, List<MainTypeDetailBean> mainTypeBeanList) {
		for (int i = 0; i < list.size(); i++) {
			MainTypeDetailBean mainTypeBean = new MainTypeDetailBean(list.get(i).getId() + "", list.get(i).getTip());
			mainTypeBean.setExamCode(list.get(i).getExamCode());
			mainTypeBean.setExamImg(list.get(i).getExamImg());
			mainTypeBeanList.add(mainTypeBean);
		}
	}

	public void changeTypeClick() {
		if (mPresenter.mainTypeBeanList.size() == 0) {
			showError(getActivity().getResources().getString(R.string.main_nodata_advice));
			return;
		}
		MobclickAgent.onEvent(getActivity(), Statistics.HOMEPAGE_EDITEXAM);
		Intent intent = new Intent(getActivity(), MainTypeChangeActivity.class);
		Bundle bundle = new Bundle();
		List<SimpleTitleTip> simpleTitleTipList = new ArrayList<>();
		mPresenter.changeData(mPresenter.mainTypeBeanList, simpleTitleTipList);
		bundle.putString("mainTypeList", JSON.toJSONString(simpleTitleTipList));
		intent.putExtras(bundle);
		startActivityForResult(intent, Constants.MAIN_FRAGMENT_CHANGE_TYPE_REQUEST);
	}

	@Subscribe
	public void onEventMainThread(ChangeTypeEvent event) {
	}

	@Subscribe
	public void onEventMainThread(LoginSuccessEvent event) {
		initAdapter();
	}

	@Subscribe
	public void onEventMainThread(LogoutSuccessEvent event) {
		initAdapter();
		DownloadManager.getInstance().pauseAll();
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.main_fragment;
	}

	@Override
	protected int getContentResId() {
		return R.layout.main_fragment_content;
	}

	@Override
	public void showViewStatus(int status) {
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
	protected StatusLayoutManager.OnRetryListener addRetryListener() {
		return new StatusLayoutManager.OnRetryListener() {
			@Override
			public void onRetry(View v) {
				mPresenter.getData();
			}
		};
	}
}
