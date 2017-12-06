package com.cjsheehan.jrace.racing.model;

public enum CasualtyReason {
	PU("PulledUp"), F("Fell"), BD("BroughtDown"), CO("CarriedOut"), DNF("DidNotFinish"), HR("HitTheRails"), RO(
			"RanOut"), R("Refused"), RFR("RefusedToStart");
	
	private String reason;

	CasualtyReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
}
