package com.me.fanyin.zbme.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.me.core.app.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xunice on 09/03/2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    AppContext provideContext() {
        return (AppContext) this.application;
    }

    @Provides
    @Singleton
    public Resources provideResources(Context context) {
        return context.getResources();
    }


}
