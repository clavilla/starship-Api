package com.clavilla.w2wchallenge.StarshipApi.service;

import com.clavilla.w2wchallenge.StarshipApi.exception.ResourceNotFoundException;
import com.clavilla.w2wchallenge.StarshipApi.mapper.StarshipMapper;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipRequestDto;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipResponseDto;
import com.clavilla.w2wchallenge.StarshipApi.model.entity.Starship;
import com.clavilla.w2wchallenge.StarshipApi.repository.StarshipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StarshipService {

    private final StarshipRepository starshipRepository;
    private final StarshipMapper starshipMapper;

    public StarshipService(StarshipRepository starshipRepository, StarshipMapper starshipMapper) {
        this.starshipRepository = starshipRepository;
        this.starshipMapper = starshipMapper;
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<StarshipResponseDto> findAll(Pageable pageable) {
        Page<Starship> starshipPage = starshipRepository.findAll(pageable);
        return starshipMapper.convertToPagetDto(starshipPage);
    }

    @NonNull
    @Transactional(readOnly = true)
    public StarshipResponseDto findById(@NonNull Long id) {
        Starship starship = starshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Starship not found with id: " + id));
        return starshipMapper.convertToDto(starship);
    }

    @Transactional
    public List<StarshipResponseDto> findByNameContainingIgnoreCase(String name) {
        List<Starship> starshipList = starshipRepository.findByNameContainingIgnoreCase(name);
        return starshipMapper.convertToListDto(starshipList);
    }

    @Transactional
    public StarshipResponseDto create(StarshipRequestDto starshipRequestDto) {
        Starship starship = starshipMapper.convertToEntity(starshipRequestDto);
        starship.setCreatedAt(LocalDate.now());
        starship = starshipRepository.save(starship);
        return starshipMapper.convertToDto(starship);
    }

    @Transactional
    public StarshipResponseDto update(Long id, StarshipRequestDto starshipRequestDto) {
        Starship starship = starshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Starship not found with id: " + id));
        starship.setName(starshipRequestDto.getName());
        starship.setModel(starshipRequestDto.getModel());
        starship.setStarshipClass(starshipRequestDto.getStarshipClass());
        starship.setUpdatedAt(LocalDate.now());
        return starshipMapper.convertToDto(starshipRepository.save(starship));
    }

    @Transactional
    public void deleteById(@NonNull Long id) {
        starshipRepository.deleteById(id);
    }
}
