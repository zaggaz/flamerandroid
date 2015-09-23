package com.appdupe.flamer.pojo;

public class ImageDetail {
	private int coulumid;

	public int getCoulumid() {
		return coulumid;
	}

	public void setCoulumid(int coulumid) {
		this.coulumid = coulumid;
	}

	private String userFacebookid;
	private String imageUrl;
	private String sdcardpath;
	private int imageOrder;

	public String getUserFacebookid() {
		return userFacebookid;
	}

	public void setUserFacebookid(String userFacebookid) {
		this.userFacebookid = userFacebookid;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSdcardpath() {
		return sdcardpath;
	}

	public void setSdcardpath(String sdcardpath) {
		this.sdcardpath = sdcardpath;
	}

	public int getImageOrder() {
		return imageOrder;
	}

	public void setImageOrder(int imageOrder) {
		this.imageOrder = imageOrder;
	}

}
