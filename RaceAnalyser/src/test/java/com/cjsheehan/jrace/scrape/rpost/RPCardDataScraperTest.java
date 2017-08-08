package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.business.JSoupLoader;
import com.cjsheehan.jrace.racing.CardEntrant;
import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Weight;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.cjsheehan.jrace.scrape.CardEntrantScraper;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RPCardDataScraperTest extends TestCase {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static boolean setUpIsDone = false;
	private static Document doc;
	private static int id = 672154;

	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docLoader;

	@Autowired
	@Qualifier("cardDataScraper")
	private RaceDataScraper cdScraper;

	@Autowired
	private CardEntrantScraper eScraper;

	
	@Before
	public void setUp() throws IOException {
		if (setUpIsDone) {
			return;
		}

		// do the setup
		doc = docLoader.load("card_race_id_" + id + ".html");
		setUpIsDone = true;
	}
	
	@Test
	public void raceIdIsScraped() throws ScrapeException {
		int expected = id;
		int actual = cdScraper.scrapeRaceId(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void courseNameIsScraped() throws ScrapeException {
		final String expected = "Perth";
		String actual = cdScraper.scrapeCourse(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void dateIsScraped() throws ScrapeException, ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("hh:mm, dd MMMM yyyy");
		Date expected = fmt.parse("14:00, 27 APRIL 2017");
		Date actual = cdScraper.scrapeDate(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void prizeIsScraped() throws ScrapeException, ParseException {
		Prize actual = cdScraper.scrapePrize(doc);
		assertEquals(3798.0, actual.getValue());
		assertEquals(Currency.GBP, actual.getCurrency());
	}
	
	@Test
	public void numRunnersIsScraped() throws ScrapeException {
		int expected = 24;
		int actual = cdScraper.scrapeNumRunners(doc);
		assertEquals(expected, actual);
	}
	
	@Test
	public void distanceIsScraped() throws ScrapeException {
		String expected = "2m47y";
		String actual = cdScraper.scrapeDistance(doc).getDistance();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void goingIsScraped() throws ScrapeException {
		String expected = "Good";
		String actual = cdScraper.scrapeGoing(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void titleIsScraped() throws ScrapeException {
		String expected = "Murrayshall Hotel And Golf Courses Novices' Hurdle";
		String actual = cdScraper.scrapeTitle(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void gradeIsScraped() throws ScrapeException {
		String expected = "Class 4";
		String actual = cdScraper.scrapeGrade(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void ageIsScraped() throws ScrapeException {
		String expected = "4yo+";
		String actual = cdScraper.scrapeAges(doc);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void enrtantsAreScraped() throws ScrapeException {
		// TODO : validate full entrant list
		CardEntrant actual = eScraper.scrapeEntrants(doc).get(0);
		assertNotNull(actual);
		assertEquals("Excellent Team", actual.getHorse().getName());
		assertEquals(850951, actual.getHorse().getId());
		assertEquals(5, actual.getAge());
		assertEquals(1, actual.getSaddleNo());
		assertTrue(EqualsBuilder.reflectionEquals(new Weight(11, 12), actual.getWeight()));
		assertEquals(0, actual.getDraw());
		assertTrue(EqualsBuilder.reflectionEquals(new Rating(127, 133, 110, -1), actual.getRating()));
		assertEquals(11, actual.getLastRan());
		assertEquals("Harry Skelton", actual.getJockey().getName());
		assertEquals(85218, actual.getJockey().getId());
		assertEquals("Dan Skelton", actual.getTrainer().getName());
		assertEquals(16270, actual.getTrainer().getId());
		assertEquals(3, actual.getWeightClaim());
	}
}
	
