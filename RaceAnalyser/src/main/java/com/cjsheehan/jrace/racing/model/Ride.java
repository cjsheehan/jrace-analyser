package com.cjsheehan.jrace.racing.model;

import java.util.List;

import com.cjsheehan.jrace.racing.Weight;

public class Ride {
	private long id;
	private int clothNumber;
	private int drawNumber;
	private int finishPosition;
	private String distanceToNext;
	private FinishDistance finishDistance;
	private RideStatus rideStatus;
	private Horse horse;
	private Weight carriedWeight;
	private List<Headgear> headgear;
	private int officialRating;
	private Trainer trainer;
	private Jockey jockey;
	private Casualty casualty;
	private String rideDescription;
	// BETTING
	private String commentary;
	private List<RaceHistoryStat> raceHistoryStats;

}
