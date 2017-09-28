package com.me.data.remote.rxjava;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by xunice on 12/03/2017.
 */

public class RxBus {

    private final PublishSubject<Object> bus = PublishSubject.create();


    public static RxBus singleton() { return LazyLoad.BUS; }


    public boolean hasObservers() {
        return bus.hasObservers();
    }


    public void post(final Object o) {
        bus.onNext(o);
    }


    public Observable<Object> toObservable() {
        return bus;
    }


    private static class LazyLoad {
        static final RxBus BUS = new RxBus();
    }
}
