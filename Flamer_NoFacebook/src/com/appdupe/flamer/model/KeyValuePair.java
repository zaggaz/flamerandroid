package com.appdupe.flamer.model;

import org.apache.http.NameValuePair;

public class KeyValuePair implements NameValuePair {

	private String name, value;

	public KeyValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
}
