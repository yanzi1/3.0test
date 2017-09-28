package com.me.fanyin.zbme.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishzhang on 2017/8/14.
 */

public class CircleProgressView extends View {

    private Paint bottomPaint;
    private Paint topPaint;
    private RectF progressArea;
    private float strokeWidth=3;
    private float shadowRadius=0;
    private float shadowOffsetX=0;
    private float shadowOffsetY=0;
    protected float precent=0f;
    protected CircleProgressLinstener progressLinstener;
    protected List<Animator> animatorList;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        animatorList=new ArrayList<>();

        strokeWidth= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,strokeWidth,context.getResources().getDisplayMetrics());

        bottomPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomPaint.setStyle(Paint.Style.STROKE);
        bottomPaint.setStrokeWidth(strokeWidth);
        bottomPaint.setColor(0xffe9fbe4);

        topPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        topPaint.setStyle(Paint.Style.STROKE);
        topPaint.setStrokeCap(Paint.Cap.ROUND);
        topPaint.setStrokeWidth(strokeWidth);
        topPaint.setColor(0xff6ed05b);
        //设置阴影  需要屏蔽硬件加速
        bottomPaint.setShadowLayer(shadowRadius,shadowOffsetX,shadowOffsetY, Color.LTGRAY);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float centerX=w/2;
        float centerY=h/2;
        float diameter=w>h? h-getPaddingRight()-getPaddingLeft()-strokeWidth-
                        (shadowRadius>0?(shadowRadius+shadowOffsetY)*2:0)
                : w-getPaddingRight()-getPaddingLeft()-strokeWidth-
                        (shadowRadius>0?(shadowRadius+shadowOffsetX)*2:0);
        float radius=diameter/2;
        progressArea=new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        if (shadowRadius>0){
            width+=(shadowRadius+shadowOffsetX)*2;
            height+=(shadowRadius+shadowOffsetY)*2;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(progressArea,0,360,false,bottomPaint);
        canvas.drawArc(progressArea,-90,precent*360,false,topPaint);
    }

    public synchronized void setDataWithAnim(float p, int duration, TimeInterpolator interpolator){
        removeAnim();

        ValueAnimator animator= ValueAnimator.ofFloat(0,p);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                precent=(float)animation.getAnimatedValue();
                if (progressLinstener != null){
                    progressLinstener.onProgress(precent);
                }
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorList.remove(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                animatorList.remove(animation);
            }
        });
        animator.setDuration((long) (duration*p));
        if (interpolator==null){
            interpolator=new LinearInterpolator();
        }
        animator.setInterpolator(interpolator);
        animator.start();
        animatorList.add(animator);
    }

    public synchronized void setData(float precent){
        this.precent=precent;
        invalidate();
    }

    public void setProgressListener(CircleProgressLinstener progressLinstener){
        this.progressLinstener=progressLinstener;
    }

    public interface CircleProgressLinstener{
        void onProgress(float precent);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAnim();
    }

    private void removeAnim() {
        if (!animatorList.isEmpty() ){
            for (Animator animator : animatorList) {
                if (animator!=null)
                    animator.cancel();
            }
            animatorList.clear();
        }
    }
}
