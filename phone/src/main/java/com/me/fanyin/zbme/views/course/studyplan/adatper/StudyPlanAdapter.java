package com.me.fanyin.zbme.views.course.studyplan.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.me.data.model.intel.IntelWeekTask;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by fengzongwei on 2016/5/16 0016.
 */
public class StudyPlanAdapter extends BaseAdapter {

    private Context context;
    private List<IntelWeekTask> studyTaskList;

    public StudyPlanAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<IntelWeekTask> studyTaskList){
        this.studyTaskList=studyTaskList;
    }

    @Override
    public int getCount() {
        return studyTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.studycase_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.studycase_list_title);
            viewHolder.times = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.already = (TextView) convertView.findViewById(R.id.tv_already);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img_status);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.studycase_planstar);
            viewHolder.line=convertView.findViewById(R.id.view_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        IntelWeekTask intelWeekTask=studyTaskList.get(position);
        viewHolder.title.setText(intelWeekTask.getNodeName());
        viewHolder.times.setText(intelWeekTask.getDuration());
        viewHolder.ratingBar.setRating(intelWeekTask.getStar());

        if(intelWeekTask.getStudyStatus()==0){
            viewHolder.img.setImageResource(R.mipmap.knowledge_notyet);
            viewHolder.already.setText("未掌握");
            viewHolder.already.setTextColor(context.getResources().getColor(R.color.color_accent));
        }else if(intelWeekTask.getStudyStatus()==1){
            viewHolder.img.setImageResource(R.mipmap.knowledge_already);
            viewHolder.already.setText("已掌握");
            viewHolder.already.setTextColor(context.getResources().getColor(R.color.color_accent_2));
        }else{
            viewHolder.img.setImageResource(R.mipmap.knowledge_nostudy);
            viewHolder.already.setText("未学习");
            viewHolder.already.setTextColor(context.getResources().getColor(R.color.text_color_primary_light_more));
        }

        if(position==studyTaskList.size()-1){
            viewHolder.line.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView title, times,already;
        RatingBar ratingBar;
        View line;
        ImageView img;
    }

}
