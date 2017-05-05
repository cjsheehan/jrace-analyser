package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.scrape.CardDataScraper;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPCardDataScraper implements CardDataScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static Pattern raceIdPtrn = Pattern.compile("race_id[=_](\\d+)");
	private static final String DATE_FORMAT = "hh:mm aa, dd MMMM yyyy";

	@Override
	public int scrapeRaceId(Document doc) throws ScrapeException {
		String url = doc.location();
		int raceId;
		Matcher m = raceIdPtrn.matcher(url);
		if (m.find()) {
			try {
				raceId = Integer.parseInt(m.group(1));
			} catch (NumberFormatException e) {
				throw new ScrapeException("Race ID", url, "pattern: " + raceIdPtrn.toString());
			}
		} else {
			throw new ScrapeException("Race ID", url, "pattern: " + raceIdPtrn.toString());
		}
		return raceId;
	}

	@Override
	public String scrapeAges(Document doc) throws ScrapeException {
		final String SELECTOR = "span[data-test-selector=RC-header__rpAges]";
		String ages = null;
		try {
			ages = Scrape.text(doc, SELECTOR).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", doc.toString(), SELECTOR);
		}
		return ages;
	}

	@Override
	public String scrapeCourse(Document doc) throws ScrapeException {
		final String SELECTOR = "a.RC-courseHeader__name";
		String course = null;
		try {
			course = Scrape.text(doc, SELECTOR);
		} catch (Exception e) {
			throw new ScrapeException("Course Name", doc.toString(), SELECTOR);
		}
		return course;
	}

	@Override
	public String scrapeGrade(Document doc) throws ScrapeException {
		final String SELECTOR = "span[data-test-selector=RC-header__raceClass]";
		String grade = null;
		try {
			grade = Scrape.text(doc, SELECTOR).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", doc.toString(), SELECTOR);
		}
		return grade;
	}

	@Override
	public String scrapeGoing(Document doc) throws ScrapeException {
		final String SELECTOR = "div[data-test-selector=RC-headerBox__going] > div.RC-headerBox__infoRow__content";
		String going = null;
		try {
			going = Scrape.text(doc, SELECTOR);
		} catch (Exception e) {
			throw new ScrapeException("Going", doc.toString(), SELECTOR);
		}
		return going;
	}

	@Override
	public String scrapeTitle(Document doc) throws ScrapeException {
		final String SELECTOR = "span[data-test-selector=RC-header__raceInstanceTitle]";
		Element elem = doc.select(SELECTOR).first();
		String title = null;
		if (elem != null) {
			title = elem.ownText();
		} else {
			throw new ScrapeException("Title", doc.toString(), SELECTOR);
		}
		return title;
	}

//	private void scrapeEntrants(Document doc) {
//		List<CardEntrant> entrants = new ArrayList<>();
//		Elements elems = doc.select(ENTRANTS_SELECT);
//		for (Element selected : elems) {
//			CardEntrant entrant;
//			try {
//				entrant = new CardEntrant(selected);
//				entrants.add(entrant);
//			} catch (ScrapeException e) {
//				log.error("Failed to scrape entrant", e);
//			}
//		}
//
//		setEntrants(entrants);
//	}

	@Override
	public Date scrapeDate(Document doc) throws ScrapeException {
		final String timeAttr = "data-race-time";
		final String TIME_SELECTOR = "span[" + timeAttr + "]";
		Element timeElem = doc.select(TIME_SELECTOR).first();
		String time = null;
		if (timeElem != null) {
			time = timeElem.attr(timeAttr);
		} else {
			throw new ScrapeException("Time", doc.toString(), TIME_SELECTOR);
		}


		final String DATE_SELECTOR = "span.RC-courseHeader__date";
		String date = null;		try {
			date = Scrape.text(doc, DATE_SELECTOR);
		} catch (Exception e) {
			throw new ScrapeException("Date", doc.toString(), TIME_SELECTOR);
		}

		Date dt = null;
		if (time != null && date != null) {
			try {
				dt = new SimpleDateFormat(DATE_FORMAT).parse(time + " PM, " + date);
			} catch (ParseException e) {
				throw new ScrapeException("Failed to parse date format");
			}
		}

		return dt;
	}

	@Override
	public double scrapePrize(Document doc) throws ScrapeException {
		final String SELECTOR = "div[data-test-selector=RC-headerBox__winner] > div.RC-headerBox__infoRow__content";
		String prize = "";
		double prizeVal = 0;
		Currency cur = Currency.GBP;
		try {
			prize = Scrape.text(doc, SELECTOR);
			if (prize.contains("€")) {
				cur = Currency.EUR;
			} else if (prize.contains("$")) {
				cur = Currency.USD;
			}
			
			prize = prize.replace("£", "").replace("€", "").replace("$", "").replace(",", "");
			prizeVal = Double.parseDouble(prize);
		} catch (Exception e) {
			throw new ScrapeException("Winning Prize", doc.toString(), SELECTOR);
		}

		return prizeVal;
	}

	@Override
	public int scrapeNumRunners(Document doc) throws ScrapeException {
		final String SELECTOR = "div[data-test-selector=RC-headerBox__runners] > div.RC-headerBox__infoRow__content";
		int numRunners = 0;
		try {
			numRunners = Integer.parseInt(Scrape.text(doc, SELECTOR));
		} catch (Exception e) {
			throw new ScrapeException("Num Runners", doc.toString(), SELECTOR);
		}
		return numRunners;
	}

	@Override
	public Distance scrapeDistance(Document doc) throws ScrapeException {
		final String SELECTOR = "span[data-test-selector=RC-header__raceDistance]";
		Element distanceElem = doc.select(SELECTOR).first();
		Distance distance = null;
		if (distanceElem != null) {
			distance = new Distance(distanceElem.ownText()
										.replace("(", "")
										.replace(")", ""));
		} else {
			throw new ScrapeException("Distance", doc.toString(), SELECTOR);
		}
		return distance;
	}

//	public String toString() {
//
//		StringBuilder entrants = new StringBuilder();
//		for (CardEntrant e : getEntrants()) {
//			entrants.append(e.toString() + "\n");
//		}
//
//		return new ToStringBuilder(this).append("Course", getCourse()).append("Race ID", getRaceId())
//				.append("Race URL", getRaceUrl()).append("Winning Prize", getWinPrize())
//				.append("Runners", getNumRunners()).append("Distance", getDistance()).append("Going", getGoing())
//				.append("Grade", getGrade()).append("Conditions", getConditions()).append("Title", getTitle())
//				.append("\n").append("Entrants", "\n" + entrants).append("\n").toString();
//	}
}
