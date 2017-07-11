package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Rating;

@Component
public interface CardEntrantDataScraper extends RaceEntrantDataScraper {
	int scrapeDaysSinceLastRan(Element elem) throws ScrapeException;
	int scrapeDraw(Element elem) throws ScrapeException;
	Rating scrapeRating(Element elem) throws ScrapeException;
	int scrapeSaddleNo(Element elem) throws ScrapeException;
}
