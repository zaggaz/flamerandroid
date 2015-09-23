package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UserFacebookLikeId 
{
	 @SerializedName("page_id")
   private String object_id;

 public String getObject_id()
  {
	return object_id;
  }

  public void setObject_id(String object_id)
   {
	this.object_id = object_id;
   }
   
}
