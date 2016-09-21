package com.cjsheehan.jrace.racing;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConvertDistanceTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public ConvertDistanceTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(ConvertDistanceTest.class);
    }

    public void testCalculateYards_FromMilesFurlongsYards() {
	String distance = "1m1f100y";
	assertEquals(2080.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromMilesFurlongs() {
	String distance = "1m2f";
	assertEquals(2200.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromMilesYards() {
	String distance = "2m400y";
	assertEquals(3920.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromMiles() {
	String distance = "3m";
	assertEquals(5280.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromFurlongsYards() {
	String distance = "5f110y";
	assertEquals(1210.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromFurlongs() {
	String distance = "5f110y";
	assertEquals(1210.0, ConvertDistance.toYards(distance));
    }

    public void testCalculateYards_FromYards() {
	String distance = "120y";
	assertEquals(120.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateBeatenYards_FromWholeOnly() {
	String distance = "22";
	assertEquals(22.0, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromWholeAndFraction() {
	String distance = "12¾";
	assertEquals(12.75, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromFractionOnly() {
	String distance = "½";
	assertEquals(0.5, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromDeadHeat() {
	String distance = "dh";
	assertEquals(0.0, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromNose() {
	String distance = "nse";
	assertEquals(0.01, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromShortHead() {
	String distance = "shd";
	assertEquals(0.1, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromHead() {
	String distance = "hd";
	assertEquals(0.2, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromNeck() {
	String distance = "nk";
	assertEquals(0.3, ConvertDistance.beatenToYards(distance));
    }
    
    public void testCalculateBeatenYards_FromDistant() {
	String distance = "dis";
	assertEquals(50.0, ConvertDistance.beatenToYards(distance));
    }


}