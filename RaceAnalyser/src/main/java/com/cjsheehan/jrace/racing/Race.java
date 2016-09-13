package com.cjsheehan.jrace.racing;

import java.util.Date;
import java.util.List;

public class Race {
    private Date date;
    private String track;
    private List<Double> prizes;
    private Prize winPrize;

    public Race(Date date, String track, Prize winPrize) {
	super();
	this.date = date;
	this.track = track;
	this.winPrize = winPrize;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public String getTrack() {
	return track;
    }

    public void setTrack(String track) {
	this.track = track;
    }

    public List<Double> getPrizes() {
	return prizes;
    }

    public void setPrizes(List<Double> prizes) {
	this.prizes = prizes;
    }

    public Prize getWinPrize() {
	return winPrize;
    }

    public void setWinPrize(Prize winPrize) {
	this.winPrize = winPrize;
    }

    public String toString() {
	return "Track: " + this.track + ", Date: " + this.date + ", Prize: " + this.winPrize;
    }

}
