package com.cjsheehan.jrace.racing;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Rating {
	
	@Column(name = "official_rating", nullable = false)
	private int officialRating;
	
	@Column(name = "rpost_rating", nullable = false)
	private int rpRating;
	
	@Column(name = "topspeed_rating", nullable = false)
	private int tsRating;

	public Rating() {
		officialRating = -1;
		rpRating = -1;
		tsRating = -1;
	}
	
	/**
	 * @param officialRating
	 * @param rpRating
	 * @param tsRating
	 */
	public Rating(int officialRating, int rpRating, int tsRating) {
		super();

		if (officialRating < -1) {
			throw new IllegalArgumentException("officialRating is < 0");
		}

		if (rpRating < -1) {
			throw new IllegalArgumentException("rpRating is < 0");
		}

		if (tsRating < -1) {
			throw new IllegalArgumentException("tsRating is < 0");
		}

		this.officialRating = officialRating;
		this.rpRating = rpRating;
		this.tsRating = tsRating;
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
}
