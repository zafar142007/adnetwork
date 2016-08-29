package com.zafar.adnetwork.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the partner_urls database table.
 * 
 */
@Entity
@Table(name="partner_urls")
@NamedQuery(name="PartnerUrl.findAll", query="SELECT p FROM PartnerUrl p")
public class PartnerUrl implements Serializable {
	private static final long serialVersionUID = 1L;

	 
	@Column(name="default_ad_id")
	private int defaultAdId;

	@Column(name="partner_id")
	private int partnerId;

	@Id
	@Column(name="partner_url")
	private String partnerUrl;

//	//bi-directional one-to-one association to CrawledInfo
//	@OneToOne(mappedBy="partnerUrl")
//	private CrawledInfo crawledInfo;

	public PartnerUrl() {
	}


	public int getDefaultAdId() {
		return this.defaultAdId;
	}

	public void setDefaultAdId(int defaultAdId) {
		this.defaultAdId = defaultAdId;
	}

	public int getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerUrl() {
		return this.partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

//	public CrawledInfo getCrawledInfo() {
//		return this.crawledInfo;
//	}
//
//	public void setCrawledInfo(CrawledInfo crawledInfo) {
//		this.crawledInfo = crawledInfo;
//	}

}