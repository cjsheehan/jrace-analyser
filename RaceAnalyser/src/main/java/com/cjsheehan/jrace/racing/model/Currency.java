package com.cjsheehan.jrace.racing.model;

public enum Currency {
	GBP, EUR, USD;
	
	public static Currency fromString(String text) {
		if(text.matches("(?i)GBP")) {
			return GBP;
		} else if (text.matches("(?i)EUR")) {
			return EUR;
		} else if(text.matches("(?i)USD")) {
			return USD;
		} else {
			return GBP;
		}
	}
}
