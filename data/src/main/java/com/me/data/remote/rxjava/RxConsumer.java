package com.me.data.remote.rxjava;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 重新定义下Comsumer，做一些操作
 * Created by xunice on 21/03/2017.
 */

public class RxConsumer<T> implements Consumer<T> {

    @Override
    public void accept(@NonNull T t) throws Exception {

    }
}
