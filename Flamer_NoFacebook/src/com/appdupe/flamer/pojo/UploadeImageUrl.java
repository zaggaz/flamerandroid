package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UploadeImageUrl
{
	 @SerializedName("url")
      private String ulr;
	 @SerializedName("flag")
      private int flag;
	public String getUlr() {
		return ulr;
	}
	public void setUlr(String ulr) {
		this.ulr = ulr;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
      
      
}
