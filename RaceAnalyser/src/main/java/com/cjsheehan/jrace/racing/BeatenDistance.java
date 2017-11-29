package com.cjsheehan.jrace.racing;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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
