package com.cjsheehan.jrace.scrape.rpost;

import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.ConvertDistance;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.Weight;
import com.cjsheehan.jrace.scrape.RaceEntrantDataScraper;
import com.cjsheehan.jrace.scrape.ResultEntrantDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPResultEntrantDataScraper implements ResultEntrantDataScraper {

	@Autowired
	@Qualifier("resultEntrantDataScraper")
	private RaceEntrantDataScraper redScraper;

	@Override
	public String scrapePosition(Element elem) throws ScrapeException {
		final String selector = "span.rp-horseTable__pos__number";
		String scraped;
		try {
			scraped = Scrape.text(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Position", elem.toString(), selector);
		}
		return scraped;
	}

	@Override
	public double scrapeBeatenDistanceToNext(Element elem) throws ScrapeException {
		final String selector = "span.rp-horseTable__pos__length > span:nth-child(1)";
		double toNext;
		try {
			String text = Scrape.text(elem, selector);
			toNext = ConvertDistance.beatenToYards(text);

		} catch (Exception e) {
			throw new ScrapeException("Beaten distance to NEXT", elem.toString(), selector);
		}
		return toNext;
	}

	public double scrapeBeatenDistanceToFirst(Element elem) throws ScrapeException {
		final String selector = "span.rp-horseTable__pos__length > span:nth-child(2)";
		double toFirst;
		try {
			String text = Scrape.text(elem, selector);
			text = text.replace("[", "").replace("]", "");
			toFirst = ConvertDistance.beatenToYards(text);

		} catch (Exception e) {
			throw new ScrapeException("Beaten distance to FIRST", elem.toString(), selector);
		}
		return toFirst;
	}

	@Override
	public String scrapePrice(Element elem) throws ScrapeException {
		final String selector = "span.rp-horseTable__horse__price";
		String scraped;
		try {
			scraped = Scrape.text(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Price", elem.toString(), selector);
		}
		return scraped;
	}

	@Override
	public String scrapeComments(Element elem) throws ScrapeException {
		final String selector = "tr.rp-horseTable__commentRow > td";
		String scraped;
		try {
			scraped = Scrape.text(elem, selector);
		} catch (Exception e) {
			throw new ScrapeException("Price", elem.toString(), selector);
		}
		return scraped;
	}

	@Override
	public int scrapeAge(Element elem) throws ScrapeException {
		return redScraper.scrapeAge(elem);
	}

	@Override
	public Horse scrapeHorse(Element elem) throws ScrapeException {
		return redScraper.scrapeHorse(elem);
	}

	@Override
	public Jockey scrapeJockey(Element elem) throws ScrapeException {
		return redScraper.scrapeJockey(elem);
	}

	@Override
	public Rating scrapeRating(Element elem) throws ScrapeException {
		return redScraper.scrapeRating(elem);
	}

	@Override
	public Trainer scrapeTrainer(Element elem) throws ScrapeException {
		return redScraper.scrapeTrainer(elem);
	}

	@Override
	public Weight scrapeWeight(Element elem) throws ScrapeException {
		return redScraper.scrapeWeight(elem);
	}

	@Override
	public int scrapeWeightAllowance(Element elem) throws ScrapeException {
		return redScraper.scrapeWeightAllowance(elem);
	}

}
