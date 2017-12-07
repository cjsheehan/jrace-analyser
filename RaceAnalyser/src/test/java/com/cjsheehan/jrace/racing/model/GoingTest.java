package com.cjsheehan.jrace.racing.model;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoingTest {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Test
	public void goingGoodToSoftFromString_thenOK() {
		assertEquals(Going.GOOD_TO_SOFT, Going.fromString("good to soft"));
		assertEquals(Going.GOOD_TO_SOFT, Going.fromString("Good to Soft"));
		assertEquals(Going.GOOD_TO_SOFT, Going.fromString("good to soft (Good n places)"));
	}

	@Test
	public void goingSoftToHeavyFromString_thenOK() {
		assertEquals(Going.SOFT_TO_HEAVY, Going.fromString("soft to heavy"));
		assertEquals(Going.SOFT_TO_HEAVY, Going.fromString("Soft to heavy"));
		assertEquals(Going.SOFT_TO_HEAVY, Going.fromString("soft to heavy (heavy in places)"));
	}

	@Test
	public void goingGoodToFirmFromString_thenOK() {
		assertEquals(Going.GOOD_TO_FIRM, Going.fromString("good to firm"));
		assertEquals(Going.GOOD_TO_FIRM, Going.fromString("Good to firm"));
		assertEquals(Going.GOOD_TO_FIRM, Going.fromString("good to firm (good in places)"));
	}

	@Test
	public void goingGoodFromString_thenOK() {
		assertEquals(Going.GOOD, Going.fromString("good"));
		assertEquals(Going.GOOD, Going.fromString("Good"));
		assertEquals(Going.GOOD, Going.fromString("good (good to firm in places)"));
	}

	@Test
	public void goingFirmFromString_thenOK() {
		assertEquals(Going.FIRM, Going.fromString("firm"));
		assertEquals(Going.FIRM, Going.fromString("Firm"));
		assertEquals(Going.FIRM, Going.fromString("Firm (Good to firm in places)"));
	}

	@Test
	public void goingHardFromString_thenOK() {
		assertEquals(Going.HARD, Going.fromString("hard"));
		assertEquals(Going.HARD, Going.fromString("Hard"));
		assertEquals(Going.HARD, Going.fromString("hard (firm in places)"));
	}

	@Test
	public void goingHeavyFromString_thenOK() {
		assertEquals(Going.HEAVY, Going.fromString("heavy"));
		assertEquals(Going.HEAVY, Going.fromString("Heavy"));
		assertEquals(Going.HEAVY, Going.fromString("heavy (soft in places)"));
	}

	@Test
	public void goingStandardFromString_thenOK() {
		assertEquals(Going.STD, Going.fromString("standard"));
		assertEquals(Going.STD, Going.fromString("Standard"));
		assertEquals(Going.STD, Going.fromString("standard (good in places)"));
	}

	@Test
	public void goingYieldingFromString_thenOK() {
		assertEquals(Going.YIELDING, Going.fromString("yielding"));
		assertEquals(Going.YIELDING, Going.fromString("Yielding"));
		assertEquals(Going.YIELDING, Going.fromString("yielding (soft in places)"));
	}

	@Test
	public void goingUnknownFromString_thenOK() {
		assertEquals(Going.UNKNOWN, Going.fromString("xyz"));
	}

}
