package com.shoki.lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ShokiLibImageManager {

	private static ShokiLibImageManager INSTANCE;

	/**
	 * 싱글톤 클래스
	 * @return
	 */
	public static ShokiLibImageManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ShokiLibImageManager();
		}
		return INSTANCE;
	}
	
	/**
	 * 
	 * 콜백리스너
	 * @author 이민석
	 *
	 */
	public interface setOnImageLoaderListener {
		public void onImageLoadingStarted(String uri, View view);
		public void onImageLoadingFailed(String uri, View view, FailReason failReason); 
		public void onImageLoadingComplete(String uri, View view, Bitmap bitmap);
		public void onImageLoadingCancelled(String uri, View view);
	}
	
	private setOnImageLoaderListener mCallbackListener;
	
	private DisplayImageOptions mOption;
	/**
	 * 이미지로더 초기화
	 * @param context
	 */
	public void init(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.threadPoolSize(3)
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		.memoryCacheSize(2 * 1024 * 1024)
		.memoryCacheSizePercentage(3)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs()
		.build();
		ImageLoader.getInstance().init(config);
	}
	
	public DisplayImageOptions getOptions() {
		return mOption;
	}
	
	/**
	 * 옵션 설정
	 * @param loding
	 * @param empty
	 * @param error
	 */
	public void setOptions(int loding, int empty, int error) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(1000)
		.showImageOnLoading(loding)
		.showImageForEmptyUri(empty)
		.showImageOnFail(error)
		.build();
	}
	
	/**
	 * 옵션 설정
	 * @param loding
	 * @param empty
	 * @param error
	 */
	public void setOptions(Drawable loding, Drawable empty, Drawable error) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(1000)
		.showImageOnLoading(loding)
		.showImageForEmptyUri(empty)
		.showImageOnFail(error)
		.build();
	}
	
	
	/**
	 * 이미지 로딩
	 * @param uri
	 * @param imageView
	 * @param listener
	 */
	public void displayLoader(String uri, ImageView imageView, setOnImageLoaderListener listener) {
		
		mCallbackListener = listener;
		
		if(mOption == null) {
			ImageLoader.getInstance().displayImage(uri, imageView, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String uri, View view) {
					mCallbackListener.onImageLoadingStarted(uri, view);
				}
				
				@Override
				public void onLoadingFailed(String uri, View view, FailReason failReason) {
					mCallbackListener.onImageLoadingFailed(uri, view, failReason);
				}
				
				@Override
				public void onLoadingComplete(String uri, View view, Bitmap bitmap) {
					mCallbackListener.onImageLoadingComplete(uri, view, bitmap);
				}
				
				@Override
				public void onLoadingCancelled(String uri, View view) {
					mCallbackListener.onImageLoadingCancelled(uri, view);
				}
			});
		}
		else {
			ImageLoader.getInstance().displayImage(uri, imageView, mOption, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String uri, View view) {
					mCallbackListener.onImageLoadingStarted(uri, view);
				}
				
				@Override
				public void onLoadingFailed(String uri, View view, FailReason failReason) {
					mCallbackListener.onImageLoadingFailed(uri, view, failReason);
				}
				
				@Override
				public void onLoadingComplete(String uri, View view, Bitmap bitmap) {
					mCallbackListener.onImageLoadingComplete(uri, view, bitmap);
				}
				
				@Override
				public void onLoadingCancelled(String uri, View view) {
					mCallbackListener.onImageLoadingCancelled(uri, view);
				}
			});
		}
	}
}
