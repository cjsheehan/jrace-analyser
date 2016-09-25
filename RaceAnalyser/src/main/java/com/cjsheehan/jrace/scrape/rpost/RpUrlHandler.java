package com.cjsheehan.jrace.scrape.rpost;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.RacingUrlHandler;

public class RpUrlHandler implements RacingUrlHandler {
    final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @Override
    public String createCardUrl(int id) {
	return Constants.CARD_URL + "?race_id=" + id;
    }
    
    @Override
    public String createResultUrl(int id) {
	return Constants.RESULT_URL + "?race_id=" + id;
    }

    @Override
    public String[] createCardUrls(int[] ids) {
	String[] urls = new String[ids.length];
	for (int i = 0; i < urls.length; i++) {
	    urls[i] = createCardUrl(ids[i]);
	}
	return urls;
    }

    @Override
    public String[] createResultUrls(int[] ids) {
	String[] urls = new String[ids.length];
	for (int i = 0; i < urls.length; i++) {
	    urls[i] = createResultUrl(ids[i]);
	}
	return urls;
    }

    @Override
    public Document requestDocument(String url) {
	Document doc = null;
	try {
	    doc = Jsoup.connect(url).userAgent(Constants.ROOT_URL).timeout(Constants.MAX_WAIT).get();
	} catch (IOException e) {
	    log.error("Failed to HTTP GET url: " + url, e);
	}
	return doc;
    }
    
    
}
