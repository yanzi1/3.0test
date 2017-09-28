package com.me.fanyin.zbme.views.main.fragemnt.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
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

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wyc on 2017/03/23.
 */
public class MainTypeAdapter extends BaseMultiItemQuickAdapter<MainDetailBean, BaseViewHolder> {
	private LinearLayout.LayoutParams flLayoutParams;
	private SpacesItemDecoration spacesItemDecoration;
	private Context context;
	private MainTypeItemClick mainTypeItemClick;
	private HashMap<RecyclerView, Boolean> decorationMap;
	private Fragment fragment;

	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param mainTypes A new list is created out of this one to avoid mutable list
	 */


	public MainTypeAdapter(Context context, List<TypeEntity> mainTypes, List<MainDetailBean> data) {
		super(data);
		this.context = context;
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int bannerWidth = displayMetrics.widthPixels;
		int bannerHeight = bannerWidth / 13 * 5;
		flLayoutParams = new LinearLayout.LayoutParams(bannerWidth, bannerHeight);
		addTtemTypes(data);
		int leftRight = DensityUtils.dip2px(context, 15);
		int topBottom = DensityUtils.dip2px(context, 10);
		spacesItemDecoration = new SpacesItemDecoration(leftRight, topBottom);
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public void notifyData() {
		notifyDataSetChanged();
	}

	private void addTtemTypes(List<MainDetailBean> mainTypes) {
		addItemType(TypeEntity.TYPE_1, R.layout.maintype_recycle_type1);
		addItemType(TypeEntity.TYPE_2, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_3, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_4, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_5, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_6, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_7, R.layout.maintype_recycle_type3);
		addItemType(TypeEntity.TYPE_8, R.layout.maintype_recycle_type4);
		addItemType(TypeEntity.TYPE_9, R.layout.maintype_recycle_type5);
		addItemType(TypeEntity.TYPE_10, R.layout.maintype_recycle_type2);
		addItemType(TypeEntity.TYPE_11, R.layout.maintype_recycle_type3);
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
	protected void convert(BaseViewHolder helper, MainDetailBean item) {
		MainDetailBean detailBean = mData.get(helper.getLayoutPosition());
		switch (detailBean.getItemType()) {
			case TypeEntity.TYPE_1:
				convertType1(helper, detailBean);
				break;
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
			case TypeEntity.TYPE_8://10
				convertType8(helper, detailBean);
				break;
			case TypeEntity.TYPE_9://5
				convertType9(helper, detailBean);
				break;
			case TypeEntity.TYPE_10://2
				convertType10(helper, detailBean);
				break;
			case TypeEntity.TYPE_11://11
				convertType11(helper, detailBean);
				break;
			default:

				break;
		}
	}

	private void convertType8(final BaseViewHolder helper, MainDetailBean detailBean) {
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

	private void convertType9(final BaseViewHolder helper, MainDetailBean detailBean) {
		if (detailBean.getList().size() == 0) {
			return;
		}
		helper.getView(R.id.main_type_item5_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mainTypeItemClick != null) {
					mainTypeItemClick.itemClick(helper.getPosition(), Constants.MAIN_FRAGMENT_CLICK_TYPE9, 1, 0);
				}
			}
		});
		if (fragment != null) {
			Glide.with(fragment).
					load(detailBean.getList().get(0).getImage()).
					placeholder(R.mipmap.img_default_smallbanner).
					error(R.mipmap.img_default_smallbanner).
					diskCacheStrategy(DiskCacheStrategy.ALL).
					fitCenter().
					into((ImageView) helper.getView(R.id.main_type_item5_iv));
		} else {
			Glide.with(context).
					load(detailBean.getList().get(0).getImage()).
					placeholder(R.mipmap.img_default_smallbanner).
					error(R.mipmap.img_default_smallbanner).
					diskCacheStrategy(DiskCacheStrategy.ALL).
					fitCenter().
					into((ImageView) helper.getView(R.id.main_type_item5_iv));
		}
	}

	private void convertType7(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		if (decorationMap == null) decorationMap = new HashMap<RecyclerView, Boolean>();
		if (!decorationMap.containsKey(recyclerView)) {
			decorationMap.put(recyclerView, true);
			recyclerView.addItemDecoration(spacesItemDecoration);
		}
		MainTypeDetailAdapter7 mainTypeDetailAdapter7 = null;
		if (recyclerView.getTag() == null) {
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter7 = new MainTypeDetailAdapter7(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter7.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter7.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter7);
			recyclerView.setTag(mainTypeDetailAdapter7);
		} else {
			mainTypeDetailAdapter7 = (MainTypeDetailAdapter7) recyclerView.getTag();
			mainTypeDetailAdapter7.setNewData(detailBean.getList());
			mainTypeDetailAdapter7.notifyDataSetChanged();
		}
	}

