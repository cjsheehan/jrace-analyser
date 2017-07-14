package com.cjsheehan.jrace.scrape.rpost;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.EntrantParamsProvider;

@Primary
@Component
public class RPCardEntrantParamProvider implements EntrantParamsProvider {
	private static final String AGE_SELECT = "span[data-test-selector=RC-cardPage-runnerAge]";
	private static final String HORSE_NAME_SELECT = "a[data-test-selector=RC-cardPage-runnerName]";
	private static final String JOCKEY_NAME_SELECT = "a[data-test-selector=RC-cardPage-runnerJockey-name]";
	private static final String TRAINER_NAME_SELECT = "a[data-test-selector=RC-cardPage-runnerTrainer-name]";
	private static final String RATING_OR_SELECT = "span[data-test-selector=RC-racecardKey__Or]";
	private static final String RATING_RPR_SELECT = "span[data-test-selector=RC-racecardKey__Rpr]";
	private static final String RATING_TS_SELECT = "span[data-test-selector=RC-racecardKey__Ts]";
	private static final String WEIGHT_ST_SELECT = "span.RC-runnerWgt__carried_st";
	private static final String WEIGHT_LB_SELECT = "span.RC-runnerWgt__carried_lb";
	private static final String WEIGHT_ALLOWANCE_SELECT = "span[data-test-selector=RC-cardPage-runnerJockey-allowance]";

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
