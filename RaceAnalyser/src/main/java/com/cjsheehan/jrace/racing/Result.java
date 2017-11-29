package com.cjsheehan.jrace.racing;

import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;

@SuppressWarnings("unused")
public class Result {
	public Result(Document doc) {
		// TODO Auto-generated constructor stub
	}

	private int raceId;
	private Date date;
	private String course;
	private String raceUrl;
	private int numRunners;
	private Distance distance;
	private Going going;
	private Prize winPrize;
	private String grade;
	private String conditions;
	private String title;
	private List<ResultEntrant> entrants;
	private List<Double> prizes;
}
