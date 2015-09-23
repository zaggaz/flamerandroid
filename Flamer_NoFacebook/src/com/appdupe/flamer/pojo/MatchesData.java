package com.appdupe.flamer.pojo;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class MatchesData {
	@SerializedName("imgCnt")
	private int imgCnt;

	@SerializedName("matchPercentage")
	private int matchPercent;

	public int getMatchPercent() {
		return matchPercent;
	}

	public void setMatchPercent(int matchPercent) {
		this.matchPercent = matchPercent;
	}

	public int getImgCnt() {
		return imgCnt;
	}

	public void setImgCnt(int imgCnt) {
		this.imgCnt = imgCnt;
	}

	private Bitmap mBitmap;

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

	@SerializedName("fbId")
	private String fbId;
	@SerializedName("firstName")
	private String firstName;

	@SerializedName("ladt")
	private String ladt;
	@SerializedName("pPic")
	private String pPic;
	@SerializedName("sex")
	private int sex;
	@SerializedName("persDesc")
	private String persDesc;
	@SerializedName("age")
	private int age;
	@SerializedName("sharedLikes")
	private int sharedLikes;

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLadt() {
		return ladt;
	}

	public void setLadt(String ladt) {
		this.ladt = ladt;
	}

	public String getpPic() {
		return pPic;
	}

	public void setpPic(String pPic) {
		this.pPic = pPic;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPersDesc() {
		return persDesc;
	}

	public void setPersDesc(String persDesc) {
		this.persDesc = persDesc;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSharedLikes() {
		return sharedLikes;
	}

	public void setSharedLikes(int sharedLikes) {
		this.sharedLikes = sharedLikes;
	}

}
