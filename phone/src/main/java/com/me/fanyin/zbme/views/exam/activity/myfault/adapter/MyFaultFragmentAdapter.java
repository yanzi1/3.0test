package com.me.fanyin.zbme.views.exam.activity.myfault.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.myfault.bean.ExaminationClass;

import java.util.HashMap;
import java.util.List;

public class MyFaultFragmentAdapter extends BaseAdapter {

	private Context context;
	private List<ExaminationClass> mlist;
	private HashMap<Integer,Integer> mMap;

	public MyFaultFragmentAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setList(List<ExaminationClass> mlist) {
		this.mlist = mlist;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.exam_myfault_fragment_item, null);
			viewHolder.push_item_title = (TextView) convertView.findViewById(R.id.push_item_title);
            viewHolder.push_item_date = (TextView) convertView.findViewById(R.id.push_item_date);
			viewHolder.push_fragment_item_tubiao_iv = (ImageView) convertView.findViewById(R.id.push_fragment_item_tubiao_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ExaminationClass mainItem = mlist.get(position);
		viewHolder.push_item_title.setText(mlist.get(position).getExaminationName());
		viewHolder.push_item_date.setText(mlist.get(position).getNumber()+"题");
		/*if (!mainItem.getCreateTime().isEmpty()){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(mainItem.getCreateTime());
//			viewHolder.push_item_date.setText(dateString);
		}*/
		//以下设置为了测试
		return convertView;
	}

	class ViewHolder {
		TextView push_item_title,push_item_date;
		ImageView push_fragment_item_tubiao_iv;
	}
}
