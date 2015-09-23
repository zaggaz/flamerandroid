package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class InviteActionData 
{
 
	 @SerializedName("errNum")
	private int errNum;
	 @SerializedName("errFlag")
	private int errFlag;
	 @SerializedName("errMsg")
	private String  errMsg;
	 @SerializedName("uName")
	 private String uName;
	 
	 @SerializedName("uFbId")
	 private String uFbId;
	 
	 @SerializedName("pPic")
	 private String pPic;
	 
	 public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuFbId() {
		return uFbId;
	}
	public void setuFbId(String uFbId) {
		this.uFbId = uFbId;
	}
	public String getpPic() {
		return pPic;
	}
	public void setpPic(String pPic) {
		this.pPic = pPic;
	}
	public String getPushNum() {
		return pushNum;
	}
	public void setPushNum(String pushNum) {
		this.pushNum = pushNum;
	}
	@SerializedName("pushNum")
	 private String pushNum;
	 
	 
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
	public String getErrMsg() 
	{
		return errMsg;
	}
	public void setErrMsg(String errMsg) 
	{
		this.errMsg = errMsg;
	}
	 
	
	
}
