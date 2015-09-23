package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class InterestData 
{
	@SerializedName("pic_square")
   private String intestPicurl;
  
	@SerializedName("name")
   private String interesname;
   
   
public String getIntestPicurl() {
	return intestPicurl;
}
public void setIntestPicurl(String intestPicurl) {
	this.intestPicurl = intestPicurl;
}
public String getInteresname() {
	return interesname;
}
public void setInteresname(String interesname) {
	this.interesname = interesname;
}
   
   
}
