package com.shoki.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.shokilibrary.R;

public class AsymmetricView extends RelativeLayout {
	
	private boolean mRoundFlag = false;
	private Bitmap mRoundBitmap = null;
	
	public AsymmetricView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AsymmetricView(Context context) {
		super(context);
		View.inflate(context, R.layout.layout_asymmetric, this);
		this.setDrawingCacheEnabled(true);
	}
	
	
	public void setRoundView(Bitmap bitmap) {
		buildDrawingCache();
		mRoundBitmap = getDrawingCache();
		
		
	}
}
