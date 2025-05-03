package com.hotel.vo;

public class Booking {
	String hotelId;
    String arrival;
    String departure;
    String roomType;
    String roomRate;
     
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Booking(String hotelId, String arrival, String departure, String roomType, String roomRate) {
		super();
		this.hotelId = hotelId;
		this.arrival = arrival;
		this.departure = departure;
		this.roomType = roomType;
		this.roomRate = roomRate;
	}

	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomRate() {
		return roomRate;
	}
	public void setRoomRate(String roomRate) {
		this.roomRate = roomRate;
	}
    
    
}
