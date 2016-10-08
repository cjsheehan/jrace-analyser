package com.cjsheehan.jrace.racing;

public enum Going {
	HARD, FIRM, GOOD_TO_FIRM, GOOD, GOOD_TO_SOFT, SOFT, SOFT_TO_HEAVY, HEAVY, STD, YIELDING;

	public static Going toGoing(String input) {
		// TODO : parse string for appropriate going
		return Going.FIRM;
	}
}