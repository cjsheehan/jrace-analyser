package com.cjsheehan.jrace.racing.model;

public enum Surface {
	TURF, UNKNOWN;

	public static Surface fromString(String text) {
		if (text.matches("TURF")) {
			return TURF;
		} else {
			return UNKNOWN;
		}
	}

}
