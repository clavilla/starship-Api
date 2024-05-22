package com.clavilla.w2wchallenge.StarshipApi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StarshipRequestDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El modelo no puede estar vacío")
    private String model;
    @NotBlank(message = "La clase de la nave no puede estar vacía")
    private String starshipClass;
}
