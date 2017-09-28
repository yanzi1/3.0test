package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.main.event.ShareResultEvent;
import com.me.fanyin.zbme.views.main.fragemnt.SubwebFragment;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jjr on 2017/4/18.
 */

public class SubwebActivity extends BaseActivity {

    private SubwebFragment mSubwebFragment;
    private MainDetailItemBean detailItemBean;
    private int mouldCode;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        detailItemBean = (MainDetailItemBean) getIntent().getSerializableExtra(Constants.FORUM_NAME);
        mouldCode = getIntent().getExtras().getInt(Constants.MOULD_CODE);
        super.onCreate(savedInstnceState);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        /**
         case "1"://轮播图
         case "2"://头条
         case "3"://热点资讯
         case "4"://考试动态、图书勘误、备考攻略
         case "10"://考试倒计时
         */
        if (mouldCode == 10) {
            mToolbar.setTitleText("考试流程安排");
        } else if (mouldCode == 1 || mouldCode == 2) {
            mToolbar.setTitleText("活动详情");
        } else {
            mToolbar.setTitleText("文章详情");
        }
        mToolbar.setImageMenuRes(R.mipmap.btn_nav_share);
        mToolbar.setOnMenuClickListener(new CommonToolbar.CommonToolbarClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.common_toolbar_ivmenu:
                        if (mShareDialog == null) {
                            mShareDialog = new ShareDialog(SubwebActivity.this, R.style.shareDialogStyle, detailItemBean);
                        }
                        mShareDialog.show();
                        break;
                }
            }
        });
        mSubwebFragment = (SubwebFragment) getSupportFragmentManager().findFragmentByTag("subweb_fragment");
    }

    @Override
    protected void initData() {
        mSubwebFragment.setUrl(detailItemBean.getLink().contains("http://")?detailItemBean.getLink():"http://" + detailItemBean.getLink());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.subweb_activity;
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

    @Subscribe
    public void onMainThreadEvnet(ShareResultEvent event) {
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }
}
