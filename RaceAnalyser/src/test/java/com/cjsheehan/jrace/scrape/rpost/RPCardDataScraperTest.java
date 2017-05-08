package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.business.JSoupLoader;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.cjsheehan.jrace.scrape.CardDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RPCardDataScraperTest extends TestCase {
	
	private static boolean setUpIsDone = false;
	private static Document doc;
	private static int raceId = 6672154;
	
	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docLoader;
	
	@Autowired
	private CardDataScraper cds;

	@Before
	public void setUp() throws IOException {
	    if (setUpIsDone) {
	        return;
	    }
	       
	    // do the setup
	    String fName = "card_race_id_" + raceId + ".html";
	    doc = docLoader.load(fName);
	    setUpIsDone = true;
	}

	@Test
	public void raceIdIsScraped() throws ScrapeException {
		int expected = raceId;
		int actual = cds.scrapeRaceId(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void courseNameIsScraped() throws ScrapeException {
		final String expected = "Perth";
		String actual = cds.scrapeCourse(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void dateIsScraped() throws ScrapeException, ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm, dd MMMM yyyy");
		Date expected = fmt.parse("14:00, 27 APRIL 2017");
		Date actual = cds.scrapeDate(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void prizeIsScraped() throws ScrapeException, ParseException {
		double expected = 3798.0;
		double actual = cds.scrapePrize(doc);
		assertEquals(expected, actual);
	}
	
	@Test
	public void numRunnersIsScraped() throws ScrapeException {
		int expected = 24;
		int actual = cds.scrapeNumRunners(doc);
		assertEquals(expected, actual);
	}
	
	@Test
	public void distanceIsScraped() throws ScrapeException {
		String expected = "2m47y";
		String actual = cds.scrapeDistance(doc).getDistance();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void goingIsScraped() throws ScrapeException {
		String expected = "Good";
		String actual = cds.scrapeGoing(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void titleIsScraped() throws ScrapeException {
		String expected = "Murrayshall Hotel And Golf Courses Novices' Hurdle";
		String actual = cds.scrapeTitle(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void gradeIsScraped() throws ScrapeException {
		String expected = "Class 4";
		String actual = cds.scrapeGrade(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void ageIsScraped() throws ScrapeException {
		String expected = "4yo+";
		String actual = cds.scrapeAges(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
}
