package com.shoki.lib.util;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.ImageView;

import com.shoki.lib.log.Loger;
import com.shoki.lib.util.vo.TaskVO;

/**
 * 
 * 갤러리 구현시 썸네일 이미지 로딩/스크롤시 task cancle
 * @author 이민석
 * 
 *
 */
public class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
	private final WeakReference imageViewReference;
//	private long data = 0;
	private Context mContext;
	private int rotate;
	private TaskVO vo;
	
	public interface resultBitmap {
		public void getLoadingBitmap(Bitmap bitmap, int id);
	}
	
	private resultBitmap mBitmapCallback;
	public BitmapWorkerTask(ImageView imageView, Context context, resultBitmap listener) {
		// Use a WeakReference to ensure the ImageView can be garbage collected
		mContext = context;
		imageViewReference = new WeakReference(imageView);
		mBitmapCallback = listener;
	}

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(Object... params) {
		vo = (TaskVO) params[0];
		rotate = ShokiLibBitmapManager.getInstance().GetExifOrientation(vo.getBucketData());
//		Bitmap objBitmap  = MediaStore.Images.Thumbnails.getThumbnail(
//				mContext.getContentResolver(), vo.getBucketImageId(),
//				MediaStore.Images.Thumbnails.MINI_KIND, null);
		Bitmap objBitmap = null;
		if(objBitmap != null) 
			return objBitmap;
		else {
			String path = "";
			
			Cursor mini = Images.Thumbnails.queryMiniThumbnail(mContext.getContentResolver(), vo.getBucketImageId(), Images.Thumbnails.MINI_KIND, null);
			if(mini != null && mini.moveToFirst()) {
				path = mini.getString(mini.getColumnIndex(Images.Thumbnails.DATA));
			}

			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;
//			BitmapFactory.decodeFile(path, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			if(options.outWidth > 96) {
				int ws = options.outWidth / 96 + 1;
				if(ws > options.inSampleSize) {
					options.inSampleSize = ws;
				}
			}
			if(options.outHeight > 96) {
				int hs = options.outHeight / 96 + 1;
				if(hs > options.inSampleSize) {
					options.inSampleSize = hs;
				}
			}
			
			mini.close();
			
			return BitmapFactory.decodeFile(path, options);
		}
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		final ImageView imageView = (ImageView) imageViewReference.get();
		if(cancelPotentialWork(vo.getRequestPosition(), imageView)) { 
			bitmap = null;
		}
		if (imageViewReference != null && bitmap != null) {
			if (imageView != null) {
				Bitmap reBitmap = ShokiLibBitmapManager.getInstance().rotate(bitmap, rotate);
				imageView.setImageBitmap(reBitmap);
				mBitmapCallback.getLoadingBitmap(reBitmap, vo.getRequestPosition());
			}
		}
	}
	
	public static boolean cancelPotentialWork(int data, ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			if(bitmapWorkerTask.vo == null) 
				return false;
			final int bitmapData = bitmapWorkerTask.vo.getRequestPosition();
			if (bitmapData != data) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was cancelled
		return true;
	}

	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}
}