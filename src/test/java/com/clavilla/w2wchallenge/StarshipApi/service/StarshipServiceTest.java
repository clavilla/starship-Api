package com.clavilla.w2wchallenge.StarshipApi.service;

import com.clavilla.w2wchallenge.StarshipApi.exception.ResourceNotFoundException;
import com.clavilla.w2wchallenge.StarshipApi.mapper.StarshipMapper;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipRequestDto;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipResponseDto;
import com.clavilla.w2wchallenge.StarshipApi.model.entity.Starship;
import com.clavilla.w2wchallenge.StarshipApi.repository.StarshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StarshipServiceTest {

    @Mock
    private StarshipRepository starshipRepository;

    @Mock
    private StarshipMapper starshipMapper;

    @InjectMocks
    private StarshipService starshipService;

    @Test
    public void testFindAll() {
        Pageable pageable = Pageable.unpaged();
        Starship starship = new Starship();
        Page<Starship> starshipPage = new PageImpl<>(Collections.singletonList(starship));
        when(starshipRepository.findAll(pageable)).thenReturn(starshipPage);

        StarshipResponseDto starshipResponseDto = new StarshipResponseDto();
        when(starshipMapper.convertToPagetDto(any(Page.class))).thenReturn(new PageImpl<>(Collections.singletonList(starshipResponseDto)));

        Page<StarshipResponseDto> result = starshipService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(starshipRepository, times(1)).findAll(pageable);
        verify(starshipMapper, times(1)).convertToPagetDto(starshipPage);
    }

    @Test
    public void testFindByIdSuccess() {
        Long id = 1L;
        Starship starship = new Starship();
        when(starshipRepository.findById(id)).thenReturn(Optional.of(starship));
        StarshipResponseDto dto = new StarshipResponseDto();
        when(starshipMapper.convertToDto(any(Starship.class))).thenReturn(dto);

        StarshipResponseDto result = starshipService.findById(id);

        assertNotNull(result);
        verify(starshipRepository, times(1)).findById(id);
        verify(starshipMapper, times(1)).convertToDto(starship);
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(starshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            starshipService.findById(id);
        });
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        String name = "Enterprise";
        List<Starship> starships = Collections.singletonList(new Starship());
        when(starshipRepository.findByNameContainingIgnoreCase(name)).thenReturn(starships);
        List<StarshipResponseDto> dtos = Collections.singletonList(new StarshipResponseDto());
        when(starshipMapper.convertToListDto(anyList())).thenReturn(dtos);

        List<StarshipResponseDto> result = starshipService.findByNameContainingIgnoreCase(name);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(starshipRepository, times(1)).findByNameContainingIgnoreCase(name);
        verify(starshipMapper, times(1)).convertToListDto(starships);
    }

    @Test
    public void testFindByNameContainingIgnoreCaseNotFound() {
        String name = "NonExistent";
        when(starshipRepository.findByNameContainingIgnoreCase(name)).thenReturn(Collections.emptyList());

        List<StarshipResponseDto> result = starshipService.findByNameContainingIgnoreCase(name);

        assertTrue(result.isEmpty());
        verify(starshipRepository, times(1)).findByNameContainingIgnoreCase(name);
        verify(starshipMapper, times(0)).convertToListDto(anyList());
    }

    @Test
    public void testCreate() {
        StarshipRequestDto requestDto = new StarshipRequestDto();
        Starship starship = new Starship();
        when(starshipMapper.convertToEntity(any(StarshipRequestDto.class))).thenReturn(starship);
        when(starshipRepository.save(any(Starship.class))).thenReturn(starship);
        StarshipResponseDto responseDto = new StarshipResponseDto();
        when(starshipMapper.convertToDto(any(Starship.class))).thenReturn(responseDto);

        StarshipResponseDto result = starshipService.create(requestDto);

        assertNotNull(result);
        verify(starshipMapper, times(1)).convertToEntity(requestDto);
        verify(starshipRepository, times(1)).save(starship);
        verify(starshipMapper, times(1)).convertToDto(starship);
    }

    @Test
    public void testUpdateSuccess() {
        Long id = 1L;
        StarshipRequestDto requestDto = new StarshipRequestDto();
        Starship starship = new Starship();
        when(starshipRepository.findById(id)).thenReturn(Optional.of(starship));
        when(starshipRepository.save(any(Starship.class))).thenReturn(starship);
        StarshipResponseDto responseDto = new StarshipResponseDto();
        when(starshipMapper.convertToDto(any(Starship.class))).thenReturn(responseDto);

        StarshipResponseDto result = starshipService.update(id, requestDto);

        assertNotNull(result);
        verify(starshipRepository, times(1)).findById(id);
        verify(starshipRepository, times(1)).save(starship);
        verify(starshipMapper, times(1)).convertToDto(starship);
    }

    @Test
    public void testUpdateNotFound() {
        Long id = 1L;
        StarshipRequestDto requestDto = new StarshipRequestDto();
        when(starshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            starshipService.update(id, requestDto);
        });
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doNothing().when(starshipRepository).deleteById(id);

        starshipService.deleteById(id);

        verify(starshipRepository, times(1)).deleteById(id);
    }
}