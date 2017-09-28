package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.core.utils.StringUtils;
import com.me.data.model.play.ClassHomeContentype;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.fragemnt.view.LoveBookImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CourseRecycleItemAdapterType3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ClassHomeContentype> results;

    //get & set
    public List<ClassHomeContentype> getResults() {
        return results;
    }

    public CourseRecycleItemAdapterType3(Context context, List<ClassHomeContentype> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_item_type4, parent, false));
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
        String price=results.get(position).getTab();
        Double dPrice=Double.parseDouble(price);
        String enPrice=StringUtils.formatPrice(dPrice);
        holder.price.setText("￥ "+enPrice);
        String path = results.get(position).getImage();

        Glide.with(context).load(path)
                .placeholder(R.mipmap.img_default_course)
                .into(holder.img);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        LoveBookImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_free);
            price = (TextView) itemView.findViewById(R.id.tv_charge);
            img = (LoveBookImageView) itemView.findViewById(R.id.image_free);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
