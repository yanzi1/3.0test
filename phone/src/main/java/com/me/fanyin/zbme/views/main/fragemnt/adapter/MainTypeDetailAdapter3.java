package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by wyc on 2017/03/23.
 * 考试动态、备考攻略
 */
public class MainTypeDetailAdapter3 extends BaseQuickAdapter<MainDetailItemBean, BaseViewHolder> {
	private Context context;
	private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
	private int mainPosition;

	public MainTypeDetailAdapter3() {
		super(R.layout.maintype_recycle_type3_item2);
	}

	public MainTypeDetailAdapter3(Context context, List<MainDetailItemBean> data, MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
		super(R.layout.maintype_recycle_type3_item2, data);
		this.context = context;
		this.mainTypeItemClick = mainTypeItemClick;
	}

	@Override
	protected void convert(final BaseViewHolder helper, final MainDetailItemBean item) {
		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(mainPosition, Constants.MAIN_FRAGMENT_CLICK_TYPE3, 0, helper.getPosition());
				}
			}
		});
		String date;
		if (item.getPublishDate().contains(" ")){
			date=item.getPublishDate().substring(0,item.getPublishDate().indexOf(" "));
		}else{
			date=item.getPublishDate();
		}
		helper.setText(R.id.maintype_item3_title, item.getTitle())
				.setText(R.id.maintype_type3_item2_time, date);
		String[] str=item.getTab().split(",");
		for (int i = 0; i < str.length; i++) {
			if (i==0){
				helper.setText(R.id.maintype_type3_item2_type1,str[i]);
				helper.setVisible(R.id.maintype_type3_item2_type2,false);
				helper.setVisible(R.id.maintype_type3_item2_type3,false);
			}else if(i==1){
				helper.setText(R.id.maintype_type3_item2_type2,str[i]);
				helper.setVisible(R.id.maintype_type3_item2_type2,true);
			}else{
				helper.setText(R.id.maintype_type3_item2_type3,str[i]);
				helper.setVisible(R.id.maintype_type3_item2_type3,true);
			}
		}
		if (helper.getPosition()==mData.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setClickListener(MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
		this.mainTypeItemClick = mainTypeItemClick;
	}

	public void setMainPosition(int mainPosition) {
		this.mainPosition = mainPosition;
	}
}
