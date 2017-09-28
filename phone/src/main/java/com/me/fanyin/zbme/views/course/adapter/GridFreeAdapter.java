package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by dell on 2017/4/25.
 */

public class GridFreeAdapter extends BaseAdapter {

    private Context context;
    private List<ClassHomeContentype> list;

    public GridFreeAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ClassHomeContentype> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.courses_course_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title_free);
            holder.name = (TextView) convertView.findViewById(R.id.teacher_free);
            holder.level = (TextView) convertView.findViewById(R.id.level_free);
            holder.img = (ImageView) convertView.findViewById(R.id.image_free);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.name.setText(list.get(position).getAuthor());
        holder.level.setText(list.get(position).getTab());
        String path = list.get(position).getImage();

        Glide.with(context).load(path)
                .placeholder(R.mipmap.img_default_course)
                .into(holder.img);
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView name;
        TextView level;
        ImageView img;
    }
}
