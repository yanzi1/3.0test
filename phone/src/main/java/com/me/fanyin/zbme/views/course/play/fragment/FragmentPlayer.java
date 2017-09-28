package com.me.fanyin.zbme.views.course.play.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.me.core.utils.DateUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwStudyLog;
import com.me.data.model.play.PlayParamsBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.db.PlayParamsDB;
import com.me.fanyin.zbme.views.course.play.utils.AESHelper;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.SignUtils;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.widget.DialogManager;
import com.umeng.analytics.MobclickAgent;
import com.yunqing.core.db.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class FragmentPlayer extends Fragment implements OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,MediaPlayer.OnSeekCompleteListener {

    private View view;
    private String paths;
    private Uri uri;
    public VideoView mVideoView;
    public ProgressBar pb;
    //    private TextView downloadRateView, loadRateView;
    public MediaController mediaController;
    private LinearLayout playError;
    private TextView errorMsg;
    private ImageView errorImg;
    private ImageView otherBack;

    private static final int DATA_POST_STARTTIME = 30001;//同步开始学习时间
    private static final int DATA_POST = 20001;//数据同步
    private static final int SWITCH_VIDEO = 1;//切换视频
    private static final int PROGRESSBAR = 66;//进度条控制
    private static final int PLAY_ISTART = 131313;
    private static final int SCREEN_ISLOCK = 9879;
    private PlayParamsDB playParamsDB;
    private PlayActivity activity;
    private int mHeight;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_POST:
                    long endTime = (long) msg.obj;
                    if (!activity.isFromFree) {
                        insertStudyLog(endTime);
                    }
                    break;
                case DATA_POST_STARTTIME:
                    long post_startTime = (long) msg.obj;
                    startTime = post_startTime;
                    break;
                case SWITCH_VIDEO:
                    CourseWare courseWare = (CourseWare) msg.obj;
                    playVedio(courseWare, false);
                    break;
                case PLAY_ISTART:
                    isStart = false;
                    break;
                case 888:
                    try {
                        int orientation = msg.arg1;
                        if (orientation > 45 && orientation < 135) {

                        } else if (orientation > 135 && orientation < 225) {

                        } else if (orientation > 225 && orientation < 315) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                            sensor_flag = false;
//                            stretch_flag = false;
                        } else if ((orientation > 315 && orientation < 360)
                                || (orientation > 0 && orientation < 45)) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                            sensor_flag = true;
//                            stretch_flag = true;

                        }
                    } catch (Exception e) {

                    }
                    break;
                case SCREEN_ISLOCK:
                    if (mediaController.isLock) {
                        int rotation = getDisplayRotation(getActivity());
                        if (rotation == 270 && mediaController.isLock) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        } else if (rotation == 90 && mediaController.isLock) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                    } else {
//                        if (sm != null) {
//                            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
//                        }
//                        if (sm1 != null) {
//                            sm1.registerListener(listener1, sensor1, SensorManager.SENSOR_DELAY_UI);
//                        }
                    }
                    break;
            }
        }
    };

    public static FragmentPlayer getInstance(Bundle args) {
        FragmentPlayer f = new FragmentPlayer();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        persenter = new FragmentPlayPersenter();
//        persenter.attachView(this);
//        AppContext.getInstance().setPlayHandler(handler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Vitamio.isInitialized(getActivity());
        view = inflater.inflate(R.layout.fragment_player, null);
//        db = new DownloadDB(getActivity());
        playParamsDB = new PlayParamsDB(getActivity());
        cwStudyLogDB = new CwStudyLogDB();
        activity = (PlayActivity) getActivity();
        initVideo();
//        play(true);
        return view;
    }

    private void initVideo() {
        userId = SharedPrefHelper.getInstance().getUserId();
        playError = (LinearLayout) view.findViewById(R.id.play_error_ll);
        errorMsg = (TextView) view.findViewById(R.id.error_msg);
        errorImg = (ImageView) view.findViewById(R.id.error_img);
        otherBack = (ImageView) view.findViewById(R.id.img_back);
        otherBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        playError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.isFromFree) {
                    if (NetWorkUtils.isConnected(getActivity())) {
                        playError.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                        play(false);
                    }
                }else if(activity.isFromSmart){
                    activity.initData();
                } else {
                    if (NetWorkUtils.isConnected(getActivity())) {
                        playError.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                        activity.refreshPlay();
                    }
                }

