package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.me.core.utils.DensityUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/5.
 */

public class BooksErrataDetailActivity extends BaseActivity {

    @BindView(R.id.errata_detail_title_tv)
    TextView errata_detail_title_tv;
    @BindView(R.id.errata_detail_books_tv)
    TextView errata_detail_books_tv;
    @BindView(R.id.errata_detail_time_tv)
    TextView errata_detail_time_tv;
    @BindView(R.id.errata_detail_source_wbv)
    WebView errata_detail_source_wbv;
    @BindView(R.id.errata_detail_target_wbv)
    WebView errata_detail_target_wbv;
    private MainDetailItemBean detailItemBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        detailItemBean = (MainDetailItemBean) getIntent().getSerializableExtra(Constants.FORUM_NAME);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("勘误详情");
        errata_detail_title_tv.setText(detailItemBean.getTitle());
        errata_detail_books_tv.setText(detailItemBean.getTab().replaceAll(",", "/"));
        errata_detail_time_tv.setText(detailItemBean.getPublishDate().substring(0, detailItemBean.getPublishDate().indexOf(" ")));
        resetWebView(errata_detail_source_wbv);
        errata_detail_source_wbv.loadDataWithBaseURL("file://", detailItemBean.getSourceText(), "text/html", "UTF-8", "about:blank");
        resetWebView(errata_detail_target_wbv);
        errata_detail_target_wbv.loadDataWithBaseURL("file://", detailItemBean.getTargetText(), "text/html", "UTF-8", "about:blank");
    }

    private void resetWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFixedFontSize(DensityUtils.px2dip(this, 14));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_errata_detail_activity;
    }

}
