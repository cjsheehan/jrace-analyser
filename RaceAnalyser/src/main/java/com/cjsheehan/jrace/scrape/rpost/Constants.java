package com.cjsheehan.jrace.scrape.rpost;

public class Constants {
    public static final String CARDS = Constants.ROOT_URL + "/horses2/cards/";
    public static final int MAX_WAIT = 3000; // http get max wait in ms
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String ROOT_URL = "http://www.racingpost.com/";
    public static final String RESULT_URL = ROOT_URL + "horses/result_home.sd";
    public static final String CARD_URL = ROOT_URL + "horses2/cards/card.sd";
    
}
