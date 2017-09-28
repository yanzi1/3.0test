package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.fragment.StatisticalFragment;

import java.util.List;

/**
 * Created by xd on 2017/7/11.
 */

public class CourseStatiscalAdapter extends RecyclerView.Adapter<CourseStatiscalAdapter.CourseStatiscalViewHolder>{
    private Context context;
    private List<CourseStatiscalAdapterBean> list;
    private int type;
    private OnItemClickListener listener;

    public CourseStatiscalAdapter(Context context, List<CourseStatiscalAdapterBean> list,OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @Override
    public CourseStatiscalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseStatiscalViewHolder(LayoutInflater.from(context).inflate(R.layout.course_statiscal_item_layout,parent,false),listener);
    }

    @Override
    public void onBindViewHolder(CourseStatiscalViewHolder holder, int position) {
        CourseStatiscalAdapterBean itemBean = list.get(position);
        holder.course_statiscal_color_view.setBackgroundColor(itemBean.getColor());
        holder.course_statiscal_name_tv.setText(itemBean.getName());
        String dataText="";
        if (type== StatisticalFragment.TYPE_LECTURES){
            dataText="Min";
        }else if (type== StatisticalFragment.TYPE_QUESTION){
            dataText="题";
        }
        holder.course_statiscal_data_tv.setText(itemBean.getData()+dataText);
        holder.course_statiscal_rootview.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public void setType(int type){
        this.type=type;
    }

    static class CourseStatiscalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View course_statiscal_color_view,course_statiscal_rootview;
        TextView course_statiscal_name_tv,course_statiscal_data_tv;
        OnItemClickListener listener;

        public CourseStatiscalViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            course_statiscal_color_view=itemView.findViewById(R.id.course_statiscal_color_view);
            course_statiscal_name_tv= (TextView) itemView.findViewById(R.id.course_statiscal_name_tv);
            course_statiscal_data_tv= (TextView) itemView.findViewById(R.id.course_statiscal_data_tv);
            course_statiscal_rootview=  itemView.findViewById(R.id.course_statiscal_rootview);
            course_statiscal_rootview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.itemClick(v, (Integer) v.getTag());
        }
    }

    public interface OnItemClickListener{
        void itemClick(View view,int position);
    }

    public static class CourseStatiscalAdapterBean{
        private String name;
        private String data;
        private int color;

        public CourseStatiscalAdapterBean() {
        }

        public CourseStatiscalAdapterBean(String name, String data, int color) {
            this.name = name;
            this.data = data;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}


