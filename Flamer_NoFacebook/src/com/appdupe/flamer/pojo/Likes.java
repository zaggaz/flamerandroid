package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class Likes 
{
	@SerializedName("fbId")
   private String fbId;
	@SerializedName("fName")
   private String fName;
	@SerializedName("ladt")
   private String ladt;
	@SerializedName("pPic")
   private String pPic;
	@SerializedName("flag")
   private int flag;
public String getFbId() {
	return fbId;
}
public void setFbId(String fbId) {
	this.fbId = fbId;
}
public String getfName() {
	return fName;
}
public void setfName(String fName) {
	this.fName = fName;
}
public String getLadt() {
	return ladt;
}
public void setLadt(String ladt) {
	this.ladt = ladt;
}
public String getpPic() {
	return pPic;
}
public void setpPic(String pPic) {
	this.pPic = pPic;
}
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
   
   
   
}
