package com.cjsheehan.jrace.racing;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "HORSE")
public class Horse {
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "entry")
	@OneToMany(mappedBy="horse", cascade=CascadeType.ALL)
	private List<Entry> entries;
	
	protected Horse() {
	}


	public Horse(String name, long id) {
		super();
		if(name == null) throw new IllegalArgumentException("name is null");
		this.name = name;
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
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
