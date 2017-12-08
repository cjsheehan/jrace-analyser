package com.cjsheehan.jrace.racing.model;

public enum RideStatus {
	RUNNER, NON_RUNNER, UNKNOWN;

	public static RideStatus fromString(String text) {
		if(text.matches("(?i)RUNNER")) {
			return RUNNER;
		} else if(text.matches("(?i)NONRUNNER")) {
			return NON_RUNNER;
		}
		return UNKNOWN;
	
	}
}
