package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.core.utils.DensityUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.ClassHomeContent;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.main.activity.SubpageActivity;
import com.me.fanyin.zbme.views.main.activity.divider.SpacesItemDecoration;

import java.util.List;

/**
 * Created by dell on 2017/7/3.
 */

public class MyRecycleAdatpter extends RecyclerView.Adapter{

    private Context context;
    private List<ClassHomeContent> forumList;
    private MyItemClickListener mListener;
    private SpacesItemDecoration spacesItemDecoration;

    public MyRecycleAdatpter(Context context) {
        this.context=context;
        int leftRight = DensityUtils.dip2px(context,15);
        int topBottom = DensityUtils.dip2px(context,10);
        spacesItemDecoration=new SpacesItemDecoration(leftRight,topBottom);
    }

    public void setList(List<ClassHomeContent> forumList){
        this.forumList=forumList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_group_item_type1, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            if(position==0){
                ((MyViewHolder) holder).topShow.setVisibility(View.GONE);
            }else{
                ((MyViewHolder) holder).topShow.setVisibility(View.VISIBLE);
            }
            ((MyViewHolder) holder).title.setText(forumList.get(position).getForumName());
            if(forumList.get(position).getForumName().equals("免费试听")){
                ((MyViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                ((MyViewHolder) holder).recyclerView.addItemDecoration(spacesItemDecoration);
                ((MyViewHolder) holder).recyclerView.setAdapter(new CourseRecycleItemAdapterType1(context,forumList.get(position).getList()));
            }else if(forumList.get(position).getForumName().equals("热门图书")){
                ((MyViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                ((MyViewHolder) holder).recyclerView.addItemDecoration(spacesItemDecoration);
                ((MyViewHolder) holder).recyclerView.setAdapter(new CourseRecycleItemAdapterType3(context,forumList.get(position).getList()));
            }else{
                ((MyViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ((MyViewHolder) holder).recyclerView.setAdapter(new CourseRecycleItemAdapterType2(context,forumList.get(position).getList()));
            }

        }
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.mListener = listener;
    }

    class MyViewHolder  extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView title;
        private RecyclerView recyclerView;
        private LinearLayout ll_more;
        private View topShow;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title_tv);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.item_recycleView);
            ll_more=(LinearLayout)itemView.findViewById(R.id.more_ll);
            topShow=itemView.findViewById(R.id.top_show);
//            itemView.setOnClickListener(this);
            ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(forumList.get(getLayoutPosition()).getForumName().equals("免费试听")){
                        ClassHomeContent homeContent = forumList.get(getLayoutPosition());
                        Intent intent = new Intent(context, SubpageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, SharedPrefHelper.getInstance().getExamId());
                        bundle.putString(Constants.FORUM_ID, homeContent.getForumId());
                        bundle.putString(Constants.MOULD_CODE, homeContent.getMouldCode());
                        bundle.putString(Constants.FORUM_NAME, homeContent.getForumName());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else if(forumList.get(getLayoutPosition()).getForumName().equals("热门图书")){
                        String examId = SharedPrefHelper.getInstance().getExamId();
                        MainActivity.startMallFragment(context, examId,Constants.MALL_CATEGORY_TYPE_BOOK);
                    }else if(forumList.get(getLayoutPosition()).getForumName().equals("热门课程")){
                        String examId = SharedPrefHelper.getInstance().getExamId();
                        MainActivity.startMallFragment(context, examId,Constants.MALL_CATEGORY_TYPE_VIDEO);
                    }else{
                        String examId = SharedPrefHelper.getInstance().getExamId();
                        MainActivity.startMallFragment(context, examId);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getLayoutPosition());
            }
        }

    }

    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }
}
