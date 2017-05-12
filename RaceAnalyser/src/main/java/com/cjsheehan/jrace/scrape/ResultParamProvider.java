package com.cjsheehan.jrace.scrape;

public interface ResultParamProvider extends StandardParamProvider {
	String winningTimeSelector();
	String nonRunnersSelector();
	String winningTimeFormat();
}
