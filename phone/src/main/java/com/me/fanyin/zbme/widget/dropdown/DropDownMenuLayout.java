package com.me.fanyin.zbme.widget.dropdown;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.me.core.utils.DensityUtils;
import com.me.fanyin.zbme.R;

/**
 * Created by mayunfei on 17-4-27.
 */

public class DropDownMenuLayout extends FrameLayout {
    public interface OnOpenCloseListener {
        void onCloseMenu(int position);

        void onOpenMenu(int position);
    }

    private OnOpenCloseListener onOpenCloseListener;
    private final FrameLayout maskView;
    private final FrameLayout popupMenuViews;

    public void setOnOpenCloseListener(OnOpenCloseListener onOpenCloseListener) {
        this.onOpenCloseListener = onOpenCloseListener;
    }

    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    public DropDownMenuLayout(@NonNull Context context) {
        this(context, null);
    }

    public DropDownMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maskView = new FrameLayout(context);
        maskView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        popupMenuViews = new FrameLayout(context);
        popupMenuViews.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getHeightInPx(context) * 0.5)));
//        popupMenuViews.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setBackgroundColor(0x33000000);
        addView(maskView);
        addView(popupMenuViews);
        setVisibility(GONE);
    }

    public void closeMenu() {
        if (current_tab_position != -1) {
            if (onOpenCloseListener != null) {
                onOpenCloseListener.onCloseMenu(current_tab_position);
            }
            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mall_spinner_out));
            maskView.setVisibility(View.GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mall_spinner_mask_out));
            current_tab_position = -1;
            this.setVisibility(GONE);
            setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mall_spinner_mask_out));
        }
    }


    /**
     * 切换菜单
     */
    public void switchMenu(int position) {

        for (int i = 0; i < popupMenuViews.getChildCount(); i++) {

            if (i == position) {
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        setVisibility(VISIBLE);
                        popupMenuViews.setVisibility(VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mall_spinner_in));
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mall_spinner_mask_in));
                        popupMenuViews.getChildAt(i).setVisibility(VISIBLE);
                    } else {
                        popupMenuViews.getChildAt(i).setVisibility(VISIBLE);
                    }
                    current_tab_position = i;
                    if (onOpenCloseListener != null) {
                        onOpenCloseListener.onOpenMenu(i);
                    }
                }
            } else {
                if (onOpenCloseListener != null) {
                    onOpenCloseListener.onCloseMenu(i);
                }
                popupMenuViews.getChildAt(i).setVisibility(GONE);
            }

        }

    }

    /**
     * 按顺序添加 menu
     */
    public void addMenuView(View view) {
        popupMenuViews.addView(view);
    }


}
