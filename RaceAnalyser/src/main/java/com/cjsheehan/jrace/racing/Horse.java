package com.cjsheehan.jrace.racing;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Horse {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "entry")
	@OneToMany(mappedBy="entry", cascade=CascadeType.ALL)
	private List<Entry> entries;


	public Horse(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the entrants
	 */
	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * @param entrants the entrants to set
	 */
	public void setEntrants(List<Entry> entries) {
		this.entries = entries;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Name", this.name).append("ID", this.id).toString();
	}
}
