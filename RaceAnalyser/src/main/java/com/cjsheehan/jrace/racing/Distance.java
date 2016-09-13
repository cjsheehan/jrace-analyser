package com.cjsheehan.jrace.racing;

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
