package com.cjsheehan.jrace.racing;

public abstract class Person {
    private String name;
    private String url;

    public Person(String name, String url) {
	super();
	this.setName(name);
	this.setUrl(url);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }
}
