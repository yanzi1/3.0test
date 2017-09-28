package com.me.fanyin.zbme.widget.dropdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.me.core.utils.DensityUtils;
import com.me.fanyin.zbme.R;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by mayunfei on 17-4-24.
 */

public class DropDownMenuTitle extends FrameLayout {
    private final AppCompatTextView textView;
    int maxlength = -1;
    private final ImageView imageView;
    //选中颜色
    private int textSelectedColor = 0xff8d34;
    //未选中颜色
    private int textUnselectedColor = 0x35323a;
    //选中的下拉图
    private int menuSelectedIcon;
    //未选中下拉图
    private int menuUnselectedIcon;

    boolean isOpen = false;

    private String text = "";

    public DropDownMenuTitle(Context context) {
        this(context, null, 0);
    }

    public DropDownMenuTitle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenuTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenuTitle);
        textSelectedColor = a.getColor(R.styleable.DropDownMenuTitle_selectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropDownMenuTitle_unselectedColor, textUnselectedColor);
        menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenuTitle_selectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenuTitle_unselectedIcon, menuUnselectedIcon);
        float titleSize = a.getDimensionPixelSize(R.styleable.DropDownMenuTitle_text_size, 0);
        maxlength = a.getInt(R.styleable.DropDownMenuTitle_text_max_length, -1);
        setForegroundGravity(Gravity.CENTER);

        /**
         * 包裹一层 linearlayout
         */
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(HORIZONTAL);

        //TEXT
        text = a.getString(R.styleable.DropDownMenuTitle_text);
        textView = new AppCompatTextView(context);
        if (titleSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        }

//        if (maxlength >= 0) {
//            textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
//        }

        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxLines(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(text)) {
            if (maxlength > 0 && text.length() > maxlength) {
                text = text.substring(0, maxlength) + "...";
            }
            textView.setText(text);
        }

        //Image
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.leftMargin = DensityUtils.dip2px(context, 2);
        imageLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        imageView = new ImageView(context);
        imageView.setLayoutParams(imageLayoutParams);
        imageView.setImageResource(menuUnselectedIcon);

        //添加
        a.recycle();

        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        addView(linearLayout);
        //默认未选中
        textView.setTextColor(textUnselectedColor);
    }


    public void setText(String txt) {
        if (txt.contains("（")) {
            txt = txt.replace("（", "(");

        }
        if (txt.contains("）")) {
            txt = txt.replace("）", ")");
        }
        if (maxlength > 0 && txt.length() > maxlength) {
            txt = txt.substring(0, maxlength) + "...";
        }
        textView.setText(txt);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//        layoutParams.gravity = Gravity.CENTER_VERTICAL;
//        textView.setLayoutParams(layoutParams);
        textView.requestLayout();
    }

    public void openMenu() {
        if (!isOpen) {
            isOpen = true;
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.drop_down_menu_open);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }

//        textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
//                ContextCompat.getDrawable(getContext(), menuSelectedIcon), null);
    }

    public void closeMenu() {
        if (isOpen) {
            isOpen = false;
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.drop_down_menu_close);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }

//        textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
//                ContextCompat.getDrawable(getContext(), menuUnselectedIcon), null);
    }

    public String getTitle() {
        return textView.getText().toString();
    }


    public void hideIcon() {
        imageView.setVisibility(GONE);
    }
}