//                if(from.equals("1")){
//                    activity.refreshPlay();
//                }else{
//                    if(NetWorkUtils.isConnected(getActivity())){
//                        pb.setVisibility(View.VISIBLE);
//                        play(flag);
//                    }
//                }
            }
        });

        mVideoView = (VideoView) view.findViewById(R.id.buffer);
        Rect outRect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int mWidth = outRect.width();
        mHeight = outRect.height();
        CenterLayout.LayoutParams pp = new CenterLayout.LayoutParams(mWidth, mHeight / 3 - 15, 0, 0);
        mVideoView.setLayoutParams(pp);

        pb = (ProgressBar) view.findViewById(R.id.probar);
    }

    /**
     * @param from 1 playactivity 2 fragmentplayer
     * @param type 0 网络提示  1 流量提示
     */
    private String from;

    public void showNetError(String msg, String type, String from) {
        this.from = from;
        if (errorMsg == null) {
            Toast.makeText(activity, "请检查网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        otherBack.setVisibility(View.GONE);
        errorMsg.setText(msg);
        errorImg.setVisibility(View.VISIBLE);
        if (type.equals("0")) {
            errorImg.setImageResource(R.mipmap.btn_error_retry);
        } else {
            errorImg.setImageResource(R.mipmap.continue_play);
        }
        pb.setVisibility(View.GONE);
        playError.setVisibility(View.VISIBLE);
        if (mediaController != null) {
            mediaController.setVisibility(View.GONE);
        }
        if (mVideoView != null) {
            if(mVideoView.isPlaying()){
                mVideoView.pause();
            }
            activity.isCanPlay=false;
//          mVideoView.stopPlayback();

            mVideoView.setBackgroundResource(R.color.black);
        }
    }

    public void showOtherError(String msg) {
        errorMsg.setText(msg);
        errorImg.setVisibility(View.GONE);
        otherBack.setVisibility(View.VISIBLE);

        pb.setVisibility(View.GONE);
        playError.setVisibility(View.VISIBLE);
    }

    public boolean getErrorIsShow(){
        if(playError!=null){
            return playError.isShown();
        }
        return false;
    }

    public void playError() {
        pb.setVisibility(View.GONE);
        mVideoView.setBackgroundResource(R.color.text_color_primary_dark);
        if (mediaController != null) {
            mediaController.showErrorLayout();
        }
    }

    public boolean flag;
    private long seekTime = 0;
    public boolean isAllowPlay;//使用流量时，每次切换课程都提示
    public boolean isPlayMp3;

    public void playVedio(CourseWare courseWare, boolean isPlayMp3) {
        if (courseWare == null) {
            return;
        }
        otherBack.setVisibility(View.GONE);
        errorTimes=0;
        this.isPlayMp3 = isPlayMp3;
        if (mediaController != null) {  //页面销毁时remove  messages
            mediaController.mHandler.removeMessages(MSG_STUDY_LOG);
        }
        playError.setVisibility(View.GONE);
        activity.isServiceStart = isPlayMp3;
        if (isPlayMp3) {
            mVideoView.setBackgroundResource(R.mipmap.audioplay_bg);
        } else {
            mVideoView.setBackgroundResource(R.color.transparent);
        }
        startTime = 0;
        CwStudyLog logstart = cwStudyLogDB.query(userId, courseWare.getId(), courseWare.getClassId());
//        /****xunice***///切换视频时同步记录上传
        if (cw != null) { //说明本身已经有正在播放的视频，把原来的课节记录进行同步
            if (!activity.isFromFree) {
                if (!cw.getId().equals(courseWare.getId())) {
                    doSync();
                } else {
                    seekTime = activity.seekTime;
                }
            }
            if(mVideoView.isPlaying()){
                mVideoView.stopPlayback();
            }
        }
        cw = courseWare;

        if (!TextUtils.isEmpty(cw.getStartTime())) {
//            startTime = Integer.parseInt(cw.getStartTime()) * 1000;
            startTime = StringUtil.getLongMills(cw.getStartTime());
        }

        if (logstart != null) {
            long totalTime = logstart.getTotalTime();
            if (startTime < logstart.getEndTime()) {
                startTime = logstart.getEndTime();
            }
            if (totalTime != 0) {
                if (totalTime - startTime < 15000) {
                    startTime = 0;
                }
            }
        }

        if (!TextUtils.isEmpty(activity.startime)) {
            String seconds = activity.startime;
            startTime = StringUtil.getLongMills(seconds);
            activity.startime = "";
        }
        if (seekTime != 0) {
            startTime = seekTime;
            seekTime = 0;
            activity.seekTime = 0;
        }

        pb.setVisibility(View.VISIBLE);

        flag = DownloadManager.getInstance().isCWDownlaoded(courseWare);

        if (!flag) {
            if (activity.isFromFree) {
                paths = courseWare.getMobileVideoUrl();
            } else {
                if (isPlayMp3) {
                    paths = cw.getMobileVideoMP3();
                } else {
                    paths = getOnlinePlayUrl(cw);
                }
            }
//            boolean isPlay = SharedPrefHelper.getInstance().canUseIn4G();
            if (!NetWorkUtils.isConnected(getActivity())) {//无网络
                showNetError(activity.getResources().getString(R.string.no_network), "0", "2");
//                DialogManager.showOneButtonDialog(getActivity(), null, activity.getResources().getString(R.string.dialog_message_vedio), 0, null);
            } else if (NetWorkUtils.getNetworkType(getActivity()) == ConnectivityManager.TYPE_MOBILE) { //流量
                showNetError("继续播放会消耗流量", "1", "2");
                if (isAllowPlay) {
                    play(flag);
                } else {
                    DialogManager.showConfirmWithCancelDialog(activity, new com.me.fanyin.zbme.widget.DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            play(flag);
                            isAllowPlay = true;
                        }

                        @Override
                        public void cancel() {
                            pb.setVisibility(View.GONE);
                            isAllowPlay = true;
                        }
                    }, activity.getResources().getString(R.string.dialog_allow_vedio), 0, null, null);
                }

            } else if (NetWorkUtils.getNetworkType(getActivity()) == ConnectivityManager.TYPE_WIFI) {//wifi
                play(flag);
            }

        } else {
            if (isPlayMp3) {
                paths = DownloadManager.getInstance().getMp3Path(courseWare);
            } else {
                initM3u8(courseWare);
//            paths = getLocalPlayUrl(localCw);
                paths = DownloadManager.getInstance().getM3u8Path(courseWare);
            }
            play(flag);
        }
    }

