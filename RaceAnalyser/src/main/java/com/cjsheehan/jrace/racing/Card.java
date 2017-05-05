package com.cjsheehan.jrace.racing;

import java.util.Date;

public class Card {
	private int raceId;
	private Date date;
	private String course;
	private String raceUrl;
	private int numRunners;
	private Distance distance;
	private String going;
	private Prize winPrize;
	private String grade;
	private String conditions;
	private String title;
	
	/**
	 * @return the raceId
	 */
	public int getRaceId() {
		return raceId;
	}
	/**
	 * @param raceId the raceId to set
	 */
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}
	/**
	 * @return the raceUrl
	 */
	public String getRaceUrl() {
		return raceUrl;
	}
	/**
	 * @param raceUrl the raceUrl to set
	 */
	public void setRaceUrl(String raceUrl) {
		this.raceUrl = raceUrl;
	}
	/**
	 * @return the numRunners
	 */
	public int getNumRunners() {
		return numRunners;
	}
	/**
	 * @param numRunners the numRunners to set
	 */
	public void setNumRunners(int numRunners) {
		this.numRunners = numRunners;
	}
	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	/**
	 * @return the going
	 */
	public String getGoing() {
		return going;
	}
	/**
	 * @param going the going to set
	 */
	public void setGoing(String going) {
		this.going = going;
	}
	/**
	 * @return the winPrize
	 */
	public Prize getWinPrize() {
		return winPrize;
	}
	/**
	 * @param winPrize the winPrize to set
	 */
	public void setWinPrize(Prize winPrize) {
		this.winPrize = winPrize;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
