package com.cjsheehan.jrace.racing;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Odds {

	@Column(name = "odds_date", nullable = false)
	private Date date;
	
	@Column(name = "odds_numerator", nullable = false)
	private int numerator;
	
	@Column(name = "odds_denominator", nullable = false)
	private int denominator;
	
	@Column(name = "impliedProbability", nullable = false)
	private double impliedProbability;

	protected Odds() {
	}

	/**
	 * @param date
	 * @param numerator
	 * @param denominator
	 */
	public Odds(Date date, int numerator, int denominator) {
		super();
		
		if(date == null) {
			throw new IllegalArgumentException("denominator is null");
		}
		
		if(denominator == 0) {
			throw new IllegalArgumentException("denominator == 0");
		}
		
		if(numerator == 0) {
			throw new IllegalArgumentException("denominator == 0");
		}
		
		this.date = date;
		this.numerator = numerator;
		this.denominator = denominator;
		this.impliedProbability = (double) numerator / (double) denominator;
		
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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

	/**
	 * @return the impliedProbability
	 */
	public double getImpliedProbability() {
		return impliedProbability;
	}
}
