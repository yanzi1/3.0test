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

package io.vov.vitamio.widget.auditionplay;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.audition.AuditionPlayActivity;
import com.me.fanyin.zbme.views.course.play.db.OperaDB;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.OutlineTextView;
import io.vov.vitamio.widget.adapter.NoChapterAdapter;
import io.vov.vitamio.widget.adapter.SpeedAdapter;

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
public class MediaController extends FrameLayout implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private static final int sDefaultTimeout = 10000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private MediaPlayerControl mPlayer;
    private Activity mContext;
    public PopupWindow mWindow;
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
    private ImageView back;
    private TextView speed;

    private CourseWare courseWare;
    /**
     * 同步 xunice
     **/
    private AuditionPlayActivity playActivity;
    private VideoView videoView;
    private long endTime = 0;

    private View bottom, top;

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
            }
        }
    };
    private OnClickListener mPauseListener = new OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
//            playActivity.notifyPlayStatus();
        }
    };
    private OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            /**同步 xunice**/
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
                long startime= StringUtil.getLongMills(courseWare.getStartTime());
                long endtime= StringUtil.getLongMills(courseWare.getEndTime());
                long time=(mDuration * bar.getProgress()) / 1000;
//                if(time<startime){
//                    mPlayer.seekTo(startime);
//                    Toast.makeText(playActivity, "超出试听范围", Toast.LENGTH_SHORT).show();
//                }else if(time>endtime){
//                    mPlayer.seekTo(startime);
//                    Toast.makeText(playActivity, "超出试听范围", Toast.LENGTH_SHORT).show();
//                }else{
//                    mPlayer.seekTo((mDuration * bar.getProgress()) / 1000);
//                }
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

            mWebJSObject.jumpLecture();
        }
    };

    public MediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = this;
        mFromXml = true;
        initController(context);
    }

    public void showErrorLayout(){
        playErrorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 同步 xunice
     **/
    public MediaController(Context context, VideoView mVideoView) {
        super(context);
        videoView = mVideoView;
        if (!mFromXml && initController(context))
            initFloatingWindow();
        initAnimation();
    }

    private Animation mAnimSlideInRight;
    private Animation mAnimSlideInTop;
    private Animation mAnimSlideInBottom;
    private Animation mAnimSlideOutTop;
    private Animation mAnimSlideOutBottom;
    private Animation mAnimSlideOutRight;

    private void initAnimation() {
        mAnimSlideInTop = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_top);
        mAnimSlideOutTop = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_top);

        mAnimSlideOutRight = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_right);
        mAnimSlideInRight = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_right);

        mAnimSlideInBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_in_bottom);
        mAnimSlideOutBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_out_bottom);

        mAnimSlideInBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (lock != null) {
                    lock.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimSlideOutBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                controller.setVisibility(View.GONE);
                if (lock != null) {
                    lock.setVisibility(View.GONE);
                }
//				mHandler.removeMessages(MSG_HIDE_SYSTEM_UI);
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
        mWindow = new PopupWindow(mContext);
        mWindow.setFocusable(true);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setOutsideTouchable(true);
        mAnimStyle = android.R.style.Animation;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setWindowLayoutType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
//				mAnchor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                Method setWindowLayoutType = PopupWindow.class.getMethod(
                        "setWindowLayoutType", new Class[]{int.class});
                setWindowLayoutType
                        .invoke(mWindow,
                                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
            } catch (Exception e) {
                Log.e("setWindowLayoutType", e);
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
            mWindow.setContentView(mRoot);
            mWindow.setWidth(LayoutParams.MATCH_PARENT);
            mWindow.setHeight(LayoutParams.MATCH_PARENT);
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
                R.layout.mediacontroller_audition, this);
    }

    private NoChapterAdapter adapter;
    private WebView mWebView;
    private LinearLayout ll_webView;

    public WebJSObject mWebJSObject = new WebJSObject();
    private RelativeLayout layer;
    private LinearLayout playErrorLayout;
    private LinearLayout ll_speed;
    private ListView listView_speed;
    private SpeedAdapter speedAdapter;
    private ImageView lock;

    @SuppressWarnings("deprecation")
    private void initControllerView(View v) {

        playErrorLayout=(LinearLayout) v.findViewById(R.id.play_error_ll);
        playErrorLayout.setOnClickListener(this);

        controller = (RelativeLayout) v.findViewById(R.id.layout_control);
        controller.setOnClickListener(this);
        layer = (RelativeLayout) v.findViewById(R.id.layer);

        bottom = v.findViewById(R.id.rl_bottom);
        top = v.findViewById(R.id.linear_top);
        ll_webView = (LinearLayout) v.findViewById(R.id.ll_webview);

        controller.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });

        ll_speed = (LinearLayout) v.findViewById(R.id.mediacontroller_ll_speed);
        ll_speed.setOnClickListener(this);
        listView_speed = (ListView) v.findViewById(R.id.mediacontroller_speed_list);
        listView_speed.setOnItemClickListener(this);

        lock=(ImageView) v.findViewById(R.id.img_lock);
        lock.setOnClickListener(this);

        mWebView = (WebView) v.findViewById(R.id.webview);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		String ur=Environment.getExternalStorageDirectory()+"/lecture.htm";
        if (!TextUtils.isEmpty(webUrl)) {
            mWebView.loadUrl(webUrl);
        } else {
            mWebView.loadDataWithBaseURL("", "讲义加载失败", "text/html", "UTF-8", "");
        }
        //"file:///android_asset/lecture.html"
        mWebView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mWebView.onTouchEvent(event);
                return true;
            }

        });
        mWebView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        // 开启JavaScript支持
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(wc);
        mWebView.setWebViewClient(mWebClient);
        mWebView.addJavascriptInterface(mWebJSObject, "myObject");

        back = (ImageView) v.findViewById(R.id.img_back);
        back.setOnClickListener(this);
        speed = (TextView) v.findViewById(R.id.mediacontroller_speed);
        speed.setOnClickListener(this);
        if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.0f) {
            speed.setText("1.0x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.2f) {
            speed.setText("1.2x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.5f) {
            speed.setText("1.5x");
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.8f) {
            speed.setText("1.8x");
        }

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
        mFileName.setOnClickListener(this);

        if (mFileName != null)
            mFileName.setText(mTitle);

        playActivity = (AuditionPlayActivity) mContext;
        //判断课程有无章节
        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        loadData();

        speedAdapter = new SpeedAdapter(mContext);
        speedAdapter.setData();
        listView_speed.setAdapter(speedAdapter);
    }


    private String webUrl;
    private long audition_endTime;

    /**
     * @param
     */
    public void setCourseWare(CourseWare cw) {
        this.courseWare = cw;
        webUrl = cw.getMobileLectureUrl();
        mTitle = cw.getName();
        audition_endTime= StringUtil.getLongMills(courseWare.getEndTime());
//        audition_endTime= Long.parseLong(courseWare.getEndTime())*1000;
    }

    private Handler mWebHanlder = new Handler();

    public class WebJSObject {

        public void jumpLecture() {
            mWebHanlder.post(new Runnable() {
                public void run() {
                    mWebView.loadUrl("javascript:jumpLecture("
                            + mPlayer.getCurrentPosition() / 1000 + ")");
                }
            });

        }

        @JavascriptInterface
        public void jumpTime(final int time) {
            mWebHanlder.post(new Runnable() {
                public void run() {
                    mPlayer.seekTo(time * 1000);
                }
            });
        }

    }

    private WebViewClient mWebClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            // mWebJSObject.getAllSection();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    };

    private WebChromeClient wc = new WebChromeClient() {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            System.out.println("result==message==" + result + "==" + message);
            return super.onJsAlert(view, url, message, result);
        }

    };

    private void toggleMediaControlsVisiblity() {
        if (controller.isShown()) {
            hide();
        } else {
            show();
        }
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

            int[] location = new int[2];
            mAnchor.getLocationOnScreen(location);
            Rect anchorRect = new Rect(location[0], location[1], location[0]
                    + mAnchor.getWidth(), location[1] + mAnchor.getHeight());
            mWindow.setAnimationStyle(mAnimStyle);
            setWindowLayoutType();
            mWindow.showAtLocation(mAnchor, Gravity.TOP, 0, 0);// anchorRect.left,anchorRect.bottom
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
//				mHandler.removeMessages(SHOW_PROGRESS);
                top.startAnimation(mAnimSlideOutBottom);
                bottom.startAnimation(mAnimSlideOutTop);
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWindow.dismiss();
//                    }
//                },500);
            } catch (IllegalArgumentException ex) {
                Log.d("MediaController already removed");
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

//        if(audition_endTime<=position){
//            mPlayer.pause();
//            Toast.makeText(playActivity, "超出试听范围", Toast.LENGTH_SHORT).show();
//        }
        return position;
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
            lock.setVisibility(View.VISIBLE);
        }
        if (isLock) {
            return true;
        }
        show(sDefaultTimeout);

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

                    showDialog(mSeekTimePosition);
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

    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
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

    protected Dialog mDialog;
    protected Dialog mDialogProgress;
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

    private ProgressBar pBar;
    private TextView tvPro;
    public void showDialog(long desPosition) {
        if (mDialogProgress == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.play_dialog_progress, null);

            tvPro = ((TextView) localView.findViewById(R.id.tv_volume));
            pBar = ((ProgressBar) localView.findViewById(R.id.volume_progressbar));
            mDialogProgress = new Dialog(getContext(), R.style.jc_style_dialog_progress);
            mDialogProgress.setContentView(localView);
            mDialogProgress.getWindow().addFlags(8);
            mDialogProgress.getWindow().addFlags(32);
            mDialogProgress.getWindow().addFlags(16);
            mDialogProgress.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mDialogProgress.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.CENTER;
            mDialogProgress.getWindow().setAttributes(localLayoutParams);
        }
        if (!mDialogProgress.isShowing()) {
            mDialogProgress.show();
        }

        if (desPosition <= 0) {
            tvPro.setText(StringUtils.generateTime(0));
        } else {
            tvPro.setText(StringUtils.generateTime(desPosition));
        }
        if(mDuration!=0){
            pBar.setProgress((int)(desPosition*100/mDuration));
        }
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

        void playSpeed(float playSpeed);
    }

    public boolean isLock;

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
            case R.id.lecture_web:
                ll_webView.startAnimation(mAnimSlideOutRight);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_webView.setVisibility(View.GONE);
                    }
                }, 500);
                break;
            case R.id.mediacontroller_lecture:
                if (ll_webView.isShown()) {
                    ll_webView.startAnimation(mAnimSlideOutRight);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ll_webView.setVisibility(View.GONE);
                        }
                    }, 500);
                } else {
                    ll_webView.setVisibility(View.VISIBLE);
                    ll_webView.startAnimation(mAnimSlideInRight);
                    hide();
                }
                break;
            case R.id.play_error_ll:
                playActivity.retryPlay();
                break;
            case R.id.mediacontroller_speed:
                if (!ll_speed.isShown()) {
                    ll_speed.setVisibility(View.VISIBLE);
                    hide();
                } else {
                    ll_speed.setVisibility(View.GONE);
                }
                break;
            case R.id.mediacontroller_ll_speed:
                ll_speed.setVisibility(View.GONE);
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
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.mediacontroller_speed_list:
                if (position == 0) {
                    mPlayer.playSpeed(1.0f);
                    speed.setText("1.0x");
                    SharedPrefHelper.getInstance().setPlaySpeed(1.0f);
                } else if (position == 1) {
                    mPlayer.playSpeed(1.2f);
                    speed.setText("1.2x");
                    SharedPrefHelper.getInstance().setPlaySpeed(1.2f);
                }else if (position == 2) {
                    mPlayer.playSpeed(1.5f);
                    speed.setText("1.5x");
                    SharedPrefHelper.getInstance().setPlaySpeed(1.5f);
                }else if (position == 3) {
                    mPlayer.playSpeed(1.8f);
                    speed.setText("1.8x");
                    SharedPrefHelper.getInstance().setPlaySpeed(1.8f);
                }
                speedAdapter.setData();
                speedAdapter.notifyDataSetChanged();
                ll_speed.setVisibility(View.GONE);
                break;
        }
    }

    public void setIsClickable(boolean flag) {
        mPauseButton.setClickable(flag);
    }

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
                videoView.playSpeed(SharedPrefHelper.getInstance().getPlaySpeed());
                videoView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playActivity.finish();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                long errorTime = mPlayer.getCurrentPosition();
                if (times != null && times.size() > 0) {
                    int errorCount = getErrorCount(times, errorTime);
                }

                return false;
            }
        });
    }

    private int getErrorCount(List<Integer> times, long errorTime) {
        if (times != null && times.size() > 0) {
            for (int i = 0; i < times.size(); i++) {
                errortotal = errortotal + times.get(i);
                if (errortotal > errorTime) {
                    return i;
                }
            }
        }
        return -1;
    }

    private List<Integer> times;
    private int errortotal;
    private OperaDB operaDB;
}
