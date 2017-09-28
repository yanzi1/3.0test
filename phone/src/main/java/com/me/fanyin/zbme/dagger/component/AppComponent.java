package com.me.fanyin.zbme.dagger.component;

import com.me.core.app.AppContext;
import com.me.fanyin.zbme.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xunice on 09/03/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class})
public abstract class AppComponent {
    private static AppComponent mComponent;

    public static AppComponent getInstance() {
        if (mComponent == null) {
            synchronized (AppComponent.class) {
                if (mComponent == null) {
                    mComponent = DaggerAppComponent.builder().appModule(new AppModule(AppContext.getInstance
                            ())).build();
                }
            }
        }

        return mComponent;
    }

}