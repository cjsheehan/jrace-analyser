package com.cjsheehan.jrace.scrape.rpost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.RaceScraperParams;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class RPRaceDataScraper implements RaceDataScraper {
	private static Pattern raceIdPtrn = Pattern.compile("race_id[=_](\\d+)");
	private static final String DATE_FORMAT = "hh:mm aa, dd MMMM yyyy";
	
	protected RaceScraperParams params;
	
	@Autowired
	public RPRaceDataScraper(RaceScraperParams params) {
		if(params == null) throw new IllegalArgumentException("params is null");
		this.params = params;
	}
	
	@Override
	public String scrapeAges(Document doc) throws ScrapeException {
		String ages = null;
		try {
			ages = Scrape.text(doc, params.ageConstraintSelector()).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", doc.toString(), params.ageConstraintSelector());
		}
		return ages;
	}


	@Override
	public String scrapeCourse(Document doc) throws ScrapeException {
		String course = null;
		try {
			course = Scrape.text(doc, params.courseNameSelector());
		} catch (Exception e) {
			throw new ScrapeException("Course Name", doc.toString(), params.courseNameSelector());
		}
		return course;
	}


	@Override
	public Date scrapeDate(Document doc) throws ScrapeException {
		final String timeAttr = "data-race-time";
		Element timeElem = doc.select(params.timeSelector()).first();
		String time = null;
		if (timeElem != null) {
			// TODO refactor : disambiguate time scrape between Card/Result
			time = timeElem.ownText(); // Result
			if(StringUtil.isBlank(time)) {
				time = timeElem.attr(timeAttr); // Card
			}
			if(StringUtil.isBlank(time)) throw new ScrapeException("Time", doc.toString(), params.timeSelector());
		} else {
			throw new ScrapeException("Time", doc.toString(), params.timeSelector());
		}


		String date = null;
		try {
			date = Scrape.text(doc, params.dateSelector());
		} catch (Exception e) {
			throw new ScrapeException("Date", doc.toString(), params.dateSelector());
		}

		Date dt = null;
		if (time != null && date != null) {
			try {
				dt = new SimpleDateFormat(params.dateFormat()).parse(time + " PM, " + date);
			} catch (ParseException e) {
				throw new ScrapeException("Failed to parse date format");
			}
		}

		return dt;
	}


	@Override
	public Distance scrapeDistance(Document doc) throws ScrapeException {
		Element distanceElem = doc.select(params.distanceSelector()).first();
		Distance distance = null;
		if (distanceElem != null) {
			distance = new Distance(distanceElem.ownText()
										.replace("(", "")
										.replace(")", "")
										.replace("yds", "y"));
		} else {
			throw new ScrapeException("Distance", doc.toString(), params.distanceSelector());
		}
		return distance;
	}


	@Override
	public String scrapeGoing(Document doc) throws ScrapeException {
		String going = null;
		try {
			going = Scrape.text(doc, params.goingSelector());
		} catch (Exception e) {
			throw new ScrapeException("Going", doc.toString(), params.goingSelector());
		}
		return going;
	}


	@Override
	public String scrapeGrade(Document doc) throws ScrapeException {
		String grade = null;
		try {
			grade = Scrape.text(doc, params.gradeSelector()).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", doc.toString(), params.gradeSelector());
		}
		return grade;
	}


	@Override
	public int scrapeNumRunners(Document doc) throws ScrapeException {
		int numRunners = 0;
		try {
			numRunners = Integer.parseInt(Scrape.text(doc, params.numRunnersSelector()));
		} catch (Exception e) {
			throw new ScrapeException("Num Runners", doc.toString(), params.numRunnersSelector());
		}
		return numRunners;
	}


	@Override
	public double scrapePrize(Document doc) throws ScrapeException {
		String prize = "";
		double prizeVal = 0;
		Currency cur = Currency.GBP;
		try {
			prize = Scrape.text(doc, params.prizeSelector());
			if (prize.contains("€")) {
				cur = Currency.EUR;
			} else if (prize.contains("$")) {
				cur = Currency.USD;
			}
			
			prize = prize.replace("£", "").replace("€", "").replace("$", "").replace(",", "");
			prizeVal = Double.parseDouble(prize);
		} catch (Exception e) {
			throw new ScrapeException("Winning Prize", doc.toString(), params.prizeSelector());
		}

		return prizeVal;
	}


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
	public String scrapeTitle(Document doc) throws ScrapeException {
		Element elem = doc.select(params.titleSelector()).first();
		String title = null;
		if (elem != null) {
			title = elem.ownText();
		} else {
			throw new ScrapeException("Title", doc.toString(), params.titleSelector());
		}
		return title;
	}
}
