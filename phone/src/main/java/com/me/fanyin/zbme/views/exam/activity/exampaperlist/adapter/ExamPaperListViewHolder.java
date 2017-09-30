package com.me.fanyin.zbme.views.exam.activity.exampaperlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.fanyin.zbme.R;

public class ExamPaperListViewHolder extends RecyclerView.ViewHolder {

    public final TextView tv_name,tv_done,tv_total,tv_error_number,tv_right_rate;
    public final LinearLayout ll_have_done;
    private ReclerViewItemClick reclerViewItemClick;

    public ExamPaperListViewHolder(View itemView) {
        super(itemView);
        tv_name= (TextView) itemView.findViewById(R.id.tv_name);
        tv_done= (TextView) itemView.findViewById(R.id.tv_done);
        tv_total= (TextView) itemView.findViewById(R.id.tv_total);
        tv_error_number= (TextView) itemView.findViewById(R.id.tv_error_number);
        tv_right_rate= (TextView) itemView.findViewById(R.id.tv_right_rate);
        ll_have_done= (LinearLayout) itemView.findViewById(R.id.ll_have_done);
    }

}
