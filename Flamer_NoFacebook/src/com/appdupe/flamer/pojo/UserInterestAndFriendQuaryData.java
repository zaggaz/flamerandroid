package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UserInterestAndFriendQuaryData {

	@SerializedName("fql_result_set1")
	private ArrayList<QuaryOneResult> interestList;
	@SerializedName("fql_result_set")
	private ArrayList<QuarySecondResult> FriendList;

	public ArrayList<QuaryOneResult> getInterestList() {
		return interestList;
	}

	public void setInterestList(ArrayList<QuaryOneResult> interestList) {
		this.interestList = interestList;
	}

	public ArrayList<QuarySecondResult> getFriendList() {
		return FriendList;
	}

	public void setFriendList(ArrayList<QuarySecondResult> friendList) {
		FriendList = friendList;
	}

}
