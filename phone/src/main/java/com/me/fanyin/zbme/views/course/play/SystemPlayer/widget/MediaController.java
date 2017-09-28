/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2013 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.me.fanyin.zbme.views.course.play.SystemPlayer.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.NetWorkUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.MyCollection;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.SystemPlayer.PlayerActivity;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.db.MyCollectionDB;
import com.me.fanyin.zbme.views.course.play.utils.MenuRightAnimations;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.course.play.widget.WVJBWebViewClient;
import com.me.fanyin.zbme.views.download.DownloadManager;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.OutlineTextView;

//import com.dongao.app.dongaoacc.R;

/**
 * A view containing controls for a MediaPlayer. Typically contains the buttons
 * like "Play/Pause" and a progress slider. It takes care of synchronizing the
 * controls with the state of the MediaPlayer.
 * <p/>
 * The way to use this class is to a) instantiate it programatically or b)
 * create it in your xml layout.
 * <p/>
 * a) The MediaController will create a default set of controls and put them in
 * a window floating above your application. Specifically, the controls will
 * float above the view specified with setAnchorView(). By default, the window
 * will disappear if left idle for three seconds and reappear when the user
 * touches the anchor view. To customize the MediaController's style, layout and
 * controls you should extend MediaController and override the {#link
 * {@link #makeControllerView()} method.
 * <p/>
 * b) The MediaController is a FrameLayout, you can put it in your layout xml
 * and get it through {@link #findViewById(int)}.
 * <p/>
 * NOTES: In each way, if you want customize the MediaController, the SeekBar's
 * id must be mediacontroller_progress, the Play/Pause's must be
 * mediacontroller_pause, current time's must be mediacontroller_time_current,
 * total time's must be mediacontroller_time_total, file name's must be
 * mediacontroller_file_name. And your resources must have a pause_button
 * drawable and a play_button drawable.
 * <p/>
 * Functions like show() and hide() have no effect when MediaController is
 * created in an xml layout.
 */
public class MediaController extends FrameLayout implements View.OnClickListener{
    private static final int sDefaultTimeout = 5000;
    private static final int TIME_STUDY_LOG = 1000 * 10; // 10s同步一次
    private static final int TIME_STUDY_LOG_SYNC = 1000 * 60 * 5;//先设为5分钟
    private static final int DIALOG_OPEN = 1000;//1S
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private static final int PLAY_ISTART = 131313;
    private static final int MSG_STUDY_LOG = 8;
    private static final int EXAM_DOWNLOAD = 13;    //下载随堂练习
    private static final int POST_STUDYLOG = 14;  //上传数据
    private MediaPlayerControl mPlayer;
    private Activity mContext;
    //    public PopupWindow mWindow;
    private ViewGroup viewGroup;
    private int mAnimStyle;
    private View mAnchor;
    private View mRoot;
    private SeekBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private TextView mFileName;
    private OutlineTextView mInfoView;
    private String mTitle;
    private long mDuration;
    private boolean mShowing;
    private boolean mDragging;
    private boolean mInstantSeeking = false;
    private boolean mFromXml = false;

    @Override
    public boolean showContextMenu() {
        return super.showContextMenu();
    }

    private ImageButton mPauseButton;
    private OnShownListener mShownListener;
    private OnHiddenListener mHiddenListener;

    private RelativeLayout controller;
    //    private LinearLayout rightlist;
    //    private CommonGestures mGestureDetector;
    private ImageView back;
    private ImageButton mini;
    private List<CourseWare> list = new ArrayList<CourseWare>();
    public CourseWare courseWare;
    /**
     * 同步 xunice
     **/
    private PlayerActivity playActivity;
    private VideoView videoView;
    private long endTime = 0;

    private View bottom, top;
    private Handler playerHander;

