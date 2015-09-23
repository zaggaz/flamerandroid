package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class FQLSecondResult 
{
	@SerializedName("photo_count")
	private int photoCount;
	@SerializedName("cover_pid")
    private String  coverPickid;
	@SerializedName("name")
    private  String name;
	@SerializedName("aid")
    private String alubumId;
	
	
	public int getPhotoCount() {
		return photoCount;
	}
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}
	public String getCoverPickid() 
	{
		return coverPickid;
	}
	public void setCoverPickid(String coverPickid) 
	{
		this.coverPickid = coverPickid;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getAlubumId() 
	{
		return alubumId;
	}
	public void setAlubumId(String alubumId) 
	{
		this.alubumId = alubumId;
	}
    
}
