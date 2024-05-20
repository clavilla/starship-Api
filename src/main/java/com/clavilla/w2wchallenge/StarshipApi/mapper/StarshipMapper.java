package com.clavilla.w2wchallenge.StarshipApi.mapper;

import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipRequestDto;
import com.clavilla.w2wchallenge.StarshipApi.model.dto.StarshipResponseDto;
import com.clavilla.w2wchallenge.StarshipApi.model.entity.Starship;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StarshipMapper {

    private final ModelMapper modelMapper;

    public StarshipMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Starship convertToEntity(StarshipRequestDto starshipRequestDto) {
        return modelMapper.map(starshipRequestDto, Starship.class);
    }

    public StarshipResponseDto convertToDto(Starship starship) {
        return modelMapper.map(starship, StarshipResponseDto.class);
    }

    public List<StarshipResponseDto> convertToListDto(List<Starship> starshipList) {
        return starshipList.stream().map(this::convertToDto).toList();
    }

    public Page<StarshipResponseDto> convertToPagetDto(Page<Starship> starshipPage) {
        return starshipPage.map(this::convertToDto);
    }
}
