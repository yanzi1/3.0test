package com.me.fanyin.zbme.views.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjr on 2017/4/5.
 */

public class BasicViewPagerAdapter extends FragmentStatePagerAdapter {

    List<PagerTab> tabs = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private final Context mContext;

    public BasicViewPagerAdapter(ViewPager mPager, FragmentManager fm) {
        super(fm);
        mContext = mPager.getContext();

        mPager.setAdapter(this);

    }

    public void addTab(String title, Class<?> clazz, Bundle bundle) {
        tabs.add(new BasicViewPagerAdapter.PagerTab(title, clazz, bundle));
        notifyDataSetChanged();
    }

    public void addNewTabs(List<BasicViewPagerAdapter.PagerTab> pagerTabs) {
        tabs.clear();
        tabs.addAll(pagerTabs);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        BasicViewPagerAdapter.PagerTab pagerTab = tabs.get(position);
        Fragment fragment = Fragment.instantiate(mContext, pagerTab.clazz.getName(), pagerTab.bundle);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        registeredFragments.remove(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).title;
    }

    public static class PagerTab {
        String title;
        Class<?> clazz;
        Bundle bundle;

        public PagerTab(String title, Class<?> clazz, Bundle bundle) {
            this.title = title;
            this.clazz = clazz;
            this.bundle = bundle;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    /**
     * 获得缓存的fragment
     */
    public Fragment getFragment(int position) {
        return registeredFragments.get(position);
    }

    public SparseArray<Fragment> getFragments(){
        return registeredFragments;
    }
}
