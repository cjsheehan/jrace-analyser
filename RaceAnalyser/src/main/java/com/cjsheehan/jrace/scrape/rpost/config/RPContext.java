package com.cjsheehan.jrace.scrape.rpost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.RaceEntrantDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPCardEntrantParamProvider;
import com.cjsheehan.jrace.scrape.rpost.RPCardParamProvider;
import com.cjsheehan.jrace.scrape.rpost.RPEntrantDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPRaceDataScraper;
import com.cjsheehan.jrace.scrape.rpost.RPResultEntrantParamProvider;
import com.cjsheehan.jrace.scrape.rpost.RPResultParamProvider;

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

	@Bean
	public RaceEntrantDataScraper cardEntrantDataScraper() {
		return new RPEntrantDataScraper(new RPCardEntrantParamProvider());
	}

	@Bean
	public RaceEntrantDataScraper resultEntrantDataScraper() {
		return new RPEntrantDataScraper(new RPResultEntrantParamProvider());
	}


}
