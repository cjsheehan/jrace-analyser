package com.cjsheehan.jrace.scrape;

import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.ResultEntrant;

@Component
public interface ResultEntrantScraper {
	List<ResultEntrant> scrapeEntrants(Document doc);
}
