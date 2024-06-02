package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.TestApplication;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static com.finalProject.finalProjectDevOnSpring.testUtils.InitTestsUtils.HOTEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelControllerTest extends TestApplication {

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_getAllHotels_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/hotel/all")
                        .param("page", "1")
                        .param("pageSize", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_findHotelById_thenReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/hotel")
                .param("hotelId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(HOTEL, objectMapper.readValue(jsonResponse, HotelDto.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_saveNewHotel_thenReturnNewHotelDto() throws Exception {
        var newHotel = new HotelChangeRequest("Hotel 2",
                "Welcome to hotel 2",
                "Novosibirsk",
                "Address 2",
                333L);

        mockMvc.perform(post("/api/v1/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHotel)))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.hotelName").value(newHotel.hotelName()))
                .andExpect(jsonPath("$.headerAd").value(newHotel.headerAd()))
                .andExpect(jsonPath("$.cityName").value(newHotel.cityName()))
                .andExpect(jsonPath("$.address").value(newHotel.address()))
                .andExpect(jsonPath("$.distanceFromCityCenter").value(newHotel.distanceFromCityCenter()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_editHotel_thenReturnEditedHotelDto() throws Exception {
        var editedHotel = new HotelChangeRequest("Hotel 2",
                "Welcome to hotel 2",
                "Novosibirsk",
                "Address 2",
                333L);

        mockMvc.perform(put("/api/v1/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedHotel))
                        .param("hotelId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.hotelName").value(editedHotel.hotelName()))
                .andExpect(jsonPath("$.headerAd").value(editedHotel.headerAd()))
                .andExpect(jsonPath("$.cityName").value(editedHotel.cityName()))
                .andExpect(jsonPath("$.address").value(editedHotel.address()))
                .andExpect(jsonPath("$.distanceFromCityCenter").value(editedHotel.distanceFromCityCenter()));
    }
}
