package com.me.fanyin.zbme.views.course;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Statistics;
import com.me.data.event.ExamChangeEvent;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.event.LogoutSuccessEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.ClassHomeBean;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.LastListenerBean;
import com.me.data.model.play.UserExamIdBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.course.fragment.StatisticalFragment;
import com.me.fanyin.zbme.views.course.fragment.UnloginCourseContentFragment;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.courselist.CourseListActivity;
import com.me.fanyin.zbme.views.course.play.db.ClassHomeDB;
import com.me.fanyin.zbme.views.course.play.db.CourseDetailDB;
import com.me.fanyin.zbme.views.course.play.db.UserExamDB;
import com.me.fanyin.zbme.views.course.play.widget.GradationScrollView;
import com.me.fanyin.zbme.views.course.play.widget.PhoneClickSpan;
import com.me.fanyin.zbme.views.main.activity.CourseTypeChangeActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.widget.EmptyViewLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends BaseMvpFragment<CourseView, CoursePersenter> implements CourseView, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TYPE_NO_SERVICE="2";
    private static final String TYPE_HAS_SERVICE="1";

    @BindView(R.id.status_bar_fix)
    View status_bar_fix;
    @BindView(R.id.class_kecheng_ll)
    LinearLayout llCourse;
    @BindView(R.id.class_tiku_ll)
    LinearLayout llExam;
    @BindView(R.id.class_dayi_ll)
    LinearLayout llquestion;
    @BindView(R.id.class_day_left)
    LinearLayout classDayLeft;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.play_continue_title)
    TextView playContinueTitle;
    @BindView(R.id.class_play_continue)
    LinearLayout classPlayContinue;
    @BindView(R.id.leftDays)
    TextView leftDays;
    @BindView(R.id.tv_leftDays)
    TextView tvLeftDays;
    @BindView(R.id.tv_comeon)
    TextView tvComeon;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.top_title_right)
    ImageView topTitleRight;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.top_title_bar_layout)
    RelativeLayout topTitleBarLayout;
    //    @BindView(R.id.fragment_class_content)
//    FrameLayout fragmentClassContent;
    Unbinder unbinder;
    @BindView(R.id.class_content)
    RelativeLayout classContent;
    @BindView(R.id.top_line)
    View topLine;
    @BindView(R.id.days)
    TextView days;

    private UnloginCourseContentFragment unloginCourseFragment;
    private String continuePlayCourseId, continuePlayCwId, sSujectId;
    private ClassHomeDB homeDB;
    private EmptyViewLayout mEmptyLayout;
    private boolean loadingType;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StatisticalFragment statisticalFragment;
    //    private View view;
    private UserExamDB userExamDB;
    private boolean isFirst=true;
    private CourseDetailDB courseDetailDB;
    private ClassHomeBean result;
    private boolean isShowUnloginFragment=true;


    public CourseFragment() {
        // Required empty public constructor
    }

    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    private void initFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
    }

    @Override
    public void initView() {
        // setTranslucentStatus();
//        initFragment();
        initImageListener();
        homeDB = new ClassHomeDB();
        userExamDB=new UserExamDB();
        courseDetailDB=new CourseDetailDB();

        topTitleBarLayout.setOnClickListener(this);
        topTitleRight.setImageResource(R.mipmap.img_class_top_right);
        topTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseType = new Intent(getActivity(), CourseTypeChangeActivity.class);
                startActivity(courseType);
            }
        });
        topTitleRight.setVisibility(View.VISIBLE);

        topTitleText.setText(SharedPrefHelper.getInstance().getExamName());

