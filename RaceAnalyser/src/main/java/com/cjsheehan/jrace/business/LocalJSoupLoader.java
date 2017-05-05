package com.cjsheehan.jrace.business;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class LocalJSoupLoader implements JSoupLoader {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public Document load(String url) throws IOException {
		if(url == null) throw new IllegalArgumentException("url is null");
		File file = new ClassPathResource(url).getFile();
		Charset ch = Charset.defaultCharset();
		String chs = ch.toString();
		Document doc = null;
		if (file.exists()) {
			try {
				doc = Jsoup.parse(file, chs);
			} catch (IOException e) {
				log.error("Failed to parse document: {}", file.getAbsolutePath(), e);
			}
		} else {
			log.error("Resource for \"{}\" cannot be found", url);
		}
		
		return doc;
	}

}
