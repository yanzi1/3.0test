package com.me.core.exception;

import android.content.Context;

import com.me.core.utils.StringUtils;
import com.orhanobut.logger.Logger;

import java.net.UnknownHostException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Created by xunice on 12/03/2017.
 */

public class ErrorHandlers {

    public static void displayError(Context context, String message) {
        if (context == null || StringUtils.isEmpty(message)) {
            return;
        }

       // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void displayError(Context context, Throwable throwable) {
        if (context == null) {
            Logger.e("[300] " + "Context is null");
            return;
        }

        String errorMessage = null;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            errorMessage = httpException.response().errorBody().charStream().toString();
        } else if (throwable instanceof UnknownHostException) {
            errorMessage = "网络连接不可用，请稍后重试";
        }
        if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            errorMessage = apiException.getMsg();
        }
        if (errorMessage != null) {
            displayError(context, errorMessage);
        } else {
            Logger.e("[301] %s", throwable);
            displayError(context, throwable.getMessage());
        }
    }

    public static Consumer<Throwable> displayErrorConsumer(final Context context) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) {
                displayError(context, throwable);
            }
        };
    }

    public static Consumer<Throwable> displayErrorActionConsumer(final Context context, final Action action) {
        return new ActionConsumer<Throwable>(displayErrorConsumer(context), action);
    }
}
