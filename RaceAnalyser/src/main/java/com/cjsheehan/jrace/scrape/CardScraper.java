package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Card;

@Component
public interface CardScraper {
	Card scrape(Document doc) throws ScrapeException;
}
