package com.cjsheehan.jrace.racing.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Odds {

	@Column(name = "odds_numerator", nullable = false)
	private int numerator;
	
	@Column(name = "odds_denominator", nullable = false)
	private int denominator;
	
	/**
	 * @param numerator
	 * @param denominator
	 */
	public Odds(int numerator, int denominator) {
		super();
		
		if(denominator == 0) {
			throw new IllegalArgumentException("denominator == 0");
		}
		
		if(numerator == 0) {
			throw new IllegalArgumentException("numerator == 0");
		}
		
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * @return the numerator
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * @param numerator
	 *            the numerator to set
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	/**
	 * @return the denominator
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * @param denominator
	 *            the denominator to set
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

}
