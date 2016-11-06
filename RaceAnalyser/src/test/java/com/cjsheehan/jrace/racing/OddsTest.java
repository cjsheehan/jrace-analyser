package com.cjsheehan.jrace.racing;

import java.util.Date;

import org.junit.Test;

import junit.framework.TestCase;

public class OddsTest extends TestCase  {

	@Test
	public void testGetImpliedProbabilityMatchesFraction() {
		Odds odds = new Odds(new Date(), 1, 2);
		assertEquals(0.5, odds.getImpliedProbability());
	}


}
