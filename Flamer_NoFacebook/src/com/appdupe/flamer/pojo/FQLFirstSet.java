package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class FQLFirstSet 
{
	@SerializedName("src")
    private  String pickUrl;
	@SerializedName("pid")
    private String  picId;
	public String getPickUrl() 
	{
		return pickUrl;
	}
	public void setPickUrl(String pickUrl) 
	{
		this.pickUrl = pickUrl;
	}
	public String getPicId() 
	{
		return picId;
	}
	public void setPicId(String picId) 
	{
		this.picId = picId;
	}
    
}
