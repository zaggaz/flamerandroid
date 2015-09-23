package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginData 
{
	@SerializedName("profilePic")
	private String profilePic;
	@SerializedName("joined")
	private String joined;
	
	public String getJoined() {
		return joined;
	}
	public void setJoined(String joined) {
		this.joined = joined;
	}
	@SerializedName("errMsg")
   private String loginMasseage;
	
	@SerializedName("token")
   private String userToken;
	
	@SerializedName("errNum")
   private int errNum;
	
	@SerializedName("errFlag")
   private int errFlag;
	
	@SerializedName("expiryLocal")
   private String expiryLocal;
	
	@SerializedName("expiryGMT")
   private String expiryGMT ;
	
	@SerializedName("flag")
   private int flag;

	
 public String getLoginMasseage() 
 {
	return loginMasseage;
}
	public void setLoginMasseage(String loginMasseage) 
	{
		this.loginMasseage = loginMasseage;
	}

	public String getUserToken() 
	{
		return userToken;
	}

	public void setUserToken(String userToken) 
	{
		this.userToken = userToken;
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

	public String getExpiryLocal() 
	{
		return expiryLocal;
	}

	public void setExpiryLocal(String expiryLocal) 
	{
		this.expiryLocal = expiryLocal;
	}

	public String getExpiryGMT() 
	{
		return expiryGMT;
	}

	public void setExpiryGMT(String expiryGMT) 
	{
		this.expiryGMT = expiryGMT;
	}

	public int isFlag() 
	{
		return flag;
	}

	public void setFlag(int flag) 
	{
		this.flag = flag;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public int getFlag() {
		return flag;
	}
	
	
   
}
