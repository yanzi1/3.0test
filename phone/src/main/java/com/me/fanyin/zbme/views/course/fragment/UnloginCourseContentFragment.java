package com.me.fanyin.zbme.views.course.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.me.fanyin.zbme.views.course.adapter.MyRecycleAdatpter;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.main.activity.SubpageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class UnloginCourseContentFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    Unbinder unbinder;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;
    @BindView(R.id.exp_unlogin)
    RecyclerView expUnlogin;
    @BindView(R.id.app_message_tv)
    TextView appMessageTv;
    private View view;

    private ClassHomeBean result;
    private MyRecycleAdatpter adapter;


    public static UnloginCourseContentFragment newInstance(Bundle bundle){
        UnloginCourseContentFragment fragment=new UnloginCourseContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unlogin_course_content_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle b = getArguments();

        if (b.getBoolean("isDataError",false))
            showDataError();
        else{
            if (b.getBoolean("isNetError",false))
                showNetError();
        }


        expUnlogin.setLayoutManager(new LinearLayoutManager(getActivity()));
        expUnlogin.setNestedScrollingEnabled(false);
        adapter = new MyRecycleAdatpter(getActivity());
//        expUnlogin.setAdapter(adapter);

        if (NetWorkUtils.isConnected(getActivity())) {
            imgEmpty.setImageResource(R.mipmap.img_special_nothing);
        } else {
            imgEmpty.setImageResource(R.mipmap.img_special_nonetwork);
        }

        setData((ClassHomeBean) b.getSerializable("data"));
    }

    public void setData(ClassHomeBean result) {
        this.result = result;
        if (result !=null && result.getForumList() != null && result.getForumList().size() > 0) {
            List<ClassHomeContent> forunmList = result.getForumList();
            for (int i = 0; i < forunmList.size(); i++) {
                if (forunmList.get(i).getList() == null || forunmList.get(i).getList().size() == 0) {
                    forunmList.remove(i);
                    i--;
                }
            }
            if (forunmList.size() > 0) {
                expUnlogin.setVisibility(View.VISIBLE);
                emptyRl.setVisibility(View.GONE);

                adapter.setList(forunmList);
                expUnlogin.setAdapter(adapter);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showDataError() {
        expUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_dataerror);
        appMessageTv.setText(getResources().getString(R.string.app_error_message));
    }

    public void showDataEmpty() {
        expUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nothing);
        appMessageTv.setText(getResources().getString(R.string.app_empty_message));
    }

    public void showNetError() {
        expUnlogin.setVisibility(View.GONE);
        emptyRl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nonetwork);
        appMessageTv.setText(getResources().getString(R.string.app_nonetwork_message));
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupotion, int childPostion, long l) {
        if (result.getForumList().get(groupotion).getForumName().equals("免费试听")) {
            ClassHomeContentype contentype = result.getForumList().get(groupotion).getList().get(childPostion);
            Intent intent = new Intent(getActivity(), PlayActivity.class);
            intent.putExtra("lectureId", contentype.getId());
            intent.putExtra("isFreePlay", true);
            intent.putExtra("examId", SharedPrefHelper.getInstance().getExamId());
            intent.putExtra(Constants.LINK, contentype.getLink());
            startActivity(intent);
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
        } else if (result.getForumList().get(groupotion).getForumName().equals("热门课程")) {
            String examId = SharedPrefHelper.getInstance().getExamId();
            MainActivity.startMallFragment(getActivity(), examId);
        } else if (result.getForumList().get(groupotion).getForumName().equals("热门图书")) {
            String examId = SharedPrefHelper.getInstance().getExamId();
            MainActivity.startMallFragment(getActivity(), examId);
        }
        return true;
    }
}
