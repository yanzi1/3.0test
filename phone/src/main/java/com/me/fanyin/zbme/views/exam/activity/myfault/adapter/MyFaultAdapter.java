package com.me.fanyin.zbme.views.exam.activity.myfault.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.me.fanyin.zbme.views.exam.activity.myfault.MyFaultFragment;
import com.me.fanyin.zbme.views.exam.activity.myfault.bean.FaultClass;
import java.util.List;

public class MyFaultAdapter extends FragmentPagerAdapter {

	//private final String[] TITLES = { "初级会计实务","经济法基础" };
	private List<FaultClass> subjects;

	public MyFaultAdapter(FragmentManager fm) {
		super(fm);

	}

	public void setFaultClasss(List<FaultClass> subjects){
		this.subjects = subjects;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return subjects.get(position).getClassName();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return subjects.size();
	}

	@Override
	public Fragment getItem(int position) {
		return new MyFaultFragment().newInstance(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}