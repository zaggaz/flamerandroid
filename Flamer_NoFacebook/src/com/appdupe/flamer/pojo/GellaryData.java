package com.appdupe.flamer.pojo;

import android.graphics.Bitmap;

public class GellaryData {
	private String imageUrl;
	private Bitmap mBitmap;
	private String interestedName;

	public String getInterestedName() {
		return interestedName;
	}

	public void setInterestedName(String interestedName) {
		this.interestedName = interestedName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

}