	private void convertType6(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		MainTypeDetailAdapter6 mainTypeDetailAdapter6 = null;
		if (getRecyclerView().getTag() == null) {
			RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter6 = new MainTypeDetailAdapter6(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter6.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter6.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter6);
			recyclerView.setTag(mainTypeDetailAdapter6);
		} else {
			mainTypeDetailAdapter6 = (MainTypeDetailAdapter6) getRecyclerView().getTag();
			mainTypeDetailAdapter6.setNewData(detailBean.getList());
			mainTypeDetailAdapter6.notifyDataSetChanged();
		}
	}

	private void convertType5(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		if (decorationMap == null) decorationMap = new HashMap<RecyclerView, Boolean>();
		if (!decorationMap.containsKey(recyclerView)) {
			decorationMap.put(recyclerView, true);
			recyclerView.addItemDecoration(spacesItemDecoration);
		}
		MainTypeDetailAdapter5 mainTypeDetailAdapter5 = null;
		if (recyclerView.getTag() == null) {
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter5 = new MainTypeDetailAdapter5(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter5.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter5.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter5);
			recyclerView.setTag(mainTypeDetailAdapter5);
		} else {
			mainTypeDetailAdapter5 = (MainTypeDetailAdapter5) recyclerView.getTag();
			mainTypeDetailAdapter5.setNewData(detailBean.getList());
			mainTypeDetailAdapter5.notifyDataSetChanged();
		}
	}

	private void convertType4(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		MainTypeDetailAdapter4 mainTypeDetailAdapter4 = null;
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		if (recyclerView.getTag() == null) {
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter4 = new MainTypeDetailAdapter4(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter4.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter4.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter4);
			recyclerView.setTag(mainTypeDetailAdapter4);
		} else {
			mainTypeDetailAdapter4 = (MainTypeDetailAdapter4) recyclerView.getTag();
			mainTypeDetailAdapter4.setNewData(detailBean.getList());
			mainTypeDetailAdapter4.notifyDataSetChanged();
		}
	}

	private void convertType3(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		MainTypeDetailAdapter3 mainTypeDetailAdapter3 = null;
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		if (recyclerView.getTag() == null) {
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter3 = new MainTypeDetailAdapter3(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter3.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter3.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter3);
			recyclerView.setTag(mainTypeDetailAdapter3);
		} else {
			mainTypeDetailAdapter3 = (MainTypeDetailAdapter3) recyclerView.getTag();
			mainTypeDetailAdapter3.setNewData(detailBean.getList());
			mainTypeDetailAdapter3.notifyDataSetChanged();
		}
	}

	private void convertType2(final BaseViewHolder helper, MainDetailBean detailBean) {

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
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		MainTypeDetailAdapter2 mainTypeDetailAdapter2 = null;
		if (recyclerView.getTag() == null) {
			recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
			recyclerView.setNestedScrollingEnabled(false);
			mainTypeDetailAdapter2 = new MainTypeDetailAdapter2(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
			mainTypeDetailAdapter2.setClickListener(mainTypeItemClick);
			mainTypeDetailAdapter2.setMainPosition(helper.getPosition());
			recyclerView.setAdapter(mainTypeDetailAdapter2);
			recyclerView.setTag(mainTypeDetailAdapter2);
		} else {
			mainTypeDetailAdapter2 = (MainTypeDetailAdapter2) recyclerView.getTag();
			mainTypeDetailAdapter2.setNewData(detailBean.getList());
			mainTypeDetailAdapter2.notifyDataSetChanged();
		}
	}

	private void convertType1(final BaseViewHolder helper, MainDetailBean detailBean) {
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
		banner.setLayoutParams(flLayoutParams);
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

	private void convertType11(final BaseViewHolder helper, MainDetailBean detailBean) {
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
		RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.main_type3_recyc);
		MainTypeDetailAdapter11 mainTypeDetailAdapter11=null;
		if (recyclerView.getTag() == null) {
		recyclerView.setLayoutManager(new FullyGridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
		recyclerView.setNestedScrollingEnabled(false);
		mainTypeDetailAdapter11 = new MainTypeDetailAdapter11(recyclerView.getContext(), detailBean.getList(), mainTypeItemClick);
		mainTypeDetailAdapter11.setClickListener(mainTypeItemClick);
		mainTypeDetailAdapter11.setMainPosition(helper.getPosition());
		recyclerView.setAdapter(mainTypeDetailAdapter11);
		recyclerView.setTag(mainTypeDetailAdapter11);
		} else {
			mainTypeDetailAdapter11= (MainTypeDetailAdapter11) recyclerView.getTag();
			mainTypeDetailAdapter11.setNewData( detailBean.getList());
			mainTypeDetailAdapter11.notifyDataSetChanged();
		}
	}

	private void convertType10(final BaseViewHolder helper, MainDetailBean detailBean) {
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
			if (detailBean.getList().size() == 1) {
				publicNoticeView.stopFlipping();
			}
		} else {
			publicNoticeView.setVisibility(View.GONE);
		}
	}

	public interface MainTypeItemClick {
		void itemClick(int mainPosition, int itemType, int type, int position);
	}

	public void setClickListener(MainTypeItemClick mainTypeItemClick) {
		this.mainTypeItemClick = mainTypeItemClick;
	}
}
