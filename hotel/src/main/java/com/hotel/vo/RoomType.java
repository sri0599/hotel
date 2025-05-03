package com.hotel.vo;

import java.util.List;

public class RoomType {
	
	private String code;
	private String description;
	private List<String> amenities;
	private List<String> features;
	
	public RoomType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomType(String code, String description, List<String> amenities, List<String> features) {
		super();
		this.code = code;
		this.description = description;
		this.amenities = amenities;
		this.features = features;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
	
 
}
