package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.fragemnt.view.FreeCourseImageView;

import java.util.List;

/**
 * Created by wyc on 2017/03/23.
 */
public class MainType4Adapter extends BaseMultiItemQuickAdapter<MainDetailItemBean, BaseViewHolder> {
	private Context context;
	private List<MainDetailItemBean> data;
	private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
	private int mainPosition;
	

	public MainType4Adapter(Context context, List<MainDetailItemBean> data) {
		super(data);
		this.context = context;
		this.data = data;
		addTtemTypes(data);
	}
	
	public void setListener(MainTypeAdapter.MainTypeItemClick mainTypeItemClick){
		this.mainTypeItemClick=mainTypeItemClick;
	}
	
	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param mainTypes A new list is created out of this one to avoid mutable list
	 */

	public MainType4Adapter(Context context, List<TypeEntity> mainTypes, List<MainDetailItemBean> data) {
		super(data);
		this.context = context;
		this.data = data;
		addTtemTypes(data);
	}
	
	public void setMainPosition(int mainPosition){
		this.mainPosition=mainPosition;
	}

	private void addTtemTypes(List<MainDetailItemBean> mainTypes) {
		for (int i = 0; i < mainTypes.size(); i++) {
			switch (mainTypes.get(i).getItemType()) {
				case TypeEntity.TYPE_2:
					addItemType(TypeEntity.TYPE_2, R.layout.maintype_recycle_type3_item1);
					break;
				case TypeEntity.TYPE_3:
					addItemType(TypeEntity.TYPE_3, R.layout.maintype_recycle_type3_item2);
					break;
				case TypeEntity.TYPE_4:
					addItemType(TypeEntity.TYPE_4, R.layout.maintype_recycle_type3_item3);
					break;
				case TypeEntity.TYPE_5:
					addItemType(TypeEntity.TYPE_5, R.layout.maintype_recycle_type3_item4);
					break;
				case TypeEntity.TYPE_6:
					addItemType(TypeEntity.TYPE_6, R.layout.maintype_recycle_type3_item5);
					break;
				case TypeEntity.TYPE_7:
					addItemType(TypeEntity.TYPE_7, R.layout.maintype_recycle_type3_item6);
					break;
				case TypeEntity.TYPE_11:
					addItemType(TypeEntity.TYPE_11, R.layout.maintype_recycle_type3_item11);
					break;
				default:
					addItemType(TypeEntity.TYPE_2, R.layout.maintype_recycle_type3_item1);
					break;
			}
		}
	}

	public List<MainDetailItemBean> getDatum() {
		return data;
	}

	public void setDatum(List<MainDetailItemBean> data) {
		this.data = data;
	}

	public static final int TYPE_MAIN = 0xffff;

	public static class TypeEntity implements MultiItemEntity {
		public static final int TYPE_1 = 1;
		public static final int TYPE_2 = 3;
		public static final int TYPE_3 = 4;
		public static final int TYPE_4 = 6;
		public static final int TYPE_5 = 7;
		public static final int TYPE_6 = 8;
		public static final int TYPE_7 = 9;
		public static final int TYPE_8 = 10;
		public static final int TYPE_9 = 5;
		public static final int TYPE_10 = 2;
		public static final int TYPE_11 = 11;
		private final int type;

		public TypeEntity(final int type) {
			this.type = type;
		}

		@Override
		public int getItemType() {
			return type;
		}
	}

	@Override
	protected void convert(BaseViewHolder helper, MainDetailItemBean item) {
		MainDetailItemBean detailBean = data.get(helper.getLayoutPosition());
		switch (detailBean.getMouldCode()) {
			case TypeEntity.TYPE_2://3
				convertType2(helper, detailBean);
				break;
			case TypeEntity.TYPE_3://4
				convertType3(helper, detailBean);
				break;
			case TypeEntity.TYPE_4://6
				convertType4(helper, detailBean);
				break;
			case TypeEntity.TYPE_5://7
				convertType5(helper, detailBean);
				break;
			case TypeEntity.TYPE_6://8
				convertType6(helper, detailBean);
				break;
			case TypeEntity.TYPE_7://9
				convertType7(helper, detailBean);
				break;
			case TypeEntity.TYPE_11://11
				convertType11(helper, detailBean);
				break;
			default:
				convertType2(helper, detailBean);
				break;
		}
	}

