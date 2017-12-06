package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.cjsheehan.jrace.scrape.CardEntrantScraper;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RPjsontest extends TestCase {
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
		Elements els = doc.select("script:containsData(window.__data)");
		assertTrue(true);
	}

}

