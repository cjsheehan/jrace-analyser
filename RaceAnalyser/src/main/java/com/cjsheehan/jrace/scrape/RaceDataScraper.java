package com.cjsheehan.jrace.scrape;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;

@Component
public interface RaceDataScraper {
	int scrapeRaceId(Element elem) throws ScrapeException;
	String scrapeCourse(Element elem) throws ScrapeException;
	Date scrapeDate(Element elem) throws ScrapeException;

	Prize scrapePrize(Element elem) throws ScrapeException;
	int scrapeNumRunners(Element elem) throws ScrapeException;
	Distance scrapeDistance(Element elem) throws ScrapeException;
	String scrapeGoing(Element elem) throws ScrapeException;
	String scrapeTitle(Element elem) throws ScrapeException;
	String scrapeGrade(Element elem) throws ScrapeException;
	String scrapeAges(Element elem) throws ScrapeException;

}
