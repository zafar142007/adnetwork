package com.zafar.adnetwork.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the partner_info database table.
 * 
 */
@Entity
@Table(name="partner_info")
@NamedQuery(name="PartnerInfo.findAll", query="SELECT p FROM PartnerInfo p")
public class PartnerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="partner_id")
	private int partnerId;

	@Column(name="partner_name")
	private String partnerName;

	public PartnerInfo() {
	}

	public int getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

}