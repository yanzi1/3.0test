package com.me.fanyin.zbme.base;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.me.core.utils.NetWorkUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by jijiangrui on 2016/12/9.
 */

public abstract class BaseWebviewFragment extends BaseFragment {

    @BindView(R.id.base_wbv)
    WebView base_wbv;
    @BindView(R.id.base_pgb)
    protected ProgressBar base_pgb;
    public static String USER_AGENT = ";dongao/android/PayH5";//webview设置代理
    protected String url;

    @Override
    public void initView() {
        resetWebView();
    }

    @Override
    public void initData() {
        loadUrl();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.base_webview_fragment;
    }

    protected void loadUrl() {
        if (!TextUtils.isEmpty(setUrl())) url = setUrl();
        if (TextUtils.isEmpty(url)) return;
        if (!TextUtils.isEmpty(setCookie())) CookieManager.getInstance().setCookie(url, setCookie());
        if (additionalHttpHeaders() == null) {
            base_wbv.loadUrl(url);
        } else {
            base_wbv.loadUrl(url, additionalHttpHeaders());
        }
    }
    
    public void reload(){
        if (base_wbv!=null){
            base_wbv.reload();
        }
    }

    public String setCookie() {
        return "";
    }

    public Map<String, String> additionalHttpHeaders() {
        return null;
    }

    protected abstract String setUrl();

    public void setUrl(String url) {
        this.url = url;
        loadUrl();
    }

    public void loadHtml(String s) {
        base_wbv.loadDataWithBaseURL("", s, "text/html", "utf-8", "");
    }

    private void resetWebView() {
        WebSettings settings = base_wbv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultFontSize(setDefaultFontSize());
        settings.setDefaultFixedFontSize(setDefaultFixedFontSize());
        settings.setUserAgentString(settings.getUserAgentString() + USER_AGENT);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设定支持缩放
        settings.setDisplayZoomControls(false);//设定缩放控件隐藏
        base_wbv.getSettings().setDefaultTextEncodingName("utf-8");
        base_wbv.setInitialScale(100);
        base_wbv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//指定的垂直滚动条有叠加样式
        base_wbv.setHorizontalScrollBarEnabled(false);
        base_wbv.setVerticalScrollBarEnabled(false);
        if (setWebViewClient() != null) {
            base_wbv.setWebViewClient(setWebViewClient());
        } else {
            base_wbv.setWebViewClient(new MyWebViewClient(this));
        }
        if (setWebChromeClient() != null) {
            base_wbv.setWebChromeClient(setWebChromeClient());
        } else {
            base_wbv.setWebChromeClient(new MyWebChromeClient(this));
        }
    }

    protected int setDefaultFixedFontSize() {
        return 16;
    }

    protected int setDefaultFontSize() {
        return 16;
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                mStatusLayoutManager.showLoading();
                loadUrl();
            }
        };
    }

    public WebViewClient setWebViewClient() {
        return null;
    }

    public WebChromeClient setWebChromeClient() {
        return null;
    }

    public boolean showProgressBar() {
        return true;
    }

    public WebView getBaseWbv() {
        return base_wbv;
    }

    @Override
    public void onDestroy() {
//        base_wbv.clearCache(true);
//        base_wbv.clearHistory();
//        base_wbv.clearFormData();
//        base_wbv.destroy();
        base_wbv.setWebChromeClient(null);
        base_wbv.destroy();
        super.onDestroy();
    }

    static class MyWebViewClient extends WebViewClient{

        WeakReference<BaseWebviewFragment> weakReference;

        public MyWebViewClient(BaseWebviewFragment fragment) {
            this.weakReference = new WeakReference<BaseWebviewFragment>(fragment);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BaseWebviewFragment baseWebviewFragment = weakReference.get();
            if (baseWebviewFragment!=null) {
                if (!NetWorkUtils.isNetworkAvailable(view.getContext()))
                    baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BaseWebviewFragment baseWebviewFragment = weakReference.get();
            if (!NetWorkUtils.isNetworkAvailable(view.getContext())) {
                if (baseWebviewFragment.base_pgb.getProgress() >= 60) {
                    baseWebviewFragment.mStatusLayoutManager.showContent();
                } else {
                    baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
                }
            } else {
                baseWebviewFragment.mStatusLayoutManager.showContent();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            BaseWebviewFragment baseWebviewFragment = weakReference.get();
            if (baseWebviewFragment!=null){
                if (!NetWorkUtils.isNetworkAvailable(view.getContext())) {
                    if (baseWebviewFragment.base_pgb.getProgress() >= 60) {
                        baseWebviewFragment.mStatusLayoutManager.showContent();
                    } else {
                        baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
                    }
                } else {
                    if (baseWebviewFragment.base_pgb.getProgress() >= 80) {
                        baseWebviewFragment.mStatusLayoutManager.showContent();
                    } else {
                        baseWebviewFragment.mStatusLayoutManager.showError();
                    }
                }
            }

        }
    }

    static class MyWebChromeClient extends WebChromeClient{
        WeakReference<BaseWebviewFragment> weakReference;

        public MyWebChromeClient(BaseWebviewFragment fragment) {
            this.weakReference = new WeakReference<BaseWebviewFragment>(fragment);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            BaseWebviewFragment baseWebviewFragment = weakReference.get();
            if (baseWebviewFragment!=null){
                if (!baseWebviewFragment.showProgressBar()) return;
                if (newProgress == 100) {
                    baseWebviewFragment.base_pgb.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    baseWebviewFragment.base_pgb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    baseWebviewFragment.base_pgb.setProgress(newProgress);//设置进度值
                }
            }
        }
    }

}
