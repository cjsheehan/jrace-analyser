package com.cjsheehan.jrace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.scrape.Html;
import com.cjsheehan.jrace.scrape.ScrapeException;
import com.cjsheehan.jrace.scrape.rpost.Card;
import com.cjsheehan.jrace.scrape.rpost.Result;

public class App {
    static final String HTML_LOC = "./html";
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
	final Logger log = LoggerFactory.getLogger(App.class);
 
	String remoteCardUrl = "http://www.racingpost.com/horses2/cards/card.sd?race_id=659479&r_date=2016-09-19";
	String localCardUrl = "./html/card_race_id_657619.html";
	
	
	String remoteResultUrl = "http://www.racingpost.com/horses/result_home.sd?race_id=642825&r_date=2016-04-09";
	String[] remoteResultUrls = {
		"http://www.racingpost.com/horses/result_home.sd?race_id=657920",
		"http://www.racingpost.com/horses/result_home.sd?race_id=659125",
		"http://www.racingpost.com/horses/result_home.sd?race_id=642825",
		"http://www.racingpost.com/horses/result_home.sd?race_id=641994",
		
		};
	
	String localResultUrl = "./html/result_race_id_657619.html";
	
	File[] files = new File(HTML_LOC).listFiles();
	List<String> localResultUrls = new ArrayList<>();
	for (File file : files) {
	    String name = file.getAbsolutePath();
	    if (file.isFile() && name.contains("result_race_id")) {
		localResultUrls.add(name);
	    }
	}
	
	List<String> urls = localResultUrls;
	
	for (String url : urls) {
		Document doc = null;
		try {
		    doc = Html.toDocument(url);
		    Result result = null;
		    try {
			result = new Result(doc);
		    } catch (ScrapeException e) {
			log.error("ERROR: doc.baseUri() + \n" + e.getMessage());
		    }
		    
		    log.info(doc.baseUri() + "\n" + result.toString());
		    
		} catch (IOException e) {
		    log.error(url + "\n" + e.getMessage());
		}
	}
    }
}
