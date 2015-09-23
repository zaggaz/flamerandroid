package com.appdupe.flamer.pojo;

import android.graphics.Bitmap;

public class LikeMatcheddataForListview {

	private String userName;
	private String facebookid;
	private String imageUrl;
	private Bitmap mBitmap;
	private String filePath;
	private String ladt;
	private String flag;

	public String getladt() {
		return ladt;
	}

	public void setladt(String ladt) {
		this.ladt = ladt;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFacebookid() {
		return facebookid;
	}

	public void setFacebookid(String facebookid) {
		this.facebookid = facebookid;
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

	/**
	 * override because of friend list display at right drawer required to be
	 * filter and adapter class is MatchedDataAdapter extedns ArrayAdapter ,so
	 * it would be easy one to use model class toString method
	 */
	@Override
	public String toString() {
		return userName;
	}

}
