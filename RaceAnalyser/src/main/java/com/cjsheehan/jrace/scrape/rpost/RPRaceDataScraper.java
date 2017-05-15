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
import com.cjsheehan.jrace.scrape.StandardParamProvider;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class RPRaceDataScraper implements RaceDataScraper {
	private static Pattern raceIdPtrn = Pattern.compile("race_id[=_](\\d+)");
	private StandardParamProvider params;
	
	@Autowired
	public RPRaceDataScraper(StandardParamProvider params) {
		if(params == null) throw new IllegalArgumentException("params is null");
		this.params = params;
	}
	
	@Override
	public String scrapeAges(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String ages = null;
		try {
			ages = Scrape.text(elem, params.ageConstraintSelector()).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", elem.toString(), params.ageConstraintSelector());
		}
		return ages;
	}


	@Override
	public String scrapeCourse(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String course = null;
		try {
			course = Scrape.text(elem, params.courseNameSelector());
		} catch (Exception e) {
			throw new ScrapeException("Course Name", elem.toString(), params.courseNameSelector());
		}
		return course;
	}


	@Override
	public Date scrapeDate(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		final String timeAttr = "data-race-time";
		Element timeElem = elem.select(params.timeSelector()).first();
		String time = null;
		if (timeElem != null) {
			// TODO refactor : disambiguate time scrape between Card/Result
			time = timeElem.ownText(); // Result
			if(StringUtil.isBlank(time)) {
				time = timeElem.attr(timeAttr); // Card
			}
			if(StringUtil.isBlank(time)) throw new ScrapeException("Time", elem.toString(), params.timeSelector());
		} else {
			throw new ScrapeException("Time", elem.toString(), params.timeSelector());
		}


		String date = null;
		try {
			date = Scrape.text(elem, params.dateSelector());
		} catch (Exception e) {
			throw new ScrapeException("Date", elem.toString(), params.dateSelector());
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
	public Distance scrapeDistance(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		Element distanceElem = elem.select(params.distanceSelector()).first();
		Distance distance = null;
		if (distanceElem != null) {
			distance = new Distance(distanceElem.ownText()
										.replace("(", "")
										.replace(")", "")
										.replace("yds", "y"));
		} else {
			throw new ScrapeException("Distance", elem.toString(), params.distanceSelector());
		}
		return distance;
	}


	@Override
	public String scrapeGoing(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String going = null;
		try {
			going = Scrape.text(elem, params.goingSelector());
		} catch (Exception e) {
			throw new ScrapeException("Going", elem.toString(), params.goingSelector());
		}
		return going;
	}


	@Override
	public String scrapeGrade(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String grade = null;
		try {
			grade = Scrape.text(elem, params.gradeSelector()).replace("(", "").replace(")", "");
		} catch (Exception e) {
			throw new ScrapeException("Ages", elem.toString(), params.gradeSelector());
		}
		return grade;
	}


	@Override
	public int scrapeNumRunners(Element elem) throws ScrapeException {
		int numRunners = 0;
		try {
			numRunners = Integer.parseInt(Scrape.text(elem, params.numRunnersSelector()));
		} catch (Exception e) {
			throw new ScrapeException("Num Runners", elem.toString(), params.numRunnersSelector());
		}
		return numRunners;
	}


	@Override
	public double scrapePrize(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String prize = "";
		double prizeVal = 0;
		Currency cur = Currency.GBP;
		try {
			prize = Scrape.text(elem, params.prizeSelector());
			if (prize.contains("€")) {
				cur = Currency.EUR;
			} else if (prize.contains("$")) {
				cur = Currency.USD;
			}
			
			prize = prize.replace("£", "").replace("€", "").replace("$", "").replace(",", "");
			prizeVal = Double.parseDouble(prize);
		} catch (Exception e) {
			throw new ScrapeException("Winning Prize", elem.toString(), params.prizeSelector());
		}

		return prizeVal;
	}


	@Override
	public int scrapeRaceId(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String selector = "section[data-card-race-id]";
		Element idElem = elem.select(selector).first();
		return Integer.parseInt(idElem.attr("data-card-race-id"));
	}


	@Override
	public String scrapeTitle(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		Element selected = elem.select(params.titleSelector()).first();
		String title = null;
		if (selected != null) {
			title = selected.ownText();
		} else {
			throw new ScrapeException("Title", elem.toString(), params.titleSelector());
		}
		return title;
	}
}
