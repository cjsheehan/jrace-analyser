package com.cjsheehan.jrace.racing;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DistanceTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public DistanceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DistanceTest.class);
	}

	public void testDistance_isInitialised() {
		String distance = "1m1f100y";
		Distance d = new Distance("1m1f100y");
		assertEquals(2080.0, d.getYards());
		assertEquals(distance, d.getDistance());
	}
}