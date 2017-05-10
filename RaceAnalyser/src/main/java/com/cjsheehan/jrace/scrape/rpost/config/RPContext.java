package com.cjsheehan.jrace.scrape.rpost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPCardDataSelector;
import com.cjsheehan.jrace.scrape.rpost.RPRaceDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPResultDataSelector;

@Configuration
public class RPContext {
	@Bean
	public RaceDataScraper cardDataScraper() {
		return new RPRaceDataScraper(new RPCardDataSelector());
	}

	@Bean
	public RaceDataScraper resultDataScraper() {
		return new RPRaceDataScraper(new RPResultDataSelector());
	}

}
