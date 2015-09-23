package com.appdupe.androidpushnotifications;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ChatMessageData {

	/*
	 * "errNum": "47", "errFlag": "0", "errMsg": "Got chat history!", "chat": [
	 * { "mid": "123", "sname": "Niraj", "sfid": "100000877715757", "msg":
	 * "dfhfdf", "dt": "2013-12-05 07:49:26" }
	 */

	@SerializedName("chat")
	private List<ChatMessageList> listChat;

	@SerializedName("errMsg")
	private String errMsg;

	@SerializedName("errFlag")
	private int errFlag;

	@SerializedName("errNum")
	private int errNum;

	public List<ChatMessageList> getListChat() {
		return listChat;
	}

	public void setListChat(List<ChatMessageList> listChat) {
		this.listChat = listChat;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getErrFlag() {
		return errFlag;
	}

	public void setErrFlag(int errFlag) {
		this.errFlag = errFlag;
	}

	public int getErrNum() {
		return errNum;
	}

	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}

}
