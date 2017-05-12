package com.cjsheehan.jrace.scrape.rpost;

import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.ResultParamProvider;

@Component
public class RPResultParamProvider implements ResultParamProvider {

	private static final String AGE_CONSTRAINT_SELECT= "span.rp-raceTimeCourseName_ratingBandAndAgesAllowed";
	private static final String COURSE_NAME_SELECT= "a.rp-raceTimeCourseName__name";
	private static final String GRADE_SELECT= "span.rp-raceTimeCourseName_class";
	private static final String GOING_SELECT= "span.rp-raceTimeCourseName_condition";
	private static final String TITLE_SELECT= "h2.rp-raceTimeCourseName__title";
	private static final String DATE_SELECT= "span.rp-raceTimeCourseName__date";
	private static final String TIME_SELECT= "span.rp-raceTimeCourseName__time";
	private static final String PRIZE_SELECT= "div[data-test-selector=RC-headerBox__winner] > div.RC-headerBox__infoRow__content";
	private static final String NUM_RUNNERS_SELECT= "div[data-test-selector=RC-headerBox__runners] > div.RC-headerBox__infoRow__content";
	private static final String DISTANCE_SELECT= "span.rp-raceTimeCourseName_distanceFull";
	private static final String DATE_FORMAT = "hh:mm aa, dd MMMM yyyy";
	private static final String WINNING_TIME_SELECT = "div.rp-raceInfo > ul > li:nth-child(1) > span.rp-raceInfo__value:nth-child(2)";
	private static final String NON_RUNNERS_SELECT = "";
	private static final String WINNING_TIME_FORMAT = "mm ss.SS";

	@Override
	public String ageConstraintSelector() {
		return AGE_CONSTRAINT_SELECT;
	}

	@Override
	public String courseNameSelector() {
		return COURSE_NAME_SELECT;
	}

	@Override
	public String gradeSelector() {
		return GRADE_SELECT;
	}

	@Override
	public String goingSelector() {
		return GOING_SELECT;
	}

	@Override
	public String titleSelector() {
		return TITLE_SELECT;
	}

	@Override
	public String dateSelector() {
		return DATE_SELECT;
	}

	@Override
	public String timeSelector() {
		return TIME_SELECT;
	}

	@Override
	public String prizeSelector() {
		return PRIZE_SELECT;
	}

	@Override
	public String numRunnersSelector() {
		return NUM_RUNNERS_SELECT;
	}

	@Override
	public String distanceSelector() {
		return DISTANCE_SELECT;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	
	@Override
	public String winningTimeSelector() {
		return WINNING_TIME_SELECT;
	}

	@Override
	public String nonRunnersSelector() {
		return NON_RUNNERS_SELECT;
	}

	@Override
	public String winningTimeFormat() {
		return WINNING_TIME_FORMAT;
	}

}
