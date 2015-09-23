package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class QuarySecondResult {
	@SerializedName("name")
	private String friendName;
	@SerializedName("pic_with_logo")
	private String friendPicUlt;

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendPicUlt() {
		return friendPicUlt;
	}

	public void setFriendPicUlt(String friendPicUlt) {
		this.friendPicUlt = friendPicUlt;
	}
}
