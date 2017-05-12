package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.cjsheehan.jrace.scrape.ResultDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RPResultDataScraperTest extends TestCase {

	private static boolean setUpIsDone = false;
	private static Document doc;
	private static int id = 6672154;

	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docLoader;

	@Autowired
	private ResultDataScraper scraper;

	@Before
	public void setUp() throws IOException {
		if (setUpIsDone) {
			return;
		}

		// do the setup
		doc = docLoader.load("result_race_id_" + id + ".html");
		setUpIsDone = true;
	}
	
	@Test
	public void raceIdIsScraped() throws ScrapeException {
		int expected = id;
		int actual = scraper.scrapeRaceId(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void courseNameIsScraped() throws ScrapeException {
		final String expected = "Perth";
		String actual = scraper.scrapeCourse(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void dateIsScraped() throws ScrapeException, ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm, dd MMMM yyyy");
		Date expected = fmt.parse("14:00, 27 APRIL 2017");
		Date actual = scraper.scrapeDate(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void prizeIsScraped() throws ScrapeException, ParseException {
		double expected = 3798.0;
		double actual = scraper.scrapePrize(doc);
		assertEquals(expected, actual);
	}
	
	@Test
	public void numRunnersIsScraped() throws ScrapeException {
		int expected = 24;
		int actual = scraper.scrapeNumRunners(doc);
		assertEquals(expected, actual);
	}
	
	@Test
	public void distanceIsScraped() throws ScrapeException {
		String expected = "2m47y";
		String actual = scraper.scrapeDistance(doc).getDistance();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void goingIsScraped() throws ScrapeException {
		String expected = "Good";
		String actual = scraper.scrapeGoing(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void titleIsScraped() throws ScrapeException {
		String expected = "Murrayshall Hotel And Golf Courses Novices' Hurdle";
		String actual = scraper.scrapeTitle(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void gradeIsScraped() throws ScrapeException {
		String expected = "Class 4";
		String actual = scraper.scrapeGrade(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void ageIsScraped() throws ScrapeException {
		String expected = "4yo+";
		String actual = scraper.scrapeAges(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void winningTimeIsScraped() throws ScrapeException {
		long expected = 233010L;
		long actual = scraper.scrapeWinningTime(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void nonRunnersIsScraped() throws ScrapeException {
		List<String> expected = Arrays.asList("xyz", "abc");
		List<String> actual = scraper.scrapeNonRunners(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
}
	

