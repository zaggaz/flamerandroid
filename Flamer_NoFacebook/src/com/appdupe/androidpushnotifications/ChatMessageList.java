package com.appdupe.androidpushnotifications;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ChatMessageList {

	/*
	 * {"errNum":"56","errFlag":"0","errMsg":"Got the message!",
	 * "message":[{"message"
	 * :"fgdd","sFid":"100001583006863","dt":"2013-12-05 05:55:13"}]}
	 */

	/*
	 * "mid": "123", "sname": "Niraj", "sfid": "100000877715757", "msg":
	 * "dfhfdf", "dt": "2013-12-05 07:49:26"
	 */

	@SerializedName("msg")
	private String strMessage;

	@SerializedName("sfid")
	private String strSenderFacebookId;

	@SerializedName("mid")
	private int intMessageId;

	@SerializedName("sname")
	private String strSendername;

	@SerializedName("dt")
	private String strDateTime;

	private String strSenderId;

	private String strReceiverId;

	private String strFlagForMessageSuccess;
	private Bitmap userBitmap;

	public Bitmap getUserBitmap() {
		return userBitmap;
	}

	public void setUserBitmap(Bitmap userBitmap) {
		this.userBitmap = userBitmap;
	}

	public String getStrFlagForMessageSuccess() {
		return strFlagForMessageSuccess;
	}

	public void setStrFlagForMessageSuccess(String strFlagForMessageSuccess) {
		this.strFlagForMessageSuccess = strFlagForMessageSuccess;
	}

	public String getStrSenderId() {
		return strSenderId;
	}

	public void setStrSenderId(String strSenderId) {
		this.strSenderId = strSenderId;
	}

	public String getStrReceiverId() {
		return strReceiverId;
	}

	public void setStrReceiverId(String strReceiverId) {
		this.strReceiverId = strReceiverId;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getStrSenderFacebookId() {
		return strSenderFacebookId;
	}

	public void setStrSenderFacebookId(String strSenderFacebookId) {
		this.strSenderFacebookId = strSenderFacebookId;
	}

	public int getIntMessageId() {
		return intMessageId;
	}

	public void setIntMessageId(int intMessageId) {
		this.intMessageId = intMessageId;
	}

	public String getStrSendername() {
		return strSendername;
	}

	public void setStrSendername(String strSendername) {
		this.strSendername = strSendername;
	}

	public String getStrDateTime() {
		return strDateTime;
	}

	public void setStrDateTime(String strDateTime) {
		this.strDateTime = strDateTime;
	}

}
