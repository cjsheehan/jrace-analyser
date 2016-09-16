package com.cjsheehan.jrace;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.cjsheehan.jrace.scrape.Html;
import com.cjsheehan.jrace.scrape.ScrapeException;
import com.cjsheehan.jrace.scrape.rpost.Card;

public class App {
    public static void main(String[] args) {
//	String url = "http://www.racingpost.com/horses2/cards/card.sd?race_id=657619&r_date=2016-09-16";
	String localUrl = "./html/card.html";
	Document doc = null;
	try {
	    doc = Html.toDocument(localUrl);
	    Card card = null;
	    try {
		card = new Card(doc);
	    } catch (ScrapeException e) {
		System.out.println("ERROR: \n" + e.getMessage());
		e.printStackTrace();
	    }
	    
	    System.out.println("RaceCard: \n" + card.toString());
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
