package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwStudyLog;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.yunqing.core.db.utils.LogUtils;

/**
 * Created by dell on 2017/5/18.
 */

public class PlayService extends Service {

    private DownloadManager downloadManager;
    private MediaPlayer mediaPlayer;
    private CourseWare cw;
    private long startTime;
    private String userId;
    private static final int MSG_STUDY_LOG = 8;//同步本地
    private static final int MSG_SEEKBAR = 0;//控制进度条
    private String path;
    private long seekTime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cwStudyLogDB = new CwStudyLogDB();
        userId = SharedPrefHelper.getInstance().getUserId();
        mediaPlayer = new MediaPlayer();
        downloadManager = DownloadManager.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            cw = (CourseWare) intent.getSerializableExtra("cw");
            seekTime = intent.getLongExtra("startime", 0);
            if (cw != null) {
                CwStudyLog logstart = cwStudyLogDB.query(userId, cw.getId(), cw.getClassId());
                if (!TextUtils.isEmpty(cw.getStartTime())) {
//                startTime = Integer.parseInt(cw.getStartTime()) * 1000;
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
                if (seekTime != 0) {
                    startTime = seekTime;
                }
                boolean flag = downloadManager.isCWDownlaoded(cw);
                if (flag) {
                    path = downloadManager.getMp3Path(cw);
                } else {
                    path = cw.getMobileVideoMP3();
                }
//                String path = Environment.getExternalStorageDirectory() + "/666.mp4";
                mediaPlayer.setDataSource(path);
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Toast.makeText(getApplicationContext(), "播放失败:" + what, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                    }
                });
                //网络上的资源 准备需要花费一定的时间
                //异步的准备
                mediaPlayer.prepareAsync();
                //注册一个准备完毕的监听器
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.seekTo((int) startTime);
                        mediaPlayer.start();
                    }
                });

            }
        } catch (Exception e) {

        }

        return super.onStartCommand(intent, flags, startId);
    }


    CwStudyLog cwLog; //本次学习记录
    CwStudyLogDB cwStudyLogDB;

    public synchronized void insertStudyLog(long endTime) {
        if (endTime == 0) {
            endTime = mediaPlayer.getCurrentPosition();
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
                cwLog.setTotalTime(mediaPlayer.getDuration());
                cwLog.setLastUpdateTime(System.currentTimeMillis() + "");//DateUtil.getCurrentTimeInString()

                cwLog.setWatchedAt(Math.abs(endTime - startTime));
                cwLog.setNativeWatcheAt(Math.abs(endTime - startTime));
                cwStudyLogDB.insert(cwLog);
            } else {//不为null的话，说明库中已经存在了

                LogUtils.d("startTime=" + startTime);
                if (endTime == 0) {
                    endTime = mediaPlayer.getCurrentPosition();
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

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}
