package com.cjsheehan.jrace.racing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertDistance {
    // conversions
    static final int YDS_PER_MILE = 1760;
    static final int YDS_PER_FURLONG = 220;

    // regex
    static Pattern pMFY = Pattern.compile("(\\d+)m *(\\d+)f *(\\d+)y");
    static Pattern pMF = Pattern.compile("(\\d+)m *(\\d+)f");
    static Pattern pMY = Pattern.compile("(\\d+)m *(\\d+)y");
    static Pattern pM = Pattern.compile("(\\d+)m *");
    static Pattern pFY = Pattern.compile("(\\d+)f *(\\d+)y");
    static Pattern pF = Pattern.compile("(\\d+)f *");
    static Pattern pY = Pattern.compile("(\\d+)y *");

    public static double toYards(String distance) throws IllegalArgumentException {
	double yards = 0.0;
	Matcher mMFY = pMFY.matcher(distance);
	Matcher mMF = pMF.matcher(distance);
	Matcher mMY = pMY.matcher(distance);
	Matcher mM = pM.matcher(distance);
	Matcher mFY = pFY.matcher(distance);
	Matcher mF = pF.matcher(distance);
	Matcher mY = pY.matcher(distance);

	if (mMFY.matches()) {
	    yards = (YDS_PER_MILE * Integer.parseInt(mMFY.group(1)))
		    + (YDS_PER_FURLONG * Integer.parseInt(mMFY.group(2))) + Integer.parseInt(mMFY.group(3));

	} else if (mMF.matches()) {
	    yards = (YDS_PER_MILE * Integer.parseInt(mMF.group(1)))
		    + (YDS_PER_FURLONG * Integer.parseInt(mMF.group(2)));

	} else if (mMY.matches()) {
	    yards = (YDS_PER_MILE * Integer.parseInt(mMY.group(1))) + Integer.parseInt(mMY.group(2));

	} else if (mM.matches()) {
	    yards = (YDS_PER_MILE * Integer.parseInt(mM.group(1)));

	} else if (mFY.matches()) {
	    yards = (YDS_PER_FURLONG * Integer.parseInt(mFY.group(1))) + Integer.parseInt(mFY.group(2));

	} else if (mF.matches()) {
	    yards = (YDS_PER_FURLONG * Integer.parseInt(mF.group(1)));

	} else if (mY.matches()) {
	    yards = Integer.parseInt(mY.group(1));

	} else {
	    throw new IllegalArgumentException("distance is invalid - must at least 1 value for miles/furlongs/yards");
	}

	return yards;
    }
    }
