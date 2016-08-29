package com.zafar.adnetwork.domain;

import java.util.Set;

public class PageMetrics {
	private Set<String> cityNames;
	private Set<TravelType> travelTypes;	
	private Set<String> keyWords;
	public PageMetrics() {
	}

	public Set<String> getCityNames() {
		return cityNames;
	}
	public void setCityNames(Set<String> cityNames) {
		this.cityNames = cityNames;
	}
	public Set<TravelType> getTravelNeeds() {
		return travelTypes;
	}
	public void setTravelNeeds(Set<TravelType> travelNeeds) {
		this.travelTypes = travelNeeds;
	}
	public Set<String> getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(Set<String> keyWords) {
		this.keyWords = keyWords;
	}
	
}
