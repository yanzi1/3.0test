package com.me.fanyin.zbme.views.course.play.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.data.model.play.Course;
import com.me.data.model.play.CourseList;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by dell on 2016/8/23.
 */
public class CourseListExpandAdapter extends BaseExpandableListAdapter {

    private List<CourseList> mList;
    private Context mContext;

    public CourseListExpandAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List list) {
        this.mList = list;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getCourseItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GroupViewHolder();
            convertView = View.inflate(mContext, R.layout.course_course_group, null);
            viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subject_name);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img_expand);
            viewHolder.view = convertView.findViewById(R.id.top_show);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }

        if (groupPosition == 0) {
            viewHolder.view.setVisibility(View.GONE);
        } else {
            viewHolder.view.setVisibility(View.VISIBLE);
        }
        viewHolder.subjectName.setText(mList.get(groupPosition).getCourseTypeName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = View.inflate(mContext, R.layout.course_course_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.course_title);
            holder.teacher = (TextView) convertView.findViewById(R.id.course_teacher);
            holder.already = (TextView) convertView.findViewById(R.id.alreday);
            holder.img = (ImageView) convertView.findViewById(R.id.course_img);
            holder.line = convertView.findViewById(R.id.view_line);
            holder.bottom = convertView.findViewById(R.id.ling_bottom);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        if (childPosition == mList.get(groupPosition).getCourseItems().size() - 1) {
            holder.line.setVisibility(View.GONE);
            holder.bottom.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
            holder.bottom.setVisibility(View.GONE);
        }
        Course course = mList.get(groupPosition).getCourseItems().get(childPosition);
        if (mList.get(groupPosition).getCourseTypeName().equals("已学习")) {
            holder.title.setText(course.getName());
            if(!TextUtils.isEmpty(course.getProgressSuggested())){
                holder.teacher.setText("/" + course.getProgressSuggested());
            }else{
                holder.teacher.setText("");
            }
            holder.already.setText("已听至" + course.getEndLectureOrder());
            holder.already.setTextColor(mContext.getResources().getColor(R.color.color_primary));
        } else {
            holder.title.setText(course.getName());
            if(!TextUtils.isEmpty(course.getProgressSuggested())){
                holder.teacher.setText("/" + course.getProgressSuggested());
            }else{
                holder.teacher.setText("");
            }
            holder.already.setText("未开始学习");
            holder.already.setTextColor(mContext.getResources().getColor(R.color.text_color_primary_light_more));
        }
        if(course.getCwNumber().equals("0")){
            holder.already.setText("暂未开课");
            holder.teacher.setText("");
        }

        Glide.with(mContext).load(course.getLecturer().getPicPath())
                .placeholder(R.mipmap.img_default_course)
                .into(holder.img);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView subjectName;
        ImageView img;
        View view;
    }

    class ChildViewHolder {
        TextView title;
        TextView teacher;
        TextView already;
        ImageView img;
        View line, bottom;
    }
}
