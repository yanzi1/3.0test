package com.me.fanyin.zbme.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by wyc 
 */

public class PopWindow {

    public static <T> PopupWindow showBottomPop(Context context, List<T> list
            , MyBaseAdapter.HolderCallback<T> cb
            , MyBaseAdapter.MyBaseAdapterOnItemClickLinstener linstener
            , PopupWindow.OnDismissListener dismissListener){
        final PopupWindow popupWindow=new PopupWindow(context);
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setClippingEnabled(false);

        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_window_list_layout, null);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.pop_window_list_recyclerView);
        LinearLayout ll_pop= (LinearLayout) rootView.findViewById(R.id.ll_pop);
        ll_pop.setGravity(Gravity.BOTTOM);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyBaseAdapter<T> adapter=new MyBaseAdapter<>(cb,list,context,linstener);
        recyclerView.setAdapter(adapter);
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopBottomAnimation);
        return popupWindow;
    }
    
    public static void setPopBackground(Context context,boolean isNormal){
        if (!isNormal){
            WindowManager.LayoutParams lp=((Activity)context).getWindow().getAttributes();
            lp.alpha=0.7f;
            ((Activity)context).getWindow().setAttributes(lp);
        }else{
            WindowManager.LayoutParams lp=((Activity)context).getWindow().getAttributes();
            lp.alpha=1f;
            ((Activity)context).getWindow().setAttributes(lp);
        }
    }
    public static void setPopTopBackground(Context context,boolean isNormal,View view){
        if (!isNormal){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    public interface ResponseDataCallBack {
        void responseData(String data);
    }

    public static <T> PopupWindow showPop(Context context, List<T> list
            , MyBaseAdapter.HolderCallback<T> cb
            , MyBaseAdapter.MyBaseAdapterOnItemClickLinstener linstener
            , PopupWindow.OnDismissListener dismissListener){
        final PopupWindow popupWindow=new PopupWindow(context);
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setClippingEnabled(false);

        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_window_list_layout, null);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.pop_window_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyBaseAdapter<T> adapter=new MyBaseAdapter<>(cb,list,context,linstener);
        recyclerView.setAdapter(adapter);
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopTopAnimation);
        return popupWindow;
    }

    public static <T> PopupWindow showTopRightPop(Context context, List<T> list
            , MyBaseAdapter.HolderCallback<T> cb
            , MyBaseAdapter.MyBaseAdapterOnItemClickLinstener linstener
            , PopupWindow.OnDismissListener dismissListener){
        final PopupWindow popupWindow=new PopupWindow(context);
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setClippingEnabled(false);

        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_window_list_layout, null);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.pop_window_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyBaseAdapter<T> adapter=new MyBaseAdapter<>(cb,list,context,linstener);
        recyclerView.setAdapter(adapter);
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopTopAnimation);
        return popupWindow;
    }


    public static PopupWindow showPopupWindow(int resId,Context context,PopupWindowViewCallBack callBack) {
        PopupWindow popupWindow= new PopupWindow(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        View view = LayoutInflater.from(context).inflate(resId, null);
        callBack.initPopView(view);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        return popupWindow;
    }

    public interface PopupWindowViewCallBack{
        void initPopView(View view);
    }
    
}
