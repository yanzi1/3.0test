package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by dell on 2017/7/18.
 */

public class KeyBoardShowListener {
    private Context ctx;

    public KeyBoardShowListener(Context ctx) {
        this.ctx = ctx;
    }

    OnKeyboardVisibilityListener keyboardListener;

    public OnKeyboardVisibilityListener getKeyboardListener() {
        return keyboardListener;
    }

    public interface OnKeyboardVisibilityListener {
        void onVisibilityChanged(boolean visible);
    }

    public void setKeyboardListener(final OnKeyboardVisibilityListener listener, Activity activity) {
        final View activityRootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean wasOpened;
            private final int DefaultKeyboardDP = 100;
            private final int EstimatedKeyboardDP = DefaultKeyboardDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, activityRootView.getResources().getDisplayMetrics());
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;
                if (isShown == wasOpened) {
//                    Log.e("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                wasOpened = isShown;
                listener.onVisibilityChanged(isShown);
            }
        });
    }
}
