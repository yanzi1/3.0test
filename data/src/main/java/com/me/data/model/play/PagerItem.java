package com.me.data.model.play;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by dell on 2017/5/25.
 */

public class PagerItem implements Serializable {

    private String name;
    private Fragment fragment;

    public PagerItem(String name,Fragment fragment){
        this.name=name;
        this.fragment=fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
