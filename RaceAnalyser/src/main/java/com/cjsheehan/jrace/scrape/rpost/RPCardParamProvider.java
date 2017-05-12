package com.cjsheehan.jrace.scrape.rpost;

import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.StandardParamProvider;

@Component
public class RPCardParamProvider implements StandardParamProvider {

	private static final String AGE_CONSTRAINT_SELECT= "span[data-test-selector=RC-header__rpAges]";
	private static final String COURSE_NAME_SELECT= "a.RC-courseHeader__name";
	private static final String GRADE_SELECT= "span[data-test-selector=RC-header__raceClass]";
	private static final String GOING_SELECT= "div[data-test-selector=RC-headerBox__going] > div.RC-headerBox__infoRow__content";
	private static final String TITLE_SELECT= "span[data-test-selector=RC-header__raceInstanceTitle]";
	private static final String DATE_SELECT= "span.RC-courseHeader__date";
	private static final String TIME_SELECT= "span[data-race-time]";
	private static final String PRIZE_SELECT= "div[data-test-selector=RC-headerBox__winner] > div.RC-headerBox__infoRow__content";
	private static final String NUM_RUNNERS_SELECT= "div[data-test-selector=RC-headerBox__runners] > div.RC-headerBox__infoRow__content";
	private static final String DISTANCE_SELECT= "span[data-test-selector=RC-header__raceDistance]";
	private static final String DATE_FORMAT = "hh:mm aa, dd MMMM yyyy";
	
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

}
