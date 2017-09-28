package com.me.fanyin.zbme.views.main;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.me.core.exception.ApiException;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.main.MainADBean;
import com.me.data.model.main.MainTypeBean;
import com.me.data.model.main.MainTypeDetailBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiInterface;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.main.activity.db.MainTypeDB;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;
import com.me.updatelib.UpdateManager;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by wyc on 15/06/2017.
 */

public class MainPersenter extends BasePersenter<MainView> {
	public List<MainTypeDetailBean> mainTypeBeanList;
	private String examId;
	private MainTypeDB mainTypeDB;
	private String userId;
	private List<MainTypeDetailBean> dbList;
	private MainTypeDetailBean jingxuan;

	@Inject
	MainPersenter() {}

	public void checkUpdate(Handler handler, int messageCode) {
		UpdateManager updateManager = new UpdateManager.Builder(getMvpView().context())
				.putMessageTransferHandler(handler, messageCode)
				.isHintNewVersion(false)
				.canMobileNetworkDownload(true)
				.checkUrl(ApiInterface.CHECK_UPDATE_URL + ParamsUtils.commonSignPramasString())
				.build();
		updateManager.check();
	}

	@Override
	public void getData() {
		userId = SharedPrefHelper.getInstance().getUserId();
		if (mainTypeBeanList == null) mainTypeBeanList = new ArrayList<>();
		if(jingxuan == null) jingxuan=new MainTypeDetailBean("999","精选");
		getDBData();
		getNetData();
	}

	private void getDBData() {
		if (mainTypeDB == null) mainTypeDB = new MainTypeDB();
		dbList = mainTypeDB.findAll();
		if (dbList == null) dbList = new ArrayList<>();
		mainTypeBeanList.clear();
		mainTypeBeanList.addAll(dbList);
		addFirst();
		getMvpView().initAdapter();
	}

	public void getADData() {
		Disposable subscribe = ApiService.getMainAD(ParamsUtils.getMainADData())
				.doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
					}
				})
				.compose(RxUtils.<MainADBean>retrofit()) //几种方式实现逻辑前置
				// .map(new BaseResFunc<UserBean>()) //几种方式实现逻辑前置
				//.compose(RxUtils.rxStateCheck()) //几种方式实现逻辑前置
				.doFinally(new Action() { //最后都要执行的操作
					@Override
					public void run() throws Exception {
//						getMvpView().hideLoading();
					}
				})
				.subscribe(new Consumer<MainADBean>() {
							   @Override
							   public void accept(@NonNull MainADBean mainADBean) throws Exception {
								   if (!mainADBean.getStatus().equals(3)){
									   getMvpView().showADDialog(mainADBean);
								   }
							   }
						   }
						, new Consumer<Throwable>() {
							@Override
							public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
							}
						}
				);
		addSubscription(subscribe);
	}
	public void getNetData() {
		Disposable subscribe = ApiService.getMainTypeInfo(ParamsUtils.getMainTypeData(Constants.MAIN_TYPE_1 + ""))
				.doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
						getMvpView().showLoading();
					}
				})
				.compose(RxUtils.<MainTypeBean>retrofit()) //几种方式实现逻辑前置
				.doFinally(new Action() { //最后都要执行的操作
					@Override
					public void run() throws Exception {
//						getMvpView().hideLoading();
					}
				})
				.subscribe(new Consumer<MainTypeBean>() {
							   @Override
							   public void accept(@NonNull MainTypeBean mainTypeBean) throws Exception {
								   mainTypeBeanList.clear();
								   mainTypeBeanList.addAll(mainTypeBean.getList());
								   if (mainTypeBeanList.size() == 0) {
									   getMvpView().showViewStatus(Constants.VIEW_STATUS_EMPTY);
									   return;
								   }
								   judgePosition();
								   saveDb();
								   addFirst();
								   getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
								   getMvpView().initAdapter();
							   }
						   }
						, new Consumer<Throwable>() {
							@Override
							public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
								if (mainTypeBeanList.size()==1){
									mainTypeBeanList.clear();
									getMvpView().initAdapter();
									if (throwable instanceof ApiException){
										getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_OTHER);
									}else{
										getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_NET);
									}
								}else{
									getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
								}
							}
						}
				);
		addSubscription(subscribe);
	}

	public void addFirst() {
		mainTypeBeanList.add(0,jingxuan);
	}


	private void judgePosition() {
		List<MainTypeDetailBean> realList = new ArrayList<>();
		a:for (int i = 0; i < dbList.size(); i++) {
			b:for (int j = 0; j < mainTypeBeanList.size(); j++) {
				if (mainTypeBeanList.get(j).getExamId().equals(dbList.get(i).getExamId())) {
					realList.add(mainTypeBeanList.get(j));
					break b;
				}
			}
		}
		for (int i = 0; i < mainTypeBeanList.size(); i++) {
			if (!realList.contains(mainTypeBeanList.get(i))){
				realList.add(mainTypeBeanList.get(i));
			}
		}
		if (realList.size() > 0) {
			mainTypeBeanList.clear();
			mainTypeBeanList.addAll(realList);
		}
	}


	public void changeData(List<MainTypeDetailBean> mainTypeBeanList, List<SimpleTitleTip> simpleTitleTipList) {
		for (int i = 0; i < mainTypeBeanList.size(); i++) {
			if (i==0) continue;
			SimpleTitleTip simpleTitleTip = new SimpleTitleTip();
			simpleTitleTip.setId(Long.valueOf(mainTypeBeanList.get(i).getId()));
			simpleTitleTip.setTip(mainTypeBeanList.get(i).getTittle());
			simpleTitleTip.setExamCode(mainTypeBeanList.get(i).getExamCode());
			simpleTitleTip.setExamImg(mainTypeBeanList.get(i).getExamImg());
			simpleTitleTipList.add(simpleTitleTip);
		}
	}

	public void saveDb() {
		userId = SharedPrefHelper.getInstance().getUserId();
		mainTypeDB.deleteAll();
		for (int i = 0; i < mainTypeBeanList.size(); i++) {
			mainTypeBeanList.get(i).setUserId(userId);
			mainTypeDB.insert(mainTypeBeanList.get(i));
		}
	}

	public void onPageSelected(int position) {
		if (mainTypeBeanList.size()<=position)return;
		SharedPrefHelper.getInstance().setMainExamId(mainTypeBeanList.get(position).getExamId());
	}
}
