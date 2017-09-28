package com.me.fanyin.zbme.views.course.play.courselist;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Statistics;
import com.me.data.event.ContinuePlayEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.Course;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseList;
import com.me.data.model.play.CourseListBean;
import com.me.data.model.play.Ssubject;
import com.me.data.model.play.Subject;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.adapter.CourseListExpandAdapter;
import com.me.fanyin.zbme.views.course.play.adapter.SsubjectMuAdapter;
import com.me.fanyin.zbme.views.course.play.adapter.SubjectMuAdapter;
import com.me.fanyin.zbme.views.course.play.db.CourseDetailDB;
import com.me.fanyin.zbme.views.course.play.db.CourseListDB;
import com.me.fanyin.zbme.views.course.play.db.SubjectDB;
import com.me.fanyin.zbme.views.download.MyDownloadActivity;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuLayout;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuTitle;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.pop.HintPopupWindow;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/4/19.
 */

public class CourseListActivity extends BaseMvpActivity<CourseListView, CourseListPersenter> implements CourseListView,
        AdapterView.OnItemClickListener, ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.courselist_exp)
    ExpandableListView courselistExp;
    @BindView(R.id.course_content)
    RelativeLayout courseContent;
    @BindView(R.id.subject_layout)
    DropDownMenuTitle subjectLayout;
    @BindView(R.id.year_layout)
    DropDownMenuTitle yearLayout;
    @BindView(R.id.mall_menu_layout)
    DropDownMenuLayout mallMenuLayout;
    @BindView(R.id.play_continue_title)
    TextView playContinueTitle;
    @BindView(R.id.play_continue)
    LinearLayout playContinue;
    @BindView(R.id.courselist_menu_right)
    TextView courselistMenuRight;
    @BindView(R.id.toolbar)
    CommonToolbar toolbar;
    //    @BindView(R.id.course_cotent)
//    LinearLayout courseCotent;
    @BindView(R.id.no_subjects)
    FrameLayout noSubjects;
    //    @BindView(R.id.rl_content)
//    RelativeLayout rlContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.menu_subject)
    LinearLayout menuSubject;