//    @Override
//    public void playError(String msg) {
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }

    public void play(boolean flag) {
//      paths = "http://localhost:" + Constants.SERVER_PORT + "/" +666 + "/video/video.m3u8";
//      paths = "https://v.dongaocloud.com/test/zjf_zjyk_kj_st_13_854x480_400kbps_m3u8_10/video.m3u8";
//        paths = "http://videodl.dongao.com/2014zs/ck/zcl/ydb/14zsck_zcldd_001_qy_yd/upload/media/m3u8_10/video.m3u8";
//        paths = Environment.getExternalStorageDirectory() + "/666.mp4";
        playError.setVisibility(View.GONE);
        activity.isCanPlay=true;
        if (isPlayMp3) {
            mVideoView.setBackgroundResource(R.mipmap.audioplay_bg);
        } else {
            mVideoView.setBackgroundResource(R.color.transparent);
        }
        if (TextUtils.isEmpty(paths)) {
            Toast.makeText(getActivity(), "视频数据错误", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (mediaController == null) {
                mediaController = new MediaController(getActivity(), mVideoView, handler);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { //初始化Controller高度
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mHeight / 3 - 15);
                    mediaController.setLayoutParams(layoutParams);
                } else {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                    mediaController.setLayoutParams(layoutParams);
                }
            }
            mediaController.setVisibility(View.VISIBLE);
            mVideoView.setMediaController(mediaController);
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnPreparedListener(this);
            mVideoView.setOnCompletionListener(this);
            mVideoView.setOnErrorListener(this);
            mVideoView.setOnSeekCompleteListener(this);
            mediaController.setCourseWare(cw, flag, startTime);
            uri = Uri.parse(paths);
            mVideoView.setVideoURI(uri);
            mVideoView.requestFocus();
            if (!activity.isFromFree && !activity.isFromSmart) {
                activity.IsCollect(cw);
            }
        }
    }

    public void seekToSec(long time) {
        mVideoView.seekTo(time);
    }

    private void initM3u8(CourseWare coursew) {
        String localPath = DownloadManager.getInstance().getM3u8Path(coursew);
        PlayParamsBean playParamsBean = playParamsDB.getParamById(SharedPrefHelper.getInstance().getUserId(), coursew.getId());
        if (playParamsBean == null) {
            Toast.makeText(activity, "视频数据错误", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(playParamsBean.getType())) {
                playParamsBean.setType("1");
            }
            String jm = new String(AESHelper.decrypt(Base64.decode(playParamsBean.getKey(), Base64.DEFAULT),
                    SignUtils.getKey(playParamsBean.getApp(), playParamsBean.getVid(), Integer.parseInt(playParamsBean.getType())).getBytes(),
                    SignUtils.getIV(playParamsBean.getVid()).getBytes()));
            getKeyTxt(localPath, jm, coursew);
        }
    }

    private String getOnlinePlayUrl(CourseWare coursew) {
        String localPath = FileUtil.getKeyPath(getActivity()) + SharedPrefHelper.getInstance().getUserId() + "_" + coursew.getsSubjectId() + "_" + coursew.getClassId() + "_" + coursew.getId() + "/online.m3u8";
        return localPath;
    }

    public void onBack() {
        if (mediaController != null) {
            if (mediaController.isLock) {
                Toast.makeText(getActivity(), "请先解除屏幕锁定", Toast.LENGTH_SHORT).show();
            } else {
//                if (sm != null) {
//                    sm.unregisterListener(listener);
//                }
//                stretch_flag = true;
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }


    /*********
     * 以下为同步相关
     ***********/
    public boolean isStart;
    private long reStartTime;

    @Override
    public void onPause() {
        super.onPause();
        if (activity.isServiceStart) {
            return;
        }
        activity.isCanPlay=false;
        isStart = true;
        reStartTime = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isLockPhone()){
            return;
        }
        if (activity.isServiceStart) {
            return;
        }
        if (mediaController != null && toExcise) {
            toExcise = false;
            isStart = false;
            activity.playNextVideo(false);
        } else { // if(mediaController!=null)
            if (isStart) {
                isStart = false;
//                playVedio(cw);
                activity.isCanPlay=true;
                mVideoView.seekTo(reStartTime);
//                mVideoView.start();
            }
        }
    }

//    如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
//    如果flag为false，表示目前未锁屏
    private boolean isLockPhone(){
        KeyguardManager mKeyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        boolean islock = mKeyguardManager.inKeyguardRestrictedInputMode();
        boolean isbackgroud=isBackground(activity);
        return islock || isbackgroud;
    }

    CwStudyLogDB cwStudyLogDB;
    private long startTime = 0; //开始时间
    CwStudyLog cwLog; //本次学习记录
    public String userId; //当前播放的用户
    public String createdTime; //什么时候开始听的
    private CourseWare cw; //当前播放的视频

    /**
     * 插入同步记录
     *
     * @param endTime 获取最后播放的时间点，单位为毫秒
     *                这是参数只有在用户拖动进度条的时候有用，其余的时间没什么用,直接设置为0即可
     */
    public synchronized void insertStudyLog(long endTime) {
        if (mVideoView == null) return;
        if (endTime == 0) {//所传参数为0  就获取当前最新的播放位置
            endTime = mVideoView.getCurrentPosition();
        }
        LogUtils.d("jumpTime方法 startTime=" + startTime);
        if (Math.abs(endTime - startTime) > 0 && Math.abs(endTime - startTime) < 200000) {//判断只有大于0的时候才保存，要不然保存也没有意义

            cwLog = cwStudyLogDB.query(userId, cw.getId(), cw.getClassId());//播放记录会时时变化，因此要提前判断一下
            if (cwLog == null) {
                cwLog = new CwStudyLog();
                cwLog.setCwid(cw.getId());
                cwLog.setCwName(cw.getName());
                cwLog.setCourseId(cw.getClassId());

//                cwLog.setStartTime(startTime);
                cwLog.setEndTime(endTime);
                cwLog.setStatus(1);
                cwLog.setUserId(userId);
                cwLog.setSubjectId(cw.getsSubjectId());
                cwLog.setTotalTime(mVideoView.getDuration());
                createdTime = DateUtils.getDateTime();
                cwLog.setCreatedTime(createdTime);
                cwLog.setLastUpdateTime(System.currentTimeMillis() + "");//DateUtil.getCurrentTimeInString()

                cwLog.setWatchedAt(Math.abs(endTime - startTime));
                cwLog.setNativeWatcheAt(Math.abs(endTime - startTime));
                cwStudyLogDB.insert(cwLog);
            } else {//不为null的话，说明库中已经存在了

                LogUtils.d("startTime=" + startTime);
                if (endTime == 0) {
                    endTime = mVideoView.getCurrentPosition();
                }
                LogUtils.d("endTime=" + endTime);
                cwLog.setWatchedAt(cwLog.getWatchedAt() + Math.abs(endTime - startTime));
                cwLog.setNativeWatcheAt(cwLog.getNativeWatcheAt() + Math.abs(endTime - startTime));
                cwLog.setLastUpdateTime(System.currentTimeMillis() + "");//DateUtil.getCurrentTimeInString()
                cwLog.setEndTime(endTime);
//                cwLog.setStartTime(startTime);
                cwStudyLogDB.update(cwLog);
            }
        }
        //跟新同步本地记录的上次时间
        startTime = endTime;
    }

    private void updataStartData() {
        cwLog = cwStudyLogDB.query(userId, cw.getId(), cw.getClassId());
        if (cwLog == null) {
            cwLog = new CwStudyLog();
            cwLog.setCwid(cw.getId());
            cwLog.setCwName(cw.getName());
            cwLog.setCourseId(cw.getClassId());
            cwLog.setStatus(1);
            cwLog.setUserId(userId);
            cwLog.setStartTime(startTime);
            cwLog.setSubjectId(cw.getsSubjectId());
            cwLog.setTotalTime(mVideoView.getDuration());
            cwLog.setCreatedTime(System.currentTimeMillis() + "");
            cwLog.setStartData(System.currentTimeMillis() + "");
            cwLog.setLastUpdateTime(System.currentTimeMillis() + "");
            cwStudyLogDB.insert(cwLog);
        } else {
            cwLog.setStartTime(startTime);
            cwLog.setLastUpdateTime(System.currentTimeMillis() + "");
            cwLog.setStartData(System.currentTimeMillis() + "");
            cwStudyLogDB.update(cwLog);
        }
    }

    /**
     * 同步上传接口
     */
    private void doSync() {
        activity.upLoadVideos();
    }


    private static final int MSG_STUDY_LOG = 8; //同步数据库

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaController != null) {  //页面销毁时remove  messages
            mediaController.mHandler.removeMessages(MSG_STUDY_LOG);
        }
        mVideoView.stopPlayback();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity() == null || mediaController == null) {
            return;
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 改变布局
            getActivity().getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int ScreenW = dm.widthPixels;
            int ScreenH = dm.heightPixels;

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, SharedPrefHelper.getInstance().getMiniScreenHight());
            mediaController.setLayoutParams(layoutParams);

            if (activity.isFromFree) {
                mediaController.setGoneAllP();
            } else {
                mediaController.setGone();
            }
            // centerLayout.setLayoutParams(p);
            CenterLayout.LayoutParams pp = new CenterLayout.LayoutParams(
                    ScreenW, SharedPrefHelper.getInstance().getMiniScreenHight(), 0, 0);
            mVideoView.setLayoutParams(pp);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 改变布局
            //去掉Activity上面的状态栏
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int ScreenW = dm.widthPixels;
            int ScreenH = dm.heightPixels;

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            mediaController.setLayoutParams(layoutParams);

            if (activity.isFromFree) {
                mediaController.setGoneAllL();
            } else {
                mediaController.setVisible();
            }
            SharedPrefHelper.getInstance().setFullScreenHight(ScreenH);
            CenterLayout.LayoutParams pp = new CenterLayout.LayoutParams(
                    ScreenW, ScreenH, 0, 0);
            mVideoView.setLayoutParams(pp);
        }
    }

    public void reSetIsCollect(boolean isCollect) {
        mediaController.reSetIsCollect(isCollect);
    }

