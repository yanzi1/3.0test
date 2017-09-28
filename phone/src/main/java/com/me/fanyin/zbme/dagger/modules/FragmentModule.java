package com.me.fanyin.zbme.dagger.modules;

import com.me.fanyin.zbme.dagger.annotation.PerFragment;
import com.me.fanyin.zbme.base.BaseMvpFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xunice on 18/03/2017.
 */

@Module
public class FragmentModule {
    private BaseMvpFragment mFragment;

    public FragmentModule(BaseMvpFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    BaseMvpFragment provideFragment() {
        return mFragment;
    }
}
