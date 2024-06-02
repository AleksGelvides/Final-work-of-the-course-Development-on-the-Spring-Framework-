package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.TestApplication;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.room.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.finalProject.finalProjectDevOnSpring.testUtils.InitTestsUtils.HOTEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTest extends TestApplication {

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_getAllRoomsFromHotel_1_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/room/all")
                        .param("page", "1")
                        .param("pageSize", "20")
                        .param("hotelId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_findRoomById_thenReturnOk() throws Exception {
        var expected = new RoomDto(
                1L,
                "Room #1",
                "Room #1",
                1,
                3,
                BigDecimal.ONE.setScale(2),
                HOTEL);
        MvcResult result = mockMvc.perform(get("/api/v1/room")
                        .param("roomId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(expected, objectMapper.readValue(jsonResponse, RoomDto.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_saveNewRoom_thenReturnNewRoomDto() throws Exception {
        var newRoom = new RoomChangeRequest(
                "Room #1000",
                "Room #1000",
                1000,
                1,
                1L);

        mockMvc.perform(post("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(21))
                .andExpect(jsonPath("$.roomName").value(newRoom.roomName()))
                .andExpect(jsonPath("$.roomDescription").value(newRoom.roomDescription()))
                .andExpect(jsonPath("$.roomNumber").value(newRoom.roomNumber()))
                .andExpect(jsonPath("$.maximumCapacity").value(newRoom.maximumCapacity()))
                .andExpect(jsonPath("$.hotel.id").value(newRoom.hotelId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_editHotel_thenReturnEditedHotelDto() throws Exception {
        var newRoom = new RoomChangeRequest(
                "Room #666",
                "Room #666",
                1001,
                1,
                1L);

        mockMvc.perform(put("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom))
                        .param("roomId", "1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomName").value(newRoom.roomName()))
                .andExpect(jsonPath("$.roomDescription").value(newRoom.roomDescription()))
                .andExpect(jsonPath("$.maximumCapacity").value(newRoom.maximumCapacity()))
                .andExpect(jsonPath("$.hotel.id").value(newRoom.hotelId()));
    }
}
