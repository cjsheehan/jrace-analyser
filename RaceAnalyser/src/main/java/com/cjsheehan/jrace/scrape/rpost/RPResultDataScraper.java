package com.cjsheehan.jrace.scrape.rpost;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.BeatenDistance;
import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.racing.FinishPosition;
import com.cjsheehan.jrace.racing.Prize;
import com.cjsheehan.jrace.racing.ResultEntrant;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ResultDataScraper;
import com.cjsheehan.jrace.scrape.ResultEntrantDataScraper;
import com.cjsheehan.jrace.scrape.ResultParamProvider;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPResultDataScraper implements ResultDataScraper {
	final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	@Qualifier("resultDataScraper")
	private RaceDataScraper rdScraper;
	
	@Autowired
	private ApplicationContext context;

	@Autowired
	private ResultParamProvider params;

	@Autowired
	private ResultEntrantDataScraper reds;
	
	private static Pattern pTime = Pattern.compile("(\\d+)m (\\d{2}).(\\d+)s"); // 3m 53.10s
	
	@Override
	public int scrapeRaceId(Element elem) throws ScrapeException {
		String selector = "div[data-race-id]";
		Element idElem = elem.select(selector).first();
		return Integer.parseInt(idElem.attr("data-race-id"));
	}

	@Override
	public String scrapeCourse(Element elem) throws ScrapeException {
		return rdScraper.scrapeCourse(elem);
	}

	@Override
	public Date scrapeDate(Element elem) throws ScrapeException {
		Element timeElem = elem.select(params.timeSelector()).first();
		String time = null;
		if (timeElem != null) {
			time = timeElem.ownText();
			if(StringUtil.isBlank(time)) throw new ScrapeException("Time", elem.toString(), params.timeSelector());
		} else {
			throw new ScrapeException("Time", elem.toString(), params.timeSelector());
		}


		String date = null;
		try {
			date = Scrape.text(elem, params.dateSelector());
		} catch (Exception e) {
			throw new ScrapeException("Date", elem.toString(), params.dateSelector());
		}

		Date dt = null;
		if (time != null && date != null) {
			try {
				dt = new SimpleDateFormat(params.dateFormat()).parse(time + " PM, " + date);
			} catch (ParseException e) {
				throw new ScrapeException("Failed to parse date format");
			}
		}

		return dt;
	}

	@Override
	public Prize scrapePrize(Element elem) throws ScrapeException {
		return rdScraper.scrapePrize(elem);
	}

	@Override
	public int scrapeNumRunners(Element elem) throws ScrapeException {
		String selector = "div.rp-horseTable > table > tbody > tr.rp-horseTable__mainRow";
		return elem.select(selector).size();
	}

	@Override
	public Distance scrapeDistance(Element elem) throws ScrapeException {
		return rdScraper.scrapeDistance(elem);
	}

	@Override
	public String scrapeGoing(Element elem) throws ScrapeException {
		return rdScraper.scrapeGoing(elem);
	}

	@Override
	public String scrapeTitle(Element elem) throws ScrapeException {
		return rdScraper.scrapeTitle(elem);
	}

	@Override
	public String scrapeGrade(Element elem) throws ScrapeException {
		return rdScraper.scrapeGrade(elem);
	}

	@Override
	public String scrapeAges(Element elem) throws ScrapeException {
		return rdScraper.scrapeAges(elem);
	}

	@Override
	public long scrapeWinningTime(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		String time = Scrape.text(elem, params.winningTimeSelector());
		if(StringUtil.isBlank(time)) throw new ScrapeException("Time", elem.toString(), params.timeSelector());
		Matcher mTime = pTime.matcher(time);
		if (!mTime.find()) throw new ScrapeException("Winning Time", elem.toString(), params.nonRunnersSelector());
		
		return TimeUnit.MINUTES.toMillis(Long.parseLong(mTime.group(1)))
				+ TimeUnit.SECONDS.toMillis(Long.parseLong(mTime.group(2)))
				+ TimeUnit.MILLISECONDS.toMillis(Long.parseLong(mTime.group(3)));
	}
	
	@Override
	public List<String> scrapeNonRunners(Element elem) throws ScrapeException {
		if(elem == null) throw new IllegalArgumentException("elem is null");
		Element selected = elem.select(params.nonRunnersSelector()).first();
		String scrapedRunners = null;
		if(selected.ownText().contains("Non-runners")) {
			scrapedRunners = selected.select("span").first().ownText();
			if(StringUtil.isBlank(scrapedRunners)) throw new ScrapeException("Non-Runners", elem.toString(), params.nonRunnersSelector());
		}
		String[] splitRunners = scrapedRunners.split(",");
		for (int i = 0; i < splitRunners.length; i++) {
			splitRunners[i] = splitRunners[i].replaceAll("\\(.+?\\)", "").trim();
		}
		
		return Arrays.asList(splitRunners);
	}

	@Override
	public List<ResultEntrant> scrapeEntrants(Document doc) {
		List<ResultEntrant> entrants = new ArrayList<>();
		String selector = "div.RC-runnerRow";
		List<Element> entrantElems = doc.select(selector);
		for (Element element : entrantElems) {
			try {
				ResultEntrant entrant = (ResultEntrant) context.getBean("resultEntrant");

				// Critical
				entrant.setHorse(reds.scrapeHorse(element));
				entrant.setJockey(reds.scrapeJockey(element));
				entrant.setTrainer(reds.scrapeTrainer(element));
				entrant.setWeightCarried(reds.scrapeWeight(element));
				entrant.setFinishPosition(scrapePosition(element));
				entrant.setBeatenDistance(scrapeBeatenDistance(element));

				// Non-Critical
				try {
					entrant.setAge(reds.scrapeAge(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setPrice(reds.scrapePrice(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setComments(reds.scrapeComments(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				try {
					entrant.setRating(reds.scrapeRating(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}


				try {
					entrant.setWeightClaim(reds.scrapeWeightAllowance(element));
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				entrants.add(entrant);

			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return entrants;
	}

	private BeatenDistance scrapeBeatenDistance(Element elem) throws ScrapeException {
		double distToNext = reds.scrapeBeatenDistanceToNext(elem);
		double distToFirst = reds.scrapeBeatenDistanceToFirst(elem);
		return (BeatenDistance) context.getBean("beatenDistance", distToNext, distToFirst);
	}

	private FinishPosition scrapePosition(Element elem) throws ScrapeException {
		String position = reds.scrapePosition(elem);
		return (FinishPosition) context.getBean("finishPosition", position);
	}

}
