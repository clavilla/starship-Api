package com.clavilla.w2wchallenge.StarshipApi.repository;

import com.clavilla.w2wchallenge.StarshipApi.model.entity.Starship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface StarshipRepository extends JpaRepository<Starship, Long> {

    @NonNull
    List<Starship> findAll();

    @NonNull
    Optional<Starship> findById(@NonNull Long id);

    List<Starship> findByNameContainingIgnoreCase(String name);

    void deleteById(@NonNull Long id);
}
