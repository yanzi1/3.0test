package com.me.fanyin.zbme.views.main.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.me.core.exception.ApiException;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.model.main.MainDetailBean;
import com.me.data.model.main.MainDetailItemBean;
import com.me.data.model.main.MainFragmentBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.base.CommonWebViewBuyActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.main.activity.BooksErrataActivity;
import com.me.fanyin.zbme.views.main.activity.BooksErrataDetailActivity;
import com.me.fanyin.zbme.views.main.activity.FamousTeacherActivity;
import com.me.fanyin.zbme.views.main.activity.SubpageActivity;
import com.me.fanyin.zbme.views.main.activity.SubwebActivity;
import com.me.fanyin.zbme.views.main.activity.db.MainFragmentDB;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeAdapter;
import com.umeng.analytics.MobclickAgent;
import org.reactivestreams.Subscription;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by wyc on 13/03/2017.
 */

public class MainTypePersenter extends BasePersenter<MainTypeView> {
	public List<MainDetailBean> mainTypeDetailList=new ArrayList<>();

	private String examId;
	private MainFragmentDB mainFragmentDB;
	private MainFragmentBean dbData;

	@Inject
	MainTypePersenter() {

	}

	@Override
	public void getData() {
//		mainTypeDetailList = new ArrayList<>();
		examId = getMvpView().getExamId();
		getDbData();
		getNetData();
	}

	private void getDbData() {
		if (mainFragmentDB == null) mainFragmentDB = new MainFragmentDB();
		dbData = mainFragmentDB.find(examId);
		if (dbData != null) {
			dbData = JSON.parseObject(dbData.getContent(), MainFragmentBean.class);
			mainTypeDetailList.clear();
			mainTypeDetailList.addAll(dbData.getForumList());
		}
	}

	public List<MainTypeAdapter.TypeEntity> getTestTypeData() {
		List<MainTypeAdapter.TypeEntity> typeEntities = new ArrayList<>();
		for (int i = 0; i < mainTypeDetailList.size(); i++) {
			if (typeEntities.contains(mainTypeDetailList.get(i).getMouldCode())) continue;
			typeEntities.add(new MainTypeAdapter.TypeEntity(mainTypeDetailList.get(i).getMouldCode()));
		}
		return typeEntities;
	}

