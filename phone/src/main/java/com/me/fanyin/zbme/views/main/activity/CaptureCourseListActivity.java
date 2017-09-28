package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.me.data.common.Constants;
import com.me.data.model.main.CaptureResultBean;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.course.play.audition.AuditionPlayActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.CaptureCourseAdapter;
import com.me.fanyin.zbme.views.main.event.SetCourseListEvent;
import com.me.fanyin.zbme.widget.statuslayoutmanager.RootFrameLayout;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/23.
 */

public class CaptureCourseListActivity extends BaseActivity {

    @BindView(R.id.rcv)
    RecyclerView rcv;
    private CaptureCourseAdapter mAdapter;
    private SetCourseListEvent extra;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        extra = (SetCourseListEvent) getIntent().getSerializableExtra(Constants.COURSE_ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_capture_course_list_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.main_capture_course_list_content;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("辅导书相关知识点课程");

        rcv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CaptureCourseAdapter();
        mAdapter.bindToRecyclerView(rcv);

        View view = mRootFrameLayout.getLayout(RootFrameLayout.LAYOUT_NO_PERMISSION_ID);
        TextView app_message_tv = (TextView) view.findViewById(R.id.app_message_tv);
        app_message_tv.setText("本知识点仅针对东奥购课用户");
        Button app_action_no_permission_btn = (Button) view.findViewById(R.id.app_action_no_permission_btn);
        app_action_no_permission_btn.setVisibility(View.VISIBLE);
        app_action_no_permission_btn.setText("去购买");
    }

    @Override
    protected void initData() {
        if (extra != null && extra.getList().size() != 0) {
            mAdapter.setNewData(extra.getList());
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    CaptureResultBean.CourseVoBean courseVoBean = (CaptureResultBean.CourseVoBean) adapter.getData().get(position);
                    CourseWare courseWare = new CourseWare();
                    courseWare.setId(courseVoBean.getId() + "");
                    courseWare.setName(courseVoBean.getName());
                    courseWare.setTotalTime(courseVoBean.getTotalTime());
                    courseWare.setStartTime(courseVoBean.getStartTime());
                    courseWare.setEndTime(courseVoBean.getEndTime());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cw", courseWare);
                    gotoActivity(AuditionPlayActivity.class, false, bundle);
                }
            });
            mStatusLayoutManager.showContent();
        } else {
            //TODO wyc修改 19327bug
            mStatusLayoutManager.showNoPermission();
        }
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                MainActivity.startMallFragment(CaptureCourseListActivity.this, null);
            }
        };
    }

}
