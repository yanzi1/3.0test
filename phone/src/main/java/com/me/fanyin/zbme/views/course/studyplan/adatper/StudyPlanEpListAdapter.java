package com.me.fanyin.zbme.views.course.studyplan.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.data.model.intel.IntelWorkReview;
import com.me.data.model.play.StudyPlanReviewBean;
import com.me.fanyin.zbme.R;

import java.util.List;

public class StudyPlanEpListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<StudyPlanReviewBean> reviewList;

    public StudyPlanEpListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<StudyPlanReviewBean> reviewList){
        this.reviewList=reviewList;
    }

    @Override
    public int getGroupCount() {
        return reviewList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return reviewList.get(groupPosition).getStudyTaskList().size();
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
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.studycase_group, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.textView_groupTitle = (TextView) convertView.findViewById(R.id.studycase_group_tv);
            viewHolderGroup.imageView_arrow = (ImageView) convertView.findViewById(R.id.studycase_group_img);
            viewHolderGroup.empty=convertView.findViewById(R.id.empty_view);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }

        if (isExpanded) {
            viewHolderGroup.empty.setVisibility(View.GONE);
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.studycase_empty);
        } else {
            viewHolderGroup.empty.setVisibility(View.VISIBLE);
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.studycase_expand);
        }
        viewHolderGroup.textView_groupTitle.setText(reviewList.get(groupPosition).getDayName());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChildCourse viewHolderChildCourse;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.studycase_child, parent, false);
            viewHolderChildCourse = new ViewHolderChildCourse();
            viewHolderChildCourse.textView_title = (TextView) convertView.findViewById(R.id.studycase_title);
            viewHolderChildCourse.already = (TextView) convertView.findViewById(R.id.tv_already);
            viewHolderChildCourse.ico=(ImageView)convertView.findViewById(R.id.child_img);
            viewHolderChildCourse.line=convertView.findViewById(R.id.view_line);
            convertView.setTag(viewHolderChildCourse);
        } else {
            viewHolderChildCourse = (ViewHolderChildCourse) convertView.getTag();
        }

        StudyPlanReviewBean reviewBean=reviewList.get(groupPosition);
        IntelWorkReview intelWorkReview=reviewBean.getStudyTaskList().get(childPosition);
        viewHolderChildCourse.textView_title.setText(intelWorkReview.getNodeName());


        if(reviewBean.getIsToday().equals("1")){
            viewHolderChildCourse.textView_title.setTextColor(context.getResources().getColor(R.color.text_color_primary_dark));
        }else{
            viewHolderChildCourse.textView_title.setTextColor(context.getResources().getColor(R.color.text_color_primary_light_more));
        }

        if(intelWorkReview.getReviewStatus().equals("2")){
            viewHolderChildCourse.ico.setImageResource(R.mipmap.knowledge_nostudy);
            viewHolderChildCourse.already.setText("未复习");
            viewHolderChildCourse.already.setTextColor(context.getResources().getColor(R.color.text_color_primary_light_more));
        }else{
            viewHolderChildCourse.ico.setImageResource(R.mipmap.knowledge_already);
            viewHolderChildCourse.already.setText("已复习");
            viewHolderChildCourse.already.setTextColor(context.getResources().getColor(R.color.color_accent_2));
        }

        if(childPosition==reviewList.get(groupPosition).getStudyTaskList().size()-1){
            viewHolderChildCourse.line.setVisibility(View.INVISIBLE);
        }else{
            viewHolderChildCourse.line.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolderGroup {
        TextView textView_groupTitle;
        ImageView imageView_arrow;
        View empty;
    }

    static class ViewHolderChildCourse {
        TextView textView_title,already;
        ImageView ico;
        View line;
    }

}
