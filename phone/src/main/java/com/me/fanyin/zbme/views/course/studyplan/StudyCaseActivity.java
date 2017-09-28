package com.me.fanyin.zbme.views.course.studyplan;

import android.content.Intent;
import android.os.Bundle;
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

import com.me.core.utils.NetWorkUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.intel.IntelWeekTask;
import com.me.data.model.intel.IntelWorkReview;
import com.me.data.model.play.StudyPlanBean;
import com.me.data.model.play.StudyPlanReviewBean;
import com.me.data.model.play.StudyPlanTaskBean;
import com.me.data.model.play.Subject;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.studyplan.adatper.StudyPlanAdapter;
import com.me.fanyin.zbme.views.course.studyplan.adatper.StudyPlanEpListAdapter;
import com.me.fanyin.zbme.views.course.studyplan.adatper.SubjectCaseAdapter;
import com.me.fanyin.zbme.widget.BaseSwipeRefreshLayout;
import com.me.fanyin.zbme.widget.ColorArcProgressBar;
import com.me.fanyin.zbme.widget.EmptyViewLayout;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuLayout;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuTitle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/8/21.
 */

public class StudyCaseActivity extends BaseMvpActivity<StudyCaseView, StudyCasePersenter> implements StudyCaseView,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, ExpandableListView.OnChildClickListener {

