package com.me.data.remote.rxjava;

import java.util.Collection;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * Created by xunice on 12/03/2017.
 */

public class RxFilters {

    /**
     * Just for the Collections.
     *
     * @param <T> The input.
     * @return true if the input collection is not empty.
     * @throws ClassCastException If the input is not an instance of Collection.
     */
    public static <T> Predicate<T> notEmpty() {
        return new Predicate<T>() {
            @Override public boolean test(@NonNull T t) throws Exception {
                return !((Collection) t).isEmpty();
            }
        };
    }


    public static <T> Predicate<T> notNull() {
        return new Predicate<T>() {
            @Override public boolean test(@NonNull T t) {
                return t != null;
            }
        };
    }


    public static <T> Predicate<?> event(final Class<T> eventClass) {
        return new Predicate<Object>() {
            @Override public boolean test(@NonNull Object o) {
                return o.getClass().equals(eventClass);
            }
        };
    }
}
