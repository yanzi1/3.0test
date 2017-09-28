package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.data.model.main.MastersCourseItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.widget.WVJBWebViewClient;
import com.me.fanyin.zbme.views.main.event.ShareResultEvent;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.ScrollWebView;
import com.me.fanyin.zbme.widget.ShareDialog;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import org.apache.http.util.TextUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by jjr on 2017/4/18.
 */

public class FamousTeacherActivity extends BaseActivity {

    @BindView(R.id.base_pgb)
    ProgressBar base_pgb;
    @BindView(R.id.base_wbv)
    ScrollWebView base_wbv;
    //    @BindView(R.id.status_bar_fix)
//    View status_bar_fix;
    private MainDetailItemBean detailItemBean;
    public static String USER_AGENT = ";dongao/android/PayH5";//webview设置代理
    private int imgBgHeight = 360;
    private String examId;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        examId = getIntent().getStringExtra(Constants.EXAM_ID);
        detailItemBean = (MainDetailItemBean) getIntent().getSerializableExtra(Constants.FORUM_NAME);
        super.onCreate(savedInstnceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        base_wbv.setWebChromeClient(null);
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        setTranslucentStatus();
        mToolbar.setNavigationIcon(R.mipmap.btn_nav_back_white);
        mToolbar.setImageMenuRes(R.mipmap.btn_nav_share_white);
        mToolbar.showIamgeMenu();
        mToolbar.setOnMenuClickListener(new CommonToolbar.CommonToolbarClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.common_toolbar_ivmenu:
                        mShareDialog = new ShareDialog(FamousTeacherActivity.this, R.style.shareDialogStyle, detailItemBean);
                        mShareDialog.show();
                        break;
                }
            }
        });
        mToolbar.setTitleColor(ContextCompat.getColor(this, R.color.transparent));
        mToolbar.setTitleText(detailItemBean.getTitle());
        resetWebView();
    }

    @Override
    protected void initData() {
        base_wbv.loadUrl(detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_famous_teacher_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.main_famous_teacher_content;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && base_wbv.canGoBack()) {//表示按返回键时的操作
            base_wbv.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void resetWebView() {
        WebSettings settings = base_wbv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUserAgentString(settings.getUserAgentString() + USER_AGENT);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设定支持缩放
        settings.setDisplayZoomControls(false);//设定缩放控件隐藏
        base_wbv.setInitialScale(100);
        base_wbv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//指定的垂直滚动条有叠加样式
        base_wbv.setHorizontalScrollBarEnabled(false);
        base_wbv.setVerticalScrollBarEnabled(false);
        base_wbv.setWebViewClient(new MyWebViewClient(base_wbv, examId) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!NetWorkUtils.isNetworkAvailable(FamousTeacherActivity.this))
                    mStatusLayoutManager.showNetWorkError();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!NetWorkUtils.isNetworkAvailable(FamousTeacherActivity.this)) {
                    if (base_pgb != null && base_pgb.getProgress() >= 60) {
                        mStatusLayoutManager.showContent();
                    } else {
                        mStatusLayoutManager.showNetWorkError();
                    }
                } else {
                    mStatusLayoutManager.showContent();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (!NetWorkUtils.isNetworkAvailable(FamousTeacherActivity.this)) {
                    if (base_pgb != null && base_pgb.getProgress() >= 60) {
                        mStatusLayoutManager.showContent();
                    } else {
                        mStatusLayoutManager.showNetWorkError();
                    }
                } else {
                    if (base_pgb != null && base_pgb.getProgress() >= 80) {
                        mStatusLayoutManager.showContent();
                    } else {
                        mStatusLayoutManager.showError();
                    }
                }
            }
        });
        base_wbv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (!showProgressBar()) return;
                if (newProgress == 100) {
                    base_pgb.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    base_pgb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    base_pgb.setProgress(newProgress);//设置进度值
                }
            }

        });
        base_wbv.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
//                status_bar_fix.setBackgroundColor(Color.argb(0, 250,250,250));
                mToolbar.setBackgroundResource(R.color.transparent);
                mToolbar.setTitleColor(ContextCompat.getColor(FamousTeacherActivity.this, R.color.transparent));
                mToolbar.setNavigationIcon(R.mipmap.btn_nav_back_white);
                mToolbar.setImageMenuRes(R.mipmap.btn_nav_share_white);
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                if (t > 0 && t <= imgBgHeight) {  //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) t / imgBgHeight;
                    float alpha = (255 * scale);
//                    status_bar_fix.setBackgroundColor(Color.argb((int) alpha, 255, 141, 52));
                    mToolbar.setBackgroundColor(Color.argb((int) alpha, 250, 250, 250));
                    mToolbar.setTitleColor(Color.argb(255, 120, 120, 120));
                    mToolbar.setNavigationIcon(R.mipmap.btn_nav_back_white);
                    mToolbar.setImageMenuRes(R.mipmap.btn_nav_share_white);
                } else {  //滑动到banner下面设置普通颜色
//                    status_bar_fix.setBackgroundColor(Color.argb(255, 255, 141, 52));
                    mToolbar.setBackgroundColor(Color.argb(255, 250, 250, 250));
                    mToolbar.setTitleColor(Color.argb(255, 53, 53, 53));
                    mToolbar.setNavigationIcon(R.mipmap.btn_nav_back);
                    mToolbar.setImageMenuRes(R.mipmap.btn_nav_share);
                }
            }
        });
    }

    public boolean showProgressBar() {
        return true;
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            status_bar_fix.setLayoutParams(new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.getStatusBarHeight(this)));
        }
    }

    static class MyWebViewClient extends WVJBWebViewClient {

        private WebView webView;
        private String examId;

        public MyWebViewClient(final WebView webView, final String examId) {
            super(webView, new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    callback.callback("Response for message from Android!");
                }
            });
            this.webView = webView;
            this.examId = examId;

            send("", new WVJBWebViewClient.WVJBResponseCallback() {
                @Override
                public void callback(Object data) {
                }
            });

            registerHandler("setVideoUrl", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                    if (NetWorkUtils.isNetworkAvailable(webView.getContext())) {
                        MastersCourseItemBean itemBean = JSON.parseObject(data.toString(), MastersCourseItemBean.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Constants.IS_FREE_PLAY, true);
                        bundle.putString(Constants.LINK, itemBean.getVideoUrl());
                        if (!TextUtils.isEmpty(itemBean.getVideoId()) && itemBean.getVideoId().contains("_")) {
                            bundle.putString(Constants.EXAM_ID, itemBean.getVideoId().substring(0, itemBean.getVideoId().indexOf("_")));
                            bundle.putString(Constants.LECTURE_ID, itemBean.getVideoId().substring(itemBean.getVideoId().indexOf("_") + 1));
                        } else {
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.LECTURE_ID, "");
                        }
                        Intent intent = new Intent(webView.getContext(), PlayActivity.class);
                        intent.putExtras(bundle);
                        webView.getContext().startActivity(intent);
                    } else {
                        ToastBarUtils.showToast(webView.getContext(), webView.getContext().getString(R.string.app_nonetwork_message));
                    }

                    callback.callback("Response for message from Android! setVideoUrl()->Success");
                }
            });
        }

    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                mStatusLayoutManager.showLoading();
                initData();
            }
        };
    }

    @Subscribe
    public void onMainThreadEvnet(ShareResultEvent event) {
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }
}
