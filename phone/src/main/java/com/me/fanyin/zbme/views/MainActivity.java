package com.me.fanyin.zbme.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.event.ContinuePlayEvent;
import com.me.data.event.ExamChangeEvent;
import com.me.data.event.PostStudyLogEvent;
import com.me.data.event.SystemChangeEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CwStudyLog;
import com.me.data.model.play.UploadVideo;
import com.me.data.model.play.VideoLogs;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.CourseFragment;
import com.me.fanyin.zbme.views.course.SystemChangeActivity;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.widget.ConnectChange;
import com.me.fanyin.zbme.views.main.MainFragment;
import com.me.fanyin.zbme.views.main.activity.CourseTypeChangeActivity;
import com.me.fanyin.zbme.views.main.view.widget.BottomNavigatorView;
import com.me.fanyin.zbme.views.mine.MineFragment;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.utils.StringUtils;

/**
 * 也加上Mvp模式，方式后续加入其它的功能
 */
public class MainActivity extends BaseMvpActivity<MainActivityView, MainActivityPersenter> implements BottomNavigatorView.OnBottomNavigatorViewItemClickListener, MainActivityView {

    @BindView(R.id.main_bottom_nav_view)
    BottomNavigatorView main_bottom_nav_view;

    @BindView(R.id.main_bottom_content_layout)
    FrameLayout main_bottom_content_layout;
    @BindView(R.id.btn_system_change)
    ImageButton btn_system_change;

    public static final String TAG_MAIN = "tag_main";
    public static final String TAG_COURSE = "tag_course";
    public static final String TAG_DISCOVER = "tag_discover";
    public static final String TAG_MINE = "tag_mine";
    public String lastTag;
    private boolean isExamChange;
    private boolean isChangeSystem = false;
//    private final MyHandler mHandler = new MyHandler(this);
    /**
     * 跳转商城
     *
     * @param examId 考种id 没有传空null
     */
    public static void startMallFragment(Context context, String examId) {
        Intent intent = new Intent(context, MainActivity.class);
        if (TextUtils.isEmpty(examId)) {
            examId = "-1";
        }
        intent.putExtra("mall_exam_id", examId);
        context.startActivity(intent);
    }
    /**
     * 跳转商城
     *
     * @param examId 考种id  类型 Constants.MALL_CATEGORY_TYPE_ALL
     */
    public static void startMallFragment(Context context, String examId,int type) {
        startMallFragment(context,examId,"-1",type);
    }

    /**
     * 根据 考种 科目 跳转指定的商城页面
     *
     * @param type $Constants.MALL_CATEGORY_TYPE_ALL
     */
    public static void startMallFragment(Context context, String examId, String subjectId, int type) {
        Intent intent = new Intent(context, MainActivity.class);
        if (TextUtils.isEmpty(examId)) {
            examId = "-1";
        }
        intent.putExtra("mall_exam_id", examId);
        intent.putExtra("mall_subject_id", subjectId);
        intent.putExtra("mall_type", type);
        context.startActivity(intent);
    }

