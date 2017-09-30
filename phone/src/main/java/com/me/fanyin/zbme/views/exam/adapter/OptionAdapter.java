package com.me.fanyin.zbme.views.exam.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.me.data.model.exam.Option;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.view.XWebView;

import java.util.List;

public class OptionAdapter extends BaseAdapter {
	private Context mContext;
	ListView lv ;
	int index;
	public List<Option> options ;
	private Question question;
	public String[] optionChoice={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public String[] optionJudge={"1","2"};
	private ColorStateList cl;
	private XmlResourceParser xrp;
	public heightListener listener;
	private int gao;//计算高度
	private int typeList[]={0,1};

	public OptionAdapter(Context context, List<Option> options, ListView lv, int index, Question question,heightListener listener) {
		this.mContext = context;
		this.options = options;
		this.lv = lv;
		this.question = question;
		this.listener=listener;
	}
	public OptionAdapter(Context context, List<Option> options, ListView lv, int index, Question question) {
		this.mContext = context;
		this.options = options;
		this.lv = lv;
		this.question = question;
	}

	public int getCount() {
		return options.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public int getViewTypeCount() {
		return typeList.length;
	}

	@Override
	public int getItemViewType(int position) {
		boolean isShowWebView=false;
		if(question.getOptionList()!=null&&question.getOptionList().size()!=0){
			for (int i = 0; i < question.getOptionList().size(); i++) {
				if (question.getOptionList().get(i).getShowWebView()!=null&&question.getOptionList().get(i).getShowWebView().equals("1")){
					isShowWebView=true;
				}
			}
		}
		if (isShowWebView){
			return	typeList[1];
		}else{
			return	typeList[0];
		}
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		int itemType=getItemViewType(position);
		ViewHolderTextView viewHolderTextView = null;
		ViewHolderWebView viewHolderWebView = null;
		if (convertView==null){
			switch (itemType){
				case 0:
					viewHolderTextView = new ViewHolderTextView();
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.exam_test_item_option_htmltextview, null);
					viewHolderTextView.ctv = (CheckedTextView) convertView.findViewById(R.id.ctv_name);
					viewHolderTextView.option = (TextView) convertView.findViewById(R.id.tv_option);
					viewHolderTextView.option.setFocusable(false);
					convertView.setTag(viewHolderTextView);
					break;
				case 1:
					
					viewHolderWebView = new ViewHolderWebView();
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.exam_test_item_option, null);
					viewHolderWebView.ctv = (CheckedTextView) convertView.findViewById(R.id.ctv_name);
					viewHolderWebView.wv_option=(XWebView) convertView.findViewById(R.id.wv_option);
					viewHolderWebView.wv_option.getSettings().setAppCacheEnabled(false);
					viewHolderWebView.wv_option.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
					viewHolderWebView.wv_option.getSettings().setDefaultTextEncodingName("UTF-8");
					viewHolderWebView.wv_option.getSettings().setJavaScriptEnabled(true);
					viewHolderWebView.wv_option.getSettings().setLayoutAlgorithm(
							WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
					viewHolderWebView.wv_option.setHorizontalScrollBarEnabled(false);// 取消Horizontal
					viewHolderWebView.wv_option.setFocusable(false);

					viewHolderWebView.wv_option.getSettings().setBlockNetworkImage(true);//解决图片不显示
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						viewHolderWebView.wv_option.getSettings().setMixedContentMode(
								WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
					}
					convertView.setTag(viewHolderWebView);
					break;
			}
		}else{
			switch (itemType){
				case 0:
					viewHolderTextView = (ViewHolderTextView) convertView.getTag();
					break;
				case 1:
					viewHolderWebView = (ViewHolderWebView) convertView.getTag();
					break;
			}
		}

		switch (itemType){
			case 0:
				viewHolderTextView.ctv.setText(""+optionChoice[position]);
				viewHolderTextView.option.setText(Html.fromHtml("<font color='#808080' style='font-size:18px;'>" + (question.getOptionList().get(position).getOptionContent()) + "</font>"));
				updateBackground(position, viewHolderTextView.ctv);
				break;
			case 1:
				viewHolderWebView.ctv.setText(" " + optionChoice[position]);
				viewHolderWebView.wv_option.loadDataWithBaseURL("", "<font color='#808080' style='font-size:18px;'>" + (question.getOptionList().get(position).getOptionContent()) + "</font>", "text/html", "utf-8", "");
				final ViewHolderWebView finalViewHolder = viewHolderWebView;
				viewHolderWebView.wv_option.setDf(new XWebView.PlayFinish() {
					@Override
					public void After() {
						int h = finalViewHolder.wv_option.getHeight();
						listener.onheightChange(h,position);
					}
				});
				updateBackground(position, viewHolderWebView.ctv);
				break;
		}
		return convertView;
	}

	static class ViewHolderTextView {
		CheckedTextView ctv;//
		TextView option;
	}

	static class ViewHolderWebView {
		CheckedTextView ctv;//
		XWebView wv_option;//选项内容，带特殊符号的
	}

	static class ViewHolder {
		CheckedTextView ctv;//
		XWebView wv_option;//选项内容，带特殊符号的
		TextView option;
	}

	public interface heightListener{
		public void onheightChange(int height, int position);
	};

	public void updateBackground(int position, CheckedTextView view) {
		int textcolorId=0;
		int backgroundId = 0;
		if(question.getChoiceType()== ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()){ //单选
			if (lv.isItemChecked(position) || (question.getUserAnswer()!=null && question.getUserAnswer().contains(optionChoice[position]))) {
				backgroundId = R.drawable.exam_button_cir_selece_hover;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_normal);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}
			} else {
				backgroundId = R.drawable.exam_btn_option_single;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text);
					  cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}

			}
		}else if(question.getChoiceType()==ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId()  ){ //多选 不定项
			if (lv.isItemChecked(position)|| (question.getUserAnswer()!=null &&question.getUserAnswer().contains(optionChoice[position]))) {
				backgroundId = R.drawable.exam_button_square_hover;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_normal);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}
			} else {
				backgroundId = R.drawable.exam_button_square_normal;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_black);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}

			}
		}else if(question.getChoiceType()==ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId() ){ //多选 不定项
			if (lv.isItemChecked(position)|| (question.getUserAnswer()!=null && question.getUserAnswer().contains(optionChoice[position]))) {
				backgroundId = R.drawable.exam_button_square_hover;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_normal);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}
			} else {
				backgroundId = R.drawable.exam_button_square_normal;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_black);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}

			}
		}else if(question.getChoiceType()== ExamTypeEnum.EXAM_TYPE_TIMAOTI.getId() ){ //多选 不定项
			if (lv.isItemChecked(position)|| (question.getUserAnswer()!=null && question.getUserAnswer().contains(optionChoice[position]))) {
				backgroundId = R.drawable.exam_button_square_hover;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_normal);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}
			} else {
				backgroundId = R.drawable.exam_button_square_normal;
				try {
					xrp =view. getResources().getXml(R.color.exam_btn_option_text_black);
					cl = ColorStateList.createFromXml(view. getResources(), xrp);
				} catch (Exception ex) {}

			}
		}else if(question.getChoiceType()==ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()){ //判断
			view.setText("");
			if(position == 0){ //只有判断题才这样处理
				if (lv.isItemChecked(position)  || (question.getUserAnswer()!=null && question.getUserAnswer().contains(optionJudge[position]))) {
					backgroundId = R.drawable.exam_button_judge_right_hover;
					//textcolorId=R.color.white;
				} else {
					backgroundId = R.drawable.exam_btn_option_judge_right;
					try {
						xrp =view. getResources().getXml(R.color.exam_btn_option_text);
						cl = ColorStateList.createFromXml(view. getResources(), xrp);
					} catch (Exception ex) {}
				}
			}else{

				if (lv.isItemChecked(position) || (question.getUserAnswer()!=null && question.getUserAnswer().contains(optionJudge[position]))) {
					backgroundId = R.drawable.exam_button_judge_unright_hover;
					//textcolorId=R.color.white;
				} else {
					backgroundId = R.drawable.exam_btn_option_judge;
				}
			}
		}

		view.setBackgroundResource(backgroundId);
		if(cl != null){
			view.setTextColor(cl);
		}/*else{
			view.setTextColor(view.getResources().getColor(textcolorId));
		}*/
	}

}
