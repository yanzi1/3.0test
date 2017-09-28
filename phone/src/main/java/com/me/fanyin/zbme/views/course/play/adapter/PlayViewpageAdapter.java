package com.me.fanyin.zbme.views.course.play.adapter;


import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.me.data.model.play.PagerItem;

public class PlayViewpageAdapter extends FragmentPagerAdapter {

    ArrayList<PagerItem> list;

    public PlayViewpageAdapter(FragmentManager fm, ArrayList<PagerItem> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}


