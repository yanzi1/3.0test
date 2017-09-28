package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.data.common.Constants;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CourseRecycleItemAdapterType1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ClassHomeContentype> results;

    //get & set
    public List<ClassHomeContentype> getResults() {
        return results;
    }

    public CourseRecycleItemAdapterType1(Context context, List<ClassHomeContentype> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_course_item, parent, false));
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
        holder.title.setText(results.get(position).getTitle());
        holder.name.setText(results.get(position).getAuthor());
        holder.level.setText(results.get(position).getTab());
        String path = results.get(position).getImage();

        Glide.with(context).load(path)
                .placeholder(R.mipmap.img_default_course)
                .into(holder.img);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView name;
        TextView level;
        ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_free);
            name = (TextView) itemView.findViewById(R.id.teacher_free);
            level = (TextView) itemView.findViewById(R.id.level_free);
            img = (ImageView) itemView.findViewById(R.id.image_free);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassHomeContentype contentype = results.get(getLayoutPosition());
                    Intent intent = new Intent(context, PlayActivity.class);
                    if(contentype.getId().contains("_")){
                        String[] str=contentype.getId().split("_");
                        intent.putExtra("lectureId", str[1]);
                        intent.putExtra("examId", str[0]);
                    }else{
                        intent.putExtra("examId", contentype.getId());
                        intent.putExtra("lectureId", "");
                    }
                    intent.putExtra("isFreePlay", true);
                    intent.putExtra("title", contentype.getTitle());
                    intent.putExtra(Constants.LINK, contentype.getLink());
                    context.startActivity(intent);
                }
            });
        }
    }
}
