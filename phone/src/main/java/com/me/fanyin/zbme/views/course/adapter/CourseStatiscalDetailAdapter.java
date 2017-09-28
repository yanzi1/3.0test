package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.data.model.course.CourseStatiscalDetailBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.fragment.StatisticalFragment;

import java.util.List;

/**
 * Created by xd on 2017/7/11.
 */

public class CourseStatiscalDetailAdapter extends RecyclerView.Adapter<CourseStatiscalDetailAdapter.CourseStatiscalViewHolder>{
    private Context context;
    private List<CourseStatiscalDetailBean.DateBean.ItemDataBean> list;
    private int type;

    public CourseStatiscalDetailAdapter(Context context, List<CourseStatiscalDetailBean.DateBean.ItemDataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CourseStatiscalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseStatiscalViewHolder(LayoutInflater.from(context).inflate(R.layout.course_statiscal_detail_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(CourseStatiscalViewHolder holder, int position) {
        CourseStatiscalDetailBean.DateBean.ItemDataBean itemBean = list.get(position);
        holder.course_statiscal_name_tv.setText(itemBean.getDate());
        String dataText="";
        if (type== StatisticalFragment.TYPE_LECTURES){
            dataText="Min";
        }else if (type== StatisticalFragment.TYPE_QUESTION){
            dataText="题";
        }
        holder.course_statiscal_data_tv.setText((int)itemBean.getStudyLength()+dataText);
        if (position == list.size()-1){
            holder.bottom_line.setVisibility(View.GONE);
        }else{
            holder.bottom_line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public void setType(int type){
        this.type=type;
    }

    static class CourseStatiscalViewHolder extends RecyclerView.ViewHolder {
        TextView course_statiscal_name_tv,course_statiscal_data_tv;
        View bottom_line;

        public CourseStatiscalViewHolder(View itemView) {
            super(itemView);
            course_statiscal_name_tv= (TextView) itemView.findViewById(R.id.course_statiscal_detail_name_tv);
            course_statiscal_data_tv= (TextView) itemView.findViewById(R.id.course_statiscal_detail_data_tv);
            bottom_line=  itemView.findViewById(R.id.bottom_line);
        }
    }
}


