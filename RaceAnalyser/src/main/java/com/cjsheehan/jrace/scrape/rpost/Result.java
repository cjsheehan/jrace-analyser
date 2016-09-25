package com.cjsheehan.jrace.scrape.rpost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cjsheehan.jrace.racing.ConvertDistance;
import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class Result {

    // core data
    private String course;
    private Date date;
    private int raceId;
    private String raceUrl;
    private int numRunners;
    private Distance distance;
    private Going going;
    private List<Double> prizes;
    private Prize winPrize;
    private String grade;
    private String conditions;
    private String title;
    private List<ResultEntrant> entrants;


    // jsoup selectors
    private static final String COURSE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > h1";
    private static final String TIME_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > h3 > span";
    private static final String DATE_SELECT = COURSE_SELECT;
    private static final String WIN_PRIZE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > ul > li:nth-child(2)";
    private static final String NUM_RUNNERS_SELECT = "#re_ > div.raceInfo > b:nth-child(1)";
    private static final String DISTANCE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > ul > li:nth-child(1)";
    private static final String GOING_SELECT = DISTANCE_SELECT;
    private static final String GRADE_SELECT = DISTANCE_SELECT;
    private static final String CONDITIONS_SELECT = DISTANCE_SELECT;
    private static final String TITLE_SELECT = "#mainwrapper > div > div > div.popUp > div.popUpHead.clearfix > div.leftColBig > h3";
    private static final String ENTRANTS_SELECT = "#re_ > table > tbody";
    private static final String SEPARATOR_SELECT = "tr > td.separator";
    
    private static final String IS_IRISH = "(IRE)";

    // pattern match
    static Pattern pGrade = Pattern.compile("(CLASS \\d{1})", Pattern.CASE_INSENSITIVE);
    static Pattern pCond = Pattern.compile("\\((.+?yo.+?)\\)");
    static Pattern pRaceId = Pattern.compile("race_id[=_](\\d+)");
    static Pattern pCourse = Pattern.compile("^(.+) Result");
    static Pattern pDate = Pattern.compile("Result (.+)$");
    static Pattern pRunners = Pattern.compile("(\\d+) ran");
    static Pattern pParenth = Pattern.compile("\\((.+?)\\)");
    static Pattern pDistance = Pattern.compile("^.+\\) (.+?) ");
    static Pattern pGoing = Pattern.compile("^.+\\) .+? (.+)\\d*");
    static Pattern pJumps = Pattern.compile("chase || hurdle", Pattern.CASE_INSENSITIVE);

    private static final String DATE_FORMAT = "h:mm, dd MMM yyyy";

    public Result(Document doc) throws ScrapeException {
	if(doc == null) {
	    throw new IllegalArgumentException("Document doc cannot be null");
	}
	
	setRaceUrl(doc.location());
	scrapeRaceId(doc);
	scrapeCourse(doc);
	scrapeDate(doc);
	scrapePrize(doc);
	scrapeNumRunners(doc);
	scrapeDistance(doc);
	scrapeGoing(doc);
	scrapeTitle(doc);
	if(!isIrish()) {
	    // Races in Ireland do not use grading system
	    scrapeGrade(doc);
	} else {
	    setGrade("N/A");
	}
	scrapeConditions(doc);
	scrapeEntrants(doc);
    }
    
    private void scrapeEntrants(Document doc) {
	List<ResultEntrant> entrants = new ArrayList<>();
	int numEntrants = ResultEntrant.countEntrants(doc);
	for (int i = 0; i < numEntrants; i++) {
		try {
		    ResultEntrant entrant = new ResultEntrant(doc, i, this);
		    entrants.add(entrant);
		} catch (ScrapeException e) {
		    e.printStackTrace();
		}
	}
	setEntrants(entrants);
    }

    private void scrapeConditions(Document doc) throws ScrapeException {
	    String text = null;
	    try {
		text = Scrape.text(doc, CONDITIONS_SELECT);
	    } catch (ScrapeException e) {
		throw new ScrapeException("Entry Conditions", doc.baseUri(), GRADE_SELECT);
	    }
	    
	    Matcher m = pParenth.matcher(text);
	    boolean found = false;
	    while(m.find() && !found) {
		if(m.group(1).contains("yo")) {
		    setConditions(m.group(1));
		    found = true;
		}
	    }
	    
	    if(!found) {
		throw new ScrapeException("Failed to match Entry Conditions", text, pCond.toString());
	    }
    }

    private void scrapeGrade(Document doc) throws ScrapeException {
	    String text = null;
	    try {
		text = Scrape.text(doc, GRADE_SELECT);
	    } catch (ScrapeException e) {
		throw new ScrapeException("Grade", doc.baseUri(), GRADE_SELECT);
	    }
	    
	    Matcher m = pGrade.matcher(text);
	    if(m.find()) {
		setGrade(m.group(1));
	    } else {
		throw new ScrapeException("Failed to match Grade in:" + text + ", with pattern : " + pGrade.toString() + ", with url: " + doc.baseUri());
	    }
    }

    private void scrapeTitle(Document doc) throws ScrapeException {
	Element elem = doc.select(TITLE_SELECT).first();
	if (elem != null) {
	    setTitle(elem.ownText());
	} else {
	    throw new ScrapeException("Title", doc.baseUri(), TITLE_SELECT);
	}
    }

    private void scrapeGoing(Document doc) throws ScrapeException {
	try {
	    String text = Scrape.text(doc, GOING_SELECT);
	    Going going = Going.parse(text);
	    setGoing(going);
	} catch (Exception e) {
	    throw new ScrapeException("Going", doc.baseUri(), DISTANCE_SELECT);
	}
    }

    private void scrapeDistance(Document doc) throws ScrapeException {
	// TODO : must handle edge case where actual
	// distance is supplied in parentheses aswell
	// as the common reported distance
	String text = Scrape.text(doc, DISTANCE_SELECT);
	
	// try edge case first where distance is supplied in both
	// parentheses and as a race distance. e.g.
	// (Class 1) (7yo+) (4m2f74y) 4m2½f 
	
	Matcher m = pParenth.matcher(text);
	boolean found = false;
	while(m.find() && !found) {
	    if(ConvertDistance.isValid(m.group(1))) {
		setDistance(new Distance(m.group(1)));
		found = true;
	    }
	}
	
	// If not found, try standard case where race distance
	// comes after conditions e.g.
	// (Class 1) (7yo+) 4m2½f or // (Class 1) (7yo+) 4m2f 
	// NOTE: there is possibility that distance will contain
	// unicode vulgar fraction 
	if(!found) {
	    m = pDistance.matcher(text);
	    String d = "";
	    if (m.find()) {
		try {
		    d = m.group(1);
		    Distance distance = new Distance(m.group(1));
		    setDistance(distance);
		} catch (IllegalArgumentException e) {
		    throw new ScrapeException("Invalid distance param: " + d);
		} catch (Exception e) {
		    throw new ScrapeException("Distance", doc.baseUri(), DISTANCE_SELECT);
		}
	    }
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
		    throw new ScrapeException("Number of Runners", doc.baseUri(), NUM_RUNNERS_SELECT);
		}
	    }
	} catch (Exception e) {
	    throw new ScrapeException("Course", doc.baseUri(), NUM_RUNNERS_SELECT);
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
	    throw new ScrapeException("Winners Prize", doc.baseUri(), WIN_PRIZE_SELECT);
	}
    }

    private void scrapeDate(Document doc) throws ScrapeException {
	Element timeElem = doc.select(TIME_SELECT).first();
	String time = null;
	if (timeElem != null) {
	    time = timeElem.ownText();
	} else {
	    throw new ScrapeException("Time", doc.baseUri(), TIME_SELECT);
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
	    throw new ScrapeException("Date", doc.baseUri(), DATE_SELECT);
	}

	Date dt = null;
	if (time != null && date != null) {
	    try {
		dt = new SimpleDateFormat(DATE_FORMAT).parse(time + ", " + date);
	    } catch (ParseException e) {
		throw new ScrapeException("Failed to parse date format");
	    }
	} else {
	    throw new ScrapeException("Date/Time", doc.baseUri(), DATE_SELECT);
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
	    throw new ScrapeException("Course", doc.baseUri(), COURSE_SELECT);
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
    
    public boolean isFlat() {
	Matcher m = pJumps.matcher(getTitle());
	if(m.find()) {
	    return false;
	} else {
	    return true;
	}
    }
    
    public boolean isIrish() {
	if(getCourse().contains(IS_IRISH)) {
	    return true;
	} else {
	    return false;
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
    public Going getGoing() {
	return going;
    }

    /**
     * @param going
     *            the going to set
     */
    public void setGoing(Going going) {
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
    public List<ResultEntrant> getEntrants() {
	return entrants;
    }

    /**
     * @param entrants
     *            the entrants to set
     */
    public void setEntrants(List<ResultEntrant> entrants) {
	this.entrants = entrants;
    }
    
    public String toString() {
	
	StringBuilder entrants = new StringBuilder();
	for(ResultEntrant e : getEntrants()) {
	    entrants.append(e.toString() + "\n");
	}
	
	return new ToStringBuilder(this)
		.append("Course", getCourse())
		.append("Date", getDate().toString())
		.append("Race ID", getRaceId())
		.append("Race URL", getRaceUrl())
		.append("Runners", getNumRunners())
		.append("Distance", getDistance())
		.append("Going", getGoing().name())
		.append("Winning Prize", getWinPrize())
		.append("Grade", getGrade())
		.append("Runners", getNumRunners())
		.append("Conditions", getConditions())
		.append("Title", getTitle())
		.append("\n")
		.append("Entrants", "\n" + entrants)
		.append("\n")
		.toString();
    }
}