    @BindView(R.id.study_nums)
    TextView studyNums;
    @BindView(R.id.study_case_pb)
    ColorArcProgressBar studyCasePb;
    @BindView(R.id.study_nums_do)
    TextView studyNumsDo;
    @BindView(R.id.study_case_listview)
    ListView studyCaseListview;
    @BindView(R.id.study_case_explist)
    ExpandableListView studyCaseExplist;
    @BindView(R.id.subject_layout)
    DropDownMenuTitle subjectLayout;
    @BindView(R.id.studycase_suject_layout)
    DropDownMenuLayout studycaseSujectLayout;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.swipe_refresh)
    BaseSwipeRefreshLayout swipeRefresh;
    @BindView(R.id.plancase_content)
    LinearLayout plancaseContent;
    @BindView(R.id.studycase_percent)
    LinearLayout studycasePercent;
    @BindView(R.id.error_layout_top)
    RelativeLayout errorLayoutTop;
    @BindView(R.id.error_layout_bottom)
    RelativeLayout errorLayoutBottom;
    @BindView(R.id.tv_week_before)
    TextView tvWeekBefore;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_week_after)
    TextView tvWeekAfter;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    private EmptyViewLayout mEmptyLayout;

    private StudyPlanAdapter adapter;
    private StudyPlanEpListAdapter exAdapter;

    private SubjectCaseAdapter caseAdapter;
    public int subjectPostion;
    private List<Subject> subjectData;
    private StudyPlanBean result;

    @Override
    protected void initInject() {
        getAppComponent().inject(StudyCaseActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subject_layout:
                studycaseSujectLayout.switchMenu(0);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_week_before:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(weekBefore);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    List<String> dates = getWeekBefore(calendar);
                    getPlanDatas(dates.get(6));
                } catch (Exception e) {

                }
                break;
            case R.id.tv_week_after:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(weekAfter);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    List<String> datess = getWeekAfter(calendar);
                    getPlanDatas(datess.get(6));
                } catch (Exception e) {

                }

                break;
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_studycase;
    }

    @Override
    protected void initView() {

        adapter = new StudyPlanAdapter(this);
        exAdapter = new StudyPlanEpListAdapter(this);

        studyCaseListview.setOnItemClickListener(this);
        tvWeekBefore.setOnClickListener(this);
        tvWeekAfter.setOnClickListener(this);
        studyCaseExplist.setOnChildClickListener(this);

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        swipeRefresh.setOnRefreshListener(this);

        imgBack.setOnClickListener(this);
        subjectData = new ArrayList<>();
        initMenu();

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
    }


    public static List<String> getWeekBefore(Calendar c) {
        List<String> dates = new ArrayList<String>();
//        final Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String date = sim.format(c.getTime());
        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DAY_OF_MONTH, -1);
            date = sim.format(c.getTime());
            dates.add(date);
        }
        return dates;
    }

    public static List<String> getWeekAfter(Calendar c) {
        List<String> dates = new ArrayList<String>();
//        final Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String date = sim.format(c.getTime());
        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            date = sim.format(c.getTime());
            dates.add(date);
        }
        return dates;
    }

    public static List<String> getWeekAftersdf() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String date = sim.format(c.getTime());
        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            date = sim.format(c.getTime());
            dates.add(date);
        }
        return dates;
    }

    private void initMenu() {
        subjectLayout.setOnClickListener(this);

        caseAdapter = new SubjectCaseAdapter(this);
        caseAdapter.setDatas(subjectData);
        ListView subjectList = getMenuListView(caseAdapter);
        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NetWorkUtils.isNetworkAvailable(StudyCaseActivity.this)) {
                    subjectLayout.setText(subjectData.get(position).getSubjectName());
                    subjectPostion = position;
                    caseAdapter.notifyDataSetChanged();

                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dates = dateFormat.format(date);
                    SharedPrefHelper.getInstance().setIntelSubjectId(subjectData.get(position).getSubjectId());
                    getPlanDatas(dates);
                } else {
                    Toast.makeText(StudyCaseActivity.this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                }
                studycaseSujectLayout.closeMenu();
            }
        });
        studycaseSujectLayout.addMenuView(subjectList);

        studycaseSujectLayout.setOnOpenCloseListener(new DropDownMenuLayout.OnOpenCloseListener() {
            @Override
            public void onCloseMenu(int position) {
                switch (position) {
                    case 0:
                        subjectLayout.closeMenu();
                        break;
                }
            }

            @Override
            public void onOpenMenu(int position) {
                switch (position) {
                    case 0:
                        subjectLayout.openMenu();
                        break;
                }
            }
        });
    }

    private ListView getMenuListView(SubjectCaseAdapter adapter) {
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
    protected void initData() {
        if (NetWorkUtils.isNetworkAvailable(this)) {
            subjectLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.showLoading();
            mPresenter.getSubjects();
        } else {
            subjectLayout.setVisibility(View.GONE);
            showNetError();
        }
//        Date date=new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        String dates=dateFormat.format(date);
//        mPresenter.getPlanData(dates);
    }

    @Override
    public void subjectDatas(List<Subject> mList) {
        this.subjectData = mList;
        if (mList != null && !mList.isEmpty()) {

            String subjectId = SharedPrefHelper.getInstance().getIntelSubjectId();
            if (TextUtils.isEmpty(subjectId)) {
                subjectLayout.setText(mList.get(0).getSubjectName());
                SharedPrefHelper.getInstance().setIntelSubjectId(mList.get(0).getSubjectId());
            } else {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getSubjectId().equals(subjectId)) {
                        subjectPostion = i;
                        break;
                    }
                }
                subjectLayout.setText(mList.get(subjectPostion).getSubjectName());
                SharedPrefHelper.getInstance().setIntelSubjectId(mList.get(subjectPostion).getSubjectId());
            }
            caseAdapter.setDatas(mList);
            caseAdapter.notifyDataSetChanged();

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dates = dateFormat.format(date);
            getPlanDatas(dates);
        } else {
            emptyData();
        }
    }

    private void getPlanDatas(String date) {
        if (NetWorkUtils.isNetworkAvailable(this)) {
            Subject subject=getSubjectBean();
            if(subject.getOpenState().equals("2")){
                mEmptyLayout.showOtherErrorView();
            }else if(subject.getOpenState().equals("3")){
                mEmptyLayout.showEmpty();
            }else{
                if(mEmptyLayout.isEmptyViewShow() || mEmptyLayout.isOtherViewShow()){
                    mEmptyLayout.showLoading();
                }
                swipeRefresh.setRefreshing(true);
                mPresenter.getPlanData(date);
            }
        } else {
            swipeRefresh.setRefreshing(false);
            Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
        }
    }

    private Subject getSubjectBean(){

        String subjectId=SharedPrefHelper.getInstance().getIntelSubjectId();

        for (int i = 0; i < subjectData.size(); i++) {
            if (subjectData.get(i).getSubjectId().equals(subjectId)) {
                return subjectData.get(i);
            }
        }
        return null;
    }

    private String weekBefore;
    private String weekAfter;

    @Override
    public void showDatas(StudyPlanBean result) {
        mEmptyLayout.showContentView();
        swipeRefresh.setRefreshing(false);
        if (result != null) {
            this.result = result;
            StudyPlanTaskBean bean = result.getStudyTask();
            if (bean != null) {
                rlDate.setVisibility(View.VISIBLE);

                weekBefore = bean.getStartDate();
                weekAfter = bean.getEndDate();
                weekBefore = weekBefore.split(" ")[0];
                weekAfter = weekAfter.split(" ")[0];
                tvDate.setText(weekBefore + "—" + weekAfter);

                if (result.getHasPrev().equals("1")) {
                    tvWeekBefore.setClickable(true);
                    tvWeekBefore.setTextColor(getResources().getColor(R.color.text_color_primary));
                } else {
                    tvWeekBefore.setClickable(false);
                    tvWeekBefore.setTextColor(getResources().getColor(R.color.text_color_primary_light_more));
                }
                if (result.getHasNext().equals("1")) {
                    tvWeekAfter.setClickable(true);
                    tvWeekAfter.setTextColor(getResources().getColor(R.color.text_color_primary));
                } else {
                    tvWeekAfter.setClickable(false);
                    tvWeekAfter.setTextColor(getResources().getColor(R.color.text_color_primary_light_more));
                }

                List<IntelWeekTask> tasks = result.getStudyTask().getStudyTaskList();
                if (tasks != null && tasks.size() > 0) {
                    showPlanData();
                    studyNums.setText(result.getStudyTask().getTaskCount());
                    studyNumsDo.setText(result.getStudyTask().getTaskCountCompleted());
                    String percent = result.getStudyTask().getStudyPercent();
                    studyCasePb.setCurrentValues(Float.parseFloat(percent));

                    adapter = new StudyPlanAdapter(this);
                    adapter.setDatas(result.getStudyTask().getStudyTaskList());
                    studyCaseListview.setAdapter(adapter);
                } else {
                    showPlanEmpty();
                }
            } else {
                rlDate.setVisibility(View.GONE);
                showPlanEmpty();
            }

            List<StudyPlanReviewBean> reviewList = result.getReviewList();
            if (reviewList != null && reviewList.size() > 0) {
                showReviewData();
                exAdapter = new StudyPlanEpListAdapter(this);
                exAdapter.setDatas(result.getReviewList());
                studyCaseExplist.setAdapter(exAdapter);
                for(int i=0;i<reviewList.size();i++){
                   String isToday= reviewList.get(i).getIsToday();
                    if(isToday.equals("1")){
                        studyCaseExplist.expandGroup(i);
                    }
                }
            } else {
                showReviewEmpty();
            }

        } else {
            showPlanEmpty();
            showReviewEmpty();
        }

    }

    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(weekBefore)) {
            getPlanDatas(weekBefore);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<IntelWeekTask> tasks = result.getStudyTask().getStudyTaskList();
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("cws", (Serializable) tasks);
        String subjectId = SharedPrefHelper.getInstance().getIntelSubjectId();
        intent.putExtra("subjectId", subjectId);
        intent.putExtra("lectureId", tasks.get(position).getKpId());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        StudyPlanReviewBean reviewBean = result.getReviewList().get(groupPosition);
        List<IntelWorkReview> studyTaskList = reviewBean.getStudyTaskList();
        if (reviewBean.getIsToday().equals("1")) {
        }
        return false;
    }

    @Override
    public void emptyData() {
        mEmptyLayout.showEmpty();
    }

    private void showPlanEmpty() {
        studyCaseListview.setVisibility(View.GONE);
        studycasePercent.setVisibility(View.GONE);
        errorLayoutTop.setVisibility(View.VISIBLE);
    }

    private void showPlanData() {
        studyCaseListview.setVisibility(View.VISIBLE);
        studycasePercent.setVisibility(View.VISIBLE);
        errorLayoutTop.setVisibility(View.GONE);
    }

    private void showReviewEmpty() {
        studyCaseExplist.setVisibility(View.GONE);
        errorLayoutBottom.setVisibility(View.VISIBLE);
    }

    private void showReviewData() {
        studyCaseExplist.setVisibility(View.VISIBLE);
        errorLayoutBottom.setVisibility(View.GONE);
    }

    private void showNetError() {
        mEmptyLayout.showNetErrorView();
    }

    @Override
    public void showOtherError() {
        mEmptyLayout.showOtherErrorView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
