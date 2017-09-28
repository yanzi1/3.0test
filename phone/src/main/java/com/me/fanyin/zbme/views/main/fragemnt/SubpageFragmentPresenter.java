package com.me.fanyin.zbme.views.main.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.data.model.main.SubpageListBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.main.activity.FamousTeacherActivity;
import com.me.fanyin.zbme.views.main.activity.SubwebActivity;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeAdapter;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter2;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter3;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter4;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter5;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter6;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter7;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by jjr on 2017/3/30.
 */

public class SubpageFragmentPresenter extends BasePersenter<SubpageFragmentView> {

    private List<MainDetailItemBean> mData = new ArrayList<>();
    private BaseQuickAdapter mAdapter;
    private String fristLink;
    private String nextLink;

    @Inject
    SubpageFragmentPresenter() {
    }

    public BaseQuickAdapter getAdatper(RecyclerView base_rcv) {
        mAdapter = null;
        switch (getMvpView().getMouldCode()) {
            case 3:
                MainTypeDetailAdapter2 adapter2 = new MainTypeDetailAdapter2(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.FORUM_NAME, mainDetailItemBean);
                        bundle.putInt(Constants.MOULD_CODE, getMvpView().getMouldCode());
                        gotoActivity(SubwebActivity.class, bundle);
                    }
                });
                adapter2.bindToRecyclerView(base_rcv);
                mAdapter = adapter2;
                break;
            case 4:
                MainTypeDetailAdapter3 adapter3 = new MainTypeDetailAdapter3(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.FORUM_NAME, mainDetailItemBean);
                        bundle.putInt(Constants.MOULD_CODE, getMvpView().getMouldCode());
                        gotoActivity(SubwebActivity.class, bundle);
                    }
                });
                adapter3.bindToRecyclerView(base_rcv);
                mAdapter = adapter3;
                break;
            case 6:
                MainTypeDetailAdapter4 adapter4 = new MainTypeDetailAdapter4(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, getMvpView().getParams().get(Constants.EXAM_ID));
                        bundle.putSerializable(Constants.FORUM_NAME, mainDetailItemBean);
                        gotoActivity(FamousTeacherActivity.class, bundle);
                    }
                });
                adapter4.bindToRecyclerView(base_rcv);
                mAdapter = adapter4;
                break;
            case 7:
                MainTypeDetailAdapter5 adapter5 = new MainTypeDetailAdapter5(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Constants.IS_FREE_PLAY, true);
                        bundle.putString(Constants.LINK, mainDetailItemBean.getLink());
                        if (mainDetailItemBean.getId().contains("_")) {
                            bundle.putString(Constants.EXAM_ID, mainDetailItemBean.getId().substring(0, mainDetailItemBean.getId().indexOf("_")));
                            bundle.putString(Constants.LECTURE_ID, mainDetailItemBean.getId().substring(mainDetailItemBean.getId().indexOf("_") + 1));
                        } else {
                            bundle.putString(Constants.EXAM_ID, mainDetailItemBean.getId());
                            bundle.putString(Constants.LECTURE_ID, "");
                        }
                        bundle.putString(Constants.TITLE, mainDetailItemBean.getTitle());
                        gotoActivity(PlayActivity.class, bundle);
                    }
                });
                adapter5.bindToRecyclerView(base_rcv);
                mAdapter = adapter5;
                break;
            case 8:
                MainTypeDetailAdapter6 adapter6 = new MainTypeDetailAdapter6(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.FORUM_NAME, mainDetailItemBean);
                        bundle.putInt(Constants.MOULD_CODE, getMvpView().getMouldCode());
                        gotoActivity(SubwebActivity.class, bundle);
                    }
                });
                adapter6.bindToRecyclerView(base_rcv);
                mAdapter = adapter6;
                break;
            case 9:
                MainTypeDetailAdapter7 adapter7 = new MainTypeDetailAdapter7(base_rcv.getContext(), mData, new MainTypeAdapter.MainTypeItemClick() {
                    @Override
                    public void itemClick(int mainPosition, int itemType, int type, int position) {
                        MainDetailItemBean mainDetailItemBean = mData.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.FORUM_NAME, mainDetailItemBean);
                        bundle.putInt(Constants.MOULD_CODE, getMvpView().getMouldCode());
                        gotoActivity(SubwebActivity.class, bundle);
                    }
                });
                adapter7.bindToRecyclerView(base_rcv);
                mAdapter = adapter7;
                break;
        }
        return mAdapter;
    }

    @Override
    public void getData() {
        fristLink = getMvpView().getParams().get(Constants.LINK).contains("http://") ? getMvpView().getParams().get(Constants.LINK) : "http://" + getMvpView().getParams().get(Constants.LINK);
        Disposable subscribe = getSubscriber(fristLink, Constants.DATA_FROM_LOADING);
        addSubscription(subscribe);
    }

    public void onRefresh() {
        Disposable subscribe = getSubscriber(fristLink, Constants.DATA_FROM_REFRESH);
        addSubscription(subscribe);
    }

    public void onLoadMore() {
        if (!TextUtils.isEmpty(nextLink)) {
            Disposable subscribe = getSubscriber(nextLink, Constants.DATA_FROM_LOADMORE);
            addSubscription(subscribe);
        } else {
            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
        }
    }


    @NonNull
    private SimpleRxSubscriber<SubpageListBean> getSubscriber(final String link, int dataFrom) {
        return Flowable.just(dataFrom)
                .flatMap(new Function<Integer, Publisher<SubpageListBean>>() {
                    @Override
                    public Publisher<SubpageListBean> apply(@NonNull Integer integer) throws Exception {
                        if (integer == Constants.DATA_FROM_LOADING) {
                            return ApiService.getSubpageList(link)
                                    .compose(RxUtils.<SubpageListBean>ioMain())
                                    .compose(RxUtils.<SubpageListBean>showLoading(getMvpView()));
                        } else {
                            return ApiService.getSubpageList(link)
                                    .compose(RxUtils.<SubpageListBean>ioMain());

                        }
                    }
                })
                .subscribeWith(getResultSubscriber(dataFrom));
    }

    /**
     * 统一的结果处理(答疑)
     *
     * @return
     */
    private SimpleRxSubscriber<SubpageListBean> getResultSubscriber(final int dataFrom) {
        return new SimpleRxSubscriber<SubpageListBean>(getMvpView()) {
            @Override
            public void doOnNext(SubpageListBean subpageListBean) {
                if (subpageListBean == null || subpageListBean.getList().size() == 0) {
                    switch (dataFrom) {
                        case Constants.DATA_FROM_LOADING:
                        case Constants.DATA_FROM_REFRESH:
                            onEmpty();
                            return;
                        case Constants.DATA_FROM_LOADMORE:
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                            return;
                    }
                }
                if (!TextUtils.isEmpty(subpageListBean.getNextPageUrl())) {
                    nextLink = subpageListBean.getNextPageUrl().contains("http://") ? subpageListBean.getNextPageUrl() : "http://" + subpageListBean.getNextPageUrl();
                } else {
                    nextLink = "";
                }
                switch (dataFrom) {
                    case Constants.DATA_FROM_LOADING:
                        mData.clear();
                        mData.addAll(subpageListBean.getList());
                        mAdapter.notifyDataSetChanged();
                        getMvpView().hideLoading();
                        break;
                    case Constants.DATA_FROM_REFRESH:
                        mData.clear();
                        mData.addAll(subpageListBean.getList());
                        mAdapter.notifyDataSetChanged();
                        break;
                    case Constants.DATA_FROM_LOADMORE:
                        mData.addAll(subpageListBean.getList());
                        mAdapter.notifyDataSetChanged();
                        if (subpageListBean.getPageNo() == subpageListBean.getTotalPage() || subpageListBean.getList().size() < 10 || TextUtils.isEmpty(subpageListBean.getNextPageUrl())) {
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                        } else {
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_DEFAULT);
                        }
                        break;
                }
            }

            @Override
            public void onApiError(int code, String msg) {
                if (dataFrom == Constants.DATA_FROM_LOADMORE) {
                    getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_FAIL);
                } else if (dataFrom == Constants.DATA_FROM_REFRESH) {
                    getMvpView().onRefreshComplete();
                } else {
                    super.onApiError(code, msg);
                }
            }

            @Override
            public void onNetWorkError() {
                if (dataFrom == Constants.DATA_FROM_LOADMORE) {
                    getMvpView().showError(getMvpView().context().getString(R.string.app_nonetwork_message));
                } else {
                    super.onNetWorkError();
                }
            }

            @Override
            public void doOnComplete() {
                if (dataFrom == Constants.DATA_FROM_REFRESH) {
                    getMvpView().onRefreshComplete();
                }
            }

        };
    }

    private void gotoActivity(Class clz, Bundle bundle) {
        Intent intent = new Intent(getMvpView().context(), clz);
        intent.putExtras(bundle);
        getMvpView().context().startActivity(intent);
    }

}

