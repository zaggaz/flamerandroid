package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UserLikesData 
{
	@SerializedName("id")
   private long liksId;
	@SerializedName("name")
   private String likesName;
	@SerializedName("category")
   private String category;

	public long getLiksId() 
	{
	return liksId;
    }
public void setLiksId(long liksId) 
{
	this.liksId = liksId;
}
public String getLikesName() {
	return likesName;
}
public void setLikesName(String likesName) 
{
	this.likesName = likesName;
}
public String getCategory() 
{
	return category;
}
public void setCategory(String category) 
{
	this.category = category;
}

  
}