//        StatusBarCompat.compat(R.color.color_primary, getActivity());

        classPlayContinue.setOnClickListener(this);
        llCourse.setOnClickListener(this);
        llExam.setOnClickListener(this);
        llquestion.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setProgressViewOffset(false, height, height + 100);

        mEmptyLayout = new EmptyViewLayout(getActivity(), classContent);
        mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatas();
            }
        });
        mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatas();
            }
        });
    }

    private int height = 400;
    private int positionY;

    private void initImageListener() {
        scrollview.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                // TODO Auto-generated method stub
                positionY = y;
                if (y <= 0) {   //设置标题的背景颜色
                    topTitleBarLayout.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    topTitleText.setTextColor(Color.argb((int) 255, 255, 255, 255));
                    topTitleRight.setImageResource(R.mipmap.img_class_top_right);
                    topLine.setBackgroundColor(getResources().getColor(R.color.transparent));
                } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / height;
                    float alpha = (255 * scale);
//                            topTitleBarLayout.setTextColor(Color.argb((int) alpha, 255,255,255));
                    topTitleBarLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    topTitleText.setTextColor(Color.argb((int) 255, 120, 120, 120));
                    topTitleRight.setImageResource(R.mipmap.img_class_top_rightdeep);
                    topLine.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else {    //滑动到banner下面设置普通颜色
                    topTitleBarLayout.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    topTitleText.setTextColor(Color.argb((int) 255, 53, 53, 53));
                    topTitleRight.setImageResource(R.mipmap.img_class_top_rightdeep);
                    topLine.setBackgroundColor(getResources().getColor(R.color.divider_line));
                }
            }
        });
    }

    @Override
    public void initData(){}

    public void initDatas() {
        if(isFirst){
            isFirst=false;
            insertDb();
        }
        topTitleText.setText(SharedPrefHelper.getInstance().getExamName());
        if (!TextUtils.isEmpty(SharedPrefHelper.getInstance().getExamId())) {
            if (NetWorkUtils.isConnected(getActivity())) {
                showLoading();
                mPresenter.getData();
            } else {
                showError(getResources().getString(R.string.app_nonetwork_message));
            }
        }
//        if (!TextUtils.isEmpty(SharedPrefHelper.getInstance().getUserId())) {
//            if (NetWorkUtils.isConnected(getActivity())) {
//                mPresenter.continPlay();
//            } else {
//                classPlayContinue.setVisibility(View.GONE);
//            }
//        }
    }


    //截取数字
    public String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    @Override
    public void showHomeData( ClassHomeBean result) {
        hideLoading();
        loadingType = true;
        this.result = result;
        if (!TextUtils.isEmpty(result.getCountDownInfo().getDays())) {
            String leftNum=getNumbers(result.getCountDownInfo().getDays());
            days.setText(leftNum);
            String examName = SharedPrefHelper.getInstance().getExamName();
            String examId=SharedPrefHelper.getInstance().getExamId();

            if(TextUtils.isEmpty(leftNum)){
                leftDays.setText(result.getCountDownInfo().getDays());
            }else{
                leftDays.setText("天");
            }

            if(examId.equals("1008")){
                leftDays.setText("实务课堂");
                tvLeftDays.setText("您身边的财税智囊团");
            }else{
                tvLeftDays.setText(examName + "考试倒计时");
            }
        } else {
            tvLeftDays.setText("");
            leftDays.setText("");
        }
        if (!TextUtils.isEmpty(result.getOilInfo().getContent())) {
            tvComeon.setText(result.getOilInfo().getContent());
//            tvComeon.setText(getClickableHtml(result.getOilInfo().getContent()));
//            final String url=result.getOilInfo().getLink();
//            String look="<a href=\""+ url +"\">查看全文</a>";
//            tvComeon.setText(getClickableHtml(result.getOilInfo().getContent()+ "。"+look));
//            tvComeon.setMovementMethod(LinkMovementMethod.getInstance());
//            if(!TextUtils.isEmpty(url)){
//                tvComeon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CommonWebViewActivity.startActivity(getActivity(), "文章详情", url);
//                    }
//                });
//            }
        } else {
            tvComeon.setText("");
//            tvComeon.setOnClickListener(null);
        }
//        synchronized (this){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (TYPE_HAS_SERVICE.equals(result.getType())){

            LastListenerBean lastListern = result.getLastListern();
            if (lastListern==null || TextUtils.isEmpty(lastListern.getCourseName())){
                continuePlayError();
            }else{
                continuePlay(lastListern.getCouseId(),lastListern.getLectureId(),lastListern.getLectureName(),lastListern.getSSubjectId());
            }
                if (statisticalFragment == null ){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data",result.getStatistics());
                    statisticalFragment =  StatisticalFragment.newInstance(bundle);
                    ft.add(R.id.fragment_class_content, statisticalFragment);
                }else{
                    statisticalFragment.setData(result.getStatistics());
                }
            ft.show(statisticalFragment);
            if (unloginCourseFragment!=null)
                ft.hide(unloginCourseFragment);
            isShowUnloginFragment =false;
        }else{
            continuePlayError();
            if (unloginCourseFragment == null ){
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",result);
                unloginCourseFragment =  UnloginCourseContentFragment.newInstance(bundle);
                ft.add(R.id.fragment_class_content, unloginCourseFragment);
            }else{
                unloginCourseFragment.setData(result);
            }
            ft.show(unloginCourseFragment);
            if (statisticalFragment!=null)
                ft.hide(statisticalFragment);
            isShowUnloginFragment =true;
        }
        ft.commit();
