package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by xd on 2017/6/20.
 */

public class MyQulineView extends View {
    public static final int LINE_TYPE_CURVE=1;
    public static final int LINE_TYPE_POLYLINE=2;

    private static final float DEFULT_LINE_WIDTH=1;
    private static final int DEFULT_LINE_COLOR=0xffffffff;
    private static final float DEFULT_XYLINE_WIDTH=0.5f;
    private static final int DEFULT_XYLINE_COLOR=0xffffc5a6;
    private static final float DEFULT_POINT_SIZE=2;
    private static final int DEFULT_POINT_COLOR=0xffffffff;
    private static final float DEFULT_TEXT_SIZE=12;
    private static final int DEFULT_TEXT_COLOR=0xffffc5a6;
    private static final int DEFULT_TRANSITION_START_COLOR=0xeeeeeeee;
    private static final int DEFULT_TRANSITION_END_COLOR=0x00eeeeee;
    private static final float DEFULT_DISTANCE_LR_SIDE_WIDTH=25;
    private static final float DEFULT_LINE_DISTANCE_BOTTOMTEXT_SIZE=18;
    private static final float DEFULT_TEXT_DISTANCE_BOTTOMLINE_SIZE=5;

    private int lineType=LINE_TYPE_CURVE;

    private static final int PADDING=10;

    private float distanceLRSideWidth=0;

    private Paint linePaint;
    private Paint xyPaint;
    private Paint textPaint;
    private Paint pointPaint;

    private int width;
    private int height;
    private float leftDataWidth;

    private float maxValue;
    private float minValue;

    private List<String> leftData;
    private List<Float> datas;
    private float pointSize;

    private int transitionStartColor;
    private int transitionEndColor;
    private boolean showPoint;

    private float lineDistanceBottomTextSize;
    private float textDistanceBottomLineSize;
    private float text_size;
    private float linePaddingTop;
    private List<MyQulineBottomBean> bottomDatas;
    private int text_color;
    private boolean isCustomWeekShowXData=true;

    public MyQulineView(Context context) {
        this(context,null);
    }

    public MyQulineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyQulineView);
        transitionStartColor=typedArray.getColor(R.styleable.MyQulineView_MytransitionStartColor
                ,DEFULT_TRANSITION_START_COLOR);
        transitionEndColor=typedArray.getColor(R.styleable.MyQulineView_MytransitionEndColor
                ,DEFULT_TRANSITION_END_COLOR);
        lineType=typedArray.getInt(R.styleable.MyQulineView_MylineType,LINE_TYPE_CURVE);
        showPoint =typedArray.getBoolean(R.styleable.MyQulineView_MyshowPoint,false);
        distanceLRSideWidth =typedArray.getDimension(R.styleable.MyQulineView_QLineDistanceLRSideWidth
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_DISTANCE_LR_SIDE_WIDTH,displayMetrics));
        lineDistanceBottomTextSize =typedArray.getDimension(R.styleable.MyQulineView_LineDistanceBottomTextSize
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_LINE_DISTANCE_BOTTOMTEXT_SIZE,displayMetrics));
        textDistanceBottomLineSize =typedArray.getDimension(R.styleable.MyQulineView_TextDistanceBottomLineSize
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_TEXT_DISTANCE_BOTTOMLINE_SIZE,displayMetrics));

        initPaint(typedArray, displayMetrics);

        typedArray.recycle();
    }

    private void initPaint(TypedArray typedArray, DisplayMetrics displayMetrics) {
        float lineWidth=typedArray.getDimension(R.styleable.MyQulineView_Myline_Width
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_LINE_WIDTH,displayMetrics));
        int lineColor=typedArray.getColor(R.styleable.MyQulineView_Myline_Color
                ,DEFULT_LINE_COLOR);

        float xyLineWidth=typedArray.getDimension(R.styleable.MyQulineView_MyxyLineWidth
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_XYLINE_WIDTH,displayMetrics));
        int xyLineColor=typedArray.getColor(R.styleable.MyQulineView_MyxyLineColor
                ,DEFULT_XYLINE_COLOR);

        pointSize = typedArray.getDimension(R.styleable.MyQulineView_Mytext_size
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFULT_POINT_SIZE,displayMetrics));
        int pointColor=typedArray.getColor(R.styleable.MyQulineView_Mytext_color
                ,DEFULT_POINT_COLOR);

        text_size = typedArray.getDimension(R.styleable.MyQulineView_Mytext_size
                , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,DEFULT_TEXT_SIZE,displayMetrics));
        text_color = typedArray.getColor(R.styleable.MyQulineView_Mytext_color
                ,DEFULT_TEXT_COLOR);


        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);

        pointPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(pointColor);

        xyPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xyPaint.setStyle(Paint.Style.STROKE);
        xyPaint.setStrokeWidth(xyLineWidth);
        xyPaint.setColor(xyLineColor);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(text_size);
        textPaint.setColor(text_color);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width =w;
        height =h;
        //各曲线设置渐变色
