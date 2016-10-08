package com.cjsheehan.jrace;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalUrlHandler implements RacingUrlHandler {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String ROOT = "./html";

	@Override
	public String createCardUrl(int id) {
		return ROOT + "/card_race_id_" + id + ".html";
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

	@Override
	public Document requestDocument(String url) {
		String fpath = StringUtils.removeStart(url, "file:/");
		ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();
		java.net.URL u = classLoader.getResource(fpath);
		Charset ch = Charset.defaultCharset();
		String chs = ch.toString();
		Document doc = null;
		if (u != null) {
			File file = null;
			try {
				file = Paths.get(u.toURI()).toFile();
			} catch (URISyntaxException e) {
				log.error("Failed to convert {} to URI", u.toString(), e);
			}
			if (file.exists()) {
				try {
					doc = Jsoup.parse(file, chs);
				} catch (IOException e) {
					log.error("Failed to parse document: {}", file.getAbsolutePath(), e);
				}
			}
		}

		return doc;
	}

}
