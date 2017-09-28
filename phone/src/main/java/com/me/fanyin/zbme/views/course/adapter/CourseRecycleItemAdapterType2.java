package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.core.utils.StringUtils;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CourseRecycleItemAdapterType2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ClassHomeContentype> results;

    //get & set
    public List<ClassHomeContentype> getResults() {
        return results;
    }

    public CourseRecycleItemAdapterType2(Context context, List<ClassHomeContentype> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            bind((ItemViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(ItemViewHolder holder, int position){
        if (position == results.size() - 1) {
            holder.line.setVisibility(View.GONE);
            holder.bottom.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
            holder.bottom.setVisibility(View.GONE);
        }
        holder.title.setText(results.get(position).getTitle());
        String price=results.get(position).getTab();
        Double dPrice=Double.parseDouble(price);
        String enPrice= StringUtils.formatPrice(dPrice);
        holder.already.setText("￥ " + enPrice);
        holder.name.setText("");
        String path = results.get(position).getImage();

        Glide.with(context).load(path)
                .placeholder(R.mipmap.img_default_course)
                .into(holder.img);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView name;
        TextView already;
        ImageView img;
        View line, bottom;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.course_title);
            already = (TextView) itemView.findViewById(R.id.alreday);
            name = (TextView) itemView.findViewById(R.id.course_teacher);
            img = (ImageView) itemView.findViewById(R.id.course_img);
            line = itemView.findViewById(R.id.view_line);
            bottom = itemView.findViewById(R.id.ling_bottom);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
