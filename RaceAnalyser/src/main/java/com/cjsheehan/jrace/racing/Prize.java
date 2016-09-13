package com.cjsheehan.jrace.racing;

public class Prize {
    private double value;
    private Currency currency;

    public Prize(double value, Currency currency) {
	super();
	this.value = value;
	this.currency = currency;
    }

    public double getValue() {
	return this.value;
    }

    public Currency getCurrency() {
	return this.currency;
    }

    public String toString() {
	return getCurrency().name() + " " + getValue();
    }
}
