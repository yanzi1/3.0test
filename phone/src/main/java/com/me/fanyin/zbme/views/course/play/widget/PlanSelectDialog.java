package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.model.play.PlanDialogBean;
import com.me.fanyin.zbme.R;

import java.util.ArrayList;
import java.util.List;

public class PlanSelectDialog extends Dialog implements OnClickListener,DialogInterface.OnDismissListener {

    private SelectDialogListener mListener;
    private Activity mActivity;
    private ImageView close,imgFirst,imgSecond,imgThird,imgFourth,imgFive,imgSix;
    private TextView tvFirst,tvSecond,tvThird,tvFourth,tvFive,tvSix;
    private LinearLayout first,second,third,fourth,five,six;
    private List<PlanDialogBean> beans;
    private List<TextView> texts=new ArrayList();
    private List<ImageView> images=new ArrayList();

    @Override
    public void onDismiss(DialogInterface dialog) {
        mCancelListener.onCancelClick();
    }

    public interface SelectDialogListener {
        public void onItemClick(int position);
    }


    /**
     * 取消事件监听接口
     *
     */
    private SelectDialogCancelListener mCancelListener;

    public interface SelectDialogCancelListener {
        public void onCancelClick();
    }

    public PlanSelectDialog(Activity activity, int theme,SelectDialogListener listener,SelectDialogCancelListener mCancelListener,List<PlanDialogBean> beans) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mCancelListener=mCancelListener;
        this.beans=beans;

        setOnDismissListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.plan_dialog_select,
                null);
        setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.take_photo_anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        initViews();
    }

    private void initViews() {
        close = (ImageView) findViewById(R.id.plan_close);

        first=(LinearLayout) findViewById(R.id.plan_play_first);
        second=(LinearLayout) findViewById(R.id.plan_play_second);
        third=(LinearLayout) findViewById(R.id.plan_play_third);
        fourth=(LinearLayout) findViewById(R.id.plan_play_fourth);
        five=(LinearLayout) findViewById(R.id.plan_play_five);
        six=(LinearLayout) findViewById(R.id.plan_play_six);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fourth.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);

        imgFirst=(ImageView) findViewById(R.id.img_first);
        imgSecond=(ImageView) findViewById(R.id.img_second);
        imgThird=(ImageView) findViewById(R.id.img_third);
        imgFourth=(ImageView) findViewById(R.id.img_fourth);
        imgFive=(ImageView) findViewById(R.id.img_five);
        imgSix=(ImageView) findViewById(R.id.img_six);

        tvFirst=(TextView) findViewById(R.id.tv_first);
        tvSecond=(TextView) findViewById(R.id.tv_second);
        tvThird=(TextView) findViewById(R.id.tv_third);
        tvFourth=(TextView) findViewById(R.id.tv_fourth);
        tvFive=(TextView) findViewById(R.id.tv_five);
        tvSix=(TextView) findViewById(R.id.tv_six);
        initResources();

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plan_play_first:
                mListener.onItemClick(0);
                break;
            case R.id.plan_play_second:
                mListener.onItemClick(1);
                break;
            case R.id.plan_play_third:
                mListener.onItemClick(2);
                break;
            case R.id.plan_play_fourth:
                mListener.onItemClick(3);
                break;
            case R.id.plan_play_five:
                mListener.onItemClick(4);
                break;
            case R.id.plan_play_six:
                mListener.onItemClick(5);
                break;
        }
        dismiss();
    }

    public void hideNexTask(){
        if(five!=null){
            five.setVisibility(View.GONE);
        }
    }

    private void initResources(){
        images.clear();
        texts.clear();
        images.add(imgFirst);
        images.add(imgSecond);
        images.add(imgThird);
        images.add(imgFourth);
        images.add(imgFive);
        images.add(imgSix);

        texts.add(tvFirst);
        texts.add(tvSecond);
        texts.add(tvThird);
        texts.add(tvFourth);
        texts.add(tvFive);
        texts.add(tvSix);

        for(int i=0;i<beans.size();i++){
            images.get(i).setImageResource(beans.get(i).getMipmap());
            texts.get(i).setText(beans.get(i).getName());
        }

    }

}
