package com.cjsheehan.jrace.racing.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FinishDistance {
	double distanceToNext;
	double distanceToFirst;

	/**
	 * @param distanceToNext
	 * @param distanceToFirst
	 */
	public FinishDistance(double distanceToNext, double distanceToFirst) {
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
