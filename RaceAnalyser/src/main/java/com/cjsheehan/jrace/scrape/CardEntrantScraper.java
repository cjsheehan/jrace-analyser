package com.cjsheehan.jrace.scrape;

import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.CardEntrant;

@Component
public interface CardEntrantScraper {
	List<CardEntrant> scrape(Document doc);
}
