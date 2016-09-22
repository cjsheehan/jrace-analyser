package com.cjsheehan.jrace.scrape.rpost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Going {
    FIRM, GOOD_TO_FIRM, GOOD, GOOD_TO_SOFT, YIELDING, YIELDING_TO_SOFT, SOFT, SOFT_TO_HEAVY, HEAVY;
    
    private static Pattern pReduce = Pattern.compile("(.+) \\d+ (fences|h.+dles)", Pattern.CASE_INSENSITIVE);
    private static Pattern pFirm  = Pattern.compile("firm *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pGoodToFirm  = Pattern.compile("good to firm *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pGood  = Pattern.compile("good *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pGoodToSoft  = Pattern.compile("good to soft *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pYielding  = Pattern.compile("yielding *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pYieldingToSoft = Pattern.compile("yielding to soft *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pSoft  = Pattern.compile("soft *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pSoftToHeavy  = Pattern.compile("soft to heavy *(?![^(])", Pattern.CASE_INSENSITIVE);
    private static Pattern pHeavy  = Pattern.compile("heavy *(?![^(])", Pattern.CASE_INSENSITIVE);
    
    public static Going parse(String input) {
	Matcher m = pReduce.matcher(input);
	if(m.find()) {
	    input = m.group(1);
	}
	Matcher mFirm = pFirm.matcher(input);
	Matcher mGoodToFirm = pGoodToFirm.matcher(input);
	Matcher mGood = pGood.matcher(input);
	Matcher mGoodToSoft = pGoodToSoft.matcher(input);
	Matcher mYielding = pYielding.matcher(input);
	Matcher mYieldingToSoft = pYieldingToSoft.matcher(input);
	Matcher mSoft = pSoft.matcher(input);
	Matcher mSoftToHeavy = pSoftToHeavy.matcher(input);
	Matcher mHeavy = pHeavy.matcher(input);
	
	Going going = null;
	if(mGoodToFirm.find()) {
	    going = Going.GOOD_TO_FIRM;
	} else if(mGoodToSoft.find()) {
	    going = Going.GOOD_TO_SOFT;
	} else if(mSoftToHeavy.find()) {
	    going = Going.SOFT_TO_HEAVY;
	}  else if(mYieldingToSoft.find()) {
	    going = Going.YIELDING_TO_SOFT;
	} else if(mYielding.find()) {
	    going = Going.YIELDING;
	} else if(mFirm.find()) {
	    going = Going.FIRM;
	} else if(mGood.find()) {
	    going = Going.GOOD;
	} else if(mSoft.find()) {
	    going = Going.SOFT;
	} else if(mHeavy.find()) {
	    going = Going.HEAVY;
	} else {
	    throw new IllegalArgumentException("Invalid input, cannot parse going from string");
	}
	
	return going;
    }
    
}
