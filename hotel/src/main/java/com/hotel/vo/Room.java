package com.hotel.vo;

public class Room {
	
	private String roomId;
	private String roomType;
	
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Room(String roomId, String roomType) {
		super();
		this.roomId = roomId;
		this.roomType = roomType;
	}

	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomType=" + roomType + "]";
	}
	
	
}
