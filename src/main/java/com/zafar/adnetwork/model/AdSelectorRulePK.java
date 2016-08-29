package com.zafar.adnetwork.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdSelectorRulePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		
		@Column(name="city_id")
		private int cityId;	
		
		@Column(name="travel_type")
		private int travelType;
		
		@Column(name="other")
		private String other;

		public int getCityId() {
			return cityId;
		}

		public void setCityId(int cityId) {
			this.cityId = cityId;
		}

		public int getTravelType() {
			return travelType;
		}

		public void setTravelType(int travelType) {
			this.travelType = travelType;
		}

		public String getOther() {
			return other;
		}

		public void setOther(String other) {
			this.other = other;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cityId;
			result = prime * result + ((other == null) ? 0 : other.hashCode());
			result = prime * result + travelType;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AdSelectorRulePK other = (AdSelectorRulePK) obj;
			if (cityId != other.cityId)
				return false;
			if (this.other == null) {
				if (other.other != null)
					return false;
			} else if (!this.other.equals(other.other))
				return false;
			if (travelType != other.travelType)
				return false;
			return true;
		}	
	

}
