package com.cjsheehan.jrace.racing;

public class Entrant {
    private Horse horse;
    private Jockey jockey;
    private Trainer trainer;
    private Race race;
    private String weight;
    private int weightClaim;
    private Rating rating;
    private int draw = -1;
    private int entryNo;
    private int age;
    
    // post race fields
    private String performance;
    private int finishPosition = -1;
    private String startPrice;
    
    private EntryState runState;
    
    /**
     * @param horse
     * @param jockey
     * @param trainer
     * @param race
     */
    public Entrant(Horse horse, Jockey jockey, Trainer trainer, Race race) {
	super();
	this.horse = horse;
	this.jockey = jockey;
	this.trainer = trainer;
	this.race = race;
    }

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
     * @return the race
     */
    public Race getRace() {
        return race;
    }

    /**
     * @param race the race to set
     */
    public void setRace(Race race) {
        this.race = race;
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

    /**
     * @return the officialRating
     */
    public int getOfficialRating() {
        return officialRating;
    }

    /**
     * @param officialRating the officialRating to set
     */
    public void setOfficialRating(int officialRating) {
        this.officialRating = officialRating;
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
     * @return the finishPosition
     */
    public int getFinishPosition() {
        return finishPosition;
    }

    /**
     * @param finishPosition the finishPosition to set
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
     * @param startPrice the startPrice to set
     */
    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * @return the runState
     */
    public EntryState getRunState() {
        return runState;
    }

    /**
     * @param runState the runState to set
     */
    public void setRunState(EntryState runState) {
        this.runState = runState;
    }

    /**
     * @return the entryNo
     */
    public int getEntryNo() {
	return entryNo;
    }

    /**
     * @param entryNo the entryNo to set
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
     * @param age the age to set
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
     * @param rating the rating to set
     */
    public void setRating(Rating rating) {
	this.rating = rating;
    }
}
