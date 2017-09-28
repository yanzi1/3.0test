package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.me.fanyin.zbme.R;

/**
 * 统一图片加载
 * Created by mayunfei on 17-6-16.
 */

public class GlideIUtils {
    private GlideIUtils() {
    }

    /**
     * 商城的统一加载图片
     */
    public static void mallImageLoad(Context context, String url, ImageView imageView) {

        Glide.with(context)                             //配置上下文
                .load(url)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.img_default_course_book)           //设置错误图片
                .placeholder(R.mipmap.img_default_course_book)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存全尺寸
                .skipMemoryCache(true)
                .into(imageView);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .error(R.mipmap.img_default_detail)           //设置错误图片
                .placeholder(R.mipmap.img_default_detail)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存全尺寸
                .skipMemoryCache(true)
                .crossFade()
                .into(imageView);
    }
    
    //首页的缓存机制
    public static void mainCommenGlid(Context context, String url, ImageView imageView,int defaultImage) {

        Glide.with(context)                             //配置上下文
                .load(url)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(defaultImage)           //设置错误图片
                .placeholder(defaultImage)     //设置占位图片
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存全尺寸
                .into(imageView)
                ;
    }

}
