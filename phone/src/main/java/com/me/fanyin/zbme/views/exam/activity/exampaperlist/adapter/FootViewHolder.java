package com.me.fanyin.zbme.views.exam.activity.exampaperlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.me.fanyin.zbme.R;


public class FootViewHolder extends RecyclerView.ViewHolder  {

    public final TextView no_data;
    private ReclerViewItemClick reclerViewItemClick;

    public FootViewHolder(View itemView) {
        super(itemView);
        no_data = (TextView) itemView.findViewById(R.id.no_data);
    }

}
