package com.me.fanyin.zbme.views.main.activity.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.data.model.main.CaptureResultBean;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.audition.AuditionPlayActivity;

/**
 * Created by jjr on 2017/5/23.
 */

public class CaptureCourseAdapter extends BaseQuickAdapter<CaptureResultBean.CourseVoBean, BaseViewHolder> {

    public CaptureCourseAdapter() {
        super(R.layout.main_capture_course_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CaptureResultBean.CourseVoBean item) {
        helper.setText(R.id.capture_course_item_title_tv, item.getName());
        helper.setText(R.id.capture_course_item_time_tv, item.getStartTime() + " - " + item.getEndTime());
        helper.getView(R.id.capture_course_item_continu_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseWare courseWare = new CourseWare();
                courseWare.setId(item.getId() + "");
                courseWare.setName(item.getName());
                courseWare.setTotalTime(item.getTotalTime());
                courseWare.setStartTime(item.getStartTime());
                courseWare.setEndTime(item.getEndTime());
                Bundle bundle = new Bundle();
                bundle.putSerializable("cw", courseWare);
                gotoActivity(AuditionPlayActivity.class, bundle);
            }
        });
        helper.setVisible(R.id.capture_course__item_sp, getData().size() - 1 == helper.getLayoutPosition() ? true : false);
    }

    public void gotoActivity(Class<?> clz, Bundle ex) {
        Intent intent = new Intent(getRecyclerView().getContext(), clz);
        if (ex != null) intent.putExtras(ex);
        mContext.startActivity(intent);
    }
}
