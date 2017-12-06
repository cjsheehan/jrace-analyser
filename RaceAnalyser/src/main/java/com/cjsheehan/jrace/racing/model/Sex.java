package com.cjsheehan.jrace.racing.model;

public enum Sex {
	GELDING("g");

	private String sex;
	
	Sex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}
}
