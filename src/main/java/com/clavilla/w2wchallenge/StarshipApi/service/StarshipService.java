package com.clavilla.w2wchallenge.StarshipApi.service;

import com.clavilla.w2wchallenge.StarshipApi.exception.ResourceNotFoundException;
import com.clavilla.w2wchallenge.StarshipApi.mapper.StarshipMapper;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipRequestDto;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipResponseDto;
import com.clavilla.w2wchallenge.StarshipApi.model.entity.Starship;
import com.clavilla.w2wchallenge.StarshipApi.repository.StarshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class StarshipService {

    private final StarshipRepository starshipRepository;
    private final StarshipMapper starshipMapper;

    public StarshipService(StarshipRepository starshipRepository, StarshipMapper starshipMapper) {
        this.starshipRepository = starshipRepository;
        this.starshipMapper = starshipMapper;
    }

    @NonNull
    @Cacheable(value = "starships", key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()")
    @Transactional(readOnly = true)
    public Page<StarshipResponseDto> findAll(Pageable pageable) {
        Page<Starship> starshipPage = starshipRepository.findAll(pageable);
        log.info("Retrieved {} starships from data base", starshipPage.getTotalElements());
        return starshipMapper.convertToPagetDto(starshipPage);
    }

    @NonNull
    @Cacheable(value = "starshipsById", key = "#id")
    @Transactional(readOnly = true)
    public StarshipResponseDto findById(@NonNull Long id) {
        Starship starship = starshipRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        log.info("Retrieved starship with id: {} from data base", id);
        return starshipMapper.convertToDto(starship);
    }

    @Cacheable(value = "starshipsByName", key = "#name")
    @Transactional(readOnly = true)
    public List<StarshipResponseDto> findByNameContainingIgnoreCase(String name) {
        List<Starship> starshipList = starshipRepository.findByNameContainingIgnoreCase(name);
        log.info("{} starships obtained from data base", starshipList.size());
        return starshipMapper.convertToListDto(starshipList);
    }

    @Transactional
    @CacheEvict(value = {"starships", "starshipsByName", "starshipsById"}, allEntries = true)
    public StarshipResponseDto create(StarshipRequestDto starshipRequestDto) {
        Starship starship = starshipMapper.convertToEntity(starshipRequestDto);
        starship.setCreatedAt(LocalDate.now());
        starship = starshipRepository.save(starship);
        return starshipMapper.convertToDto(starship);
    }

    @Transactional
    @CacheEvict(value = {"starships", "starshipsByName", "starshipsById"}, allEntries = true)
    @CachePut(value = "starshipsById", key = "#id")
    public StarshipResponseDto update(Long id, StarshipRequestDto starshipRequestDto) {
        Starship starship = starshipRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        starship.setName(starshipRequestDto.getName());
        starship.setModel(starshipRequestDto.getModel());
        starship.setStarshipClass(starshipRequestDto.getStarshipClass());
        starship.setUpdatedAt(LocalDate.now());
        return starshipMapper.convertToDto(starshipRepository.save(starship));
    }

    @Transactional
    @CacheEvict(value = {"starships", "starshipsByName", "starshipsById"}, allEntries = true)
    public void deleteById(@NonNull Long id) {
        starshipRepository.deleteById(id);
    }
}
