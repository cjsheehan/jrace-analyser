package com.cjsheehan.jrace.racing.scrape.rpost;

import com.cjsheehan.jrace.scrape.rpost.Going;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class GoingTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public GoingTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(GoingTest.class);
    }

    public void testParse_Good() {
	String input = ") good";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodWithTextBefore() {
	String input = ") test text good";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodWithTextAfter() {
	String input = ") good (test text ";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodWithTextBeforeAndAfter() {
	String input = "(Class 1) (2yo) 5f Good (good to soft in places)";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodWithMixedCase() {
	String input = ") GoOd";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodWithExtraGoingInParentheses() {
	String input = ") good (good to soft in places)";
	assertEquals(Going.GOOD, Going.parse(input));
    }
    
    public void testParse_GoodToSoft() {
	String input = ") good to soft";
	assertEquals(Going.GOOD_TO_SOFT, Going.parse(input));
    }

    public void testParse_Yielding() {
	String input = "Yielding";
	assertEquals(Going.YIELDING, Going.parse(input));
    }
    
    public void testParse_YieldingWithTextBeforeAndAfter() {
	String input = "(4yo) 2m Yielding 10 hdles ";
	assertEquals(Going.YIELDING, Going.parse(input));
    }
    
    public void testParse_YieldingToSoft() {
	String input = ") yielding to soft";
	assertEquals(Going.YIELDING_TO_SOFT, Going.parse(input));
    }
    
    public void testParse_Soft() {
	String input = ") soft";
	assertEquals(Going.SOFT, Going.parse(input));
    }
    
    public void testParse_SoftToHeavy() {
	String input = ") soft to heavy";
	assertEquals(Going.SOFT_TO_HEAVY, Going.parse(input));
    }
}