package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.ClassHomeContent;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;

import java.util.List;

/**
 * Created by dell on 2017/4/26.
 */

public class CourseExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ClassHomeContent> list;
    private GridFreeAdapter adapter;
    private GridBookAdapter adapterBook;

    public CourseExpandAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ClassHomeContent> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (list.get(i).getForumName().equals("免费试听") || list.get(i).getForumName().equals("热门图书")) {
            return 1;
        } else {
            return list.get(i).getList().size();
        }
    }

    @Override
    public Object getGroup(int i) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GroupViewHolder();
            convertView = View.inflate(context, R.layout.courses_group_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_tv);
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
        viewHolder.title.setText(list.get(groupPosition).getForumName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        String groupName = list.get(groupPosition).getForumName();
        if (groupName.equals("免费试听")) {
            final List<ClassHomeContentype> listtypes = list.get(groupPosition).getList();

            convertView = View.inflate(context, R.layout.courses_course_free, null);
            GridView gridView = (GridView) convertView.findViewById(R.id.gridview);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ClassHomeContentype contentype = listtypes.get(position);
                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra("lectureId", contentype.getId());
                    intent.putExtra("isFreePlay", true);
                    intent.putExtra("title", contentype.getTitle());
                    intent.putExtra("examId", SharedPrefHelper.getInstance().getExamId());
                    intent.putExtra(Constants.LINK, contentype.getLink());
                    context.startActivity(intent);
                }
            });
            adapter = new GridFreeAdapter(context);
            adapter.setList(list.get(groupPosition).getList());
            gridView.setAdapter(adapter);

        } else if (groupName.equals("热门图书")) {
            final List<ClassHomeContentype> listtypes = list.get(groupPosition).getList();
            convertView = View.inflate(context, R.layout.courses_course_free, null);
            GridView gridView = (GridView) convertView.findViewById(R.id.gridview);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ClassHomeContentype contentype = listtypes.get(position);
                }
            });
            adapterBook = new GridBookAdapter(context);
            adapterBook.setList(list.get(groupPosition).getList());
            gridView.setAdapter(adapterBook);
        } else {
            ChildViewHolderImg viewHolder;
            if (convertView == null || !(convertView.getTag() instanceof ChildViewHolderImg)) {
                viewHolder = new ChildViewHolderImg();
                convertView = View.inflate(context, R.layout.course_course_item, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.course_title);
                viewHolder.already = (TextView) convertView.findViewById(R.id.alreday);
                viewHolder.name = (TextView) convertView.findViewById(R.id.course_teacher);
                viewHolder.img = (ImageView) convertView.findViewById(R.id.course_img);
                viewHolder.line = convertView.findViewById(R.id.view_line);
                viewHolder.bottom = convertView.findViewById(R.id.ling_bottom);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ChildViewHolderImg) convertView.getTag();
            }
            if (childPosition == list.get(groupPosition).getList().size() - 1) {
                viewHolder.line.setVisibility(View.GONE);
                viewHolder.bottom.setVisibility(View.VISIBLE);
            } else {
                viewHolder.line.setVisibility(View.VISIBLE);
                viewHolder.bottom.setVisibility(View.GONE);
            }
            viewHolder.title.setText(list.get(groupPosition).getList().get(childPosition).getTitle());
            viewHolder.already.setText("￥ " + list.get(groupPosition).getList().get(childPosition).getTab());
            viewHolder.name.setText("");
            String path = list.get(groupPosition).getList().get(childPosition).getImage();

            Glide.with(context).load(path)
                    .placeholder(R.mipmap.img_default_course)
                    .into(viewHolder.img);

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView title;
        View view;
    }

    class ChildViewHolderImg {
        TextView title;
        TextView name;
        TextView already;
        ImageView img;
        View line, bottom;
    }
}
