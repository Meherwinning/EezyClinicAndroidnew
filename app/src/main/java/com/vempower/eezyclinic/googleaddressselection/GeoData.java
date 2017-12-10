package com.vempower.eezyclinic.googleaddressselection;

import java.io.Serializable;

public class GeoData implements Serializable {
	
	private String address,lat,lng,placeId;
	
	public GeoData() {
		this("", "0", "0");
		// TODO Auto-generated constructor stub
	}
	
	

	public GeoData(String address, String lat, String lng) {
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}



	public String getAddress() {
		return address;
	}

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "GeoData [address=" + address + ", lat=" + lat + ", lng=" + lng
				+ "]";
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}


