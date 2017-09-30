package com.me.fanyin.zbme.views.exam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.views.exam.fragment.QuestionCompreFragment;

import java.util.List;

/**
 * @author wyc
 * @Description: ViewPager的数据适配器
 */
public class ComprehensiveItemAdapterTest extends FragmentStatePagerAdapter {
    private List<Question> list;
    private int position;

    public ComprehensiveItemAdapterTest(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Question> list) {
		this.list=list;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int arg0) {
        this.position=arg0;

       return new QuestionCompreFragment().newInstance(list.get(arg0));
    }


    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
