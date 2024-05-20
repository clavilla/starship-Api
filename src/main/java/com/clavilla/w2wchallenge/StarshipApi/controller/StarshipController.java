package com.clavilla.w2wchallenge.StarshipApi.controller;

import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipRequestDto;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipResponseDto;
import com.clavilla.w2wchallenge.StarshipApi.service.StarshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController {

    private final StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @GetMapping("/getAllStarships")
    public ResponseEntity<List<StarshipResponseDto>> getAllStarships() {
        List<StarshipResponseDto> starships = starshipService.findAll();
        return new ResponseEntity<>(starships, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarshipResponseDto> getStarshipById(@PathVariable Long id) {
        StarshipResponseDto starship = starshipService.findById(id);
        return new ResponseEntity<>(starship, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<StarshipResponseDto>> getStarshipByNameContaining(@RequestParam String name) {
        List<StarshipResponseDto> starships = starshipService.findByNameContainingIgnoreCase(name.toLowerCase());
        return new ResponseEntity<>(starships, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StarshipResponseDto> createStarship(
            @Validated  @RequestBody StarshipRequestDto starshipDto) {
        StarshipResponseDto newStarship = starshipService.create(starshipDto);
        return new ResponseEntity<>(newStarship, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StarshipResponseDto> updateStarship(
            @PathVariable Long id, @Validated @RequestBody StarshipRequestDto starshipDto) {
        StarshipResponseDto updatedStarship = starshipService.update(id, starshipDto);
        return new ResponseEntity<>(updatedStarship, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStarship(@PathVariable Long id) {
        starshipService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
