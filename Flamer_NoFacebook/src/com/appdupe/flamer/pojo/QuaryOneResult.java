package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class QuaryOneResult {

	@SerializedName("name")
	private String interestName;

	@SerializedName("pic")
	private String interestPicUlt;

	@SerializedName("pic_with_logo")
	private String interestPicUrl1;

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

	public String getInterestPicUlt() {
		if (interestPicUlt == null) {
			if (interestPicUrl1 != null) {
				return interestPicUrl1;
			}
		}
		return interestPicUlt;
	}

	public void setInterestPicUlt1(String interestPicUrl1) {
		this.interestPicUrl1 = interestPicUrl1;
	}

	public void setInterestPicUlt(String interestPicUlt) {
		this.interestPicUlt = interestPicUlt;
	}

}
