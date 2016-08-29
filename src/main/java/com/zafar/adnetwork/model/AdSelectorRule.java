package com.zafar.adnetwork.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ad_selector_rules database table.
 * 
 */
@Entity
@Table(name="ad_selector_rules")
@NamedQuery(name="AdSelectorRule.findAll", query="SELECT a FROM AdSelectorRule a")
public class AdSelectorRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="ad_id")
	private String adId;
	
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	@EmbeddedId
	private AdSelectorRulePK id;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public TravelType getTravel() {
		return travel;
	}

	public void setTravel(TravelType travel) {
		this.travel = travel;
	}

	@ManyToOne
	@JoinColumn(name="city_id",referencedColumnName="city_id", insertable=false, updatable=false)
	private City city;

	@ManyToOne
	@JoinColumn(name="travel_type", referencedColumnName="id", insertable=false, updatable=false)
	private TravelType travel;
	

	public AdSelectorRule() {
	}

	public AdSelectorRulePK getId() {
		return this.id;
	}

	public void setId(AdSelectorRulePK id) {
		this.id = id;
	}

}