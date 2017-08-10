package com.cjsheehan.jrace.business;

public interface RacingUrlHandler {
	public String createCardUrl(int id);

	public String createResultUrl(int id);

	public String[] createCardUrls(int[] ids);

	public String[] createResultUrls(int[] ids);

}
