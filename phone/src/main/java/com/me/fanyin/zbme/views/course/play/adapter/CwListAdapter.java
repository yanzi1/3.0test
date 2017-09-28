package com.me.fanyin.zbme.views.course.play.adapter;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;

import java.util.List;

/**
 * Created by fengzongwei on 2016/5/16 0016.
 */
public class CwListAdapter extends BaseAdapter {

    private List<CourseWare> courseWareWareList;
    private PlayActivity context;
    private Handler handler;

    public CwListAdapter(PlayActivity context, List<CourseWare> courseWareWareList, Handler handler) {
        this.context = context;
        this.courseWareWareList = courseWareWareList;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return courseWareWareList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.play_courelist_nosection_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout_course_body = (LinearLayout) convertView.findViewById(R.id.play_courselist_nosection_course_body);
            viewHolder.imageView_test = (TextView) convertView.findViewById(R.id.play_courselist_child_test_img);
            viewHolder.textView_title = (TextView) convertView.findViewById(R.id.play_courselist_child_tv);
            viewHolder.imgPlay = (ImageView) convertView.findViewById(R.id.img_play);
            viewHolder.local = (TextView) convertView.findViewById(R.id.tv_local);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        boolean isDownload = DownloadManager.getInstance().isCWDownlaoded(courseWareWareList.get(position));
        if (isDownload) {
            viewHolder.local.setVisibility(View.VISIBLE);
        } else {
            viewHolder.local.setVisibility(View.GONE);
        }

//        if (courseWareWareList.get(position).isPlayFinished())
//            viewHolder.textView_title.setTextColor(Color.parseColor("#DBDBDB"));
//        else
//            viewHolder.textView_title.setTextColor(Color.BLACK);

        if (context.courseWare.getId().equals(courseWareWareList.get(position).getId())) {
            viewHolder.textView_title.setTextColor(context.getResources().getColor(R.color.color_primary));
            viewHolder.imgPlay.setImageResource(R.mipmap.course_play_icodeep);
        } else {
            viewHolder.textView_title.setTextColor(context.getResources().getColor(R.color.text_color_primary_dark));
            viewHolder.imgPlay.setImageResource(R.mipmap.course_play_icolight);
        }
        if (!TextUtils.isEmpty(courseWareWareList.get(position).getPaperId())) {
            viewHolder.imageView_test.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageView_test.setVisibility(View.GONE);
        }

        viewHolder.imageView_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        viewHolder.textView_title.setText(courseWareWareList.get(position).getLectureOrder() + " " +
                courseWareWareList.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        LinearLayout linearLayout_course_body;
        TextView imageView_test, local;
        ImageView imgPlay;
        TextView textView_title;
    }

}
