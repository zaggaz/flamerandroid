package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FindMatchData {

	@SerializedName("matches")
	private ArrayList<MatchesData> matches;

	public ArrayList<MatchesData> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<MatchesData> matches) {
		this.matches = matches;
	}

	@SerializedName("errMsg")
	private String erorrMassage;

	@SerializedName("errNum")
	private int errNum;

	@SerializedName("errFlag")
	private int errFlag;

	public String getErorrMassage() {
		return erorrMassage;
	}

	public void setErorrMassage(String erorrMassage) {
		this.erorrMassage = erorrMassage;
	}

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

}
