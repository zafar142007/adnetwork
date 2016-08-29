package com.zafar.adnetwork.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the advertisement database table.
 * 
 */
@Entity
@Table(name="advertisement")
@NamedQuery(name="Advertisement.findAll", query="SELECT a FROM Advertisement a")
public class Advertisement implements Serializable {
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ad_id")
	private int adId;

	@Column(name="ad_image_url")
	private String adImageUrl;

	@Column(name="ad_text")
	private String adText;

	@Column(name="destination_url")
	private String destinationUrl;

	public String getDestinationUrl() {
		return destinationUrl;
	}

	public void setDestinationUrl(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}

	public Advertisement() {
	}

	public int getAdId() {
		return this.adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getAdImageUrl() {
		return this.adImageUrl;
	}

	public void setAdImageUrl(String adImageUrl) {
		this.adImageUrl = adImageUrl;
	}

	public String getAdText() {
		return this.adText;
	}

	public void setAdText(String adText) {
		this.adText = adText;
	}

}