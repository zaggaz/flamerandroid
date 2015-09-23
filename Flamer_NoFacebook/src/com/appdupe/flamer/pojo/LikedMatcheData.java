package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class LikedMatcheData 
{
	@SerializedName("errNum")
   private int errNum;
	@SerializedName("errFlag")
   private int errFlag;
	@SerializedName("errMsg")
   private String errMsg;
	@SerializedName("likes")
   private ArrayList<Likes>likes;
   
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
public ArrayList<Likes> getLikes() {
	return likes;
}
public void setLikes(ArrayList<Likes> likes) {
	this.likes = likes;
}
   
   
}









