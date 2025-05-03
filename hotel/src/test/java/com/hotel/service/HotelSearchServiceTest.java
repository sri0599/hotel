package com.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.google.gson.Gson;
import com.hotel.vo.Booking;
import com.hotel.vo.Hotel;
import com.hotel.vo.Room;
import com.hotel.vo.RoomType;

class HotelSearchServiceTest {

	private HotelSearchService service;

	@BeforeEach
	void setUp() {
		service = new HotelSearchService();
	}

	@Test
	void testFormatDateRanges() {
		Map<String, Long> input = new LinkedHashMap<>();
		input.put("20250410", 2L);
		input.put("20250411", 2L);
		input.put("20250412", 2L);
		input.put("20250413", 1L);
		input.put("20250414", 1L);
		input.put("20250415", 2L);

		Map<String, Long> result = service.formatDateRanges(input);

		Map<String, Long> expected = new LinkedHashMap<>();
		expected.put("20250410-20250412", 2L);
		expected.put("20250413-20250414", 1L);
		expected.put("20250415-20250415", 2L);

		assertEquals(expected, result);
	}

	@Test
	void testGetRoomCount() throws FileNotFoundException {
		// Mock Gson and hotel list
		Gson gsonMock = mock(Gson.class);
		List<Room> rooms = Arrays.asList(new Room("101", "SGL"), new Room("102", "SGL"));
		List<RoomType> roomTypes = Arrays.asList(
				new RoomType("SGL", "Single Room", Arrays.asList("WiFi", "TV"),
						Arrays.asList("Non-smoking", "Sea View")),
				new RoomType("DBL", "Double Room", Arrays.asList("WiFi", "TV", "Minibar"),
						Arrays.asList("Non-smoking", "Sea View")));
		List<Hotel> hotels = Collections.singletonList(new Hotel("H1", "Hotel California", null, rooms));

		try (MockedStatic<Gson> gsonStatic = mockStatic(Gson.class)) {
			gsonStatic.when(Gson::new).thenReturn(gsonMock);
			when(gsonMock.fromJson(any(FileReader.class), any(Type.class))).thenReturn(hotels);

			long count = service.getRoomCount("H1", "20250410", "20250411", "SGL");
			assertEquals(2, count);
		}
	}

	@Test
	void testGetBookingCount() throws FileNotFoundException {
		List<Booking> bookings = Arrays.asList(new Booking("H1", "20250410", "20250412", "SGL", "Standard"),
				new Booking("H1", "20250411", "20250413", "SGL", "Standard"));

		Gson gsonMock = mock(Gson.class);

		try (MockedStatic<Gson> gsonStatic = mockStatic(Gson.class)) {
			gsonStatic.when(Gson::new).thenReturn(gsonMock);
			when(gsonMock.fromJson(any(FileReader.class), any(Type.class))).thenReturn(bookings);

			long booked = service.getBookingCount("H1", "20250410", "20250411", "SGL");
			assertEquals(2, booked);
		}
	}

	@Test
	void testGetAvailableRoomCount() throws FileNotFoundException {
		HotelSearchService spyService = spy(service);

		doReturn(3L).when(spyService).getRoomCount("H1", "20250410", "20250411", "SGL");
		doReturn(1L).when(spyService).getBookingCount("H1", "20250410", "20250411", "SGL");

		long available = spyService.getAvailableRoomCount("H1", "20250410-20250411", "SGL");
		assertEquals(2, available);
	}

}
