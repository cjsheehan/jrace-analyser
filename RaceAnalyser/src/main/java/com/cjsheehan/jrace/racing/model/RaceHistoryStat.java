package com.cjsheehan.jrace.racing.model;

public enum RaceHistoryStat {
	BEATEN_FAVOURITE("BeatenFavourite"), COURSE_DISTANCE("CourseDistance"), COURSE("Course");

	private String raceHistoryStat;

	RaceHistoryStat(String raceHistoryStat) {
		this.raceHistoryStat = raceHistoryStat;
	}

	public String getRaceHistoryStat() {
		return raceHistoryStat;
	}
}
