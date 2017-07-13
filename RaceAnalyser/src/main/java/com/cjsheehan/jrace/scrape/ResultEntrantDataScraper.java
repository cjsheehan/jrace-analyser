package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public interface ResultEntrantDataScraper extends RaceEntrantDataScraper {
	String scrapePosition(Element elem) throws ScrapeException;

	double scrapeBeatenDistanceToFirst(Element elem) throws ScrapeException;

	double scrapeBeatenDistanceToNext(Element elem) throws ScrapeException;

	String scrapePrice(Element elem) throws ScrapeException;

	String scrapeComments(Element elem) throws ScrapeException;
}
