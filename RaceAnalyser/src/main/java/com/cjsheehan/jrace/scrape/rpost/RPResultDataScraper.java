package com.cjsheehan.jrace.scrape.rpost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Distance;
import com.cjsheehan.jrace.scrape.RaceDataScraper;
import com.cjsheehan.jrace.scrape.ResultDataScraper;
import com.cjsheehan.jrace.scrape.ResultParamProvider;
import com.cjsheehan.jrace.scrape.Scrape;
import com.cjsheehan.jrace.scrape.ScrapeException;

@Component
public class RPResultDataScraper implements ResultDataScraper {
	@Autowired
	@Qualifier("resultDataScraper")
	private RaceDataScraper rdScraper;
	
	@Autowired
	private ResultParamProvider params;
	
	private static Pattern pTime = Pattern.compile("(\\d+)m (\\d{2}).(\\d+)s"); // 3m 53.10s
	
	@Override
	public int scrapeRaceId(Element elem) throws ScrapeException {
		return rdScraper.scrapeRaceId(elem);
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
	public double scrapePrize(Element elem) throws ScrapeException {
		return rdScraper.scrapePrize(elem);
	}

	@Override
	public int scrapeNumRunners(Element elem) throws ScrapeException {
		return rdScraper.scrapeNumRunners(elem);
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
		long total = 0;
		if (mTime.find()) {
			total = TimeUnit.MINUTES.toMillis(Long.parseLong(mTime.group(1)))
					+ TimeUnit.SECONDS.toMillis(Long.parseLong(mTime.group(2)))
					+ TimeUnit.MILLISECONDS.toMillis(Long.parseLong(mTime.group(3)));
		}

		return total;
	}
	
	@Override
	public List<String> scrapeNonRunners(Element elem) throws ScrapeException {
		// TODO Auto-generated method stub
		return null;
	}

}
