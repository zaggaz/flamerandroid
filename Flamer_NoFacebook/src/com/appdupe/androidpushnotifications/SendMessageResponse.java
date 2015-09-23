package com.appdupe.androidpushnotifications;

import com.google.gson.annotations.SerializedName;

public class SendMessageResponse {

	/* "errNum":"46","errFlag":"1","errMsg":"Unable to send push" */

	@SerializedName("errNum")
	private int errNum;
	@SerializedName("errMsg")
	private String statusMessage;

	@SerializedName("errFlag")
	private int statusNumber;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusNumber() {
		return statusNumber;
	}

	public void setStatusNumber(int statusNumber) {
		this.statusNumber = statusNumber;
	}

}
