package com.me.fanyin.zbme.widget.dropdown;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.fanyin.zbme.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mayunfei on 17-4-24.
 */

public abstract class ShopMenuAdapter<T> extends BaseAdapter {


    List<T> data;
    private int selectPosition;

    public ShopMenuAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else return 0;
    }

    @Override
    public T getItem(int position) {
        if (data != null && data.size() > 0) {
            return data.get(position);
        } else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mall_spinner_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        if (position == selectPosition){
            viewHolder.mText.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.color_primary));
        }else {
            viewHolder.mText.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.text_color_primary_dark));
        }

        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(showItemText(data.get(position)));
    }

    public void setSelect(int position){
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    /**
     * 显示选择
     */
    protected abstract String showItemText(T t);

    static class ViewHolder {
        @BindView(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
