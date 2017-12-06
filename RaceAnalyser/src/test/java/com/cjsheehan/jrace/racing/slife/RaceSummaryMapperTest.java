package com.cjsheehan.jrace.racing.slife;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.business.JSoupLoader;
import com.cjsheehan.jrace.racing.model.RaceSummary;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class RaceSummaryMapperTest extends TestCase {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static boolean setUpIsDone = false;
	private static Document doc;
	private static int id = 212924;
	
	@Autowired
	@Qualifier("localJSoupLoader")
	private JSoupLoader docLoader;

	private JsonNode jp;

	private ObjectMapper mapper;

	private String json;

	@Before
	public void setUp() throws IOException {
		if (setUpIsDone) {
			return;
		}

		// do the setup
		doc = docLoader.load("card_race_id_" + id + ".html");

		Element elem = doc.selectFirst("script:containsData(window.__data)");
		json = null;

		if (elem != null) {
			json = elem.data().replace("window.__data=", "");
		}

		mapper = new ObjectMapper();
		jp = mapper.readTree(json);

		setUpIsDone = true;
	}
	
	@Test
	public void whenDeserializeFromJson_thenOK() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(RaceSummary.class, new RaceSummaryMapper());
		mapper.registerModule(module);

		RaceSummary raceSummary = mapper.readValue(json, RaceSummary.class);

		assertTrue(true);

	}
	
	
}
	
