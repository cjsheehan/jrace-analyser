package com.cjsheehan.jrace.racing;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertDistance {
	final static Logger log = LoggerFactory.getLogger(ConvertDistance.class);

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
	static Pattern pWholeOnly = Pattern.compile("(\\d+)$");
	static Pattern pWholeAndFrac = Pattern.compile("(\\d+)(\\d)/(\\d)$");
	static Pattern pFracOnly = Pattern.compile("(\\d)/(\\d)$");

	public static double toYards(String distance) {
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

	public static double beatenToYards(String distance) {

		if (StringUtils.isBlank(distance)) {
			throw new IllegalArgumentException("distance cannot be null/blank");
		}

		double converted = 0.0;
		if (distance.contentEquals("dh")) {
			converted = 0.0;
		} else if (distance.contentEquals("nse")) {
			converted = 0.01;
		} else if (distance.contentEquals("shd")) {
			converted = 0.1;
		} else if (distance.contentEquals("hd")) {
			converted = 0.2;
		} else if (distance.contentEquals("nk")) {
			converted = 0.3;
		} else if (distance.contentEquals("dis") || distance.contentEquals("dist")) {
			converted = 50.0;
		} else {
			String normal = Normalizer.normalize(distance, Normalizer.Form.NFKD).replace("\u2044", "/");
			Matcher mFrac = pFracOnly.matcher(normal);
			Matcher mWhole = pWholeOnly.matcher(normal);
			Matcher mWholeAndFrac = pWholeAndFrac.matcher(normal);

			if (mWholeAndFrac.find()) {
				double whole = Double.parseDouble(mWholeAndFrac.group(1));
				double numerator = Double.parseDouble(mWholeAndFrac.group(2));
				double denominator = Double.parseDouble(mWholeAndFrac.group(3));
				converted = whole + numerator / denominator;
			} else if (mFrac.find()) {
				double numerator = Double.parseDouble(mFrac.group(1));
				double denominator = Double.parseDouble(mFrac.group(2));
				converted = numerator / denominator;
			} else if (mWhole.find()) {
				converted = Double.parseDouble(mWhole.group(1));
			} else {
				throw new IllegalArgumentException("distance :" + distance + " is invalid format");
			}
		}
		return converted;
	}

	public static boolean isValid(String distance) {
		Matcher mMFY = pMFY.matcher(distance);
		Matcher mMF = pMF.matcher(distance);
		Matcher mMY = pMY.matcher(distance);
		Matcher mM = pM.matcher(distance);
		Matcher mFY = pFY.matcher(distance);
		Matcher mF = pF.matcher(distance);
		Matcher mY = pY.matcher(distance);

		if (mMFY.matches()) {
			return true;
		} else if (mMF.matches()) {
			return true;
		} else if (mMY.matches()) {
			return true;
		} else if (mM.matches()) {
			return true;
		} else if (mFY.matches()) {
			return true;
		} else if (mF.matches()) {
			return true;
		} else if (mY.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
