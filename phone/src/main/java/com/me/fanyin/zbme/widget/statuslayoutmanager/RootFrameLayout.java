package com.me.fanyin.zbme.widget.statuslayoutmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.me.fanyin.zbme.R;

/**
 * Created by jjr on 2017/5/22.
 */

public class RootFrameLayout extends FrameLayout {

    /**
     * loading 加载id
     */
    public static final int LAYOUT_LOADING_ID = 1;

    /**
     * 内容id
     */
    public static final int LAYOUT_CONTENT_ID = 2;

    /**
     * 异常id
     */
    public static final int LAYOUT_ERROR_ID = 3;

    /**
     * 网络异常id
     */
    public static final int LAYOUT_NETWORK_ERROR_ID = 4;

    /**
     * 空数据id
     */
    public static final int LAYOUT_EMPTYDATA_ID = 5;

    /**
     * 无权限id
     */
    public static final int LAYOUT_NO_PERMISSION_ID = 6;

    /**
     * 自定义布局id
     */
    public static final int LAYOUT_CUSTOM_ID = 7;

    /**
     * 存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray();

    /**
     * 布局管理器
     */
    private StatusLayoutManager mStatusLayoutManager;

    /**
     * 正在显示的布局的id
     * {@link #LAYOUT_LOADING_ID,#LAYOUT_CONTENT_ID,#LAYOUT_ERROR_ID,#LAYOUT_NETWORK_ERROR_ID,#LAYOUT_EMPTYDATA_ID,#LAYOUT_NO_PERMISSION_ID,#LAYOUT_CUSTOM_ID}
     */
    private int showingLayoutId;
    private AnimationDrawable loadingAnimation;


    public RootFrameLayout(Context context) {
        super(context);
    }

