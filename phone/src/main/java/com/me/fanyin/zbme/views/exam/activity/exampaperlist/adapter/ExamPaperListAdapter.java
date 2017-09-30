package com.me.fanyin.zbme.views.exam.activity.exampaperlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean.Knowledge;

import java.util.List;

public class ExamPaperListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<Knowledge> mFeedList;
    private Context context;
    private ReclerViewItemClick reclerViewItemClick;
    private boolean isShow=false;

    public ExamPaperListAdapter(Context context, List<Knowledge> feedList) {
        mFeedList = feedList;
        this.context = context;
    }

    public void setNoDataShow(boolean isShow){
        this.isShow=isShow;
    }
    
    public void setOnItemClick(ReclerViewItemClick reclerViewItemClick){
        this.reclerViewItemClick=reclerViewItemClick;
    }

    @Override
    public int getItemViewType(int position) {
       if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       
        if (viewType == TYPE_ITEM) {
            View view = View.inflate(parent.getContext(), R.layout.exam_list_item, null);
            return new ExamPaperListViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.swiperefresh_item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ExamPaperListViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reclerViewItemClick!=null){
                        reclerViewItemClick.onItemClickListenerPosition(position);
                    }
                }
            });
            Knowledge homeModel = mFeedList.get(position);

            if (homeModel.getFinishedQuestions()==0){
                ((ExamPaperListViewHolder)holder).ll_have_done.setVisibility(View.INVISIBLE);
            }else {
                ((ExamPaperListViewHolder)holder).ll_have_done.setVisibility(View.VISIBLE);
            }
            
//            int errorNumber=(int)(homeModel.getTotalQuestions()-(homeModel.getTotalQuestions()*Float.valueOf(homeModel.getCorrectRate())));
            ((ExamPaperListViewHolder)holder).tv_right_rate.setText(homeModel.getCorrectRate());
            ((ExamPaperListViewHolder)holder).tv_error_number.setText(homeModel.getErrorQuestions()+"");
            ((ExamPaperListViewHolder)holder).tv_name.setText(homeModel.getExaminationName() + "");
            ((ExamPaperListViewHolder)holder).tv_done.setText(homeModel.getFinishedQuestions()+"");
            ((ExamPaperListViewHolder)holder).tv_total.setText(homeModel.getTotalQuestions()+"");
            
            
        }else if (holder instanceof FootViewHolder) {
            if (getItemCount()< Constants.PAGE_NUMBER){
                isShow=true;
            }
            if (isShow){
                ((FootViewHolder)holder).no_data.setVisibility(View.VISIBLE);
            }else{
                ((FootViewHolder)holder).no_data.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? (mFeedList.size()+1):0);
    }
   public interface ReclerViewItemClick{
        void onItemClickListenerPosition(int position);
    }
}
