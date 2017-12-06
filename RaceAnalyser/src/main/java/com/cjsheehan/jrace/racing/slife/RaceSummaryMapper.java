package com.cjsheehan.jrace.racing.slife;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cjsheehan.jrace.racing.model.Distance;
import com.cjsheehan.jrace.racing.model.Going;
import com.cjsheehan.jrace.racing.model.RaceStage;
import com.cjsheehan.jrace.racing.model.RaceSummary;
import com.cjsheehan.jrace.racing.model.Surface;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class RaceSummaryMapper extends StdDeserializer<RaceSummary> {

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

		RaceSummary raceSummary = new RaceSummary(); // (RaceSummary)
														// context.getBean("raceSummary");

		try {
			JsonNode root = jp.getCodec().readTree(jp);
			JsonNode summaryNode = root.path("horseRacing")
					.path("race")
					.path("race")
					.path("race_summary");
			
			raceSummary.setId(summaryNode.path("race_summary_reference").get("id").asLong());
			raceSummary.setName(summaryNode.get("name").asText());
			raceSummary.setCourseName(summaryNode.get("course_name").asText());
			raceSummary.setCourseSurface(Surface.fromString(summaryNode.get("course_surface").asText()));
			raceSummary.setAge(summaryNode.get("age").asText());
			raceSummary.setRaceClass(summaryNode.get("race_class").asText());
			// raceSummary.setDistance((Distance) context.getBean("distance",
			// summaryNode.get("distance").asText()));
			raceSummary.setDistance(new Distance(summaryNode.get("distance").asText()));
			raceSummary.setDate(summaryNode.get("date").asText());
			raceSummary.setTime(summaryNode.get("time").asText());

			try {
				raceSummary.setWinningTime(Duration.parse(summaryNode.get("winning_time").asText()));
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}

			raceSummary.setRideCount(summaryNode.get("ride_count").asInt());
			raceSummary.setTime(summaryNode.get("time").asText());
			raceSummary.setRaceStage(RaceStage.fromString(summaryNode.get("race_stage").asText()));
			raceSummary.setGoing(Going.fromString(summaryNode.get("going").asText()));
			raceSummary.setHandicap(summaryNode.get("has_handicap").asBoolean());
			raceSummary.setVerdict(summaryNode.get("verdict").asText());

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return raceSummary;
	}
}