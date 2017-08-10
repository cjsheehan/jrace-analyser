package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cjsheehan.jrace.racing.Card;
import com.cjsheehan.jrace.scrape.CardScraper;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class RPCardScraper implements CardScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private RaceDataScraper cds;

	@Override
	public Card scrape(Document doc) throws ScrapeException {
		if (doc == null)
			throw new IllegalArgumentException("doc is null");
		Card card = new Card();

		// CRITICAL
		card.setRaceId(cds.scrapeRaceId(doc));
		card.setDate(cds.scrapeDate(doc));
		card.setCourse(cds.scrapeCourse(doc));
		card.setDistance(cds.scrapeDistance(doc));
		// card.setEntrants(cds.scrape));

		// NON-CRITICAL

		try {
			card.setGoing(cds.scrapeGoing(doc));
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}

		try {
			card.setNumRunners(cds.scrapeNumRunners(doc));
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}

		try {
			card.setWinPrize(cds.scrapePrize(doc));
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}

		try {
			card.setGrade(cds.scrapeGrade(doc));
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}

		// TODO : are conditions required?
		card.setConditions("");

		try {
			card.setTitle(cds.scrapeTitle(doc));
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}

		return card;
	}

}
