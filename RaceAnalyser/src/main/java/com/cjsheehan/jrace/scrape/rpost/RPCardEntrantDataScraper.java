package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.scrape.CardEntrantDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPCardEntrantDataScraper implements CardEntrantDataScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static Pattern idPtrn = Pattern.compile("/profile/.+?/(\\d+)/");
	
	@Override
	public int scrapeAge(Element elem) throws ScrapeException {
		final String selector = "span.RC-runnerAge";
		int scraped = 0;
		try {
			scraped = Scrape.integer(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Age", elem.toString(), selector);
		}
		return scraped;
	}

	@Override
	public int scrapeDaysSinceLastRan(Element elem) throws ScrapeException {
		final String selector = "div.RC-runnerStats__lastRun";
		int scraped = 0;
		try {
			scraped = Scrape.integer(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("lastRan", elem.toString(), selector);
		}
		return scraped;
	}

	@Override
	public int scrapeDraw(Element elem) throws ScrapeException {
		throw new NotImplementedException("Not Implemented");
	}

	@Override
	public Horse scrapeHorse(Element elem) throws ScrapeException {
		String name = scrapeHorseName(elem);
		long id = scrapeHorseId(elem);
		return new Horse(name, id);
	}
	
	private String scrapeHorseName(Element elem) throws ScrapeException {
		String scraped = null;
		final String selector = "a.RC-runnerName";
		try {
			scraped = Scrape.text(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Horse Name", elem.toString(), selector);
		}
		return scraped;
	}
	
	private long scrapeHorseId(Element elem) throws ScrapeException {
		String url;
		final String selector = "div.RC-runnerMainWrapper > a[href]";
		Element selected = elem.select(selector).first();
		try {
			url = selected.attr("href");
		} catch (Exception e) {
			throw new ScrapeException("Horse ID", elem.toString(), selector);
		}
		
		int id;
		Matcher m = idPtrn.matcher(url);
		if (m.find()) {
			try {
				id = Integer.parseInt(m.group(1));
			} catch (NumberFormatException e) {
				throw new ScrapeException("Horse ID", url, "pattern: " + idPtrn.toString());
			}
		} else {
			throw new ScrapeException("Horse ID", url, "pattern: " + idPtrn.toString());
		}
		return id;
	}

	@Override
	public Jockey scrapeJockey(Element elem) throws ScrapeException {
		String name = scrapeJockeyName(elem);
		long id = scrapeJockeyId(elem);
		return new Jockey(name, id);
	}
	
	private String scrapeJockeyName(Element elem) throws ScrapeException {
		String scraped = null;
		final String selector = "div.RC-runnerInfo_jockey > a[href]";
		try {
			scraped = Scrape.text(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Jockey Name", elem.toString(), selector);
		}
		return scraped;
	}
	
	private long scrapeJockeyId(Element elem) throws ScrapeException {
		String url;
		final String selector = "div.RC-runnerInfo_jockey > a[href]";
		Element selected = elem.select(selector).first();
		try {
			url = selected.attr("href");
		} catch (Exception e) {
			throw new ScrapeException("Horse ID", elem.toString(), selector);
		}
		
		int id;
		Matcher m = idPtrn.matcher(url);
		if (m.find()) {
			try {
				log.info("id = " + m.group(1));
				id = Integer.parseInt(m.group(1));
			} catch (NumberFormatException e) {
				throw new ScrapeException("Jockey ID", url, "pattern: " + idPtrn.toString());
			}
		} else {
			throw new ScrapeException("Jockey ID", url, "pattern: " + idPtrn.toString());
		}
		return id;
	}

	@Override
	public Rating scrapeRating(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int scrapeSaddleNo(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Trainer scrapeTrainer(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scrapeWeight(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int weightClaim(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return 0;
	}

}
