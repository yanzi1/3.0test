package com.me.fanyin.zbme.views.course.play.audition;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.me.data.event.PlayErrorEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.auditionplay.MediaController;
import io.vov.vitamio.widget.auditionplay.VideoView;

/**
 * Created by dell on 2016/6/16.
 */
public class AuditionPlayActivity extends BaseMvpActivity<AuditionPlayView, AuditionPlayPersenter> implements MediaPlayer.OnInfoListener, AuditionPlayView {

    @BindView(R.id.surface_view)
    VideoView videoView;
    @BindView(R.id.probar)
    ProgressBar pb;

    private MediaController mediaController;
    private String paths;
    private CourseWare cw;
    private Uri uri;
    private boolean seekto;
    private static final int SEEK_TO = 166;
    private static final int ERROR = 44;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEEK_TO:
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    int ScreenW = dm.widthPixels;
                    int ScreenH = dm.heightPixels;
                    SharedPrefHelper.getInstance().setFullScreenHight(ScreenH);
                    CenterLayout.LayoutParams pp = new CenterLayout.LayoutParams(
                            ScreenW, ScreenH, 0, 0);
                    videoView.setLayoutParams(pp);
                    if (isStart) {
                        videoView.seekTo(reStartTime);
                        reStartTime=0;
                        isStart=false;
                    } else {
                        videoView.seekTo(startTime);
                    }
                    seekto = true;
                    break;
                case 66:
                    cw = (CourseWare) msg.obj;
                    startPlay(cw);
                    break;
                case ERROR:
                    String message=(String)msg.obj;
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EventBus.getDefault().register(this);
        Vitamio.isInitialized(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.play_audition;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(AuditionPlayActivity.this);
    }

    @Override
    public void initView() {
        cw = (CourseWare) getIntent().getSerializableExtra("cw");
        if (!TextUtils.isEmpty(cw.getStartTime())) {
            startTime= StringUtil.getLongMills(cw.getStartTime());
//            startTime = Long.parseLong(cw.getStartTime()) * 1000;
        }
        mPresenter.getData();
    }

    public void retryPlay(){
        if(cw!=null){
            startPlay(cw);
        }
    }

    @Subscribe
    public void onEventMainThread(PlayErrorEvent event) {
        pb.setVisibility(View.GONE);
        videoView.setBackgroundResource(R.color.text_color_primary_dark);
        if (mediaController != null) {
            mediaController.showErrorLayout();
        }
    }

    //paths = "http://appdownload.dongao.com/2014zs/ck/zzf/jc/ydb/14zsjc_zzfjc_ck_061_qy_yd_2/upload/media/m3u8_10/video.m3u8";
    @Override
    public void initData() {


    }

    @Override
    public void startPlay(CourseWare courseware) {
        if (isNull) {
            return;
        }
        videoView.setBackgroundResource(R.color.transparent);
//        paths=courseware.getMobileVideoUrl();
        paths = getPlayUrl(courseware);
//        paths = Environment.getExternalStorageDirectory() + "/666.mp4";
        if (paths == "") {
            Toast.makeText(this, "视频数据错误", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (mediaController == null) {
                mediaController = new MediaController(this, videoView);
            }
            videoView.setMediaController(mediaController);
            videoView.setOnInfoListener(this);
            mediaController.setCourseWare(courseware);
            uri = Uri.parse(paths);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
        }
    }

    private String getPlayUrl(CourseWare coursew) {
        String localPath = FileUtil.getKeyPath(this)+ SharedPrefHelper.getInstance().getUserId() + "_sanning_" + coursew.getId() + "/online.m3u8";
        return localPath;
    }

    @Override
    public void onClick(View v) {

    }

    private boolean isStart;
    private long reStartTime;
    private long startTime;

    @Override
    protected void onPause() {
        super.onPause();
        isStart = true;
        seekto = false;
        if(reStartTime==0){
            reStartTime = videoView.getCurrentPosition();
        }
        videoView.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStart) {
            startPlay(cw);
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    mediaController.setIsClickable(false);
                    pb.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mediaController.setIsClickable(true);
                if (!seekto) {
                    handler.sendEmptyMessage(SEEK_TO);
                }
                videoView.start();
                pb.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                break;
        }
        return true;
    }

    private boolean isNull;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.stopPlayback();
        }
        isNull = true;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public CourseWare getCw() {
        return cw;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
        if (mediaController != null) {
            if (mediaController.isLock) {
                Toast.makeText(this, "请先解除屏幕锁定", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void showError(String message) {
        Message msg =Message.obtain();
        msg.obj=message;
        msg.what=ERROR;
        handler.sendMessage(msg);
    }

    @Override
    public void playError(String msg) {
        Toast.makeText(AuditionPlayActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
