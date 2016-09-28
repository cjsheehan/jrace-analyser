package com.cjsheehan.jrace.scrape;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cjsheehan.jrace.scrape.rpost.Constants;

public class Html {
    public static Document toDocument(String url) throws IOException, URISyntaxException {
	if (StringUtils.isBlank(url)) {
	    throw new IllegalArgumentException("url cannot be empty/null/blank");
	}

	Document doc = null;
	if (url.startsWith("http://") || url.startsWith("https://")) {
	    doc = Jsoup.connect(url).userAgent(Constants.ROOT_URL).timeout(Constants.MAX_WAIT).get();
	} else {
	    String fpath = StringUtils.removeStart(url, "file:/");
	    ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();
	    URL u = classLoader.getResource(fpath);
	    Charset ch = Charset.defaultCharset();
	    String chs = ch.toString();
	    if (u != null) {
		File file = null;
		file = Paths.get(u.toURI()).toFile();
		if (file.exists()) {
		    doc = Jsoup.parse(file, chs);
		}
	    }
	}

	return doc;
    }
}
