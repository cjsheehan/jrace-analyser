package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.CardEntrant;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.scrape.CardEntrantDataScraper;
import com.cjsheehan.jrace.scrape.CardEntrantScraper;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPCardDataScraper implements RaceDataScraper, CardEntrantScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	@Qualifier("cardDataScraper")
	private RaceDataScraper rdScraper;
	
	@Autowired
	private CardEntrantDataScraper ceds;
	
	@Autowired
	private ApplicationContext context;

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

	@Override
	public List<CardEntrant> scrapeEntrants(Document doc) {
		List<CardEntrant> entrants = new ArrayList<>();
		String selector = "div.RC-runnerRow";
		List<Element> entrantElems = doc.select(selector);
		for (Element element : entrantElems) {
			try {
				CardEntrant entrant = (CardEntrant) context.getBean("cardEntrant");

				// Critical
				entrant.setHorse(ceds.scrapeHorse(element));
				entrant.setJockey(ceds.scrapeJockey(element));
				entrant.setTrainer(ceds.scrapeTrainer(element));
				entrant.setWeight(ceds.scrapeWeight(element));

				// Non-Critical
				try {
					entrant.setAge(ceds.scrapeAge(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setSaddleNo(ceds.scrapeSaddleNo(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setDraw(ceds.scrapeDraw(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setRating(ceds.scrapeRating(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setLastRan(ceds.scrapeDaysSinceLastRan(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setWeightClaim(ceds.scrapeWeightAllowance(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				entrants.add(entrant);

			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return entrants;
	}

}
