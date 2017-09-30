package com.me.fanyin.zbme.views.exam.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 答题卡适配器
 * @author wyc
 */
public class ScoreCardAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Question> mList;
	private int tag;
	private int index;
	private Handler handler;
	private List<Question> totalQuestion;

	public ScoreCardAdapter(Context context, ArrayList<Question> mList,int tag,Handler handler,int index,List<Question> totalQuestion) {
		this.mContext = context;
		this.mList = mList;
		this.tag = tag;
		this.handler = handler;
		this.index = index;
		this.totalQuestion=totalQuestion;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(mContext);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
		tv.setPadding(8, 8, 8, 8);

		/*tv.setText((position+1) + "");*/
		tv.setText((totalQuestion.indexOf(mList.get(position)) + 1) + "");
		tv.setTextColor(mContext.getResources().getColor(R.color.exam_test_question_font));
		if(tag == 2){
			if(mList.get(position).getUserAnswer() != null &&!"".equals(mList.get(position).getUserAnswer())){
				tv.setTextColor(mContext.getResources().getColor(android.R.color.white));
				if(mList.get(position).getUserAnswer().equals(mList.get(position).getRealAnswer())){
					tv.setBackgroundResource(R.drawable.exam_answercard_button_circle_finished_normal);
					tv.setTextColor(mContext.getResources().getColor(R.color.exam_test_question_score_scard_right_bg));
				}else{
					tv.setBackgroundResource(R.drawable.exam_answernote_botton_unright_normal);
					tv.setTextColor(mContext.getResources().getColor(R.color.errortest_color));
				}
			}else{
				tv.setBackgroundResource(R.drawable.exam_answecard_button_item_selector);
				tv.setTextColor(mContext.getResources().getColor(R.color.exam_test_question_font));
			}
		}else if(tag == 1 || tag == 0){
			if(mList.get(position).getUserAnswer() != null &&!"".equals(mList.get(position).getUserAnswer())){
				tv.setBackgroundResource(R.drawable.exam_answercard_button_circle_hover);
				tv.setTextColor(mContext.getResources().getColor(R.color.exam_test_question_font));
			}else{
				tv.setBackgroundResource(R.drawable.exam_answecard_button_item_selector);
				tv.setTextColor(mContext.getResources().getColor(R.color.exam_test_question_font));
			}
		}
		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Message message = handler.obtainMessage();
				message.what = 1;
				Bundle bundle=new Bundle();
				bundle.putInt("index",index);
				bundle.putInt("position",position);
				bundle.putInt("totalPosition",totalQuestion.indexOf(mList.get(position))+1);
				//message.arg1 = index;
				//message.arg2 = position;
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
		return tv;
	}

}
