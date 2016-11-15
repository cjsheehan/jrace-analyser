package com.cjsheehan.jrace.racing;

public class RunnerResult {
	String performance;
	int position;
	
	protected RunnerResult() {
	}

	/**
	 * @param performance
	 * @param position
	 */
	public RunnerResult(String performance, int position) {
		super();
		this.performance = performance;
		this.position = position;
	}

	/**
	 * @return the performance
	 */
	public String getPerformance() {
		return performance;
	}

	/**
	 * @param performance the performance to set
	 */
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}
}
