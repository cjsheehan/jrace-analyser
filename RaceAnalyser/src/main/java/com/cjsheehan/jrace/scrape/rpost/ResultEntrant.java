package com.cjsheehan.jrace.scrape.rpost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.racing.ConvertDistance;
import com.cjsheehan.jrace.racing.EntryState;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

public class ResultEntrant {
	final Logger log = LoggerFactory.getLogger(ResultEntrant.class);
	
	private String horseName;
	private int horseId;
	private int age;
	private int no;
	private String weight;
	private int draw;

	private int or;

	private String jockeyName;
	private int jockeyId;
	
	private int jockeyWeightClaim;
	
	private String trainerName;
	private int trainerId;
	private String performance;
	private double distanceBeaten;
	private double totalDistanceBeaten;
	private int finishPosition;
	private String startPrice;
	private EntryState runState;
	private Document doc;
	private Result raceResult;

	// jsoup selectors
	private static final String HORSE_NAME_SELECT = "td:nth-child(4) > span > b > a";
	private static final String HORSE_ID_SELECT = "tr:nth-child(2) > td:nth-child(4) > span > b > a";
	private static final String DRAW_SELECT = "tr:nth-child(2) > td.nowrap.noPad > span";
	private static final String NO_SELECT = "tr:nth-child(2) > td.nowrap.noPad > h3";
	private static final String WEIGHT_SELECT = "tr:nth-child(2) > td:nth-child(6) > span";
	private static final String OR_SELECT = "tr:nth-child(2) > td:nth-child(8)";
	private static final String AGE_SELECT = "tr:nth-child(2) > td:nth-child(5)";
	private static final String JOCKEY_NAME_SELECT = "tr:nth-child(3) > td.lightGray > a";
	private static final String JOCKEY_CLAIM_SELECT = "tr:nth-child(3) > td.lightGray > sup";
	private static final String JOCKEY_ID_SELECT = JOCKEY_NAME_SELECT;
	private static final String TRAINER_NAME_SELECT = "tr:nth-child(2) > td:nth-child(7) > a";
	private static final String TRAINER_ID_SELECT = TRAINER_NAME_SELECT;
	private static final String START_PRICE_SELECT = "tr:nth-child(2) > td:nth-child(4) > span";
	private static final String ENTRANTS_SELECT = "#re_ > table > tbody";
	private static final String DISTANCE_BEAT_SELECT = "td.dstDesc";
	private static final String RACE_POSITION_SELECT = "tr:nth-child(2) > td.nowrap.noPad > h3";

	// rx patterns
	private static final Pattern pWeight = Pattern.compile("(\\d+-\\d+)");
	private static final Pattern pHorseId = Pattern.compile("horse_id=(\\d+)");
	private static final Pattern pJockeyId = Pattern.compile("jockey_id=(\\d+)");
	private static final Pattern pTrainerId = Pattern.compile("trainer_id=(\\d+)");
	private static final Pattern pPrice = Pattern.compile("(\\d+/\\d+)");

	public ResultEntrant(Document doc, int entrantIdx, Result raceResult) throws ScrapeException {

		if (doc == null) {
			throw new IllegalArgumentException("doc cannot be null");
		}

		if (raceResult == null) {
			throw new IllegalArgumentException("raceResult cannot be null");
		}

		Elements entrants = doc.select(ENTRANTS_SELECT);

		if (entrantIdx < 0 || entrantIdx > entrants.size() - 1) {
			throw new IllegalArgumentException(
					String.format("entrantIdx %d is out of valid range : 0-%d", entrantIdx, entrants.size() - 1));
		}

		if (entrants.size() <= 0) {
			throw new IllegalArgumentException("document is not a valid race result, no entrants found");
		}

		this.raceResult = raceResult;
		this.doc = doc;
		Element entrant = entrants.get(entrantIdx);
		scrapeHorseName(entrant);
		scrapeHorseId(entrant);
		scrapeWeight(entrant);
		scrapeAge(entrant);
		if (raceResult.isFlat()) {
			scrapeNo(entrant);
			scrapeDraw(entrant);
		} else {
			setNo(-1);
			setDraw(-1);
		}

		scrapeOr(entrant);
		scrapeJockeyName(entrant);
		scrapeJockeyId(entrant);
		scrapeJockeyWeightClaim(entrant);
		scrapeTrainerName(entrant);
		scrapeTrainerId(entrant);
		scrapeStartPrice(entrant);
		scrapeResult(entrant); // position and runstate
		scrapeDistanceBeaten(entrant);
		calculateTotalDistanceBeaten(getFinishPosition());
	}

	private void scrapeHorseId(Element entrant) throws ScrapeException {
		String text = "";
		try {
			Element selected = entrant.select(HORSE_ID_SELECT).first();
			text = selected.attr("href");

		} catch (Exception e) {
			throw new ScrapeException("Horse ID", entrant.toString(), HORSE_ID_SELECT);
		}

		Matcher m = pHorseId.matcher(text);
		if (m.find()) {
			int id = Integer.parseInt(m.group(1));
			setHorseId(id);
		} else {
			throw new ScrapeException(String.format("Could not match %1 in text %2", pHorseId.toString(), text));
		}
	}

