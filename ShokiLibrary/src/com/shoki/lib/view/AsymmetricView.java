package com.shoki.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shokilibrary.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.shoki.lib.util.ShokiLibBitmapManager;
import com.shoki.lib.util.ShokiLibImageManager;
import com.shoki.lib.util.ShokiLibImageManager.setOnImageLoaderListener;

public class AsymmetricView extends RelativeLayout {

	private boolean mRoundFlag = false;
	private Bitmap mRoundBitmap = null;
	private boolean loadingYn1 = false;
	private boolean loadingYn2 = false;
	private boolean loadingYn3 = false;
	private boolean loadingYn4 = false;
	private ImageView i1;
	private ImageView i2;
	private ImageView i3;
	private ImageView i4;

	public AsymmetricView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View.inflate(context, R.layout.layout_asymmetric, this);
		this.setDrawingCacheEnabled(true);
		ShokiLibImageManager.getInstance().init(context);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 32;
		ShokiLibImageManager.getInstance().setOptions(android.R.color.black, 0, 0, options);
	}

	public AsymmetricView(Context context) {
		super(context);
		View.inflate(context, R.layout.layout_asymmetric, this);
		this.setDrawingCacheEnabled(true);
		ShokiLibImageManager.getInstance().init(context);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 32;
		ShokiLibImageManager.getInstance().setOptions(android.R.color.black, 0, 0, options);
	}

	public void loaderImage(String uri) {
		ImageView img = (ImageView) findViewById(R.id.round_cover_image);
		ShokiLibImageManager.getInstance().displayLoader(uri, img, new setOnImageLoaderListener() {
			@Override
			public void onImageLoadingStarted(String uri, View view) {
			}
			
			@Override
			public void onImageLoadingFailed(String uri, View view,
					FailReason failReason) {
			}
			
			@Override
			public void onImageLoadingComplete(String uri, View view, Bitmap bitmap) {
			}
			
			@Override
			public void onImageLoadingCancelled(String uri, View view) {
			}
		});
	}

	public void setRoundView() {
//		if(loadingYn1 && loadingYn2 && loadingYn3 && loadingYn4) {
//			
//			measure(MeasureSpec.makeMeasureSpec(300, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(300, MeasureSpec.EXACTLY));
//			layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
//			setDrawingCacheEnabled(true);
//			buildDrawingCache();
//			mRoundBitmap = getDrawingCache();
//			ImageView img = (ImageView) findViewById(R.id.round_cover_image);
//			img.setImageBitmap(ShokiLibBitmapManager.getInstance().roundedBitmap(mRoundBitmap));
//			i1.setVisibility(View.INVISIBLE);
//			i2.setVisibility(View.INVISIBLE);
//			i3.setVisibility(View.INVISIBLE);
//			i4.setVisibility(View.INVISIBLE);
//		}

	}
}
