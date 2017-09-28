package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import com.me.fanyin.zbme.R;


/**
 * Created by xd on 2017/3/31.
 */

public class VerificationEditText extends EditText {
    private Paint textPaint;
    private String textContent;
    private int codeLength;

    public VerificationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationEditText);

        int codeColor=typedArray.getColor(R.styleable.VerificationEditText_codeTextColor, Color.DKGRAY);
        float codeSize=typedArray.getDimension(R.styleable.VerificationEditText_codeTextSize
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,context.getResources().getDisplayMetrics()));
//        codeSize = codeSize/context.getResources().getDisplayMetrics().density+0.5f;
        codeLength = typedArray.getInteger(R.styleable.VerificationEditText_codeLength,6);
        setFilters(new InputFilter[] { new InputFilter.LengthFilter(codeLength) });
        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(codeSize);
        textPaint.setColor(codeColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float offset=width/codeLength;
        float x=offset/2;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        for (int i=0;i<textContent.length();i++){
            canvas.drawText(textContent.charAt(i)+""
                    ,x
                    ,(height-fontMetrics.top-fontMetrics.bottom)/2
                    ,textPaint);
            x+=offset;
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textContent=text.toString();
        invalidate();
    }
}
