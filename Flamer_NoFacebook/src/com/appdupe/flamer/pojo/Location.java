package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class Location 
{
	@SerializedName("id")
	private long locatinid;
	@SerializedName("name")
	private String name;
	
	public long getLocatinid() 
	{
		return locatinid;
	}
	public void setLocatinid(long locatinid) 
	{
		this.locatinid = locatinid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
}
