package com.appdupe.flamer.pojo;

import java.io.File;

import android.graphics.Bitmap;

public class GridViewData {
     
	private Bitmap bitmap;
	private String picUrl;
	private File filePath;
	public File getFilePath() {
		return filePath;
	}
	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
