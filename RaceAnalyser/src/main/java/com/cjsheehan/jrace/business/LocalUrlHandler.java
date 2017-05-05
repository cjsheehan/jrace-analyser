package com.cjsheehan.jrace.business;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalUrlHandler implements RacingUrlHandler {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String ROOT = "./html";

	@Override
	public String createCardUrl(int id) {
//		return ROOT + "/card_race_id_" + id + ".html";
		return "card_race_id_" + id + ".html";
	}

	@Override
	public String createResultUrl(int id) {
		return ROOT + "/result_race_id_" + id + ".html";
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
