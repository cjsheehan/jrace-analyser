package com.cjsheehan.jrace.business;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class LocalJSoupLoaderTest extends TestCase {
	

	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docProvider;

	@Test
	public void documentIsLoaded() throws IOException {
		LocalUrlHandler urlHandler = new LocalUrlHandler();
		int id = 6672154;
		String url = urlHandler.createCardUrl(id);
		Document doc = null;
		doc = docProvider.load(url);
		assertNotNull(doc);
	}

}
