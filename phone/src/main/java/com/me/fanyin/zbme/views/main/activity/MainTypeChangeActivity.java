package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.StringUtils;
import com.me.data.common.Constants;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.ItemDragAdapter;
import com.me.fanyin.zbme.views.main.activity.adapter.ItemDragHideAdapter;
import com.me.fanyin.zbme.views.main.activity.divider.SpacesItemDecoration;
import com.me.fanyin.zbme.views.main.view.bean.SimpleTitleTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 也加上Mvp模式，方式后续加入其它的功能
 */
public class MainTypeChangeActivity extends BaseMvpActivity<MainTypeChangeView, MainTypeChangePersenter> implements MainTypeChangeView {

	@BindView(R.id.maintype_change_rv)
	RecyclerView maintype_change_rv;
	@BindView(R.id.maintype_change_hide_rv)
	RecyclerView maintype_change_hide_rv;
	@BindView(R.id.tv_main_type_left)
	TextView tv_main_type_left;
	@BindView(R.id.iv_main_type_right)
	ImageView iv_main_type_right;
	private boolean isMove = false;
	private boolean isDrag;
	private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
	private ItemDragAdapter mAdapter;
	private ArrayList<SimpleTitleTip> typeList;
	private ArrayList<SimpleTitleTip> hideList=new ArrayList<>();
	private ItemTouchHelper mItemTouchHelper;
	private int startPosition;
	private int removePosition;
	private ItemDragHideAdapter hideAdapter;
	private SimpleTitleTip removeTip;
	private TextView tv_header_top;
	private boolean isChanged;
	@OnClick(R.id.iv_main_type_right) void onClose(){
		onBackPressed();
	}
	@OnClick(R.id.tv_main_type_left) void onChange(){
		if (isDrag){
			drageType(true);
		}else{
			drageType(false);
		}
	}

	private void drageType(boolean drag) {
		if (drag){
			tv_main_type_left.setTextColor(getResources().getColor(R.color.text_color_primary_dark));
			tv_main_type_left.setText(getResources().getString(R.string.main_change_type_manager));
			iv_main_type_right.setVisibility(View.VISIBLE);
			tv_header_top.setText(getResources().getString(R.string.main_change_type_click));
			isDrag=false;
		}else{
			tv_main_type_left.setText(getResources().getString(R.string.main_change_type_complete));
			tv_main_type_left.setTextColor(getResources().getColor(R.color.color_primary));
			iv_main_type_right.setVisibility(View.GONE);
			tv_header_top.setText(getResources().getString(R.string.main_change_type_longclick));
			isDrag=true;
		}
	}

	@Override
	public void initView() {
		mToolbar.setTitleText("全部类目");
		maintype_change_hide_rv.setVisibility(View.GONE);
		maintype_change_hide_rv.setNestedScrollingEnabled(false);
//		maintype_change_rv.setNestedScrollingEnabled(false);
		Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();
		String data = bundle.getString("mainTypeList");
		List<SimpleTitleTip> simpleTitleTips = JSON.parseArray(data, SimpleTitleTip.class);
		if (simpleTitleTips == null) {
			simpleTitleTips = new ArrayList<>();
		}
		typeList = new ArrayList<>();
		typeList.addAll(simpleTitleTips);
		initConfig();
		initHide();
	}

	private void initHide() {
		
		int leftRight = DensityUtils.dip2px(MainTypeChangeActivity.this,2);
		int topBottom = DensityUtils.dip2px(MainTypeChangeActivity.this,2);
		SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(leftRight,topBottom);
		maintype_change_hide_rv.setLayoutManager(new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false));
		maintype_change_hide_rv.addItemDecoration(spacesItemDecoration);

		hideAdapter = new ItemDragHideAdapter(hideList);
		mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(hideAdapter);
		mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
		mItemTouchHelper.attachToRecyclerView(maintype_change_hide_rv);

		mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
		hideAdapter.disableSwipeItem();
		hideAdapter.enableDragItem(mItemTouchHelper);
		maintype_change_hide_rv.setAdapter(hideAdapter);
		hideAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//				removePosition=position;
//				removeTip=hideList.get(position);
//				startMoveAnim(view,false);
			}
		});
	}

	private void initConfig() {
		int leftRight = DensityUtils.dip2px(MainTypeChangeActivity.this,1);
		int topBottom = DensityUtils.dip2px(MainTypeChangeActivity.this,1);
		SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(leftRight,topBottom);
		maintype_change_rv.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));
		OnItemDragListener listener = new OnItemDragListener() {
			@Override
			public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
				
				if (!isDrag){
					drageType(false);
				}
				startPosition=pos;
			}

			@Override
			public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
			}

			@Override
			public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
				BaseViewHolder holder = ((BaseViewHolder) viewHolder);
				if (pos!=startPosition){
					isChanged=true;
				}
			}
		};
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(20);
		paint.setColor(Color.BLACK);
		OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
			@Override
			public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
				BaseViewHolder holder = ((BaseViewHolder) viewHolder);
			}

			@Override
			public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
				BaseViewHolder holder = ((BaseViewHolder) viewHolder);
			}

			@Override
			public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
			}

			@Override
			public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
				canvas.drawColor(ContextCompat.getColor(MainTypeChangeActivity.this, R.color.color_primary));
			}
		};

		mAdapter = new ItemDragAdapter(typeList);
		mAdapter.setContext(this);
		LinearLayout headView= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.main_type_change_top, null, false);
		tv_header_top=(TextView)headView.findViewById(R.id.tv_header_top);
		tv_header_top.setText(getResources().getString(R.string.main_change_type_click));
		mAdapter.addHeaderView(headView);
		mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
		mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
		mItemTouchHelper.attachToRecyclerView(maintype_change_rv);

		mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
		mAdapter.disableSwipeItem();
		mAdapter.setOnItemSwipeListener(onItemSwipeListener);
		mAdapter.enableDragItem(mItemTouchHelper);
		mAdapter.setOnItemDragListener(listener);

		maintype_change_rv.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				if (isDrag)return;
//				isChanged=true;
				finishActivity(position+"");
			}
		});
	}

	private void startMoveAnim(final View view, final boolean isHideViewClick) {
		if (isHideViewClick){

			hideList.add(removeTip);
			hideAdapter.notifyDataSetChanged();
			typeList.remove(removeTip);
			mAdapter.notifyDataSetChanged();
		}else{
			typeList.add(removeTip);
			mAdapter.notifyDataSetChanged();
			hideList.remove(removeTip);
			hideAdapter.notifyDataSetChanged();
		}
		final int[] startLocation = new int[2];
		view.getLocationInWindow(startLocation);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					int[] endLocation = new int[2];
					if (isHideViewClick){
						RecyclerView.LayoutManager layoutManager = maintype_change_hide_rv.getLayoutManager();
						if (layoutManager instanceof GridLayoutManager){
							GridLayoutManager gridLayoutManager=(GridLayoutManager)layoutManager;
							maintype_change_hide_rv.getChildAt(gridLayoutManager.getChildCount()-1).getLocationInWindow(endLocation);
						}
					}else{
						RecyclerView.LayoutManager layoutManager = maintype_change_rv.getLayoutManager();
						if (layoutManager instanceof GridLayoutManager){
							GridLayoutManager gridLayoutManager=(GridLayoutManager)layoutManager;
							maintype_change_rv.getChildAt(gridLayoutManager.getChildCount()-1).getLocationInWindow(endLocation);
						}
					}
					MoveAnim(view, startLocation , endLocation, isHideViewClick);
				} catch (Exception localException) {
				}
			}
		}, 50L);
	}

	@Override
	public void initData() {
		
	}

	@Override
	public int getLayoutRes() {
		return R.layout.main_type_change;
	}

	@Override
	protected void initInject() {
		getAppComponent().inject(MainTypeChangeActivity.this);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {

	}
	
	private void finishActivity(String clickiExamId){
		Intent intent1=new Intent();
		Bundle bundle1=new Bundle();
		if (!StringUtils.isEmpty(clickiExamId)){
			bundle1.putString("clickiExamId",clickiExamId);
		}
		bundle1.putBoolean("isChanged",isChanged);
		bundle1.putString("typeList",JSON.toJSONString(typeList));
		intent1.putExtras(bundle1);
		setResult(Constants.MAIN_FRAGMENT_CHANGE_TYPE_RESULT,intent1);
		finish();
	}


	@Override
	public void onBackPressed() {
		if (isDrag){
			drageType(true);
		}else{
			finishActivity("");
		}
	}

	@Override
	public void showError(String message) {

	}

	private void MoveAnim(View moveView, int[] startLocation, int[] endLocation,
						  final boolean isHideViewClick) {
		int[] initLocation = new int[2];
		moveView.getLocationInWindow(initLocation);
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);

		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);

		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);

				if (!isHideViewClick) {
					hideAdapter.notifyDataSetChanged();
//					typeList.remove(removePosition);
					mAdapter.notifyDataSetChanged();
				}else{
					mAdapter.notifyDataSetChanged();
//					hideList.remove(removePosition);
					hideAdapter.notifyDataSetChanged();
				}
				isMove = false;
			}
		});
	}

	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}

	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}

	@Override
	protected boolean displayHomeAsUp() {
		return false;
	}
}
