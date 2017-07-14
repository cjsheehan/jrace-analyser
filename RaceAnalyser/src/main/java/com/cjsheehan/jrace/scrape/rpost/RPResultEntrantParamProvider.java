package com.cjsheehan.jrace.scrape.rpost;

import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.EntrantParamsProvider;

@Component
public class RPResultEntrantParamProvider implements EntrantParamsProvider {
	private static final String AGE_SELECT = "td[data-test-selector=horse-age]";
	private static final String HORSE_NAME_SELECT = "a[data-test-selector=link-horseName]";
	private static final String JOCKEY_NAME_SELECT = "a[data-test-selector=link-jockeyName]";
	private static final String TRAINER_NAME_SELECT = "a[data-test-selector=link-trainerName]";
	private static final String RATING_OR_SELECT = "td[data-ending=\"OR\"]";
	private static final String RATING_RPR_SELECT = "td[data-test-selector=full-result-rpr]";
	private static final String RATING_TS_SELECT = "td[data-test-selector=full-result-topspeed]";
	private static final String WEIGHT_ST_SELECT = "span[data-test-selector=horse-weight-st]";
	private static final String WEIGHT_LB_SELECT = "span[data-test-selector=horse-weight-lb]";
	private static final String WEIGHT_ALLOWANCE_SELECT = "span.rp-horseTable__human__wrapper > sup";

	@Override
	public String ageSelector() {
		return AGE_SELECT;
	}

	@Override
	public String horseSelector() {
		return HORSE_NAME_SELECT;
	}

	@Override
	public String jockeySelector() {
		return JOCKEY_NAME_SELECT;
	}

	@Override
	public String trainerSelector() {
		return TRAINER_NAME_SELECT;
	}

	@Override
	public String ratingOrSelector() {
		return RATING_OR_SELECT;
	}

	@Override
	public String ratingTsSelector() {
		return RATING_TS_SELECT;
	}

	@Override
	public String ratingRprSelector() {
		return RATING_RPR_SELECT;
	}

	@Override
	public String weightAllowanceSelector() {
		return WEIGHT_ALLOWANCE_SELECT;
	}

	@Override
	public String weightStSelector() {
		return WEIGHT_ST_SELECT;
	}

	@Override
	public String weightLbSelector() {
		return WEIGHT_LB_SELECT;
	}
}
