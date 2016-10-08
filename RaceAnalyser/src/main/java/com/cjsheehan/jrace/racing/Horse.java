package com.cjsheehan.jrace.racing;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Horse {
    private long id;
    private String name;

    /**
     * @param name
     */
    public Horse(String name) {
	super();
	this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
	return new ToStringBuilder(this)
		.append("Name", this.name)
		.append("ID", this.id)
		.toString();
    }
}