	private double[] createBeatenDistances(Document doc) {
		Elements distances = doc.select(DISTANCE_BEAT_SELECT);
		double[] sum = new double[distances.size()];
		for (int i = 0; i < sum.length; i++) {
			for (int j = 0; j <= i; j++) {
				String text = distances.get(j).ownText();
				if (StringUtils.isNotBlank(text)) {
					sum[i] += ConvertDistance.beatenToYards(text);
				}
			}
		}
		return sum;

	}

	private void scrapeDistanceBeaten(Element entrant) throws ScrapeException {
		String text = "";
		try {
			text = Scrape.text(entrant, DISTANCE_BEAT_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Beaten Distance", entrant.toString(), DISTANCE_BEAT_SELECT);
		}

		if (StringUtils.isNotBlank(text)) {
			// 2nd or worse
			double beaten = ConvertDistance.beatenToYards(text);
			setDistanceBeaten(beaten);
		} else {
			if (getFinishPosition() == 1) {
				// winner
				setDistanceBeaten(0);
			} else {
				// didn't compete/finish
				setDistanceBeaten(-1);
			}
		}

	}

	private double calculateTotalDistanceBeaten(int position) {
		double[] distances = createBeatenDistances(doc);
		double total = -1;
		if (position >= 1) {
			setTotalDistanceBeaten(distances[position - 1]);
		} else {
			// did npt run/finish
			setTotalDistanceBeaten(-1);
		}
		return total;
	}

	public static int countEntrants(Document doc) {
		if (doc == null) {
			throw new IllegalArgumentException("doc cannot be null");
		}
		// 1 is removed from size as last entry is just a separator
		return doc.select(ENTRANTS_SELECT).size() - 1;
	}

	private void scrapeResult(Element entrant) throws ScrapeException {
		String text = "";
		try {
			text = Scrape.text(entrant, RACE_POSITION_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Entry Result", entrant.toString(), RACE_POSITION_SELECT);
		}

		int pos = -1;
		try {
			pos = Integer.parseInt(text);
			setRunState(EntryState.FINISHED);
		} catch (NumberFormatException e) {
			// Horse is either 1st or did not finish/run
		}
		setFinishPosition(pos);

		if (pos < 1) {
			// TODO: need data on other text cases from RP
			if (text.equals("UR")) {
				setRunState(EntryState.UNSEATED_RIDER);
			} else if (text.equals("PU")) {
				setRunState(EntryState.PULLED_UP);
			} else if (text.equals("F")) {
				setRunState(EntryState.FELL);
			} else {
				throw new ScrapeException("Entry Result", entrant.toString(), RACE_POSITION_SELECT);
			}
		}

	}

	private void scrapeStartPrice(Element entrant) throws ScrapeException {
		String text = "";
		try {
			text = Scrape.text(entrant, START_PRICE_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Start Price", entrant.toString(), START_PRICE_SELECT);
		}

		Matcher m = pPrice.matcher(text);
		if (m.find()) {
			setStartPrice(m.group(1));
		} else {
			throw new ScrapeException(String.format("Could not match %1 in text %2", pPrice.toString(), text));
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
	}

	private void scrapeTrainerId(Element elem) throws ScrapeException {
		String text = "";
		try {
			Element selected = elem.select(TRAINER_ID_SELECT).first();
			text = selected.attr("href");

		} catch (Exception e) {
			throw new ScrapeException("Trainer ID", elem.toString(), TRAINER_ID_SELECT);
		}

		Matcher m = pTrainerId.matcher(text);
		if (m.find()) {
			int id = Integer.parseInt(m.group(1));
			setTrainerId(id);
		} else {
			throw new ScrapeException(String.format("Could not match %1 in text %2", pTrainerId.toString(), text));
		}
	}

	private void scrapeTrainerName(Element elem) throws ScrapeException {
		try {
			String name = Scrape.text(elem, TRAINER_NAME_SELECT);
			setTrainerName(name);
		} catch (Exception e) {
			throw new ScrapeException("Trainer Name", elem.toString(), TRAINER_NAME_SELECT);
		}
	}

	private void scrapeJockeyWeightClaim(Element elem) throws ScrapeException {
		try {
			int claim = Scrape.integer(elem, JOCKEY_CLAIM_SELECT);
			setJockeyWeightClaim(claim);
		} catch (ScrapeException e) {
			// This is OK, not all jockeys have a weight claim
			setJockeyWeightClaim(0);
		}
	}

	private void scrapeJockeyId(Element elem) throws ScrapeException {
		try {
			Element selected = elem.select(JOCKEY_ID_SELECT).first();
			String text = selected.attr("href");
			Matcher m = pJockeyId.matcher(text);
			if (m.find()) {
				int id = Integer.parseInt(m.group(1));
				setJockeyId(id);
			} else {
				throw new ScrapeException(String.format("Could not match %1 in text %2", pJockeyId.toString(), text));
			}
		} catch (Exception e) {
			throw new ScrapeException("Jockey ID", elem.toString(), JOCKEY_ID_SELECT);
		}
	}

	private void scrapeJockeyName(Element entrant) throws ScrapeException {
		try {
			String name = Scrape.text(entrant, JOCKEY_NAME_SELECT);
			setJockeyName(name);
		} catch (Exception e) {
			throw new ScrapeException("Jockey Name", entrant.toString(), JOCKEY_NAME_SELECT);
		}
	}

	private void scrapeDraw(Element entrant) throws ScrapeException {
		try {
			int draw = Scrape.integer(entrant, DRAW_SELECT);
			setDraw(draw);
		} catch (Exception e) {
			throw new ScrapeException("Draw", entrant.toString(), DRAW_SELECT);
		}

	}

	private void scrapeAge(Element entrant) throws ScrapeException {
		try {
			int age = Scrape.integer(entrant, AGE_SELECT);
			setAge(age);
		} catch (Exception e) {
			throw new ScrapeException("Age", entrant.toString(), AGE_SELECT);
		}

	}

	private void scrapeNo(Element entrant) throws ScrapeException {
		try {
			int no = Scrape.integer(entrant, NO_SELECT);
			setNo(no);
		} catch (Exception e) {
			throw new ScrapeException("Entry Number", entrant.toString(), NO_SELECT);
		}
	}

	private void scrapeWeight(Element entrant) throws ScrapeException {
		String text = "";
		try {
			text = Scrape.text(entrant, WEIGHT_SELECT);
		} catch (Exception e) {
			throw new ScrapeException("Weight", entrant.toString(), WEIGHT_SELECT);
		}

		Matcher m = pWeight.matcher(text);
		if (m.find()) {
			setWeight(m.group(1));
		} else {
			throw new ScrapeException("Failed to match Weight in:" + text + ", with pattern : " + pWeight.toString());
		}
	}

	private void scrapeHorseName(Element entrant) throws ScrapeException {
		try {
			String name = Scrape.text(entrant, HORSE_NAME_SELECT);
			setHorseName(name);
		} catch (Exception e) {
			throw new ScrapeException("Conditions", entrant.toString(), HORSE_NAME_SELECT);
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
	 * @return the jockeyWeightClaim
	 */
	public int getJockeyWeightClaim() {
		return jockeyWeightClaim;
	}

	/**
	 * @param jockeyWeightClaim
	 *            the jockeyWeightClaim to set
	 */
	public void setJockeyWeightClaim(int jockeyWeightClaim) {
		this.jockeyWeightClaim = jockeyWeightClaim;
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

	/**
	 * @return the performance
	 */
	public String getPerformance() {
		return performance;
	}

	/**
	 * @param performance
	 *            the performance to set
	 */
	public void setPerformance(String performance) {
		this.performance = performance;
	}

	/**
	 * @return the distanceBeaten
	 */
	public double getDistanceBeaten() {
		return distanceBeaten;
	}

	/**
	 * @param distanceBeaten
	 *            the distanceBeaten to set
	 */
	public void setDistanceBeaten(double distanceBeaten) {
		this.distanceBeaten = distanceBeaten;
	}

	/**
	 * @return the finishPosition
	 */
	public int getFinishPosition() {
		return finishPosition;
	}

	/**
	 * @param finishPosition
	 *            the finishPosition to set
	 */
	public void setFinishPosition(int finishPosition) {
		this.finishPosition = finishPosition;
	}

	/**
	 * @return the startPrice
	 */
	public String getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice
	 *            the startPrice to set
	 */
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	public EntryState getRunState() {
		return runState;
	}

	public void setRunState(EntryState state) {
		this.runState = state;
	}

	/**
	 * @return the totalDistanceBeaten
	 */
	public double getTotalDistanceBeaten() {
		return totalDistanceBeaten;
	}

	/**
	 * @param totalDistanceBeaten
	 *            the totalDistanceBeaten to set
	 */
	public void setTotalDistanceBeaten(double totalDistanceBeaten) {
		this.totalDistanceBeaten = totalDistanceBeaten;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Horse Name", horseName).append("Horse ID", horseId).append("Age", age)
				.append("No.", no).append("Weight", weight).append("OR", or).append("Draw", draw)
				.append("Jockey Name", jockeyName).append("Jockey ID", jockeyId)
				.append("Jockey Claim", jockeyWeightClaim).append("Trainer Name", trainerName)
				.append("Trainer ID", trainerId).append("DistBeat", distanceBeaten)
				.append("Total DistBeat", totalDistanceBeaten).append("Position", finishPosition)
				.append("Start Price", startPrice).append("Run State", runState.name()).toString();
	}
};
