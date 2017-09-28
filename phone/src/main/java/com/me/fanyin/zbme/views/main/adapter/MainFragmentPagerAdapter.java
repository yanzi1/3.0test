package com.me.fanyin.zbme.views.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.me.fanyin.zbme.views.main.fragemnt.MainTypeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyc
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    List<BasicViewPagerAdapter.PagerTab> tabs = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private final Context mContext;

    public MainFragmentPagerAdapter(ViewPager mPager, FragmentManager fm) {
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
        BasicViewPagerAdapter.PagerTab pagerTab=tabs.get(position);
        MainTypeFragment fragment=null;
        fragment=new MainTypeFragment().newInstance(pagerTab.bundle);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        registeredFragments.remove(position);
    }

    @Override
    public int getCount() {
        return tabs==null?0:tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).title;
    }


    /**
     * 获得缓存的fragment
     */
    public Fragment getFragment(int position) {
        return registeredFragments.get(position);
    }
}
