package com.clavilla.w2wchallenge.StarshipApi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StarshipResponseDto {

    private Long id;
    private String name;
    private String model;
    private String starshipClass;
}
