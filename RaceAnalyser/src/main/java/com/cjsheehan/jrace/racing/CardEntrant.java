package com.cjsheehan.jrace.racing;

public class CardEntrant {
	private Horse horse;
	private int age;
	private int saddleNo;
	private String weight;
	private int draw;
	private Rating rating = new Rating();
	private int lastRan;
	private Jockey jockey;
	private Trainer trainer;
	private int weightClaim;
	

	/**
	 * @return the horse
	 */
	public Horse getHorse() {
		return horse;
	}
	/**
	 * @param horse the horse to set
	 */
	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the clothNo
	 */
	public int getSaddleNo() {
		return saddleNo;
	}
	/**
	 * @param clothNo the clothNo to set
	 */
	public void setSaddleNo(int clothNo) {
		this.saddleNo = clothNo;
	}
	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}
	/**
	 * @param draw the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}
	/**
	 * @return the rating
	 */
	public Rating getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	/**
	 * @return the lastRan
	 */
	public int getLastRan() {
		return lastRan;
	}
	/**
	 * @param lastRan the lastRan to set
	 */
	public void setLastRan(int lastRan) {
		this.lastRan = lastRan;
	}
	/**
	 * @return the jockey
	 */
	public Jockey getJockey() {
		return jockey;
	}
	/**
	 * @param jockey the jockey to set
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
	 * @param trainer the trainer to set
	 */
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	/**
	 * @return the weightClaim
	 */
	public int getWeightClaim() {
		return weightClaim;
	}
	/**
	 * @param weightClaim the weightClaim to set
	 */
	public void setWeightClaim(int weightClaim) {
		this.weightClaim = weightClaim;
	}
	
	
}
