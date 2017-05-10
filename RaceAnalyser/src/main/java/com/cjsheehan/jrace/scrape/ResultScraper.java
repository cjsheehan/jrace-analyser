package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.rpost.Result;

@Component
public interface ResultScraper {
	Result scrape(Document doc);
}
