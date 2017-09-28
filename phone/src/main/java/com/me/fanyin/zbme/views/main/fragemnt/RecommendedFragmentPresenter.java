package com.me.fanyin.zbme.views.main.fragemnt;

import android.support.v7.widget.RecyclerView;

import com.me.data.model.BaseRes;
import com.me.data.model.main.RecommendedGoodListBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.RecommendedAdapter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jjr on 2017/5/11.
 */

public class RecommendedFragmentPresenter extends BasePersenter<RecommendedFragmentView> {

    private RecommendedAdapter mAdapter;

    @Inject
    RecommendedFragmentPresenter() {
    }

    @Override
    public void getData() {
        Disposable subscribe = ApiService.getRecommendedGoodsList(getMvpView().getExamId(), "", getMvpView().getGoodsType())
                .compose(RxUtils.<BaseRes<RecommendedGoodListBean>>ioMain())
                .compose(RxUtils.<RecommendedGoodListBean>retrofit())
                .subscribe(new Consumer<RecommendedGoodListBean>() {
                    @Override
                    public void accept(@NonNull RecommendedGoodListBean recommendedGoodListBean) throws Exception {
                        if (recommendedGoodListBean != null) {
                            getMvpView().resetView(recommendedGoodListBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().resetView(null);
                    }
                });
        addSubscription(subscribe);
    }


    public RecommendedAdapter initAdapter(String goodsType, RecyclerView recommended_goods_rcv) {
        mAdapter = new RecommendedAdapter(goodsType);
        mAdapter.bindToRecyclerView(recommended_goods_rcv);
        return mAdapter;
    }
}
