package com.clavilla.w2wchallenge.StarshipApi.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StarshipTest {

    @Test
    public void testStarship() {

        Starship starship = new Starship();
        starship.setId(1L);
        starship.setName("Enterprise");
        starship.setModel("NCC-1701-D");
        starship.setStarshipClass("Galaxy");
        LocalDate now = LocalDate.now();
        starship.setCreatedAt(now);
        starship.setUpdatedAt(now);

        assertEquals(1L, starship.getId());
        assertEquals("Enterprise", starship.getName());
        assertEquals("NCC-1701-D", starship.getModel());
        assertEquals("Galaxy", starship.getStarshipClass());
        assertEquals(now, starship.getCreatedAt());
        assertEquals(now, starship.getUpdatedAt());
    }
}