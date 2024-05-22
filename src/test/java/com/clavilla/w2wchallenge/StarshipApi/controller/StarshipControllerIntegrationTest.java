package com.clavilla.w2wchallenge.StarshipApi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.clavilla.w2wchallenge.StarshipApi.utils.ErrorCatalog.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", password = "admin", roles={"USER","ADMIN"})
@Transactional
public class StarshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetAllStarships() throws Exception {
        mockMvc.perform(get("/starships/getAllStarships")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetStarshipById() throws Exception {
        mockMvc.perform(get("/starships/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetStarshipByNameContaining() throws Exception {
        mockMvc.perform(get("/starships/name")
                        .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateStarship() throws Exception {
        String starshipJson = "{\"name\":\"Test Starship\",\"model\":\"Model X\",\"starshipClass\":\"Class Y\"}";
        mockMvc.perform(post("/starships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(starshipJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateStarship() throws Exception {
        String starshipJson = "{\"name\":\"Updated Name\",\"model\":\"Updated Model\",\"starshipClass\":\"Updated Class\"}";
        mockMvc.perform(put("/starships/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(starshipJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteStarship() throws Exception {
        mockMvc.perform(delete("/starships/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testHandleStarshipNotFoundException() throws Exception {
        mockMvc.perform(get("/starships/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(STARSHIP_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(STARSHIP_NOT_FOUND.getMessage()));
    }

    @Test
    public void testHandleMethodArgumentNotValidException() throws Exception {
        String starshipJson = "{\"model\":\"Model X\",\"starshipClass\":\"Class Y\"}";
        mockMvc.perform(post("/starships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(starshipJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(INVALID_STARSHIP.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_STARSHIP.getMessage()));
    }

    @Test
    public void testHandleGenericError() throws Exception {
        mockMvc.perform(get("/starships/triggerGenericError"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(GENERIC_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(GENERIC_ERROR.getMessage()));
    }
}