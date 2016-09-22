package com.cjsheehan.jrace;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.scrape.Html;
import com.cjsheehan.jrace.scrape.ScrapeException;
import com.cjsheehan.jrace.scrape.rpost.Card;
import com.cjsheehan.jrace.scrape.rpost.Result;

public class App {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
	final Logger logger = LoggerFactory.getLogger(App.class);
	String remoteCardUrl = "http://www.racingpost.com/horses2/cards/card.sd?race_id=659479&r_date=2016-09-19";
	String localCardUrl = "./html/card_race_id_657619.html";
	
	String remoteResultUrl = "http://www.racingpost.com/horses/result_home.sd?race_id=642825&r_date=2016-04-09";
	String localResultUrl = "./html/result_race_id_657619.html";
	
	String url = remoteResultUrl;
	Document doc = null;
	try {
	    doc = Html.toDocument(url);
	    Result result = null;
	    try {
		result = new Result(doc);
	    } catch (ScrapeException e) {
		System.out.println("ERROR: \n" + e.getMessage());
		e.printStackTrace();
	    }
	    
	    System.out.println("Result: \n" + result.toString());
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
