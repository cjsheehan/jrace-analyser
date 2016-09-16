package com.cjsheehan.jrace.scrape.rpost;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;

import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class CardEntrant {

    private String weight;
    private int or;
    private int rpr;
    private int ts;
    private int age;
    private int draw;
    private int no;
    private String horseName;
    private int horseId;
    private String jockeyName;
    private int jockeyId;
    private String trainerName;
    private int trainerId;

    // jsoup selectors
    private static final String HORSE_NAME_SELECT = "tr.cr > td:nth-child(3) > a > b";
    private static final String DRAW_SELECT = "tr.cr > td.t > sup";
    private static final String NO_SELECT = "tr.cr > td.t > strong";
    private static final String WEIGHT_SELECT = "tr.cr > td:nth-child(5) > div:nth-child(1)";
    private static final String OR_SELECT = "tr.cr > td:nth-child(5) > div:nth-child(2)";
    private static final String RPR_SELECT = "";
    private static final String TS_SELECT = "";
    private static final String AGE_SELECT = "tr.cr > td.c";
    
    public CardEntrant(Element entrant) throws ScrapeException {
	scrapeHorseName(entrant);
	scrapeWeight(entrant);
	scrapeOr(entrant);
	scrapeNo(entrant);
	scrapeAge(entrant);
	scrapeDraw(entrant);
    }
    
    private void scrapeHorseName(Element elem) throws ScrapeException {
	try {
	    String name = Scrape.text(elem, HORSE_NAME_SELECT);
	    setHorseName(name);
	} catch (Exception e) {
	    throw new ScrapeException("Conditions", elem.toString(), HORSE_NAME_SELECT);
	}
    }
    
    private void scrapeWeight(Element elem) throws ScrapeException {
	try {
	    String weight = Scrape.text(elem, WEIGHT_SELECT);
	    setWeight(weight);
	} catch (Exception e) {
	    throw new ScrapeException("weight", elem.toString(), WEIGHT_SELECT);
	}
    }
    
    private void scrapeNo(Element elem) throws ScrapeException {
	try {
	    int no = Scrape.integer(elem, NO_SELECT);
	    setNo(no);

	} catch (Exception e) {
	    throw new ScrapeException("Entry Number", elem.toString(), NO_SELECT);
	}
    }
    
    private void scrapeAge(Element elem) throws ScrapeException {
	try {
	    int age = Scrape.integer(elem, AGE_SELECT);
	    setAge(age);
	} catch (Exception e) {
	    throw new ScrapeException("Age", elem.toString(), AGE_SELECT);
	}

    }
    
    private void scrapeDraw(Element elem) throws ScrapeException {
	try {
	    int draw = Scrape.integer(elem, DRAW_SELECT);
	    setDraw(draw);
	} catch (Exception e) {
	    throw new ScrapeException("Age", elem.toString(), DRAW_SELECT);
	}

    }
    
    private void scrapeOr(Element elem) throws ScrapeException {

	try {
	    String or = Scrape.text(elem, OR_SELECT);
	    if (or.equals("—")) {
		setOr(-1);
	    } else {
		setOr(Integer.parseInt(or));
	    }
	} catch (Exception e) {
	    throw new ScrapeException("OR", elem.toString(), OR_SELECT);
	}
	
	setOr(or);
    }

    /**
     * @return the weight
     */
    public String getWeight() {
	return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(String weight) {
	this.weight = weight;
    }

    /**
     * @return the or
     */
    public int getOr() {
	return or;
    }

    /**
     * @param or
     *            the or to set
     */
    public void setOr(int or) {
	this.or = or;
    }

    /**
     * @return the rpr
     */
    public int getRpr() {
	return rpr;
    }

    /**
     * @param rpr
     *            the rpr to set
     */
    public void setRpr(int rpr) {
	this.rpr = rpr;
    }

    /**
     * @return the ts
     */
    public int getTs() {
	return ts;
    }

    /**
     * @param ts
     *            the ts to set
     */
    public void setTs(int ts) {
	this.ts = ts;
    }

    /**
     * @return the age
     */
    public int getAge() {
	return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(int age) {
	this.age = age;
    }

    /**
     * @return the draw
     */
    public int getDraw() {
	return draw;
    }

    /**
     * @param draw
     *            the draw to set
     */
    public void setDraw(int draw) {
	this.draw = draw;
    }

    /**
     * @return the no
     */
    public int getNo() {
	return no;
    }

    /**
     * @param no
     *            the no to set
     */
    public void setNo(int no) {
	this.no = no;
    }

    /**
     * @return the horseName
     */
    public String getHorseName() {
	return horseName;
    }

    /**
     * @param horseName
     *            the horseName to set
     */
    public void setHorseName(String horseName) {
	this.horseName = horseName;
    }

    /**
     * @return the horseId
     */
    public int getHorseId() {
	return horseId;
    }

    /**
     * @param horseId
     *            the horseId to set
     */
    public void setHorseId(int horseId) {
	this.horseId = horseId;
    }

    /**
     * @return the jockeyName
     */
    public String getJockeyName() {
	return jockeyName;
    }

    /**
     * @param jockeyName
     *            the jockeyName to set
     */
    public void setJockeyName(String jockeyName) {
	this.jockeyName = jockeyName;
    }

    /**
     * @return the jockeyId
     */
    public int getJockeyId() {
	return jockeyId;
    }

    /**
     * @param jockeyId
     *            the jockeyId to set
     */
    public void setJockeyId(int jockeyId) {
	this.jockeyId = jockeyId;
    }

    /**
     * @return the trainerName
     */
    public String getTrainerName() {
	return trainerName;
    }

    /**
     * @param trainerName
     *            the trainerName to set
     */
    public void setTrainerName(String trainerName) {
	this.trainerName = trainerName;
    }

    /**
     * @return the trainerId
     */
    public int getTrainerId() {
	return trainerId;
    }

    /**
     * @param trainerId
     *            the trainerId to set
     */
    public void setTrainerId(int trainerId) {
	this.trainerId = trainerId;
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this)
		.append("name", horseName)
		.append("age", age)
		.append("no", no)
		.append("weight", weight)
		.append("or", or)
		.toString();
    }

}
