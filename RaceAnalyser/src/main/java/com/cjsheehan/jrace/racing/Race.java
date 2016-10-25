package com.cjsheehan.jrace.racing;

import java.util.Date;
import java.util.List;

/**
 * @author Chris
 *
 */
public class Race {
	private Date date;
	private String course;
	private int numRunners;
	private Distance distance;
	private String going;
	private List<Double> prizes;
	private Prize winPrize;
	private String grade;
	private String conditions;
	private String title;
	private List<Entry> entrants;

	/**
	 * @param date
	 * @param course
	 * @param numRunners
	 * @param distance
	 * @param going
	 * @param prizes
	 * @param winPrize
	 * @param grade
	 * @param conditions
	 * @param title
	 */
	public Race(Date date, String course, int numRunners, Distance distance, String going, List<Double> prizes,
			Prize winPrize, String grade, String conditions, String title, List<Entry> entrants) {
		super();
		setDate(date);
		setCourse(course);
		setNumRunners(numRunners);
		setDistance(distance);
		setGoing(going);
		setPrizes(prizes);
		setWinPrize(winPrize);
		setGrade(grade);
		setConditions(conditions);
		setTitle(title);
		setEntrants(entrants);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
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
	 * @param course
	 *            the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * @return the numRunners
	 */
	public int getNumRunners() {
		return numRunners;
	}

	/**
	 * @param numRunners
	 *            the numRunners to set
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
	 * @param distance
	 *            the distance to set
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
	 * @param going
	 *            the going to set
	 */
	public void setGoing(String going) {
		this.going = going;
	}

	/**
	 * @return the prizes
	 */
	public List<Double> getPrizes() {
		return prizes;
	}

	/**
	 * @param prizes
	 *            the prizes to set
	 */
	public void setPrizes(List<Double> prizes) {
		this.prizes = prizes;
	}

	/**
	 * @return the winPrize
	 */
	public Prize getWinPrize() {
		return winPrize;
	}

	/**
	 * @param winPrize
	 *            the winPrize to set
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
	 * @param grade
	 *            the grade to set
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
	 * @param conditions
	 *            the conditions to set
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
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the entrants
	 */
	public List<Entry> getEntrants() {
		return entrants;
	}

	/**
	 * @param entrants
	 *            the entrants to set
	 */
	public void setEntrants(List<Entry> entrants) {
		this.entrants = entrants;
	}

	@Override
	public String toString() {
		return "Course: " + getCourse() + ", Date: " + getDate() + ", Prize: " + getWinPrize() + ", Runners: "
				+ getNumRunners() + ", Distance: " + getDistance().toString() + ", Going: " + getGoing() + ", Grade: "
				+ getGrade() + ", Conditions: " + getConditions() + ", Title: " + getTitle();
	}

}
