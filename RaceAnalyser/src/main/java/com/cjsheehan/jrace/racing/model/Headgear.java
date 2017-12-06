package com.cjsheehan.jrace.racing.model;

public enum Headgear {
	P("Cheek pieces"), T("Tongue strap");

	private String headgear;

	Headgear(String headgear) {
		this.headgear = headgear;
	}

	/**
	 * @return the headgear
	 */
	public String getHeadgear() {
		return headgear;
	}
}
