package com.hotel.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hotel.vo.Booking;
import com.hotel.vo.Hotel;

@Service
public class HotelSearchService {

	/***[Command 1]*************************************/
	public long getAvailableRoomCount(String hotelId, String date, String roomType)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		Long availableRoomCount = 0L;

		String dates[] = date.split("-");
		String arrival = dates[0];
		String departure = arrival;

		if (dates.length > 1)
			departure = dates[1];

		Long totalRoomCount = getRoomCount(hotelId, arrival, departure, roomType);
		Long bookedRoomCount = getBookingCount(hotelId, arrival, departure, roomType);

		availableRoomCount = totalRoomCount - bookedRoomCount;

		return availableRoomCount;

	}

	/***Function to get total number or rooms*************************************/
	public long getRoomCount(String hotelId, String arrival, String departure, String roomType)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		Gson gson = new Gson();
		Type hotelListType = new TypeToken<List<Hotel>>() {}.getType();
		List<Hotel> hotels = gson.fromJson(new FileReader("src/main/resources/hotels.json"), hotelListType);

		long roomCount = hotels.stream()
				.filter(hotel -> hotelId.equals(hotel.getId()))
				.flatMap(hotel -> hotel.getRooms().stream())
				.filter(room -> roomType.equals(room.getRoomType()))
				.count();

		return roomCount;

	}
	
	/***Function to get total booked rooms*************************************/
	public long getBookingCount(String hotelId, String arrival, String departure, String roomType)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		Gson gson = new Gson();
		Type bookingListType = new TypeToken<List<Booking>>() {}.getType();
		List<Booking> bookings = gson.fromJson(new FileReader("src/main/resources/bookings.json"), bookingListType);

		long bookingCount = bookings.stream().filter(b -> hotelId.equals(b.getHotelId())) // filter by hotelId
				.filter(b -> roomType.equals(b.getRoomType())) // filter by roomType
				.filter(b -> (Integer.parseInt(arrival) <= Integer.parseInt(b.getArrival())
						&& Integer.parseInt(departure) >= Integer.parseInt(b.getArrival()))
						|| (Integer.parseInt(arrival) >= Integer.parseInt(b.getArrival())
								&& Integer.parseInt(arrival) <= Integer.parseInt(b.getDeparture())))
				// .peek(e -> System.out.println("dates - " + e.getArrival() + "-" +
				.count();

		return bookingCount;

	}

	/***[Command 2]*************************************/
	public Map<String, Long> getAvailableRooms(String hotelId, Integer days, String roomType)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		Map<String, Long> bookedCountPerDate = new LinkedHashMap<>();
		Map<String, Long> bookedCountPerRange = new LinkedHashMap<>();

		LocalDate searchStart = LocalDate.now();
		LocalDate searchEnd = searchStart.plusDays(days);

		LocalDate current = searchStart;

		while (current.isBefore(searchEnd)) {
			String dateStr = current.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			Long count = getAvailableRoomCount(hotelId, dateStr + "-" + dateStr, roomType);
			bookedCountPerDate.put(dateStr, count);
			current = current.plusDays(1);
		}
		bookedCountPerRange = formatDateRanges(bookedCountPerDate);
		//System.out.println(bookedCountPerRange);

		return bookedCountPerRange;
	}

	public Map<String, Long> formatDateRanges(Map<String, Long> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        List<String> sortedDates = new ArrayList<>(map.keySet());
        Collections.sort(sortedDates);

        Map<String, Long> result = new LinkedHashMap<>();

        String start = sortedDates.get(0);
        String end = start;
        Long currentCount = map.get(start);

        for (int i = 1; i < sortedDates.size(); i++) {
            String currentDateStr = sortedDates.get(i);
            LocalDate prevDate = LocalDate.parse(end, formatter);
            LocalDate currentDate = LocalDate.parse(currentDateStr, formatter);

            Long count = map.get(currentDateStr);

            if (count == currentCount && currentDate.minusDays(1).equals(prevDate)) {
                end = currentDateStr;
            } else {
                result.put(start + "-" + end, currentCount);
                start = currentDateStr;
                end = currentDateStr;
                currentCount = count;
            }
        }

        result.put(start + "-" + end, currentCount);

        return result;
    }
}
