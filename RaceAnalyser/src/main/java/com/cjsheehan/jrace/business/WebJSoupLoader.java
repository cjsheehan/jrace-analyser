package com.cjsheehan.jrace.business;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.scrape.rpost.Constants;

@Component
public class WebJSoupLoader implements JSoupLoader {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public Document load(String url) throws IOException {
		if (StringUtils.isBlank(url)) throw new IllegalArgumentException("url cannot be empty/null/blank");

		Document doc = null;
		if (url.startsWith("http://") || url.startsWith("https://")) {
			doc = Jsoup.connect(url).userAgent(Constants.ROOT_URL).timeout(Constants.MAX_WAIT).get();
		} else {
			 throw new IllegalArgumentException(String.format("url \"{}\" must be valid web address", url));
		}

		return doc;
	}
}
