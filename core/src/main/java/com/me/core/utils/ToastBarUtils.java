package com.me.core.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.me.core.app.AppContext;

/**
 * Created by xunice on 21/03/2017.
 */

public class ToastBarUtils {
    private ToastBarUtils() {
    }

    private static Toast mToast = null;

    public static void show(Activity context, String message) {
        Snackbar.make(context.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(AppContext.getInstance(), message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
