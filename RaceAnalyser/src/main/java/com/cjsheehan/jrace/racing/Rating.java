package com.cjsheehan.jrace.racing;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Rating {
	
	public static final int NO_RATING = -1;
	
	public enum Provider { OR, TS, RPR };
	
	@Column(name = "official_rating", nullable = false)
	private int officialRating;
	
	@Column(name = "rpost_rating", nullable = false)
	private int rpRating;
	
	@Column(name = "topspeed_rating", nullable = false)
	private int tsRating;
	
	@Column(name = "custom_rating", nullable = false)
	private int customRating;

	public Rating() {
		this.officialRating = NO_RATING;
		this.rpRating = NO_RATING;
		this.tsRating = NO_RATING;
		this.customRating = NO_RATING;
	}

	public Rating(int officialRating, int rpRating, int tsRating, int customRating) {
		super();

		
		if (officialRating < NO_RATING) {
			throw new IllegalArgumentException("officialRating is < " + NO_RATING);
		}

		if (rpRating < NO_RATING) {
			throw new IllegalArgumentException("rpRating is <  " + NO_RATING);
		}

		if (tsRating < NO_RATING) {
			throw new IllegalArgumentException("tsRating is <  " + NO_RATING);
		}
		
		if (customRating < NO_RATING) {
			throw new IllegalArgumentException("customRating is <  " + NO_RATING);
		}

		this.officialRating = officialRating;
		this.rpRating = rpRating;
		this.tsRating = tsRating;
		this.customRating = customRating;
	}

	/**
	 * @return the officialRating
	 */
	public int getOfficialRating() {
		return officialRating;
	}

	/**
	 * @param officialRating
	 *            the officialRating to set
	 */
	public void setOfficialRating(int officialRating) {
		this.officialRating = officialRating;
	}

	/**
	 * @return the rpRating
	 */
	public int getRpRating() {
		return rpRating;
	}

	/**
	 * @param rpRating
	 *            the rpRating to set
	 */
	public void setRpRating(int rpRating) {
		this.rpRating = rpRating;
	}

	/**
	 * @return the tsRating
	 */
	public int getTsRating() {
		return tsRating;
	}

	/**
	 * @param tsRating
	 *            the tsRating to set
	 */
	public void setTsRating(int tsRating) {
		this.tsRating = tsRating;
	}

	/**
	 * @return the customRating
	 */
	public int getCustomRating() {
		return customRating;
	}

	/**
	 * @param customRating the customRating to set
	 */
	public void setCustomRating(int customRating) {
		this.customRating = customRating;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("OR", officialRating)
				.append("RPR", rpRating)
				.append("TS", tsRating)
				.append("CUSTOMR", customRating)
				.toString();
	}
}
