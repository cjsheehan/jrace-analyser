package com.cjsheehan.jrace.scrape.rpost;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.ConvertDistance;
import com.cjsheehan.jrace.racing.Odds;
import com.cjsheehan.jrace.scrape.ResultEntrantDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPResultEntrantDataScraper implements ResultEntrantDataScraper {

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
	public Odds scrapeOdds(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scrapeComments(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

}
