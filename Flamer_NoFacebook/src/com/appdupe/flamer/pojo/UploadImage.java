package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class UploadImage 
{
   
	 @SerializedName("errNum")
	 private int errNum;
	 @SerializedName("profFlag")
	 private int profFlag;
	 
	 
	 public int getProfFlag() {
		return profFlag;
	}
	public void setProfFlag(int profFlag) {
		this.profFlag = profFlag;
	}
	@SerializedName("errFlag")
	private int errFlag;
	
	// @SerializedName("errorMassage")
	@SerializedName("errMsg")
	private String errorMassage;
	 @SerializedName("picURL")
	private String picURL;
	 @SerializedName("images")
	 private ArrayList<UploadeImageUrl>images;
	 
	 public ArrayList<UploadeImageUrl> getImages() {
		return images;
	}
	public void setImages(ArrayList<UploadeImageUrl> images) {
		this.images = images;
	}
	public int getErrNum()
	 {
		return errNum;
	}
	public void setErrNum(int errNum) 
	{
		this.errNum = errNum;
	}
	public int getErrFlag() 
	{
		return errFlag;
	}
	public void setErrFlag(int errFlag) 
	{
		this.errFlag = errFlag;
	}
	public String getErrorMassage() 
	{
		return errorMassage;
	}
	public void setErrorMassage(String errorMassage) 
	{
		this.errorMassage = errorMassage;
	}
	public String getPicURL() 
	{
		return picURL;
	}
	public void setPicURL(String picURL) 
	{
		this.picURL = picURL;
	}
	
	


}
