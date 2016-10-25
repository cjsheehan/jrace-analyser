package com.cjsheehan.jrace.racing;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "JOCKEY")
public class Jockey {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 60)
	private String name;
	
	@Column(name = "entry")
	@OneToMany(mappedBy="entry", cascade=CascadeType.ALL)
	private List<Entry> entries;
	
	protected Jockey() {
	}

	/**
	 * @param name
	 */
	public Jockey(String name) {
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
	 * @param id
	 *            the id to set
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