    public RootFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager) {
        mStatusLayoutManager = statusLayoutManager;

        addAllLayoutToLayout();
    }

    public void addAllLayoutToLayout() {
        if (mStatusLayoutManager.contentLayoutResId != 0) {
            addLayoutResId(mStatusLayoutManager.contentLayoutResId, RootFrameLayout.LAYOUT_CONTENT_ID);
        } else if (mStatusLayoutManager.contentView != null) {
            layoutSparseArray.put(RootFrameLayout.LAYOUT_CONTENT_ID, mStatusLayoutManager.contentView);
            addView(mStatusLayoutManager.contentView);
        }
        if (mStatusLayoutManager.loadingLayoutResId != 0)
            addLayoutResId(mStatusLayoutManager.loadingLayoutResId, RootFrameLayout.LAYOUT_LOADING_ID);

        if (mStatusLayoutManager.emptyDataVs != null) addView(mStatusLayoutManager.emptyDataVs);
        if (mStatusLayoutManager.errorVs != null) addView(mStatusLayoutManager.errorVs);
        if (mStatusLayoutManager.netWorkErrorVs != null) addView(mStatusLayoutManager.netWorkErrorVs);
        if (mStatusLayoutManager.noPermissionVs != null) addView(mStatusLayoutManager.noPermissionVs);

        setLoadingAnim();
    }

    /**
     * 设置 loading 动画
     */
    private void setLoadingAnim() {
        View loadingLayout = layoutSparseArray.get(LAYOUT_LOADING_ID);
        ImageView imageView_loading = (ImageView) loadingLayout.findViewById(R.id.app_loading_iv);
        if (imageView_loading != null) {
            loadingAnimation = (AnimationDrawable) imageView_loading.getDrawable();
        }
    }

    public void addLayoutResId(@LayoutRes int layoutResId, int layoutId) {
        View resView = LayoutInflater.from(mStatusLayoutManager.context).inflate(layoutResId, null);
        layoutSparseArray.put(layoutId, resView);
        addView(resView);
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        if (layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            showHideViewById(LAYOUT_LOADING_ID);
            if (loadingAnimation != null) {
                loadingAnimation.start();
            }
        }
    }

    private void stopLoadingAnim() {
        if (loadingAnimation != null && loadingAnimation.isRunning()) {
            loadingAnimation.stop();
        }
    }

    /**
     * 显示内容
     */
    public void showContent() {
        if (layoutSparseArray.get(LAYOUT_CONTENT_ID) != null) {
            showHideViewById(LAYOUT_CONTENT_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 显示空数据
     */
    public void showEmptyData() {
        if (inflateLayout(LAYOUT_EMPTYDATA_ID)) {
            showHideViewById(LAYOUT_EMPTYDATA_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 显示网络异常
     */
    public void showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID)) {
            showHideViewById(LAYOUT_NETWORK_ERROR_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 显示异常
     */
    public void showError() {
        if (inflateLayout(LAYOUT_ERROR_ID)) {
            showHideViewById(LAYOUT_ERROR_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 显示无权限
     */
    public void showNoPermission() {
        if (inflateLayout(LAYOUT_NO_PERMISSION_ID)) {
            showHideViewById(LAYOUT_NO_PERMISSION_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 显示自定义
     */
    public void showCustom() {
        View view = layoutSparseArray.get(LAYOUT_CUSTOM_ID);
        if (view != null) {
            retryLoad(view, mStatusLayoutManager.customRetryViewId != 0 ? mStatusLayoutManager.customRetryViewId : mStatusLayoutManager.retryViewId);
            showHideViewById(LAYOUT_CUSTOM_ID);
            stopLoadingAnim();
        }
    }

    /**
     * 根据ID显示隐藏布局
     *
     * @param id
     */
    private void showHideViewById(int id) {
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);
            //显示该view
            if (key == id) {
                valueView.setVisibility(View.VISIBLE);
                if (mStatusLayoutManager.onShowHideViewListener != null)
                    mStatusLayoutManager.onShowHideViewListener.onShowView(valueView, key);
                showingLayoutId = id;
            } else {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                    if (mStatusLayoutManager.onShowHideViewListener != null)
                        mStatusLayoutManager.onShowHideViewListener.onHideView(valueView, key);
                }
            }
        }
    }

    public int getShowingLayoutId() {
        return showingLayoutId;
    }

    private boolean inflateLayout(int layoutId) {
        boolean isShow = true;
        if (layoutSparseArray.get(layoutId) != null) return isShow;
        switch (layoutId) {
            case LAYOUT_NETWORK_ERROR_ID:
                if (mStatusLayoutManager.netWorkErrorVs != null) {
                    View view = mStatusLayoutManager.netWorkErrorVs.inflate();
                    retryLoad(view, mStatusLayoutManager.netWorkErrorRetryViewId != 0 ? mStatusLayoutManager.netWorkErrorRetryViewId : mStatusLayoutManager.retryViewId);
                    layoutSparseArray.put(layoutId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_ERROR_ID:
                if (mStatusLayoutManager.errorVs != null) {
                    View view = mStatusLayoutManager.errorVs.inflate();
                    retryLoad(view, mStatusLayoutManager.errorRetryViewId != 0 ? mStatusLayoutManager.errorRetryViewId : mStatusLayoutManager.retryViewId);
                    layoutSparseArray.put(layoutId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_NO_PERMISSION_ID:
                if (mStatusLayoutManager.noPermissionVs != null) {
                    View view = mStatusLayoutManager.noPermissionVs.inflate();
                    retryLoad(view, mStatusLayoutManager.noPermissionRetryViewId != 0 ? mStatusLayoutManager.noPermissionRetryViewId : mStatusLayoutManager.retryViewId);
                    layoutSparseArray.put(layoutId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_EMPTYDATA_ID:
                if (mStatusLayoutManager.emptyDataVs != null) {
                    View view = mStatusLayoutManager.emptyDataVs.inflate();
                    retryLoad(view, mStatusLayoutManager.emptyDataRetryViewId != 0 ? mStatusLayoutManager.emptyDataRetryViewId : mStatusLayoutManager.retryViewId);
                    layoutSparseArray.put(layoutId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
        }
        return isShow;
    }

    /**
     * 重试加载
     */
    public void retryLoad(View view, int retryViewId) {
        View retryView = view.findViewById(retryViewId == 0 ? mStatusLayoutManager.retryViewId : retryViewId);
        if (retryView == null || mStatusLayoutManager.onRetryListener == null) return;
        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayoutManager.onRetryListener.onRetry(v);
            }
        });
    }

    public void replaceLoadingView(int loadingLayoutResId) {
        replaceLayoutResId(loadingLayoutResId, 0, RootFrameLayout.LAYOUT_LOADING_ID);
    }

    public void replaceEmptyDataView(int emptyDataLayoutResId) {
        replaceLayoutResId(emptyDataLayoutResId, 0, RootFrameLayout.LAYOUT_EMPTYDATA_ID);
    }

    public void replaceEmptyDataView(int emptyDataLayoutResId, int retryViewId) {
        replaceLayoutResId(emptyDataLayoutResId, retryViewId, RootFrameLayout.LAYOUT_EMPTYDATA_ID);
    }

    public void replaceNetworkErrorView(int networkErrorLayoutResId) {
        replaceLayoutResId(networkErrorLayoutResId, 0, RootFrameLayout.LAYOUT_NETWORK_ERROR_ID);
    }

    public void replaceNetworkErrorView(int networkErrorLayoutResId, int retryViewId) {
        replaceLayoutResId(networkErrorLayoutResId, retryViewId, RootFrameLayout.LAYOUT_NETWORK_ERROR_ID);
    }

    public void replaceErrorView(int errorLayoutResId) {
        replaceLayoutResId(errorLayoutResId, 0, RootFrameLayout.LAYOUT_ERROR_ID);
    }

    public void replaceErrorView(int errorLayoutResId, int retryViewId) {
        replaceLayoutResId(errorLayoutResId, retryViewId, RootFrameLayout.LAYOUT_ERROR_ID);
    }

    public void replaceNoPermissionView(int noPermissionLayoutResId) {
        replaceLayoutResId(noPermissionLayoutResId, 0, RootFrameLayout.LAYOUT_NO_PERMISSION_ID);
    }

    public void replaceNoPermissionView(int noPermissionLayoutResId, int retryViewId) {
        replaceLayoutResId(noPermissionLayoutResId, retryViewId, RootFrameLayout.LAYOUT_NO_PERMISSION_ID);
    }

    /**
     * 更换异常状态的布局
     *
     * @param layoutResId 要更换的布局Id
     * @param retryViewId  更换的布局中点击事件View的Id.
     *                     如果更换布局时传入了retryViewId,
     *                     使用传入的Id去判断点击事件,
     *                     不可使用StatusLayoutManager的对应的Id.
     * @param layoutId {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID}
     */
    private void replaceLayoutResId(@LayoutRes int layoutResId, int retryViewId, int layoutId) {
        removeView(layoutSparseArray.get(layoutId));
        View resView = LayoutInflater.from(mStatusLayoutManager.context).inflate(layoutResId, null);
        if (retryViewId == 0) {
            switch (layoutId) {
                case LAYOUT_NETWORK_ERROR_ID:
                    retryLoad(resView, mStatusLayoutManager.netWorkErrorRetryViewId != 0 ? mStatusLayoutManager.netWorkErrorRetryViewId : mStatusLayoutManager.retryViewId);
                    break;
                case LAYOUT_ERROR_ID:
                    retryLoad(resView, mStatusLayoutManager.noPermissionRetryViewId != 0 ? mStatusLayoutManager.noPermissionRetryViewId : mStatusLayoutManager.retryViewId);
                    break;
                case LAYOUT_NO_PERMISSION_ID:
                    retryLoad(resView, mStatusLayoutManager.errorRetryViewId != 0 ? mStatusLayoutManager.errorRetryViewId : mStatusLayoutManager.retryViewId);
                    break;
                case LAYOUT_EMPTYDATA_ID:
                    retryLoad(resView, mStatusLayoutManager.emptyDataRetryViewId != 0 ? mStatusLayoutManager.emptyDataRetryViewId : mStatusLayoutManager.retryViewId);
                    break;
            }
        } else {
            retryLoad(resView, retryViewId);
        }
        layoutSparseArray.put(layoutId, resView);
        addView(resView);
    }

    public void setCustomView(int customLayoutResId) {
        addLayoutResId(customLayoutResId, LAYOUT_CUSTOM_ID);
    }

    public View getLayout(int layoutId) {
        if (layoutSparseArray.get(layoutId) == null) {
            inflateLayout(layoutId);
        }
        return layoutSparseArray.get(layoutId);
    }

}
