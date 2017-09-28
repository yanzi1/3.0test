package com.me.fanyin.zbme.views.course.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.me.fanyin.zbme.R;

/**
 * Created by dell on 2017/4/25.
 */

public class RecordAdapter extends BaseAdapter{

    private Context context;

    public RecordAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View content =View.inflate(context, R.layout.record_item,null);

        return content;
    }
}
