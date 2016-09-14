package com.cjsheehan.jrace.racing;

import java.util.Date;
import java.util.List;

/**
 * @author Chris
 *
 */
public class Race {
    private Date date;
    private String track;
    private int numRunners;
    private Distance distance;
    private String going;
    private List<Double> prizes;
    private Prize winPrize;
    private String grade;
    private String conditions;
    private String title;

    /**
     * @param date
     * @param track
     * @param numRunners
     * @param distance
     * @param going
     * @param prizes
     * @param winPrize
     * @param grade
     * @param conditions
     * @param title
     */
    public Race(Date date, String track, int numRunners, Distance distance, String going, List<Double> prizes,
	    Prize winPrize, String grade, String conditions, String title) {
	super();
	this.date = date;
	this.track = track;
	this.numRunners = numRunners;
	this.distance = distance;
	this.going = going;
	this.prizes = prizes;
	this.winPrize = winPrize;
	this.setGrade(grade);
	this.setConditions(conditions);
	this.setTitle(title);
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @return the track
     */
    public String getTrack() {
	return track;
    }

    /**
     * @return the numRunners
     */
    public int getNumRunners() {
	return numRunners;
    }

    /**
     * @return the distance
     */
    public Distance getDistance() {
	return distance;
    }

    /**
     * @return the going
     */
    public String getGoing() {
	return going;
    }

    /**
     * @return the prizes
     */
    public List<Double> getPrizes() {
	return prizes;
    }

    /**
     * @return the winPrize
     */
    public Prize getWinPrize() {
	return winPrize;
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

    @Override
    public String toString() {
	return "Track: " + getTrack() + ", Date: " + getDate() + ", Prize: " + getWinPrize() + ", Runners: "
		+ getNumRunners() + ", Distance: " + distance.toString() + ", Going: " + getGoing()
		+ ", Grade: " + getGrade() + ", Conditions: " + getConditions() + ", Title: " + getTitle();
    }

}
