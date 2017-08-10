package com.cjsheehan.jrace.racing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;

public class Weight {
	
	private int stonesComponent = 0;
	private int lbsComponent = 0;
	private String weight;

	private static final int LBS_PER_STONE = 14;
	private static final Pattern pToLbs = Pattern.compile("^(\\d+)-(\\d+)$");
	
	public Weight(int st, int lbs) {
		if(st < 0) throw new IllegalArgumentException("st < 0");
		if(lbs < 0) throw new IllegalArgumentException("lbs < 0");
		stonesComponent = st;
		lbsComponent = lbs;
		weight = st + "-" + lbs;
	}
	
	public Weight(String weight) {
		if(StringUtil.isBlank(weight)) throw new IllegalArgumentException("weight is blank/null");
		int totalLbs = toLbs(weight);
		setStonesComponent(totalLbs / LBS_PER_STONE);
		setLbsComponent(totalLbs % LBS_PER_STONE);
		this.weight = weight;
	}

	@Override
	public String toString() {
		return weight;
	}

	/**
	 * @return the stonesComponent
	 */
	public int getStonesComponent() {
		return stonesComponent;
	}

	/**
	 * @param stonesComponent the stonesComponent to set
	 */
	private void setStonesComponent(int stonesComponent) {
		this.stonesComponent = stonesComponent;
	}

	/**
	 * @return the lbsComponent
	 */
	public int getLbsComponent() {
		return lbsComponent;
	}

	/**
	 * @param lbsComponent the lbsComponent to set
	 */
	private void setLbsComponent(int lbsComponent) {
		this.lbsComponent = lbsComponent;
	}

	public static int toLbs(String weight) {
		if (StringUtil.isBlank(weight)) {
			throw new IllegalArgumentException("weight is blank/null");
		}

		int totalLbs = 0;
		Matcher m = pToLbs.matcher(weight);
		if (m.find()) {
			int st = Integer.parseInt(m.group(1));
			int lbs = Integer.parseInt(m.group(2));
			totalLbs = (LBS_PER_STONE * st) + lbs;
		} else {
			throw new IllegalArgumentException("weight must be in the form x-y");
		}

		return totalLbs;
	}

	public static String toStAndLbs(int lbs) {
		if (lbs < 0) {
			throw new IllegalArgumentException("lbs must be > 0");
		}

		int st = lbs / LBS_PER_STONE;
		lbs = lbs % LBS_PER_STONE;
		return st + "-" + lbs;
	}
}
