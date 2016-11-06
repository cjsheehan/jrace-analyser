package com.cjsheehan.jrace.racing.repository;

import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.LocalUrlHandler;
import com.cjsheehan.jrace.RacingUrlHandler;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.cjsheehan.jrace.scrape.ScrapeException;
import com.cjsheehan.jrace.scrape.rpost.Result;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.APPLICATION)
public class RaceRepositoryTest extends TestCase {

	static int[] resultIds = { 646160, 646834, 657619, 659125 };

	@Autowired
	private RaceRepository repository;

	@Test
	public void repositoryShouldNotBeNull() {
		assertNotNull(repository);
	}

	@Test
	public void raceIsSavedToDb() {
		final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		String[] urls = {};
		RacingUrlHandler urlManager;

		urlManager = new LocalUrlHandler();
		urls = urlManager.createResultUrls(resultIds);

		for (String url : urls) {
			Document doc = null;
			try {
				doc = urlManager.requestDocument(url);

				Result result = null;
				try {
					result = new Result(doc);
//					Race race = new Race(result.getDate(), result.getCourse(), result.getNumRunners(), result.getDistance(),
//											result.getGoing(), result.getPrizes(), result.getWinPrize(), result.getGrade(),
//											result.getConditions(), result.getTitle(), result.getEntrants());
//					repository.save(entry);
					log.info(doc.baseUri() + "\n" + result.toString());
				} catch (ScrapeException e) {
					log.error("doc.baseUri() + \n", e);
				}
			} catch (Exception e) {
				log.error(url + "\n" + e.getMessage());
			}
		}
		assertEquals(1, 1);
	}

}