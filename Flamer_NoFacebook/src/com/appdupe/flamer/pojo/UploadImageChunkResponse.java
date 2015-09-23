package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UploadImageChunkResponse 
{
	 @SerializedName("errNum")
     private int   errNum;
	 @SerializedName("errFlag")
     private int errFlag;
	 @SerializedName("errMsg")
	 private  String errMsg;
	 @SerializedName("picURL")
     private String picURL;
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public int getErrFlag() {
		return errFlag;
	}
	public void setErrFlag(int errFlag) {
		this.errFlag = errFlag;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
}
