package com.me.fanyin.zbme.views.course.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.ClassHomeBean;
import com.me.data.model.play.ClassHomeContent;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.course.adapter.CourseExpandAdapter;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.main.activity.SubpageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class UnloginCourseFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    Unbinder unbinder;
    @BindView(R.id.exp_unlogin)
    ExpandableListView recycUnlogin;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;
    @BindView(R.id.app_message_tv)
    TextView appMessageTv;
    private View view;

    private CourseExpandAdapter adapter;
    private ClassHomeBean result;

    public UnloginCourseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unlogin_course_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter = new CourseExpandAdapter(getActivity());
        recycUnlogin.setOnChildClickListener(this);
        recycUnlogin.setOnGroupClickListener(this);
    }

    public void setData(ClassHomeBean result) {
        this.result = result;
        if (result.getForumList() != null && result.getForumList().size() > 0) {
            List<ClassHomeContent> forunmList = result.getForumList();
            for (int i = 0; i < forunmList.size(); i++) {
                if (forunmList.get(i).getList() == null || forunmList.get(i).getList().size() == 0) {
                    forunmList.remove(i);
                    i--;
                }
            }
            if (forunmList.size() > 0) {
                recycUnlogin.setVisibility(View.VISIBLE);
                emptyRl.setVisibility(View.GONE);

                adapter.setData(forunmList);
                recycUnlogin.setAdapter(adapter);
                for (int i = 0; i < result.getForumList().size(); i++) {
                    recycUnlogin.expandGroup(i);
                }
            } else {
                if (NetWorkUtils.isConnected(getActivity())) {
                    showDataEmpty();
                } else {
                    showNetError();
                }
            }
        } else {
            if (NetWorkUtils.isConnected(getActivity())) {
                showDataEmpty();
            } else {
                showNetError();
            }
        }
    }

    public void showDataError(){
        recycUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_dataerror);
        appMessageTv.setText(getResources().getString(R.string.app_error_message));
    }

    public void showDataEmpty(){
        recycUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nothing);
        appMessageTv.setText(getResources().getString(R.string.app_empty_message));
    }

    public void showNetError(){
        recycUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nonetwork);
        appMessageTv.setText(getResources().getString(R.string.app_nonetwork_message));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupotion, int childPostion, long l) {
        if (result.getForumList().get(groupotion).getForumName().equals("免费试听")) {
            ClassHomeContentype contentype = result.getForumList().get(groupotion).getList().get(childPostion);
            Intent intent = new Intent(getActivity(), PlayActivity.class);
            if(contentype.getId().contains("_")){
                String[] str=contentype.getId().split("_");
                intent.putExtra("lectureId", str[1]);
                intent.putExtra("examId", str[0]);
            }else{
                intent.putExtra("examId", "3");
                intent.putExtra("lectureId", "173");
            }
            intent.putExtra("isFreePlay", true);
            intent.putExtra("title", contentype.getTitle());
            intent.putExtra(Constants.LINK, contentype.getLink());
            startActivity(intent);
        } else {
        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupotion, long l) {
        if (result.getForumList().get(groupotion).getForumName().equals("免费试听")) {
            ClassHomeContent homeContent = result.getForumList().get(groupotion);
            Intent intent = new Intent(getActivity(), SubpageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXAM_ID, SharedPrefHelper.getInstance().getExamId());
            bundle.putString(Constants.FORUM_ID, homeContent.getForumId());
            bundle.putString(Constants.MOULD_CODE, homeContent.getMouldCode());
            bundle.putString(Constants.FORUM_NAME, homeContent.getForumName());
            intent.putExtras(bundle);
            startActivity(intent);
        } else if(result.getForumList().get(groupotion).getForumName().equals("热门图书")){
            String examId = SharedPrefHelper.getInstance().getExamId();
            MainActivity.startMallFragment(getActivity(), examId,Constants.MALL_CATEGORY_TYPE_BOOK);
        } else if(result.getForumList().get(groupotion).getForumName().equals("热门课程")){
            String examId = SharedPrefHelper.getInstance().getExamId();
            MainActivity.startMallFragment(getActivity(), examId,Constants.MALL_CATEGORY_TYPE_VIDEO);
        }else{
            String examId = SharedPrefHelper.getInstance().getExamId();
            MainActivity.startMallFragment(getActivity(), examId);
        }
        return true;
    }
}
