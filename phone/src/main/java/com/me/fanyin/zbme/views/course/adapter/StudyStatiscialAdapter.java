package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.data.model.course.CourseStatiscalDetailBean;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by fishzhang on 2017/8/29.
 */

public class StudyStatiscialAdapter extends RecyclerView.Adapter<StudyStatiscialAdapter.StudyStatiscialVH>{
    private Context context;
    private List<CourseStatiscalDetailBean.DateBean.ItemDataBean> datas;
    private int type;

    public StudyStatiscialAdapter(Context context, List<CourseStatiscalDetailBean.DateBean.ItemDataBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public StudyStatiscialVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudyStatiscialVH(LayoutInflater.from(context).inflate(R.layout.study_statiscal_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(StudyStatiscialVH holder, int position) {
        CourseStatiscalDetailBean.DateBean.ItemDataBean itemDataBean = datas.get(position);
        holder.date.setText(itemDataBean.getDate());
        int studyLength = (int) itemDataBean.getStudyLength();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class StudyStatiscialVH extends RecyclerView.ViewHolder{

        TextView date,count;

        public StudyStatiscialVH(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.study_statiscal_detail_date_tv);
            count = (TextView) itemView.findViewById(R.id.study_statiscal_detail_count_tv);
        }
    }
}
