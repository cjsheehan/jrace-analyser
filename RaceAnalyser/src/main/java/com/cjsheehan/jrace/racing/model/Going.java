package com.cjsheehan.jrace.racing.model;

public enum Going {
	HARD, FIRM, GOOD_TO_FIRM, GOOD, GOOD_TO_SOFT, SOFT, SOFT_TO_HEAVY, HEAVY, STD, YIELDING, UNKNOWN;

	public static Going fromString(String input) {
		input = input.replaceFirst(" *\\(.+", "");

		if (input.matches("(?i)good to soft")) {
			return GOOD_TO_SOFT;
		} else if (input.matches("(?i)soft to heavy")) {
			return SOFT_TO_HEAVY;
		} else if (input.matches("(?i)good to firm")) {
			return GOOD_TO_FIRM;
		} else if (input.matches("(?i)good")) {
			return GOOD;
		} else if (input.matches("(?i)firm")) {
			return FIRM;
		} else if (input.matches("(?i)hard")) {
			return HARD;
		} else if (input.matches("(?i)heavy")) {
			return HEAVY;
		} else if (input.matches("(?i)standard")) {
			return STD;
		} else if (input.matches("(?i)yielding")) {
			return YIELDING;
		} else {
			return UNKNOWN;
		}
	}
}