    /**
     * 跳转商城
     *
     * @param tag
     */
    public static void startCurrentFragment(Context context, String tag) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    /**
     * 跳转 指定考种商城
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = intent.getStringExtra("tag");
        if (TextUtils.isEmpty(tag)) {
            String exam_id = intent.getStringExtra("mall_exam_id");
            String subject_id = intent.getStringExtra("mall_subject_id");
            int type = intent.getIntExtra("mall_type", Constants.MALL_CATEGORY_TYPE_ALL); //默认全部

            if (!TextUtils.isEmpty(exam_id)) {
                if (!exam_id.equals("-1") && !exam_id.equals("999")) {  //没有传 examId
                    SharedPrefHelper.getInstance().setMallExamId(exam_id);
                }
                if (!TextUtils.isEmpty(subject_id)) {
                    SharedPrefHelper.getInstance().setMallSubjectId(subject_id);
                }
                SharedPrefHelper.getInstance().setMallType(type);

                main_bottom_nav_view.findViewById(R.id.nav_discover).performClick();

            }
        } else {
            if (tag.equals(TAG_MAIN)) {
                main_bottom_nav_view.findViewById(R.id.nav_main).performClick();
                boolean isTokenError=false;
                String tokenErrorMsg="";
                if (intent.getBooleanExtra("token_timeOut",false)){
                    isTokenError=true;
                    tokenErrorMsg="您的账户已过期，请重新登录";
                }
                if (intent.getBooleanExtra("token_kickedOut",false)){
                    isTokenError=true;
                    tokenErrorMsg="该账户已在其他设备登录，为了您的账户安全，请您重新登录";
                }
                if (isTokenError){
                    DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {
                            gotoActivity(LoginActivity.class);
                        }
                    },tokenErrorMsg,0,null);
                }
            } else if (tag.equals(TAG_MINE)) {
                main_bottom_nav_view.findViewById(R.id.nav_mine).performClick();
            } else if (tag.equals(TAG_DISCOVER)) {
                main_bottom_nav_view.findViewById(R.id.nav_discover).performClick();
            }
        }

    }

    @Override
    public void initView() {
        initConnectBroadCast();
        SharedPrefHelper.getInstance().setFirstIn(false);
        cwStudyLogDB = new CwStudyLogDB();
        EventBus.getDefault().register(this);
        main_bottom_nav_view.setOnBottomNavigatorViewItemClickListener(this);
        //开启动态权限
        if (!isStorgePermissionGranted()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //默认第一个tab
//        showFragment(TAG_MAIN);
        main_bottom_nav_view.findViewById(R.id.nav_main).performClick();
        lastTag = TAG_MAIN;
//        BottomNavigationHelper.disableShiftMode(main_bottom_nav_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int ScreenW = dm.widthPixels;
        int ScreenH = dm.heightPixels;
        SharedPrefHelper.getInstance().setFullScreenHight(ScreenW);
        SharedPrefHelper.getInstance().setScreenHight(ScreenH);
//        mHandler.sendEmptyMessage(MyHandler.WHAT_ALPHA30);
        btn_system_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SystemChangeActivity.class));
                overridePendingTransition(R.anim.slide_in_top,android.R.anim.fade_out);
            }
        });
    }

    private ConnectChange connectChange;
    private void initConnectBroadCast() {
        connectChange = new ConnectChange();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectChange, intentFilter);
    }

    @Override
    public void initData() {
        mPresenter.getData();
        boolean flag=SharedPrefHelper.getInstance().isIntel();
        if(flag){
            upLoadVideosSmart();
        }else{
            upLoadVideos();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.main_bottom_activity;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isExamChange){
            isExamChange=false;
            if (!lastTag.equals(TAG_COURSE)) {
                main_bottom_nav_view.findViewById(R.id.nav_course).performClick();
            }
        }
        if (isChangeSystem){
            main_bottom_nav_view.findViewById(R.id.nav_course).performClick();
            isChangeSystem = false;

        }
    }

    @Override
    public void onBottomNavigatorViewItemClick(int position, View view) {
        main_bottom_nav_view.select(position);
        switch (position) {
            case 0:
                MobclickAgent.onEvent(this, Statistics.HOMEPAGE);
                showFragment(TAG_MAIN);
                lastTag = TAG_MAIN;
                break;
            case 1:
                if ((!SharedPrefHelper.getInstance().isIntel()&&TextUtils.isEmpty(SharedPrefHelper.getInstance().getExamId()))|| (SharedPrefHelper.getInstance().isIntel()&&TextUtils.isEmpty(SharedPrefHelper.getInstance().getIntelExamId()))) {
                    startCurrentFragment(context(), lastTag);
                    Intent asdasd = new Intent(MainActivity.this, CourseTypeChangeActivity.class);
                    startActivity(asdasd);
                } else {
                    MobclickAgent.onEvent(this, Statistics.CLASSROOM);
                    showFragment(TAG_COURSE);
                    lastTag = TAG_COURSE;
                }
                break;
            case 2:
                MobclickAgent.onEvent(this, Statistics.SHOPPINGMALL);
                showFragment(TAG_DISCOVER);
                lastTag = TAG_DISCOVER;
                break;
            case 3:
                MobclickAgent.onEvent(this, Statistics.PROFILE);
                showFragment(TAG_MINE);
                lastTag = TAG_MINE;
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(ExamChangeEvent event) {  //更新数据
        isExamChange =true;
//        showFragment(TAG_COURSE);
    }
    @Subscribe
    public void onSystemChangeEvent(SystemChangeEvent event) {  //智能系统切换
//        main_bottom_nav_view.findViewById(R.id.nav_course).performClick();
        isChangeSystem =true;
    }


    /**
     * 显示fragment
     */
    public void showFragment(String tag) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentByTag == null) {
            switch (tag) {
                case TAG_MAIN:
                    fragmentByTag = MainFragment.newInstance();
                    break;
                case TAG_COURSE:
                        fragmentByTag = CourseFragment.newInstance();
                    break;
                case TAG_DISCOVER:
                    break;
                case TAG_MINE:
                    fragmentByTag = MineFragment.newInstance();
                    break;

            }
            fragmentTransaction.add(R.id.main_bottom_content_layout, fragmentByTag, tag);
        }else {
            if (tag.equals(TAG_COURSE)){
                if (SharedPrefHelper.getInstance().isIntel() && fragmentByTag instanceof  CourseFragment && SharedPrefHelper.getInstance().isLogin()){ //必须登录才能看只能系统
                    fragmentTransaction.remove(fragmentByTag);
                    fragmentTransaction.add(R.id.main_bottom_content_layout, fragmentByTag, TAG_COURSE);
                }

            }
        }

        if (tag.equals(TAG_COURSE)){
            btn_system_change.setVisibility(View.VISIBLE);
        }else {
            btn_system_change.setVisibility(View.GONE);
        }
        hideOtherFragment(fragmentTransaction, tag);
        fragmentTransaction.show(fragmentByTag).commit();
    }

    /**
     * 隐藏其他fragment
     */
    private void hideOtherFragment(FragmentTransaction fragmentTransaction, String tag) {
        switch (tag) {
            case TAG_MAIN:
                hideFragment(fragmentTransaction, TAG_COURSE);
                hideFragment(fragmentTransaction, TAG_DISCOVER);
                hideFragment(fragmentTransaction, TAG_MINE);
                // setStatusBar(false);
                break;
            case TAG_COURSE:
                hideFragment(fragmentTransaction, TAG_MAIN);
                hideFragment(fragmentTransaction, TAG_DISCOVER);
                hideFragment(fragmentTransaction, TAG_MINE);
                // setStatusBar(false);
                break;
            case TAG_DISCOVER:
                hideFragment(fragmentTransaction, TAG_MAIN);
                hideFragment(fragmentTransaction, TAG_COURSE);
                hideFragment(fragmentTransaction, TAG_MINE);
                // setStatusBar(false);
                break;
            case TAG_MINE:
                hideFragment(fragmentTransaction, TAG_COURSE);
                hideFragment(fragmentTransaction, TAG_DISCOVER);
                hideFragment(fragmentTransaction, TAG_MAIN);
                //setStatusBar(false);
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, String tag) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentByTag != null) {
            fragmentTransaction.hide(fragmentByTag);
        }
    }

    public boolean isStorgePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 判断是否已经点击过一次回退键
     */

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void showError(String message) {

    }

    public void upLoadVideos() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        if (NetWorkUtils.isNetworkAvailable(this) && !TextUtils.isEmpty(userId)) {
            String listenStr = getStudyLogs();
            if (!TextUtils.isEmpty(listenStr)) {
                mPresenter.upLoadVideos(listenStr);
            }
        }
    }

    private List<CwStudyLog> studyLogs;
    private CwStudyLogDB cwStudyLogDB;

    private String getStudyLogs() {
        UploadVideo uploadVideo = new UploadVideo();
        String userId = SharedPrefHelper.getInstance().getUserId();
        studyLogs = cwStudyLogDB.queryByUserId(userId);
        List<VideoLogs> list = new ArrayList();
        if (studyLogs != null && studyLogs.size() > 0) {
            for (int i = 0; i < studyLogs.size(); i++) {
                if (studyLogs.get(i).getWatchedAt() != 0) {
                    VideoLogs log = new VideoLogs();
                    log.setCourseId(studyLogs.get(i).getCourseId());
                    log.setLectureId(studyLogs.get(i).getCwid());
                    log.setStartTime(StringUtils.generateTime(studyLogs.get(i).getStartTime()));
                    log.setEndTime(StringUtils.generateTime(studyLogs.get(i).getEndTime()));
                    log.setListenTime(studyLogs.get(i).getWatchedAt() / 1000 + "");
                    list.add(log);
                }
            }
            if (list != null && list.size() > 0) {
                uploadVideo.setMemberId(userId);
                uploadVideo.setListenList(list);

                return JSON.toJSONString(uploadVideo);
            }
        }
        return "";
    }

    public void upLoadVideosSmart() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        if (NetWorkUtils.isNetworkAvailable(this) && !TextUtils.isEmpty(userId)) {
            String listenStr = getStudyLogsSmart();
            if (!TextUtils.isEmpty(listenStr)) {
                mPresenter.upLoadVideosSmart(listenStr);
            }
        }
    }

    private String getStudyLogsSmart() {
        UploadVideo uploadVideo = new UploadVideo();
        String userId = SharedPrefHelper.getInstance().getUserId();
        studyLogs = cwStudyLogDB.queryByUserIdSmart(userId);
        List<VideoLogs> list = new ArrayList();
        if (studyLogs != null && studyLogs.size() > 0) {
            for (int i = 0; i < studyLogs.size(); i++) {
                if (studyLogs.get(i).getWatchedAt() != 0) {
                    VideoLogs log = new VideoLogs();
                    log.setNodeId(studyLogs.get(i).getCwid());
                    log.setStartTime(StringUtils.generateTime(studyLogs.get(i).getStartTime()));
                    log.setEndTime(StringUtils.generateTime(studyLogs.get(i).getEndTime()));
                    log.setListenTime(studyLogs.get(i).getWatchedAt() / 1000 + "");
                    list.add(log);
                }
            }
            if (list != null && list.size() > 0) {
                uploadVideo.setMemberId(userId);
                uploadVideo.setListenList(list);

                return JSON.toJSONString(uploadVideo);
            }
        }
        return "";
    }

    @Override
    public void postStudyLogSucess() {
        for (CwStudyLog cwStudyLog : studyLogs) {
            cwStudyLog.setWatchedAt(0);
            cwStudyLogDB.update(cwStudyLog);
        }
        EventBus.getDefault().post(new ContinuePlayEvent());
    }

    @Override
    public void onDestroy() {
//        mHandler.removeMessages(MyHandler.WHAT_ALPHA100);
//        mHandler.removeMessages(MyHandler.WHAT_ALPHA30);
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(connectChange);
    }

    @Subscribe
    public void onEventMainThread(PostStudyLogEvent event) {
        if(event.getType().equals("1")){
            upLoadVideosSmart();
        }else{
            upLoadVideos();
        }
    }

    private void setStatusBar(boolean flag) {
        /*if(flag){
            StatusBarCompat.setStatusBarColor(this,255);
        }else{

        }*/
        StatusBarCompat.setStatusBarColor(this, -1);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(flag) {
                StatusBarCompat.setStatusBarColor(this,255);
               // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }else{
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }*/
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(flag){
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }else{
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

//    private static class MyHandler extends Handler {
//        private final WeakReference<MainActivity> mActivity;
//        static final int WHAT_ALPHA100 = 1;
//        static final int WHAT_ALPHA30 = 2;
////        static final int WHAT_HIDE = 3;
////        static final int WHAT_SHOW = 4;
//
//        private MyHandler(MainActivity activity) {
//            mActivity = new WeakReference<MainActivity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            final MainActivity activity = mActivity.get();
//            if (activity != null) {
//                switch (msg.what){
//                    case WHAT_ALPHA100:
//                        activity.btn_system_change.setAlpha(1f);
//                        activity.btn_system_change.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activity.startActivity(new Intent(activity, SystemChangeActivity.class));
//                                activity.overridePendingTransition(R.anim.slide_in_top,android.R.anim.fade_out);
//                            }
//                        });
//                        sendEmptyMessageDelayed(WHAT_ALPHA30,2800);
//                        break;
//                    case WHAT_ALPHA30:
//                        activity.btn_system_change.setAlpha(0.3f);
//                        activity.btn_system_change.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                sendEmptyMessage(WHAT_ALPHA100);
//                            }
//                        });
//                }
//            }
//        }
//    }

}