//    private boolean sensor_flag = true; // 重力感应的
//    private boolean stretch_flag = true; // 点击的
//    private SensorManager sm;
//    private OrientationSensorListener listener;
//    private Sensor sensor;
//
//    private SensorManager sm1;
//    private Sensor sensor1;
//    private OrientationSensorListener2 listener1;

//    private void initSensor() {
//
//        width = (int) DensityUtil.getWidthInPx(getActivity());
//        height = (int) DensityUtil.getHeightInPx(getActivity());
//
//        // 系统的重力感应  注册监听两个的目的就是其中一个取消了，不影响另外一个
//        sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        listener = new OrientationSensorListener(handler);
//        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
//
//        // 系统的重力感应2
//        sm1 = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        sensor1 = sm1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        listener1 = new OrientationSensorListener2();
//        sm1.registerListener(listener1, sensor1, SensorManager.SENSOR_DELAY_UI);
//    }

//    /**
//     * 感应横竖屏变化
//     */
//    public class OrientationSensorListener implements SensorEventListener {
//        private static final int _DATA_X = 0;
//        private static final int _DATA_Y = 1;
//        private static final int _DATA_Z = 2;
//
//        public static final int ORIENTATION_UNKNOWN = -1;
//
//        private Handler rotateHandler;
//
//        public OrientationSensorListener(Handler handler) {
//            rotateHandler = handler;
//        }
//
//        public void onAccuracyChanged(Sensor arg0, int arg1) {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void onSensorChanged(SensorEvent event) {
//
//            if (sensor_flag != stretch_flag) // 说明了什么？如果两个监听不一致，则说明目前的方向不一致，则继续往下监听，否则没有必要
//            {
//                float[] values = event.values;
//                int orientation = ORIENTATION_UNKNOWN;
//                float X = -values[_DATA_X];
//                float Y = -values[_DATA_Y];
//                float Z = -values[_DATA_Z];
//                float magnitude = X * X + Y * Y;
//                // Don't trust the angle if the magnitude is small compared to
//                // the y value
//                if (magnitude * 4 >= Z * Z) {
//                    //
//                    float OneEightyOverPi = 57.29577957855f;
//                    float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
//                    orientation = 90 - (int) Math.round(angle);
//                    // normalize to 0 - 359 range
//                    while (orientation >= 360) {
//                        orientation -= 360;
//                    }
//                    while (orientation < 0) {
//                        orientation += 360;
//                    }
//                }
//                if (rotateHandler != null) {
//                    rotateHandler.obtainMessage(888, orientation, 0)
//                            .sendToTarget();
//                }
//
//            } else {
//                if (getActivity() != null) {
//                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 感应横竖屏变化，此监听的目的
//     *
//     * @author xunice
//     */
//    public class OrientationSensorListener2 implements SensorEventListener {
//        private static final int _DATA_X = 0;
//        private static final int _DATA_Y = 1;
//        private static final int _DATA_Z = 2;
//
//        public static final int ORIENTATION_UNKNOWN = -1;
//
//        public void onAccuracyChanged(Sensor arg0, int arg1) {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void onSensorChanged(SensorEvent event) {
//
//            float[] values = event.values;
//
//            int orientation = ORIENTATION_UNKNOWN;
//            float X = -values[_DATA_X];
//            float Y = -values[_DATA_Y];
//            float Z = -values[_DATA_Z];
//
//            /**
//             *
//             */
//            float magnitude = X * X + Y * Y;
//            // Don't trust the angle if the magnitude is small compared to the y
//            // value
//            if (magnitude * 4 >= Z * Z) {
//                //
//                float OneEightyOverPi = 57.29577957855f;
//                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
//                orientation = 90 - (int) Math.round(angle);
//                // normalize to 0 - 359 range
//                while (orientation >= 360) {
//                    orientation -= 360;
//                }
//                while (orientation < 0) {
//                    orientation += 360;
//                }
//            }
//
//            if (orientation > 225 && orientation < 315) { //
//                sensor_flag = false;
//            } else if ((orientation > 315 && orientation < 360)
//                    || (orientation > 0 && orientation < 45)) { //
//                sensor_flag = true;
//            }
//
//            if (stretch_flag == sensor_flag) { //
//                if (mediaController != null && !mediaController.isLock) {
//                    sm.registerListener(listener, sensor,
//                            SensorManager.SENSOR_DELAY_UI);
//                } else if ((mediaController != null && mediaController.isLock)) {
//                    sm.unregisterListener(listener);
//                }
//            }
//        }
//    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param activity
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public int getDisplayRotation(Activity activity) {
        if (activity == null)
            return 0;

        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    /**
     * 获取并保存Key到文件中
     *
     * @param
     */
    public void getKeyTxt(String m3u8Path, String keyText, CourseWare coursew) {

        try {
            File file = new File(getPath(coursew) + "keyText.txt");
            File file1 = new File(getPath(coursew));
            if (!file.exists()) {
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(keyText);
                fileWriter.flush();
                fileWriter.close();
            }
            String keyUrl = null;
            FileOutputStream fos = null;
            File m3u8File = new File(m3u8Path);
            FileReader fileReader = new FileReader(m3u8File);
            BufferedReader reader11 = new BufferedReader(fileReader);
            String line1 = null;
            StringBuffer stringBuffer1 = new StringBuffer();
            String line_copy = null;
            boolean isNeedKey = false;//是否需要进行加解密
            while ((line1 = reader11.readLine()) != null) {
                line_copy = line1;
                if (line1.contains("EXT-X-KEY"))
                    isNeedKey = true;
                if (line1.contains("EXT-X-KEY") && !line1.contains(Constants.M3U8_KEY_SUB)) {
                    //如果当前视频需要进行解密，则修改m3u8文件中EXT-X-KEY对应的值指向本地key文件，来标示需要进行解密
                    String[] keyPart = line1.split(",");
                    String keyUrll = keyPart[1].split("URI=")[1].trim();
                    keyUrl = keyUrll.substring(1, keyUrll.length() - 1);
                    line_copy = keyPart[0] + Constants.M3U8_KEY_SUB + getKeyPath(coursew) + "keyText.txt\"," + keyPart[2];
//                    line_copy = keyPart[0] + ",URI=\""+keyText+"\"," + keyPart[2];
                } else if (line1.contains(".ts") && !line1.contains("http:") && isNeedKey) {
//                    line_copy = line_copy;//补全ts的网络路径
                }
                stringBuffer1.append(line_copy);
                stringBuffer1.append("\r\n");
            }
            reader11.close();
            FileWriter writer = new FileWriter(m3u8File, false);
            BufferedWriter writer2 = new BufferedWriter(writer);
            writer2.write(stringBuffer1.toString());
            writer2.flush();
            writer2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getPath(CourseWare courseWare) {

        String userId = SharedPrefHelper.getInstance().getUserId();
        String desPath = FileUtil.getKeyPath(getActivity());

        return desPath + userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";

    }

    private String getKeyPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();

        return userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                pb.setVisibility(View.VISIBLE);
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mediaController.setIsClickable(false);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                pb.setVisibility(View.GONE);
                mediaController.setIsClickable(true);
                if (!isStart) {
                    mVideoView.start();
                }
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
//                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mediaController != null) {
            mediaController.playError=false;
            mediaController.errorTime=0;
            mediaController.isSeekEnd=false;
            mediaController.updatePausePlay();
            mediaController.mHandler.sendEmptyMessageDelayed(MSG_STUDY_LOG, 10000);
        }

        if (isPlayMp3) {
            mp.setPlaybackSpeed(1.0f);
        } else {
            mp.setPlaybackSpeed(SharedPrefHelper.getInstance().getPlaySpeed());
        }
        mp.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);
        if(activity.seekTime!=0){
            mp.seekTo(activity.seekTime);
            activity.seekTime=0;
        }else{
            mp.seekTo(startTime);
        }
        if(!activity.isFromFree){
            updataStartData();
            insertStudyLog(startTime);
        }
    }

    private boolean toExcise;

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mVideoView.start();
    }

    private int errorTimes;
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(errorTimes<2){   //播放出错的处理逻辑
            if(mediaController!=null){
                if(mediaController.playError && !mediaController.isSeekEnd){
                    mediaController.playError=false;
                    mp.seekTo(mediaController.errorTime);
                    errorTimes++;
                    return;
                }
            }
        }

        activity.isServiceStart = false;
        if(isPlayMp3){
            PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();
            boolean isback = isBackground(getActivity());

            if (isback || !isScreenOn) {
                activity.playNextVideo(true);
            } else {
                activity.playNextVideo(false);
            }
        }else{
            if (!TextUtils.isEmpty(cw.getPaperId()) && !toExcise && NetWorkUtils.isConnected(getActivity())) {    //调到随堂练习
                toExcise = true;
                MobclickAgent.onEvent(getActivity(), Statistics.COURSE_FULLSCREEN_EXERCISE);
            } else {
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pm.isScreenOn();
                boolean isback = isBackground(getActivity());

                if (isback || !isScreenOn) {
                    activity.playNextVideo(true);
                } else {
                    activity.playNextVideo(false);
                }
            }
        }
    }

    private boolean isBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        activity.analysisDNS(cw);
        return false;
    }
}
