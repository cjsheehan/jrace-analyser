package com.cjsheehan.jrace.racing.model;

public enum Headgear {
	BLINKERS, TONGUE_STRAP, CHEEK_PIECES, HOOD;

	public static Headgear fromString(String text) {
		if (text.matches("(?i)Hood")) {
			return HOOD;
		} else if (text.matches("(?i)Tongue strap")) {
			return TONGUE_STRAP;
		} else if (text.matches("(?i)Blinkers")) {
			return BLINKERS;
		} else if (text.matches("(?i)Cheek pieces")) {
			return TONGUE_STRAP;
		} else {
			return null;
		}
	}
}
