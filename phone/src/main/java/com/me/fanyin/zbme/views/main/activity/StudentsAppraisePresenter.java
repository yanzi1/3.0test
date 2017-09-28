package com.me.fanyin.zbme.views.main.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.data.common.Constants;
import com.me.data.model.BaseRes;
import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.activity.adapter.StudentsAppraiseAdapter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/5/11.
 */

public class StudentsAppraisePresenter extends BasePersenter<StudentsAppraiseView> {

    private StudentsAppraiseAdapter mAdapter;
    private int currentPage = 1;
    private int pageSize =10;

    @Inject
    StudentsAppraisePresenter() {

    }

    public StudentsAppraiseAdapter initAdapter(RecyclerView recyclerView) {
        if (mAdapter == null) {
            mAdapter = new StudentsAppraiseAdapter();
            recyclerView.setAdapter(mAdapter);
        }
        return mAdapter;
    }

    @Override
    public void getData() {
        currentPage = 1;
        Disposable subscribe = ApiService.getStudentsEvaluateInfo(getMvpView().getLectureId(), currentPage, pageSize)
                .compose(RxUtils.<BaseRes<StudentsEvaluateInfo>>ioMain())
                .compose(RxUtils.<BaseRes<StudentsEvaluateInfo>>showLoading(getMvpView()))
                .compose(RxUtils.<StudentsEvaluateInfo>retrofit())
                .subscribeWith(getResultSubscriber(Constants.DATA_FROM_LOADING));
        addSubscription(subscribe);
    }

    public void onRefresh() {
        currentPage = 1;
        Disposable subscribe = getRefreshOrLoadMoreSubscriber(Constants.DATA_FROM_REFRESH);
        addSubscription(subscribe);
    }

    public void onLoadMore() {
        Disposable subscribe = getRefreshOrLoadMoreSubscriber(Constants.DATA_FROM_LOADMORE);
        addSubscription(subscribe);
    }

    @NonNull
    private SimpleRxSubscriber<StudentsEvaluateInfo> getRefreshOrLoadMoreSubscriber(int dataFrom) {
        return ApiService.getStudentsEvaluateInfo(getMvpView().getLectureId(), dataFrom == Constants.DATA_FROM_LOADMORE ? currentPage + 1 : currentPage, pageSize)
                .compose(RxUtils.<BaseRes<StudentsEvaluateInfo>>ioMain())
                .compose(RxUtils.<StudentsEvaluateInfo>retrofit())
                .subscribeWith(getResultSubscriber(dataFrom));
    }

    /**
     * 统一的结果处理
     *
     * @return
     */
    private SimpleRxSubscriber<StudentsEvaluateInfo> getResultSubscriber(final int dataFrom) {
        return new SimpleRxSubscriber<StudentsEvaluateInfo>(getMvpView()) {
            @Override
            public void doOnNext(StudentsEvaluateInfo bean) {
                if (bean == null || bean.getList().size() == 0) {
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
                switch (dataFrom) {
                    case Constants.DATA_FROM_LOADING:
                        mAdapter.setNewData(bean.getList());
                        getMvpView().setAppraiseScore(Float.parseFloat(bean.getBeautifulScore()), Float.parseFloat(bean.getAttractScore()), Float.parseFloat(bean.getReasonableScore()));
                        getMvpView().hideLoading();
                        break;
                    case Constants.DATA_FROM_REFRESH:
                        getMvpView().setAppraiseScore(Float.parseFloat(bean.getBeautifulScore()), Float.parseFloat(bean.getAttractScore()), Float.parseFloat(bean.getReasonableScore()));
                        mAdapter.setNewData(bean.getList());
                        break;
                    case Constants.DATA_FROM_LOADMORE:
                        getMvpView().setAppraiseScore(Float.parseFloat(bean.getBeautifulScore()), Float.parseFloat(bean.getAttractScore()), Float.parseFloat(bean.getReasonableScore()));
                        if (Math.ceil(Double.parseDouble(bean.getAllCount()) / 10)  < currentPage + 1) {
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                        } else if (Math.ceil(Double.parseDouble(bean.getAllCount()) / 10)  == currentPage + 1 || bean.getList().size() < 10) {
                            mAdapter.addData(bean.getList());
                            currentPage += 1;
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                        } else {
                            mAdapter.addData(bean.getList());
                            currentPage += 1;
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_DEFAULT);
                        }
                        break;
                }
            }

            @Override
            public void onApiError(int code, String msg) {
                if (dataFrom == Constants.DATA_FROM_LOADMORE) {
                    getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_FAIL);
                } else if (dataFrom == Constants.DATA_FROM_REFRESH){
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

            @Override
            public boolean showErrorToast() {
                return true;
            }
        };
    }
}
