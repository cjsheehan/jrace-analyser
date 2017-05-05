package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cjsheehan.jrace.racing.Card;
import com.cjsheehan.jrace.scrape.CardDataScraper;
import com.cjsheehan.jrace.scrape.CardScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class RPCardScraper implements CardScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	private CardDataScraper cds;

	@Override
	public Card scrape(Document doc) {
		if(doc == null) throw new IllegalArgumentException("doc is null");
		Card card = new Card();
		
		try {
			int raceId = cds.scrapeRaceId(doc);
		} catch (ScrapeException e) {
			log.error(e.getMessage());
		}
		
		
		
		return null;
	}

}
