package com.cjsheehan.jrace.scrape;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scrape {
     
    public static String text(Document doc, String selector)  throws ScrapeException {
	Element selected = doc.select(selector).first();
	String text = null;
	if (selected != null) {
	    text = selected.ownText();
	} else {
	    throw new ScrapeException("Could not scrape valid text from document with selector: " + selector);
	}
	
	return text;
    }
    
    
    public static String text(Element root, String selector)  throws ScrapeException {
	Element selected = root.select(selector).first();
	String text = null;
	if (selected != null) {
	    text = selected.ownText();
	} else {
	    throw new ScrapeException("Could not scrape valid text from root with selector: " + selector);
	}
	return text;
    }
    
    public static int integer(Element root, String selector) throws ScrapeException {
	Element selected = root.select(selector).first();
	int integer = 0;
	if (selected != null) {
	    String text = selected.ownText();
	    try {
		integer = Integer.parseInt(text);
	    } catch (NumberFormatException e) {
		throw new ScrapeException("Could not scrape valid integer from root element with selector: " + selector);
	    }
	}
	return integer;
    }
    
}
