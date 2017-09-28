package com.me.fanyin.zbme.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.core.app.AppContext;
import com.me.core.app.AppManager;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.LoadingDialog;
import com.me.fanyin.zbme.widget.statuslayoutmanager.RootFrameLayout;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import org.apache.http.util.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jjr on 2017/4/1.
 */

public abstract class BaseFragment extends Fragment {

    protected AppContext appContext;
    protected AppCompatActivity mActivity;
    protected Context mContext;

    @Nullable
    @BindView(R.id.toolbar)
    protected CommonToolbar mToolbar;

    protected View mRootView;
    protected RootFrameLayout mRootFrameLayout;

    protected LoadingDialog loadingDialog;
    protected StatusLayoutManager mStatusLayoutManager;
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        appContext = AppContext.getInstance();
        mActivity = (AppCompatActivity) getActivity();
        mContext = getActivity();
        initLayout(inflater, container);
        if (mRootFrameLayout == null) {
            return mStatusLayoutManager.getRootLayout();
        } else {
            return mRootView;
        }
    }

    private void initLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        mRootView = inflater.inflate(getLayoutRes(), null, false);
        mRootFrameLayout = ButterKnife.findById(mRootView, R.id.common_root_framelayout);
        StatusLayoutManager.Builder builder = StatusLayoutManager.newBuilder(mActivity);
        if (mRootFrameLayout == null) {
            builder.contentView(mRootView);
        } else {
            builder.rootFrameLayout(mRootFrameLayout)
                    .contentView(getContentResId());
        }
        builder.emptyDataView(R.layout.app_view_empty)
                .loadingView(R.layout.app_view_loading)
                .errorView(R.layout.app_view_error)
                .networkErrorView(R.layout.app_view_nonetwork)
                .noPermissionView(R.layout.app_view_no_permission)
                .retryViewId(R.id.app_action_btn)
                .emptyDataRetryViewId(R.id.app_action_empty_btn)
                .errorRetryViewId(R.id.app_action_error_btn)
                .netWorkErrorRetryViewId(R.id.app_action_nonetwork_btn)
                .noPermissionRetryViewId(R.id.app_action_no_permission_btn)
                .onShowHideViewListener(addShowHideViewListener())
                .onRetryListener(addRetryListener());
        mStatusLayoutManager = builder.build();
        mStatusLayoutManager.showContent();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
        initView();
        initData();
    }

    // 初始化
    private void init() {
        //加载框
        loadingDialog = new LoadingDialog(mActivity);
    }

    public abstract void initView();

    public abstract void initData();

    @LayoutRes
    protected abstract int getLayoutRes();

    @LayoutRes
    protected int getContentResId() {
        return 0;
    }

    protected StatusLayoutManager.OnShowHideViewListener addShowHideViewListener() {
        return null;
    }

    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return null;
    }

    protected void replaceStatusView(@LayoutRes int layoutRes, int status) {
        mStatusLayoutManager.replaceStatusView(layoutRes, 0, status);
    }

    /**
     * 更换异常状态的布局
     *
     * @param layoutRes 要更换的布局id
     * @param retryViewId  更换的布局中点击事件View的Id
     * @param status {@link RootFrameLayout#LAYOUT_LOADING_ID,RootFrameLayout#LAYOUT_ERROR_ID,RootFrameLayout#LAYOUT_NETWORK_ERROR_ID,RootFrameLayout#LAYOUT_EMPTYDATA_ID,RootFrameLayout#LAYOUT_NO_PERMISSION_ID}
     */
    protected void replaceStatusView(@LayoutRes int layoutRes, int retryViewId, int status) {
        mStatusLayoutManager.replaceStatusView(layoutRes, retryViewId, status);
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     *
     * @param clz
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        gotoActivity(clz, isCloseCurrentActivity, ex, "");
    }

    public void gotoActivity(Class<?> clz, String className) {
        gotoActivity(clz, false, null, className);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex, String className) {
        Intent intent = new Intent(mActivity, clz);
        if (!TextUtils.isEmpty(className)) intent.putExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME, className);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            AppManager.getAppManager().finishActivity(mActivity);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
