package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UserInterest 
{
	@SerializedName("data")
       private ArrayList<InterestData>data;

	public ArrayList<InterestData> getData() {
		return data;
	}

	public void setData(ArrayList<InterestData> data) {
		this.data = data;
	}
	
}
