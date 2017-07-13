package com.cjsheehan.jrace.scrape.rpost;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.business.JSoupLoader;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.Weight;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.cjsheehan.jrace.scrape.ResultEntrantDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RPResultEntrantDataScraperTest {

	private static boolean setUpIsDone = false;
	private static Document doc;
	private static Element entrant;
	private static int raceId = 672154;
	private static final double DELTA = 1e-15;
	ApplicationContext context;

	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docLoader;

	@Autowired
	private ResultEntrantDataScraper reds;

	@Before
	public void setUp() throws IOException {
		if (setUpIsDone) {
			return;
		}

		// do the setup
		String fName = "result_race_id_" + raceId + ".html";
		doc = docLoader.load(fName);
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").first();
		setUpIsDone = true;
	}

	@Test
	public void positionIsScraped() throws ScrapeException {
		String actual = reds.scrapePosition(entrant);
		String expected = "1";
		assertEquals(expected, actual);
	}

	@Test
	public void beatenDistanceToFirstIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").get(2);
		double actual = reds.scrapeBeatenDistanceToFirst(entrant);
		assertEquals(11.75, actual, DELTA);
	}

	@Test
	public void beatenDistanceToNextIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").get(2);
		double actual = reds.scrapeBeatenDistanceToNext(entrant);
		assertEquals(8, actual, DELTA);
	}

	@Test
	public void priceIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").get(2);
		String actual = reds.scrapePrice(entrant);
		assertEquals("40/1", actual);
	}

	@Test
	public void commentIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__commentRow").get(2);
		String actual = reds.scrapeComments(entrant);
		assertEquals("Prominent, effort and pushed along before 2 out, soon one pace (op 50/1)", actual);
	}

	@Test
	public void ageIsScraped() throws ScrapeException {
		int expected = 5;
		int actual = reds.scrapeAge(entrant);
		assertEquals(expected, actual);
	}

	@Test
	public void horseIsScraped() throws ScrapeException {
		Horse expected = new Horse("Ramonex", 850951L);
		Horse actual = reds.scrapeHorse(entrant);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void jockeyIsScraped() throws ScrapeException {
		Jockey expected = new Jockey("Harry Skelton", 85218L);
		Jockey actual = reds.scrapeJockey(entrant);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void trainerIsScraped() throws ScrapeException {
		Trainer expected = new Trainer("Dan Skelton", 16270L);
		Trainer actual = reds.scrapeTrainer(entrant);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void weightIsScraped() throws ScrapeException {
		Weight expected = new Weight(11, 12);
		Weight actual = reds.scrapeWeight(entrant);
		assertEquals(expected.getStonesComponent(), actual.getStonesComponent());
		assertEquals(expected.getLbsComponent(), actual.getLbsComponent());
	}

	@Test
	public void weightAllowanceIsScraped() throws ScrapeException {
		int expected = 3;
		int actual = reds.scrapeWeightAllowance(entrant);
		assertEquals(expected, actual);
	}

	@Test
	public void ratingIsScraped() throws ScrapeException {
		Rating expected = new Rating(127, 133, 110, -1);
		Rating actual = reds.scrapeRating(entrant);
		assertEquals(expected.getOfficialRating(), actual.getOfficialRating());
		assertEquals(expected.getRpRating(), actual.getRpRating());
		assertEquals(expected.getTsRating(), actual.getTsRating());
		assertEquals(expected.getCustomRating(), actual.getCustomRating());
	}

}
