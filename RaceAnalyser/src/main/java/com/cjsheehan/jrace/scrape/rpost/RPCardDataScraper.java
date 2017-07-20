package com.cjsheehan.jrace.scrape.rpost;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class RPCardDataScraper implements RaceDataScraper {

	@Autowired
	@Qualifier("cardDataScraper")
	private RaceDataScraper rdScraper;
	
	
	@Override
	public int scrapeRaceId(Element elem) throws ScrapeException {
		return rdScraper.scrapeRaceId(elem);
	}

	@Override
	public String scrapeCourse(Element elem) throws ScrapeException {
		return rdScraper.scrapeCourse(elem);
	}

	@Override
	public Date scrapeDate(Element elem) throws ScrapeException {
		return rdScraper.scrapeDate(elem);
	}

	@Override
	public Prize scrapePrize(Element elem) throws ScrapeException {
		return rdScraper.scrapePrize(elem);
	}

	@Override
	public int scrapeNumRunners(Element elem) throws ScrapeException {
		return rdScraper.scrapeNumRunners(elem);
	}

	@Override
	public Distance scrapeDistance(Element elem) throws ScrapeException {
		return rdScraper.scrapeDistance(elem);
	}

	@Override
	public String scrapeGoing(Element elem) throws ScrapeException {
		return rdScraper.scrapeGoing(elem);
	}

	@Override
	public String scrapeTitle(Element elem) throws ScrapeException {
		return rdScraper.scrapeTitle(elem);
	}

	@Override
	public String scrapeGrade(Element elem) throws ScrapeException {
		return rdScraper.scrapeGrade(elem);
	}

	@Override
	public String scrapeAges(Element elem) throws ScrapeException {
		return rdScraper.scrapeAges(elem);
	}

}
