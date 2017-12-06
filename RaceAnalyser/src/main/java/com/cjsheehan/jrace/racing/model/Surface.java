package com.cjsheehan.jrace.racing.model;

public enum Surface {
	TURF("TURF"), UNKNOWN("UNKNOWN");

	private String surface;

	Surface(String surface) {
		this.surface = surface;
	}

	public static Surface fromString(String text) {
		for (Surface b : Surface.values()) {
			if (b.surface.equalsIgnoreCase(text)) {
				return b;
			}
		}

		return UNKNOWN;
	}

}
