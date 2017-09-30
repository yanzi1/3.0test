package com.me.fanyin.zbme.views.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.data.model.exam.RelevantPoint;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * @Description: 选项适配器
 * @author xunice
 */
public class RelevantPointAdapter extends BaseAdapter {
	private Context mContext;
	public List<RelevantPoint> options ;

	public RelevantPointAdapter(Context context, List<RelevantPoint> options) {
		this.mContext = context;
		this.options = options;
	}

	public int getCount() {
		return options.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		 
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.exam_test_item_relevanpoint, null);
			viewHolder.option = (TextView) convertView.findViewById(R.id.tv_relevant_option);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.option.setText(options.get(position).getkPointTitle());
		return convertView;
	 
	}

	class ViewHolder {
		TextView option;// 已学完/播放中
	}

}