//        }
    }


    @Override
    public void continuePlay(String courseId, String cwId, String cwName, String sSubjectId) {
        classPlayContinue.setVisibility(View.VISIBLE);
        playContinueTitle.setText(cwName);
        this.continuePlayCourseId = courseId;
        this.continuePlayCwId = cwId;
        this.sSujectId = sSubjectId;
    }

    @Override
    public void continuePlayError() {
        classPlayContinue.setVisibility(View.GONE);
    }

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        // 获取所有超链接
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(clickableHtmlBuilder, span); // 为每个超链接样式设置
        }
        return clickableHtmlBuilder;
    }

    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,
                                  final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);

        PhoneClickSpan phoneClickSpan = new PhoneClickSpan(new PhoneClickSpan.OnLinkClickListener() {
            @Override
            public void onLinkClick(View view) {
            }
        });

        clickableHtmlBuilder.setSpan(phoneClickSpan, start, end, flags);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.class_kecheng_ll:
                if (!SharedPrefHelper.getInstance().isLogin()) {
                    gotoActivity(LoginActivity.class, "com.dongao.kaoqian.phone.views.course.play.courselist.CourseListActivity");
                } else {
                    gotoActivity(CourseListActivity.class);
                }
                break;
            case R.id.class_tiku_ll:
                if (!SharedPrefHelper.getInstance().isLogin()) {
                    gotoActivity(LoginActivity.class, "com.dongao.kaoqian.phone.views.exam.ExamActivity");
                } else {
                    MobclickAgent.onEvent(getActivity(), Statistics.EXERCISE_HOMEPAGE);
                }
                break;
            case R.id.class_dayi_ll:
                if (!SharedPrefHelper.getInstance().isLogin()) {
                    gotoActivity(LoginActivity.class, "com.dongao.kaoqian.phone.views.query.QueryActivity");
                } else {
                }
                break;
            case R.id.class_play_continue:
                if(NetWorkUtils.isConnected(getActivity())){
                    Intent continuePlay = new Intent(getActivity(), PlayActivity.class);
                    continuePlay.putExtra("courseId", continuePlayCourseId);
                    continuePlay.putExtra("lectureId", continuePlayCwId);
                    continuePlay.putExtra("sSubjectId", sSujectId);
                    startActivity(continuePlay);
                }else{
                    String userId=SharedPrefHelper.getInstance().getUserId();
                    CourseDetail courseDetailInDB = courseDetailDB.find(userId, sSujectId, continuePlayCourseId);
                    if(courseDetailInDB!=null){
                        Intent continuePlay = new Intent(getActivity(), PlayActivity.class);
                        continuePlay.putExtra("courseId", continuePlayCourseId);
                        continuePlay.putExtra("lectureId", continuePlayCwId);
                        continuePlay.putExtra("sSubjectId", sSujectId);
                        startActivity(continuePlay);
                    }else{
                        Toast.makeText(appContext, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                    }
                }
                MobclickAgent.onEvent(getActivity(), Statistics.COURSE_CONTINUEPLAY);
                break;
        }
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void showError(String message) {
        swipeRefresh.setRefreshing(false);
        hideLoading();
        loadingType = true;
//        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
        String userId = SharedPrefHelper.getInstance().getUserId();
        String examId = SharedPrefHelper.getInstance().getExamId();
        ClassHomeBean homeBean = homeDB.find(userId, examId);
        if (homeBean != null) {
            String datas = homeBean.getContent();
            try {
                ClassHomeBean classHomeBean = JSON.parseObject(datas, ClassHomeBean.class);
                showHomeData(classHomeBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tvLeftDays.setText("");
            leftDays.setText("");
            days.setText("");
            tvComeon.setText("");

            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (NetWorkUtils.isConnected(getActivity())) {
                if (isShowUnloginFragment){
                    if (unloginCourseFragment == null ){
                        Bundle bundle=new Bundle();
                        bundle.putBoolean("isDataError",true);
                        unloginCourseFragment = UnloginCourseContentFragment.newInstance(bundle);
                        ft.add(R.id.fragment_class_content, unloginCourseFragment);
                        ft.show(unloginCourseFragment);
                        if (statisticalFragment!=null)
                            ft.hide(statisticalFragment);
                        ft.commit();
                    }else{
                        unloginCourseFragment.showDataError();
                    }
                }else{
                    if (statisticalFragment == null ) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isDataError", true);
                        statisticalFragment = StatisticalFragment.newInstance(bundle);
                        ft.add(R.id.fragment_class_content, statisticalFragment);
                        ft.show(statisticalFragment);
                        if (unloginCourseFragment!=null)
                            ft.hide(unloginCourseFragment);
                        ft.commit();
                    }
                    statisticalFragment.showDataError();
                }
            } else {
                if (isShowUnloginFragment){
                    if (unloginCourseFragment == null ) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isNetError", true);
                        unloginCourseFragment = UnloginCourseContentFragment.newInstance(bundle);
                        ft.add(R.id.fragment_class_content, unloginCourseFragment);
                        ft.show(unloginCourseFragment);
                        if (statisticalFragment != null)
                            ft.hide(statisticalFragment);
                        ft.commit();
                    }else{
                        unloginCourseFragment.showNetError();
                    }
                }else{
                    if (statisticalFragment == null ) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isNetError", true);
                        statisticalFragment = StatisticalFragment.newInstance(bundle);
                        ft.add(R.id.fragment_class_content, statisticalFragment);
                        ft.show(statisticalFragment);
                        if (unloginCourseFragment!=null)
                            ft.hide(unloginCourseFragment);
                        ft.commit();
                    }else{
                        statisticalFragment.showNetError();
                    }
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        initDatas();
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.getStatusBarHeight(mContext)));
        }
    }

    @Subscribe
    public void onEventMainThread(ExamChangeEvent event) {  //更新数据
        insertDb();
//        initDatas();
    }

    private void insertDb(){
        String userId=SharedPrefHelper.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            userId="unlogin";
        }

        UserExamIdBean userExamIdBean = userExamDB.find(userId);
        UserExamIdBean bean = new UserExamIdBean();
        bean.setUserId(userId);
        bean.setExamId(SharedPrefHelper.getInstance().getExamId());
        bean.setExamName(SharedPrefHelper.getInstance().getExamName());

        if (userExamIdBean == null) {//数据库中不存在  插入数据库
            userExamDB.insert(bean);
        } else {//存在 更新数据库
            userExamIdBean.setExamId(SharedPrefHelper.getInstance().getExamId());
            userExamIdBean.setExamName(SharedPrefHelper.getInstance().getExamName());
            userExamDB.update(userExamIdBean);
        }
    }

    @Subscribe
    public void onEventMainThread(LogoutSuccessEvent event) {  //退出登陆
        classPlayContinue.setVisibility(View.GONE);

        UserExamIdBean bean=userExamDB.find("unlogin");
        if(bean!=null){
            SharedPrefHelper.getInstance().setExamId(bean.getExamId());
            SharedPrefHelper.getInstance().setExamName(bean.getExamName());
        }
//        initDatas();
    }

    @Subscribe
    public void onEventMainThread(LoginSuccessEvent event) {  //登陆
        String userId=SharedPrefHelper.getInstance().getUserId();
        UserExamIdBean bean=userExamDB.find(userId);
        if(bean!=null){
            SharedPrefHelper.getInstance().setExamId(bean.getExamId());
            SharedPrefHelper.getInstance().setExamName(bean.getExamName());
        }
//        initDatas();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    public void showLoading() {
        if (loadingType) {
            swipeRefresh.setRefreshing(true);
        } else {
            mEmptyLayout.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingType) {
            swipeRefresh.setRefreshing(false);
        } else {
            mEmptyLayout.showContentView();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.course_fragment;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
