package com.hotel.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.hotel.service.HotelSearchService;

@RestController
public class HotelSearchController {
	
	@Autowired
	private HotelSearchService hotelSearchService;
	
	
	/***[Command 1]*************************************/
	@GetMapping("/Availability/{hotelId}/{date}/{roomType}")
	public Map<String,Long> checkAvailability(@PathVariable String hotelId, @PathVariable String date,@PathVariable  String roomType) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		
		Map<String,Long> availableRoomCount = new LinkedHashMap<>();
		
		Long roomCount = hotelSearchService.getAvailableRoomCount(hotelId, date, roomType);
		availableRoomCount.put("availableRoomCount", roomCount);
				
		return availableRoomCount;
		
	}

	/***[Command 2]*************************************/
	@GetMapping("/Search/{hotelId}/{days}/{roomType}")
	public Map<String,Long> searchAvailability(@PathVariable String hotelId, @PathVariable Integer days, @PathVariable  String roomType) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		
		Map<String,Long> availableRoomCount = new LinkedHashMap<>();
		
		availableRoomCount = hotelSearchService.getAvailableRooms(hotelId, days, roomType);
		
		return availableRoomCount;
		
	}

}
