package com.cjsheehan.jrace.racing.model;

public enum RaceStage {
	WEIGHEDIN, DORMANT, UNKNOWN;

	public static RaceStage fromString(String text) {
		for (RaceStage b : RaceStage.values()) {
			if (b.toString().equalsIgnoreCase(text)) {
				return b;
			}
		}

		return UNKNOWN;
	}
}
