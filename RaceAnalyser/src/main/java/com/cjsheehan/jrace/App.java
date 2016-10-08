package com.cjsheehan.jrace;

import java.lang.invoke.MethodHandles;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjsheehan.jrace.scrape.ScrapeException;
import com.cjsheehan.jrace.scrape.rpost.Card;
import com.cjsheehan.jrace.scrape.rpost.Result;
import com.cjsheehan.jrace.scrape.rpost.RpUrlHandler;

enum SCRAPE {
	CARD, RESULT
};

public class App {
	static final String HTML_LOC = "/html";
	static final String REMOTE_RESULT_URL = "http://www.racingpost.com/horses/result_home.sd";
	static final String REMOTE_CARD_URL = "http://www.racingpost.com/horses2/cards/card.sd";
	// static int[] cardIds = {657619, 657991, 658295, 659557, 659807, 658422};
	static int[] cardIds = { 659557 };
	static int[] resultIds = { 646160, 646834, 657619, 659125 };
	static SCRAPE scrape = SCRAPE.CARD;
	static boolean useRemote = false;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		String[] urls = {};
		RacingUrlHandler urlManager;
		if (useRemote) {
			urlManager = new RpUrlHandler();
		} else {
			urlManager = new LocalUrlHandler();
		}

		if (scrape == SCRAPE.CARD) {
			urls = urlManager.createCardUrls(cardIds);
		} else if (scrape == SCRAPE.RESULT) {
			urls = urlManager.createResultUrls(resultIds);
		}

		for (String url : urls) {
			Document doc = null;
			try {
				doc = urlManager.requestDocument(url);

				if (scrape == SCRAPE.CARD) {
					Card card = null;
					try {
						card = new Card(doc);
						log.info(doc.baseUri() + "\n" + card.toString());
					} catch (ScrapeException e) {
						log.error("doc.baseUri() + \n", e);
					}

				} else if (scrape == SCRAPE.RESULT) {
					Result result = null;
					try {
						result = new Result(doc);
						log.info(doc.baseUri() + "\n" + result.toString());
					} catch (ScrapeException e) {
						log.error("doc.baseUri() + \n", e);
					}
				}
			} catch (Exception e) {
				log.error(url + "\n" + e.getMessage());
			}
		}
	}
}
