package com.cjsheehan.jrace.scrape.rpost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Rating.Provider;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.Weight;
import com.cjsheehan.jrace.scrape.EntrantParamsProvider;
import com.cjsheehan.jrace.scrape.RaceEntrantDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPEntrantDataScraper implements RaceEntrantDataScraper {

	private static Pattern idPtrn = Pattern.compile("/profile/.+?/(\\d+)/");
	private EntrantParamsProvider params;

	public RPEntrantDataScraper(EntrantParamsProvider params) {
		if (params == null)
			throw new IllegalArgumentException("params is null");
		this.params = params;
	}

	@Override
	public int scrapeAge(Element elem) throws ScrapeException {
		int scraped = 0;
		try {
			scraped = Scrape.integer(elem, params.ageSelector());
		} catch (Exception e) {
			throw new ScrapeException("Age", elem.toString(), params.ageSelector());
		}
		return scraped;
	}

	@Override
	public Horse scrapeHorse(Element elem) throws ScrapeException {
		String name;
		long id;

		try {
			name = scrapeName(elem, params.horseSelector());
		} catch (Exception e) {
			throw new ScrapeException("Horse Name", elem.toString(), params.horseSelector());
		}

		try {
			id = scrapeId(elem, params.horseSelector());
		} catch (Exception e) {
			throw new ScrapeException("Horse ID", elem.toString(), params.horseSelector());
		}
		return new Horse(name, id);
	}

	@Override
	public Jockey scrapeJockey(Element elem) throws ScrapeException {
		String name;
		long id;

		try {
			name = scrapeName(elem, params.jockeySelector());
		} catch (Exception e) {
			throw new ScrapeException("Jockey Name", elem.toString(), params.jockeySelector());
		}

		try {
			id = scrapeId(elem, params.jockeySelector());
		} catch (Exception e) {
			throw new ScrapeException("Jockey ID", elem.toString(), params.jockeySelector());
		}
		return new Jockey(name, id);
	}

	@Override
	public Rating scrapeRating(Element elem) throws ScrapeException {
		int ts = scrapeRating(elem, Provider.TS);
		int rpr = scrapeRating(elem, Provider.RPR);
		int or = scrapeRating(elem, Provider.OR);
		return new Rating(or, rpr, ts, -1);
	}

	@Override
	public Trainer scrapeTrainer(Element elem) throws ScrapeException {
		String name;
		long id;

		try {
			name = scrapeName(elem, params.trainerSelector());
		} catch (Exception e) {
			throw new ScrapeException("Trainer Name", elem.toString(), params.trainerSelector());
		}

		try {
			id = scrapeId(elem, params.trainerSelector());
		} catch (Exception e) {
			throw new ScrapeException("Trainer ID", elem.toString(), params.trainerSelector());
		}
		return new Trainer(name, id);
	}

	@Override
	public Weight scrapeWeight(Element elem) throws ScrapeException {
		String wgtSt;
		try {
			wgtSt = Scrape.text(elem, params.weightStSelector());
		} catch (Exception e) {
			throw new ScrapeException("Carried Weight St", elem.toString(), params.weightStSelector());
		}

		String wtLbs;
		try {
			wtLbs = Scrape.text(elem, params.weightLbSelector());
		} catch (Exception e) {
			throw new ScrapeException("Carried Weight lbs", elem.toString(), params.weightLbSelector());
		}

		Weight wt = null;

		try {
			wt = new Weight(wgtSt + "-" + wtLbs);

		} catch (NumberFormatException e) {
			throw new ScrapeException(String.format("Could not convert {0} to valid weight", wgtSt + "-" + wtLbs));
		}
		return wt;
	}

	@Override
	public int scrapeWeightAllowance(Element elem) throws ScrapeException {
		int allowance = 0;
		try {
			allowance = Scrape.integer(elem, params.weightAllowanceSelector());
		} catch (ScrapeException e) {
			// Swallow as most entrants have no weight allowance
		} catch (Exception e) {
			throw new ScrapeException("Weight claim", elem.toString(), params.weightAllowanceSelector());
		}

		return allowance;
	}

	private long scrapeId(Element elem, String selector) throws ScrapeException {
		String url;
		Element selected = elem.select(selector).first();
		url = selected.attr("href");

		int id;
		Matcher m = idPtrn.matcher(url);
		if (m.find()) {
			id = Integer.parseInt(m.group(1));
		} else {
			throw new ScrapeException("Entity ID", url, "pattern: " + idPtrn.toString());
		}
		return id;
	}

	private String scrapeName(Element elem, String selector) throws ScrapeException {
		return Scrape.text(elem, selector);
	}

	private int scrapeRating(Element elem, Rating.Provider provider) throws ScrapeException {
		String selector;
		switch (provider) {
		case RPR:
			selector = params.ratingRprSelector();
			break;

		case OR:
			selector = params.ratingOrSelector();
			break;

		case TS:
			selector = params.ratingTsSelector();
			break;

		default:
			return -1;
		}

		int rating;
		String scraped;
		try {
			scraped = Scrape.text(elem, selector);
			if (scraped.equals("–")) {
				return -1;
			} else {
				try {
					rating = Integer.parseInt(scraped);
				} catch (NumberFormatException e) {
					throw new ScrapeException(
							String.format("Could not parse valid rating from {0} with selector: {1} from text : {2}",
									scraped, selector, elem.toString()));
				}
			}
		} catch (Exception e) {
			throw new ScrapeException(provider.toString() + " Rating", "", selector);
		}
		return rating;
	}
}
