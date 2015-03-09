package com.shoki.lib.util.vo;

import java.io.Serializable;

import android.graphics.Bitmap;

public class TaskVO implements Serializable {

	private long bucketImageId;
	private String bucketData;
	private int requestPosition;
	
	
	
	
	
	public int getRequestPosition() {
		return requestPosition;
	}
	public void setRequestPosition(int requestPosition) {
		this.requestPosition = requestPosition;
	}
	public long getBucketImageId() {
		return bucketImageId;
	}
	public void setBucketImageId(long bucketImageId) {
		this.bucketImageId = bucketImageId;
	}
	public String getBucketData() {
		return bucketData;
	}
	public void setBucketData(String bucketData) {
		this.bucketData = bucketData;
	}
}
