package com.me.fanyin.zbme.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.fragemnt.SubwebFragment;

/**
 * 简单的webView
 * Created by mayunfei on 17-6-8.
 */

public class CommonWebViewActivity extends BaseActivity {

    private SubwebFragment mSubwebFragment;

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.subweb_activity;
    }

    @Override
    protected void initView() {
        mSubwebFragment = (SubwebFragment) getSupportFragmentManager().findFragmentByTag("subweb_fragment");
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
        mSubwebFragment.setUrl(url.contains("http://")?url:"http://"+url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && mSubwebFragment.getBaseWbv().canGoBack()) {//表示按返回键时的操作
            mSubwebFragment.getBaseWbv().goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
