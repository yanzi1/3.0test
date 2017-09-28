/*
 *
 *  * Copyright (C) 2015 by  xunice@qq.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *
 */

package com.me.core.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类，对图片进行一些处理
 * 
 * @author xunice
 * 
 */
public class ImageUtils {

	/**
	 * convert Bitmap to byte array
	 *
	 * @param b
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * convert byte array to Bitmap
	 *
	 * @param b
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory
				.decodeByteArray(b, 0, b.length);
	}

	/**
	 * convert Drawable to Bitmap
	 *
	 * @param d
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	/**
	 * convert Bitmap to Drawable
	 *
	 * @param b
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * convert Drawable to byte array
	 *
	 * @param d
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * convert byte array to Drawable
	 *
	 * @param b
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	/**
	 * 图片旋转
	 *
	 * @param bmp
	 * @param degree
	 * @return
	 */
	public static Bitmap postRotateBitamp(Bitmap bmp, float degree) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
                matrix, true);
		return resizeBmp;
	}

	/**
	 * 图片放大缩小
	 *
	 * @param bmp
	 * @param sx
	 * @return
	 */
	public static Bitmap postScaleBitamp(Bitmap bmp, float sx, float sy) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// System.out.println("before+w+h:::::::::::"+bmpWidth+","+bmpHeight);
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setScale(sx, sy);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
                matrix, true);
		// System.out.println("after+w+h:::::::::::"+resizeBmp.getWidth()+","+resizeBmp.getHeight());
		return resizeBmp;
	}

	/**
	 * 图片 亮度调整
	 *
	 * @param bm
	 *            huevalue亮度调整黑白
	 * @param hueValue
	 * @param lumValue
	 * @return
	 */
	public static Bitmap postColorRotateBitamp(int hueValue, int lumValue,
                                               Bitmap bm) {
		// 获得Bitmap的高和宽
		// System.out.println(bm.getWidth()+","+bm.getHeight()+"------before");
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
		// 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
		Canvas canvas = new Canvas(bmp); // 得到画笔对象
		Paint paint = new Paint(); // 新建paint
		paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

		// 产生resize后的Bitmap对象
		ColorMatrix mAllMatrix = new ColorMatrix();
		ColorMatrix mLightnessMatrix = new ColorMatrix();
		ColorMatrix mSaturationMatrix = new ColorMatrix();
		ColorMatrix mHueMatrix = new ColorMatrix();

		float mHueValue = hueValue * 1.0F / 127; // 亮度
		mHueMatrix.reset();
		mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考

		float mSaturationValue = 127 * 1.0F / 127;// 饱和度
		mSaturationMatrix.reset();
		mSaturationMatrix.setSaturation(mSaturationValue);

		float mLumValue = (lumValue - 127) * 1.0F / 127 * 180; // 色相
		mLightnessMatrix.reset(); // 设为默认值
		mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度

		mAllMatrix.reset();
		mAllMatrix.postConcat(mHueMatrix);
		mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
		mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

		paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
		canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
		// System.out.println(bmp.getWidth()+","+bmp.getHeight()+"------after");
		return bmp;
	}

	/**
	 * 读取资源图片
	 *
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 获取一个120x120的图片
	 *
	 * @param path
	 * @return
	 */
	public static Bitmap parseHeadBitmapToLittle(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);

		int mWidth = 120;

		int max = options.outWidth > options.outHeight ? options.outWidth
				: options.outHeight;
		if (max > mWidth) {
			options.inSampleSize = max / mWidth;
			options.outWidth = 120;
			options.outHeight = 120;

		} else {
			options.inSampleSize = max / mWidth;
			options.outWidth = 120;
			options.outHeight = 120;
		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;
		return getBitmapByPath(path, options);
	}

	/**
	 * 获取bitmap通过路径
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 根据Uri获取文件的路径
	 *
	 * @Title: getUriString
	 * @param uri
	 * @return
	 * @return String
	 * @date 2012-11-28 下午1:19:31
	 */
	public static String getUriString(Uri uri, ContentResolver cr) {
		String imgPath = null;
		if (uri != null) {
			String uriString = uri.toString();
			// 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
			// 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
			// 所以就不能通过query来查询，否则获取的cursor会为null。
			if (uriString.startsWith("file")) {
				// uri的格式为file:///mnt....,将前七个过滤掉获取路径
				imgPath = uriString.substring(7, uriString.length());
				return imgPath;
			}
			Cursor cursor = cr.query(uri, null, null, null, null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1); // 图片文件路径

		}
		return imgPath;
	}

	/**
	 * 写图片文件到SD卡
	 *
	 * @throws java.io.IOException
	 */
	public static void saveImageToSD(String filePath, Bitmap bitmap) {
		try {
			if (bitmap != null) {
				FileOutputStream fos = new FileOutputStream(filePath);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 100, stream);
				byte[] bytes = stream.toByteArray();
				fos.write(bytes);
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
