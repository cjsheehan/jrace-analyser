package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Card;
import com.cjsheehan.jrace.scrape.rpost.RPCardDataScraper;

@Component
public interface CardScraper {
	Card scrape(Document doc);
}
