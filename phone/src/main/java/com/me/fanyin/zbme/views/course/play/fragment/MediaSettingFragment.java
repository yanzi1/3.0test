package com.me.fanyin.zbme.views.course.play.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Statistics;
import com.me.data.event.MediaSettingQualityEvent;
import com.me.data.event.MediaSettingSpeedEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class MediaSettingFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.setting_close)
    TextView settingClose;
    Unbinder unbinder;
    @BindView(R.id.play_setting_speed)
    RelativeLayout playSettingSpeed;
    @BindView(R.id.play_setting_dd)
    RelativeLayout playSettingDD;
    @BindView(R.id.setting_quality_tv)
    TextView settingQualityTv;
    @BindView(R.id.play_download_setting)
    CheckedTextView isPlayDownload;
    @BindView(R.id.setting_speed_tv)
    TextView settingSpeedTv;
    private View view;
    private PlayActivity acivity;
    List<String> quaitys = new ArrayList<>();
    List<String> speeds = new ArrayList<>();
    private String beforeQuality;

    public MediaSettingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.course_media_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        quaitys.clear();
        speeds.clear();
        acivity = (PlayActivity) getActivity();
        settingClose.setOnClickListener(this);
        isPlayDownload.setOnClickListener(this);

        playSettingSpeed.setOnClickListener(this);
        playSettingDD.setOnClickListener(this);

        if (SharedPrefHelper.getInstance().canUseIn4G()) {
            isPlayDownload.setChecked(false);
        } else {
            isPlayDownload.setChecked(true);
        }

        settingQualityTv.setText(SharedPrefHelper.getInstance().getPlayQuaity());
        if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.0f) {
            settingSpeedTv.setText("1.0x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.2f) {
            settingSpeedTv.setText("1.2x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.5f) {
            settingSpeedTv.setText("1.5x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.8f) {
            settingSpeedTv.setText("1.8x");
        }

        quaitys.add("流畅");
        quaitys.add("标清");
        quaitys.add("高清");
        speeds.add("1.0x");
        speeds.add("1.2x");
        speeds.add("1.5x");
        speeds.add("1.8x");
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        if(isPlayDownload==null){
            return;
        }
        if (SharedPrefHelper.getInstance().canUseIn4G()) {
            isPlayDownload.setChecked(false);
        } else {
            isPlayDownload.setChecked(true);
        }

        settingQualityTv.setText(SharedPrefHelper.getInstance().getPlayQuaity());
        if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.0f) {
            settingSpeedTv.setText("1.0x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.2f) {
            settingSpeedTv.setText("1.2x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.5f) {
            settingSpeedTv.setText("1.5x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.8f) {
            settingSpeedTv.setText("1.8x");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_close:
                acivity.hideSetting(this);
                break;
            case R.id.play_setting_speed:
                if(acivity.isServiceStart){
                    Toast.makeText(getActivity(), "正在播放音频，无法切换播放速度", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                SharedPrefHelper.getInstance().setPlaySpeed(1.0f);
                                settingSpeedTv.setText("1.0x");
                                EventBus.getDefault().post(new MediaSettingSpeedEvent());
                                break;
                            case 1:
                                SharedPrefHelper.getInstance().setPlaySpeed(1.2f);
                                settingSpeedTv.setText("1.2x");
                                EventBus.getDefault().post(new MediaSettingSpeedEvent());
                                break;
                            case 2:
                                SharedPrefHelper.getInstance().setPlaySpeed(1.5f);
                                settingSpeedTv.setText("1.5x");
                                EventBus.getDefault().post(new MediaSettingSpeedEvent());
                                break;
                            case 3:
                                SharedPrefHelper.getInstance().setPlaySpeed(1.8f);
                                settingSpeedTv.setText("1.8x");
                                EventBus.getDefault().post(new MediaSettingSpeedEvent());
                                break;
                            default:
                                break;
                        }

                    }
                }, speeds, "", "setting_speed");
                break;
            case R.id.play_setting_dd:
                if(DownloadManager.getInstance().isCWDownlaoded(acivity.courseWare)){
                    Toast.makeText(getActivity(), "正在播放离线视频，无法切换清晰度", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(acivity.isServiceStart){
                    Toast.makeText(getActivity(), "正在播放音频，无法切换清晰度", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!NetWorkUtils.isConnected(getActivity())){
                    Toast.makeText(getActivity(),getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                    return;
                }
                beforeQuality=SharedPrefHelper.getInstance().getPlayQuaity();
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                SharedPrefHelper.getInstance().setPlayQuaity("流畅");
                                settingQualityTv.setText("流畅");
                                EventBus.getDefault().post(new MediaSettingQualityEvent(beforeQuality));
                                MobclickAgent.onEvent(getActivity(), Statistics.PROFILE_DOWNLOAD_FLUENT);
                                break;
                            case 1:
                                SharedPrefHelper.getInstance().setPlayQuaity("标清");
                                settingQualityTv.setText("标清");
                                EventBus.getDefault().post(new MediaSettingQualityEvent(beforeQuality));
                                MobclickAgent.onEvent(getActivity(), Statistics.PROFILE_DOWNLOAD_SD);
                                break;
                            case 2:
                                SharedPrefHelper.getInstance().setPlayQuaity("高清");
                                settingQualityTv.setText("高清");
                                EventBus.getDefault().post(new MediaSettingQualityEvent(beforeQuality));
                                MobclickAgent.onEvent(getActivity(), Statistics.PROFILE_DOWNLOAD_HD);
                                break;
                            default:
                                break;
                        }

                    }
                }, quaitys, "", "setting_midea");
                break;
            case R.id.play_download_setting:
                if (SharedPrefHelper.getInstance().canUseIn4G()) {
                    SharedPrefHelper.getInstance().set4GCanUse(false);
                    isPlayDownload.setChecked(true);
                } else {
                    SharedPrefHelper.getInstance().set4GCanUse(true);
                    isPlayDownload.setChecked(false);
                }
                break;
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names, String title, String flag) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names, title, flag);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
