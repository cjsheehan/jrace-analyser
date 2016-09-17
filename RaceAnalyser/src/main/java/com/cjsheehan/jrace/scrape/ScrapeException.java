package com.cjsheehan.jrace.scrape;

public class ScrapeException extends Exception {
    private static final long serialVersionUID = 1L;
    public ScrapeException(String message) {
	super(message);
    }
    
    public ScrapeException(String target, String source, String selector) {
	super(target + " could not be scraped using selector: " + selector + " \nfrom :" + source);
    }
}