    private static final int DATA_POST = 20001;//同步数据库
    private static final int DATA_POST_STARTTIME = 30001;//同步开始学习时间
    private CwStudyLogDB studyDB;

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if (!mDragging && mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        updatePausePlay();
                    }
                    break;
                /**同步 xunice**/
                case MSG_STUDY_LOG:
                    callLecture(mPlayer.getCurrentPosition() / 1000);
//                    System.out.println("msg_study_log 方法每隔10s走一次");
                    Message dataPost = Message.obtain();
                    dataPost.what = DATA_POST;
                    dataPost.obj = mPlayer.getCurrentPosition();
                    playerHander.sendMessage(dataPost);
                    mHandler.sendEmptyMessageDelayed(MSG_STUDY_LOG, TIME_STUDY_LOG);
                    break;
                case POST_STUDYLOG:
                    Message postlog = Message.obtain();
                    postlog.what = POST_STUDYLOG;
                    playerHander.sendMessage(postlog);
                    mHandler.sendEmptyMessageDelayed(POST_STUDYLOG, TIME_STUDY_LOG_SYNC);//每5分钟上传一次数据
                    break;
            }
        }
    };
    private OnClickListener mPauseListener = new OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    public void setPausePlay() {
        mPauseButton.setImageResource(getResources().getIdentifier(
                "mediacontroller_pause", "drawable",
                mContext.getPackageName()));
    }

    private OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {

                endTime = mPlayer.getCurrentPosition();
                mDragging = true;
                show(3600000);
                mHandler.removeMessages(SHOW_PROGRESS);
                if (mInstantSeeking)
                    mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                if (mInfoView != null) {
                    mInfoView.setText("");
                    mInfoView.setVisibility(View.VISIBLE);
                }
                playerHander.sendEmptyMessage(PLAY_ISTART);//按Home键播放暂停 控制isStart逻辑
        }

        public void onProgressChanged(SeekBar bar, int progress,
                                      boolean fromuser) {
                if (!fromuser)
                    return;

                long newposition = (mDuration * progress) / 1000;
                String time = StringUtils.generateTime(newposition);
                if (mInstantSeeking)
                    mPlayer.seekTo(newposition);
                if (mInfoView != null)
                    mInfoView.setText(time);
                if (mCurrentTime != null)
                    mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
                if (!mInstantSeeking) {
                    mPlayer.seekTo((mDuration * bar.getProgress()) / 1000);
                }

                if (mInfoView != null) {
                    mInfoView.setText("");
                    mInfoView.setVisibility(View.GONE);
                }
                show(sDefaultTimeout);
                mHandler.removeMessages(SHOW_PROGRESS);
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                mDragging = false;
                mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);

                /****xunice***/
                Message dataPost = Message.obtain();
                dataPost.what = DATA_POST;
                dataPost.obj = endTime;
                playerHander.sendMessage(dataPost);

                callLecture(mPlayer.getCurrentPosition() / 1000);
        }
    };

    public MediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = this;
        mFromXml = true;
        initController(context);
    }

    /**
     * 同步 xunice
     **/
    public MediaController(Context context, VideoView mVideoView, Handler handler) {
        super(context);
        videoView = mVideoView;
        playerHander = handler;
        if (!mFromXml && initController(context))
            initFloatingWindow();
        initAnimation();
    }

    //    private Animation mAnimSlideInRight;
    private Animation mAnimSlideInTop;
    private Animation mAnimSlideInBottom;
    private Animation mAnimSlideOutTop;
    private Animation mAnimSlideOutBottom;
//    private Animation mAnimSlideOutRight;

    private void initAnimation() {
        mAnimSlideInTop = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_top);
        mAnimSlideOutTop = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_top);

//        mAnimSlideOutRight = AnimationUtils.loadAnimation(mContext,
//                R.anim.slide_out_right);
//        mAnimSlideInRight = AnimationUtils.loadAnimation(mContext,
//                R.anim.slide_in_right);

        mAnimSlideInBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_bottom);
        mAnimSlideInBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (lock != null) {
                    if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        lock.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimSlideOutBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_bottom);
        mAnimSlideOutBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                controller.setVisibility(View.GONE);
