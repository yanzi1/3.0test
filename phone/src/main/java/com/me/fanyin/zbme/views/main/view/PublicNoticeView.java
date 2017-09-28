package com.me.fanyin.zbme.views.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * @author wyc
 * @since  2017.03.27
 */
public class PublicNoticeView extends LinearLayout  {

	private static final String TAG = "PUBLICNOTICEVIEW";
	private Context mContext;
	private ViewFlipper mViewFlipper;
	private View mScrollTitleView;
	private NotiviceClick notiviceClick;

	public PublicNoticeView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public void setClickListener(NotiviceClick notiviceClick){
		this.notiviceClick=notiviceClick;
	}

	public PublicNoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		bindLinearLayout();
//		bindNotices();
	}

	/**
	 * 初始化自定义的布局
	 */
	private void bindLinearLayout() {
		mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.maintype_scrollnoticebar, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(mScrollTitleView, params);
		mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.id_scrollNoticeTitle);
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.main_type_slide_in_bottom));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.main_type_slide_out_top));
		mViewFlipper.startFlipping();
	}
	
	/**
	 * 网络请求内容后进行适配
	 */
	protected void bindNotices() {
		mViewFlipper.removeAllViews();
		int i = 0;
		while (i < 5) {
			String text = "公告:恭喜您中了500W,赶紧去领取吧!"+i;
			TextView textView = new TextView(mContext);
			textView.setText(text);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			mViewFlipper.addView(textView, layoutParams);
			i++;
		}
	}
	
	public void stopFlipping(){
		mViewFlipper.stopFlipping();
	}
	
	public void addNews(List<MainDetailItemBean> newsList){
		if(newsList.size()>0){
			mViewFlipper.removeAllViews() ;
			for (int i = 0; i < newsList.size(); i++) {
				TextView textView= (TextView) LayoutInflater.from(mContext).inflate(R.layout.maintype_recycle_type2_item,null);
				textView.setText(newsList.get(i).getTitle());
				textView.setId(i);
				final int finalI = i;
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (notiviceClick!=null){
							notiviceClick.clickItem(finalI);
						}
					}
				});
				LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				mViewFlipper.addView(textView, layoutParams);
			}
		}
	}
	
	public interface NotiviceClick{
		void clickItem(int position);
	}

}
