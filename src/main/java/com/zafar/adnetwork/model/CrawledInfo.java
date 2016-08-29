package com.zafar.adnetwork.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * The persistent class for the crawled_info database table.
 * 
 */
@Entity
@Table(name="crawled_info")
@NamedQuery(name="CrawledInfo.findAll", query="SELECT c FROM CrawledInfo c")
public class CrawledInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="page_id")
	private String pageId;

	@Column(name="city_names")
	private String cityNames;

	@Column(name="crawl_period")
	private Time crawlPeriod;

	@Column(name="other_keywords")
	private String otherKeywords;

	private Timestamp timestamp;

	@Column(name="travel_type_ids")
	private String travelTypeIds;

	//bi-directional one-to-one association to PartnerUrl
//	@OneToOne
//	@JoinColumn(name="page_id", insertable=false, updatable=false)
//	private PartnerUrl partnerUrl;

	public CrawledInfo() {
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}



	public String getCityNames() {
		return this.cityNames;
	}

	public void setCityNames(String cityNames) {
		this.cityNames = cityNames;
	}

	public Time getCrawlPeriod() {
		return this.crawlPeriod;
	}

	public void setCrawlPeriod(Time crawlPeriod) {
		this.crawlPeriod = crawlPeriod;
	}

	public String getOtherKeywords() {
		return this.otherKeywords;
	}

	public void setOtherKeywords(String otherKeywords) {
		StringBuffer buf=new StringBuffer();
		if(otherKeywords!=null){
			for(String s:otherKeywords.split(","))
				buf.append("'").append(s).append("',");
			this.otherKeywords = buf.substring(0,buf.length()-1);
		}
		
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTravelTypeIds() {
		return this.travelTypeIds;
	}

	public void setTravelTypeIds(String travelTypeIds) {
		this.travelTypeIds = travelTypeIds;
	}

//	public PartnerUrl getPartnerUrl() {
//		return this.partnerUrl;
//	}
//
//	public void setPartnerUrl(PartnerUrl partnerUrl) {
//		this.partnerUrl = partnerUrl;
//	}

}