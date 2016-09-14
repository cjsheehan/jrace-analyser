package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.racing.Race;

public class Card {
    // jsoup selectors
    private static final String TRACK_SELECT = "span.placeRace";
    private static final String TIME_SELECT = "div.raceTitle span.navRace > span";
    private static final String DATE_SELECT = "div.raceTitle span.placeRace > span.date";
    private static final String WIN_PRIZE_SELECT = "div.raceInfo > ul > li:nth-child(1) > strong";
    private static final String NUM_RUNNERS_SELECT = "div.raceInfo > ul > li:nth-child(2) > strong";
    private static final String DISTANCE_SELECT = "div.raceInfo > ul > li:nth-child(3) > strong";
    private static final String GOING_SELECT = "div.raceInfo > ul > li:nth-child(4) > strong";
    private static final String TITLE_SELECT = "div.raceInfo > div > p > strong > strong";
    private static final String GRADE_SELECT = "div.raceInfo > div > p";
    private static final String CONDITIONS_SELECT = "div.raceInfo > div > p";
    
    
    // pattern match
    static Pattern pGrade = Pattern.compile("(CLASS \\d{1})");
    static Pattern pCond = Pattern.compile("\\(.+\\) *\\((.+)\\)");
    
    private static final String DATE_FORMAT = "h:mm EEEE, dd MMMM yyyy";

    public Race scrape(String url) {
	if (StringUtils.isBlank(url)) {
	    throw new IllegalArgumentException("url cannot be empty/null/blank");
	}

	Document doc = null;
	try {
	    doc = Jsoup.connect(url)
		    .userAgent(Constants.ROOT_URL)
		    .timeout(Constants.MAX_WAIT)
		    .get();

	    if (doc == null) {
		return null;
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String track = getString(doc, TRACK_SELECT);
	Date date = getDate(doc);
	Prize winPrize = getPrize(doc);
	int numRunners = getNumRunners(doc);
	Distance distance = getDistance(doc);
	String going = getString(doc, GOING_SELECT);
	String title = getString(doc, TITLE_SELECT);
	String grade = getGrade(doc);
	String conditions = getConditions(doc);
	
	return new Race(date, track, numRunners, distance, going, null, winPrize, grade, conditions, title);

    }

    private String getGrade(Document doc) {
	Element elem = doc.select(GRADE_SELECT).first();
	String grade = null;
	if (elem != null) {
	    String text = elem.ownText();
	    Matcher mGrade = pGrade.matcher(text);
	    if(mGrade.find()) {
		grade = mGrade.group(1);
	    }
	}
	
	return grade;
    }
    
    private String getConditions(Document doc) {
	Element elem = doc.select(CONDITIONS_SELECT).first();
	String cond = null;
	if (elem != null) {
	    Matcher mCond = pCond.matcher(elem.ownText());
	    if(mCond.find()) {
		cond = mCond.group(1);
	    }
	}
	
	return cond;
    }

    @SuppressWarnings("unused")
    private String getTrack(Document doc) {
	Element elem = doc.select(TRACK_SELECT).first();
	if (elem != null) {
	    return elem.ownText();
	} else {
	    return null;
	}
    }

    private Date getDate(Document doc) {
	Element timeElem = doc.select(TIME_SELECT).first();
	String time = null;
	if (timeElem != null) {
	    time = timeElem.ownText();
	}

	Element dateElem = doc.select(DATE_SELECT).first();
	String date = null;
	if (dateElem != null) {
	    date = dateElem.ownText();
	}

	Date dt = null;
	if (time != null && date != null) {
	    try {
		dt = new SimpleDateFormat(DATE_FORMAT).parse(time + " " + date);
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	return dt;
    }

    private Prize getPrize(Document doc) {
	Element elem = doc.select(WIN_PRIZE_SELECT).first();
	String prize = "";
	double prizeVal = 0;
	Currency cur = Currency.GBP;

	if (elem != null) {
	    prize = elem.ownText();
	    if (prize.contains("€")) {
		cur = Currency.EUR;
	    } else if (prize.contains("$")) {
		cur = Currency.USD;
	    }

	    prize = prize.replace("£", "").replace("€", "").replace(",", "");

	}

	if (prize != "") {
	    prizeVal = Double.parseDouble(prize);
	    return new Prize(prizeVal, cur);
	} else {
	    return null;
	}
    }

    private int getNumRunners(Document doc) {
	Element elem = doc.select(NUM_RUNNERS_SELECT).first();
	int numRunners = 0;
	if (elem != null) {
	    numRunners = Integer.parseInt(elem.ownText());
	}
	return numRunners;
    }
    
    private Distance getDistance(Document doc) {
	Element elem = doc.select(DISTANCE_SELECT).first();
	Distance distance = null;
	if (elem != null) {
	    String dist = elem.ownText();
	    distance = new Distance(dist);
	}
	return distance;
    }
    
    @SuppressWarnings("unused")
    private String getGoing(Document doc) {
	Element elem = doc.select(GOING_SELECT).first();
	String going = null;
	if (elem != null) {
	    going = elem.ownText();
	}
	return going;
    }
    
    private String getString(Document doc, String selector) {
	Element elem = doc.select(selector).first();
	String target = null;
	if (elem != null) {
	    target = elem.ownText();
	}
	return target;
    }
}
