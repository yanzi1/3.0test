package com.me.fanyin.zbme.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.me.fanyin.zbme.R;

/**
 * 活动 H5 页面
 *
 * Created by jijiangrui on 17-7-20.
 */

public class CommonWebViewBuyActivity extends BaseActivity {

    private BaseWebviewBuyFragment mBaseWebviewBuyFragment;

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebViewBuyActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.subweb_buy_activity;
    }

    @Override
    protected void initView() {
        mBaseWebviewBuyFragment = (BaseWebviewBuyFragment) getSupportFragmentManager().findFragmentByTag("subweb_buy_fragment");
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            finish();
            return;
        }
        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitleText(title);
        }
        mBaseWebviewBuyFragment.setUrl(url.contains("http://")?url:"http://"+url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && mBaseWebviewBuyFragment.getBaseWbv().canGoBack()) {//表示按返回键时的操作
            mBaseWebviewBuyFragment.getBaseWbv().goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
