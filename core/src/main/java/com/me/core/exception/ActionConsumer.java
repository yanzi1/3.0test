package com.me.core.exception;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by jjr on 2017/5/27.
 */

public class ActionConsumer<T> implements Consumer<T> {
    private final Action action;
    private Consumer<T> consumer;

    ActionConsumer(Action action) {
        this.action = action;
    }

    ActionConsumer(Consumer<T> consumer, Action action) {
        this.consumer = consumer;
        this.action = action;
    }

    @Override
    public void accept(T t) throws Exception {
        consumer.accept(t);
        action.run();
    }
}
