package com.cjsheehan.jrace.racing;

public abstract class Person {
    private String name;
    private Long id;
    
    /**
     * @param name
     */
    public Person(String name) {
	super();
	this.name = name;
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


}
