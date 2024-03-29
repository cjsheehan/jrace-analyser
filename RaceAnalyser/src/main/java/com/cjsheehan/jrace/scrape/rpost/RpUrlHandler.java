package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.business.RacingUrlHandler;

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

}
