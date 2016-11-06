package com.cjsheehan.jrace.racing;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "race_entry")
public class Entry {
	@Id
	@Column(name = "id")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Horse horse;

	@ManyToOne(cascade = CascadeType.ALL)
	private Jockey jockey;

	@ManyToOne(cascade = CascadeType.ALL)
	private Trainer trainer;

	@ManyToOne(cascade = CascadeType.ALL)
	private Race race;
	
	@Column(name = "weight_in_lbs", nullable = false)
	private int weightInLbs;
	
	@Column(name = "weight_claim", nullable = false)
	private int weightClaim = 0;
	
	@Embedded
	private Rating rating = new Rating();
	
	@Embedded
	private Odds odds;
	
	@Column(name = "draw", nullable = false)
	private int draw = -1;
	
	@Column(name = "entry_number", nullable = false)
	private int entryNo;
	
	@Column(name = "age")
	private int age = -1;

	// post race fields
	@Column(name = "race_performance", nullable = false)
	private String performance = "";
	
	@Column(name = "finish_position")
	private int finishPosition = -1;
	
	@Column(name = "start_price", nullable = false)
	private String startPrice = "";

	@Enumerated(EnumType.STRING)
	@Column(name = "entry_state", nullable = false)
	private EntryState entryState = EntryState.NOT_AVAILABLE;

	/**
	 * @param id
	 * @param horse
	 * @param jockey
	 * @param trainer
	 * @param odds
	 * @param race
	 */
	public Entry(long id, Horse horse, Jockey jockey, Trainer trainer, Odds odds, Race race) {
		super();
		
		if(horse == null) {
			throw new IllegalArgumentException("horse is null");
		}
		
		if(trainer == null) {
			throw new IllegalArgumentException("trainer is null");
		}
		
		this.id = id;
		this.horse = horse;
		this.jockey = jockey;
		this.trainer = trainer;
		this.odds = odds;
		this.race = race;
	}

	protected Entry() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

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
	 * @return the race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * @param race
	 *            the race to set
	 */
	public void setRace(Race race) {
		this.race = race;
	}

	/**
	 * @return the weight
	 */
	public int getWeightInLbs() {
		return weightInLbs;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeightInLbs(int lbs) {
		this.weightInLbs = lbs;
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
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * @param draw
	 *            the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * @return the performance
	 */
	public String getPerformance() {
		return performance;
	}

	/**
	 * @param performance
	 *            the performance to set
	 */
	public void setPerformance(String performance) {
		this.performance = performance;
	}

	/**
	 * @return the finishPosition
	 */
	public int getFinishPosition() {
		return finishPosition;
	}

	/**
	 * @param finishPosition
	 *            the finishPosition to set
	 */
	public void setFinishPosition(int finishPosition) {
		this.finishPosition = finishPosition;
	}

	/**
	 * @return the startPrice
	 */
	public String getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice
	 *            the startPrice to set
	 */
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the entryState
	 */
	public EntryState getEntryState() {
		return entryState;
	}

	/**
	 * @param runState
	 *            the entryState to set
	 */
	public void setEntryState(EntryState runState) {
		this.entryState = runState;
	}

	/**
	 * @return the entryNo
	 */
	public int getEntryNo() {
		return entryNo;
	}

	/**
	 * @param entryNo
	 *            the entryNo to set
	 */
	public void setEntryNo(int entryNo) {
		this.entryNo = entryNo;
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
}
