package com.cjsheehan.jrace.scrape.rpost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cjsheehan.jrace.scrape.ResultDataScraper;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPCardParamProvider;
import com.cjsheehan.jrace.scrape.rpost.RPResultDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPResultParamProvider;
import com.cjsheehan.jrace.scrape.rpost.RPRaceDataScraper;

@Configuration
public class RPContext {
	
	@Bean
	public RaceDataScraper cardDataScraper() {
		return new RPRaceDataScraper(new RPCardParamProvider());
	}

	@Bean
	public RaceDataScraper resultDataScraper() {
		return new RPRaceDataScraper(new RPResultParamProvider());
	}

}
