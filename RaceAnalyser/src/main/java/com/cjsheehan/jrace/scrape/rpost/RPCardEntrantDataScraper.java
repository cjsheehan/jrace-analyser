package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.NotImplementedException;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.Weight;
import com.cjsheehan.jrace.scrape.CardEntrantDataScraper;
import com.cjsheehan.jrace.scrape.RaceEntrantDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPCardEntrantDataScraper implements CardEntrantDataScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	@Qualifier("cardEntrantDataScraper")
	private RaceEntrantDataScraper redScraper;

	@Override
	public int scrapeAge(Element elem) throws ScrapeException {
		return redScraper.scrapeAge(elem);
	}

	@Override
	public int scrapeDaysSinceLastRan(Element elem) throws ScrapeException {
		final String select = "div.RC-runnerStats__lastRun";
		int scraped = 0;
		try {
			scraped = Scrape.integer(elem, select);
		} catch (Exception e) {
			throw new ScrapeException("Age", elem.toString(), select);
		}
		return scraped;
	}

	@Override
	public int scrapeDraw(Element elem) throws ScrapeException {
		throw new NotImplementedException("Not Implemented");
	}

	@Override
	public Horse scrapeHorse(Element elem) throws ScrapeException {
		return redScraper.scrapeHorse(elem);
	}

	@Override
	public Jockey scrapeJockey(Element elem) throws ScrapeException {
		return redScraper.scrapeJockey(elem);
	}
	

	@Override
	public Rating scrapeRating(Element elem) throws ScrapeException {
		return redScraper.scrapeRating(elem);
	}

	@Override
	public int scrapeSaddleNo(Element elem) throws ScrapeException {
		final String selector = "span.RC-runnerNumber__no";
		int saddleNo;
		try {
			saddleNo = Scrape.integer(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Saddle Number", elem.toString(), selector);
		}
		return saddleNo;
	}

	@Override
	public Trainer scrapeTrainer(Element elem) throws ScrapeException {
		return redScraper.scrapeTrainer(elem);
	}

	@Override
	public Weight scrapeWeight(Element elem) throws ScrapeException {
		return redScraper.scrapeWeight(elem);
	}

	@Override
	public int scrapeWeightAllowance(Element elem) throws ScrapeException {
		return redScraper.scrapeWeightAllowance(elem);
	}

}
