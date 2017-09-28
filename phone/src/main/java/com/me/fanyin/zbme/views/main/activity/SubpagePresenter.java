package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.me.data.common.Constants;
import com.me.data.model.BaseRes;
import com.me.data.model.main.MainTypeDetailBean;
import com.me.data.model.main.TabListBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.views.main.activity.db.MainTypeDB;
import com.me.fanyin.zbme.views.main.adapter.BasicViewPagerAdapter;
import com.me.fanyin.zbme.views.main.fragemnt.SubpageFragment;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/5/9.
 */

class SubpagePresenter extends BasePersenter<SubpageView> {

    @Inject
    SubpagePresenter() {

    }

    @Override
    public void getData() {

    }

    public void initTabs(final BasicViewPagerAdapter adapter, final SmartTabLayout subpage_tbl, final ViewPager subpage_vp) {
        Disposable subscribe = ApiService.getTabList(getMvpView().getExamId(), getMvpView().getForumId())
                .compose(RxUtils.<BaseRes<TabListBean>>ioMain())
                .compose(RxUtils.<BaseRes<TabListBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<TabListBean>retrofit())
                .compose(RxUtils.<TabListBean>hideDialogLoading(getMvpView()))
                .subscribeWith(new SimpleRxSubscriber<TabListBean>(getMvpView()){
                    @Override
                    public void doOnNext(TabListBean tabListBean) {
                        if (getMvpView().getMouldCode() == 6 || getMvpView().getMouldCode() == 7) {
                            tabListBean = sortTabs(tabListBean);
                        }
                        getMvpView().setTabVisibility(true);
                        setupAdapter(adapter, tabListBean);
                        subpage_tbl.setPageAnimClose(true);
                        subpage_tbl.setViewPager(subpage_vp);
                        if (getMvpView().getMouldCode() == 6 || getMvpView().getMouldCode() == 7) {
                            int primaryPosition = getPrimaryPosition(tabListBean);
                            subpage_vp.setCurrentItem(primaryPosition);
                        }
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void doOnComplete() {

                    }
                });
        addSubscription(subscribe);
    }

    private TabListBean sortTabs(TabListBean tabListBean) {
        MainTypeDB db = new MainTypeDB();
        List<MainTypeDetailBean> beanList = db.findAll();
        for (int i = 0; i < beanList.size(); i++) {
            for (int j = i; j < tabListBean.getList().size(); j++) {
                if (beanList.get(i).getExamId().equals(tabListBean.getList().get(j).getTabId())) {
                    Collections.swap(tabListBean.getList(), i, j);
                    break;
                }
            }
        }
        return tabListBean;
    }

    private int getPrimaryPosition(TabListBean tabListBean) {
        for (int i = 0; i < tabListBean.getList().size(); i++) {
            if (getMvpView().getExamId().equals(tabListBean.getList().get(i).getTabId())) return i;
        }
        return 0;
    }

    private void setupAdapter(BasicViewPagerAdapter adapter, TabListBean tabs) {
        if (tabs != null && tabs.getList().size() != 0) {
            for (TabListBean.ListItemBean itemBean : tabs.getList()) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.MOULD_CODE, getMvpView().getMouldCode());
                bundle.putString(Constants.EXAM_ID, getMvpView().getExamId());
                bundle.putString(Constants.FORUM_ID, getMvpView().getForumId());
                bundle.putString(Constants.TAB_ID, itemBean.getTabId());
                bundle.putString(Constants.LINK, itemBean.getLink());
                adapter.addTab(itemBean.getTabName(), SubpageFragment.class, bundle);
            }
        }
    }
}
