package com.me.fanyin.zbme.views.course.play.SystemPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.DateUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwStudyLog;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.PlayParamsBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.notes.NotesActivity;
import com.me.fanyin.zbme.views.course.play.SystemPlayer.widget.MediaController;
import com.me.fanyin.zbme.views.course.play.SystemPlayer.widget.VideoView;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.db.PlayParamsDB;
import com.me.fanyin.zbme.views.course.play.utils.AESHelper;
import com.me.fanyin.zbme.views.course.play.utils.SignUtils;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.course.play.widget.PlayService;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.yunqing.core.db.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.utils.StringUtils;

/**
 * Created by dell on 2017/4/20.
 */

public class PlayerActivity extends BaseMvpActivity<PlayerView, PlayerPersenter> implements PlayerView {

    @BindView(R.id.videoview)
    VideoView videoview;
    private static final int DATA_POST = 20001;//数据同步

    private String path;
    private CourseWare cw;
    CwStudyLog cwLog; //本次学习记录
    public String createdTime; //什么时候开始听的
    CwStudyLogDB cwStudyLogDB;
    private PlayParamsDB playParamsDB;
    private String userId;
    public long startTime = 0; //开始时间
    private MediaController controller;
    public boolean isToAskNote;
    public boolean isServiceStart;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DATA_POST:
                    long endTime = (long) msg.obj;
                    insertStudyLog(endTime);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        initVideo();
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(PlayerActivity.this);
    }

    public void initView() {
        cwStudyLogDB = new CwStudyLogDB();
        playParamsDB = new PlayParamsDB(this);
        userId = SharedPrefHelper.getInstance().getUserId();

//        cw=(CourseWare)getIntent().getSerializableExtra("cwBean");
        cw = new CourseWare();
        cw.setName("asdf");
        cw.setsSubjectId("23");
        cw.setClassId("23");
        cw.setId("23");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.player_system;
    }

    private void initVideo() {
        CwStudyLog logstart = cwStudyLogDB.query(userId, cw.getId(), cw.getClassId());
        if (!TextUtils.isEmpty(cw.getStartTime())) {
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
        initM3u8(cw);
        path=DownloadManager.getInstance().getM3u8Path(cw);
        playVedio();
    }

    public void playVedio() {
        isServiceStart=false;
        path = Environment.getExternalStorageDirectory() + "/666.mp4";
        if(controller == null){
            controller = new MediaController(this, videoview, handler);
        }
        controller.setCourseWare(cw, true);
        videoview.setMediaController(controller);
        videoview.setVideoPath(path);
        videoview.seekTo(20000);
        videoview.start();
    }

    private void initM3u8(CourseWare coursew) {
        String localPath = DownloadManager.getInstance().getM3u8Path(coursew);
        PlayParamsBean playParamsBean = playParamsDB.getParamById(SharedPrefHelper.getInstance().getUserId(), coursew.getId());
        String jm = new String(AESHelper.decrypt(Base64.decode(playParamsBean.getKey(), Base64.DEFAULT),
                SignUtils.getKey(playParamsBean.getApp(), playParamsBean.getVid(), Integer.parseInt(playParamsBean.getType())).getBytes(),
                SignUtils.getIV(playParamsBean.getVid()).getBytes()));
        mPresenter.getKeyTxt("", localPath, jm, coursew);
    }

    public synchronized void insertStudyLog(long endTime) {
        if (videoview == null) return;
        if (endTime == 0) {//所传参数为0  就获取当前最新的播放位置
            endTime = videoview.getCurrentPosition();
        }
        if (Math.abs(endTime - startTime) > 0 && Math.abs(endTime - startTime) < 200000) {//判断只有大于0的时候才保存，要不然保存也没有意义

            cwLog = cwStudyLogDB.query(userId, cw.getId(), cw.getClassId());//播放记录会时时变化，因此要提前判断一下
            if (cwLog == null) {
                cwLog = new CwStudyLog();
                cwLog.setCwid(cw.getId());
                cwLog.setCwName(cw.getName());
                cwLog.setCourseId(cw.getClassId());

                cwLog.setStartTime(startTime);
                cwLog.setEndTime(endTime);
                cwLog.setStatus(1);
                cwLog.setUserId(userId);
                cwLog.setSubjectId(cw.getsSubjectId());
                cwLog.setTotalTime(videoview.getDuration());
                createdTime = DateUtils.getDateTime();
                cwLog.setCreatedTime(createdTime);
                cwLog.setLastUpdateTime(System.currentTimeMillis() + "");//DateUtil.getCurrentTimeInString()

                cwLog.setWatchedAt(Math.abs(endTime - startTime));
                cwLog.setNativeWatcheAt(Math.abs(endTime - startTime));
                cwStudyLogDB.insert(cwLog);
            } else {//不为null的话，说明库中已经存在了

                LogUtils.d("startTime=" + startTime);
                if (endTime == 0) {
                    endTime = videoview.getCurrentPosition();
                }
                LogUtils.d("endTime=" + endTime);
                cwLog.setWatchedAt(cwLog.getWatchedAt() + Math.abs(endTime - startTime));
                cwLog.setNativeWatcheAt(cwLog.getNativeWatcheAt() + Math.abs(endTime - startTime));
                cwLog.setLastUpdateTime(System.currentTimeMillis() + "");//DateUtil.getCurrentTimeInString()
                cwLog.setEndTime(endTime);
                cwLog.setStartTime(startTime);
                cwStudyLogDB.update(cwLog);
            }
        }
        //跟新同步本地记录的上次时间
        startTime = endTime;
    }

    public void gotoAsk(Object data) {

        String result = data.toString();
        com.alibaba.fastjson.JSONObject object = JSON.parseObject(result);
        String hanConId = object.getString("id");

    }

    public void gotoNote(Object data) {

        String result = data.toString();
        com.alibaba.fastjson.JSONObject object = JSON.parseObject(result);
        String hanConId = object.getString("id");

        NoteClassDetail noteClassDetail = new NoteClassDetail();
        noteClassDetail.setHanConId(hanConId);
        noteClassDetail.setSubjectId("");
        noteClassDetail.setsSubjectId(cw.getsSubjectId());
        noteClassDetail.setCourseId(cw.getClassId());
        noteClassDetail.setLectrueId(cw.getId());
        noteClassDetail.setCoursewareTime(getCourseWareTime());

        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("from", "3");
        intent.putExtra("noteClassDetail", noteClassDetail);
        startActivity(intent);
    }

    public String getCourseWareTime() {
        String currentime = "";
        if (videoview != null) {
            long time = videoview.getCurrentPosition();
            currentime = StringUtils.generateTime(time);
        }
        return currentime;
    }

    Intent playService;

    public void startPlayService(CourseWare courseWare) {
        if (videoview.isPlaying()) {
//            player.mVideoView.stopPlayback();
            videoview.pause();
//            pb.setVisibility(View.VISIBLE);
        }
        videoview.setBackgroundResource(R.mipmap.audioplay_bg);
        playService = new Intent(this, PlayService.class);
        playService.putExtra("cw", courseWare);
        startService(playService);
    }

    public void seekTo(long time) {
        if (videoview!= null) {
            videoview.seekTo(time);
        }
    }

    @Override
    public void onDestroy() {
        if(playService!=null){
            stopService(playService);
        }
        super.onDestroy();
    }

    public void collectCw(CourseWare cw) {
        mPresenter.collectCw(cw);
    }

    public void delCollectCw(CourseWare cw) {
        mPresenter.delCollectCw(cw);
    }

    @Override
    public void collectCwResult() { //收藏成功
        controller.reSetIsCollect(true);
    }

    @Override
    public void delCwResult() { //取消收藏
        controller.reSetIsCollect(false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showError(String message) {

    }
}
