package com.me.fanyin.zbme.dagger.modules;

import android.support.v7.app.AppCompatActivity;

import com.me.fanyin.zbme.dagger.annotation.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xunice on 10/03/2017.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        mActivity = appCompatActivity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return mActivity;
    }
}