	public void getNetData() {
		examId = getMvpView().getExamId();
		Disposable subscribe = ApiService.getMainTypeFragment(ParamsUtils.getMainTypeFragmentData(examId))
				.doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
						getMvpView().showLoading();
					}
				})
				.compose(RxUtils.<BaseRes<MainFragmentBean>>ioMain())
				.compose(RxUtils.<MainFragmentBean>retrofit()) //几种方式实现逻辑前置
				.doFinally(new Action() { //最后都要执行的操作
					@Override
					public void run() throws Exception {
					}
				})
				.subscribe(new Consumer<MainFragmentBean>() {
							   @Override
							   public void accept(@NonNull MainFragmentBean queryAddBook) throws Exception {
								   mainTypeDetailList.clear();
								   mainTypeDetailList.addAll(queryAddBook.getForumList());
								   if (mainTypeDetailList.size() == 0) {
									   getMvpView().showViewStatus(Constants.VIEW_STATUS_EMPTY);
									   return;
								   }
								   saveDB(queryAddBook);
								   getMvpView().initAdapter();
								   getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
							   }
						   }
						, new Consumer<Throwable>() {
							@Override
							public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
								if (mainTypeDetailList.size() == 0) {
									if (throwable instanceof ApiException) {
										getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_OTHER);
									} else {
										getMvpView().showViewStatus(Constants.VIEW_STATUS_ERROR_NET);
									}
								} else {
									getMvpView().initAdapter();
									getMvpView().showViewStatus(Constants.VIEW_STATUS_SUCCESS);
								}
							}
						}
				);
		addSubscription(subscribe);
	}

	private void saveDB(MainFragmentBean queryAddBook) {
		queryAddBook.setExamId(examId);
		queryAddBook.setContent(JSON.toJSONString(queryAddBook));
		mainFragmentDB.deleteBeanById(examId);
		mainFragmentDB.insert(queryAddBook);

	}

	public void itemClick(int mainPosition, int itemType, int type, int position) {
		clickTypeUM(itemType);
		switch (itemType) {
			case Constants.MAIN_FRAGMENT_CLICK_TYPE1:
				intentDetailWeb(mainPosition, itemType, type, position);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE2:
				switch (type) {
					case 1:
						intentSubpageAddActivity(mainPosition, itemType, type, position);
						break;
					case 0:
						intentDetailWeb(mainPosition, itemType, type, position);
						break;
				}
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE3:
				switch (type) {
					case 1:
						intentSubpageAddActivity(mainPosition, itemType, type, position);
						break;
					case 0:
						intentDetailWeb(mainPosition, itemType, type, position);
						break;
				}
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE4:
				switch (type) {
					case 1:
						intentSubpageAddActivity(mainPosition, itemType, type, position);
						break;
					case 0:
						intentFamousTeacher(mainPosition, itemType, type, position);
						break;
				}
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE5://免费试听
				switch (type) {
					case 1:
						intentSubpageAddActivity(mainPosition, itemType, type, position);
						break;
					case 0:
						intentPlay(mainPosition, itemType, type, position);
						break;
				}
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE6:
			case Constants.MAIN_FRAGMENT_CLICK_TYPE7:
				switch (type) {
					case 1:
						intentMallFragment(mainPosition, itemType, type, position);
						break;
					case 0:
						break;
				}
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE11://图书勘误
				switch (type) {
					case 1:
						intentBookErrorActivity(mainPosition, itemType, type, position);
						break;
					case 0:
						intentBookError(mainPosition, itemType, type, position);
						break;
				}
				break;
			default:
				intentDetailWeb(mainPosition, itemType, type, position);
				break;
		}
	}

	private void clickTypeUM(int itemType) {
		switch (itemType) {
			case Constants.MAIN_FRAGMENT_CLICK_TYPE1://轮播
				umType(Statistics.HOMEPAGE_FEATURED_BANNER, Statistics.HOMEPAGE_OTHERS_BANNER);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE2://3 热点资讯
				umType(Statistics.HOMEPAGE_FEATURED_INFOMATION, "");
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE3://4考试动态、备考攻略
				umType(Statistics.HOMEPAGE_FEATURED_EXAMTREND, Statistics.HOMEPAGE_OTHERS_EXAMTREND);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE4://6东奥名师
				umType(Statistics.HOMEPAGE_FEATURED_TEACHER, Statistics.HOMEPAGE_OTHERS_TEACHER);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE5://免费试听
				umType(Statistics.HOMEPAGE_FEATURED_LISTENFORFREE, Statistics.HOMEPAGE_OTHERS_LISTENFORFREE);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE6://8热门课程
				umType(Statistics.HOMEPAGE_FEATURED_HOTCOURSE, Statistics.HOMEPAGE_OTHERS_HOTCOURSE);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE7://9热门图书
				umType(Statistics.HOMEPAGE_FEATURED_HOTBOOK, Statistics.HOMEPAGE_OTHERS_HOTBOOK);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE8://10 考试倒计时
				umType("", Statistics.HOMEPAGE_OTHERS_EXAMCOUNTDOWN);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE9://5 广告
				umType(Statistics.HOMEPAGE_FEATURED_PERMANENTAD, Statistics.HOMEPAGE_OTHERS_PERMANENTAD1);
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE10://2 滚动消息
				umType(Statistics.HOMEPAGE_FEATURED_HEADLINE, "");
				break;
			case Constants.MAIN_FRAGMENT_CLICK_TYPE11://图书勘误
				umType("", Statistics.HOMEPAGE_OTHERS_BOOKCORRENT);
				break;
			default:
				break;
		}
	}

	private void umType(String jingxuan, String normal) {
		if (examId.equals("999")) {
			if (!StringUtils.isEmpty(normal))
				MobclickAgent.onEvent(getMvpView().context(), jingxuan);
		} else {
			if (!StringUtils.isEmpty(normal)) MobclickAgent.onEvent(getMvpView().context(), normal);
		}
	}

	private void intentBookError(int mainPosition, int itemType, int type, int position) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.FORUM_NAME, mainTypeDetailList.get(mainPosition).getList().get(position));
		gotoActivity(BooksErrataDetailActivity.class, false, bundle);
	}

	private void intentDetailWeb(int mainPosition, int itemType, int type, int position) {
		if (position < 0) position = 0;
		if (mainTypeDetailList.get(mainPosition).getList().size() <= position) return;
		if (StringUtils.isEmpty(mainTypeDetailList.get(mainPosition).getList().get(position).getLink()))
			return;
		switch (itemType) {
			case Constants.MAIN_FRAGMENT_CLICK_TYPE1:
			case Constants.MAIN_FRAGMENT_CLICK_TYPE9:
			case Constants.MAIN_FRAGMENT_CLICK_TYPE10:
				if (mainTypeDetailList.get(mainPosition).getList().get(position).getLink().contains("hj_xj_type=1")) {
					String url = mainTypeDetailList.get(mainPosition).getList().get(position).getLink();
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
					return;
				} else {
					CommonWebViewBuyActivity.startActivity(getMvpView().context(), "活动详情", mainTypeDetailList.get(mainPosition).getList().get(position).getLink());
					return;
				}
			default:
				Bundle bundle = new Bundle();
				bundle.putSerializable(Constants.FORUM_NAME, mainTypeDetailList.get(mainPosition).getList().get(position));
				bundle.putInt(Constants.MOULD_CODE, mainTypeDetailList.get(mainPosition).getMouldCode());
				gotoActivity(SubwebActivity.class, false, bundle);
				break;
		}
	}

	private void intentPlay(int mainPosition, int itemType, int type, int position) {
		MainDetailItemBean mainDetailItemBean = mainTypeDetailList.get(mainPosition).getList().get(position);
		Bundle bundle = new Bundle();
		bundle.putBoolean(Constants.IS_FREE_PLAY, true);
		bundle.putString(Constants.TITLE, mainDetailItemBean.getTitle());
		bundle.putString(Constants.LINK, mainDetailItemBean.getLink());
		if (!mainDetailItemBean.getId().contains("_")) {
			bundle.putString(Constants.EXAM_ID, mainDetailItemBean.getId());
		} else {
			bundle.putString(Constants.EXAM_ID, mainDetailItemBean.getId().substring(0, mainDetailItemBean.getId().indexOf("_")));
			bundle.putString(Constants.LECTURE_ID, mainDetailItemBean.getId().substring(mainDetailItemBean.getId().indexOf("_") + 1));
		}
		gotoActivity(PlayActivity.class, false, bundle);
	}

	private void intentMallFragment(int mainPosition, int itemType, int type, int position) {
		int mallType;
		if (itemType == Constants.MAIN_FRAGMENT_CLICK_TYPE6) {
			mallType = Constants.MALL_CATEGORY_TYPE_VIDEO;
		} else {
			mallType = Constants.MALL_CATEGORY_TYPE_BOOK;
		}
		String examId = SharedPrefHelper.getInstance().getMainExamId();
		MainActivity.startMallFragment(getMvpView().context(), examId, mallType);

	}

	private void intentFamousTeacher(int mainPosition, int itemType, int type, int position) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.EXAM_ID, examId);
		bundle.putSerializable(Constants.FORUM_NAME, mainTypeDetailList.get(mainPosition).getList().get(position));
		gotoActivity(FamousTeacherActivity.class, false, bundle);
	}

	private void intentBookErrorActivity(int mainPosition, int itemType, int type, int position) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.EXAM_ID, examId);
		bundle.putString(Constants.FORUM_ID, mainTypeDetailList.get(mainPosition).getForumId() + "");
		bundle.putString(Constants.MOULD_CODE, mainTypeDetailList.get(mainPosition).getMouldCode() + "");
		bundle.putString(Constants.FORUM_NAME, mainTypeDetailList.get(mainPosition).getForumName() + "");
		gotoActivity(BooksErrataActivity.class, false, bundle);
	}

	private void intentSubpageAddActivity(int mainPosition, int itemType, int type, int position) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.EXAM_ID, examId);
		bundle.putString(Constants.FORUM_ID, mainTypeDetailList.get(mainPosition).getForumId() + "");
		bundle.putString(Constants.MOULD_CODE, mainTypeDetailList.get(mainPosition).getMouldCode() + "");
		bundle.putString(Constants.FORUM_NAME, mainTypeDetailList.get(mainPosition).getForumName() + "");
		gotoActivity(SubpageActivity.class, false, bundle);
	}

	private void gotoActivity(Class<?> queryAddActivityClass, boolean b, Bundle bundle) {
		Intent intent = new Intent(getMvpView().context(), queryAddActivityClass);
		intent.putExtras(bundle);
		getMvpView().context().startActivity(intent);
	}

	private void gotoActivity(Class<?> queryAddActivityClass) {
		Intent intent = new Intent(getMvpView().context(), queryAddActivityClass);
		getMvpView().context().startActivity(intent);
	}
}
