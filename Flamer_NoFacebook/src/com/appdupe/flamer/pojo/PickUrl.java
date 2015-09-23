package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class PickUrl {
   @SerializedName("src_big")
	  private String src;

public String getSrc() {
	return src;
}

public void setSrc(String src) {
	this.src = src;
}
}
