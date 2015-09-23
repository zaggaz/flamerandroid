package com.appdupe.flamer.pojo;

import android.graphics.Bitmap;

public class ListviewAlubumData {
	private String alubumName;
	private int PhotoCount;

	public int getPhotoCount() {
		return PhotoCount;
	}

	public void setPhotoCount(int photoCount) {
		PhotoCount = photoCount;
	}

	private String pickUrl;

	public String getPickUrl() {
		return pickUrl;
	}

	public void setPickUrl(String pickUrl) {
		this.pickUrl = pickUrl;
	}

	private Bitmap mBitmap;
	private String alubumid;

	public String getAlubumid() {
		return alubumid;
	}

	public void setAlubumid(String alubumid) {
		this.alubumid = alubumid;
	}

	public String getAlubumName() {
		return alubumName;
	}

	public void setAlubumName(String alubumName) {
		this.alubumName = alubumName;
	}

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

}
