package com.zafar.adnetwork.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the advertisement database table.
 * 
 */
@Entity
@Table(name="dashboard")
@NamedQuery(name="Dashboard.findAll", query="SELECT a FROM Dashboard a")
public class Dashboard implements Serializable {
	private static final long serialVersionUID = 2L;

	@Id
	@Column(name="partner_url")
	private String partnerUrl;

	@Column(name="impressions")
	private int impressions;

	@Column(name="clicks")
	private int clicks;

	public Dashboard() {
	}

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}


}