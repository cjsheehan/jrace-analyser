package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Odds;

@Component
public interface ResultEntrantDataScraper {
	String scrapePosition(Element elem) throws ScrapeException;

	double scrapeBeatenDistanceToFirst(Element elem) throws ScrapeException;

	double scrapeBeatenDistanceToNext(Element elem) throws ScrapeException;

	Odds scrapeOdds(Element elem) throws ScrapeException;

	String scrapeComments(Element elem) throws ScrapeException;
}
