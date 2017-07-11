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
	private ResultEntrantDataScraper scraper;

	@Before
	public void setUp() throws IOException {
		if (setUpIsDone) {
			return;
		}

		// do the setup
		String fName = "result_race_id_" + raceId + ".html";
		doc = docLoader.load(fName);
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").first();
		//body > div.rp-results.rp-container.cf.js-contentWrapper.ng-scope > main > section > div.rp-horseTable > table > tbody > tr:nth-child(5)
		setUpIsDone = true;
	}

	@Test
	public void positionIsScraped() throws ScrapeException {
		String actual = scraper.scrapePosition(entrant);
		String expected = "1";
		assertEquals(expected, actual);
	}

	@Test
	public void beatenDistanceToFirstIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").get(2);
		double actual = scraper.scrapeBeatenDistanceToFirst(entrant);
		assertEquals(11.75, actual, DELTA);
	}

	@Test
	public void beatenDistanceToNextIsScraped() throws ScrapeException {
		entrant = doc.select("div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow").get(2);
		double actual = scraper.scrapeBeatenDistanceToNext(entrant);
		assertEquals(8.00, actual, DELTA);
	}

}
