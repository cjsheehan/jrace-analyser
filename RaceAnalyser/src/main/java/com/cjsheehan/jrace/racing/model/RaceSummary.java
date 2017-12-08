package com.cjsheehan.jrace.racing.model;

import java.time.Duration;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RaceSummary {
	private long id;
	private String name;
	private String courseName;
	private Surface courseSurface;
	private String age;
	private int raceClass;
	private Distance distance;
	private String date;
	private String time;
	private Duration winningTime;
	private int rideCount;
	private RaceStage raceStage;
	private Going going;
	private boolean handicap;
	private String verdict;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the courseSurface
	 */
	public Surface getCourseSurface() {
		return courseSurface;
	}

	/**
	 * @param courseSurface
	 *            the courseSurface to set
	 */
	public void setCourseSurface(Surface courseSurface) {
		this.courseSurface = courseSurface;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the raceClass
	 */
	public int getRaceClass() {
		return raceClass;
	}

	/**
	 * @param raceClass
	 *            the raceClass to set
	 */
	public void setRaceClass(int raceClass) {
		this.raceClass = raceClass;
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
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the winningTime
	 */
	public Duration getWinningTime() {
		return winningTime;
	}

	/**
	 * @param winningTime
	 *            the winningTime to set
	 */
	public void setWinningTime(Duration winningTime) {
		this.winningTime = winningTime;
	}

	/**
	 * @return the rideCount
	 */
	public int getRideCount() {
		return rideCount;
	}

	/**
	 * @param rideCount
	 *            the rideCount to set
	 */
	public void setRideCount(int rideCount) {
		this.rideCount = rideCount;
	}

	/**
	 * @return the raceStage
	 */
	public RaceStage getRaceStage() {
		return raceStage;
	}

	/**
	 * @param raceStage
	 *            the raceStage to set
	 */
	public void setRaceStage(RaceStage raceStage) {
		this.raceStage = raceStage;
	}

	/**
	 * @return the going
	 */
	public Going getGoing() {
		return going;
	}

	/**
	 * @param going
	 *            the going to set
	 */
	public void setGoing(Going going) {
		this.going = going;
	}



	/**
	 * @return the verdict
	 */
	public String getVerdict() {
		return verdict;
	}

	/**
	 * @param verdict
	 *            the verdict to set
	 */
	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}

	/**
	 * @return the handicap
	 */
	public boolean hasHandicap() {
		return handicap;
	}

	/**
	 * @param handicap
	 *            the handicap to set
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}

	/**
	 * @return the handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
