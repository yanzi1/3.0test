package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.MainDetailBean;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.activity.divider.SpacesItemDecoration;
import com.me.fanyin.zbme.views.main.fragemnt.view.FullyGridLayoutManager;
import com.me.fanyin.zbme.views.main.view.PublicNoticeView;
import com.me.fanyin.zbme.widget.banner.Banner;
import com.me.fanyin.zbme.widget.banner.BannerConfig;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wyc on 2017/03/23.
 */
public class MainTypeScrollviewAdapter extends BaseMultiItemQuickAdapter<MainDetailBean, BaseViewHolder> {
	private SpacesItemDecoration spacesItemDecoration;
	private Context context;
	private List<MainDetailBean> data;
	private MainTypeAdapter.MainTypeItemClick mainTypeItemClick;
	private MainTypeDetailAdapter2 mainTypeDetailAdapter2;
	private MainTypeDetailAdapter3 mainTypeDetailAdapter3;
	private MainTypeDetailAdapter4 mainTypeDetailAdapter4;
	private MainTypeDetailAdapter5 mainTypeDetailAdapter5;
	private MainTypeDetailAdapter6 mainTypeDetailAdapter6;
	private MainTypeDetailAdapter7 mainTypeDetailAdapter7;
	private MainTypeDetailAdapter11 mainTypeDetailAdapter11;
	private LinearLayout ll_sv_content;

	public MainTypeScrollviewAdapter(Context context, List<MainDetailBean> data1, List<MainDetailBean> data) {
		super(data1);
		this.context = context;
		this.data = data;
		addTtemTypes(data);
		int leftRight = DensityUtils.dip2px(context, 15);
		int topBottom = DensityUtils.dip2px(context, 10);
		spacesItemDecoration = new SpacesItemDecoration(leftRight, topBottom);
	}

	public void notifyData() {
		notifyDataSetChanged();
		mainTypeDetailAdapter2.notifyDataSetChanged();
		mainTypeDetailAdapter3.notifyDataSetChanged();
		mainTypeDetailAdapter4.notifyDataSetChanged();
		mainTypeDetailAdapter5.notifyDataSetChanged();
		mainTypeDetailAdapter6.notifyDataSetChanged();
		mainTypeDetailAdapter7.notifyDataSetChanged();
		mainTypeDetailAdapter11.notifyDataSetChanged();
	}

	private void addTtemTypes(List<MainDetailBean> mainTypes) {
		if (data.size()<1)return;
		addItemType(mainTypes.get(0).getItemType(), R.layout.maintype_recycle_scroll);
	}

	public List<MainDetailBean> getDatum() {
		return data;
	}

	public void setDatum(List<MainDetailBean> data) {
		this.data = data;
	}

	private void addToLLTypes(List<MainDetailBean> mainTypes, LinearLayout ll_sv_content) {
		for (int i = 0; i < mainTypes.size(); i++) {
		addItemToScrollType(mainTypes.get(i).getItemType(), R.layout.maintype_recycle_type1,i);
//			switch (mainTypes.get(i).getItemType()) {
//				case MainTypeAdapter.TypeEntity.TYPE_1:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_1, R.layout.maintype_recycle_type1,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_2:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_2, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_3:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_3, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_4:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_4, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_5:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_5, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_6:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_6, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_7:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_7, R.layout.maintype_recycle_type3,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_8:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_8, R.layout.maintype_recycle_type4,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_9:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_9, R.layout.maintype_recycle_type5,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_10:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_10, R.layout.maintype_recycle_type2,i);
//					break;
//				case MainTypeAdapter.TypeEntity.TYPE_11:
//					addItemToScrollType(MainTypeAdapter.TypeEntity.TYPE_11, R.layout.maintype_recycle_type3,i);
//					break;
//				default:
//					addItemToScrollType(mainTypes.get(i).getItemType(), R.layout.maintype_recycle_type2,i);
//			}
		}
	}

