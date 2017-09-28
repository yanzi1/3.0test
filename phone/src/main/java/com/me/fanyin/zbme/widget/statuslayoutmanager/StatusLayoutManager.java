package com.me.fanyin.zbme.widget.statuslayoutmanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;


public class StatusLayoutManager {

    final Context context;
    final ViewStub netWorkErrorVs;
    final int netWorkErrorRetryViewId;
    final ViewStub emptyDataVs;
    final int emptyDataRetryViewId;
    final ViewStub errorVs;
    final int errorRetryViewId;
    final ViewStub noPermissionVs;
    final int noPermissionRetryViewId;
    final int loadingLayoutResId;
    final int contentLayoutResId;
    final View contentView;
    final int retryViewId;
    int customRetryViewId = 0;

    private final RootFrameLayout rootFrameLayout;
    final OnShowHideViewListener onShowHideViewListener;
    final OnRetryListener onRetryListener;

    public StatusLayoutManager(Builder builder) {
        this.context = builder.context;
        this.loadingLayoutResId = builder.loadingLayoutResId;
        this.netWorkErrorVs = builder.netWorkErrorVs;
        this.netWorkErrorRetryViewId = builder.netWorkErrorRetryViewId;
        this.emptyDataVs = builder.emptyDataVs;
        this.emptyDataRetryViewId = builder.emptyDataRetryViewId;
        this.errorVs = builder.errorVs;
        this.errorRetryViewId = builder.errorRetryViewId;
        this.noPermissionVs = builder.noPermissionVs;
        this.noPermissionRetryViewId = builder.noPermissionRetryViewId;
        this.contentLayoutResId = builder.contentLayoutResId;
        this.contentView = builder.contentView;
        this.onShowHideViewListener = builder.onShowHideViewListener;
        this.retryViewId = builder.retryViewId;
        this.onRetryListener = builder.onRetryListener;

        if (builder.rootFrameLayout != null) {
            this.rootFrameLayout = builder.rootFrameLayout;
        } else {
            this.rootFrameLayout = new RootFrameLayout(this.context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootFrameLayout.setLayoutParams(layoutParams);
        }

        rootFrameLayout.setStatusLayoutManager(this);

    }

    /**
     * 更换异常状态的布局
     *
     * @param layoutRes 要更换的布局Id
     * @param retryViewId  更换的布局中点击事件View的Id.
     *                     如果更换布局时传入了retryViewId,
     *                     使用传入的Id去判断点击事件,
     *                     不可使用StatusLayoutManager的对应的Id.
     * @param status {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID}
     */
    public void replaceStatusView(@LayoutRes int layoutRes, int retryViewId, int status) {
        switch (status) {
            case RootFrameLayout.LAYOUT_LOADING_ID:
                replaceLoadingView(layoutRes);
                break;
            case RootFrameLayout.LAYOUT_ERROR_ID:
                if (retryViewId == 0) {
                    replaceErrorView(layoutRes);
                } else {
                    replaceErrorView(layoutRes, retryViewId);
                }
                break;
            case RootFrameLayout.LAYOUT_NETWORK_ERROR_ID:
                if (retryViewId == 0) {
                    replaceNetworkErrorView(layoutRes);
                } else {
                    replaceNetworkErrorView(layoutRes, retryViewId);
                }
                break;
            case RootFrameLayout.LAYOUT_EMPTYDATA_ID:
                if (retryViewId == 0) {
                    replaceEmptyDataView(layoutRes);
                } else {
                    replaceEmptyDataView(layoutRes, retryViewId);
                }
                break;
            case RootFrameLayout.LAYOUT_NO_PERMISSION_ID:
                if (retryViewId == 0) {
                    replaceNoPermissionView(layoutRes);
                } else {
                    replaceNoPermissionView(layoutRes, retryViewId);
                }
                break;
        }
    }

    /**
     * 更换loading布局
     *
     * @param loadingLayoutResId
     */
    private void replaceLoadingView(@LayoutRes int loadingLayoutResId) {
        rootFrameLayout.replaceLoadingView(loadingLayoutResId);
    }

    /**
     * 更换emptyData布局
     *
     * @param emptyDataLayoutResId
     */
    private void replaceEmptyDataView(@LayoutRes int emptyDataLayoutResId) {
        rootFrameLayout.replaceEmptyDataView(emptyDataLayoutResId);
    }

    /**
     * 更换emptyData布局
     *
     * @param emptyDataLayoutResId
     */
    private void replaceEmptyDataView(@LayoutRes int emptyDataLayoutResId, int retryViewId) {
        rootFrameLayout.replaceEmptyDataView(emptyDataLayoutResId, retryViewId);
    }

    /**
     * 更换netWorkError布局
     *
     * @param networkErrorLayoutResId
     */
    private void replaceNetworkErrorView(@LayoutRes int networkErrorLayoutResId) {
        rootFrameLayout.replaceNetworkErrorView(networkErrorLayoutResId);
    }

    /**
     * 更换netWorkError布局
     *
     * @param networkErrorLayoutResId
     */
    private void replaceNetworkErrorView(@LayoutRes int networkErrorLayoutResId, int retryViewId) {
        rootFrameLayout.replaceNetworkErrorView(networkErrorLayoutResId, retryViewId);
    }

    /**
     * 更换error布局
     *
     * @param errorLayoutResId
     */
    private void replaceErrorView(@LayoutRes int errorLayoutResId) {
        rootFrameLayout.replaceErrorView(errorLayoutResId);
    }

    /**
     * 更换error布局
     *
     * @param errorLayoutResId
     */
    private void replaceErrorView(@LayoutRes int errorLayoutResId, int retryViewId) {
        rootFrameLayout.replaceErrorView(errorLayoutResId, retryViewId);
    }

    /**
     * 更换noPermission布局
     *
     * @param noPermissionLayoutResId
     */
    private void replaceNoPermissionView(@LayoutRes int noPermissionLayoutResId) {
        rootFrameLayout.replaceNoPermissionView(noPermissionLayoutResId);
    }

    /**
     * 更换noPermission布局
     *
     * @param noPermissionLayoutResId
     */
    private void replaceNoPermissionView(@LayoutRes int noPermissionLayoutResId, int retryViewId) {
        rootFrameLayout.replaceNoPermissionView(noPermissionLayoutResId, retryViewId);
    }

    /**
     * 设置自定义布局
     *
     * @param customLayoutResId
     */
    public void setCustomView(@LayoutRes int customLayoutResId) {
        rootFrameLayout.setCustomView(customLayoutResId);
    }

    /**
     * 设置自定义布局
     *
     * @param customLayoutResId
     */
    public void setCustomView(@LayoutRes int customLayoutResId, int retryViewId) {
        customRetryViewId = retryViewId;
        rootFrameLayout.setCustomView(customLayoutResId);
    }

    /**
     *  显示loading
     */
    public void showLoading() {
        rootFrameLayout.showLoading();
    }

    /**
     *  显示内容
     */
    public void showContent() {
        rootFrameLayout.showContent();
    }

    /**
     *  显示空数据
     */
    public void showEmptyData() {
        rootFrameLayout.showEmptyData();
    }

    /**
     *  显示网络异常
     */
    public void showNetWorkError() {
        rootFrameLayout.showNetWorkError();
    }

    /**
     *  显示异常
     */
    public void showError() {
        rootFrameLayout.showError();
    }

    /**
     *  显示无权限
     */
    public void showNoPermission() {
        rootFrameLayout.showNoPermission();
    }

    /**
     *  显示自定义
     */
    public void showCustom() {
        rootFrameLayout.showCustom();
    }

    /**
     *  得到正在显示的布局ID
     *  {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_CONTENT_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID,RootFrameLayout#LAYOUT_CUSTOM_ID}
     */
    public int getShowingLayoutId() {
        return rootFrameLayout.getShowingLayoutId();
    }

    /**
     *  得到root 布局
     */
    public RootFrameLayout getRootLayout() {
        return rootFrameLayout;
    }

    /**
     *  得到id对应 布局
     *
     *  @param layoutId {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_CONTENT_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID,RootFrameLayout#LAYOUT_CUSTOM_ID}}
     */
    public View getLayout(int layoutId) {
        return rootFrameLayout.getLayout(layoutId);
    }

    /**
     *
     * @param layoutId {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_CONTENT_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID,RootFrameLayout#LAYOUT_CUSTOM_ID}}
     * @param viewId 要获取的View的id
     * @return 返回viewId对应的View
     */
    public View findViewById(int layoutId, int viewId) {
        View layoutView = getLayout(layoutId);
        return layoutView.findViewById(viewId);
    }

    public static final class Builder {

        private Context context;
        private RootFrameLayout rootFrameLayout;
        private int loadingLayoutResId;
        private int contentLayoutResId;
        private ViewStub netWorkErrorVs;
        private int netWorkErrorRetryViewId;
        private ViewStub emptyDataVs;
        private int emptyDataRetryViewId;
        private ViewStub errorVs;
        private int errorRetryViewId;
        private ViewStub noPermissionVs;
        private int noPermissionRetryViewId;
        private int retryViewId;
        private OnShowHideViewListener onShowHideViewListener;
        private OnRetryListener onRetryListener;
        private View contentView;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder rootFrameLayout(RootFrameLayout rootFrameLayout) {
            this.rootFrameLayout = rootFrameLayout;
            return this;
        }

        public Builder loadingView(@LayoutRes int loadingLayoutResId) {
            this.loadingLayoutResId = loadingLayoutResId;
            return this;
        }

        public Builder networkErrorView(@LayoutRes int networkErrorId) {
            netWorkErrorVs = new ViewStub(context);
            netWorkErrorVs.setLayoutResource(networkErrorId);
            return this;
        }

        public Builder emptyDataView(@LayoutRes int noDataViewId) {
            emptyDataVs = new ViewStub(context);
            emptyDataVs.setLayoutResource(noDataViewId);
            return this;
        }

        public Builder errorView(@LayoutRes int errorViewId) {
            errorVs = new ViewStub(context);
            errorVs.setLayoutResource(errorViewId);
            return this;
        }

        public Builder noPermissionView(@LayoutRes int noPermissionViewId) {
            noPermissionVs = new ViewStub(context);
            noPermissionVs.setLayoutResource(noPermissionViewId);
            return this;
        }

        /**
         * 和contentView(View contentView)方法只需要设置一个
         *
         * @param contentLayoutResId
         * @return
         */
        public Builder contentView(@LayoutRes int contentLayoutResId) {
            this.contentLayoutResId = contentLayoutResId;
            return this;
        }

        /**
         * 和contentView(@LayoutRes int contentLayoutResId)方法只需要设置一个
         *
         * @param contentView
         * @return
         */
        public Builder contentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder netWorkErrorRetryViewId(int netWorkErrorRetryViewId) {
            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId;
            return this;
        }

        public Builder emptyDataRetryViewId(int emptyDataRetryViewId) {
            this.emptyDataRetryViewId = emptyDataRetryViewId;
            return this;
        }

        public Builder errorRetryViewId(int errorRetryViewId) {
            this.errorRetryViewId = errorRetryViewId;
            return this;
        }

        public Builder noPermissionRetryViewId(int noPermissionRetryViewId) {
            this.noPermissionRetryViewId = noPermissionRetryViewId;
            return this;
        }

        public Builder retryViewId(int retryViewId) {
            this.retryViewId = retryViewId;
            return this;
        }

        public Builder onShowHideViewListener(OnShowHideViewListener onShowHideViewListener) {
            this.onShowHideViewListener = onShowHideViewListener;
            return this;
        }

        public Builder onRetryListener(OnRetryListener onRetryListener) {
            this.onRetryListener = onRetryListener;
            return this;
        }

        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public interface OnRetryListener {
        void onRetry(View v);
    }

    public interface OnShowHideViewListener {
        void onShowView(View view, int id);
        void onHideView(View view, int id);
    }

}