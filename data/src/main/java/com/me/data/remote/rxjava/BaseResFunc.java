package com.me.data.remote.rxjava;


import com.me.core.exception.ConnectErrorException;
import com.me.data.model.BaseRes;

import io.reactivex.functions.Function;

/**
 * 请求之前处理异常,例如一些网络连接的异常
 * Created by xunice on 21/03/2017.
 */

public class BaseResFunc<T> implements Function<BaseRes<T>, T> {

    /**
     * @param baseRes
     * @return
     * @throws Exception
     */
    @Override
    public T apply(BaseRes<T> baseRes) throws Exception {
        if (baseRes == null) {
            throw new ConnectErrorException(ConnectErrorException.TIME_OUT_ERROR);
        }

        return baseRes.getObj();
    }
}
