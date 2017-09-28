package com.me.fanyin.zbme.views.main.activity;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.me.core.exception.ApiException;
import com.me.data.common.Constants;
import com.me.data.event.ExamChangeEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.model.main.MainTypeBean;
import com.me.data.model.main.MainTypeDetailBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.main.activity.db.MainTypeNormalDB;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by wyc on 13/03/2017.
 */

public class CourseTypeChangePersenter extends BasePersenter<CourseTypeChangeView> {

	private String examId;
	private List<MainTypeDetailBean> mainTypeBeanList = new ArrayList<>();
	private List<MainTypeDetailBean> dbList;
	private MainTypeNormalDB mainTypeNormalDB;
	public List<SimpleTitleTip> typeList = new ArrayList<>();
	boolean isIntel;
	@Inject
	CourseTypeChangePersenter() {

	}

	@Override
	public void attachView(CourseTypeChangeView mvpView) {
		super.attachView(mvpView);
	}

	@Override
	public void detachView() {
		super.detachView();
	}

	@Override
	public void getData() {
		isIntel=SharedPrefHelper.getInstance().isIntel();
		if (mainTypeNormalDB == null) mainTypeNormalDB = new MainTypeNormalDB();
		getTestData();
		getNetData();
	}

	private void getTestData() {
		if (!isIntel){
			dbList = mainTypeNormalDB.findAll();
			if (dbList == null) dbList = new ArrayList<>();
			mainTypeBeanList.addAll(dbList);
			changeData();
			getMvpView().initAdapter();
		}
	}

	public void getNetData() {
		Flowable<BaseRes<MainTypeBean>> queryFlowable;
		if (isIntel){
			queryFlowable= ApiService.getIntelMainTypeInfo(ParamsUtils.getIntelTypeData());
		}else{
			queryFlowable= ApiService.getMainTypeInfo(ParamsUtils.getMainTypeData(Constants.MAIN_TYPE_2 + ""));
		}
		Disposable subscribe =queryFlowable.doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
						getMvpView().showLoading();
					}

				})
				.onBackpressureLatest()
				.compose(RxUtils.<MainTypeBean>retrofit()) //几种方式实现逻辑前置
				.map(new Function<MainTypeBean, MainTypeBean>() { //附加操作
					@Override
					public MainTypeBean apply(MainTypeBean mainTypeBean) throws Exception {
						return mainTypeBean;
					}
				})
				.doFinally(new Action() { //最后都要执行的操作
					@Override
					public void run() throws Exception {
//                        getMvpView().hideLoading();
					}
				})
				.subscribe(new Consumer<MainTypeBean>() {
							   @Override
							   public void accept(MainTypeBean mainTypeBean) throws Exception {
								   try {
									   mainTypeBeanList.clear();
									   mainTypeBeanList.addAll(mainTypeBean.getList());
									   if (mainTypeBeanList.size() == 0) {
										   getMvpView().showViewStatus(Constants.VIEW_STATUS_EMPTY);
										   return;
									   }
									   mainTypeBean.setContent(JSON.toJSONString(mainTypeBean.getList()));
									   saveNormalDB(mainTypeBean);
									   changeData();
									   getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
									   getMvpView().initAdapter();
								   } catch (Exception e) {
								   }
							   }
						   }, new Consumer<Throwable>() {
							   @Override
							   public void accept(@NonNull Throwable throwable) throws Exception {
								   if (mainTypeBeanList.size() > 0) {
									   getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
								   } else {
									   if (throwable instanceof ApiException) {
										   getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_OTHER);
									   } else {
										   getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_NET);
									   }
								   }
							   }
						   }
				);
		addSubscription(subscribe);
	}


	private void saveNormalDB(MainTypeBean mainTypeBean) {
		if (!isIntel){
			mainTypeNormalDB.deleteAll();
			mainTypeNormalDB.insert(mainTypeBean);
		}
	}

	public void changeData() {
		typeList.clear();
		for (int i = 0; i < mainTypeBeanList.size(); i++) {
			SimpleTitleTip simpleTitleTip = new SimpleTitleTip();
			simpleTitleTip.setId(Long.valueOf(mainTypeBeanList.get(i).getId()));
			simpleTitleTip.setTip(mainTypeBeanList.get(i).getTittle());
			simpleTitleTip.setExamCode(mainTypeBeanList.get(i).getExamCode());
			simpleTitleTip.setExamImg(mainTypeBeanList.get(i).getExamImg());
			typeList.add(simpleTitleTip);
		}
	}

	public void onItemClick(View view, int position) {
		if (isIntel){
			SharedPrefHelper.getInstance().setIntelExamId(mainTypeBeanList.get(position).getExamId());
			SharedPrefHelper.getInstance().setIntelExamName(mainTypeBeanList.get(position).getExamName());
			EventBus.getDefault().post(new ExamChangeEvent());
			getMvpView().finishActivity();
		}else{
			SharedPrefHelper.getInstance().setExamId(mainTypeBeanList.get(position).getExamId());
			SharedPrefHelper.getInstance().setExamName(mainTypeBeanList.get(position).getExamName());
			EventBus.getDefault().post(new ExamChangeEvent());
			getMvpView().finishActivity();
		}

	}
}
