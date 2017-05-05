package com.cjsheehan.jrace.business;

import org.jsoup.nodes.Document;

public interface RacingUrlHandler {
	public String createCardUrl(int id);

	public String createResultUrl(int id);

	public String[] createCardUrls(int[] ids);

	public String[] createResultUrls(int[] ids);

}
