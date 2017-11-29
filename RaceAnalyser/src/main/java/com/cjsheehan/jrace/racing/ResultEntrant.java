package com.cjsheehan.jrace.racing;

public class ResultEntrant {
	private Horse horse;
	private Jockey jockey;
	private Trainer trainer;
	private int age;
	private Rating rating;
	private Weight weightCarried;
	private int weightClaim;
	private FinishPosition finishPosition;
	private String comments;
	private String price;
	private BeatenDistance beatenDistance;

	/**
	 * @return the horse
	 */
	public Horse getHorse() {
		return horse;
	}

	/**
	 * @param horse
	 *            the horse to set
	 */
	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	/**
	 * @return the jockey
	 */
	public Jockey getJockey() {
		return jockey;
	}

	/**
	 * @param jockey
	 *            the jockey to set
	 */
	public void setJockey(Jockey jockey) {
		this.jockey = jockey;
	}

	/**
	 * @return the trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * @param trainer
	 *            the trainer to set
	 */
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the rating
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	/**
	 * @return the weightCarried
	 */
	public Weight getWeightCarried() {
		return weightCarried;
	}

	/**
	 * @param weightCarried
	 *            the weightCarried to set
	 */
	public void setWeightCarried(Weight weightCarried) {
		this.weightCarried = weightCarried;
	}

	/**
	 * @return the weightClaim
	 */
	public int getWeightClaim() {
		return weightClaim;
	}

	/**
	 * @param weightClaim
	 *            the weightClaim to set
	 */
	public void setWeightClaim(int weightClaim) {
		this.weightClaim = weightClaim;
	}

	/**
	 * @return the finishPosition
	 */
	public FinishPosition getFinishPosition() {
		return finishPosition;
	}

	/**
	 * @param finishPosition
	 *            the finishPosition to set
	 */
	public void setFinishPosition(FinishPosition finishPosition) {
		this.finishPosition = finishPosition;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the beatenDistance
	 */
	public BeatenDistance getBeatenDistance() {
		return beatenDistance;
	}

	/**
	 * @param beatenDistance
	 *            the beatenDistance to set
	 */
	public void setBeatenDistance(BeatenDistance beatenDistance) {
		this.beatenDistance = beatenDistance;
	}
}
