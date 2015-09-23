package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UserfriendData 
{
	@SerializedName("data")
    private ArrayList<UserFriendsData>data;

	public ArrayList<UserFriendsData> getData() {
		return data;
	}

	public void setData(ArrayList<UserFriendsData> data) {
		this.data = data;
	}
}
