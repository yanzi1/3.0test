package com.me.fanyin.zbme.views.mine.settings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jjr on 2017/5/11.
 */

public class DownloadAndPlaySettingActivity extends BaseActivity {

    @BindView(R.id.network_status_setting_ctv)
    CheckedTextView network_status_setting_ctv;
    @BindView(R.id.network_status_setting_ll)
    LinearLayout network_status_setting_ll;
    @BindView(R.id.video_clarity_setting_tv)
    TextView video_clarity_setting_tv;
    @BindView(R.id.video_clarity_setting_ll)
    LinearLayout video_clarity_setting_ll;
    @BindView(R.id.course_play_multiple_setting_tv)
    TextView course_play_multiple_setting_tv;
    @BindView(R.id.course_play_multiple_setting_ll)
    LinearLayout course_play_multiple_setting_ll;
    private static final String[] clarity = {"流畅", "标清", "高清"};
    private static final String[] multiple = {"1.0x", "1.2x", "1.5x", "1.8x"};
    private List<String> options;

    @Override
    protected int getLayoutRes() {
        return R.layout.mine_download_play_setting_activity;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("下载与播放器设置");
        if (SharedPrefHelper.getInstance().canUseIn4G()) {
            network_status_setting_ctv.setChecked(false);
        } else network_status_setting_ctv.setChecked(true);
        video_clarity_setting_tv.setText(SharedPrefHelper.getInstance().getPlayQuaity());
        course_play_multiple_setting_tv.setText(SharedPrefHelper.getInstance().getPlaySpeed() + "X");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.network_status_setting_ctv, R.id.video_clarity_setting_ll, R.id.course_play_multiple_setting_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.network_status_setting_ctv:
                setNetworkStatus();
                break;
            case R.id.video_clarity_setting_ll:
                showClaritySettingDialog();
                break;
            case R.id.course_play_multiple_setting_ll:
                showPlayMultipleSettingDialog();
                break;
        }
    }

    private void setNetworkStatus() {
        if (SharedPrefHelper.getInstance().canUseIn4G()) {
            network_status_setting_ctv.setChecked(true);
            SharedPrefHelper.getInstance().set4GCanUse(false);
        } else {
            network_status_setting_ctv.setChecked(false);
            SharedPrefHelper.getInstance().set4GCanUse(true);
        }
    }

    private void showClaritySettingDialog() {

        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)  {
                    case 0:
                        SharedPrefHelper.getInstance().setPlayQuaity("流畅");
                        MobclickAgent.onEvent(DownloadAndPlaySettingActivity.this, Statistics.PROFILE_DOWNLOAD_FLUENT);
                        break;
                    case 1:
                        SharedPrefHelper.getInstance().setPlayQuaity("标清");
                        MobclickAgent.onEvent(DownloadAndPlaySettingActivity.this, Statistics.PROFILE_DOWNLOAD_SD);
                        break;
                    case 2:
                        SharedPrefHelper.getInstance().setPlayQuaity("高清");
                        MobclickAgent.onEvent(DownloadAndPlaySettingActivity.this, Statistics.PROFILE_DOWNLOAD_HD);
                        break;
                }
                video_clarity_setting_tv.setText(clarity[position]);
            }
        }, getOptions("clarity"), "", "setting_midea");
    }

    private void showPlayMultipleSettingDialog() {
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)  {
                    case 0:
                        SharedPrefHelper.getInstance().setPlaySpeed(1.0f);
                        break;
                    case 1:
                        SharedPrefHelper.getInstance().setPlaySpeed(1.2f);
                        break;
                    case 2:
                        SharedPrefHelper.getInstance().setPlaySpeed(1.5f);
                        break;
                    case 3:
                        SharedPrefHelper.getInstance().setPlaySpeed(1.8f);
                        break;
                }
                course_play_multiple_setting_tv.setText(multiple[position]);
            }
        }, getOptions("multiple"), "", "setting_speed");
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names, String title, String flag) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names, title, flag);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    private List<String> getOptions(String s) {
        switch (s) {
            case "clarity":
                options = Arrays.asList(clarity);
                break;
            case "multiple":
                options = Arrays.asList(multiple);
                break;
        }
        return options;
    }
}
