package com.cjsheehan.jrace.scrape;

import java.util.List;

import org.jsoup.nodes.Element;

public interface ResultDataScraper extends RaceDataScraper, ResultEntrantScraper {
	long scrapeWinningTime(Element elem) throws ScrapeException;
	List<String> scrapeNonRunners(Element elem) throws ScrapeException;
}
