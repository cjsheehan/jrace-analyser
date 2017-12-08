package com.cjsheehan.jrace.racing.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.ConvertDistance;

@Component
@Scope("prototype")
public class Distance {
	private String distance;
	private double yards;

	public Distance(String distance) {
		super();
		this.distance = distance;
		setYards(distance);
	}

	public String getDistance() {
		return distance;
	}

	public double getYards() {
		return yards;
	}

	private void setYards(String distance) {
		yards = ConvertDistance.toYards(distance);
	}

	@Override
	public String toString() {
		return "Actual: " + getDistance() + ", Yards: " + getYards();
	}
}
