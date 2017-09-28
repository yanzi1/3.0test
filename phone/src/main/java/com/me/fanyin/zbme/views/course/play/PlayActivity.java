package com.me.fanyin.zbme.views.course.play;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.event.ChangeTaskStatus;
import com.me.data.event.HeadSetEvent;
import com.me.data.event.MediaSettingQualityEvent;
import com.me.data.event.MediaSettingSpeedEvent;
import com.me.data.event.NetWorkChangeEvent;
import com.me.data.event.PlayErrorEvent;
import com.me.data.event.PostNoteEvent;
import com.me.data.event.PostStudyLogEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.intel.IntelWeekTask;
import com.me.data.model.play.CourseChapter;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwClarity;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.PagerItem;
import com.me.data.model.play.PlanDialogBean;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.notes.NoteDetailsActivity;
import com.me.fanyin.zbme.views.course.notes.NotesActivity;
import com.me.fanyin.zbme.views.course.play.adapter.PlayViewpageAdapter;
import com.me.fanyin.zbme.views.course.play.db.CourseDetailDB;
import com.me.fanyin.zbme.views.course.play.db.CwStudyLogDB;
import com.me.fanyin.zbme.views.course.play.fragment.CourseCommentFragment;
import com.me.fanyin.zbme.views.course.play.fragment.CourseHandOutFragment;
import com.me.fanyin.zbme.views.course.play.fragment.CourseIntroFragment;
import com.me.fanyin.zbme.views.course.play.fragment.CourseListFragment;
import com.me.fanyin.zbme.views.course.play.fragment.FragmentPlayer;
import com.me.fanyin.zbme.views.course.play.fragment.MediaSettingFragment;
import com.me.fanyin.zbme.views.course.play.widget.HeadsetReceiver;
import com.me.fanyin.zbme.views.course.play.widget.MydViewPager;
import com.me.fanyin.zbme.views.course.play.widget.PlanSelectDialog;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.views.download.DownloadMoreFragment;
import com.me.fanyin.zbme.views.main.fragemnt.FreeCourseFragment;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.EmptyViewLayout;
import com.me.fanyin.zbme.widget.smarttablayout.SmartTabLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.utils.StringUtils;

//import com.dongao.kaoqian.phone.views.course.play.widget.PlayService;

/**
 * Created by wenpeng on 2017/3/30.
 */

