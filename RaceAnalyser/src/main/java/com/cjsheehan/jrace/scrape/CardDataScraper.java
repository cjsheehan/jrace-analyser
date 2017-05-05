package com.cjsheehan.jrace.scrape;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Distance;

@Component
public interface CardDataScraper {
	int scrapeRaceId(Document doc) throws ScrapeException;
	String scrapeCourse(Document doc) throws ScrapeException;
	Date scrapeDate(Document doc) throws ScrapeException;
	double scrapePrize(Document doc) throws ScrapeException;
	int scrapeNumRunners(Document doc) throws ScrapeException;
	Distance scrapeDistance(Document doc) throws ScrapeException;
	String scrapeGoing(Document doc) throws ScrapeException;
	String scrapeTitle(Document doc) throws ScrapeException;
	String scrapeGrade(Document doc) throws ScrapeException;
	String scrapeAges(Document doc) throws ScrapeException;
}
