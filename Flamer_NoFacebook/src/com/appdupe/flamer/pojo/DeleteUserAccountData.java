package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class DeleteUserAccountData {
	@SerializedName("errNum")
	private int errNum;
	@SerializedName("errFlag")
	private int errFlag;
	@SerializedName("errMsg")
	private String errMsg;

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
}
