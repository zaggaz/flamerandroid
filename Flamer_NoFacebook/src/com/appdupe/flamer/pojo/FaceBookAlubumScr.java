package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class FaceBookAlubumScr
{
	@SerializedName("src_big")
   private String pickUrl;

public String getPickUrl() {
	return pickUrl;
}

public void setPickUrl(String pickUrl) {
	this.pickUrl = pickUrl;
}
   
}
