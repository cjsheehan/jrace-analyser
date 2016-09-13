package com.cjsheehan.jrace.racing;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConvertDistanceTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConvertDistanceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConvertDistanceTest.class );
    }

    public void testCalculateYards_FromMilesFurlongsYards()
    {
        String distance = "1m1f100y";
        assertEquals(2080.0, ConvertDistance.toYards(distance));
    }
    
    
    public void testCalculateYards_FromMilesFurlongs()
    {
        String distance = "1m2f";
        assertEquals(2200.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateYards_FromMilesYards()
    {
        String distance = "2m400y";
        assertEquals(3920.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateYards_FromMiles()
    {
        String distance = "3m";
        assertEquals(5280.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateYards_FromFurlongsYards()
    {
        String distance = "5f110y";
        assertEquals(1210.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateYards_FromFurlongs()
    {
        String distance = "5f110y";
        assertEquals(1210.0, ConvertDistance.toYards(distance));
    }
    
    public void testCalculateYards_FromYards()
    {
        String distance = "120y";
        assertEquals(120.0, ConvertDistance.toYards(distance));
    }
    
}