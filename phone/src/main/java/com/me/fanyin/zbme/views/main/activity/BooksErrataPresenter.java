package com.me.fanyin.zbme.views.main.activity;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.data.common.Constants;
import com.me.data.model.BaseRes;
import com.me.data.model.main.BooksErrataListBean;
import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.data.model.main.MainDetailItemBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.activity.adapter.BooksErrataAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/6/12.
 */

public class BooksErrataPresenter extends BasePersenter<BooksErrataView> {

    private BooksErrataAdapter mAdapter;
    private List<MainDetailItemBean> mData = new ArrayList<>();
    private int currentPage = 1;

    @Inject
    BooksErrataPresenter() {

    }

    public BaseQuickAdapter initAdapter(RecyclerView recyclerView) {
        mAdapter = new BooksErrataAdapter(mData);
        mAdapter.bindToRecyclerView(recyclerView);
        return mAdapter;
    }

    @Override
    public void getData() {
        Disposable subscribe = ApiService.getBooksErrataMenuList(getMvpView().getExamId())
                .compose(RxUtils.<BaseRes<BooksErrataMenuListBean>>ioMain())
                .compose(RxUtils.<BaseRes<BooksErrataMenuListBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<BooksErrataMenuListBean>retrofit())
                .compose(RxUtils.<BooksErrataMenuListBean>hideDialogLoading(getMvpView()))
                .subscribeWith(new SimpleRxSubscriber<BooksErrataMenuListBean>(getMvpView()) {
                    @Override
                    public void doOnNext(BooksErrataMenuListBean bean) {
                        if (bean != null && bean.getSubjectList().size() != 0) {
                            for (int i = 0; i < bean.getSubjectList().size(); i++) {
                                for (int j = 0; j < bean.getSubjectList().get(i).getSeasonList().size(); j++) {
                                    List<BooksErrataMenuListBean.SubjectListBean.SeasonListBean.BookListBean> bookList = bean.getSubjectList().get(i).getSeasonList().get(j).getBookList();
                                    if (bookList == null || bookList.size() == 0) {
                                        BooksErrataMenuListBean.SubjectListBean.SeasonListBean.BookListBean bookListBean = new BooksErrataMenuListBean.SubjectListBean.SeasonListBean.BookListBean();
                                        bookListBean.setName("图书");
                                        bookList.add(bookListBean);
                                    }
                                }
                            }
                            getMvpView().initMenu(bean.getSubjectList());
                        }
                    }

                    @Override
                    public void doOnComplete() {

                    }
                });
        addSubscription(subscribe);
    }

    public void getErrataList() {
        Disposable subscribe = ApiService.getBooksErrataList(getMvpView().getBookId(), currentPage)
                .compose(RxUtils.<BaseRes<BooksErrataListBean>>ioMain())
                .compose(RxUtils.<BaseRes<BooksErrataListBean>>showLoading(getMvpView()))
                .compose(RxUtils.<BooksErrataListBean>retrofit())
                .subscribeWith(getResultSubscriber(Constants.DATA_FROM_LOADING));
        addSubscription(subscribe);
    }

    public void onLoadMore() {
        Disposable loadMoreSubscriber = getRefreshOrLoadMoreSubscriber(Constants.DATA_FROM_LOADMORE);
        addSubscription(loadMoreSubscriber);
    }

    public void onRefresh() {
        currentPage = 1;
        Disposable refreshSubscriber = getRefreshOrLoadMoreSubscriber(Constants.DATA_FROM_REFRESH);
        addSubscription(refreshSubscriber);
    }

    @NonNull
    private SimpleRxSubscriber<BooksErrataListBean> getRefreshOrLoadMoreSubscriber(int dataFrom) {
        return ApiService.getBooksErrataList(getMvpView().getBookId(), dataFrom == Constants.DATA_FROM_LOADMORE ? currentPage + 1 : currentPage)
                .compose(RxUtils.<BaseRes<BooksErrataListBean>>ioMain())
                .compose(RxUtils.<BooksErrataListBean>retrofit())
                .subscribeWith(getResultSubscriber(dataFrom));
    }

    private SimpleRxSubscriber<BooksErrataListBean> getResultSubscriber(final int dataFrom) {
        return new SimpleRxSubscriber<BooksErrataListBean>(getMvpView()) {
            @Override
            public void doOnNext(BooksErrataListBean bean) {
                if (bean == null || bean.getCount().equals("0") || bean.getList().size() == 0) {
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
                        mData.clear();
                        mData.addAll(bean.getList());
                        mAdapter.notifyDataSetChanged();
                        getMvpView().hideLoading();
                        break;
                    case Constants.DATA_FROM_REFRESH:
                        mData.clear();
                        mData.addAll(bean.getList());
                        mAdapter.notifyDataSetChanged();
                        break;
                    case Constants.DATA_FROM_LOADMORE:
                        if (Math.ceil(Double.parseDouble(bean.getCount()) / 10)  < currentPage + 1) {
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                        } else if (Math.ceil(Double.parseDouble(bean.getCount()) / 10)  == currentPage + 1 || bean.getList().size() < 10) {
                            mData.addAll(bean.getList());
                            mAdapter.notifyDataSetChanged();
                            currentPage += 1;
                            getMvpView().onLoadMoreComplete(LoadMoreView.STATUS_END);
                        } else {
                            mData.addAll(bean.getList());
                            mAdapter.notifyDataSetChanged();
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
        };
    }

}
