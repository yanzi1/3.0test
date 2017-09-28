package io.vov.vitamio.widget.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;

/**
 * Created by fengzongwei on 2016/1/4.
 */
public class MediaControllerEpListAdapter extends BaseExpandableListAdapter {

    private CourseDetail courseDetail;
    private PlayActivity context;
    private CourseWare cw;
    private final int[] childeType = {0, 1};
    private int playingGroupPosition, playingChildPosition;

    public MediaControllerEpListAdapter(PlayActivity context, CourseDetail courseDetail, CourseWare cw) {
        this.context = context;
        this.courseDetail = courseDetail;
        this.cw = cw;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.media_group, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.textView_groupTitle = (TextView) convertView.findViewById(R.id.play_courselist_group_tv);
            viewHolderGroup.imageView_arrow = (ImageView) convertView.findViewById(R.id.play_courselist_group_img);
            viewHolderGroup.linearLayout_course_body = (LinearLayout) convertView.findViewById(R.id.play_courselist_group_course_body);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }

        viewHolderGroup.textView_groupTitle.setText(courseDetail.getResultList().get(groupPosition).getName());
        if (!isExpanded) {
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.course_play_expandmore);
        } else {
            viewHolderGroup.imageView_arrow.setImageResource(R.mipmap.course_play_expandless);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChildCourse viewHolderChildCourse = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.media_child, parent, false);
            viewHolderChildCourse = new ViewHolderChildCourse();
            viewHolderChildCourse.textView_title = (TextView) convertView.findViewById(R.id.play_courselist_child_tv);
            viewHolderChildCourse.local = (TextView) convertView.findViewById(R.id.tv_local);
            viewHolderChildCourse.img_play = (ImageView) convertView.findViewById(R.id.img_play);
            convertView.setTag(viewHolderChildCourse);
        } else {
            viewHolderChildCourse = (ViewHolderChildCourse) convertView.getTag();
        }
        boolean isDownload= DownloadManager.getInstance().isCWDownlaoded(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition));
        if(isDownload){
            viewHolderChildCourse.local.setVisibility(View.VISIBLE);
        }else{
            viewHolderChildCourse.local.setVisibility(View.GONE);
        }

        viewHolderChildCourse.textView_title.setText(courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                .get(childPosition).getLectureOrder() + " " + courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList()
                .get(childPosition).getName());

        if (courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition).getName().equals(cw.getName())) {
            viewHolderChildCourse.textView_title.setTextColor(context.getResources().getColor(R.color.color_primary_select));
            viewHolderChildCourse.img_play.setImageResource(R.mipmap.course_play_icodeep);
        } else {
            viewHolderChildCourse.textView_title.setTextColor(Color.WHITE);
            viewHolderChildCourse.img_play.setImageResource(R.mipmap.course_play_icolight);
        }

        return convertView;
    }

    public void setPlayingPosition(int playingGroupPosition, int playingChildPosition) {
        this.playingGroupPosition = playingGroupPosition;
        this.playingChildPosition = playingChildPosition;
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
        TextView textView_title,local;
        ImageView img_play;
        ImageView imageView_download_status;
        TextView textView_download_status, textView_progress, textView_courseName;
        LinearLayout ll_download;
    }
}