public class PlayActivity extends BaseMvpActivity<PlayView, PlayPersenter> implements PlayView {
    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.play_img_evaluation)
    ImageView playImgEvaluation;
    @BindView(R.id.play_img_setting)
    ImageView playImgSetting;
    @BindView(R.id.play_img_download)
    ImageView playImgDownload;
    @BindView(R.id.course_comment_ll)
    LinearLayout courseCommentLl;
    @BindView(R.id.course_content)
    RelativeLayout courseContent;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.mall_detail_smart_tab_layout)
    SmartTabLayout mallDetailSmartTabLayout;
    @BindView(R.id.play_smart_more)
    ImageView playSmartMore;
    private PlayViewpageAdapter adapter;
    public FragmentPlayer player;
    private CourseHandOutFragment lecturef;
    private CourseListFragment coursef;
    private CourseIntroFragment introf;
    private MediaSettingFragment settingFragment;
    private CourseCommentFragment commentFragment;
    private FreeCourseFragment freeCourseFragment;
    private DownloadMoreFragment downloadMoreFragment;

    private ArrayList<PagerItem> pagerItems;
    private ArrayList<Fragment> fragments;

    public MydViewPager viewpager;

    private String userId;
    public CourseWare courseWare;//当前播放的视频
    public CourseWare cwOffLine;//离线播放的视频
    public String endTime, startime;
    private boolean alreadyStudy;
    private CourseDetail courseDetailInDB;
    private CourseDetailDB courseDetailDB;
    private int gourPosition, childPosition;//当前播放视频的位置
    private boolean isHavaChapter;//是否有章节
    boolean flag; //是否已下载
    public List<CourseWare> courseWareList;
    public CourseDetail courseDetail;
    public boolean isFromFree;//免费试听
    public boolean isFromSmart;//智能系统
    public String cwId;//收藏、取消收藏时的课件Id
    public boolean isServiceStart;
    private CwStudyLogDB cwStudyLogDB;
    private String lectureId;//继续播放视频Id
    private String examId, playPath, freeTitile;
    public boolean isPost;//是否提交评价
    public boolean isOpenComment;//是否提交评价
    private static final int ERROR = 44;
    private static final int CWS_ERROR = 45;
    public String subjectId;
    public String courseId, sSubjectId;
    public boolean isToAskNote;
    private EmptyViewLayout mEmptyLayout;
    public long seekTime = 0;
    private String[] titles = {"课程介绍", "课程目录", "课程讲义"};
    public long lectureTime = 0;
    private FragmentManager fm;
    public boolean isCanPlay = true;
    private String[] names = {"做题", "做真题", "答疑", "我会了", "下一任务", "知识点统计"};
    private int[] mipmaps = {R.mipmap.play_zuoti, R.mipmap.play_zhenti, R.mipmap.play_dayi, R.mipmap.play_know, R.mipmap.play_next, R.mipmap.play_tongji};
    private List<PlanDialogBean> lists = new ArrayList();
    private List<IntelWeekTask> tasks;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 13:
                    CourseWare freeCw = new CourseWare();
                    freeCw.setName(freeTitile);
                    freeCw.setMobileVideoUrl(playPath);
                    player.playVedio(freeCw, false);
                    break;
                case 99:
                    hideLoading();
                    break;
                case ERROR:
                    String message = (String) msg.obj;
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
                    if (player != null) {
                        if (player.mediaController != null) {
                            player.mediaController.setFavsClickable();
                        }
                    }
                    break;
                case CWS_ERROR:
                    String messag = (String) msg.obj;
                    Toast.makeText(appContext, messag, Toast.LENGTH_SHORT).show();
                    mEmptyLayout.showError();
                    break;
                default:
                    CourseWare cw = (CourseWare) msg.obj;
                    startPlayVideo(cw, false, false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        super.onCreate(savedInstnceState);
        EventBus.getDefault().register(this);
        // StatusBarCompat.compat(R.color.color_primary, this);
        initHeadSetBroadCast();
    }

    private BroadcastReceiver receiver;

    private void initHeadSetBroadCast() {
        receiver = new HeadsetReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.course_play;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(PlayActivity.this);
    }

    @Override
    public void initView() {
        fm = getSupportFragmentManager();
        isFromSmart=SharedPrefHelper.getInstance().isIntel();
        isFromFree = getIntent().getBooleanExtra("isFreePlay", false);
        if (!isFromFree && !isFromSmart) {
            userId = SharedPrefHelper.getInstance().getUserId();
            courseDetailDB = new CourseDetailDB();
            cwStudyLogDB = new CwStudyLogDB();

            playImgSetting.setOnClickListener(this);
            courseCommentLl.setOnClickListener(this);
            playImgDownload.setOnClickListener(this);

            fragments = new ArrayList();
            pagerItems = new ArrayList();

            commentFragment = new CourseCommentFragment();
            settingFragment = new MediaSettingFragment();
            downloadMoreFragment = new DownloadMoreFragment();
        } else if (isFromSmart) {
            playSmartMore.setVisibility(View.VISIBLE);
            playSmartMore.setOnClickListener(this);

            courseWareList = new ArrayList<>();
            tasks = (List<IntelWeekTask>) getIntent().getSerializableExtra("cws");
            lectureId = getIntent().getStringExtra("lectureId");
            subjectId = getIntent().getStringExtra("subjectId");
            childPosition = getIntent().getIntExtra("position", 0);
            reSetSmartDatas(tasks);
        } else {
            lectureId = getIntent().getStringExtra("lectureId");
            examId = getIntent().getStringExtra("examId");
            freeTitile = getIntent().getStringExtra("title");
            playPath = getIntent().getStringExtra(Constants.LINK);

            freeCourseFragment = new FreeCourseFragment();
            container.setVisibility(View.GONE);
        }
        initPlayer();
        mEmptyLayout = new EmptyViewLayout(this, llEmpty);
        mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
        mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        mEmptyLayout.setOtherOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOtherUI();
            }
        });
    }

    private void reSetSmartDatas(List<IntelWeekTask> tasks) {
        courseWareList.clear();
        for (int i = 0; i < tasks.size(); i++) {
            IntelWeekTask weekTask = tasks.get(i);
            CourseWare cw = new CourseWare();
            cw.setName(weekTask.getNodeName());
            cw.setId(weekTask.getKpId());
            cw.setStatus(weekTask.getStudyStatus() + "");
            cw.setsSubjectId(subjectId);
            cw.setClassId("0");
            courseWareList.add(cw);
        }
    }

    @Override
    public void initData() {
        if (!isFromFree && !isFromSmart) {
            cwOffLine = (CourseWare) getIntent().getSerializableExtra("cwBean");
            alreadyStudy = getIntent().getBooleanExtra("alreadyStudy", true);
            if (cwOffLine == null) {   //在线
                lectureId = getIntent().getStringExtra("lectureId");
                courseId = getIntent().getStringExtra("courseId");
                sSubjectId = getIntent().getStringExtra("sSubjectId");
                endTime = getIntent().getStringExtra("endTime");
                startime = getIntent().getStringExtra("startime");
                if (NetWorkUtils.isConnected(this)) {
                    showLoading();
                    mPresenter.getData();
                } else {
                    courseDetailInDB = courseDetailDB.find(userId, sSubjectId, courseId);
                    if (courseDetailInDB != null) {
                        String content = courseDetailInDB.getContentJson();
                        JSONObject object = JSON.parseObject(content);
                        String resultList = object.getString("resultList");
                        List<CourseChapter> result = JSON.parseArray(resultList, CourseChapter.class);
                        courseDetailInDB.setResultList(result);
                        if (!isDestory) {
                            initAdapter(courseDetailInDB);
                        }
                    } else {
                        DialogManager.showOneButtonDialog(context(), new DialogManager.OneButtonDialogListener() {
                            @Override
                            public void dialogBtClick() {
                                finish();
                            }
                        }, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
                    }
                }
            } else {  //离线
                sSubjectId = cwOffLine.getsSubjectId();
                courseId = cwOffLine.getClassId();
                courseDetailInDB = courseDetailDB.find(userId, sSubjectId, courseId);
                if (courseDetailInDB != null) {
                    String content = courseDetailInDB.getContentJson();
                    JSONObject object = JSON.parseObject(content);
                    String resultList = object.getString("resultList");
                    List<CourseChapter> result = JSON.parseArray(resultList, CourseChapter.class);
                    courseDetailInDB.setResultList(result);
                    if (!isDestory) {
                        initAdapter(courseDetailInDB);
                    }
                } else {
                    startPlayVideo(cwOffLine, true, false);
                }
            }
        } else if (isFromSmart) {
            courseWare = courseWareList.get(childPosition);
            if(NetWorkUtils.isNetworkAvailable(this)){
                showLoading();
                mPresenter.getSmartPlayUrl(courseWare, false);
            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player.showNetError(getResources().getString(R.string.no_network), "0", "1");
                    }
                }, 1000);
                mEmptyLayout.showNetErrorView();
            }
        } else {
            //这里要添加试听课程接口
            lectureId = getIntent().getStringExtra("lectureId");
            examId = getIntent().getStringExtra("examId");
            freeTitile = getIntent().getStringExtra("title");
            playPath = getIntent().getStringExtra(Constants.LINK);
        }
    }

    private boolean onNewIntent;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onNewIntent = true;
        if(isFromSmart){
            tasks = (List<IntelWeekTask>) getIntent().getSerializableExtra("cws");
            lectureId = intent.getStringExtra("lectureId");
            subjectId = intent.getStringExtra("subjectId");
            childPosition = intent.getIntExtra("position", 0);
            reSetSmartDatas(tasks);
            courseWare=courseWareList.get(childPosition);
            holdToPlaySmart(courseWare,childPosition,false);
        }else{
            lectureId = intent.getStringExtra("lectureId");
            startime = intent.getStringExtra("startime");
            if (player.mVideoView != null) {
                if (player.mVideoView.isPlaying()) {
                    player.mVideoView.pause();
                }
            }
            if (isHavaChapter) {
                courseWare = chooseCourseWare(courseDetail, lectureId);
            } else {
                courseWare = chooseCourseWare(courseWareList, lectureId);
            }
            holdToPlay(courseWare, gourPosition, childPosition, false);
        }
    }

    @Override
    public void showLoading() {
        mEmptyLayout.showLoading();
    }

    @Override
    public void hideLoading() {
        mEmptyLayout.showContentView();
    }

    @Override
    public String getCourseId() {
        return courseId;
    }

    public int getGourPosition() {
        return gourPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public boolean isHaveChapter() {
        return isHavaChapter;
    }

    private void initPlayer() {
        Bundle bundle = new Bundle();
        player = FragmentPlayer.getInstance(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.media_play_fragment, player);
        if (isFromFree) {
            Bundle args = new Bundle();
            args.putString("lectureId", lectureId);
            args.putString("examId", examId);
            freeCourseFragment.setArguments(args);
            ft.add(R.id.media_setting, freeCourseFragment);
            handler.sendEmptyMessage(13);
            ft.show(freeCourseFragment);
        } else if (isFromSmart) {
            lecturef = CourseHandOutFragment.getInstance("");
            ft.add(R.id.media_setting, lecturef);
        } else {
            ft.add(R.id.media_setting, settingFragment);
            ft.hide(settingFragment);
        }
        ft.commit();
    }

    @Override
    public void showSmartContent(final String lectureId) {
//        FragmentTransaction ft = fm.beginTransaction();
//        if(lecturef==null){
//            lecturef = CourseHandOutFragment.getInstance(ParamsUtils.getSmartLectureUrl(lectureId));
//            ft.add(R.id.media_setting, lecturef);
//        }else{
//            lecturef.setCourseHandOutUrl(ParamsUtils.getSmartLectureUrl(lectureId));
//        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lecturef.setCourseHandOutUrl(ParamsUtils.getSmartLectureUrl(lectureId));
            }
        },500);
    }

    @Override
    public void initAdapter(CourseDetail courseDetail) {
        hideLoading();
        this.courseDetail = courseDetail;
        subjectId = courseDetail.getSubjectId();
        if (courseDetail.getResultList().size() > 1) {    //包含章节
            isHavaChapter = true;
            initFragment(courseDetail, true);
            showData(courseDetail);
        } else {
            isHavaChapter = false;
            initFragment(courseDetail, false);
            showCwData(courseDetail.getResultList().get(0).getPcClientCourseWareList());
        }
    }


    private void initFragment(CourseDetail courseDetail, boolean isHavaChapter) {
        try {
            String url = "";
            courseWareList = courseDetail.getResultList().get(0).getPcClientCourseWareList();
            if (introf == null) {
                introf = CourseIntroFragment.getInstance(courseDetail.getDescription());
                coursef = CourseListFragment.getInstance(courseDetail, isHavaChapter);
                if (cwOffLine != null) {
                    courseWare = cwOffLine;
                    if (isHavaChapter) {
                        chooseCourseWare(courseDetail, courseWare.getId());
                    } else {
                        chooseCourseWare(courseWareList, courseWare.getId());
                    }
                    url = DownloadManager.getInstance().getLecturePath(courseWare);
                } else {
                    if (isHavaChapter) {
                        if (!TextUtils.isEmpty(lectureId)) {
                            courseWare = chooseCourseWare(courseDetail, lectureId);
                        } else {
                            courseWare = chooseCourseWare(courseDetail);
                        }
                    } else {
                        if (!TextUtils.isEmpty(lectureId)) {
                            courseWare = chooseCourseWare(courseWareList, lectureId);
                        } else {
                            courseWare = chooseCourseWare(courseWareList);
                        }
                    }
                    url = ParamsUtils.getLectureUrl(courseWare.getId());
                }
                lecturef = CourseHandOutFragment.getInstance(url);
                fragments.add(introf);
                fragments.add(coursef);
                fragments.add(lecturef);

                for (int i = 0; i < titles.length; i++) {
                    pagerItems.add(new PagerItem(titles[i], fragments.get(i)));
                }
            } else {
                introf.setCourseInfo(courseDetail.getDescription());
                if (!isHavaChapter) {
                    coursef.showCwData(courseWareList);
                } else {
                    coursef.showGroupData(courseDetail);
                }
                coursef.notifyAdapter();

                if (cwOffLine != null) {
                    courseWare = cwOffLine;
                    if (isHavaChapter) {
                        chooseCourseWare(courseDetail, courseWare.getId());
                    } else {
                        chooseCourseWare(courseWareList, courseWare.getId());
                    }
                    url = DownloadManager.getInstance().getLecturePath(courseWare);
                } else {
                    if (isHavaChapter) {
                        if (!TextUtils.isEmpty(lectureId)) {
                            courseWare = chooseCourseWare(courseDetail, lectureId);
                        } else {
                            courseWare = chooseCourseWare(courseDetail);
                        }
                    } else {
                        if (!TextUtils.isEmpty(lectureId)) {
                            courseWare = chooseCourseWare(courseWareList, lectureId);
                        } else {
                            courseWare = chooseCourseWare(courseWareList);
                        }
                    }
                    url = ParamsUtils.getLectureUrl(courseWare.getId());
                }

                lecturef.setCourseHandOutUrl(url);
            }
            courseWare.setMobileLectureUrl(url);

            viewpager = (MydViewPager) findViewById(R.id.play_viewpger);
            adapter = new PlayViewpageAdapter(getSupportFragmentManager(), pagerItems);
            viewpager.setAdapter(adapter);
            mallDetailSmartTabLayout.setViewPager(viewpager);
            viewpager.setOffscreenPageLimit(3);
            if (alreadyStudy) {
                viewpager.setCurrentItem(2);
            }
        } catch (Exception e) {

        }
    }

    public void showCwData(List<CourseWare> courseWareList) {
        if (courseWareList.size() > 0) {
            this.courseWareList = courseWareList;
            if (cwOffLine != null) {
                courseWare = cwOffLine;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startPlayVideo(cwOffLine, true, false);
                    }
                }, 1000);
            } else {
                boolean isDownlaod = DownloadManager.getInstance().getInstance().isCWDownlaoded(courseWare);
                if (isDownlaod) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startPlayVideo(courseWare, true, false);
                        }
                    }, 1000);
                } else {
                    if (!NetWorkUtils.isConnected(this)) {
                        lecturef.showNetError();
//                        DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.showNetError(getResources().getString(R.string.no_network), "0", "1");
                            }
                        }, 1000);
                    } else {
                        mPresenter.getPlayUrl(courseWare, false);
                    }
                }
            }
        } else {
            DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                @Override
                public void confirm() {
                    finish();
                }

                @Override
                public void cancel() {
                    finish();
                }
            }, "课程暂未开放", 0, null, null);
        }
    }

    public void refreshPlay() {
        if (NetWorkUtils.isConnected(this)) {
            if (courseWare != null) {
                mPresenter.getPlayUrl(courseWare, isServiceStart);
            }
        }
    }

    public void showData(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
        if (cwOffLine != null) {
            courseWare = cwOffLine;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startPlayVideo(cwOffLine, true, false);
                }
            }, 1000);
        } else {
            boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseWare);
            if (isDownlaod) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startPlayVideo(courseWare, true, false);
                    }
                }, 1000);
            } else {
                if (!NetWorkUtils.isConnected(this)) {
                    lecturef.showNetError();
//                    DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player.showNetError(getResources().getString(R.string.no_network), "0", "1");
                        }
                    }, 1000);
                } else {
                    mPresenter.getPlayUrl(courseWare, false);
                }
            }
        }
    }

    /**
     * 选择要播放的课件
     *
     * @param courseWareList
     * @return
     */
    private CourseWare chooseCourseWare(List<CourseWare> courseWareList) {
        String cwId = "";
        List<CourseWare> studyLogs = cwStudyLogDB.getStudyLogCw(userId, courseId);
        if (studyLogs != null && studyLogs.size() > 0) {
            for (int i = 0; i < studyLogs.size(); i++) {
                if (courseWareList.contains(studyLogs.get(i))) {
                    cwId = studyLogs.get(i).getId();
                    break;
                }
            }
            if (!TextUtils.isEmpty(cwId)) {
                for (int k = 0; k < courseWareList.size(); k++) {
                    if (courseWareList.get(k).getId().equals(cwId)) {
                        childPosition = k;
                        return courseWareList.get(k);
                    }
                }
            } else {
                return courseWareList.get(0);
            }
        } else {
            return courseWareList.get(0);
        }

        return courseWareList.get(0);
    }

    /**
     * 选择要播放的课件
     *
     * @param courseWareList
     * @return
     */
    private CourseWare chooseCourseWare(List<CourseWare> courseWareList, String cwId) {

        for (int k = 0; k < courseWareList.size(); k++) {
            if (courseWareList.get(k).getId().equals(cwId)) {
                childPosition = k;
                return courseWareList.get(k);
            }
        }
        return courseWareList.get(0);
    }

    /**
     * 选择要播放的课件
     *
     * @param courseDetail
     * @return
     */
    private CourseWare chooseCourseWare(CourseDetail courseDetail) {
        String cwId = "";
        List<CourseWare> studyLogs = cwStudyLogDB.getStudyLogCw(userId, courseId);
        if (studyLogs != null && studyLogs.size() > 0) {

            for (int i = 0; i < studyLogs.size(); i++) {
                if (!TextUtils.isEmpty(cwId)) {
                    break;
                }
                for (int j = 0; j < courseDetail.getResultList().size(); j++) {
                    List<CourseWare> courseWareList = courseDetail.getResultList().get(j).getPcClientCourseWareList();
                    if (courseWareList.contains(studyLogs.get(i))) {
                        gourPosition = j;
                        cwId = studyLogs.get(i).getId();
                        break;
                    }
                }
            }
            if (!TextUtils.isEmpty(cwId)) {
                for (int k = 0; k < courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().size(); k++) {
                    if (courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(k).getId().equals(cwId)) {
                        childPosition = k;
                        return courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(k);
                    }
                }
            } else {
                return courseDetail.getResultList().get(0).getPcClientCourseWareList().get(0);
            }
        } else {
            return courseDetail.getResultList().get(0).getPcClientCourseWareList().get(0);
        }

        return courseDetail.getResultList().get(0).getPcClientCourseWareList().get(0);
    }

    /**
     * 选择要播放的课件
     *
     * @param courseDetail
     * @return
     */
    private CourseWare chooseCourseWare(CourseDetail courseDetail, String cwId) {
        CourseWare cw = new CourseWare();
        cw.setId(cwId);

        for (int j = 0; j < courseDetail.getResultList().size(); j++) {
            List<CourseWare> courseWareList = courseDetail.getResultList().get(j).getPcClientCourseWareList();
            if (courseWareList.contains(cw)) {
                gourPosition = j;
                break;
            }
        }

        for (int k = 0; k < courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().size(); k++) {
            if (courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(k).getId().equals(cwId)) {
                childPosition = k;
                return courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(k);
            }
        }
        return courseDetail.getResultList().get(0).getPcClientCourseWareList().get(0);
    }

    /**
     * 点击item 播放视频
     *
     * @param flag       //是否播放mp3
     * @param courseWare
     */
    public void holdToPlay(CourseWare courseWare, int gourPosition, int childPosition, boolean flag) {
        if (this.courseWare == null || !this.courseWare.getId().equals(courseWare.getId()) || player.getErrorIsShow() || onNewIntent) {
            setIsAllowPlay();
            onNewIntent = false;
            this.courseWare = courseWare;
            this.gourPosition = gourPosition;
            this.childPosition = childPosition;
            coursef.notifyAdapter();

            if (!flag) {
                if (player.mVideoView.isPlaying()) {
                    player.mVideoView.stopPlayback();
                    player.pb.setVisibility(View.VISIBLE);
                }
            }

            //这里要加视频是否已下载的逻辑
            boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseWare);
            if (isDownlaod) {
                startPlayVideo(courseWare, true, flag);
            } else {
                if (NetWorkUtils.isConnected(this)) {
                    mPresenter.getPlayUrl(courseWare, flag);
                } else {
                    player.pb.setVisibility(View.GONE);
                    lecturef.showNetError();
                    player.showNetError(getResources().getString(R.string.no_network), "0", "1");
//                    DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
                }
            }
        }
    }

    public void showPlayNetError() {
        if (player != null) {
            player.showNetError(getResources().getString(R.string.no_network), "0", "1");
        }
    }

    public void setIsAllowPlay() {
        player.isAllowPlay = false;
    }

    /**
     * 音频转视频调用
     *
     * @param courseWare
     * @param flag
     */
    public void holdToPlay(CourseWare courseWare, boolean flag) {
        if (player.mVideoView != null) {
            seekTime = player.mVideoView.getCurrentPosition();
        }
        this.courseWare = courseWare;
        coursef.notifyAdapter();

        //这里要加视频是否已下载的逻辑
        boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseWare);
        if (isDownlaod) {
            startPlayVideo(courseWare, true, flag);
        } else {
            if (NetWorkUtils.isConnected(this)) {
                String playPath = mPresenter.getPath(courseWare) + "online.m3u8";
                File file = new File(playPath);
                if (file.exists()) {
                    startPlayVideo(courseWare, false, flag); //此处不用重新请求播放接口
                } else {
                    mPresenter.getPlayUrl(courseWare, flag);
                }
            } else {
                player.pb.setVisibility(View.GONE);
                lecturef.showNetError();
                DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
            }
        }

    }

    /**
     * @param courseWare
     * @param isOffline  是否为离线播放
     * @param flag       是否为mp3播放
     */
    @Override
    public void startPlayVideo(CourseWare courseWare, boolean isOffline, boolean flag) {
        this.courseWare = courseWare;
        if (!isFromSmart) {
            if (isOffline) {
                lecturef.setCourseHandOutUrl(DownloadManager.getInstance().getLecturePath(courseWare));
            } else {
                lecturef.setCourseHandOutUrl(courseWare.getMobileLectureUrl());
            }
        }

        player.playVedio(courseWare, flag);
    }

    public void IsCollect(CourseWare courseWare) {
        if (NetWorkUtils.isConnected(this)) {
            mPresenter.getIsCollect(courseWare);
        }
    }

    @Subscribe
    public void onEventMainThread(NetWorkChangeEvent event) {
        String type=event.getType();
        if (!isFromFree) {
            if (courseWare != null) {
                boolean flag = DownloadManager.getInstance().isCWDownlaoded(courseWare);
                if (!flag) {
                    if (player != null) {
                        if (type.equals("1")) {
                            player.showNetError("继续播放会消耗流量", "1", "2");
                            DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                                @Override
                                public void confirm() {
                                    player.isAllowPlay = true;
                                    refreshPlay();
                                }

                                @Override
                                public void cancel() {

                                }
                            }, "当前已切换至2G/3G/4G网络，继续播放会消耗您的流量，可能产生资费", 0, null, null);
                        } else {
                            player.showNetError(getResources().getString(R.string.no_network), "0", "2");
                        }
                    }
                }
            }
        } else {
            if (type.equals("0")) {
                if (player != null) {
                    player.showNetError(getResources().getString(R.string.no_network), "0", "2");
                }
            } else if(type.equals("1")){
                if (player != null) {
                    if (player.mVideoView != null) {
                        seekTime = player.mVideoView.getCurrentPosition();
                    }
                    player.showNetError("继续播放会消耗流量", "1", "2");
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            player.isAllowPlay = true;
                            player.play(false);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, "当前已切换至2G/3G/4G网络，继续播放会消耗您的流量，可能产生资费", 0, null, null);
                }
            }
        }
    }

    @Subscribe
    public void onEventMainThread(HeadSetEvent event) {
        if (player.mVideoView != null) {
            if (player.mVideoView.isPlaying()) {
                player.mVideoView.pause();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(PlayErrorEvent event) {
        player.playError();
    }

    @Subscribe
    public void onEventMainThread(MediaSettingQualityEvent event) {
        String beforeQuality = event.getBeforeQuality();

        if (player.mVideoView != null && player.mediaController != null && player.mediaController.courseWare != null) {
            String quality = SharedPrefHelper.getInstance().getPlayQuaity();
            CwClarity qualit = new CwClarity();
            qualit.setName(quality);
            List<CwClarity> list = player.mediaController.courseWare.getClaritys();
            if (list != null) {
                if (list.contains(qualit)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(quality)) {
                            player.mediaController.courseWare.setQualityName(list.get(i).getName());
                            swichQuaity(player.mediaController.courseWare, list.get(i).getPath());
                            return;
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(beforeQuality)) {
                        SharedPrefHelper.getInstance().setPlayQuaity(beforeQuality);
                        if (settingFragment != null) {
                            settingFragment.refreshData();
                        }
                    }
                    Toast.makeText(appContext, "该讲次不存在选择的清晰度", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!TextUtils.isEmpty(beforeQuality)) {
                    SharedPrefHelper.getInstance().setPlayQuaity(beforeQuality);
                    if (settingFragment != null) {
                        settingFragment.refreshData();
                    }
                }
                Toast.makeText(appContext, "该讲次不存在选择的清晰度", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void refreshSetting() {
        if (settingFragment != null) {
            settingFragment.refreshData();
        }
    }

    @Subscribe
    public void onEventMainThread(MediaSettingSpeedEvent event) {

        if (player.mVideoView != null) {
            player.mVideoView.playSpeed(SharedPrefHelper.getInstance().getPlaySpeed());
            player.mediaController.speedAdapterRefresh();
        }
    }

    @Subscribe
    public void onEventMainThread(PostNoteEvent event) {
        String noteId = event.getNoteId();
        String hanConId = event.getHanConId();
        String type = event.getType();

        final org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("hanConId", hanConId);
            object.put("noteId", noteId);
            object.put("type", type);
        } catch (Exception e) {

        }

        lecturef.callNote(object);
        if (player.mediaController != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.mediaController.callNote(object);
                }
            }, 5000);
        }
    }

    public void seekTo(long time) {
        if (player.mVideoView != null) {
            if(time+20000>=player.mVideoView.getDuration()){
                player.mediaController.isSeekEnd=true;
            }else{
                player.mediaController.isSeekEnd=false;
            }
            player.mVideoView.seekTo(time);
        }
    }

    public void seekToTime(long time, String type) {
        if (!isFromFree) {
            if (player.mediaController != null) {
                player.mediaController.callLecture(time, type);
            }
            if (lecturef != null) {
                lecturef.callTime(time, type);
            }
        }
    }

    public void setPlayIstart() {
        if (player != null) {
            player.isStart = false;
        }
    }

    public void playPause() {
        isCanPlay = false;
        if (player.mVideoView != null && player.mVideoView.isPlaying()) {
            player.mVideoView.pause();
        }
    }

    /**
     * isCanPlay 感觉写在activity中比较好
     */
    public void playStart() {
        isCanPlay = true;
        if (player.mVideoView != null) {
            player.mVideoView.start();
        }
    }

    public void gotoNote(Object data) {
        if (!NetWorkUtils.isConnected(this)) {
            Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
            return;
        }

        String result = data.toString();
        JSONObject object = JSON.parseObject(result);
        String hanConId = object.getString("id");
        String type = object.getString("type");
        String noteId = object.getString("noteId");
        if (type.equals("0")) {
            NoteClassDetail noteClassDetail = new NoteClassDetail();
            noteClassDetail.setHanConId(hanConId);
            noteClassDetail.setSubjectId(subjectId);
            noteClassDetail.setsSubjectId(sSubjectId);
            noteClassDetail.setCourseId(courseId);
            noteClassDetail.setLectrueId(courseWare.getId());
            noteClassDetail.setCoursewareTime(getCourseWareTime());

            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra("from", "3");
            intent.putExtra("noteClassDetail", noteClassDetail);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra("detailFrom", "1");
            intent.putExtra("noteId", noteId);
            startActivity(intent);
        }
    }

    public void gotoAsk(Object data) {
        if (!NetWorkUtils.isConnected(this)) {
            Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
            return;
        }

        String result = data.toString();
        JSONObject object = JSON.parseObject(result);
        String hanConId = object.getString("id");

//        Intent intent = new Intent(this, QueryRecomendActivity.class);
//        intent.putExtra("type", 3);
//        intent.putExtra("hanConId", hanConId);
//        intent.putExtra("sSubjectId", sSubjectId);
//        Bundle bundle = new Bundle();
//        bundle.putString("courseId", courseId);
//        bundle.putString("coursewareId", courseWare.getId());
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    @Override
    public void playError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void downloadCourseWare(final CourseWare courseWare) {

        if (DownloadManager.getInstance().getInstance().isCWDownlaoded(courseWare)) {
            ToastBarUtils.showToast(context(), "已下载完成");
            return;
        }

        if (DownloadManager.getInstance().getInstance().isAddDownloadTask(courseWare)) {
            ToastBarUtils.showToast(context(), "正在下载");
            return;
        }
        String type = NetWorkUtils.getNetworkTypeName(context());
        if (type.equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {//
            DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
        } else if (type.equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
            if (DownloadManager.getInstance().haveEnoughSpace()) {

                switch (SharedPrefHelper.getInstance().getPlayQuaity()) {
                    case "流畅":
                        MobclickAgent.onEvent(context(), Statistics.COURSE_FULLSCREEN_DOWNLOADFLUENT);
                        break;
                    case "标清":
                        MobclickAgent.onEvent(context(), Statistics.COURSE_FULLSCREEN_DOWNLOADSD);
                        break;
                    case "高清":
                        MobclickAgent.onEvent(context(), Statistics.COURSE_FULLSCREEN_DOWNLOADHD);
                        break;
                }

                mPresenter.downloadCw(courseWare);

            } else {
                DialogManager.showOneButtonDialog(context(), null, "您的存储空间不足，无法下载该视频", 0, null);
            }
        } else {
            if (DownloadManager.getInstance().haveEnoughSpace()) {

                //3/4G
                if (SharedPrefHelper.getInstance().canUseIn4G()) //允许34g下
                {
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            mPresenter.downloadCw(courseWare);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);

                } else {
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            SharedPrefHelper.getInstance().set4GCanUse(true);
                            DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                                @Override
                                public void confirm() {
                                    mPresenter.downloadCw(courseWare);
                                }

                                @Override
                                public void cancel() {

                                }
                            }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, context().getResources().getString(R.string.dialog_warnning_download), 0, null, null);
                }

            } else {
                DialogManager.showOneButtonDialog(context(), null, "您的存储空间不足，无法下载该视频", 0, null);
            }
        }
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.course_note_in, R.anim.course_note_out);
        switch (view.getId()) {
            case R.id.play_img_setting:
                ft.replace(R.id.media_setting, settingFragment);
                ft.show(settingFragment);
                ft.commit();
                break;
            case R.id.course_comment_ll:
                if (!NetWorkUtils.isConnected(this)) {
                    Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.isCommented(courseWare);
                break;
            case R.id.play_img_download:
                if (this.courseDetail != null) {
                    ft.replace(R.id.media_setting, downloadMoreFragment);
                    ft.show(downloadMoreFragment);
                    ft.commit();
                    downloadMoreFragment.setDetail(courseDetail, courseWare);
                }
                break;
            case R.id.play_smart_more:
                lists.clear();
                CourseWare cwsmart=courseWareList.get(childPosition);
                if (cwsmart.getStatus().equals("1")) {
                    names[3] = "已掌握";
                    mipmaps[3] = R.mipmap.play_know;
                } else {
                    names[3] = "我会了";
                    mipmaps[3] = R.mipmap.play_known;
                }
                for (int i = 0; i < names.length; i++) {
                    PlanDialogBean bean = new PlanDialogBean();
                    bean.setName(names[i]);
                    bean.setMipmap(mipmaps[i]);
                    lists.add(bean);
                }
                playSmartMore.setVisibility(View.GONE);
                PlanSelectDialog dialog=showDialog(new PlanSelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 2:
                                Bundle bundle = new Bundle();
                                bundle.putString("nodeId", courseWare.getId());
//                                gotoActivity(QueryRecomendActivity.class, false, bundle);
                                break;
                            case 3:
                                if (NetWorkUtils.isNetworkAvailable(PlayActivity.this)) {
                                    if (!courseWare.getStatus().equals("1")) {
                                        mPresenter.changeStatus(courseWare.getId(),childPosition);
                                    }
                                } else {
                                    Toast.makeText(PlayActivity.this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:
                                if (childPosition < courseWareList.size() - 1) {
                                    holdToPlaySmart(courseWareList.get(childPosition + 1), childPosition + 1, flag);
                                } else {
                                    Toast.makeText(PlayActivity.this, "当前为本周最后一个任务", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 5:
                                break;
                        }
                    }
                }, new PlanSelectDialog.SelectDialogCancelListener() {
                    @Override
                    public void onCancelClick() {
                        playSmartMore.setVisibility(View.VISIBLE);
                    }
                }, lists);
                break;
        }
    }

    private void showOtherUI(){
        lists.clear();
        CourseWare cwsmart=courseWareList.get(childPosition);
        if (cwsmart.getStatus().equals("1")) {
            names[3] = "已掌握";
            mipmaps[3] = R.mipmap.play_know;
        } else {
            names[3] = "我会了";
            mipmaps[3] = R.mipmap.play_known;
        }
        for (int i = 0; i < names.length; i++) {
            PlanDialogBean bean = new PlanDialogBean();
            bean.setName(names[i]);
            bean.setMipmap(mipmaps[i]);
            lists.add(bean);
        }
        final ImageView imageView=mEmptyLayout.getPlayImage();
        imageView.setVisibility(View.GONE);
        PlanSelectDialog dialog=showDialog(new PlanSelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 2:
                        Bundle bundle = new Bundle();
                        bundle.putString("nodeId", courseWare.getId());
//                        gotoActivity(QueryRecomendActivity.class, false, bundle);
                        break;
                    case 3:
                        if (NetWorkUtils.isNetworkAvailable(PlayActivity.this)) {
                            if (!courseWare.getStatus().equals("1")) {
                                mPresenter.changeStatus(courseWare.getId(),childPosition);
                            }
                        } else {
                            Toast.makeText(PlayActivity.this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if (childPosition < courseWareList.size() - 1) {
                            holdToPlaySmart(courseWareList.get(childPosition + 1), childPosition + 1, flag);
                        } else {
                            Toast.makeText(PlayActivity.this, "当前为本周最后一个任务", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        break;
                }
            }
        }, new PlanSelectDialog.SelectDialogCancelListener() {
            @Override
            public void onCancelClick() {
                imageView.setVisibility(View.VISIBLE);
            }
        }, lists);
    }

    @Override
    public void showEmptyPlay(String msg) {
        mEmptyLayout.setOtherData("该知识点下无视频");
        mEmptyLayout.showOtherErrorView();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.showOtherError("该知识点下无视频");
            }
        },500);
    }

    @Override
    public void studyKnow(int postion) {
        courseWareList.get(postion).setStatus("1");
        tasks.get(postion).setStudyStatus(1);
        Toast.makeText(appContext, "成功", Toast.LENGTH_SHORT).show();
        ChangeTaskStatus changeTaskStatus=new ChangeTaskStatus();
        changeTaskStatus.setPosition(postion);
        changeTaskStatus.setNodeId(courseWareList.get(postion).getId());
        EventBus.getDefault().post(new ChangeTaskStatus());
    }

    @Override
    public void openComment() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.course_note_in, R.anim.course_note_out);
        ft.replace(R.id.media_setting, commentFragment);
        ft.show(commentFragment);
        ft.commit();
        isOpenComment = true;
    }

    public void hideSetting(Fragment fragment) {
        isPost = false;
        isOpenComment = false;
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.course_note_in, R.anim.course_note_out);
        ft.hide(fragment);
        ft.commit();
    }

    public void isFinish() {
        if (isOpenComment) {
            if (isPost) {
                finish();
            } else {
                if (TextUtils.isEmpty(commentFragment.getComment())) {
                    finish();
                } else {
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            finish();
                        }

                        @Override
                        public void cancel() {

                        }
                    }, "您的评价还未提交，是否确认离开？", 0, null, null);

                }
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean isSmart() {
        return isFromSmart;
    }

    @Override
    public void showError(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        msg.what = ERROR;
        handler.sendMessage(msg);
        hideLoading();
    }

    @Override
    public void showCwsError(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        msg.what = CWS_ERROR;
        handler.sendMessage(msg);
//        hideLoading();
    }

    public void swichQuaity(CourseWare courseWare, String path) {
        if (player.mVideoView.isPlaying()) {
            player.mVideoView.stopPlayback();
            player.pb.setVisibility(View.VISIBLE);
        }
        mPresenter.downloadM3U8(courseWare, path, courseWare.getJm());
    }

    public void collectCw(CourseWare cw) {
        mPresenter.collectCw(cw);
    }

    public void delCollectCw(CourseWare cw) {
        mPresenter.delCollectCw(cw);
    }

    @Override
    public void collectCwResult() { //收藏成功
        isCollect = "true";
        if (isHavaChapter) {
            courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(childPosition).setIsCollect(true);
            coursef.showGroupData(courseDetail);
        } else {
            courseWareList.get(childPosition).setIsCollect(true);
            coursef.showCwData(courseWareList);
        }
        player.reSetIsCollect(true);
    }

    @Override
    public void delCwResult() { //取消收藏
        isCollect = "false";
        if (isHavaChapter) {
            courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(childPosition).setIsCollect(false);
            coursef.showGroupData(courseDetail);
        } else {
            courseWareList.get(childPosition).setIsCollect(false);
            coursef.showCwData(courseWareList);
        }
        player.reSetIsCollect(false);
    }

    public String isCollect = "";

    @Override
    public void isCollectResult(String result) {
        this.isCollect = result;
        player.mediaController.setCwCollection(result);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            player.onBack();
        } else if (isOpenComment) {
            isFinish();
        } else {
            super.onBackPressed();
        }
    }

    public void setWebViewProgress(long timePosition) {
        if (!isFromFree) {
            lecturef.setWebViewProgress(timePosition);
        }
    }

    public void webSeekTo(long time) {
        player.mVideoView.seekTo(time);
    }

    public void analysisDNS(CourseWare courseWare) {
        mPresenter.analysisIp(courseWare);
    }

    public String getCourseWareTime() {
        String currentime = "";
        if (player.mVideoView != null) {
            long time = player.mVideoView.getCurrentPosition();
            currentime = StringUtils.generateTime(time);
        }
        return currentime;
    }

    private boolean isDestory;

    @Override
    public void onDestroy() {
        mPresenter.postOperat();
        SharedPrefHelper.getInstance().setIsPlayOnet(false);
        isDestory = true;
        EventBus.getDefault().unregister(this);
        upLoadVideos();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void upLoadVideos() {
        if (isFromFree) {
            return;
        }

        if (isFromSmart) {
            EventBus.getDefault().post(new PostStudyLogEvent("1"));
        } else {
            EventBus.getDefault().post(new PostStudyLogEvent("2"));
        }
    }

    @Subscribe
    public void onEventMainThread(ChangeTaskStatus event) {
        int postion=event.getPosition();
        courseWareList.get(postion).setStatus("1");
        tasks.get(postion).setStudyStatus(1);
    }

    /**
     * @param flag true 播放mp3 false 播放m3u8
     */
    public void playNextVideo(boolean flag) {
        if (isFromFree) {
            return;
        }
        if (isFromSmart) {
//            if(childPosition<courseWareList.size()-1){
//                holdToPlaySmart(courseWareList.get(childPosition+1),childPosition+1,flag);
//            }else{
//                Toast.makeText(this, "当前讲次已是最后一讲", Toast.LENGTH_SHORT).show();
//            }
            return;
        }
        if (isHaveChapter()) {
            if (childPosition < courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().size() - 1) {
                boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(childPosition + 1));
                if (isDownlaod) {
                    holdToPlay(courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(childPosition + 1), gourPosition, (childPosition + 1), flag);
                } else {
                    if (!NetWorkUtils.isConnected(this)) {
                        showPlayNetError();
                        return;
                    } else {
                        holdToPlay(courseDetail.getResultList().get(gourPosition).getPcClientCourseWareList().get(childPosition + 1), gourPosition, (childPosition + 1), flag);
                    }
                }
            } else if (gourPosition < courseDetail.getResultList().size() - 1) {
                boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseDetail.getResultList().get(gourPosition + 1).getPcClientCourseWareList().get(0));
                if (isDownlaod) {
                    holdToPlay(courseDetail.getResultList().get(gourPosition + 1).getPcClientCourseWareList().get(0), (gourPosition + 1), 0, flag);
                } else {
                    if (!NetWorkUtils.isConnected(this)) {
                        showPlayNetError();
                        return;
                    } else {
                        holdToPlay(courseDetail.getResultList().get(gourPosition + 1).getPcClientCourseWareList().get(0), (gourPosition + 1), 0, flag);
                    }
                }
            } else {
                Toast.makeText(this, "当前讲次已是最后一讲", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (childPosition < courseWareList.size() - 1) {
                boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseWareList.get(childPosition + 1));
                if (isDownlaod) {
                    holdToPlay(courseWareList.get(childPosition + 1), -1, (childPosition + 1), flag);
                } else {
                    if (!NetWorkUtils.isConnected(this)) {
                        showPlayNetError();
                        return;
                    } else {
                        holdToPlay(courseWareList.get(childPosition + 1), -1, (childPosition + 1), flag);
                    }
                }
            } else {
                Toast.makeText(this, "当前讲次已是最后一讲", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 点击item 播放视频
     *
     * @param flag       //是否播放mp3
     * @param courseWare
     */
    public void holdToPlaySmart(CourseWare courseWare, int childPosition, boolean flag) {

        setIsAllowPlay();
        this.courseWare = courseWare;
        this.childPosition = childPosition;

        if (!flag) {
            if (player.mVideoView.isPlaying()) {
                player.mVideoView.stopPlayback();
                player.pb.setVisibility(View.VISIBLE);
            }
        }

        //这里要加视频是否已下载的逻辑
        boolean isDownlaod = DownloadManager.getInstance().isCWDownlaoded(courseWare);
        if (isDownlaod) {
            startPlayVideo(courseWare, true, flag);
        } else {
            if (NetWorkUtils.isConnected(this)) {
                mPresenter.getSmartPlayUrl(courseWare, flag);
            } else {
                player.pb.setVisibility(View.GONE);
                lecturef.showNetError();
                player.showNetError(getResources().getString(R.string.no_network), "0", "1");
            }
        }
    }

    @Override
    public Activity context() {
        return this;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    private PlanSelectDialog showDialog(PlanSelectDialog.SelectDialogListener listener, PlanSelectDialog.SelectDialogCancelListener cancelListener, List<PlanDialogBean> beans) {
        PlanSelectDialog dialog = new PlanSelectDialog(this, R.style.transparentFrameWindowStyle, listener, cancelListener, beans);
        if (!isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
