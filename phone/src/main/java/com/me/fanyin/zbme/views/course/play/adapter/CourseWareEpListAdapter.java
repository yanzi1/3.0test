package com.me.fanyin.zbme.views.course.play.adapter;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.model.play.CourseDetail;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;

/**
 * Created by fengzongwei on 2016/1/4.
 */
public class CourseWareEpListAdapter extends BaseExpandableListAdapter {

    private CourseDetail courseDetail;
    private PlayActivity context;
    private Handler handler;

    //    private final int[] childeType = {0,1};
    public CourseWareEpListAdapter(PlayActivity context, CourseDetail courseDetail) {
        this.context = context;
        this.courseDetail = courseDetail;
    }

    public CourseWareEpListAdapter(PlayActivity context, CourseDetail courseDetail, Handler handler) {
        this.context = context;
        this.courseDetail = courseDetail;
        this.handler = handler;
    }

    @Override
    public int getGroupCount() {
        return courseDetail.getResultList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.play_courselist_group, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.textView_groupTitle = (TextView) convertView.findViewById(R.id.play_courselist_group_tv);
            viewHolderGroup.imageView_arrow = (ImageView) convertView.findViewById(R.id.play_courselist_group_img);
            viewHolderGroup.linearLayout_course_body = (LinearLayout) convertView.findViewById(R.id.play_courselist_group_course_body);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }

        viewHolderGroup.linearLayout_course_body.setVisibility(View.VISIBLE);
        if (isExpanded) {
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.course_play_expandless);
        } else {
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.course_play_expandmore);
        }
        viewHolderGroup.textView_groupTitle.setText(courseDetail.getResultList().get(groupPosition).getName());


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChildCourse viewHolderChildCourse = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.play_courselist_child, parent, false);
            viewHolderChildCourse = new ViewHolderChildCourse();
            viewHolderChildCourse.textView_title = (TextView) convertView.findViewById(R.id.play_courselist_child_tv);
            viewHolderChildCourse.linearLayout_body = (LinearLayout) convertView.findViewById(R.id.play_courselist_child_body);
            viewHolderChildCourse.ico=(ImageView)convertView.findViewById(R.id.course_detail_ico);
            viewHolderChildCourse.test = (TextView) convertView.findViewById(R.id.play_courselist_child_test);
            viewHolderChildCourse.local = (TextView) convertView.findViewById(R.id.tv_local);
            convertView.setTag(viewHolderChildCourse);
        } else {
            viewHolderChildCourse = (ViewHolderChildCourse) convertView.getTag();
        }

        viewHolderChildCourse.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        boolean isDownload = DownloadManager.getInstance().isCWDownlaoded(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition));
        if(isDownload){
            viewHolderChildCourse.local.setVisibility(View.VISIBLE);
        }else{
            viewHolderChildCourse.local.setVisibility(View.GONE);
        }

//        if (courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition).isPlayFinished())
//            viewHolderChildCourse.textView_title.setTextColor(Color.parseColor("#DBDBDB"));
//        else
//            viewHolderChildCourse.textView_title.setTextColor(Color.BLACK);

        if (context.courseWare.getId().equals(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition).getId())) {
            viewHolderChildCourse.textView_title.setTextColor(context.getResources().getColor(R.color.color_primary));
            viewHolderChildCourse.ico.setImageResource(R.mipmap.course_play_icodeep);
        } else {
            viewHolderChildCourse.textView_title.setTextColor(context.getResources().getColor(R.color.text_color_primary_dark));
            viewHolderChildCourse.ico.setImageResource(R.mipmap.course_play_icolight);
        }

        if (!TextUtils.isEmpty(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition).getPaperId())) {
            viewHolderChildCourse.test.setVisibility(View.VISIBLE);
        } else {
            viewHolderChildCourse.test.setVisibility(View.GONE);
        }


        //正常的课子view操作
        if (courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                .get(childPosition).getLectureOrder() != null)
            viewHolderChildCourse.textView_title.setText(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                    .get(childPosition).getLectureOrder() + " " +
                    courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                            .get(childPosition).getName());
        else
            viewHolderChildCourse.textView_title.setText(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                    .get(childPosition).getName());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolderGroup {
        TextView textView_groupTitle;
        ImageView imageView_arrow;
        LinearLayout linearLayout_course_body;
    }

    static class ViewHolderChildCourse {
        TextView textView_title,test,local;
        LinearLayout linearLayout_body;
        ImageView ico;
    }

}