//        float sh=height;
//        linearGradient=new LinearGradient(0f,0f,0f,sh,new int[]{ Color.BLUE,Color.GRAY ,Color.BLUE},null, Shader.TileMode.REPEAT);
//        linePaint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isNoData())
            return;
        //获取原点
        float lypx=0;
        float lypy=height- text_size - lineDistanceBottomTextSize;
        float ypx=distanceLRSideWidth;
        float ypy=lypy;

        linePaddingTop = textDistanceBottomLineSize+text_size;

        //根据数值算对应占用高度和宽度的比例
        float xlenghtScale=(width-ypx-distanceLRSideWidth-leftDataWidth)/(datas.size()-1);
        if (isCustomWeekShowXData){
            if (datas.size()<7){
                xlenghtScale=(width-ypx-distanceLRSideWidth-leftDataWidth)/6;
            }
        }
//        xlenghtScale+=xlenghtScale/(datas.size()-1);
        float ylenghtScale=(ypy- linePaddingTop)/(maxValue);

        Path linePath=new Path();
        Path shadowPath=new Path();

        if (lineType == LINE_TYPE_CURVE){//设置曲线的path
            for (int i = 0; i < datas.size()-1; i++) {
                float svalue = datas.get(i) ;
                float evalue = datas.get(i+1)  ;
                PointF sp=new PointF(xlenghtScale*i+ypx,ypy-svalue*ylenghtScale );
                PointF ep=new PointF(xlenghtScale*(i+1)+ypx,ypy-evalue*ylenghtScale );
                float wt=(sp.x+ep.x)/2;
                PointF p1=new PointF(wt,sp.y);
                PointF p2=new PointF(wt,ep.y);
                if (i==0){
                    linePath.moveTo(sp.x,sp.y);
                    shadowPath.moveTo(sp.x,sp.y);
                }
                linePath.cubicTo(p1.x,p1.y,p2.x,p2.y,ep.x,ep.y);
                shadowPath.cubicTo(p1.x,p1.y,p2.x,p2.y,ep.x,ep.y);

            }
        }else if (lineType == LINE_TYPE_POLYLINE){//设置折线的path
            for (int i = 0; i < datas.size()-1; i++) {
                float svalue = datas.get(i) ;
                float evalue = datas.get(i+1) ;
                PointF sp=new PointF(xlenghtScale*i+ypx,ypy-svalue*ylenghtScale );
                PointF ep=new PointF(xlenghtScale*(i+1)+ypx,ypy-evalue*ylenghtScale );
                if (i==0){
                    linePath.moveTo(sp.x,sp.y);
                    shadowPath.moveTo(sp.x,sp.y);
                }
                linePath.lineTo(ep.x,ep.y);
                shadowPath.lineTo(ep.x,ep.y);
            }
        }else{
            throw new RuntimeException("don't fuck blind set type");
        }

        //加渐变色
        int dataCount=0;
        if (isCustomWeekShowXData){
            if (datas.size()<7){
                dataCount=7-datas.size();
            }
        }
        Paint shadowPaint =new Paint();
        shadowPaint.setShader(new LinearGradient(0,0,0,height,transitionStartColor
                ,transitionEndColor, Shader.TileMode.CLAMP));
        shadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        shadowPath.lineTo(width-leftDataWidth-distanceLRSideWidth-dataCount*xlenghtScale, ypy);
        shadowPath.lineTo(ypx, ypy);
        canvas.drawPath(shadowPath,shadowPaint);

        //画曲线
        canvas.drawPath(linePath,linePaint);

        //y轴
        float lrScale=(lypy-linePaddingTop)/(leftData.size()-1);
        for (int i = 0; i < leftData.size(); i++) {
            xyPaint.setPathEffect(null);
            if (i==0)
                canvas.drawLine(lypx,lypy,width,lypy,xyPaint);
            else if (i == (leftData.size()-1)){
//                canvas.drawLine(lypx,linePaddingTop,width,linePaddingTop,xyPaint);
            }else{
                Path xlinePath=new Path();
                xlinePath.moveTo(lypx,i*lrScale+linePaddingTop);
                xlinePath.lineTo(width,i*lrScale+linePaddingTop);
                DashPathEffect effect=new DashPathEffect(new float[]{10,10},0);
                xyPaint.setPathEffect(effect);
                canvas.drawPath(xlinePath,xyPaint);
            }

            float y=lypy-i*lrScale-textDistanceBottomLineSize;
            float x=width-textPaint.measureText(leftData.get(i));
            textPaint.setColor(text_color);
            canvas.drawText(leftData.get(i),x,y,textPaint);
        }

        //x轴
        int bottomCount = bottomDatas.size();
        float bottomScale=(width-leftDataWidth-distanceLRSideWidth*2)/(bottomCount-1);
        for (int i = 0; i < bottomCount; i++) {
            MyQulineBottomBean myQulineBottomBean = bottomDatas.get(i);
            if (myQulineBottomBean.isHighlighted())
                textPaint.setColor(0xffffffff);
            else
                textPaint.setColor(text_color);
            String content = myQulineBottomBean.getContent();
            canvas.drawText(content,i*bottomScale+distanceLRSideWidth-textPaint.measureText(content)/2
                    ,lypy+lineDistanceBottomTextSize,textPaint);
        }

        if (showPoint)
            //画点
            drawPoint(xlenghtScale,ylenghtScale,ypx,ypy,canvas);

    }

    /**
     * 根据比例和原点画出每个值对应的点
     * @param xlenghtScale
     * @param ylenghtScale
     * @param ypx
     * @param ypy
     * @param canvas
     */
    private void drawPoint(float xlenghtScale, float ylenghtScale, float ypx, float ypy, Canvas canvas) {
        for (int i = 0; i < datas.size(); i++) {
            float svalue = datas.get(i) - minValue;
            PointF sp=new PointF(xlenghtScale*i+ypx,ypy-svalue*ylenghtScale);
            canvas.drawCircle(sp.x,sp.y,pointSize,pointPaint);
        }
    }

    /**
     * 设置曲线或者折线 显示
     * @param lineType
     */
    public void setType(int lineType){
        this.lineType =lineType;
        invalidate();
    }

    public void setLeftData(List<String> leftData){
        if (leftData==null || leftData.size()<=0){
            throw new NullPointerException("leftData is null or size is 0");
        }
        this.leftData = leftData;
        leftDataWidth = 0;
        for (int i = 0; i < leftData.size(); i++) {
            float mwidth = textPaint.measureText(leftData.get(i));
            leftDataWidth = leftDataWidth <mwidth?mwidth: leftDataWidth;
        }
        invalidate();
    }

    public void setBottomData(List<MyQulineBottomBean> bottomDatas){
        if (bottomDatas ==null || bottomDatas.size()<=0){
            throw new NullPointerException("bottomData is null or size is 0");
        }
        this.bottomDatas = bottomDatas;
        invalidate();
    }

    public void setData(List<Float> datas){
        if (datas ==null || datas.size()<=0){
            throw new NullPointerException("bottomData is null or size is 0");
        }
        this.datas = datas;
        maxValue =0;
        minValue = datas.get(0);
        for (int i = 0; i < datas.size(); i++) {
            float mv = datas.get(i);
            maxValue = Math.max(mv,maxValue);
            minValue = Math.min(mv,minValue);
        }
        invalidate();
    }

    public boolean isNoData() {
        if (leftData == null || leftData.size()<=0
                || datas ==null || datas.size()<=0
                || bottomDatas == null || bottomDatas.size() <=0)
            return true;
        return false;
    }



    public static class MyQulineBottomBean{
        private String content;
        private boolean isHighlighted;

        public MyQulineBottomBean() {
        }

        public MyQulineBottomBean(String content) {
            this.content = content;
        }

        public MyQulineBottomBean(String content, boolean isHighlighted) {
            this.content = content;
            this.isHighlighted = isHighlighted;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isHighlighted() {
            return isHighlighted;
        }

        public void setHighlighted(boolean highlighted) {
            isHighlighted = highlighted;
        }
    }
}
