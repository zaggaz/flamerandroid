package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class AlubumListData
{	
	@SerializedName("data")
    private  ArrayList<FaceBookAlubumData>  facebookArrayList;
	
	public ArrayList<FaceBookAlubumData> getFacebookArrayList() 
	{
		return facebookArrayList;
	}
	
	public void setFacebookArrayList(ArrayList<FaceBookAlubumData> facebookArrayList) 
	{
		this.facebookArrayList = facebookArrayList;
	}
}
