package com.clavilla.w2wchallenge.StarshipApi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "starships")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="model")
    private String model;
    @Column(name="starship_class")
    private String starshipClass;

}
