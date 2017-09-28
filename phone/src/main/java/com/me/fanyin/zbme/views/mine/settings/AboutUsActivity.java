package com.me.fanyin.zbme.views.mine.settings;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.core.utils.AppUtils;
import com.me.core.utils.SystemUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.PermissionsActivity;
import com.me.fanyin.zbme.widget.DialogManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/11.
 */

public class AboutUsActivity extends BaseMvpActivity<AboutUsView, AboutUsPresenter> implements AboutUsView {

    @BindView(R.id.about_us_version_name_tv)
    TextView about_us_version_name_tv;
    @BindView(R.id.about_us_app_name_tv)
    TextView about_us_app_name_tv;
//    @BindView(R.id.about_us_version_explain_tv)
//    TextView about_us_version_explain_tv;
//    @BindView(R.id.about_us_comment_tv)
//    TextView about_us_comment_tv;
    @BindView(R.id.about_us_feedback_tv)
    TextView about_us_feedback_tv;
    @BindView(R.id.about_us_weibo_tv)
    TextView about_us_weibo_tv;
    @BindView(R.id.about_us_weixin_tv)
    TextView about_us_weixin_tv;
    @BindView(R.id.about_us_service_ll)
    LinearLayout about_us_service_ll;

    @Override
    protected int getLayoutRes() {
        return R.layout.mine_about_us_activity;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("关于我们");
        about_us_version_name_tv.setText("V" + AppUtils.getAppVersionName(appContext, getPackageName()));
        about_us_app_name_tv.setText(AppUtils.getAppName(appContext, getPackageName()));
        about_us_version_name_tv.setOnClickListener(this);
//        about_us_version_explain_tv.setOnClickListener(this);
//        about_us_comment_tv.setOnClickListener(this);
        about_us_feedback_tv.setOnClickListener(this);
        about_us_weibo_tv.setOnClickListener(this);
        about_us_weixin_tv.setOnClickListener(this);
        about_us_service_ll.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_us_version_name_tv:

                break;
//            case R.id.about_us_version_explain_tv:
//                mPresenter.getVersionInfo();
//                break;
//            case R.id.about_us_comment_tv:
//                gotoComment();
//                break;
            case R.id.about_us_feedback_tv:
                gotoActivity(FeedbackActivity.class);
                break;
            case R.id.about_us_weibo_tv:
                gotoWeiboOnlineActivity();
                break;
            case R.id.about_us_weixin_tv:
                showWeixinQRDialog();
                break;
            case R.id.about_us_service_ll:
                showCallServiceDialog();
                break;
        }
    }

    private void gotoComment() {
        try{
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
            ToastBarUtils.showToast(this, "您的手机没有安装Android应用市场");
            e.printStackTrace();
        }
    }

    private void gotoWeiboOnlineActivity() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("http://weibo.com/dongaoonline"));
        startActivity(intent);
    }

    /**
     * 显示展示微信二维码图片的Dialog
     */
    private void showWeixinQRDialog() {
        DialogManager.showWeixinQrDialog(AboutUsActivity.this, new DialogManager.OnChildClickListener() {
            @Override
            public void OnChildClick(View view) {

            }
        });
    }

    private void showCallServiceDialog() {
        DialogManager.showConfirmWithCancelDialog(AboutUsActivity.this, new DialogManager.ConfirmWithCancelDialogListener() {
            @Override
            public void confirm() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionsActivity.startActivityForResult(AboutUsActivity.this, 1, new String[]{Manifest.permission.CALL_PHONE});
                } else {
                    SystemUtils.callPhone(AboutUsActivity.this, "400-627-5566");
                }
            }

            @Override
            public void cancel() {

            }
        }, "400-627-5566", 0, "呼叫", "取消");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            SystemUtils.callPhone(AboutUsActivity.this, "400-627-5566");
        }
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(this, message);
    }

}