//				mHandler.removeMessages(MSG_HIDE_SYSTEM_UI);
                if (lock != null) {
                    if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        lock.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private boolean initController(Context context) {
        mContext = (Activity) context;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        return true;
    }

    @Override
    public void onFinishInflate() {
        if (mRoot != null)
            initControllerView(mRoot);
        super.onFinishInflate();
    }

    private void initFloatingWindow() {
//        mWindow = new PopupWindow(mContext);
//        mWindow.setFocusable(true);
//        mWindow.setBackgroundDrawable(new BitmapDrawable());
//        mWindow.setOutsideTouchable(true);
        mAnimStyle = android.R.style.Animation;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setWindowLayoutType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
//				mAnchor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                Method setWindowLayoutType = PopupWindow.class.getMethod(
                        "setWindowLayoutType", new Class[]{int.class});
//                setWindowLayoutType
//                        .invoke(mWindow,
//                                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
            } catch (Exception e) {
//                Log.e("setWindowLayoutType", e);
            }
        }
    }

    /**
     * Set the view that acts as the anchor for the control view. This can for
     * example be a VideoView, or your Activity's main_drawer_menu view.
     *
     * @param view The view to which to anchor the controller when it is visible.
     */
    public void setAnchorView(View view) {
        mAnchor = view;
        if (!mFromXml) {
            removeAllViews();
            mRoot = makeControllerView();
            if (viewGroup == null) {
                viewGroup = (ViewGroup) mContext.findViewById(Window.ID_ANDROID_CONTENT);
                viewGroup.setFocusable(true);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                viewGroup.setLayoutParams(params);
                viewGroup.addView(mRoot);
            }
//            mWindow.setContentView(mRoot);
//            mWindow.setWidth(LayoutParams.MATCH_PARENT);
//            mWindow.setHeight(LayoutParams.MATCH_PARENT);
        }
        initControllerView(mRoot);
    }

    /**
     * Create the view that holds the widgets that control playback. Derived
     * classes can override this to create their own.
     *
     * @return The controller view.
     */
    protected View makeControllerView() {
        return ((LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.mediacontroller, this);
    }

    private WebView mWebView;
    private RelativeLayout ll_webView;

    private RelativeLayout layer;
    private LinearLayout mediacontroller_top_right;
    private ImageButton bt_offline, bt_chapter, bt_lectrue;
    private ImageView lecture_web;

    private LinearLayout bottom_three;
    //    private RelativeLayout speed;
    private TextView chooseSpeed;
    private TextView tv_lecture;

    private MyCollectionDB collectionDB;
    private MyCollection collection;
    private TextView excise;
    public ImageView lock, listen;
    private TextView progress_play, progressAll;
    private LinearLayout ll_progress;
    private ImageButton nextPlay;
    private LinearLayout playErrorLayout;

    public void showErrorLayout() {
        playErrorLayout.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("deprecation")
    private void initControllerView(View v) {
        studyDB = new CwStudyLogDB();//初始化学习记录表
        collectionDB = new MyCollectionDB();
        playActivity = (PlayerActivity) mContext;

        playErrorLayout = (LinearLayout) v.findViewById(R.id.play_error_ll);
        playErrorLayout.setOnClickListener(this);

        progress_play = (TextView) v.findViewById(R.id.tv_progress);
        progressAll = (TextView) v.findViewById(R.id.tv_progressall);
        ll_progress = (LinearLayout) v.findViewById(R.id.ll_progress);

        lock = (ImageView) v.findViewById(R.id.img_lock);
        lock.setOnClickListener(this);
        listen = (ImageView) v.findViewById(R.id.img_listen);
        listen.setOnClickListener(this);

        nextPlay = (ImageButton) v.findViewById(R.id.mediacontroller_play_next);
        nextPlay.setOnClickListener(this);

        tv_lecture = (TextView) v.findViewById(R.id.mediacontroller_lecture);
        tv_lecture.setOnClickListener(this);

        excise = (TextView) v.findViewById(R.id.tv_excise);
        excise.setText("本地");
        excise.setClickable(false);

        chooseSpeed = (TextView) v.findViewById(R.id.mediacontroller_speed);
        chooseSpeed.setText("1.0x");
        chooseSpeed.setClickable(false);

        controller = (RelativeLayout) v.findViewById(R.id.layout_control);
        controller.setOnClickListener(this);
        layer = (RelativeLayout) v.findViewById(R.id.layer);
        mediacontroller_top_right = (LinearLayout) v.findViewById(R.id.mediacontroller_top_right);
        bottom_three = (LinearLayout) v.findViewById(R.id.ll_bottom_three);

        bt_offline = (ImageButton) v.findViewById(R.id.img_download);
        bt_chapter = (ImageButton) v.findViewById(R.id.img_chapter);
        bt_lectrue = (ImageButton) v.findViewById(R.id.img_lecture);
        bt_offline.setOnClickListener(this);
        bt_chapter.setOnClickListener(this);
        bt_lectrue.setOnClickListener(this);

        bottom = v.findViewById(R.id.rl_bottom);
        top = v.findViewById(R.id.linear_top);
        ll_webView = (RelativeLayout) v.findViewById(R.id.ll_webview);

        controller.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });

        lecture_web = (ImageView) v.findViewById(R.id.lecture_web);
        lecture_web.setOnClickListener(this);
        mWebView = (WebView) v.findViewById(R.id.webview);
        initWebView();

        if (!TextUtils.isEmpty(webUrl)) {
            mWebView.loadUrl(webUrl);
        } else {
            mWebView.loadDataWithBaseURL("", "讲义加载失败", "text/html", "UTF-8", "");
        }

        back = (ImageView) v.findViewById(R.id.img_back);
        back.setOnClickListener(this);
        mini = (ImageButton) v.findViewById(R.id.mini_screen);
        mini.setOnClickListener(this);

        mPauseButton = (ImageButton) v.findViewById(getResources()
                .getIdentifier("mediacontroller_play_pause", "id",
                        mContext.getPackageName()));
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mProgress = (SeekBar) v.findViewById(getResources().getIdentifier(
                "mediacontroller_seekbar", "id", mContext.getPackageName()));
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.setOnSeekBarChangeListener(mSeekListener);
            }
            mProgress.setMax(1000);
        }

        mEndTime = (TextView) v.findViewById(getResources().getIdentifier(
                "mediacontroller_time_total", "id", mContext.getPackageName()));
        mCurrentTime = (TextView) v.findViewById(getResources()
                .getIdentifier("mediacontroller_time_current", "id",
                        mContext.getPackageName()));
        mFileName = (TextView) v.findViewById(getResources().getIdentifier(
                "mediacontroller_file_name", "id", mContext.getPackageName()));
        mFileName.setText(mTitle);

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setGone();
        } else {
            setVisible();
            MenuRightAnimations.initOffset(mContext);
        }


        collection = collectionDB.findCollection(SharedPrefHelper.getInstance().getUserId(), courseWare.getClassId() + "_" + courseWare.getId(), "3");
        if (collection != null) {
            isCollect = true;
            bt_lectrue.setImageResource(R.mipmap.collect_star_deep);
        } else {
            isCollect = false;
            bt_lectrue.setImageResource(R.mipmap.collect_star_ligt);
        }
        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        /**同步 xunice**/
        loadData();
        if (playActivity.isToAskNote) {
            playActivity.isToAskNote = false;
            ll_webView.setVisibility(View.VISIBLE);
        }
    }

    private void initWebView() {
        mWebView.setWebChromeClient(wc);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSupportZoom(false);
        // 开启JavaScript支持
        mWebView.getSettings().setJavaScriptEnabled(true);

        String ua = mWebView.getSettings().getUserAgentString();
        mWebView.getSettings().setUserAgentString(ua + ";dongao/android/PayH5");
//        webView.addJavascriptInterface(webJSObject, "myObject");
        myWebViewClient = new MyWebViewClient(mWebView);
        mWebView.setWebViewClient(myWebViewClient);

        mWebView.clearCache(true);
        mWebView.clearHistory();
    }

    private MyWebViewClient myWebViewClient;

    class MyWebViewClient extends WVJBWebViewClient {
        public MyWebViewClient(WebView webView) {
            super(webView, new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBWebViewClient.WVJBResponseCallback callback) {
                    callback.callback("Response for message from Android!");
                }
            });

            send("", new WVJBWebViewClient.WVJBResponseCallback() {
                @Override
                public void callback(Object data) {
                }
            });

            registerHandler("setLectureQuestion", new WVJBHandler() {       //提问
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    playActivity.isToAskNote = true;
                    playActivity.gotoAsk(data);
                }
            });

            registerHandler("setLectureNote", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {   //添加笔记
                    playActivity.isToAskNote = true;
                    playActivity.gotoNote(data);
                }
            });

            registerHandler("setLectureTime", new WVJBHandler() {   //时间跳转
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    String result = data.toString();
                    com.alibaba.fastjson.JSONObject object = JSON.parseObject(result);
                    String seconds = object.getString("time");
                    if (!TextUtils.isEmpty(seconds)) {
                        long seekTime = StringUtil.getLongMills(seconds);
                        playActivity.seekTo(seekTime);
                    }
                }
            });
        }
    }

    public void callNote(JSONObject object) {
        String webNote = object.toString();
        myWebViewClient.callHandler("getLectureNote", webNote, new WVJBWebViewClient.WVJBResponseCallback() {
            @Override
            public void callback(Object data) {
            }
        });
    }

    public void callLecture(long time) {
        org.json.JSONObject object = null;
        try {
            object = new org.json.JSONObject();
            object.put("time", time + "");
        } catch (Exception e) {

        }
        String webTime = object.toString();
        myWebViewClient.callHandler("getLectureTime", webTime, new WVJBWebViewClient.WVJBResponseCallback() {
            @Override
            public void callback(Object data) {
            }
        });
    }

    private boolean isCollect;
    public CourseDetail courseDetail;

    private WebChromeClient wc = new WebChromeClient() {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            System.out.println("result==message==" + result + "==" + message);
            return super.onJsAlert(view, url, message, result);
        }

    };

    private void toggleMediaControlsVisiblity() {
//        if (controller.isShown()) {
//            hide();
//        } else {
//            show();
//        }
    }

    public void setList(List list) {
//        this.list = list;
    }


    private String webUrl;
    private MyCollection myCollection;

    /**
     * @param flag 是否为本地课程
     */
    public void setCourseWare(CourseWare cw, boolean flag) {
        this.courseWare = cw;
        myCollection = new MyCollection();
        myCollection.setTitle(cw.getName());
        myCollection.setTime(System.currentTimeMillis() + "");
        myCollection.setYear("666");
        myCollection.setUserId(SharedPrefHelper.getInstance().getUserId());
        myCollection.setSubjectId(cw.getsSubjectId());
        myCollection.setClasssId(cw.getClassId());
        myCollection.setCwId(cw.getId());
        myCollection.setType("3");
        myCollection.setCollectionId(cw.getClassId() + "_" + cw.getId());
        if (flag) {
            webUrl = "file://" + DownloadManager.getInstance().getLecturePath(courseWare);
        } else {
            if (NetWorkUtils.isConnected(mContext)) {
                webUrl = cw.getMobileLectureUrl();
            } else {
                String lecturePath = DownloadManager.getInstance().getLecturePath(courseWare);
                File lectrueFile = new File(lecturePath);
                if (lectrueFile.exists()) {
                    webUrl = "file://" + lecturePath;
                } else {
                    webUrl = "";
                }
            }
        }
        mTitle = cw.getName();
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
    }

    /**
     * Control the action when the seekbar dragged by user
     *
     * @param seekWhenDragging True the media will seek periodically
     */
    public void setInstantSeeking(boolean seekWhenDragging) {
        mInstantSeeking = seekWhenDragging;
    }

    public void show() {
        show(sDefaultTimeout);
    }

    /**
     * Set the content of the file_name TextView
     *
     * @param name
     */
