package com.cjsheehan.jrace.racing.slife;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.model.Distance;
import com.cjsheehan.jrace.racing.model.Going;
import com.cjsheehan.jrace.racing.model.RaceStage;
import com.cjsheehan.jrace.racing.model.RaceSummary;
import com.cjsheehan.jrace.racing.model.Surface;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@Component
public class RaceSummaryMapper extends StdDeserializer<RaceSummary> {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 1L;

	@Autowired
	private ApplicationContext context;

	public RaceSummaryMapper() {
		this(null);
	}

	public RaceSummaryMapper(Class<?> vc) {
		super(vc);
	}

	@Override
	public RaceSummary deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		RaceSummary raceSummary = (RaceSummary) context.getBean(RaceSummary.class);
		JsonNode root = jp.getCodec().readTree(jp);
		JsonNode summaryNode = root.path("horseRacing").path("race").path("race").path("race_summary");

		raceSummary.setId(summaryNode.path("race_summary_reference").get("id").asLong());
		raceSummary.setName(summaryNode.get("name").asText());
		raceSummary.setCourseName(summaryNode.get("course_name").asText());
		raceSummary.setCourseSurface(Surface.fromString(summaryNode.path("course_surface").get("surface").asText()));
		raceSummary.setAge(summaryNode.get("age").asText());
		raceSummary.setRaceClass(summaryNode.get("race_class").asInt());
		raceSummary.setDistance((Distance) context.getBean("distance", summaryNode.get("distance").asText()));
		raceSummary.setDate(summaryNode.get("date").asText());
		raceSummary.setTime(summaryNode.get("time").asText());

		try {
			String time = "PT" + summaryNode.get("winning_time").asText().toUpperCase().replace(" ", "");
			raceSummary.setWinningTime(Duration.parse(time));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}

		raceSummary.setRideCount(summaryNode.get("ride_count").asInt());
		raceSummary.setTime(summaryNode.get("time").asText());
		raceSummary.setRaceStage(RaceStage.fromString(summaryNode.get("race_stage").asText()));
		raceSummary.setGoing(Going.fromString(summaryNode.get("going").asText()));
		raceSummary.setHandicap(summaryNode.get("has_handicap").asBoolean());
		raceSummary.setVerdict(summaryNode.get("verdict").asText());

		return raceSummary;
	}
}