package com.me.fanyin.zbme.views.exam.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.fragment.QuestionFragment;
import com.me.fanyin.zbme.views.exam.fragment.ScoreCardFragment;


/**
 * @Description: ViewPager的数据适配器
 * @author wyc
 */
public class QuestionItemAdapter extends FragmentStatePagerAdapter {
	Context context;
	private int tag = 0;
	private int exam_tag;
	private FragmentManager fm;
	public QuestionItemAdapter(FragmentManager fm,Context context) {
		super(fm);
		this.fm=fm;
		exam_tag = SharedPrefHelper.getInstance().getExamTag();
	}

	@Override
	public int getCount() {
		if(ExamPersenter.questionlist==null){
			return 0;
		}else{
			if(exam_tag == Constants.EXAM_TAG_REPORT ||exam_tag == Constants.EXAM_ORIGINAL_QUESTION){
				return ExamPersenter.questionlist.size();
			}else{
				return ExamPersenter.questionlist.size()+1;
			}
		}
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

// private SparseArray<WeakReference<Fragment>> mFragments = new SparseArray<>();
//
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		Fragment f = (Fragment) super.instantiateItem(container, position);
//		mFragments.put(position, new WeakReference<>(f));  // Remember what fragment was in position
//		return f;
//	}

//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
//		mFragments.remove(position);
//	}

	@Override
	public Fragment getItem(int position) {
		if ( ExamPersenter.questionlist==null|| ExamPersenter.questionlist.size()==0){
			return null;
		}
		Fragment f ;
			if(position == ExamPersenter.questionlist.size()){//做完了
				f = new ScoreCardFragment().newInstance(tag);
			}else {
				f = new QuestionFragment().newInstance(position); 
			}
		return f;
	}
}
