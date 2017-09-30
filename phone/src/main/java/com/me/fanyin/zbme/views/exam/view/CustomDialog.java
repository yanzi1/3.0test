package com.me.fanyin.zbme.views.exam.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class CustomDialog extends Dialog {

	private static int default_width = LayoutParams.MATCH_PARENT;
	private static int default_height = LayoutParams.WRAP_CONTENT;

	public CustomDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style,false);
	}
	
	public CustomDialog(Context context, int layout, int width, int style) {
		this(context, width, default_height, layout, style,false);
	}

	public CustomDialog(Context context, int width, int height, int layout, int style, boolean isFullScreen) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		float density = getDensity(context);
		// 代码修改，FILL_PARENT也会留出�?���?
		if (width < 0) {
			int[] widthAndHeight = getSrceenPixels(context);
			if(isFullScreen){
				params.width = (int) (widthAndHeight[0]);
			}else{
				params.width = (int) (widthAndHeight[0] - 60 * density);
			}
		} else {
			params.width = (int) (width * density);
		}
		if (height < 0) {
			params.height = default_height;
		} else {
			params.height = (int) (height * density);
		}
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	private int[] getSrceenPixels(Context context) {
		DisplayMetrics displaysMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		windowManager.getDefaultDisplay().getMetrics(displaysMetrics);
		int[] widthAndHeight = new int[2];
		widthAndHeight[0] = displaysMetrics.widthPixels;
		widthAndHeight[1] = displaysMetrics.heightPixels;
		return widthAndHeight;
	}
}
