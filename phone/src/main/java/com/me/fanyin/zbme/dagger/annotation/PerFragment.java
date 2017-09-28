package com.me.fanyin.zbme.dagger.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xunice on 18/03/2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}

