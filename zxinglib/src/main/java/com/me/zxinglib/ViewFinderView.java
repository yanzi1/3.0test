package com.me.zxinglib;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class ViewFinderView extends View implements IViewFinder {
    private static final String TAG = "ViewFinderView";

    private Rect mFramingRect;

    private static final float PORTRAIT_WIDTH_RATIO = 6f / 8;
    private static final float PORTRAIT_WIDTH_HEIGHT_RATIO = 0.75f;

    private static final float LANDSCAPE_HEIGHT_RATIO = 5.5f / 8;
    private static final float LANDSCAPE_WIDTH_HEIGHT_RATIO = 1.25f;
    private static final int MIN_DIMENSION_DIFF = 50;

    private int[] mGridColors;

    private final int mDefaultGridColor = getResources().getColor(R.color.viewfinder_grid);
    private final int mDefaultLaserStartColor = getResources().getColor(R.color.viewfinder_laser_start);
    private final int mDefaultMaskColor = getResources().getColor(R.color.viewfinder_mask);
    private final int mDefaultBorderColor = getResources().getColor(R.color.viewfinder_border);
    private final int mTransparentColor = getResources().getColor(android.R.color.transparent);
    private final int mDefaultBorderStrokeWidth = getResources().getInteger(R.integer.viewfinder_border_width);
    private final int mDefaultBorderLineLength = getResources().getInteger(R.integer.viewfinder_border_length);

    protected Paint mGridPaint;
    private Paint mLaserPaint;
    protected Paint mFinderMaskPaint;
    protected Paint mBorderPaint;
    protected int mBorderLineLength;
    protected boolean mSquareViewFinder;
    private float mBoxNum = 40f;//每行方向上小方格的数量
    private float mColumnHeight;
    private float columnStartX, columnStartY, columnStopX, columnStopY;
    private float rowStartX, rowStartY, rowStopX, rowStopY;
    private int mTopSpacing = 0;

//    private int[] mLaserColors;
//    private float[] mLaserPositions = {0.05f, 0.95f};
//    private LinearGradient mLaserGradient;

    public ViewFinderView(Context context) {
        super(context);
        init();
    }

    public ViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //set up grid paint
        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridColors = new int[]{mTransparentColor, mDefaultGridColor};

        //set up laser paint
        mLaserPaint = new Paint();
        mLaserPaint.setAntiAlias(true);
        mLaserPaint.setStyle(Paint.Style.FILL);
//        mLaserColors = new int[]{mTransparentColor, mDefaultLaserStartColor};
        mLaserPaint.setColor(mDefaultLaserStartColor);

        //finder mask paint
        mFinderMaskPaint = new Paint();
        mFinderMaskPaint.setColor(mDefaultMaskColor);

        //border paint
        mBorderPaint = new Paint();
        mBorderPaint.setColor(mDefaultBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mDefaultBorderStrokeWidth);

        mBorderLineLength = mDefaultBorderLineLength;
    }

    //设置扫描网格的颜色
    public void setLaserColor(int laserColor) {
        mGridPaint.setColor(laserColor);
    }

    //设置四周背景色
    public void setMaskColor(int maskColor) {
        mFinderMaskPaint.setColor(maskColor);
    }

    //设置四个角的直角颜色
    public void setBorderColor(int borderColor) {
        mBorderPaint.setColor(borderColor);
    }

    //设置四个角的直角线条的宽度
    public void setBorderStrokeWidth(int borderStrokeWidth) {
        mBorderPaint.setStrokeWidth(borderStrokeWidth);
    }

    //设置四个角的直角线条的长度
    public void setBorderLineLength(int borderLineLength) {
        mBorderLineLength = borderLineLength;
    }

    //设置扫描框为正方形
    public void setSquareViewFinder(boolean set) {
        mSquareViewFinder = set;
    }

    //设置扫描激光每行的方格数
    public void setBoxNum(int boxNum) {
        mBoxNum = boxNum;
    }

    //设置扫描框距离顶部的距离
    public void setTopSpacing(int topSpacing) {
        mTopSpacing = topSpacing;
    }

    @Override
    public void setupViewFinder() {
        updateFramingRect();
        invalidate();
    }

    //设置网格渐变色区域
    private void initGridShader() {
        LinearGradient shader = new LinearGradient(getFramingRect().left, getFramingRect().top, getFramingRect().left, getFramingRect().bottom,
                mGridColors, null, Shader.TileMode.CLAMP);
        mGridPaint.setShader(shader);
    }

    @Override
    public Rect getFramingRect() {
        return mFramingRect;
    }

    @Override
    public void startCamera() {
        mColumnHeight = 0;
    }

    @Override
    public void stopCamera() {
        mColumnHeight = 0;
    }

    @Override
    public void onDraw(Canvas canvas) {
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

    public void drawViewFinderBorder(Canvas canvas) {
        Rect framingRect = getFramingRect();
        float offset = mDefaultBorderStrokeWidth / 2;
        canvas.drawLine(framingRect.left + offset, framingRect.top, framingRect.left + offset, framingRect.top + mBorderLineLength, mBorderPaint);
        canvas.drawLine(framingRect.left, framingRect.top + offset, framingRect.left + mBorderLineLength, framingRect.top + offset, mBorderPaint);

        canvas.drawLine(framingRect.left + offset, framingRect.bottom, framingRect.left + offset, framingRect.bottom - mBorderLineLength, mBorderPaint);
        canvas.drawLine(framingRect.left, framingRect.bottom - offset, framingRect.left + mBorderLineLength, framingRect.bottom - offset, mBorderPaint);

        canvas.drawLine(framingRect.right - offset, framingRect.top, framingRect.right - offset, framingRect.top + mBorderLineLength, mBorderPaint);
        canvas.drawLine(framingRect.right, framingRect.top + offset, framingRect.right - mBorderLineLength, framingRect.top + offset, mBorderPaint);

        canvas.drawLine(framingRect.right - offset, framingRect.bottom, framingRect.right - offset, framingRect.bottom - mBorderLineLength, mBorderPaint);
        canvas.drawLine(framingRect.right, framingRect.bottom - offset, framingRect.right - mBorderLineLength, framingRect.bottom - offset, mBorderPaint);
    }

    public void drawLaser(final Canvas canvas) {
        drawGrid(canvas, getFramingRect());
    }

    private void drawGrid(Canvas canvas, Rect framingRect) {

        mColumnHeight = mColumnHeight >= mFramingRect.height() - 10 ? 0 : (mColumnHeight += 5);
        //小矩形的宽度
        float boxWidth = framingRect.width() / mBoxNum;
        //行直线的数量
        float rowNum = framingRect.height() / boxWidth;
        //画竖线
        for (int i = 0; i <= mBoxNum; i++) {
            columnStartX = framingRect.left + boxWidth * i;
            columnStartY = framingRect.top;
            columnStopX = columnStartX;
            columnStopY = columnStartY + mColumnHeight;
            canvas.drawLine(columnStartX, columnStartY, columnStopX, columnStopY, mGridPaint);
        }
        //画横线
        for (int i = 1; i <= rowNum + 1; i++) {
            rowStartX = framingRect.left;
            rowStartY = framingRect.top + mColumnHeight / (rowNum + 1) * i;
            rowStopX = framingRect.right;
            rowStopY = rowStartY;
            if (i == rowNum + 1) {
//                mLaserGradient = new LinearGradient(rowStartX, rowStartY - 10, rowStartX, rowStartY,
//                        mLaserColors, mLaserPositions, Shader.TileMode.CLAMP);
//                mLaserPaint.setShader(mLaserGradient);
                canvas.drawRect(rowStartX, rowStartY - 3, rowStopX, rowStopY, mLaserPaint);
            }
            canvas.drawLine(rowStartX, rowStartY, rowStopX, rowStopY, mGridPaint);
        }
        postInvalidate(framingRect.left, framingRect.top, framingRect.right, framingRect.bottom);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        updateFramingRect();
        initGridShader();
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(getWidth(), getHeight());
        int width;
        int height;
        int orientation = DisplayUtils.getScreenOrientation(getContext());

        if (mSquareViewFinder) {
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                height = (int) (getHeight() * LANDSCAPE_HEIGHT_RATIO);
                width = height;
            } else {
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
}
