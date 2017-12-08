package com.cjsheehan.jrace.racing.model;

public enum Sex {
	GELDING, UNKNOWN;

	public static Sex fromString(String text) {
		if (text.matches("g")) {
			return GELDING;
		} else {
			return UNKNOWN;
		}
	}
}
