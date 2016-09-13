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

    /**
     * @param date
     * @param track
     * @param numRunners
     * @param distance
     * @param going
     * @param prizes
     * @param winPrize
     */
    public Race(Date date, String track, int numRunners, Distance distance, String going, 
	    Prize winPrize) {
	super();
	this.date = date;
	this.track = track;
	this.numRunners = numRunners;
	this.distance = distance;
	this.going = going;
	this.winPrize = winPrize;
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

    @Override
    public String toString() {
	return "Track: " + getTrack() + ", Date: " + getDate() + ", Prize: " + getWinPrize() + ", Runners: "
		+ getNumRunners() + ", Distance: " + distance.toString() + ", Going: " + getGoing();
    }

}
