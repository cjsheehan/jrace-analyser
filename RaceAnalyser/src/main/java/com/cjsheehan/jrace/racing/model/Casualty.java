package com.cjsheehan.jrace.racing.model;

public class Casualty {

	private CasualtyReason casualtyReason;

	/**
	 * @param casualtyReason
	 */
	public Casualty(CasualtyReason casualtyReason) {
		super();
		this.casualtyReason = casualtyReason;
	}

	/**
	 * @return the casualtyReason
	 */
	public CasualtyReason getCasualtyReason() {
		return casualtyReason;
	}

	/**
	 * @param casualtyReason the casualtyReason to set
	 */
	public void setCasualtyReason(CasualtyReason casualtyReason) {
		this.casualtyReason = casualtyReason;
	}
}
