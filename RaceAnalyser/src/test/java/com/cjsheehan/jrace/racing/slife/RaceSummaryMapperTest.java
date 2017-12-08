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
import com.cjsheehan.jrace.racing.model.Going;
import com.cjsheehan.jrace.racing.model.RaceStage;
import com.cjsheehan.jrace.racing.model.RaceSummary;
import com.cjsheehan.jrace.racing.model.Surface;
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
	private static int id = 425018;
	
	@Autowired
	private RaceSummaryMapper summaryMapper;

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
		module.addDeserializer(RaceSummary.class, summaryMapper);
		mapper.registerModule(module);

		RaceSummary summary = mapper.readValue(json, RaceSummary.class);

		assertNotNull(summary);
		assertEquals(425018L, summary.getId());
		assertEquals("Murrayshall Hotel And Golf Courses Novices' Hurdle", summary.getName());
		assertEquals("Perth", summary.getCourseName());
		assertEquals(Surface.TURF, summary.getCourseSurface());
		assertEquals("4YO plus", summary.getAge());
		assertEquals(4, summary.getRaceClass());
		assertEquals("2m 47y", summary.getDistance().getDistance());
		assertEquals("2017-04-27", summary.getDate());
		assertEquals("13:00", summary.getTime());
		assertEquals(233200L, summary.getWinningTime().toMillis());
		assertEquals(11, summary.getRideCount());
		assertEquals(RaceStage.WEIGHEDIN, summary.getRaceStage());
		assertEquals(Going.GOOD, summary.getGoing());
		assertEquals(false, summary.hasHandicap());
		assertEquals(
				"\u003Cb\u003ETOP VILLE BEN\u003C\u002Fb\u003E is without doubt down the pecking order at Sevenbarrows, but despite the concession of a double penalty has the clear ability to comfortably see off these rivals. He can be expected to make all for Jerry McGrath, with the unexposed \u003Cb\u003EMr Grumpy\u003C\u002Fb\u003E, \u003Cb\u003EAldeburgh\u003C\u002Fb\u003E (also entered on Wednesday), and \u003Cb\u003EGolden Town\u003C\u002Fb\u003E playing for places.\u003Cbr\u002F\u003E\u003Col type=\"1\"\u003E\u003Cli\u003E\u003Cb\u003ETop Ville Ben\u003C\u002Fb\u003E\u003C\u002Fli\u003E\u003Cli\u003E\u003Cb\u003EAldeburgh\u003C\u002Fb\u003E\u003C\u002Fli\u003E\u003Cli\u003E\u003Cb\u003EGolden Town\u003C\u002Fb\u003E\u003C\u002Fli\u003E\u003C\u002Fol\u003E",
				summary.getVerdict());


	}
	
	
}
	
