package com.me.fanyin.zbme.views.mine.settings;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.me.core.utils.ToastBarUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.base.CommonWebViewActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.mine.utils.DataCleanManager;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.GlideCircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jjr on 2017/5/3.
 */

public class SettingsActivity extends BaseMvpActivity<SettingsView, SettingsPresenter> implements SettingsView {

    @BindView(R.id.settings_avatar_iv)
    ImageView settings_avatar_iv;
    @BindView(R.id.settings_nickname_tv)
    TextView settings_nickname_tv;
    @BindView(R.id.settings_address_tv)
    TextView settings_address_tv;
    @BindView(R.id.settings_account_safety_tv)
    TextView settings_account_safety_tv;
    @BindView(R.id.settings_download_play_tv)
    TextView settings_download_play_tv;
    @BindView(R.id.settings_common_problem_tv)
    TextView settings_common_problem_tv;
    @BindView(R.id.settings_about_us_tv)
    TextView settings_about_us_tv;
    @BindView(R.id.settings_logout_btn)
    Button settings_logout_btn;
    @BindView(R.id.settings_cache_size_tv)
    TextView settings_cache_size_tv;
    @BindView(R.id.settings_clean_cache_ll)
    LinearLayout settings_clean_cache_ll;

    @Override
    protected void initView() {
        mToolbar.setTitleText("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetView();
    }

    private void resetView() {
        Glide.with(this).load(SharedPrefHelper.getInstance().getUserAvatarImageUrl()).asBitmap().placeholder(R.mipmap.ico_avatar_weidenglu).error(SharedPrefHelper.getInstance().isLogin() ? R.mipmap.ico_avatar_denglu : R.mipmap.ico_avatar_weidenglu).transform(new GlideCircleTransform(this)).into(settings_avatar_iv);
        settings_nickname_tv.setText(SharedPrefHelper.getInstance().isLogin() ? SharedPrefHelper.getInstance().getLoginUsername() : "未登录");
        settings_cache_size_tv.setText(DataCleanManager.getTotalCacheSize(this));
        settings_logout_btn.setVisibility(SharedPrefHelper.getInstance().isLogin() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.mine_settings_activity;
    }

    @OnClick({R.id.settings_avatar_iv, R.id.settings_nickname_tv, R.id.settings_address_tv, R.id.settings_account_safety_tv, R.id.settings_download_play_tv, R.id.settings_common_problem_tv, R.id.settings_about_us_tv, R.id.settings_logout_btn, R.id.settings_clean_cache_ll})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_avatar_iv:
            case R.id.settings_nickname_tv:
                break;
            case R.id.settings_address_tv:
                break;
            case R.id.settings_account_safety_tv:
                break;
            case R.id.settings_download_play_tv:
                gotoActivity(DownloadAndPlaySettingActivity.class);
                break;
            case R.id.settings_common_problem_tv:
                CommonWebViewActivity.startActivity(SettingsActivity.this, "常见问题", "http://m.dongao.com/app/question/app.html");
                break;
            case R.id.settings_about_us_tv:
                gotoActivity(AboutUsActivity.class);
                break;
            case R.id.settings_logout_btn:
                DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
//                        mPresenter.logout();
                        SharedPrefHelper.getInstance().setExamSubjectSubListJson("");
                        SharedPrefHelper.getInstance().logout();
                        resetView();
                        gotoActivity(MainActivity.class);
                    }

                    @Override
                    public void cancel() {

                    }
                },"是否确认退出？",0,null,null);
                break;
            case R.id.settings_clean_cache_ll:
                cleanCache();
                break;
        }
    }

    public void gotoActivityAfterCheckLogin(Class<?> clz) {
        if (SharedPrefHelper.getInstance().isLogin()) {
            gotoActivity(clz);
        } else gotoActivity(LoginActivity.class);
    }

    private void cleanCache() {
        DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
            @Override
            public void confirm() {
                DataCleanManager.clearAllCache(SettingsActivity.this);
                settings_cache_size_tv.setText(DataCleanManager.getTotalCacheSize(SettingsActivity.this));
                ToastBarUtils.showToast(SettingsActivity.this, "清除完毕");
            }

            @Override
            public void cancel() {

            }
        }, "是否确认清除缓存?", 0, "确认", "取消");
    }

    @Override
    public void logoutSuccess(String msg) {
        SharedPrefHelper.getInstance().setExamSubjectSubListJson("");
        resetView();
        gotoActivity(MainActivity.class);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }
}
