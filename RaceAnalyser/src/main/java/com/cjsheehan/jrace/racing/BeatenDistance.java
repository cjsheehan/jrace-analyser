package com.cjsheehan.jrace.racing;

public class BeatenDistance {
	double distanceToNext;
	double distanceToFirst;

	/**
	 * @param distanceToNext
	 * @param distanceToFirst
	 */
	public BeatenDistance(double distanceToNext, double distanceToFirst) {
		super();
		this.distanceToNext = distanceToNext;
		this.distanceToFirst = distanceToFirst;
	}

	/**
	 * @return the distanceToNext
	 */
	public double getDistanceToNext() {
		return distanceToNext;
	}

	/**
	 * @return the distanceToFirst
	 */
	public double getDistanceToFirst() {
		return distanceToFirst;
	}
}
