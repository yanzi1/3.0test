package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.fanyin.zbme.R;

/**
 * Created by xd on 2017/3/30.
 */

public class CommonToolbar extends Toolbar implements View.OnClickListener{
    private static final int DEFAULT_COLOR_VALUE=-1;
    private static final float DEFAULT_TEXT_SIZE_VALUE=16f;
    private static final int DEFAULT_TITLE_VISIBLE=0;
    private static final int DEFAULT_TITLE_INVISIBLE=1;
    private static final int DEFAULT_TITLE_GONE=2;
    private static final float DEFAULT_IMAGE_MENU_MARGIN_RIGHT = 0.0f;
    private Context context;
    private TextView titleView;
    private ImageView imageMenu;
    private ImageView imageMenu2;
    private TextView textMenu;
    private CommonToolbarClickListener commonToolbarClickListener;
    private final LinearLayout menuLayout;

    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolbar);
        int titleColor = typedArray.getColor(R.styleable.CommonToolbar_titleColor,DEFAULT_COLOR_VALUE);
        float titleSize = typedArray.getDimension(R.styleable.CommonToolbar_titleSize
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,DEFAULT_TEXT_SIZE_VALUE
                        ,context.getResources().getDisplayMetrics()));
        titleSize=titleSize/context.getResources().getDisplayMetrics().density+0.5f;
        String titleText=typedArray.getString(R.styleable.CommonToolbar_titleText);
        int visible_type=typedArray.getInt(R.styleable.CommonToolbar_titleVisible,DEFAULT_TITLE_VISIBLE);
        float image_menu_margin_right = typedArray.getDimension(R.styleable.CommonToolbar_imageMenuMarginRight,DEFAULT_IMAGE_MENU_MARGIN_RIGHT);

        // 添加标题
        titleView = new TextView(context);
        LayoutParams titleLp=new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.gravity= Gravity.CENTER;
        titleView.setLayoutParams(titleLp);
        titleView.setMaxLines(1);
        titleView.setId(R.id.common_toolbar_title);
//        titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        titleView.setOnClickListener(this);
        if (titleColor!=DEFAULT_COLOR_VALUE){
            titleView.setTextColor(titleColor);
        }
        titleView.setTextSize(titleSize);
        if (!TextUtils.isEmpty(titleText)){
            titleView.setText(titleText);
        }
        switch (visible_type){
            case DEFAULT_TITLE_VISIBLE:
                titleView.setVisibility( View.VISIBLE);
                break;
            case DEFAULT_TITLE_INVISIBLE:
                titleView.setVisibility(View.INVISIBLE);
                break;
            case DEFAULT_TITLE_GONE:
                titleView.setVisibility(View.GONE);
                break;
        }
        addView(titleView);

        int imageMenuRes = typedArray.getResourceId(R.styleable.CommonToolbar_imageMenuSrc, 0);
        int imageMenu2Res = typedArray.getResourceId(R.styleable.CommonToolbar_imageMenu2Src, 0);
        String  textMenuText=typedArray.getString(R.styleable.CommonToolbar_textMenuText);
        float  textMenuTextSize=typedArray.getDimension(R.styleable.CommonToolbar_textMenuTextSize
                ,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,DEFAULT_TEXT_SIZE_VALUE
                        ,context.getResources().getDisplayMetrics()));
        textMenuTextSize=textMenuTextSize/context.getResources().getDisplayMetrics().density+0.5f;
        int  textMenuColor=typedArray.getColor(R.styleable.CommonToolbar_textMenuTextColor,0);



        // 添加自定义menu
        menuLayout = new LinearLayout(context);
        LayoutParams menuLayoutLp=new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.MATCH_PARENT
        );
        menuLayoutLp.gravity=Gravity.RIGHT;
        menuLayout.setOrientation(LinearLayout.HORIZONTAL);
        menuLayout.setLayoutParams(menuLayoutLp);
        menuLayout.setVisibility(GONE);

        LayoutParams imageMenuLp=new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT
        );
        imageMenuLp.setMargins(0,0,0,0);
        imageMenu=new ImageView(context);
        imageMenu.setScaleType(ImageView.ScaleType.CENTER);
        imageMenu.setLayoutParams(imageMenuLp);
        imageMenu.setVisibility(View.GONE);
        imageMenu.setId(R.id.common_toolbar_ivmenu);
        imageMenu.setOnClickListener(this);
        if (imageMenuRes!=0){
            imageMenu.setImageResource(imageMenuRes);
        }
        menuLayout.addView(imageMenu);

        imageMenu2=new ImageView(context);
        imageMenu2.setScaleType(ImageView.ScaleType.CENTER);
        imageMenu2.setLayoutParams(imageMenuLp);
        imageMenu2.setVisibility(View.GONE);
        imageMenu2.setId(R.id.common_toolbar_ivmenu2);
        imageMenu2.setOnClickListener(this);
        if (imageMenu2Res!=0){
            imageMenu2.setImageResource(imageMenu2Res);
        }
        menuLayout.addView(imageMenu2);

        LayoutParams textMenuLp=new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.MATCH_PARENT
        );
        int rightPadding = (int) context.getResources().getDimension(R.dimen.spacing_xlarge);