//    @BindView(R.id.app_view_iv)
//    ImageView appViewIv;
//    @BindView(R.id.app_message_tv)
//    TextView appMessageTv;
//    @BindView(R.id.app_action_btn)
//    Button appActionBtn;

    private CourseListExpandAdapter adapter;
    private CourseListDB courseListDB;
    private String userId;
    private String examId;
    private String sSubjectId;
    private String continueSsubjectId;
    private List<CourseList> courseList;
    private List<Subject> subjectData;
    private List<Ssubject> sSubjectData;
    private String subjectName;
    private SubjectMuAdapter subjectMuAdapter;
    private SsubjectMuAdapter yearAdapter;
    private String continuePlayCourseId, continuePlayCwId, getsSubjectId;
    private SubjectDB subjectDB;

    private ImageView image_loading;
    public int subjectPostion = 0;
    public int sSubjectPostion = 0;
    private CourseDetailDB courseDetailDB;

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        super.onCreate(savedInstnceState);
        EventBus.getDefault().register(this);
    }

    HintPopupWindow hintPopupWindow;
    List list;

    private void initPopBottom(View view) {
        if (list == null) {
            String[] str = new String[]{"我的收藏", "我的笔记", "我的下载"};
            list = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                list.add(str[i]);
            }
        }
        if (hintPopupWindow == null) {
            hintPopupWindow = new HintPopupWindow(this, list, new HintPopupWindow.TopRightClick() {
                @Override
                public void clickPosition(int position) {
                    hintPopupWindow.dismissPopupWindow();
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            Intent download = new Intent(CourseListActivity.this, MyDownloadActivity.class);
                            MobclickAgent.onEvent(CourseListActivity.this, Statistics.PROFILE_DOWNLOAD);
                            startActivity(download);
                            break;
                    }
                }
            });
        }
        hintPopupWindow.showPopupWindow(view);

    }


    @Override
    protected void initInject() {
        getAppComponent().inject(CourseListActivity.this);
    }

    @Override
    protected void initView() {
        subjectDB = new SubjectDB();
        courseListDB = new CourseListDB();
        courseDetailDB=new CourseDetailDB();
        image_loading = (ImageView) findViewById(R.id.app_loading_iv);
        AnimationDrawable animation = (AnimationDrawable) image_loading.getDrawable();
        animation.start();
//        toolbar.setNavigationIcon(R.drawable.go_back);
        toolbar.setTitleText("课程");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        playContinue.setOnClickListener(this);
        courselistMenuRight.setVisibility(View.GONE);
        courselistMenuRight.setOnClickListener(this);

        userId = SharedPrefHelper.getInstance().getUserId();
        examId = SharedPrefHelper.getInstance().getExamId();

//        appActionBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initData();
//            }
//        });

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        swipeRefresh.setOnRefreshListener(this);

        adapter = new CourseListExpandAdapter(this);
        courselistExp.setOnChildClickListener(this);
        courselistExp.setOnGroupClickListener(this);

        subjectData = new ArrayList<>();
        sSubjectData = new ArrayList<>();
        initMenu();
    }

    private void initMenu() {
        subjectLayout.setOnClickListener(this);
        yearLayout.setOnClickListener(this);

        subjectMuAdapter = new SubjectMuAdapter(this);
        subjectMuAdapter.setDatas(subjectData);
        ListView subjectList = getMenuListView(subjectMuAdapter);
        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectPostion = position;
                sSubjectPostion = 0;

                Subject subject = subjectData.get(position);
                subjectName = subject.getSubjectName();

                subjectLayout.setText(subject.getSubjectName());
                sSubjectData = subjectData.get(position).getMemberSeasonSubjectList();
                yearAdapter.setDatas(sSubjectData);
                yearAdapter.notifyDataSetChanged();
                subjectMuAdapter.notifyDataSetChanged();

                yearLayout.setText(sSubjectData.get(0).getsSubjectYear());
                SharedPrefHelper.getInstance().setMainSubjectId(subject.getSubjectId());
                SharedPrefHelper.getInstance().setMainSsubjectId(sSubjectData.get(0).getsSubjectId());
                mallMenuLayout.closeMenu();
                getCourseList();
            }
        });
        mallMenuLayout.addMenuView(subjectList);

        yearAdapter = new SsubjectMuAdapter(this);
        yearAdapter.setDatas(sSubjectData);
        ListView yearListview = getMenuListView(yearAdapter);
        yearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sSubjectPostion = position;

                yearLayout.setText(sSubjectData.get(position).getsSubjectYear());
                yearAdapter.notifyDataSetChanged();

                SharedPrefHelper.getInstance().setMainSsubjectId(sSubjectData.get(position).getsSubjectId());
                mallMenuLayout.closeMenu();
                getCourseList();
            }
        });
        mallMenuLayout.addMenuView(yearListview);
        menuSubject.setVisibility(View.VISIBLE);

        mallMenuLayout.setOnOpenCloseListener(new DropDownMenuLayout.OnOpenCloseListener() {
            @Override
            public void onCloseMenu(int position) {
                switch (position) {
                    case 0:
                        subjectLayout.closeMenu();
                        break;
                    case 1:
                        yearLayout.closeMenu();
                        break;
                }
            }

            @Override
            public void onOpenMenu(int position) {
                switch (position) {
                    case 0:
                        subjectLayout.openMenu();
                        break;
                    case 1:
                        yearLayout.openMenu();
                        break;
                }
            }
        });
    }

    private void getCourseList() {
        if (NetWorkUtils.isConnected(this)) {
            swipeRefresh.setRefreshing(true);
            mPresenter.getData();
        } else {
            showOffline();
        }
        if (NetWorkUtils.isConnected(this)) {
            mPresenter.continPlay();
        }
    }

    private void showOffline() {
        sSubjectId = SharedPrefHelper.getInstance().getMainSsubjectId();
        CourseListBean cache = courseListDB.find(userId, sSubjectId);
        if (cache != null) {
            String content = cache.getContent();
            CourseListBean courseListBean = JSON.parseObject(content, CourseListBean.class);
            if (courseListBean.getCourseList() != null && courseListBean.getCourseList().size() > 0) {
                initAdapter(courseListBean.getCourseList());
            } else {
                showNetworkError();
            }
        } else {
            showNetworkError();
        }
    }

    @Override
    public void showDataError() {
        sSubjectId = SharedPrefHelper.getInstance().getMainSsubjectId();
        CourseListBean cache = courseListDB.find(userId, sSubjectId);
        if (cache != null) {
            String content = cache.getContent();
            CourseListBean courseListBean = JSON.parseObject(content, CourseListBean.class);
            if (courseListBean.getCourseList() != null && courseListBean.getCourseList().size() > 0) {
                initAdapter(courseListBean.getCourseList());
            } else {
                showErrorData();
            }
        } else {
            showErrorData();
        }
    }

    @Override
    protected void initData() {
        if (NetWorkUtils.isConnected(this)) {
            showLoading();
            mPresenter.getSubjects();
        } else {
            showError("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject_layout:
                mallMenuLayout.switchMenu(0);
                break;
            case R.id.year_layout:
                mallMenuLayout.switchMenu(1);
                break;
            case R.id.courselist_menu_right:
                initPopBottom(courselistMenuRight);
                break;
            case R.id.play_continue:
                if(NetWorkUtils.isConnected(this)){
                    Intent continuePlay = new Intent(this, PlayActivity.class);
                    continuePlay.putExtra("courseId", continuePlayCourseId);
                    continuePlay.putExtra("lectureId", continuePlayCwId);
                    continuePlay.putExtra("sSubjectId", continueSsubjectId);
                    startActivity(continuePlay);
                }else{
                    CourseDetail courseDetailInDB = courseDetailDB.find(userId, continueSsubjectId, continuePlayCourseId);
                    if(courseDetailInDB!=null){
                        Intent continuePlay = new Intent(this, PlayActivity.class);
                        continuePlay.putExtra("courseId", continuePlayCourseId);
                        continuePlay.putExtra("lectureId", continuePlayCwId);
                        continuePlay.putExtra("sSubjectId", continueSsubjectId);
                        startActivity(continuePlay);
                    }else{
                        Toast.makeText(appContext, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                    }
                }
                MobclickAgent.onEvent(this, Statistics.COURSE_CONTINUEPLAY);
                break;
        }
    }

    private ListView getMenuListView(SubjectMuAdapter adapter) {
        ListView listView = new ListView(this);
        listView.setFastScrollAlwaysVisible(false);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(ContextCompat.getColor(context(), R.color.white_menu_list_bg));
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setVisibility(View.GONE);
        return listView;
    }

    private ListView getMenuListView(SsubjectMuAdapter adapter) {
        ListView listView = new ListView(this);
        listView.setFastScrollAlwaysVisible(false);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(ContextCompat.getColor(context(), R.color.white_menu_list_bg));
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setVisibility(View.GONE);
        return listView;
    }

    @Override
    public void showError(String message) {
//        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
        Subject subject = subjectDB.find(userId, examId);
        if (subject != null) {
            String datas = subject.getContent();
            try {
                List<Subject> list = JSON.parseArray(datas, Subject.class);
                subjectCacheDatas(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (TextUtils.isEmpty(message)) {
                showNetworkError();
            } else {
                showErrorData();
            }
        }
    }

    @Override
    public void initAdapter(List<CourseList> courseList) {
        hideLoading();
        if (courseList != null && courseList.size() > 0) {
            this.courseList = courseList;
            courselistMenuRight.setVisibility(View.VISIBLE);
            courselistExp.setVisibility(View.VISIBLE);
            adapter.setList(courseList);
            courselistExp.setAdapter(adapter);
            for (int i = 0; i < courseList.size(); i++) {
                courselistExp.expandGroup(i);
            }
        } else {
            showEmptyData();
        }
    }

    @Override
    public void subjectDatas(List<Subject> mList) {
        this.subjectData = mList;
        if (mList != null && !mList.isEmpty()) {
            noSubjects.setVisibility(View.GONE);
//            courseCotent.setVisibility(View.VISIBLE);

            String subjectId = SharedPrefHelper.getInstance().getMainSubjectId();
            String ssubjectId = SharedPrefHelper.getInstance().getMainSsubjectId();
            if (TextUtils.isEmpty(subjectId)) {
                subjectLayout.setText(mList.get(0).getSubjectName());
                yearLayout.setText(mList.get(0).getMemberSeasonSubjectList().get(0).getsSubjectYear());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(0).getMemberSeasonSubjectList().get(0).getsSubjectId());
                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(0).getSubjectId());

                sSubjectData = mList.get(0).getMemberSeasonSubjectList();
                yearAdapter.setDatas(mList.get(0).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            } else if (!TextUtils.isEmpty(subjectId) && TextUtils.isEmpty(ssubjectId)) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getSubjectId().equals(subjectId)) {
                        subjectPostion = i;
                        break;
                    }
                }
                subjectLayout.setText(mList.get(subjectPostion).getSubjectName());
                sSubjectData = mList.get(subjectPostion).getMemberSeasonSubjectList();

                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(subjectPostion).getSubjectId());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(subjectPostion).getMemberSeasonSubjectList().get(0).getsSubjectId());
                yearLayout.setText(mList.get(subjectPostion).getMemberSeasonSubjectList().get(0).getsSubjectYear());
                yearAdapter.setDatas(mList.get(subjectPostion).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            } else if (!TextUtils.isEmpty(subjectId) && !TextUtils.isEmpty(ssubjectId)) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getSubjectId().equals(subjectId)) {
                        subjectPostion = i;
                        break;
                    }
                }
                subjectLayout.setText(mList.get(subjectPostion).getSubjectName());
                sSubjectData = mList.get(subjectPostion).getMemberSeasonSubjectList();
                for (int i = 0; i < sSubjectData.size(); i++) {
                    if (sSubjectData.get(i).getsSubjectId().equals(ssubjectId)) {
                        sSubjectPostion = i;
                        break;
                    }
                }
                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(subjectPostion).getSubjectId());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(subjectPostion).getMemberSeasonSubjectList().get(sSubjectPostion).getsSubjectId());
                yearLayout.setText(mList.get(subjectPostion).getMemberSeasonSubjectList().get(sSubjectPostion).getsSubjectYear());
                yearAdapter.setDatas(mList.get(subjectPostion).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            }
            subjectMuAdapter.setDatas(mList);
            subjectMuAdapter.notifyDataSetChanged();
            getCourseList();
        } else {
            emptyData();
        }
    }

    @Override
    public void emptyData() {
        hideLoading();
        if(subjectData!=null && subjectData.size()>0){
            menuSubject.setVisibility(View.VISIBLE);
        }else{
            menuSubject.setVisibility(View.GONE);
        }
        noSubjects.setVisibility(View.VISIBLE);
//        courseCotent.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.no_subjects, new CourseEmptyFragment());
        ft.commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.course_courselist_all;
    }

    @Override
    protected int getContentResId() {
        return R.layout.course_courselist_content;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        Course course = courseList.get(groupPosition).getCourseItems().get(childPosition);
        if(NetWorkUtils.isConnected(this)){
            if (!course.getCwNumber().equals("0")) {
                Intent intent = new Intent(this, PlayActivity.class);
                intent.putExtra("sSubjectId", SharedPrefHelper.getInstance().getMainSsubjectId());
                intent.putExtra("lectureId", course.getEndLectureId());
                intent.putExtra("courseId", course.getId());
                if (courseList.get(groupPosition).getCourseTypeName().equals("未学习")) {
                    intent.putExtra("alreadyStudy", false);
                }
                startActivity(intent);
            } else {
                Toast.makeText(this, "暂未开课", Toast.LENGTH_SHORT).show();
            }
        }else{
            CourseDetail courseDetailInDB = courseDetailDB.find(userId, SharedPrefHelper.getInstance().getMainSsubjectId(), course.getId());
            if(courseDetailInDB!=null){
                if (!course.getCwNumber().equals("0")) {
                    Intent intent = new Intent(this, PlayActivity.class);
                    intent.putExtra("sSubjectId", SharedPrefHelper.getInstance().getMainSsubjectId());
                    intent.putExtra("lectureId", course.getEndLectureId());
                    intent.putExtra("courseId", course.getId());
                    if (courseList.get(groupPosition).getCourseTypeName().equals("未学习")) {
                        intent.putExtra("alreadyStudy", false);
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "暂未开课", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(appContext, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
            }
        }


        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void continuePlay(String courseId, String cwId, String cwName, String sSubjectId) {
        playContinue.setVisibility(View.VISIBLE);
        playContinueTitle.setText(cwName);
        this.continuePlayCourseId = courseId;
        this.continuePlayCwId = cwId;
        this.continueSsubjectId = sSubjectId;
    }

    @Override
    public void continuePlayError() {
        playContinue.setVisibility(View.GONE);
    }

    /**
     * 获取数据后缓存
     *
     * @param courseList
     */
    @Override
    public void insertData(CourseListBean courseList) {
        sSubjectId = SharedPrefHelper.getInstance().getMainSsubjectId();
        CourseListBean cache = courseListDB.find(userId, sSubjectId);
        String list = JSON.toJSONString(courseList);
        courseList.setContent(JSON.toJSONString(courseList));
        courseList.setUserId(userId);
        courseList.setsSubjectId(sSubjectId);
        if (cache != null) {
            cache.setContent(JSON.toJSONString(courseList));
            courseListDB.update(cache);
        } else {
            courseListDB.insert(courseList);
        }
    }


    public void subjectCacheDatas(List<Subject> mList) {
        this.subjectData = mList;
        if (mList != null && !mList.isEmpty()) {
            String subjectId = SharedPrefHelper.getInstance().getMainSubjectId();
            String ssubjectId = SharedPrefHelper.getInstance().getMainSsubjectId();
            if (TextUtils.isEmpty(subjectId)) {
                subjectLayout.setText(mList.get(0).getSubjectName());
                yearLayout.setText(mList.get(0).getMemberSeasonSubjectList().get(0).getsSubjectYear());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(0).getMemberSeasonSubjectList().get(0).getsSubjectId());
                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(0).getSubjectId());

                sSubjectData = mList.get(0).getMemberSeasonSubjectList();
                yearAdapter.setDatas(mList.get(0).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            } else if (!TextUtils.isEmpty(subjectId) && TextUtils.isEmpty(ssubjectId)) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getSubjectId().equals(subjectId)) {
                        subjectPostion = i;
                        break;
                    }
                }
                subjectLayout.setText(mList.get(subjectPostion).getSubjectName());
                sSubjectData = mList.get(subjectPostion).getMemberSeasonSubjectList();

                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(subjectPostion).getSubjectId());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(subjectPostion).getMemberSeasonSubjectList().get(0).getsSubjectId());
                yearLayout.setText(mList.get(subjectPostion).getMemberSeasonSubjectList().get(0).getsSubjectYear());
                yearAdapter.setDatas(mList.get(subjectPostion).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            } else if (!TextUtils.isEmpty(subjectId) && !TextUtils.isEmpty(ssubjectId)) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getSubjectId().equals(subjectId)) {
                        subjectPostion = i;
                        break;
                    }
                }
                subjectLayout.setText(mList.get(subjectPostion).getSubjectName());
                sSubjectData = mList.get(subjectPostion).getMemberSeasonSubjectList();
                for (int i = 0; i < sSubjectData.size(); i++) {
                    if (sSubjectData.get(i).getsSubjectId().equals(ssubjectId)) {
                        sSubjectPostion = i;
                        break;
                    }
                }

                SharedPrefHelper.getInstance().setMainSubjectId(mList.get(subjectPostion).getSubjectId());
                SharedPrefHelper.getInstance().setMainSsubjectId(mList.get(subjectPostion).getMemberSeasonSubjectList().get(sSubjectPostion).getsSubjectId());
                yearLayout.setText(mList.get(subjectPostion).getMemberSeasonSubjectList().get(sSubjectPostion).getsSubjectYear());
                yearAdapter.setDatas(mList.get(subjectPostion).getMemberSeasonSubjectList());
                yearAdapter.notifyDataSetChanged();
            }
            subjectMuAdapter.setDatas(mList);
            subjectMuAdapter.notifyDataSetChanged();
            showOffline();
        } else {
            showNetworkError();
        }
    }

    @Override
    public void onRefresh() {
        if(NetWorkUtils.isConnected(this)){
            getCourseList();
        }else{
            swipeRefresh.setRefreshing(false);
            Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                if (!NetWorkUtils.isNetworkAvailable(context())) {
                    showNetworkError();
                    return;
                }
                initData();
            }
        };
    }

    @Override
    public void showNetworkError() {
        super.showNetworkError();
        if(subjectData!=null && subjectData.size()>0){
            menuSubject.setVisibility(View.VISIBLE);
        }else{
            menuSubject.setVisibility(View.GONE);
        }
        noSubjects.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyData() {
        super.showEmptyData();
        courselistMenuRight.setVisibility(View.GONE);
        noSubjects.setVisibility(View.GONE);
    }

    @Override
    public void showErrorData() {
        super.showErrorData();
        if(subjectData!=null && subjectData.size()>0){
            menuSubject.setVisibility(View.VISIBLE);
        }else{
            menuSubject.setVisibility(View.GONE);
        }
        noSubjects.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        swipeRefresh.setRefreshing(false);
        noSubjects.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(ContinuePlayEvent event) {
        getCourseList();
    }
}
