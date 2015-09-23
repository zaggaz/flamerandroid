package com.appdupe.flamer.model;

public class PreferenceModel {
	private int prefLowerAge, prefRadius, prefSex, prefUpperAge, sex, errFlag;

	public int getErrFlag() {
		return errFlag;
	}

	public void setErrFlag(int errFlag) {
		this.errFlag = errFlag;
	}

	public int getPrefLowerAge() {
		return prefLowerAge;
	}

	public void setPrefLowerAge(int prefLowerAge) {
		this.prefLowerAge = prefLowerAge;
	}

	public int getPrefRadius() {
		return prefRadius;
	}

	public void setPrefRadius(int prefRadius) {
		this.prefRadius = prefRadius;
	}

	public int getPrefSex() {
		return prefSex;
	}

	public void setPrefSex(int prefSex) {
		this.prefSex = prefSex;
	}

	public int getPrefUpperAge() {
		return prefUpperAge;
	}

	public void setPrefUpperAge(int prefUpperAge) {
		this.prefUpperAge = prefUpperAge;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}
