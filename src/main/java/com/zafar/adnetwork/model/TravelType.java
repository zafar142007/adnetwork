package com.zafar.adnetwork.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the travel_types database table.
 * 
 */
@Entity
@Table(name="travel_types")
@NamedQuery(name="TravelType.findAll", query="SELECT t FROM TravelType t")
public class TravelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="travel_type_name")
	private String travelTypeName;
	
	@OneToMany
	@JoinColumn(name="travel_type",referencedColumnName="id")
	private Set<AdSelectorRule> rules=new HashSet<AdSelectorRule>();

	public Set<AdSelectorRule> getRules() {
		return rules;
	}

	public void setRules(Set<AdSelectorRule> rules) {
		this.rules = rules;
	}

	public TravelType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTravelTypeName() {
		return this.travelTypeName;
	}

	public void setTravelTypeName(String travelTypeName) {
		this.travelTypeName = travelTypeName;
	}

}