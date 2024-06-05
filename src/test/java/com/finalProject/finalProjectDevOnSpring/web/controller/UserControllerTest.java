package com.finalProject.finalProjectDevOnSpring.web.controller;

import com.finalProject.finalProjectDevOnSpring.TestApplication;
import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Set;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends TestApplication {

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_getAllUsers_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/user/all")
                        .param("page", "1")
                        .param("pageSize", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_findUserById_thenReturnOk() throws Exception {
        var expected = new UserDto(
                1L,
                "user",
                "user",
                Set.of(RoleType.ROLE_USER)
        );
        MvcResult result = mockMvc.perform(get("/api/v1/user")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(expected, objectMapper.readValue(jsonResponse, UserDto.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_saveNewUser_thenReturnNewUserDto() throws Exception {
        var newUser = new UserChangeRequest(
                "user1",
                "user1",
                "user1",
                Set.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN));

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.username").value(newUser.username()))
                .andExpect(jsonPath("$.email").value(newUser.email()))
                .andExpect(jsonPath("$.role").isArray())
                .andExpect(jsonPath("$.role", hasSize(2)))
                .andExpect(jsonPath("$.role", containsInAnyOrder(RoleType.ROLE_USER.name(), RoleType.ROLE_ADMIN.name())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_saveExistNewUser_thenReturnNewUserDto() throws Exception {
        var newUser = new UserChangeRequest(
                "user",
                "user",
                "user",
                Set.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN));

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_editUser_thenReturnEditedUserDto() throws Exception {
        var updatedUser = new UserChangeRequest(
                "user",
                "user",
                "user",
                Set.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN));

        mockMvc.perform(put("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser))
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(updatedUser.username()))
                .andExpect(jsonPath("$.email").value(updatedUser.email()))
                .andExpect(jsonPath("$.role").isArray())
                .andExpect(jsonPath("$.role", hasSize(2)))
                .andExpect(jsonPath("$.role", containsInAnyOrder(RoleType.ROLE_USER.name(), RoleType.ROLE_ADMIN.name())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void when_findByUsername_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/user/findByUsername")
                        .param("userName", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }
}
