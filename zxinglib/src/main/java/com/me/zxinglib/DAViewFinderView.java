package com.me.zxinglib;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jjr on 2017/6/14.
 */

public class DAViewFinderView extends View implements IViewFinder {

    private static final float LANDSCAPE_HEIGHT_RATIO = 5.5f / 8;
    private static final float LANDSCAPE_WIDTH_HEIGHT_RATIO = 1.25f;
    private static final float PORTRAIT_WIDTH_RATIO = 6f / 8;
    private static final float PORTRAIT_WIDTH_HEIGHT_RATIO = 0.75f;
    private static final int MIN_DIMENSION_DIFF = 50;

    private final int mDefaultMaskColor = getResources().getColor(R.color.viewfinder_mask);

    private Rect mFramingRect;
    protected boolean mSquareViewFinder;
    protected Paint mFinderMaskPaint;
    protected Paint mBitmapPaint;
    private Bitmap mBorderBitmap;
    private Bitmap mLaserBitmap;
    private float mColumnHeight;
    private int mTopSpacing = 0;
    private Matrix mMatrix;
    private boolean isStartDrawLaser = false;

    public DAViewFinderView(Context context) {
        super(context);
        init();
    }

    public DAViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mFinderMaskPaint = new Paint();
        mFinderMaskPaint.setColor(mDefaultMaskColor);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBorderBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_saoyisao_kuang);
        mLaserBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_hinghlight);
        mMatrix = new Matrix();

    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        updateFramingRect();
        invalidate();
    }

    @Override
    public void setupViewFinder() {
        updateFramingRect();
    }

    @Override
    public Rect getFramingRect() {
        return mFramingRect;
    }

    @Override
    public void startCamera() {
        mColumnHeight = 0;
        isStartDrawLaser = !isStartDrawLaser;
        invalidate();
    }

    @Override
    public void stopCamera() {
        mColumnHeight = 0;
        isStartDrawLaser = !isStartDrawLaser;
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(getWidth(), getHeight());
        int width;
        int height;
        int orientation = DisplayUtils.getScreenOrientation(getContext());

        if (mSquareViewFinder) {//正方形
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {//竖屏
                height = (int) (getHeight() * LANDSCAPE_HEIGHT_RATIO);
                width = height;
            } else {//横屏
                width = (int) (getWidth() * PORTRAIT_WIDTH_RATIO);
                height = width;
            }
        } else {
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                height = (int) (getHeight() * LANDSCAPE_HEIGHT_RATIO);
                width = (int) (LANDSCAPE_WIDTH_HEIGHT_RATIO * height);
            } else {
                width = (int) (getWidth() * PORTRAIT_WIDTH_RATIO);
                height = (int) (PORTRAIT_WIDTH_HEIGHT_RATIO * width);
            }
        }

        if (width > getWidth()) {
            width = getWidth() - MIN_DIMENSION_DIFF;
        }

        if (height > getHeight()) {
            height = getHeight() - MIN_DIMENSION_DIFF;
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = mTopSpacing != 0 ? mTopSpacing : (viewResolution.y - height) / 2;
        mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getFramingRect() == null) {
            return;
        }
        drawViewFinderMask(canvas);
        drawViewFinderBorder(canvas);
        drawLaser(canvas);
    }

    public void drawViewFinderMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Rect framingRect = getFramingRect();

        canvas.drawRect(0, 0, width, framingRect.top, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.top, framingRect.left, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(framingRect.right + 1, framingRect.top, width, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.bottom + 1, width, height, mFinderMaskPaint);
    }

    private void drawViewFinderBorder(Canvas canvas) {
        canvas.drawBitmap(mBorderBitmap, null, mFramingRect, mBitmapPaint);
    }

    private void drawLaser(Canvas canvas) {
        if (isStartDrawLaser) {
            mMatrix.reset();
            mColumnHeight = mColumnHeight >= mFramingRect.height() - mLaserBitmap.getHeight() ? 0 : (mColumnHeight += 5);
            mMatrix.postTranslate((getWidth() - mLaserBitmap.getWidth()) / 2, mFramingRect.top + mColumnHeight);
            canvas.drawBitmap(mLaserBitmap, mMatrix, mBitmapPaint);
            invalidate();
        }
    }

    //设置扫描框为正方形
    public void setSquareViewFinder(boolean set) {
        mSquareViewFinder = set;
    }

    //设置扫描框距离顶部的距离
    public void setTopSpacing(int topSpacing) {
        mTopSpacing = topSpacing;
    }

}