	private void addItemToScrollType(int type1, int maintype_recycle_type1,int mainPosition) {
		MainDetailBean detailBean = data.get(mainPosition);
		View v;
		switch (detailBean.getMouldCode()) {
			case MainTypeAdapter.TypeEntity.TYPE_1:
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type1,null,false);
				convertType1(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_2://3
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType2(v, detailBean, mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_3://4
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType3(v,detailBean, mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_4://6
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType4(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_5://7
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType5(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_6://8
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType6(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_7://9
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType7(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_8://10
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type4,null,false);
				convertType8(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_9://5
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type5,null,false);
				convertType9(v,detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_10://2
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type2,null,false);
				convertType10(v, detailBean,mainPosition);
				break;
			case MainTypeAdapter.TypeEntity.TYPE_11://11
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type3,null,false);
				convertType11(v,detailBean, mainPosition);
				break;
			default:
				v=LayoutInflater.from(context).inflate(R.layout.maintype_recycle_type2,null,false);
				convertType10(v, detailBean,mainPosition);
				break;
		}
		ll_sv_content.addView(v);
	}



	public static final int TYPE_MAIN = 0xffff;
	

	@Override
	protected void convert(BaseViewHolder helper, MainDetailBean item) {
		convertTypeScrollview(helper);
	}

	private void convertTypeScrollview(BaseViewHolder helper) {
		ll_sv_content = helper.getView(R.id.ll_sv_content);
		addToLLTypes(data, ll_sv_content);

	}

	private void convertType8(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);

		helper.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE8, 1, 0);
				}
			}
		});
		MainDetailItemBean mainDetailItemBean = detailBean.getList().get(0);
		String dayNumber = getNumbers(mainDetailItemBean.getDes());
		if (StringUtils.isEmpty(dayNumber)) {
			helper.setText(R.id.main_item4_rest, mainDetailItemBean.getDes());
			helper.setVisible(R.id.main_item4_rest_num, false);
			helper.setVisible(R.id.main_item4_rest_day, false);
		} else {
			helper.setVisible(R.id.main_item4_rest_num, true);
			helper.setVisible(R.id.main_item4_rest_day, true);
			helper.setText(R.id.main_item4_rest, mainDetailItemBean.getDes().substring(0, mainDetailItemBean.getDes().indexOf(dayNumber)));
			helper.setText(R.id.main_item4_rest_num, dayNumber);
		}
	}

	//截取数字  
	public String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	private void convertType9(View v, MainDetailBean detailBean,int mainPosition) {
		if (detailBean.getList().size() == 0) {
			return;
		}
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		helper.getView(R.id.main_type_item5_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE9, 1, 0);
				}
			}
		});

		Glide.with(context).
				load(detailBean.getList().get(0).getImage()).
				placeholder(R.mipmap.img_default_smallbanner).
				error(R.mipmap.img_default_smallbanner).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				fitCenter().
				into((ImageView) helper.getView(R.id.main_type_item5_iv));
	}

	private void convertType7(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);

		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE7, 1, 0);
				}
			});
		}

//		if (mainTypeDetailAdapter7 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter7 = new MainTypeDetailAdapter7(context, detailBean.getList(), mainTypeItemClick);
		recyclerView.addItemDecoration(spacesItemDecoration);
		mainTypeDetailAdapter7.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter7.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter7);
//		} else {
//			mainTypeDetailAdapter7.notifyDataSetChanged();
//		}
	}

	private void convertType6(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);

		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE6, 1, 0);
				}
			});
		}

//		if (mainTypeDetailAdapter6 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter6 = new MainTypeDetailAdapter6(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter6.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter6.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter6);
//		} else {
//			mainTypeDetailAdapter6.notifyDataSetChanged();
//		}
	}

	private void convertType5(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);

		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE5, 1, 0);
				}
			});
		}

//		if (mainTypeDetailAdapter5 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.addItemDecoration(spacesItemDecoration);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter5 = new MainTypeDetailAdapter5(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter5.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter5.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter5);
//		} else {
//			mainTypeDetailAdapter5.notifyDataSetChanged();
//		}
	}

	private void convertType4(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE4, 1, 0);
				}
			});
		}

