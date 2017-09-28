package com.me.share.adapter;


import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.share.R;

import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by xd on 2017/3/28.
 */

public class ShareAuthorizeAdapter extends AuthorizeAdapter {

    @Override
    public void onCreate() {
        hideShareSDKLogo();
        disablePopUpAnimation();
        TitleLayout llTitle = getTitleLayout();
        for (int i=0;i< llTitle.getChildCount();i++){
            llTitle.getChildAt(i).setVisibility(View.GONE);
        }

        TextView titleTv=new TextView(getActivity());
        LinearLayout.LayoutParams titleLp=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.gravity= Gravity.CENTER;
        titleTv.setLayoutParams(titleLp);
        titleTv.setText(llTitle.getTvTitle().getText());
        titleTv.setTextSize(18);
        titleTv.setTextColor(Color.parseColor("#35323a"));

        ImageView btnBack = llTitle.getBtnBack();
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setImageResource(R.drawable.app_back_arrow_icon);

        LinearLayout.LayoutParams toolbarLp=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT
                , (int)(getActivity().getResources().getDisplayMetrics().density*50f+0.5f));
        llTitle.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        llTitle.setLayoutParams(toolbarLp);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
            llTitle.setTranslationZ(getActivity().getResources().getDisplayMetrics().density*4+0.5f);
        llTitle.addView(titleTv);
    }
}