//        textMenuLp.rightMargin=rightMargin;

        textMenu=new TextView(context);
        textMenu.setPadding(0,0,rightPadding,0);
        textMenu.setGravity(Gravity.CENTER);
        textMenu.setLayoutParams(textMenuLp);
        textMenu.setVisibility(View.GONE);
        textMenu.setId(R.id.common_toolbar_tvmenu);
        textMenu.setOnClickListener(this);
        if (!TextUtils.isEmpty(textMenuText)){
            textMenu.setText(textMenuText);
        }
        textMenu.setTextSize(textMenuTextSize);
        if (textMenuColor!=0){
            textMenu.setTextColor(textMenuColor);
        }
        menuLayout.addView(textMenu);

        addView(menuLayout);

        typedArray.recycle();

    }

    public void setTitleText(String title){
        titleView.setText(title);
    }

    public String getTitleText(){
        return titleView.getText().toString();
    }

    public void setTitleTextSize(float size){
        titleView.setTextSize(size);
    }

    public void setTitleColor(int color){
        titleView.setTextColor(color);
    }

    public void setTitleVisible(int visible){
        titleView.setVisibility(visible);
    }

    public void setImageMenuRes(int resId){
        menuLayout.setVisibility(VISIBLE);
        imageMenu.setImageResource(resId);
        imageMenu.setVisibility(VISIBLE);
    }

    public void setImageMenu2Res(int resId){
        imageMenu2.setImageResource(resId);
    }

    public void setTextMenuText(@NonNull String text){
        menuLayout.setVisibility(VISIBLE);
        textMenu.setVisibility(VISIBLE);
        textMenu.setText(text);
    }

    public void setMenuLayoutVisibility(int visibility){
        menuLayout.setVisibility(visibility);
    }

    public void settextMenuTextColor(@ColorRes int color){
        textMenu.setTextColor(ContextCompat.getColor(context,color));
    }

    public void settextMenuTextSize(float size){
        textMenu.setTextSize(size);
    }

    public void showIamgeMenu(){
        imageMenu.setVisibility(VISIBLE);
    }

    public void showIamgeMenu2(){
        imageMenu2.setVisibility(View.VISIBLE);
    }

    public TextView getTitleView(){
        return titleView;
    }

    public ImageView getImageMenu(){
        return imageMenu;
    }

    public ImageView getImageMenu2(){
        return imageMenu2;
    }

    public void setOnMenuClickListener(CommonToolbarClickListener listener){
        commonToolbarClickListener =listener;
    }

    @Override
    public void onClick(View v) {
        if (commonToolbarClickListener!=null)
            commonToolbarClickListener.onClick(v);
    }

    public interface CommonToolbarClickListener{
        void onClick(View view);
    }
}