//		if (mainTypeDetailAdapter4 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter4 = new MainTypeDetailAdapter4(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter4.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter4.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter4);
//		} else {
//			mainTypeDetailAdapter4.notifyDataSetChanged();
//		}
	}

	private void convertType3(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE3, 1, 0);
				}
			});
		}
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
//		if (mainTypeDetailAdapter3 == null) {
		mainTypeDetailAdapter3 = new MainTypeDetailAdapter3(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter3.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter3.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter3);
//		} else {
//			mainTypeDetailAdapter3.setMainPosition(helper.getPosition());
//			recyclerView.setAdapter(mainTypeDetailAdapter3);
//			mainTypeDetailAdapter3.notifyDataSetChanged();
//		}
	}

	private void convertType2(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE2, 1, 0);
				}
			});
		}
//		if (mainTypeDetailAdapter2 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter2 = new MainTypeDetailAdapter2(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter2.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter2.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter2);
//		} else {
//			mainTypeDetailAdapter2.notifyDataSetChanged();
//		}
	}

	private void convertType1(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		String img = "http://pic16.nipic.com/20110921/7247268_215811562102_2.jpg";
		String[] imgs = new String[detailBean.getList().size()];
		for (int i = 0; i < detailBean.getList().size(); i++) {
			if (StringUtils.isEmpty(detailBean.getList().get(i).getImage())) {
				imgs[i] = img;
			} else {
				imgs[i] = detailBean.getList().get(i).getImage();
			}
		}
//		if (imgs.length>1)return;
		Banner banner = (Banner) helper.getView(R.id.main_type1_banner);
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
		banner.setIndicatorGravity(BannerConfig.CENTER);
		banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
			@Override
			public void OnBannerClick(View view, int position) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE1, 0, position - 1);
				}
			}
		});
		banner.setImages(imgs);

	}

	private void convertType11(View v, MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		helper.setText(R.id.title_tv, detailBean.getForumName());
		if (detailBean.getShowMore() == 0) {
			helper.setVisible(R.id.maintype_item_more, false);
		} else {
			helper.setVisible(R.id.maintype_item_more, true);
			helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE11, 1, 0);
				}
			});
		}
//		if (mainTypeDetailAdapter11 == null) {
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(true);
		mainTypeDetailAdapter11 = new MainTypeDetailAdapter11(context, detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter11.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter11.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter11);
//		} else {
//			mainTypeDetailAdapter11.notifyDataSetChanged();
//		}
	}

	private void convertType10(View v,MainDetailBean detailBean,int mainPosition) {
		final HelperUtil helper=new HelperUtil(v,mainPosition);
		PublicNoticeView publicNoticeView = (PublicNoticeView) helper.getView(R.id.main_type2_pnv);
		publicNoticeView.setClickListener(new PublicNoticeView.NotiviceClick() {
			@Override
			public void clickItem(int position) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE10, 1, position);
				}
			}
		});
		if (detailBean.getList().size() > 0) {
			publicNoticeView.addNews(detailBean.getList());
		} else {
			publicNoticeView.setVisibility(View.GONE);
		}
	}

	public void setClickListener(MainTypeAdapter.MainTypeItemClick mainTypeItemClick) {
		this.mainTypeItemClick = mainTypeItemClick;
	}
	
	public class HelperUtil{
		private View v;
		private int mainPosiiton;
		HelperUtil(View v,int mainPosition){
			this.v=v;
			this.mainPosiiton=mainPosition;
		}

		public View getView(int main_type2_pnv) {
			return  v.findViewById(main_type2_pnv);
		}

		public int getPosition() {
			return mainPosiiton;
		}

		public void setText(int title_tv, String forumName) {
			((TextView)getView(title_tv)).setText(forumName);
		}

		public void setVisible(int maintype_item_more, boolean b) {
			if (b){
				getView(maintype_item_more).setVisibility(View.VISIBLE);
			}else{
				getView(maintype_item_more).setVisibility(View.GONE);
			}
		}

		public View getConvertView() {
			return v;
		}
	}
}
