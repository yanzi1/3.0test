package com.me.fanyin.zbme.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.me.core.app.AppContext;
import com.me.core.app.AppManager;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.statusbar.StatusBarCompat;
import com.me.fanyin.zbme.widget.statuslayoutmanager.RootFrameLayout;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import org.apache.http.util.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 简单的Activity，无需MVP
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    protected CommonToolbar mToolbar;

    protected RootFrameLayout mRootFrameLayout;
    protected AppContext appContext;
    protected Unbinder unbinder;
    protected StatusLayoutManager mStatusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        super.onCreate(savedInstnceState);
        init();
        setContentView(getLayoutRes());

        mRootFrameLayout = ButterKnife.findById(this, R.id.common_root_framelayout);

        if (mRootFrameLayout != null) {
            if (getContentResId() == 0) {
                throw new IllegalArgumentException("内容布局未指定,应复写getContentResId()方法");
            }
            mStatusLayoutManager = StatusLayoutManager.newBuilder(this)
                    .rootFrameLayout(mRootFrameLayout)
                    .contentView(getContentResId())
                    .loadingView(R.layout.app_view_loading)
                    .emptyDataView(R.layout.app_view_empty)
                    .errorView(R.layout.app_view_error)
                    .noPermissionView(R.layout.app_view_no_permission)
                    .networkErrorView(R.layout.app_view_nonetwork)
                    .retryViewId(R.id.app_action_btn)
                    .emptyDataRetryViewId(R.id.app_action_empty_btn)
                    .errorRetryViewId(R.id.app_action_error_btn)
                    .netWorkErrorRetryViewId(R.id.app_action_nonetwork_btn)
                    .noPermissionRetryViewId(R.id.app_action_no_permission_btn)
                    .onShowHideViewListener(addShowHideViewListener())
                    .onRetryListener(addRetryListener())
                    .build();
            mStatusLayoutManager.showContent();
        }

        unbinder = ButterKnife.bind(this);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(displayShowTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp());
        }
        initView();
        initData();
        if(setStatusBarVisiable()){
            StatusBarCompat.setStatusBarColor(this,-1);
        }
    }

    protected boolean setStatusBarVisiable(){
        return true;
    }

    // 初始化
    private void init() {
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        appContext = AppContext.getInstance();

    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

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

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected boolean displayHomeAsUp() {
        return true;
    }

    protected boolean displayShowTitle() {
        return false;
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
        gotoActivity(clz, false, null,className);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex, String className) {
        Intent intent = new Intent(this, clz);
        if (!TextUtils.isEmpty(className)) intent.putExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME, className);
        if (ex != null)
            intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            AppManager.getAppManager().finishActivity(this);
        }
    }

    public void gotoActivityForResult(Class<?> clz, int requestCode) {
        gotoActivityForResult(clz, null, requestCode);
    }

    public void gotoActivityForResult(Class<?> clz, Bundle ex, int requestCode) {
        Intent intent = new Intent(this, clz);
        if (ex != null)
            intent.putExtras(ex);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        // 将Activity从堆栈清除
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