//    public void setFileName(String name) {
//        mTitle = name;
//        if (mFileName != null)
//            mFileName.setText(mTitle);
//    }

    /**
     * Set the View to hold some information when interact with the
     * MediaController
     *
     * @param v
     */
    public void setInfoView(OutlineTextView v) {
        mInfoView = v;
    }

    /**
     * <p>
     * Change the animation style resource for this controller.
     * </p>
     * <p/>
     * <p>
     * If the controller is showing, calling this method will take effect only
     * the next time the controller is shown.
     * </p>
     *
     * @param animationStyle animation style to use when the controller appears and
     *                       disappears. Set to -1 for the default animation, 0 for no
     *                       animation, or a resource identifier for an explicit animation.
     */
    public void setAnimationStyle(int animationStyle) {
        mAnimStyle = animationStyle;
    }

    /**
     * Show the controller on screen. It will go away automatically after
     * 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show the controller
     *                until hide() is called.
     */
    public void show(int timeout) {
        if (mPauseButton != null)
            mPauseButton.requestFocus();
        if (!mShowing) {
            if (!isLock) {
                controller.setVisibility(View.VISIBLE);
                bottom.startAnimation(mAnimSlideInTop);
                top.startAnimation(mAnimSlideInBottom);
            }
        }

        mShowing = true;
        if (mShownListener != null)
            mShownListener.onShown();

        updatePausePlay();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT),
                    timeout);
        }
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void hide() {
        if (mShowing) {
            try {
                bottom.startAnimation(mAnimSlideOutTop);
                top.startAnimation(mAnimSlideOutBottom);
            } catch (IllegalArgumentException ex) {
//                Log.d("MediaController already removed");
            }
            mShowing = false;
            if (mHiddenListener != null)
                mHiddenListener.onHidden();
        }
    }

    public void setOnShownListener(OnShownListener l) {
        mShownListener = l;
    }

    public void setOnHiddenListener(OnHiddenListener l) {
        mHiddenListener = l;
    }

    private long setProgress() {
        if (playActivity.isServiceStart) {
            return 0;
        }
        if (mPlayer == null || mDragging)
            return 0;
        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        mDuration = duration;

        if (mEndTime != null)
            mEndTime.setText(StringUtils.generateTime(mDuration));
        if (mCurrentTime != null)
            mCurrentTime.setText(StringUtils.generateTime(position));

        return position;
    }

    public void setProgress(long currentime, long totalTime) {
        if (mProgress != null) {
            if (totalTime > 0) {
                long pos = 1000L * currentime / totalTime;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null)
            mEndTime.setText(StringUtils.generateTime(mDuration));
        if (mCurrentTime != null)
            mCurrentTime.setText(StringUtils.generateTime(currentime));
    }

    private float mDownX;
    private float mDownY;
    private boolean mChangeVolume;
    private boolean mChangePosition;
    private boolean mChangeBrightness;
    private int mGestureDownPosition;
    private int mGestureDownVolume;
    private float mGestureDownBrightness;
    public static final int THRESHOLD = 80;
    private int mScreenWidth;
    private int mScreenHeight;
    private AudioManager mAudioManager;
    private int mSeekTimePosition;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (lock != null) {
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                lock.setVisibility(View.VISIBLE);
            }
        }
        if (!isLock) {
            show(sDefaultTimeout);
        }
        if (ll_webView.isShown()) {
            return true;
        }

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || isLock) {
            return true;
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                mChangeVolume = false;
                mChangePosition = false;
                mChangeBrightness = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                float absDeltaX = Math.abs(deltaX);
                float absDeltaY = Math.abs(deltaY);

                if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                    if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
//                        cancelProgressTimer();
                        if (absDeltaX >= THRESHOLD) {
                            // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                            // 否则会因为mediaplayer的状态非法导致App Crash
                            mChangePosition = true;
                            mGestureDownPosition = getCurrentPositionWhenPlaying();
                        } else {
                            //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                            if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                                mChangeBrightness = true;
                                try {
                                    mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                    System.out.println("当前亮度 " + mGestureDownBrightness);
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {//右侧改变声音
                                mChangeVolume = true;
                                mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            }
                        }
                    }
                }

                if (mChangePosition) {
                    int totalTimeDuration = (int) mPlayer.getDuration();
                    mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                    if (mSeekTimePosition > totalTimeDuration)
                        mSeekTimePosition = totalTimeDuration;

                    showProgressDialog(mSeekTimePosition);
                }
                if (mChangeVolume) {
                    deltaY = -deltaY;
                    int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                    //dialog中显示百分比
                    int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
//                    setVolumeScale((float)volumePercent/100);
                    showDialog(volumePercent, false);
                }

                if (mChangeBrightness) {
                    deltaY = -deltaY;
                    int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
                    WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
                    if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                        params.screenBrightness = 1;
                    } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                        params.screenBrightness = 0.01f;
                    } else {
                        params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                    }
                    mContext.getWindow().setAttributes(params);
                    //dialog中显示百分比
                    int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
                    System.out.println("percentfdsfdsf : " + brightnessPercent + " " + deltaY + " " + mGestureDownBrightness);
