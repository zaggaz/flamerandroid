package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UserInterestAndFriendData {
	@SerializedName("data")
	ArrayList<UserInterestAndFriendQuaryData> datalist;

	public ArrayList<UserInterestAndFriendQuaryData> getDatalist() {
		return datalist;
	}

	public void setDatalist(ArrayList<UserInterestAndFriendQuaryData> datalist) {
		this.datalist = datalist;
	}

}
