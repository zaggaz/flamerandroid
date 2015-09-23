package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FaceBookAlubumData 
{
	
	@SerializedName("fql_result_set1")
    private ArrayList<FQLFirstSet> pickList;
	@SerializedName("fql_result_set")
    private ArrayList<FQLSecondResult>alubumnamList;
	public ArrayList<FQLFirstSet> getPickList() {
		return pickList;
	}
	public void setPickList(ArrayList<FQLFirstSet> pickList) 
	{
		this.pickList = pickList;
	}
	public ArrayList<FQLSecondResult> getAlubumnamList() 
	{
		return alubumnamList;
	}
	public void setAlubumnamList(ArrayList<FQLSecondResult> alubumnamList) 
	{
		this.alubumnamList = alubumnamList;
	}
    
}
