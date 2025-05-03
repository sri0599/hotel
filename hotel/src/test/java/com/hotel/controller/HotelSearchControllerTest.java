package com.hotel.controller;

import com.hotel.service.HotelSearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelSearchController.class)
class HotelSearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HotelSearchService hotelSearchService;

	@Test
	void testCheckAvailability() throws Exception {
		Mockito.when(hotelSearchService.getAvailableRoomCount("H1", "20250410", "SGL")).thenReturn(2L);

		mockMvc.perform(get("/Availability/H1/20250410/SGL")).andExpect(status().isOk())
				.andExpect(jsonPath("$.availableRoomCount").value(2));
	}

	@Test
	void testSearchAvailability() throws Exception {
		Map<String, Long> mockResult = new LinkedHashMap<>();
		mockResult.put("20250410-20250414", 2L);
		mockResult.put("20250415-20250420", 1L);

		Mockito.when(hotelSearchService.getAvailableRooms("H1", 30, "SGL")).thenReturn(mockResult);

		mockMvc.perform(get("/Search/H1/30/SGL")).andExpect(status().isOk())
				.andExpect(jsonPath("$.['20250410-20250414']").value(2))
				.andExpect(jsonPath("$.['20250415-20250420']").value(1));
	}
}
