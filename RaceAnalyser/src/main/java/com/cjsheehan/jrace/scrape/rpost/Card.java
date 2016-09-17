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
import org.jsoup.select.Elements;

import com.cjsheehan.jrace.racing.Currency;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class Card {
    
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
    private static final String COURSE_SELECT = "span.placeRace";
    private static final String TIME_SELECT = "div.raceTitle span.navRace > span";
    private static final String DATE_SELECT = "div.raceTitle span.placeRace > span.date";
    private static final String WIN_PRIZE_SELECT = "div.raceInfo > ul > li:nth-child(1) > strong";
    private static final String NUM_RUNNERS_SELECT = "div.raceInfo > ul > li:nth-child(2) > strong";
    private static final String DISTANCE_SELECT = "div.raceInfo > ul > li:nth-child(3) > strong";
    private static final String GOING_SELECT = "div.raceInfo > ul > li:nth-child(4) > strong";
    private static final String TITLE_SELECT = "div.raceInfo > div > p > strong > strong";
    private static final String GRADE_SELECT = "div.raceInfo > div > p";
    private static final String CONDITIONS_SELECT = "div.raceInfo > div > p";
    private static final String ENTRANTS_SELECT = "#sc_horseCard > tbody > tr.cr";
    
    // pattern match
    static Pattern pGrade = Pattern.compile("(CLASS \\d{1})");
    static Pattern pCond = Pattern.compile("\\(.+\\) *\\((.+)\\)");
    static Pattern pRaceId = Pattern.compile("race_id[=_](\\d+)");
    
    private static final String DATE_FORMAT = "h:mm EEEE, dd MMMM yyyy";

    public Card(Document doc) throws ScrapeException {
	setRaceUrl(doc.location());
	scrapeRaceId(doc);
	scrapeCourse(doc);
	scrapeDate(doc);
	scrapePrize(doc);
	scrapeNumRunners(doc);
	scrapeDistance(doc);
	scrapeGoing(doc);
	scrapeTitle(doc);
	scrapeGrade(doc);
	scrapeConditions(doc);
	scrapeEntrants(doc);
    }
    

    private void scrapeRaceId(Document doc) throws ScrapeException {
	Matcher m = pRaceId.matcher(getRaceUrl());
	if(m.find()) {
	    int raceId;
	    try {
		raceId = Integer.parseInt(m.group(1));
		setRaceId(raceId);
	    } catch (NumberFormatException e) {
		throw new ScrapeException("Race ID", getRaceUrl(), "pattern: " + pRaceId.toString());
	    }
	} else {
	    throw new ScrapeException("Race ID", getRaceUrl(), "pattern: " + pRaceId.toString());
	}
    }


    private void scrapeConditions(Document doc) throws ScrapeException {
	try {
	    String conditions = Scrape.text(doc, CONDITIONS_SELECT);
	    setConditions(conditions);
	} catch (Exception e) {
	    throw new ScrapeException("Conditions", doc.toString(), CONDITIONS_SELECT);
	}
    }
    
    private void scrapeCourse(Document doc) throws ScrapeException {
	try {
	    String course = Scrape.text(doc, COURSE_SELECT);
	    setCourse(course);
	} catch (Exception e) {
	    throw new ScrapeException("Going", doc.toString(), GOING_SELECT);
	}
    }
    
    private void scrapeGrade(Document doc) throws ScrapeException {
	try {
	    String grade = Scrape.text(doc, GRADE_SELECT);
	    setGrade(grade);
	} catch (Exception e) {
	    throw new ScrapeException("Grade", doc.toString(), GRADE_SELECT);
	}
    }
    
    private void scrapeGoing(Document doc) throws ScrapeException {
	try {
	    String going = Scrape.text(doc, GOING_SELECT);
	    setGoing(going);
	} catch (Exception e) {
	    throw new ScrapeException("Going", doc.toString(), GOING_SELECT);
	}
    }
    
    private void scrapeTitle(Document doc) throws ScrapeException {
	try {
	    String title = Scrape.text(doc, TITLE_SELECT);
	    setTitle(title);
	} catch (Exception e) {
	    throw new ScrapeException("Title", doc.toString(), TITLE_SELECT);
	}
    }
    
    private void scrapeEntrants(Document doc) {
	List<CardEntrant> entrants = new ArrayList<>();
	Elements elems = doc.select(ENTRANTS_SELECT);
	for(Element selected : elems) {
	    CardEntrant entrant;
	    try {
		entrant = new CardEntrant(selected);
		entrants.add(entrant);
	    } catch (ScrapeException e) {
		e.printStackTrace();
	    }
	}
	
	setEntrants(entrants);
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
	} else {
	    throw new ScrapeException("Date", doc.toString(), DATE_SELECT);
	}

	Date dt = null;
	if (time != null && date != null) {
	    try {
		dt = new SimpleDateFormat(DATE_FORMAT).parse(time + " " + date);
	    } catch (ParseException e) {
		throw new ScrapeException("Failed to parse date format");
	    }
	}

	setDate(dt);
    }

    private void scrapePrize(Document doc) throws ScrapeException {
	Element elem = doc.select(WIN_PRIZE_SELECT).first();
	String prize = "";
	double prizeVal = 0;
	Currency cur = Currency.GBP;

	try {
	    if (elem != null) {
	        prize = elem.ownText();
		if (prize.contains("€")) {
		    cur = Currency.EUR;
		} else if (prize.contains("$")) {
		    cur = Currency.USD;
		}

	        prize = prize.replace("£", "").replace("€", "").replace(",", "");
	        prizeVal = Double.parseDouble(prize);
	        setWinPrize(new Prize(prizeVal, cur));
	    }
	} catch (NumberFormatException e) {
	    throw new ScrapeException("Winners Prize", doc.toString(),  WIN_PRIZE_SELECT);
	}
    }

    private void scrapeNumRunners(Document doc) throws ScrapeException {
	Element elem = doc.select(NUM_RUNNERS_SELECT).first();
	if (elem != null) {
	    int numRunners = Integer.parseInt(elem.ownText());
	    setNumRunners(numRunners);
	} else {
	    throw new ScrapeException("Number of Runners", doc.toString(),  NUM_RUNNERS_SELECT);
	}
    }
    
    private void scrapeDistance(Document doc) throws ScrapeException {
	Element elem = doc.select(DISTANCE_SELECT).first();
	if (elem != null) {
	    String dist = elem.ownText();
	    setDistance(new Distance(dist));
	} else {
	    throw new ScrapeException("Distance", doc.toString(),  DISTANCE_SELECT);
	}
    }
    
    
    /**
     * @return the raceId
     */
    public int getRaceId() {
        return raceId;
    }

    /**
     * @param raceId the raceId to set
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
     * @param raceUrl the raceUrl to set
     */
    public void setRaceUrl(String raceUrl) {
        this.raceUrl = raceUrl;
    }


    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
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
     * @param course the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * @return the numRunners
     */
    public int getNumRunners() {
        return numRunners;
    }

    /**
     * @param numRunners the numRunners to set
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
     * @param distance the distance to set
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
     * @param going the going to set
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
     * @param prizes the prizes to set
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
     * @param winPrize the winPrize to set
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
     * @param grade the grade to set
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
     * @param conditions the conditions to set
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
     * @param title the title to set
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
     * @param entrants the entrants to set
     */
    public void setEntrants(List<CardEntrant> entrants) {
        this.entrants = entrants;
    }

    /**
     * @return the pGrade
     */
    public static Pattern getpGrade() {
        return pGrade;
    }

    /**
     * @param pGrade the pGrade to set
     */
    public static void setpGrade(Pattern pGrade) {
        Card.pGrade = pGrade;
    }

    /**
     * @return the pCond
     */
    public static Pattern getpCond() {
        return pCond;
    }

    /**
     * @param pCond the pCond to set
     */
    public static void setpCond(Pattern pCond) {
        Card.pCond = pCond;
    }

    /**
     * @return the trackSelect
     */
    public static String getTrackSelect() {
        return COURSE_SELECT;
    }

    /**
     * @return the timeSelect
     */
    public static String getTimeSelect() {
        return TIME_SELECT;
    }

    /**
     * @return the dateSelect
     */
    public static String getDateSelect() {
        return DATE_SELECT;
    }

    /**
     * @return the winPrizeSelect
     */
    public static String getWinPrizeSelect() {
        return WIN_PRIZE_SELECT;
    }

    /**
     * @return the numRunnersSelect
     */
    public static String getNumRunnersSelect() {
        return NUM_RUNNERS_SELECT;
    }

    /**
     * @return the distanceSelect
     */
    public static String getDistanceSelect() {
        return DISTANCE_SELECT;
    }

    /**
     * @return the goingSelect
     */
    public static String getGoingSelect() {
        return GOING_SELECT;
    }

    /**
     * @return the titleSelect
     */
    public static String getTitleSelect() {
        return TITLE_SELECT;
    }

    /**
     * @return the gradeSelect
     */
    public static String getGradeSelect() {
        return GRADE_SELECT;
    }

    /**
     * @return the conditionsSelect
     */
    public static String getConditionsSelect() {
        return CONDITIONS_SELECT;
    }

    /**
     * @return the entrantsSelect
     */
    public static String getEntrantsSelect() {
        return ENTRANTS_SELECT;
    }

    /**
     * @return the dateFormat
     */
    public static String getDateFormat() {
        return DATE_FORMAT;
    }
    
    public String toString() {
	
	StringBuilder entrants = new StringBuilder();
	for(CardEntrant e : getEntrants()) {
	    entrants.append(e.toString() + "\n");
	}
	
	return new ToStringBuilder(this)
		.append("Course", getCourse())
		.append("Race ID", getRaceId())
		.append("Race URL", getRaceUrl())
		.append("Winning Prize", getWinPrize())
		.append("Runners", getNumRunners())
		.append("Distance", getDistance())
		.append("Going", getGoing())
		.append("Grade", getGrade())
		.append("Conditions", getConditions())
		.append("Title", getTitle())
		.append("\n")
		.append("Entrants", "\n" + entrants)
		.append("\n")
		.toString();
    }
}
