package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;

@Component
public interface CardEntrantDataScraper {
	int scrapeAge(Element elem) throws ScrapeException;
	int scrapeDaysSinceLastRan(Element elem) throws ScrapeException;
	int scrapeDraw(Element elem) throws ScrapeException;
	Horse scrapeHorse(Element elem) throws ScrapeException;
	Jockey scrapeJockey(Element elem) throws ScrapeException;
	Rating scrapeRating(Element elem) throws ScrapeException;
	int scrapeSaddleNo(Element elem) throws ScrapeException;
	Trainer scrapeTrainer(Element elem) throws ScrapeException;
	String scrapeWeight(Element elem) throws ScrapeException;
	int weightClaim(Element elem) throws ScrapeException;
}