//                    setBrightnessScale((float) brightnessPercent/100);
                    showDialog(brightnessPercent, true);
                }
                break;
            case MotionEvent.ACTION_UP:
                dismissDialog();
                if (mChangePosition) {
                    mPlayer.seekTo(mSeekTimePosition);
                }
                break;
        }
        return true;
    }

    protected Dialog mDialog;
    protected ProgressBar mDialogProgressBar;
    protected TextView mDialogTextView;
    protected ImageView mDialogImageView;

    public void showDialog(int percent, boolean isBright) {  //ture 亮度  false 音量
        if (mDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.play_dialog_volume, null);
            mDialogImageView = ((ImageView) localView.findViewById(R.id.volume_image_tip));
            mDialogTextView = ((TextView) localView.findViewById(R.id.tv_volume));
            mDialogProgressBar = ((ProgressBar) localView.findViewById(R.id.volume_progressbar));
            mDialog = new Dialog(getContext(), R.style.jc_style_dialog_progress);
            mDialog.setContentView(localView);
            mDialog.getWindow().addFlags(8);
            mDialog.getWindow().addFlags(32);
            mDialog.getWindow().addFlags(16);
            mDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.CENTER;
            mDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        if (isBright) {
            mDialogImageView.setBackgroundResource(R.mipmap.mediacontroller_brightness);
        } else {
            if (percent <= 0) {
                mDialogImageView.setBackgroundResource(R.mipmap.mediacontroller_zero);
            } else {
                mDialogImageView.setBackgroundResource(R.mipmap.mediacontroller_vol);
            }
        }
        if (percent > 100) {
            percent = 100;
        } else if (percent < 0) {
            percent = 0;
        }
        mDialogTextView.setText(percent + "%");
        mDialogProgressBar.setProgress(percent);
    }

    private void showProgressDialog(int desPosition) {
        ll_progress.setVisibility(View.VISIBLE);
        if (desPosition <= 0) {
            progress_play.setText(StringUtils.generateTime(0) + "/");
        } else {
            progress_play.setText(StringUtils.generateTime(desPosition) + "/");
        }
        progressAll.setText(StringUtils.generateTime(mDuration));
    }

    private void dismissDialog() {
        ll_progress.setVisibility(View.GONE);
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public int getCurrentPositionWhenPlaying() {
        int position = 0;
        if (mPlayer == null) {
            return position;//这行代码不应该在这，如果代码和逻辑万无一失的话，心头之恨呐
        } else {
            position = (int) mPlayer.getCurrentPosition();
        }

        return position;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(sDefaultTimeout);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getRepeatCount() == 0
                && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE)) {
            doPauseResume();
            show(sDefaultTimeout);
            if (mPauseButton != null)
                mPauseButton.requestFocus();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_MENU) {
            hide();
            return true;
        } else {
            show(sDefaultTimeout);
        }
        return super.dispatchKeyEvent(event);
    }

    private void updatePausePlay() {
        if (playActivity != null) {
            if (playActivity.isServiceStart) {
                return;
            }
        }

        if (mRoot == null || mPauseButton == null)
            return;

        if (mPlayer.isPlaying())
            mPauseButton.setImageResource(getResources().getIdentifier(
                    "mediacontroller_pause", "drawable",
                    mContext.getPackageName()));
        else
            mPauseButton.setImageResource(getResources().getIdentifier(
                    "mediacontroller_play", "drawable",
                    mContext.getPackageName()));
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying())
            mPlayer.pause();
        else
            mPlayer.start();
        playerHander.sendEmptyMessage(PLAY_ISTART);
        updatePausePlay();
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mPauseButton != null)
            mPauseButton.setEnabled(enabled);
        if (mProgress != null)
            mProgress.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    public interface OnShownListener {
        public void onShown();
    }

    public interface OnHiddenListener {
        public void onHidden();
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        long getDuration();

        long getCurrentPosition();

        void seekTo(long pos);

        boolean isPlaying();

        int getBufferPercentage();
    }

    public PlayerActivity getPlayActivity() {
        return playActivity;
    }

    private static final int SCREEN_PORTRAIT = 1111;
    private static final int SCREEN_LANDSCAPE = 7777;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_control:
                hide();
                break;
            case R.id.img_back:
                if (isLock) {
                    return;
                }
                playActivity.finish();
                break;
            case R.id.mini_screen:
                if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.img_lecture:
                if (isCollect) {
                    playActivity.delCollectCw(courseWare);
                } else {
                    playActivity.collectCw(courseWare);
                }
                break;
            case R.id.cb_collect:
                if (!NetWorkUtils.isConnected(mContext)) {
                    Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isCollect) {
                    playActivity.delCollectCw(courseWare);
                } else {
                    playActivity.collectCw(courseWare);
                }
                break;
            case R.id.img_download:
                break;
            case R.id.lecture_web:
                ll_webView.setVisibility(View.GONE);
                break;
            case R.id.mediacontroller_lecture:
                if (ll_webView.isShown()) {
                    ll_webView.setVisibility(View.GONE);
                } else {
                    ll_webView.setVisibility(View.VISIBLE);
                    hide();
                }
                break;
            case R.id.img_lock:
                if (isLock) {
                    controller.setVisibility(View.VISIBLE);
                    bottom.startAnimation(mAnimSlideInTop);
                    top.startAnimation(mAnimSlideInBottom);
                    show();
                    lock.setImageResource(R.drawable.media_btn_unlock);
                    isLock = false;
                } else {
                    bottom.startAnimation(mAnimSlideOutTop);
                    top.startAnimation(mAnimSlideOutBottom);
                    hide();
                    lock.setImageResource(R.drawable.media_btn_lock);
                    isLock = true;
                }
                playerHander.sendEmptyMessage(SCREEN_ISLOCK);
                break;
            case R.id.mediacontroller_play_next:

                break;
            case R.id.img_listen:
            case R.id.img_listen_little:
                if (playActivity.isServiceStart) {
                    playActivity.playVedio();
                } else {
                    playActivity.isServiceStart = true;
                    playActivity.startPlayService(courseWare);

                    listen.setImageResource(R.mipmap.mediacontroller_listen_deep);
                }
                break;
            case R.id.play_error_ll:
                playActivity.playVedio();
                break;
            default:
                break;
        }
    }

    public void reSetIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
        if (isCollect) {
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                bt_lectrue.setImageResource(R.mipmap.collect_star_deep);
                collectionDB.insertNotUpdate(myCollection);
                Toast toast = Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, SharedPrefHelper.getInstance().getMiniScreenHight() / 2);
                toast.show();
            } else {
                bt_lectrue.setImageResource(R.mipmap.collect_star_deep);
                collectionDB.insertNotUpdate(myCollection);
                Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                bt_lectrue.setImageResource(R.mipmap.collect_star_ligt);
                if (collection == null) {
                    collection = collectionDB.findCollection(SharedPrefHelper.getInstance().getUserId(), courseWare.getClassId() + "_" + courseWare.getId(), "3");
                }
                collectionDB.delete(collection);
                Toast toast = Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, SharedPrefHelper.getInstance().getMiniScreenHight() / 2);
                toast.show();
            } else {
                bt_lectrue.setImageResource(R.mipmap.collect_star_ligt);
                if (collection == null) {
                    collection = collectionDB.findCollection(SharedPrefHelper.getInstance().getUserId(), courseWare.getClassId() + "_" + courseWare.getId(), "3");
                }
                collectionDB.delete(collection);
                Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setCwCollection(String flag) {
        if (flag.equals("true")) {
            bt_lectrue.setImageResource(R.mipmap.collect_star_deep);
        } else {
            bt_lectrue.setImageResource(R.mipmap.collect_star_ligt);
        }
    }

    public boolean isLock;
    private static final int SCREEN_ISLOCK = 9879;

    public void setGoneAllP() {
        lock.setVisibility(View.GONE);
        ll_webView.setVisibility(View.GONE);
        mediacontroller_top_right.setVisibility(View.GONE);
        nextPlay.setVisibility(View.GONE);

        bottom_three.setVisibility(View.GONE);
        mini.setVisibility(View.VISIBLE);
    }

    public void setGoneAllL() {
        lock.setVisibility(View.GONE);
        ll_webView.setVisibility(View.GONE);
        mediacontroller_top_right.setVisibility(View.GONE);
        nextPlay.setVisibility(View.GONE);

        bottom_three.setVisibility(View.VISIBLE);
        tv_lecture.setVisibility(View.GONE);
        excise.setVisibility(View.GONE);
        mini.setVisibility(View.GONE);
    }

    public void setGone() {
        lock.setVisibility(View.GONE);
        ll_webView.setVisibility(View.GONE);
        mediacontroller_top_right.setVisibility(View.GONE);
        nextPlay.setVisibility(View.GONE);

        bottom_three.setVisibility(View.GONE);
        mini.setVisibility(View.VISIBLE);
    }

    public void setVisible() {
        lock.setVisibility(View.VISIBLE);
        mediacontroller_top_right.setVisibility(View.VISIBLE);
//        right.setVisibility(View.VISIBLE);
        nextPlay.setVisibility(View.VISIBLE);

        bottom_three.setVisibility(View.VISIBLE);
        mini.setVisibility(View.GONE);
    }

    public void setIsClickable(boolean flag) {
        mPauseButton.setClickable(flag);
    }

    public boolean toExcise;

    /**
     * 同步 xunice
     **/
//     注册播放完成的监听
    private void loadData() {

        // 播放完成后
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                // TODO Auto-generated method stub
                //以下只需要第一次的时候用就可以
                updatePausePlay();
//                videoView.setBackgroundColor(Color.TRANSPARENT);
                mHandler.sendEmptyMessageDelayed(MSG_STUDY_LOG, TIME_STUDY_LOG);
//                playActivity.notifyPlayStatus();
                mHandler.sendEmptyMessageDelayed(POST_STUDYLOG, TIME_STUDY_LOG_SYNC);//上传studyLog
            }
        });

        // 播放完成后
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                //courseWare.getPracticeFlag() == 1 && !toExcise
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
//                playActivity.analysisDNS(courseWare);
                return false;
            }
        });
    }
}
