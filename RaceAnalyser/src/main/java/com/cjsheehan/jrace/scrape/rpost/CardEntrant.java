package com.cjsheehan.jrace.scrape.rpost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.CDBF;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Rating;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class CardEntrant {
	final Logger log = LoggerFactory.getLogger(CardEntrant.class);
	
	private String horseName;
	private int horseId;
	private int age;
	private int no;
	private String weight;
	private int draw;

	private Rating rating = new Rating();

	private int lastRan;
	
	private Jockey jockey;
	private Trainer trainer;

	private int weightClaim;
	private CDBF entrantCDBF;

	// jsoup selectors
	private static final String HORSE_NAME_SELECT = "tr.cr > td:nth-child(3) > a > b";
	private static final String DRAW_SELECT = "tr.cr > td.t > sup";
	private static final String NO_SELECT = "tr.cr > td.t > strong";
	private static final String WEIGHT_SELECT = "tr.cr > td:nth-child(5) > div:nth-child(1)";
	private static final String OR_SELECT = "tr.cr > td:nth-child(5) > div:nth-child(2)";
	private static final String RPR_SELECT = "tr.cr > td:nth-child(8)";
	private static final String TS_SELECT = "tr.cr > td:nth-child(7)";
	private static final String AGE_SELECT = "tr.cr > td.c";
	private static final String LAST_RAN_SELECT = "tr.cr > td:nth-child(3) > div > span";
	private static final String CDBF_SELECT = "tr.cr > td:nth-child(3) > div > span.ico > img";
	private static final String JOCKEY_NAME_SELECT = "tr.cr > td:nth-child(6) > div:nth-child(1) > a";
	private static final String JOCKEY_CLAIM_SELECT = "tr.cr > td:nth-child(6) > div:nth-child(1) > sup";
	private static final String JOCKEY_ID_SELECT = JOCKEY_NAME_SELECT;
	private static final String TRAINER_NAME_SELECT = "tr.cr > td:nth-child(6) > div:nth-child(2) > a";
	private static final String TRAINER_ID_SELECT = TRAINER_NAME_SELECT;

	// rx patterns
	private static final Pattern pCDBF = Pattern.compile("ico/distance-(.+)\\.gif");
	private static final Pattern pJockeyId = Pattern.compile("jockey_id=(\\d+)");
	private static final Pattern pTrainerId = Pattern.compile("trainer_id=(\\d+)");

	// const strings
	private static final String DISTANCE = "d";
	private static final String COURSE_AND_DISTANCE = "cd";
	private static final String COURSE = "c";
	private static final String BEATEN_FAVOURITE = "bf";

//	public CardEntrant(Element entrant) throws ScrapeException {
//		scrapeHorseName(entrant);
//		scrapeWeight(entrant);
//		scrapeOr(entrant);
//		scrapeRpr(entrant);
//		scrapeTs(entrant);
//		scrapeNo(entrant);
//		scrapeAge(entrant);
//		scrapeDraw(entrant);
//		scrapeLastRan(entrant);
//		scrapeCDBF(entrant);
//		scrapeJockeyWeightClaim(entrant);
//		scrapeJockey(entrant);
//		scrapeTrainer(entrant);
//	}

	private long scrapeTrainerId(Element elem) throws ScrapeException {
		long id = -1L;
		try {
			Element selected = elem.select(TRAINER_ID_SELECT).first();
			String text = selected.attr("href");
			Matcher m = pTrainerId.matcher(text);
			if (m.find()) {
				id = Long.parseLong(m.group(1));
			} else {
				throw new ScrapeException(String.format("Could not match %1 in text %2", pTrainerId.toString(), text));
			}
		} catch (Exception e) {
			throw new ScrapeException("Trainer ID", elem.toString(), TRAINER_ID_SELECT);
		}
		
		return id;
	}

	private String scrapeTrainerName(Element elem) throws ScrapeException {
		try {
			return Scrape.text(elem, TRAINER_NAME_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Trainer Name", elem.toString(), TRAINER_NAME_SELECT);
		}
	}

	private void scrapeJockey(Element elem) throws ScrapeException {
		long id = scrapeJockeyId(elem);
		String name = scrapeJockeyName(elem);
		jockey = new Jockey(name, id);
	}
	
	private void scrapeTrainer(Element elem) throws ScrapeException {
		long id = scrapeTrainerId(elem);
		String name = scrapeTrainerName(elem);
		trainer = new Trainer(name, id);
	}
	
	private long scrapeJockeyId(Element elem) throws ScrapeException {
		long id = -1L;
		try {
			Element selected = elem.select(JOCKEY_ID_SELECT).first();
			String text = selected.attr("href");
			Matcher m = pJockeyId.matcher(text);
			if (m.find()) {
				id = Long.parseLong(m.group(1));
			} else {
				throw new ScrapeException(String.format("Could not match %1 in text %2", pJockeyId.toString(), text));
			}
		} catch (Exception e) {
			throw new ScrapeException("Jockey ID", elem.toString(), JOCKEY_ID_SELECT);
		}
		
		return id;
	}

	private String scrapeJockeyName(Element elem) throws ScrapeException {
		try {
			return Scrape.text(elem, JOCKEY_NAME_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Jockey Name", elem.toString(), JOCKEY_NAME_SELECT);
		}
	}

	private void scrapeJockeyWeightClaim(Element elem) throws ScrapeException {
		try {
			int claim = Scrape.integer(elem, JOCKEY_CLAIM_SELECT);
			setJockeyWeightClaim(claim);
		} catch (Exception e) {
			// This is OK, not all jockeys have a weight claim
			setJockeyWeightClaim(0);
		}
	}

	private void scrapeCDBF(Element elem) throws ScrapeException {
		Elements images = elem.select(CDBF_SELECT);
		entrantCDBF = new CDBF(false, false, false, false);
		if (images.size() > 0) {
			for (Element image : images) {
				Matcher m = pCDBF.matcher(image.absUrl("src"));

				if (m.find()) {
					switch (m.group(1)) {

					case COURSE:
						entrantCDBF.setWonAtCourse(true);
						break;

					case DISTANCE:
						entrantCDBF.setWonAtDistance(true);
						break;

					case COURSE_AND_DISTANCE:
						entrantCDBF.setWonAtCourseAndDistance(true);
						break;

					case BEATEN_FAVOURITE:
						entrantCDBF.setBeatenFavourite(true);
						break;

					default:

						break;
					}
				}
			}
			setCdbf(entrantCDBF);
		} else {
			setCdbf(entrantCDBF);
		}
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

	private void scrapeRpr(Element elem) throws ScrapeException {
		try {
			String rpr = Scrape.text(elem, RPR_SELECT);
			if (rpr.equals("�")) {
				rating.setRpRating(Rating.NO_RATING);
			} else {
				rating.setRpRating(Integer.parseInt(rpr));
			}
		} catch (Exception e) {
			throw new ScrapeException("RPR", elem.toString(), RPR_SELECT);
		}
	}

	private void scrapeOr(Element elem) throws ScrapeException {

		try {
			String or = Scrape.text(elem, OR_SELECT);
			if (or.equals("�")) {
				rating.setOfficialRating(Rating.NO_RATING);
			} else {
				rating.setOfficialRating(Integer.parseInt(or));
			}
		} catch (Exception e) {
			throw new ScrapeException("OR", elem.toString(), OR_SELECT);
		}
	}

	private void scrapeTs(Element elem) throws ScrapeException {

		try {
			String ts = Scrape.text(elem, TS_SELECT);
			if (ts.equals("�")) {
				rating.setTsRating(Rating.NO_RATING);
			} else {
				rating.setTsRating(Integer.parseInt(ts));
			}
		} catch (Exception e) {
			throw new ScrapeException("TS", elem.toString(), TS_SELECT);
		}
	}

	private void scrapeLastRan(Element elem) throws ScrapeException {
		String lastRan;
		try {
			lastRan = Scrape.text(elem, LAST_RAN_SELECT);
			setLastRan(Integer.parseInt(lastRan));
		} catch (Exception e) {
			setLastRan(-1);
		}
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
	 * @return the lastRan
	 */
	public int getLastRan() {
		return lastRan;
	}

	/**
	 * @param lastRan
	 *            the lastRan to set
	 */
	public void setLastRan(int lastRan) {
		this.lastRan = lastRan;
	}

	public CDBF getCdbf() {
		return entrantCDBF;
	}

	public void setCdbf(CDBF cdbf) {
		this.entrantCDBF = cdbf;
	}

	/**
	 * @return the jockeyWeightClaim
	 */
	public int getJockeyWeightClaim() {
		return weightClaim;
	}

	/**
	 * @param jockeyWeightClaim
	 *            the jockeyWeightClaim to set
	 */
	public void setJockeyWeightClaim(int jockeyWeightClaim) {
		this.weightClaim = jockeyWeightClaim;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("Horse Name", horseName).append("Horse ID", horseId).append("Age", age)
				.append("No.", no).append("Weight", weight).append("RATING", rating.toString())
				.append("Last Ran", lastRan).append("Jockey", jockey.toString())
				.append("Weight Claim", weightClaim).append("Trainer", trainer.toString())
				.append("CDBF", entrantCDBF.toString()).toString();
	}

}
