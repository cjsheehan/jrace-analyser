package com.cjsheehan.jrace.racing;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WeightTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public WeightTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WeightTest.class);
	}

	public void testWeight_stAndLbsGt0_isConvertedToLbs() {
		String weight = "10-4";
		assertEquals(144, Weight.toLbs(weight));
	}

	public void testWeight_stEq0_isConvertedToLbs() {
		String weight = "0-4";
		assertEquals(4, Weight.toLbs(weight));
	}

	public void testWeight_lbsEq0_isConvertedToLbs() {
		String weight = "3-0";
		assertEquals(42, Weight.toLbs(weight));
	}

	public void testWeight_lbsEq0_isConvertedToStAndLbs() {
		int lbs = 0;
		assertEquals("0-0", Weight.toStAndLbs(lbs));
	}

	public void testWeight_lbsGt0Lt14_isConvertedToStAndLbs() {
		int lbs = 13;
		assertEquals("0-13", Weight.toStAndLbs(lbs));
	}

	public void testWeight_lbsGt14_isConvertedToStAndLbs() {
		int lbs = 16;
		assertEquals("1-2", Weight.toStAndLbs(lbs));
	}
}