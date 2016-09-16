package com.cjsheehan.jrace.scrape;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cjsheehan.jrace.scrape.rpost.Constants;

public class Html {
    public static Document toDocument(String url) throws IOException {
	if (StringUtils.isBlank(url)) {
	    throw new IllegalArgumentException("url cannot be empty/null/blank");
	}
	
	Document doc = null;
	if(url.startsWith("http://") || url.startsWith("https://")) {
	    doc = Jsoup.connect(url)
		    .userAgent(Constants.ROOT_URL)
		    .timeout(Constants.MAX_WAIT)
		    .get();
	} else {
	    String fpath = StringUtils.removeStart(url, "file:/");
	    File file = new File(fpath);
	    if(file.exists()) {
		doc = Jsoup.parse(file, "cp1252");
	    }
	}
	
	return doc;
    }
}
