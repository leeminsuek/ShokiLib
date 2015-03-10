package com.shoki.lib.util;

import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

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
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.threadPoolSize(3)
				.memoryCache(new LruMemoryCache(50 * 1024 * 1024))
				.memoryCacheSize(50 * 1024 * 1024)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		
		
		
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getOptions() {
		return mOption;
	}


	/**
	 * 옵션설정
	 * @param loding
	 * @param empty
	 * @param error
	 * @param options
	 */
	public void setOptions(int loding, int empty, int error, BitmapFactory.Options options) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(0)
//		.displayer(new FadeInBitmapDisplayer(200))
		.showImageOnLoading(loding)
		.showImageForEmptyUri(empty)
		.showImageOnFail(error)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.imageScaleType (ImageScaleType.EXACTLY)
		.considerExifParams(true) //roate잡아주는옵션
		.decodingOptions(options)
		.build();
	}
	
	/**
	 * 옵션설정
	 * @param loding
	 * @param empty
	 * @param error
	 * @param options
	 */
	public void setOptions(BitmapFactory.Options options) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(0)
//		.displayer(new FadeInBitmapDisplayer(200))
		.decodingOptions(options)
		.build();
	}

	/**
	 * 옵션 설정
	 * @param loding
	 * @param empty
	 * @param error
	 */
	public void setOptions(int loding, int empty, int error) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(0)
		.displayer(new FadeInBitmapDisplayer(600))
		.showImageOnLoading(loding)
		.showImageForEmptyUri(empty)
		.showImageOnFail(error)
		.build();
	}
	
	
	public void clearOption() {
		mOption = null;
	}
	
	public void resume() {
		ImageLoader.getInstance().resume();
	}

	/**
	 * 옵션 설정
	 * @param loding
	 * @param empty
	 * @param error
	 */
	public void setOptions(Drawable loding, Drawable empty, Drawable error) {
		mOption = new DisplayImageOptions.Builder()
		.delayBeforeLoading(0)
//		.displayer(new FadeInBitmapDisplayer(200))
		.showImageOnLoading(loding)
		.showImageForEmptyUri(empty)
		.showImageOnFail(error)
		.build();
	}
	
	public DisplayImageOptions getOption() {
		return mOption;
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
