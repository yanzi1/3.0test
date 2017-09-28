package com.me.fanyin.zbme.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jjr on 2017/3/30.
 */

public abstract class BaseViewPagerFragment extends BaseFragment {

    @BindView(R.id.base_tbl)
    SmartTabLayout base_tbl;
    @BindView(R.id.base_vp)
    ViewPager base_vp;
    private BasicViewPagerAdapter mAdapter;

    @Override
    public void initView() {

        mAdapter = new BasicViewPagerAdapter(base_vp, getChildFragmentManager());

        setupAdapter(mAdapter);

        base_tbl.setViewPager(base_vp);

    }

    protected abstract void setupAdapter(BasicViewPagerAdapter adapter);

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.base_viewpager_fragment;
    }

    @NonNull
    public Bundle getBundle(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        return bundle;
    }

    public static class BasicViewPagerAdapter extends FragmentStatePagerAdapter {

        List<PagerTab> tabs = new ArrayList<>();
        private final Context mContext;

        public BasicViewPagerAdapter(ViewPager mPager, FragmentManager fm) {
            super(fm);
            mContext = mPager.getContext();

            mPager.setAdapter(this);

        }

        public void addTab(String title, Class<?> clazz, Bundle bundle) {
            tabs.add(new PagerTab(title, clazz, bundle));
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            PagerTab pagerTab = tabs.get(position);
            return Fragment.instantiate(mContext, pagerTab.clazz.getName(), pagerTab.bundle);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).title;
        }

        class PagerTab {
            String title;
            Class<?> clazz;
            Bundle bundle;

            public PagerTab(String title, Class<?> clazz, Bundle bundle) {
                this.title = title;
                this.clazz = clazz;
                this.bundle = bundle;
            }
        }
    }
}
