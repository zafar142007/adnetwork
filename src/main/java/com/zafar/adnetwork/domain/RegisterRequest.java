package com.zafar.adnetwork.domain;

public class RegisterRequest {

	private String partnerName;
	private String urlPaths[];

	public String[] getUrlPaths() {
		return urlPaths;
	}

	public void setUrlPaths(String[] urlPaths) {
		this.urlPaths = urlPaths;
	}	
	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	
}
