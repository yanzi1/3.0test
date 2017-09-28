package com.me.fanyin.zbme.views.main.fragemnt.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by wyc on 2017/6/20.
 */

public class MyGlideModule implements GlideModule{

	int diskSize = 1024 * 1024 * 100;
	int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
//	int memorySize = 50*1024*1024;  // 取1/8最大内存作为最大缓存

	public MyGlideModule() {
	}

	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		// 定义缓存大小和位置
		builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //内存中
		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); //sd卡中

				// 自定义内存和图片池大小
		builder.setMemoryCache(new LruResourceCache(memorySize));
		builder.setBitmapPool(new LruBitmapPool(memorySize));

		// 定义图片格式
		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
	}

	@Override
	public void registerComponents(Context context, Glide glide) {
//		glide.register(GlideUrl.class,InputStream.class, new Factory());
	}
}
