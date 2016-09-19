package com.cjsheehan.jrace.scrape.rpost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class Result {

    // core data
    private Date date;
    private String course;
    private int raceId;
    private String raceUrl;
    private int numRunners;
    private Distance distance;
    private String going;
    private List<Double> prizes;
    private Prize winPrize;
    private String grade;
    private String conditions;
    private String title;
    private List<CardEntrant> entrants;

    // jsoup selectors
    private static final String COURSE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > h1";
    private static final String TIME_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > h3 > span";
    private static final String DATE_SELECT = COURSE_SELECT;
    private static final String WIN_PRIZE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > ul > li:nth-child(2)";
    private static final String NUM_RUNNERS_SELECT = "#re_ > div.raceInfo > b:nth-child(1)";
    private static final String DISTANCE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > ul > li:nth-child(1)";
    private static final String GOING_SELECT = DISTANCE_SELECT;
    private static final String TITLE_SELECT = "";
    private static final String GRADE_SELECT = "";
    private static final String CONDITIONS_SELECT = "";
    private static final String ENTRANTS_SELECT = "";

    // pattern match
    static Pattern pGrade = Pattern.compile("(CLASS \\d{1})");
    static Pattern pCond = Pattern.compile("\\(.+\\) *\\((.+)\\)");
    static Pattern pRaceId = Pattern.compile("race_id[=_](\\d+)");
    static Pattern pCourse = Pattern.compile("^(.+) Result");
    static Pattern pDate = Pattern.compile("Result (.+)$");
    static Pattern pRunners = Pattern.compile("(\\d+) ran");
    static Pattern pParenth = Pattern.compile("\\((.+)\\)");
    static Pattern pDistance = Pattern.compile("^.+\\) (.+?) ");
    static Pattern pGoing= Pattern.compile("^.+\\) .+? (.+)");

    private static final String DATE_FORMAT = "h:mm, dd MMM yyyy";

    public Result(Document doc) throws ScrapeException {
	setRaceUrl(doc.location());
	scrapeRaceId(doc);
	scrapeCourse(doc);
	scrapeDate(doc);
	scrapePrize(doc);
	scrapeNumRunners(doc);
	scrapeDistance(doc);
	scrapeGoing(doc);
	// scrapeTitle(doc);
	// scrapeGrade(doc);
	// scrapeConditions(doc);
	// scrapeEntrants(doc);
    }

    private void scrapeGoing(Document doc) throws ScrapeException {
	try {
	    String text = Scrape.text(doc, GOING_SELECT);
	    Matcher m = pGoing.matcher(text);
	    if (m.find()) {
		    setGoing(m.group(1));
	    }
	} catch (ScrapeException e) {
	    throw new ScrapeException("Going", doc.toString(), DISTANCE_SELECT);
	}
    }

    private void scrapeDistance(Document doc) throws ScrapeException {
	// TODO : must handle edge case where actual
	// distance is supplied in parentheses aswell
	// as the common reported distance
	try {
	    String text = Scrape.text(doc, DISTANCE_SELECT);
	    Matcher m = pDistance.matcher(text);
	    String d = "";
	    if (m.find()) {
		try {
		    d = m.group(1);
		    Distance distance = new Distance(m.group(1));
		    setDistance(distance);
		} catch (IllegalArgumentException e) {
		    throw new ScrapeException("Invalid distance param: " + d);
		} catch (Exception e) {
		    throw new ScrapeException("Distance", doc.toString(), DISTANCE_SELECT);
		}
	    }

	} catch (ScrapeException e) {
	    throw new ScrapeException("Distance", doc.toString(), DISTANCE_SELECT);
	}
    }

    private void scrapeNumRunners(Document doc) throws ScrapeException {
	try {
	    String runners = Scrape.text(doc, NUM_RUNNERS_SELECT);
	    Matcher m = pRunners.matcher(runners);
	    if (m.find()) {
		try {
		    int numRunners = Integer.parseInt(m.group(1));
		    setNumRunners(numRunners);
		} catch (NumberFormatException e) {
		    throw new ScrapeException("Number of Runners", doc.toString(), NUM_RUNNERS_SELECT);
		}
	    }
	} catch (Exception e) {
	    throw new ScrapeException("Course", doc.toString(), NUM_RUNNERS_SELECT);
	}

    }

    private void scrapePrize(Document doc) throws ScrapeException {
	Element elem = doc.select(WIN_PRIZE_SELECT).first();
	String prize = "";
	double prizeVal = 0;
	Currency cur = Currency.GBP;

	try {
	    if (elem != null) {
		String[] prizeList = elem.ownText().split(", ");
		prize = prizeList[0];
		if (prize.contains("€")) {
		    cur = Currency.EUR;
		} else if (prize.contains("$")) {
		    cur = Currency.USD;
		}

		prize = prize.replace("£", "").replace("€", "").replace("$", "").replace(",", "");
		prizeVal = Double.parseDouble(prize);
		setWinPrize(new Prize(prizeVal, cur));
	    }
	} catch (NumberFormatException e) {
	    throw new ScrapeException("Winners Prize", doc.toString(), WIN_PRIZE_SELECT);
	}
    }

    private void scrapeDate(Document doc) throws ScrapeException {
	Element timeElem = doc.select(TIME_SELECT).first();
	String time = null;
	if (timeElem != null) {
	    time = timeElem.ownText();
	} else {
	    throw new ScrapeException("Time", doc.toString(), TIME_SELECT);
	}

	Element dateElem = doc.select(DATE_SELECT).first();
	String date = null;
	if (dateElem != null) {
	    date = dateElem.ownText();
	    Matcher m = pDate.matcher(date);
	    if (m.find()) {
		date = m.group(1);
	    }
	} else {
	    throw new ScrapeException("Date", doc.toString(), DATE_SELECT);
	}

	Date dt = null;
	if (time != null && date != null) {
	    try {
		dt = new SimpleDateFormat(DATE_FORMAT).parse(time + ", " + date);
	    } catch (ParseException e) {
		throw new ScrapeException("Failed to parse date format");
	    }
	} else {
	    throw new ScrapeException("Date/Time", doc.toString(), DATE_SELECT);
	}

	setDate(dt);

    }

    private void scrapeCourse(Document doc) throws ScrapeException {
	try {
	    String course = Scrape.text(doc, COURSE_SELECT);
	    Matcher m = pCourse.matcher(course);
	    if (m.find()) {
		setCourse(m.group(1));
	    }
	} catch (Exception e) {
	    throw new ScrapeException("Course", doc.toString(), COURSE_SELECT);
	}
    }

    private void scrapeRaceId(Document doc) throws ScrapeException {
	String url = doc.location();
	Matcher m = pRaceId.matcher(url);
	if (m.find()) {
	    int raceId;
	    try {
		raceId = Integer.parseInt(m.group(1));
		setRaceId(raceId);
	    } catch (NumberFormatException e) {
		throw new ScrapeException("Race ID", url, "pattern: " + pRaceId.toString());
	    }
	} else {
	    throw new ScrapeException("Race ID", url, "pattern: " + pRaceId.toString());
	}
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the course
     */
    public String getCourse() {
	return course;
    }

    /**
     * @param course
     *            the course to set
     */
    public void setCourse(String course) {
	this.course = course;
    }

    /**
     * @return the raceId
     */
    public int getRaceId() {
	return raceId;
    }

    /**
     * @param raceId
     *            the raceId to set
     */
    public void setRaceId(int raceId) {
	this.raceId = raceId;
    }

    /**
     * @return the raceUrl
     */
    public String getRaceUrl() {
	return raceUrl;
    }

    /**
     * @param raceUrl
     *            the raceUrl to set
     */
    public void setRaceUrl(String raceUrl) {
	this.raceUrl = raceUrl;
    }

    /**
     * @return the numRunners
     */
    public int getNumRunners() {
	return numRunners;
    }

    /**
     * @param numRunners
     *            the numRunners to set
     */
    public void setNumRunners(int numRunners) {
	this.numRunners = numRunners;
    }

    /**
     * @return the distance
     */
    public Distance getDistance() {
	return distance;
    }

    /**
     * @param distance
     *            the distance to set
     */
    public void setDistance(Distance distance) {
	this.distance = distance;
    }

    /**
     * @return the going
     */
    public String getGoing() {
	return going;
    }

    /**
     * @param going
     *            the going to set
     */
    public void setGoing(String going) {
	this.going = going;
    }

    /**
     * @return the prizes
     */
    public List<Double> getPrizes() {
	return prizes;
    }

    /**
     * @param prizes
     *            the prizes to set
     */
    public void setPrizes(List<Double> prizes) {
	this.prizes = prizes;
    }

    /**
     * @return the winPrize
     */
    public Prize getWinPrize() {
	return winPrize;
    }

    /**
     * @param winPrize
     *            the winPrize to set
     */
    public void setWinPrize(Prize winPrize) {
	this.winPrize = winPrize;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
	return grade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public void setGrade(String grade) {
	this.grade = grade;
    }

    /**
     * @return the conditions
     */
    public String getConditions() {
	return conditions;
    }

    /**
     * @param conditions
     *            the conditions to set
     */
    public void setConditions(String conditions) {
	this.conditions = conditions;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the entrants
     */
    public List<CardEntrant> getEntrants() {
	return entrants;
    }

    /**
     * @param entrants
     *            the entrants to set
     */
    public void setEntrants(List<CardEntrant> entrants) {
	this.entrants = entrants;
    }
}
