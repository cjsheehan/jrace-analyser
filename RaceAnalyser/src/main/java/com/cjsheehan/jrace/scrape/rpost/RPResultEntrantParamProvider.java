package com.cjsheehan.jrace.scrape.rpost;

import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.EntrantParamsProvider;

@Component
public class RPResultEntrantParamProvider implements EntrantParamsProvider {
	private static final String AGE_SELECT = "td[data-test-selector=horse-age]";
	private static final String HORSE_NAME_SELECT = "a[data-test-selector=link-horseName]";
	private static final String JOCKEY_NAME_SELECT = "a[data-test-selector=link-jockeyName]";
	private static final String TRAINER_NAME_SELECT = "a[data-test-selector=link-trainerName]";
	private static final String RATING_OR_SELECT = "td[data-ending=OR]";
	private static final String RATING_RPR_SELECT = "td[data-ending=RPR]";
	private static final String RATING_TS_SELECT = "td[data-ending=TS]";
	private static final String WEIGHT_SELECT = "td.rp-horseTable__wgt";
	private static final String WEIGHT_ALLOWANCE_SELECT = "div.rp-horseTable__human > sup";

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
	public String weightSelector() {
		return WEIGHT_SELECT;
	}

	@Override
	public String weightAllowanceSelector() {
		return WEIGHT_ALLOWANCE_SELECT;
	}
}
