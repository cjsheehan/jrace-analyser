package com.cjsheehan.jrace.racing;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CDBF {

	private boolean wonAtCourse;
	private boolean wonAtDistance;
	private boolean wonAtCourseAndDistance;
	private boolean beatenFavourite;

	/**
	 * @param wonAtCourse
	 * @param wonAtDistance
	 * @param wonAtCourseAndDistance
	 * @param beatenFavourite
	 */
	public CDBF(boolean wonAtCourse, boolean wonAtDistance, boolean wonAtCourseAndDistance, boolean beatenFavourite) {
		super();
		this.wonAtCourse = wonAtCourse;
		this.wonAtDistance = wonAtDistance;
		this.wonAtCourseAndDistance = wonAtCourseAndDistance;
		this.beatenFavourite = beatenFavourite;
	}

	/**
	 * @return the wonAtCourse
	 */
	public boolean hasWonAtCourse() {
		return wonAtCourse;
	}

	/**
	 * @param wonAtCourse
	 *            the wonAtCourse to set
	 */
	public void setWonAtCourse(boolean wonAtCourse) {
		this.wonAtCourse = wonAtCourse;
	}

	/**
	 * @return the wonAtDistance
	 */
	public boolean hasWonAtDistance() {
		return wonAtDistance;
	}

	/**
	 * @param wonAtDistance
	 *            the wonAtDistance to set
	 */
	public void setWonAtDistance(boolean wonAtDistance) {
		this.wonAtDistance = wonAtDistance;
	}

	/**
	 * @return the wonAtCourseAndDistance
	 */
	public boolean hasWonAtCourseAndDistance() {
		return wonAtCourseAndDistance;
	}

	/**
	 * @param wonAtCourseAndDistance
	 *            the wonAtCourseAndDistance to set
	 */
	public void setWonAtCourseAndDistance(boolean wonAtCourseAndDistance) {
		this.wonAtCourseAndDistance = wonAtCourseAndDistance;
	}

	/**
	 * @return the beatenFavourite
	 */
	public boolean isBeatenFavourite() {
		return beatenFavourite;
	}

	/**
	 * @param beatenFavourite
	 *            the beatenFavourite to set
	 */
	public void setBeatenFavourite(boolean beatenFavourite) {
		this.beatenFavourite = beatenFavourite;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Distance winner", hasWonAtDistance())
				.append("Course winner", hasWonAtCourse()).append("Course&Distance winner", hasWonAtCourseAndDistance())
				.append("Beaten favourite", isBeatenFavourite()).toString();
	}

}
