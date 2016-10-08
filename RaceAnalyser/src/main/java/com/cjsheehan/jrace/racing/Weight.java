package com.cjsheehan.jrace.racing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;

public class Weight {

    private static final int LBS_PER_STONE = 14;
    private static final Pattern pToLbs = Pattern.compile("^(\\d+)-(\\d+)$");
    
    public static int toLbs(String weight) {
	if(StringUtil.isBlank(weight)) {
	    throw new IllegalArgumentException("weight is blank/null");
	}
	
	int totalLbs = 0;
	Matcher m = pToLbs.matcher(weight);
	if(m.find()) {
	    int st = Integer.parseInt(m.group(1));
	    int lbs = Integer.parseInt(m.group(2)); 
	    totalLbs = (LBS_PER_STONE * st) + lbs;
	} else {
	    throw new IllegalArgumentException("weight must be in the form x-y");
	}
	
	return totalLbs;
    }
    
    public static String toStAndLbs(int lbs) {
	if(lbs < 0) {
	    throw new IllegalArgumentException("lbs must be > 0");
	}
	
	int st = lbs / LBS_PER_STONE;
	lbs = lbs % LBS_PER_STONE;
	return st + "-" + lbs;
    }
}