	private void convertType7(final BaseViewHolder helper, MainDetailItemBean detailBean) {

		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick!=null){
					mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE7,0,helper.getPosition());
				}
			}
		});
		Glide.with(context).load(detailBean.getImage()).
				placeholder(R.mipmap.img_default_course_book).
				error(R.mipmap.img_default_course_book).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				fitCenter().
				into((ImageView) helper.getView(R.id.maintype_type3_item6_iv));
		helper.setText(R.id.maintype_type3_item6_bookname_tv, detailBean.getTitle());
		helper.setText(R.id.maintype_type3_item6_price_tv, "¥" + detailBean.getTab());
	}


	private void convertType6(final BaseViewHolder helper, MainDetailItemBean detailBean) {
		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick!=null){
					mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE6,0,helper.getPosition());
				}
			}
		});
		Glide.with(context).load(detailBean.getImage()).
				placeholder(R.mipmap.img_default_course_book).
				error(R.mipmap.img_default_course_book).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				fitCenter().
				into((ImageView) helper.getView(R.id.maintype_type3_item5_iv));
		helper.setText(R.id.maintype_type3_item5_coursename, detailBean.getTitle());
		helper.setText(R.id.maintype_type3_item5_original_price_tv, "￥"+detailBean.getTab());
		if (helper.getPosition()==data.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

	private void convertType5(final BaseViewHolder helper, MainDetailItemBean detailBean) {

		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick!=null){
					mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE5,0,helper.getPosition());
				}
			}
		});
		Glide.with(context).load(detailBean.getImage()).
				placeholder(R.mipmap.img_default_freecourse).
				error(R.mipmap.img_default_freecourse).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				fitCenter().
				into((FreeCourseImageView) helper.getView(R.id.maintype_type3_item4_iv));
		helper.setText(R.id.maintype_type3_item4_coursename, detailBean.getTitle());
		helper.setText(R.id.maintype_type3_item4_zhujiang, detailBean.getAuthor());
		if (StringUtils.isEmpty(detailBean.getTab())){
			helper.setVisible(R.id.maintype_type3_item4_type, false);
		}else{
			helper.setText(R.id.maintype_type3_item4_type, detailBean.getTab());
			helper.setVisible(R.id.maintype_type3_item4_type, true);
		}
	}

	private void convertType4(final BaseViewHolder helper, MainDetailItemBean detailBean) {
		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null){
					mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE4,0,helper.getPosition());
				}
			}
		});
		Glide.with(context).load(detailBean.getImage()).
				placeholder(R.mipmap.img_default_course_book).
				error(R.mipmap.img_default_course_book).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				fitCenter().
				into((ImageView) helper.getView(R.id.maintype_type3_item3_iv));
		helper.setText(R.id.maintype_type3_item3_teachername, detailBean.getTitle());
		helper.setText(R.id.maintype_type3_item3_pro, detailBean.getDes());
		if (helper.getPosition()==data.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

	private void convertType3(final BaseViewHolder helper, MainDetailItemBean detailBean) {

		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(mainPosition, Constants.MAIN_FRAGMENT_CLICK_TYPE3, 0, helper.getPosition());
				}
			}
		});
		String date=detailBean.getPublishDate().substring(0,detailBean.getPublishDate().indexOf(" "));
		helper.setText(R.id.maintype_item3_title, detailBean.getTitle())
				.setText(R.id.maintype_type3_item2_time, date);
		String[] str=detailBean.getTab().split(",");
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
		if (helper.getPosition()==data.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

	private void convertType2(final BaseViewHolder helper, MainDetailItemBean detailBean) {

		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick!=null){
					mainTypeItemClick.itemClick(mainPosition,Constants.MAIN_FRAGMENT_CLICK_TYPE2, 0, helper.getPosition());
				}
			}
		});
		Glide.with(context).load(detailBean.getImage()).
				placeholder(R.mipmap.img_default_course_book).
				error(R.mipmap.img_default_course_book)
				.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
				.fitCenter()
				.into((ImageView) helper.getView(R.id.maintype_type3_item1_iv));
		helper.setText(R.id.maintype_type3_item1_title, detailBean.getTitle());
		helper.setText(R.id.maintype_type3_item1_type, detailBean.getTab());
		String date=detailBean.getPublishDate().substring(0,detailBean.getPublishDate().indexOf(" "));
		helper.setText(R.id.maintype_type3_item1_time, date);
		if (helper.getPosition()==data.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

	private void convertType11(final BaseViewHolder helper, MainDetailItemBean detailBean) {
		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(mainPosition, Constants.MAIN_FRAGMENT_CLICK_TYPE11, 0, helper.getPosition());
				}
			}
		});
		String date=detailBean.getPublishDate().substring(0,detailBean.getPublishDate().indexOf(" "));
		helper.setText(R.id.maintype_item3_title, detailBean.getTitle())
				.setText(R.id.maintype_type3_item2_time, date);
		String subTitle=detailBean.getTab().replaceAll(",","/");
		helper.setText(R.id.maintype_type3_item2_type1,subTitle);
		if (helper.getPosition()==data.size()-1){
			helper.setVisible(R.id.app_line,false);
		}else{
			helper.setVisible(R.id.app_line,true);
		}
	}

}
